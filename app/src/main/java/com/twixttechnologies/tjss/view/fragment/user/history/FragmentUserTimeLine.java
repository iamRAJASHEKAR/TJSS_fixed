package com.twixttechnologies.tjss.view.fragment.user.history;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.UserTimeLineItem;
import com.twixttechnologies.tjss.utils.MeasureUtil;
import com.twixttechnologies.tjss.view.activity.UserTimeLineActivity;
import com.twixttechnologies.tjss.view.adapter.UserTimlineAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.UserTimeLineViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 07-10-17.
 */

public class FragmentUserTimeLine extends BaseFragment implements OnMapReadyCallback {

    public static final String TAG = "userTimLine";
    @BindView(R.id.viewTransparent)
    Space mViewTransparent;
    @BindView(R.id.imgTimeLineUserImage)
    CircleImageView mImgTimeLineUserImage;
    @BindView(R.id.lblTimeLineUserName)
    AppCompatTextView mLblTimeLineUserName;
    @BindView(R.id.lblTimeLineLastUpdateTime)
    AppCompatTextView mLblTimeLineLastUpdateTime;
    Unbinder unbinder;
    @BindView(R.id.userTimeLineCard)
    CardView mUserTimeLineCard;
    @BindView(R.id.lstTimeline)
    RecyclerView mLstTimeline;
    private final Observer<List<UserTimeLineItem>> mTimeLineObserver
            = new Observer<List<UserTimeLineItem>>() {
        @Override
        public void onChanged(@Nullable List<UserTimeLineItem> userTimeLineItems) {
            if (userTimeLineItems == null || userTimeLineItems.size() <= 0) return;

            mLstTimeline.setAdapter(new UserTimlineAdapter(userTimeLineItems, getActivity()));
            mLstTimeline.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstTimeline.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        }
    };
    SupportMapFragment mMap;
    private SafetyCircleMember mSafetyCircleMember;
    private GoogleMap mGoogleMap;
    private UserTimeLineViewModel mViewModel;

    public static FragmentUserTimeLine newInstance(SafetyCircleMember safetyCircleMember) {
        Bundle args = new Bundle();
        args.putParcelable(UserTimeLineActivity.SAFETY_CIRCLE_MEMBER, safetyCircleMember);
        FragmentUserTimeLine fragment = new FragmentUserTimeLine();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        Bundle args = getArguments();
        mSafetyCircleMember = args.getParcelable(UserTimeLineActivity.SAFETY_CIRCLE_MEMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_time_line, container, false);
        unbinder = ButterKnife.bind(this, view);
        int screenHeight = MeasureUtil.getScreenHeight();
        int cardHeight = mUserTimeLineCard.getHeight();
        mViewTransparent.getLayoutParams().height = screenHeight -
                (cardHeight <= 0 ? MeasureUtil.dpToPixel(175) : cardHeight - 125);
        mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);
        mViewTransparent.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new UserTimeLineViewModel.UserTimeLineViewModelFactory(getActivity().getApplication()))
                .get(UserTimeLineViewModel.class);

        mViewModel.getTimeLineData().observe(this, mTimeLineObserver);

        String imageBase = getString(R.string.image_base_url);
        Glide.with(this)
                .load(imageBase + mSafetyCircleMember.image)
                .apply(new RequestOptions()
                        .override(MeasureUtil.dpToPixel(55))
                        .placeholder(R.drawable.user_image)
                        .fallback(R.drawable.user_image))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        mImgTimeLineUserImage.setImageDrawable(resource);
                    }
                });

        mLblTimeLineUserName.setText(mSafetyCircleMember.fname);
        mLblTimeLineUserName.setSelected(true);
        mViewModel.getTimeLine(mSafetyCircleMember.userId);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
