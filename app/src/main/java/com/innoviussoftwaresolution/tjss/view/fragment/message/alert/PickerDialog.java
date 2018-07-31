package com.innoviussoftwaresolution.tjss.view.fragment.message.alert;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.innoviussoftwaresolution.tjss.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 22/11/17.
 */

public class PickerDialog extends AppCompatDialogFragment {

    Unbinder unbinder;
    private PickerSelectionListener mListener;

    public void setListener(PickerSelectionListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnCheckedChanged({R.id.rdoPickerImageGallery, R.id.rdoPickerImageCamera, R.id.rdoPickerVideoGallery,
            R.id.rdoPickerVideoCamera, R.id.rdoPickerAudioRecord, R.id.rdoPickerAudioGallery})
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (!checked) return;
        @SelectionType int selected = INVALID;
        switch (compoundButton.getId()) {
            case R.id.rdoPickerImageGallery:
                selected = IMAGE_GALLERY;
                break;

            case R.id.rdoPickerImageCamera:
                selected = IMAGE_CAMERA;
                break;

            case R.id.rdoPickerVideoGallery:
                selected = VIDEO_GALLERY;
                break;

            case R.id.rdoPickerVideoCamera:
                selected = VIDEO_CAMERA;
                break;

            case R.id.rdoPickerAudioGallery:
                selected = AUDIO_GALLERY;
                break;

            case R.id.rdoPickerAudioRecord:
                selected = AUDIO_RECORD;
                break;
        }
        if (selected != INVALID)
            if (mListener != null)
                mListener.onSelected(selected);

        dismiss();

    }

    public static final int INVALID = -1;
    public static final int IMAGE_GALLERY = 1;
    public static final int IMAGE_CAMERA = 2;
    public static final int VIDEO_GALLERY = 3;
    public static final int VIDEO_CAMERA = 4;
    public static final int AUDIO_GALLERY = 5;
    public static final int AUDIO_RECORD = 6;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @IntDef({IMAGE_GALLERY, IMAGE_CAMERA, VIDEO_GALLERY, VIDEO_CAMERA, AUDIO_GALLERY, AUDIO_RECORD,
            INVALID})
    @Retention(RetentionPolicy.SOURCE)
    @interface SelectionType {
    }


    public interface PickerSelectionListener {
        void onSelected(@SelectionType int type);
    }


}
