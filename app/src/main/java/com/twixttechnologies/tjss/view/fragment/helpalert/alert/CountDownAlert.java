package com.twixttechnologies.tjss.view.fragment.helpalert.alert;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.utils.M;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 04-11-17.
 */

public class CountDownAlert extends AppCompatDialogFragment {

    @BindView(R.id.lblCounter)
    TextSwitcher mLblCounter;
    @BindView(R.id.btnCancel)
    AppCompatButton mBtnCancel;
    Unbinder unbinder;
    private OnCountDownFinishedListener mListener;
    private CountDownTimer mCountDownTimer;

    public void setListener(OnCountDownFinishedListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count_down_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        Animation inAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
        Animation outAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);

        mLblCounter.setInAnimation(inAnimation);
        mLblCounter.setOutAnimation(outAnimation);

        mLblCounter.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                @SuppressLint("InflateParams")
                TextView progressTextView = (TextView) getActivity().getLayoutInflater()
                        .inflate(R.layout.extra_count_down_timer_text, null, false);
                progressTextView.setText(String.valueOf(10));
                return progressTextView;
            }
        });


        mCountDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    mLblCounter.setText(String.valueOf(millisUntilFinished / 1000));
                } catch (Exception e) {
                    M.log(e.getMessage());
                }
            }

            @Override
            public void onFinish() {
                mListener.onFinish();
                mLblCounter.setText(String.valueOf(1));
                dismiss();
            }
        };

        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }

        mCountDownTimer.start();

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnCancel)
    public void onViewClicked() {
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
        dismiss();
    }


    public interface OnCountDownFinishedListener {
        void onFinish();
    }

}
