package com.innoviussoftwaresolution.tjss.view.fragment.home;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bugsnag.android.Bugsnag;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 18-09-17.
 */

public class FavPlaceMarkerInfoWindowDialog extends AppCompatDialogFragment {

    public static final String PARTS = "parts";
    Unbinder unbinder;
    @BindView(R.id.imgMarkerDetailUserImage)
    CircleImageView mImgMarkerDetailUserImage;
    @BindView(R.id.lblMarkerDetailsName)
    AppCompatTextView mLblMarkerDetailsUserName;
    private String[] parts;

    private GeoDataClient mGeoDataClient;

    public static FavPlaceMarkerInfoWindowDialog newInstance(String[] parts) {
        Bundle args = new Bundle();
        args.putStringArray(PARTS, parts);
        FavPlaceMarkerInfoWindowDialog fragment = new FavPlaceMarkerInfoWindowDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extra_fav_place_marker_info_window, container, false);
        unbinder = ButterKnife.bind(this, view);
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);

        Bugsnag.init(getContext());
        String placeId = parts[parts.length - 1];

        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                try {
                    PlacePhotoMetadataResponse photos = task.getResult();
                    PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                    PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                    Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            try {
                                PlacePhotoResponse photo = task.getResult();
                                Bitmap bitmap = photo.getBitmap();
                                mImgMarkerDetailUserImage.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                Bugsnag.notify(new RuntimeException(e));
                                M.log(e.getMessage());
                            }
                        }
                    });
                } catch (Exception e) {
                    Bugsnag.notify(new RuntimeException(e));
                    //M.log(e.getMessage());
                }
            }
        });
        mLblMarkerDetailsUserName.setText(parts[0]);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLblMarkerDetailsUserName.requestFocus();
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
        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parts = getArguments().getStringArray(PARTS);
    }

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

}
