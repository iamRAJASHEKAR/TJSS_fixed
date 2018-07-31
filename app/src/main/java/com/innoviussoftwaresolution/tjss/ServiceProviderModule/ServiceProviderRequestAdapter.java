package com.innoviussoftwaresolution.tjss.ServiceProviderModule;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ServiceProviderRequestAdapter extends RecyclerView.Adapter<ServiceProviderRequestAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServiceProviderRequestModel> list = new ArrayList<>();
    private ServiceProviderRequestModel requestModel;

    public ServiceProviderRequestAdapter(Context context, ArrayList<ServiceProviderRequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ServiceProviderRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_provider_requests, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceProviderRequestAdapter.ViewHolder holder, int position) {

        requestModel = list.get(position);
        holder.tvRequestTiltle.setText(requestModel.getRequestTitle());
        holder.tvRequestDescription.setText(requestModel.getRequestDescription());
        holder.btnRequestReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Request Rejected",Toast.LENGTH_LONG).show();

            }
        });

        holder.btnRequestAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Request Accepted",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRequestTiltle, tvRequestDescription;
        AppCompatButton btnRequestAccept, btnRequestReject;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRequestTiltle = (TextView) itemView.findViewById(R.id.tvSrequestTitle);
            tvRequestDescription = (TextView) itemView.findViewById(R.id.tvSPrequestDescription);
            btnRequestAccept = (AppCompatButton) itemView.findViewById(R.id.btnSPrequestAccept);
            btnRequestReject = (AppCompatButton) itemView.findViewById(R.id.btnSPrequestReject);
        }

    }
}
