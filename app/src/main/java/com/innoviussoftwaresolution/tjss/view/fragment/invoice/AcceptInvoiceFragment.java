package com.innoviussoftwaresolution.tjss.view.fragment.invoice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptInvoiceFragment extends Fragment {


    RecyclerView recyclerView;
    private List<TaskResponce.getdata> requestList;
    AcceptInvoiceAdaptor adaptor;

    public AcceptInvoiceFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_accept_invoice, container, false);
        // Inflate the layout for this fragment
        recyclerView =(RecyclerView)view.findViewById(R.id.recy_accept_list);
        return view;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (adaptor != null) {
            getData();
            adaptor.notifyDataSetChanged();
        }
    }


   public void getData(){
       EndpointsServices endpointsServices = APIClient.getClient().create(EndpointsServices.class);

       Call<TaskResponce> call = endpointsServices.getinvoicelist(PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, ""));

       call.enqueue(new Callback<TaskResponce>() {
           @Override
           public void onResponse(Call<TaskResponce> call, Response<TaskResponce> response) {


            requestList = new ArrayList<TaskResponce.getdata>();

               if (response != null){


                   for (int i = 0; i < response.body().taskList.size(); i++) {
                       if (response.body().taskList.get(i).getTaskStatus().equalsIgnoreCase("0")) {
                           requestList.add(response.body().taskList.get(i));
                       }



                   }



                   adaptor = new AcceptInvoiceAdaptor(getContext(), requestList);
                   recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                   recyclerView.setAdapter(adaptor);
               }



           }

           @Override
           public void onFailure(Call<TaskResponce> call, Throwable t) {
               Bugsnag.notify(new RuntimeException(t));
           }
       });

   }

}
