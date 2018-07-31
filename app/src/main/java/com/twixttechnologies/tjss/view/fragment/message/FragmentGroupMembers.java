package com.twixttechnologies.tjss.view.fragment.message;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.GroupMember;
import com.twixttechnologies.tjss.view.activity.GroupMembersListingActivity;
import com.twixttechnologies.tjss.view.adapter.listadapter.GroupMembersAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.GroupDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 28-10-17.
 */

public class FragmentGroupMembers extends BaseFragment {

    public static final String TAG = "groupMembers";
    @BindView(R.id.lstGroupMembers)
    RecyclerView mLstGroupMembers;
    Unbinder unbinder;

    private String mGroupId, mGroupName;

    public static FragmentGroupMembers newInstance(String groupId, String groupName) {
        Bundle args = new Bundle();
        args.putString(GroupMembersListingActivity.GROUP_ID, groupId);
        args.putString(GroupMembersListingActivity.GROUP_NAME, groupName);
        FragmentGroupMembers fragment = new FragmentGroupMembers();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        Bundle args = getArguments();
        mGroupId = args.getString(GroupMembersListingActivity.GROUP_ID);
        mGroupName = args.getString(GroupMembersListingActivity.GROUP_NAME);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_members, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GroupDetailViewModel viewModel = ViewModelProviders.of(this,
                new GroupDetailViewModel.GroupDetailViewModelFactory(getActivity().getApplication()))
                .get(GroupDetailViewModel.class);


        viewModel.getMembersData().observe(this, new Observer<List<GroupMember>>() {
            @Override
            public void onChanged(@Nullable List<GroupMember> groupMembers) {
                if (groupMembers == null || groupMembers.size() <= 0) return;
                mLstGroupMembers.setAdapter(new GroupMembersAdapter(groupMembers, getActivity()));
                mLstGroupMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
                mLstGroupMembers.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            }
        });

        viewModel.getGroupMembers(mGroupId);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(mGroupName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
