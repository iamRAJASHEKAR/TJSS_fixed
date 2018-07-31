package com.innoviussoftwaresolution.tjss.ServiceProviderModule;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class ServiceProviderRequestList extends Fragment {

    private RecyclerView rvServiceProviderRequestList;
    private ArrayList<ServiceProviderRequestModel> list;
    private ServiceProviderRequestAdapter adapter;
    private ServiceProviderRequestModel requestModel;

    public ServiceProviderRequestList() {

    }


    public static ServiceProviderRequestList newInstance(String param1, String param2) {
        ServiceProviderRequestList fragment = new ServiceProviderRequestList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            requestModel=new ServiceProviderRequestModel();
            requestModel.setRequestTitle("Request " + String.valueOf(i+1));
            requestModel.setRequestDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry");
            list.add(requestModel);
        }
        adapter = new ServiceProviderRequestAdapter(getContext(), list);
        rvServiceProviderRequestList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServiceProviderRequestList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_provider_request_list, container, false);
        rvServiceProviderRequestList = (RecyclerView) view.findViewById(R.id.rvServiceProviderRequestList);
        return view;
    }

}
