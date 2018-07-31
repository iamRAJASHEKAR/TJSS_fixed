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

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters.ServiceProviderTaskListAdapter;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestsRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.RequestModel;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceProviderTaskList extends Fragment {


    private RecyclerView rvServiceProviderTaskList;
    private ServiceProviderTaskListAdapter adapter;

    private ProviderGetAllRequestsRequestModel requestModel;
    private ProviderGetAllRequestResponseModel responseModel;
    private List<RequestModel> requestList;

    public ServiceProviderTaskList() {
        // Required empty public constructor
    }


    public static ServiceProviderTaskList newInstance(String param1, String param2) {
        ServiceProviderTaskList fragment = new ServiceProviderTaskList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllTask();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Toast.makeText(getActivity().getApplicationContext(), "1234567890", Toast.LENGTH_SHORT).show();
        if(adapter!=null) {
            getAllTask();
            adapter.notifyDataSetChanged();
            Log.d("Test", adapter.getItemCount() + "");
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_provider_task_list, container, false);
        rvServiceProviderTaskList = (RecyclerView) view.findViewById(R.id.rvServiceProviderTaskList);
        return view;
    }

    private void getAllTask() {

        requestList = new ArrayList<>();
        requestModel = new ProviderGetAllRequestsRequestModel();
        requestModel.setProviderId(PreferenceHelper.getString(getContext(), PreferenceHelper.KEY_USER_ID, ""));
        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        Call<ProviderGetAllRequestResponseModel> call = services.getAllTask(requestModel);
        call.enqueue(new Callback<ProviderGetAllRequestResponseModel>() {
            @Override
            public void onResponse(Call<ProviderGetAllRequestResponseModel> call, Response<ProviderGetAllRequestResponseModel> response) {
                if (response != null) {



                    if (response.body() != null) {
                        if(response.body().getRequestList()!=null || response.body().getRequestList().size()>0)
                        {
                            requestList.addAll(response.body().getRequestList());
                        }
                        else
                        {
                            requestList.add(new RequestModel());
                        }
                    }
                    else
                    {
                        M.showToast(getContext(),"Failed to fetch Tasks");
                    }
                    adapter=new ServiceProviderTaskListAdapter(getContext(),requestList);
                    rvServiceProviderTaskList.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvServiceProviderTaskList.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ProviderGetAllRequestResponseModel> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
            M.showToast(getContext(),"Failed to fetch tasks");
            }
        });
    }


}
