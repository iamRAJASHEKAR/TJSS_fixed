package com.innoviussoftwaresolution.tjss.view.fragment.helpalert;

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
import com.innoviussoftwaresolution.tjss.model.network.response.HelpAlert;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.HelpAlertsListingAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.HelpAlertListingViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 02-11-17.
 */

public class FragmentHelpAlertsListing extends BaseFragment  {


    public static final String TAG = "helpAlertListing";
    @BindView(R.id.lstCheckInHistory)
    RecyclerView mLstHelpAlertHistory;
    private final Observer<List<HelpAlert>> mHelpAlertHistoryObserver
            = new Observer<List<HelpAlert>>() {
        @Override
        public void onChanged(@Nullable List<HelpAlert> helpAlertContacts) {
            if (helpAlertContacts == null || helpAlertContacts.size() <= 0) {
                HelpAlertsListingAdapter adapter = new HelpAlertsListingAdapter(null, getActivity());
                adapter.setGotNoContent();
                mLstHelpAlertHistory.setAdapter(adapter);
                mLstHelpAlertHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                return;
            }
            mLstHelpAlertHistory.setAdapter(new HelpAlertsListingAdapter(helpAlertContacts,
                    getActivity()));
            mLstHelpAlertHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    };
    Unbinder unbinder;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_in_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HelpAlertListingViewModel viewModel = ViewModelProviders.of(this,
                new HelpAlertListingViewModel.HelpAlertListingViewModelFactory(getActivity().getApplication()))
                .get(HelpAlertListingViewModel.class);
        viewModel.getHelpAlertHistoryData().observe(this, mHelpAlertHistoryObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
