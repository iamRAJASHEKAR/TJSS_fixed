package com.twixttechnologies.tjss.view.fragment.generalalerts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.innoviussoftwaresolution.tjss.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20-10-17.
 */

public class TextInputDialog extends AppCompatDialogFragment {

    public static final String DIALOG_TITLE = "dialogTitle";
    @BindView(R.id.lblInputDialogTitle)
    AppCompatTextView mLblInputDialogTitle;
    @BindView(R.id.txtInputDialog)
    AppCompatEditText mTxtInputDialog;
    Unbinder unbinder;

    private OnTextConfirmedCallback mCallback;

    private String mTitle;

    public static TextInputDialog newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, title);
        TextInputDialog fragment = new TextInputDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCallback(OnTextConfirmedCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(DIALOG_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_input_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLblInputDialogTitle.setText(mTitle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnInputDialogSet, R.id.btnInputDialogCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnInputDialogSet:
                if (TextUtils.isEmpty(mTxtInputDialog.getText())) {
                    mTxtInputDialog.setError("Required!");
                    return;
                }
                mCallback.processText(mTxtInputDialog.getText());
                dismiss();
                break;
            case R.id.btnInputDialogCancel:
                dismiss();
                break;
        }
    }


    public interface OnTextConfirmedCallback {
        void processText(CharSequence text);
    }

}
