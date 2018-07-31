package com.twixttechnologies.tjss.view.fragment.helpalert;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.HelpAlert;
import com.twixttechnologies.tjss.model.network.response.HelpAlertDetail;
import com.twixttechnologies.tjss.view.adapter.listadapter.HelpAlertIncidentLogAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.HelpAlertDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 02-11-17.
 */

public class FragmentHelpAlertDetail extends BaseFragment {

    public static final String TAG = "helpAlertDetail";

    @BindView(R.id.btnIncidentType)
    AppCompatButton mBtnIncidentType;
    @BindView(R.id.lblIncidentHeading)
    AppCompatTextView mLblIncidentHeading;
    @BindView(R.id.lblIncidentTimeAndDate)
    AppCompatTextView mLblIncidentTimeAndDate;
    @BindView(R.id.lblIncidentDescription)
    AppCompatTextView mLblIncidentDescription;
    @BindView(R.id.lstIncidentLog)
    RecyclerView mLstIncidentLog;
    Unbinder unbinder;

    private HelpAlert mHelpAlert;

    public static FragmentHelpAlertDetail newInstance(HelpAlert helpAlert) {
        Bundle args = new Bundle();
        args.putParcelable("helpAlert", helpAlert);
        FragmentHelpAlertDetail fragment = new FragmentHelpAlertDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        mHelpAlert = getArguments().getParcelable("helpAlert");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HelpAlertDetailViewModel viewModel = ViewModelProviders.of(this,
                new HelpAlertDetailViewModel.HelpAlertDetailViewModelFactory(getActivity().getApplication()))
                .get(HelpAlertDetailViewModel.class);

        viewModel.getHelpAlertDetailData().observe(this, new Observer<HelpAlertDetail>() {
            @Override
            public void onChanged(@Nullable HelpAlertDetail helpAlertDetail) {
                if (helpAlertDetail == null) return;
                mLblIncidentDescription.setText(helpAlertDetail.provideName);
                mLblIncidentHeading.setText(helpAlertDetail.displayName);
                mLblIncidentTimeAndDate.setText(helpAlertDetail.createdAt);

                mBtnIncidentType.setText(helpAlertDetail.createdAt);

                mLstIncidentLog.setAdapter(new HelpAlertIncidentLogAdapter(helpAlertDetail.log, getActivity()));
                mLstIncidentLog.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });

        viewModel.getDetails(mHelpAlert);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_alert_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
