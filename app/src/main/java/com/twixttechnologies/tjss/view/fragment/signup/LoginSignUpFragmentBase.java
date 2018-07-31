package com.twixttechnologies.tjss.view.fragment.signup;

import android.app.TaskStackBuilder;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.twixttechnologies.tjss.broadcastreceiver.EditorBroadCastReceiver;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;
import com.twixttechnologies.tjss.service.ChatListenerService;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.activity.HomeActivity;
import com.twixttechnologies.tjss.view.activity.SecurityQuestionsActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.security.FragmentSecurityQuestion;

/**
 * @author Sony Raj on 08-08-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public abstract class LoginSignUpFragmentBase extends BaseFragment implements EditorBroadCastReceiver.OnEditorActionListener {

    protected Observer<String> mErrorObserver
            = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        }
    };

    protected Observer<SignUpAndLoginResponse> mSignUpResponseObserver
            = new Observer<SignUpAndLoginResponse>() {
        @Override
        public void onChanged(@Nullable SignUpAndLoginResponse signUpByInviteCodeResponse) {
            dismissDialog();
            if (signUpByInviteCodeResponse == null) {
                Toast t = Toast.makeText(getActivity(), "Error!! Try again later", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, 0, 200);
                t.show();
            } else if (signUpByInviteCodeResponse.message == null || signUpByInviteCodeResponse.message.equals("Success")) {
                PreferenceHelper.saveBoolean(getActivity(), PreferenceHelper.KEY_USER_LOGGED_IN, true);

                //start service
                getActivity().startService(new Intent(getActivity().getApplication(), ChatListenerService.class));

                boolean hasSecurity = PreferenceHelper.getBoolean(getActivity(), PreferenceHelper.KEY_ADDED_SECURITY, false);


                Intent intent;
                if (!hasSecurity) {
                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getActivity());
                    Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
                    taskStackBuilder.addParentStack(HomeActivity.class);
                    taskStackBuilder.addNextIntent(homeIntent);

                    intent = new Intent(getActivity(), SecurityQuestionsActivity.class);
                    intent.putExtra(FragmentSecurityQuestion.REDIRECT, true);
                    taskStackBuilder.addNextIntent(intent);
                    taskStackBuilder.startActivities();
                } else {
                    intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }
                getActivity().startService(new Intent(getActivity(),ChatListenerService.class));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        }
    };
    private EditorBroadCastReceiver mReceiver;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            if (canGoBack()) {
                getActivity().onBackPressed();
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mReceiver == null)
            mReceiver = new EditorBroadCastReceiver(this);

        IntentFilter intentFilter = new IntentFilter(EditorBroadCastReceiver.EDITOR_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, intentFilter);
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity())
                .unregisterReceiver(mReceiver);
    }

    protected abstract boolean canGoBack();


}
