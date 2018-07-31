package com.innoviussoftwaresolution.tjss.view.fragment.home;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.innoviussoftwaresolution.tjss.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 18-09-17.
 */

public class SafetyCircleMarkerInfoWindowDialog extends AppCompatDialogFragment {

    public static final String PARTS = "parts";
    @BindView(R.id.imgMarkerDetailUserImage)
    CircleImageView mImgMarkerDetailUserImage;
    @BindView(R.id.lblMarkerDetailsName)
    AppCompatTextView mLblMarkerDetailsUserName;
    Unbinder unbinder;
    private OnMarkerOptionSelectedListener mMarkerOptionSelectedListener;
    private String[] parts;

    public static SafetyCircleMarkerInfoWindowDialog newInstance(String[] parts) {
        Bundle args = new Bundle();
        args.putStringArray(PARTS, parts);
        SafetyCircleMarkerInfoWindowDialog fragment = new SafetyCircleMarkerInfoWindowDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extra_safety_circle_marker_info_window, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLblMarkerDetailsUserName.setText(parts[0]);
        String baseUrl = getString(R.string.image_base_url);
        String url = baseUrl + parts[2];
        Glide.with(SafetyCircleMarkerInfoWindowDialog.this)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().override(125, 125).dontAnimate().dontTransform().placeholder(R.drawable.user_image))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        mImgMarkerDetailUserImage.setImageBitmap(resource);
                    }
                });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(layoutParams);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT).show();*/
        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parts = getArguments().getStringArray(PARTS);
    }

    public void setMarkerOptionSelectedListener(OnMarkerOptionSelectedListener listener) {
        mMarkerOptionSelectedListener = listener;
    }

    @OnClick(R.id.btnMarkerCall)
    public void onMBtnMarkerCallClicked() {

        String phone = parts[3];
        if (mMarkerOptionSelectedListener != null)
            mMarkerOptionSelectedListener.onSelected(OnMarkerOptionSelectedListener.CALL, phone);

    }

    @OnClick(R.id.btnMarkerMail)
    public void onMBtnMarkerMailClicked() {
        if (mMarkerOptionSelectedListener != null)
            mMarkerOptionSelectedListener.onSelected(OnMarkerOptionSelectedListener.MAIL, parts[parts.length - 2], parts[0]);
    }


    interface OnMarkerOptionSelectedListener {

        int CALL = 1;
        int MAIL = 2;

        void onSelected(@MarkerOption int selectedItem, String... extra);

        @IntDef({CALL, MAIL})
        @Retention(RetentionPolicy.SOURCE)
        @interface MarkerOption {
        }
    }

    //Mansi 16/5/2018
    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
    //end
}
