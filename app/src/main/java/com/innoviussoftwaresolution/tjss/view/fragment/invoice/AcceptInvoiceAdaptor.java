package com.innoviussoftwaresolution.tjss.view.fragment.invoice;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptInvoiceAdaptor extends RecyclerView.Adapter<AcceptInvoiceAdaptor.ViewHolder> {

    private Context context;

    private List<TaskResponce.getdata> responseList;

        TaskResponce.getdata getdata;

    public AcceptInvoiceAdaptor(Context context, List<TaskResponce.getdata> responseList) {

        this.context = context;
        this.responseList = responseList;
    }

    @Override
    public AcceptInvoiceAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_accept_request, parent, false);
        return new AcceptInvoiceAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AcceptInvoiceAdaptor.ViewHolder holder, final int position) {


        getdata = responseList.get(position);


        holder.tvTiltle.setText("Request By:  " + getdata.getProvideName());
        holder.tvDescription.setText("Description:  "+getdata.getDesc());
        holder.tvAmount.setText("₦ "+getdata.getAmount());

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStatus(getdata.taskId,"1");
                getData();

            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStatus(getdata.taskId,"0");
                getData();

            }
        });


    }

    @Override
    public int getItemCount() {
        return responseList.size();
        //return responseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTiltle, tvDescription,tvAmount;
        AppCompatButton btnAccept, btnReject;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTiltle = (TextView) itemView.findViewById(R.id.tv_c_a_Title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_c_a_Description);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_c_a_Amount);

            btnAccept = (AppCompatButton) itemView.findViewById(R.id.btn_c_a_Accept);
            btnReject = (AppCompatButton) itemView.findViewById(R.id.btn_c_a_Reject);
        }

    }

    private void requestStatus(String taskId,String taskstatus) {

        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        Call<TaskResponce> call = services.setrevietasklist(taskId,taskstatus);
        call.enqueue(new Callback<TaskResponce>() {
            @Override
            public void onResponse(Call<TaskResponce> call, Response<TaskResponce> response) {
                if (response != null) {
                    if (response.body() != null) {

                        M.showToast(context, "Request processed");



                    }
                }
            }

            @Override
            public void onFailure(Call<TaskResponce> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
                M.showToast(context, "Fialed to process request");

            }
        });


    }

    public void getData(){
        EndpointsServices endpointsServices = APIClient.getClient().create(EndpointsServices.class);

        Call<TaskResponce> call = endpointsServices.getinvoicelist(PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, ""));

        call.enqueue(new Callback<TaskResponce>() {
            @Override
            public void onResponse(Call<TaskResponce> call, Response<TaskResponce> response) {


                List<TaskResponce.getdata> datalist = new ArrayList<TaskResponce.getdata>();

                if (response != null){


                    responseList.clear();
                    for (int i = 0; i < response.body().taskList.size(); i++) {
                        if (response.body().taskList.get(i).getTaskStatus().equalsIgnoreCase("0")) {
                            datalist.add(response.body().taskList.get(i));
                          //  notifyDataSetChanged();
                        }
                    }

                    responseList=datalist;
                    notifyDataSetChanged();

                }



            }

            @Override
            public void onFailure(Call<TaskResponce> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
            }
        });

    }

}
