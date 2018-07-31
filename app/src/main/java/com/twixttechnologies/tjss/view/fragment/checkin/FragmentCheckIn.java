package com.twixttechnologies.tjss.view.fragment.checkin;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.CheckInHistory;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.view.adapter.listadapter.CheckInHistoryAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.CheckInViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 01-11-17.
 */

public class FragmentCheckIn extends BaseFragment {

    public static final String TAG = "CheckIn";
    @BindView(R.id.lstCheckInHistory)
    RecyclerView mLstCheckInHistory;
    Unbinder unbinder;

    private SafetyCircleMember mCircleMember;


    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        mCircleMember = getArguments().getParcelable("member");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_in_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static FragmentCheckIn newInstance(SafetyCircleMember member) {

        Bundle args = new Bundle();
        args.putParcelable("member", member);
        FragmentCheckIn fragment = new FragmentCheckIn();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Checkin History");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckInViewModel viewModel = ViewModelProviders.of(this,
                new CheckInViewModel.CheckInViewModelFactory(getActivity().getApplication()))
                .get(CheckInViewModel.class);

        viewModel.getHistoryData().observe(this, new Observer<List<CheckInHistory>>() {
            @Override
            public void onChanged(@Nullable List<CheckInHistory> checkInHistories) {
                if (checkInHistories == null) return;
                if (checkInHistories.size() <= 0) {
                    CheckInHistoryAdapter adapter = new CheckInHistoryAdapter(checkInHistories);
                    adapter.setGotNoContent();
                    mLstCheckInHistory.setAdapter(adapter);
                    mLstCheckInHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                    return;
                }
                mLstCheckInHistory.setAdapter(new CheckInHistoryAdapter(checkInHistories));
                mLstCheckInHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        viewModel.getHistory(mCircleMember.userId);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
