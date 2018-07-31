package com.innoviussoftwaresolution.tjss.view.fragment.checkin;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircleMember;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.CheckInListingActivity;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.SafetyCircleMembersListingAdapterForCheckIn;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.SafetyCircleViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20/11/17.
 */

public class FragmentSafetyCircleMembersListing extends BaseFragment implements SafetyCircleMembersListingAdapterForCheckIn.OnMemberSelectedCallback {

    public static final String TAG = "SafetyCircleMembersForCheckIn";
    @BindView(R.id.lstSafetyCircleMembers)
    RecyclerView mLstSafetyCircleMembers;
    @BindView(R.id.btnAddNewMember)
    AppCompatTextView mBtnAddNewMember;
    Unbinder unbinder;

    private SafetyCircleViewModel mSafetyCircleViewModel;

    private Observer<SafetyCircleMember[]> mSafetyCircleMembersObserver
            = new Observer<SafetyCircleMember[]>()
    {
        @Override
        public void onChanged(@Nullable SafetyCircleMember[] safetyCircleMembers) {
            if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;
            SafetyCircleMembersListingAdapterForCheckIn adapter
                    = new SafetyCircleMembersListingAdapterForCheckIn(new ArrayList<>(Arrays.asList(safetyCircleMembers)), getActivity(), FragmentSafetyCircleMembersListing.this);
            mLstSafetyCircleMembers.setAdapter(adapter);
            mLstSafetyCircleMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstSafetyCircleMembers.addItemDecoration(new DividerItemDecoration(getActivity(),
                    LinearLayoutManager.VERTICAL));

        }
    };


    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety_circle_members, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Select Member");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnAddNewMember.setVisibility(View.GONE);
        mSafetyCircleViewModel = ViewModelProviders.of(this,
                new SafetyCircleViewModel.SafetyCircleViewModelFactory(getActivity().getApplication()))
                .get(SafetyCircleViewModel.class);
        mSafetyCircleViewModel.getSafetyCircleMembersData().observe(this, mSafetyCircleMembersObserver);
        mLstSafetyCircleMembers.setHasFixedSize(true);
        mSafetyCircleViewModel.getCircleMembers(PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PRIMARY_CIRCLE, ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSelected(SafetyCircleMember member) {
        Intent intent = new Intent(getActivity(), CheckInListingActivity.class);
        intent.putExtra("member", member);
        startActivity(intent);
    }
}
