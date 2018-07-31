package com.innoviussoftwaresolution.tjss.view.fragment.safetycircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.ShareInviteCodeActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 16-10-17.
 */

public class FragmentShareInviteCode extends BaseFragment {

    public static final String TAG = "shareInviteCode";
    @BindView(R.id.lblInviteCode)
    AppCompatTextView mLblInviteCode;
    Unbinder unbinder;

    private String inviteCode;

    public static FragmentShareInviteCode newInstance(String inviteCode) {
        Bundle args = new Bundle();
        args.putString(ShareInviteCodeActivity.INVITE_CODE, inviteCode);
        FragmentShareInviteCode fragment = new FragmentShareInviteCode();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        inviteCode = getArguments().getString(ShareInviteCodeActivity.INVITE_CODE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_invite_code, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLblInviteCode.setText(inviteCode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSendCode)
    public void onViewClicked() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        String name = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_FIRST_NAME, "");
        share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shareCodeSubject));
        share.putExtra(Intent.EXTRA_TEXT, name + " "+getString(R.string.joinCircleMsgText) + inviteCode);
        startActivity(Intent.createChooser(share, getString(R.string.shareViaText)));
    }
}
