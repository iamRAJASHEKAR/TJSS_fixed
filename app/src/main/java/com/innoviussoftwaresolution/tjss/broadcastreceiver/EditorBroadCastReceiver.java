package com.innoviussoftwaresolution.tjss.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Sony Raj on 28-10-17.
 */

public class EditorBroadCastReceiver extends BroadcastReceiver {

    public static final String EDITOR_ACTION = "com.twixttechnologies.tjss.bm.SHOW_NEXT";

    private OnEditorActionListener mActionListener;

    public EditorBroadCastReceiver(OnEditorActionListener actionListener) {
        mActionListener = actionListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(EDITOR_ACTION))
            mActionListener.showNext();
    }


    public interface OnEditorActionListener {
        void showNext();
    }

}
