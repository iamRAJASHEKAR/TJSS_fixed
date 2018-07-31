package com.twixttechnologies.tjss.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.twixttechnologies.tjss.model.internal.ChatInfo;
import com.twixttechnologies.tjss.service.ChatListenerService;

import static com.twixttechnologies.tjss.service.ChatListenerService.CHAT_INTENT_DATA;

/**
 * @author Sony Raj on 30-09-17.
 */

public class MessageReceiver extends BroadcastReceiver {

    private final MessageListener messageListener;

    public MessageReceiver(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(ChatListenerService.CHAT_ACTION)) return;
        if (messageListener != null) {
            ChatInfo chatInfo = intent.getParcelableExtra(CHAT_INTENT_DATA);
            messageListener.onNewMessage(chatInfo);
        }
    }

    public interface MessageListener {
        void onNewMessage(ChatInfo message);
    }

}
