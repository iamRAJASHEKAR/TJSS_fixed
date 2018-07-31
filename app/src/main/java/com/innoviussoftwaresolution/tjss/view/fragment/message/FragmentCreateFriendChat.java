package com.innoviussoftwaresolution.tjss.view.fragment.message;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircleMember;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.ChatDetailActivity;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.SafetyCircleMembersForFriendChatAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.CreateFriendChatViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 02-10-17.
 */

public class FragmentCreateFriendChat extends BaseFragment implements SearchView.OnQueryTextListener {

    public static final String TAG = "fragmentCreateFriendChat";
    @BindView(R.id.lstFriends)
    RecyclerView mLstFriends;
    private final Observer<SafetyCircleMember[]> mSafetyCircleMembersObserver
            = new Observer<SafetyCircleMember[]>() {
        @Override
        public void onChanged(@Nullable SafetyCircleMember[] safetyCircleMembers) {
            if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;
            String localUser = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "");
            ArrayList<SafetyCircleMember> members = new ArrayList<>(Arrays.asList(safetyCircleMembers));
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).userId.equals(localUser)) {
                    members.remove(i);
                    break;
                }
            }
            SafetyCircleMembersForFriendChatAdapter adapter
                    = new SafetyCircleMembersForFriendChatAdapter(members, getActivity());
            mLstFriends.setAdapter(adapter);
            mLstFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    };
    @BindView(R.id.btnCreateSingleChat)
    AppCompatButton mBtnCreateSingleChat;
    Unbinder unbinder;
    private CreateFriendChatViewModel mViewModel;
    private List<SafetyCircleMember> mSafetyCircleMembers;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager)
                getActivity().getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchMenuItem = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();


        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(this);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_friend_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new CreateFriendChatViewModel.CreateFriendChatViewModelFactory(getActivity().getApplication()))
                .get(CreateFriendChatViewModel.class);

        mViewModel.getSafetyCircleMembersData().observe(this, mSafetyCircleMembersObserver);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Create single chat");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnCreateSingleChat)
    public void onViewClicked() {
        SafetyCircleMember selectedMember = ((SafetyCircleMembersForFriendChatAdapter) mLstFriends.getAdapter()).getSelectedMember();
        if (selectedMember == null) {
            M.showToast(getActivity(), "Please select a friend");
            return;
        }

        Intent intent = new Intent(getActivity(), ChatDetailActivity.class);
        intent.putExtra(FragmentChatDetail.USERS, selectedMember.userId);
        intent.putExtra(FragmentChatDetail.GROUP_NAME, selectedMember.fname);
        intent.putExtra(FragmentChatDetail.NEW, "new");
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText);
        return true;
    }

    private void search(String text) {
        if (mSafetyCircleMembers != null)
            mSafetyCircleMembers.clear();
        if (mViewModel == null) return;
        SafetyCircleMember[] safetyCircleMembers = mViewModel.getSafetyCircleMembersData().getValue();
        if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;
        if (mSafetyCircleMembers == null) mSafetyCircleMembers = new ArrayList<>();

        String localUser = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "");
        if (TextUtils.isEmpty(text)) {
            mSafetyCircleMembers.addAll(Arrays.asList(safetyCircleMembers));
            for (int i = 0; i < mSafetyCircleMembers.size(); i++) {
                if (mSafetyCircleMembers.get(i).userId.equals(localUser)) {
                    mSafetyCircleMembers.remove(i);
                    break;
                }
            }
        } else {
            for (SafetyCircleMember s : safetyCircleMembers) {
                if (s.userId.equals(localUser)) continue;
                if (s.fname.toLowerCase().startsWith(text.toLowerCase()) || s.phone.startsWith(text)) {
                    mSafetyCircleMembers.add(s);
                }
            }
        }
        ((SafetyCircleMembersForFriendChatAdapter) mLstFriends.getAdapter()).replace(mSafetyCircleMembers);
    }


}
