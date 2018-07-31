package com.innoviussoftwaresolution.tjss.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.innoviussoftwaresolution.tjss.model.internal.ChatInfo;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @author Sony Raj on 30-09-17.
 */

public class ChatListenerService extends Service implements ChatConstants {

    private final Handler mHandler = new Handler();
    private Intent mBroadCastIntent = new Intent();
    private ConnectionRunnable mConnectionRunnable;
    private BroadcastReceiver mOutgoingChatIntentReceiver
            = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(OUTGOING_CHAT_ACTION)) return;
            M.log("local broadcast received");
            try {
                mConnectionRunnable.emit(intent.getStringExtra(EXTRA),
                        new JSONObject(intent.getStringExtra(EXTRA_PAYLOAD)));
            } catch (JSONException e) {
                M.log(e.getMessage());
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        M.log("Socket", "Chat service started");
        if (mConnectionRunnable == null) {
            mConnectionRunnable = new ConnectionRunnable();
            mHandler.post(mConnectionRunnable);
            IntentFilter outgoingChatIntentFilter = new IntentFilter(OUTGOING_CHAT_ACTION);
            LocalBroadcastManager.getInstance(getApplication()).
                    registerReceiver(mOutgoingChatIntentReceiver, outgoingChatIntentFilter);
        }

        String data = intent.getStringExtra(EXTRA);
        if (data != null) {
            if (data.equals(CONTACTS_ACTION)) {
                if (mConnectionRunnable != null)
                    mConnectionRunnable.sendContactData();
                else
                    M.log("Socket", "Connection runnable is null");
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getApplication())
                .unregisterReceiver(mOutgoingChatIntentReceiver);
        mConnectionRunnable.disconnect();
        mHandler.removeCallbacks(mConnectionRunnable);
        mConnectionRunnable = null;
        stopSelf();
        super.onDestroy();
    }

    private class UpdateRunnable implements Runnable {

        private ChatInfo mPayload;

        void setPayload(ChatInfo payload) {
            this.mPayload = payload;
        }

        @Override
        public void run() {
            mBroadCastIntent = new Intent();
            mBroadCastIntent.setAction(CHAT_ACTION);
            mBroadCastIntent.putExtra(CHAT_INTENT_DATA, mPayload);
            LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(mBroadCastIntent);
        }
    }

    private class ConnectionRunnable implements Runnable {

        private ChatInfo chatInfo = new ChatInfo();

        private Socket mSocket;
        private Emitter.Listener mOnConnectedListener;
        private Emitter.Listener mOnConnectionFailedListener;
        private Emitter.Listener mOnDisconnectedListener;
        private Emitter.Listener mOnContactListListener;
        private Emitter.Listener mOnChatHistory;
        private Emitter.Listener mOnSendMessage;
        private UpdateRunnable mUpdateRunnable = null;

        ConnectionRunnable() {
            Log.e("Socket", "inside Constructor");
            setUpUpdateRunnable();
            mOnConnectedListener
                    = new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    M.log("Socket", "Socket connected");
                    JSONObject jsonObject = new JSONObject();
                    String userId = PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_USER_ID, "");
                    try {
                        jsonObject.put("userId", userId);
                        jsonObject.put("roomId", userId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mSocket.emit(SEND_DATA, jsonObject);
                    sendUpdate(CONNECTED);
                }
            };
            mOnConnectionFailedListener
                    = new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    sendUpdate(CONNECTION_ERROR);
                }
            };
            mOnDisconnectedListener
                    = new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    sendUpdate(DISCONNECTED);
                }
            };

            mOnContactListListener
                    = new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    chatInfo.payload = args[0].toString();
                    sendUpdate(CONTACT_LIST);
                }
            };

            mOnChatHistory
                    = new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    chatInfo.payload = args[0].toString();
                    sendUpdate(CHAT_HISTORY);
                }
            };

            mOnSendMessage
                    = new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        chatInfo.payload = args[0].toString();
                        sendUpdate(SEND_MESSAGE);
                    } catch (Exception e) {
                        M.log(e.getMessage());
                    }
                }
            };


            String url = "http://165.227.91.250:3000";
            try {
                mSocket = IO.socket(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        private void setUpUpdateRunnable() {
            if (mUpdateRunnable == null)
                mUpdateRunnable = new UpdateRunnable();
        }

        void emit(String key, JSONObject payload) {
            try {
                mSocket.emit(key, payload);
            } catch (Exception e) {
                M.log(e.getMessage());
            }
        }

        void sendContactData() {
            JSONObject jsonObject = new JSONObject();
            String userId = PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_USER_ID, "");
            String circleId = PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
            try {
                jsonObject.put("userId", userId);
                jsonObject.put("circleId", circleId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSocket.emit(GET_CONTACT_LIST, jsonObject);
        }

        private void sendUpdate(String type) {
            chatInfo.type = type;
            mUpdateRunnable.setPayload(chatInfo);
            mHandler.post(mUpdateRunnable);
        }

        void disconnect() {
            mSocket.disconnect();
            mSocket.off(Socket.EVENT_CONNECT, mOnConnectedListener);
            mSocket.off(Socket.EVENT_CONNECT_ERROR, mOnConnectionFailedListener);
            mSocket.off(Socket.EVENT_DISCONNECT, mOnDisconnectedListener);
            mSocket.off(CONTACT_LIST, mOnContactListListener);
            mSocket.off(CHAT_HISTORY, mOnChatHistory);
            mSocket.off(SEND_MESSAGE, mOnSendMessage);
            mSocket = null;
        }

        @Override
        public void run() {
            if (!mSocket.connected()) {
                Log.e("Socket", "Not connected");
                mSocket.connect();
                mSocket.on(Socket.EVENT_CONNECT, mOnConnectedListener);
                mSocket.on(Socket.EVENT_CONNECT_ERROR, mOnConnectionFailedListener);
                mSocket.on(Socket.EVENT_DISCONNECT, mOnDisconnectedListener);
                mSocket.on(CONTACT_LIST, mOnContactListListener);
                mSocket.on(CHAT_HISTORY, mOnChatHistory);
                mSocket.on(SEND_MESSAGE, mOnSendMessage);
            } else {
                Log.e("Socket", "Socket already connected");
            }
            Log.e("Socket", this.toString());
        }

    }


}
