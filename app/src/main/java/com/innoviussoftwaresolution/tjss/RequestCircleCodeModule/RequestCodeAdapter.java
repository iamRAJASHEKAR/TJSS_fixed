package com.innoviussoftwaresolution.tjss.RequestCircleCodeModule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCodeAdapter extends RecyclerView.Adapter<RequestCodeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CircleDetailsModel> detailsList;
    private CircleDetailsModel detailsModel;

    public RequestCodeAdapter(Context context, ArrayList<CircleDetailsModel> detailsList) {
        this.context = context;
        this.detailsList = detailsList;
    }

    @Override
    public RequestCodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact_cricle_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestCodeAdapter.ViewHolder holder, final int position) {

        holder.tvUserName.setText(detailsList.get(position).getName());
        holder.tvCircleName.setText(detailsList.get(position).getCircleName());

        holder.btnRequestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                detailsModel = new CircleDetailsModel();
                detailsModel.setId(detailsList.get(position).getId());
                detailsModel.setEmail(detailsList.get(position).getEmail());
                detailsModel.setCircleName(detailsList.get(position).getCircleName());
                detailsModel.setName(detailsList.get(position).getName());
                SharedPreferences preferences = context.getSharedPreferences("userName", 0);
                detailsModel.setUsername(preferences.getString("name", ""));
                sendRequest(detailsModel);


            }
        });

    }

    @Override
    public int getItemCount() {
        if (detailsList != null || detailsList.size() > 0) {
            return detailsList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvUserName, tvCircleName;
        private AppCompatButton btnRequestCode;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCircleName = (AppCompatTextView) itemView.findViewById(R.id.tvCircleName);
            tvUserName = (AppCompatTextView) itemView.findViewById(R.id.tvCircleMemberName);
            btnRequestCode = (AppCompatButton) itemView.findViewById(R.id.btnRequestCode);
        }
    }

    private void sendRequest(CircleDetailsModel model) {
        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        Call<SendRequestResponseModel> call = services.sendRequest(model);
        call.enqueue(new Callback<SendRequestResponseModel>() {
            @Override
            public void onResponse(Call<SendRequestResponseModel> call, Response<SendRequestResponseModel> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getMessage().equalsIgnoreCase("success")) {
                            showAlertBox("Success","Request has been sent to the Adimn. Please wait until Admin responds, then Sign up again with Circle join code.");
                        } else {
                            showAlertBox("Error","Failed to send request to admin");
                        }
                    }
                } else {
                    showAlertBox("Error","Failed to send request to Admin.");
                }

            }

            @Override
            public void onFailure(Call<SendRequestResponseModel> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
                showAlertBox("Error","Failed to send request to Admin.");
            }
        });
    }

    private void showAlertBox(String title,String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity)context).finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }
}
