package com.innoviussoftwaresolution.tjss.view.fragment.message;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.innoviussoftwaresolution.tjss.broadcastreceiver.MessageReceiver;
import com.innoviussoftwaresolution.tjss.service.ChatListenerService;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;

/**
 * @author Sony Raj on 04-10-17.
 */

abstract class MessageFragmentBase extends BaseFragment implements MessageReceiver.MessageListener {


    private MessageReceiver mMessageReceiver;

    @Override
    public void onResume() {
        super.onResume();
        if (mMessageReceiver == null)
            mMessageReceiver = new MessageReceiver(this);

        IntentFilter intentFilter = new IntentFilter(ChatListenerService.CHAT_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMessageReceiver != null)
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }

}
