package com.twixttechnologies.tjss.view.fragment.incident;

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
import com.twixttechnologies.tjss.model.network.response.Incident;
import com.twixttechnologies.tjss.view.adapter.listadapter.IncidentsAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.IncidentsViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 19-10-17.
 */

public class FragmentIncidentListing extends BaseFragment {

    public static final String TAG = "Incidents";
    @BindView(R.id.lstIncidents)
    RecyclerView mLstIncidents;
    Unbinder unbinder;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incident_listing, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IncidentsViewModel viewModel = ViewModelProviders.of(this,
                new IncidentsViewModel.IncidentsViewModelFactory(getActivity().getApplication()))
                .get(IncidentsViewModel.class);


        viewModel.getIncidentsData().observe(this, new Observer<Incident[]>() {
            @Override
            public void onChanged(@Nullable Incident[] incidents) {
                if (incidents == null || incidents.length <= 0) return;
                mLstIncidents.setAdapter(new IncidentsAdapter(new ArrayList<>(Arrays.asList(incidents))));
                mLstIncidents.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Incidents");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
