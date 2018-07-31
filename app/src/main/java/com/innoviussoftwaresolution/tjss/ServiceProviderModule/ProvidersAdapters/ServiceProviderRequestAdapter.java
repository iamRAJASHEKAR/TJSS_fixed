package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.AcceptRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderGetAllRequestsRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.RequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.StatusResponseModel;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderRequestAdapter extends RecyclerView.Adapter<ServiceProviderRequestAdapter.ViewHolder> {

    private Context context;
    private AcceptRequestModel acceptRequestModel;

    private List<RequestModel> responseList;
    private RequestModel model;

    private ProviderGetAllRequestsRequestModel requestModel;
    private ProviderGetAllRequestResponseModel responseModel;


    public ServiceProviderRequestAdapter(Context context, List<RequestModel> responseList) {

        this.context = context;
        this.responseList = responseList;
    }

    @Override
    public ServiceProviderRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_provider_requests, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceProviderRequestAdapter.ViewHolder holder, final int position) {

        model = responseList.get(position);
        Log.e("aj", model.getCreated_at()+
                "----"+model.getId()+"----"+
                model.getProviderId()+"----"+
                model.getRequestStatus()+"----"+
                model.getUpdated_at()+"----"+
                model.getUserId());



        holder.tvRequestTiltle.setText("Request " + responseList.get(position).getId());
        holder.tvRequestDescription.setText("Test requests");
        acceptRequestModel = new AcceptRequestModel();
        holder.btnRequestReject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                acceptRequestModel.setRequestId(responseList.get(position).getId());
                acceptRequestModel.setRequestStatus("0");
                requestStatus(acceptRequestModel);
                getAllRequest();

            }
        });

        holder.btnRequestAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequestModel.setRequestId(responseList.get(position).getId());
                acceptRequestModel.setRequestStatus("1");
                requestStatus(acceptRequestModel);
                getAllRequest();
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseList.size();
        //return responseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRequestTiltle;
        public TextView tvRequestDescription;
        public AppCompatButton btnRequestAccept;
        public AppCompatButton btnRequestReject;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRequestTiltle = (TextView) itemView.findViewById(R.id.tvSrequestTitle);
            tvRequestDescription = (TextView) itemView.findViewById(R.id.tvSPrequestDescription);
            btnRequestAccept = (AppCompatButton) itemView.findViewById(R.id.btnSPrequestAccept);
            btnRequestReject = (AppCompatButton) itemView.findViewById(R.id.btnSPrequestReject);
        }

    }

    private void requestStatus(final AcceptRequestModel requestModel) {
        StatusResponseModel responseModel = new StatusResponseModel();

        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        Call<StatusResponseModel> call = services.requestStatus(requestModel);
        call.enqueue(new Callback<StatusResponseModel>() {
            @Override
            public void onResponse(Call<StatusResponseModel> call, Response<StatusResponseModel> response) {
                if (response != null) {
                    if (response.body() != null) {
                        M.showToast(context, "Request processed");



                    }
                }
            }

            @Override
            public void onFailure(Call<StatusResponseModel> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
                M.showToast(context, "Fialed to process request");

            }
        });


    }

    private void getAllRequest() {

        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        requestModel = new ProviderGetAllRequestsRequestModel();
        requestModel.setProviderId(PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, ""));

        Call<ProviderGetAllRequestResponseModel> call = services.getAllRequest(requestModel);
        call.enqueue(new Callback<ProviderGetAllRequestResponseModel>() {
            @Override
            public void onResponse(Call<ProviderGetAllRequestResponseModel> call, Response<ProviderGetAllRequestResponseModel> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getRequestList() != null || response.body().getRequestList().size() != 0) {

                            setRequestList(response.body().getRequestList());
                        } else {
                            responseList.add(new RequestModel());
                        }
                    }

                }
                Log.d("req", "success");
            }

            @Override
            public void onFailure(Call<ProviderGetAllRequestResponseModel> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
                Toast.makeText(context, "Failed to fetch requests", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setRequestList(List<RequestModel> list) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRequestStatus().equalsIgnoreCase("0")) {
                responseList.add(list.get(i));
                notifyDataSetChanged();
            }

        }

    }

}
