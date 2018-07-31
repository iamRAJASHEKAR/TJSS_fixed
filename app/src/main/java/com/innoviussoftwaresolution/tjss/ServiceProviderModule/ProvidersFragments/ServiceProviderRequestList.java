package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters.ServiceProviderRequestAdapter;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestsRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.RequestModel;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceProviderRequestList extends Fragment {

    private RecyclerView rvServiceProviderRequestList;

    private ServiceProviderRequestAdapter adapter;
    private ProviderGetAllRequestsRequestModel requestModel;
    private ProviderGetAllRequestResponseModel responseModel;
    private List<RequestModel> requestList;

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
        getAllRequest();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Toast.makeText(getActivity().getApplicationContext(), "1234567890", Toast.LENGTH_SHORT).show();
        if(adapter!=null) {
            getAllRequest();
            adapter.notifyDataSetChanged();
            Log.d("Test", adapter.getItemCount() + "");
        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_provider_request_list, container, false);
        rvServiceProviderRequestList = (RecyclerView) view.findViewById(R.id.rvServiceProviderRequestList);
        return view;
    }

    private void getAllRequest() {
        requestList = new ArrayList<>();
        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        requestModel = new ProviderGetAllRequestsRequestModel();
        requestModel.setProviderId(PreferenceHelper.getString(getContext(), PreferenceHelper.KEY_USER_ID, ""));

        Call<ProviderGetAllRequestResponseModel> call = services.getAllRequest(requestModel);
        call.enqueue(new Callback<ProviderGetAllRequestResponseModel>() {
            @Override
            public void onResponse(Call<ProviderGetAllRequestResponseModel> call, Response<ProviderGetAllRequestResponseModel> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getRequestList() != null || response.body().getRequestList().size() != 0) {

                            setRequestList(response.body().getRequestList());
                        } else {
                            requestList.add(new RequestModel());
                        }
                    }

                }
                Log.d("req", "success");
            }

            @Override
            public void onFailure(Call<ProviderGetAllRequestResponseModel> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
                Toast.makeText(getContext(), "Failed to fetch requests", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setRequestList(List<RequestModel> list) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRequestStatus().equalsIgnoreCase("0")) {
                requestList.add(list.get(i));
            }

        }
        adapter = new ServiceProviderRequestAdapter(getContext(), requestList);
        rvServiceProviderRequestList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServiceProviderRequestList.setAdapter(adapter);
    }

}
