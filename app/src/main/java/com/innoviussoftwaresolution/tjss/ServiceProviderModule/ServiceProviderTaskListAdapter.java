package com.innoviussoftwaresolution.tjss.ServiceProviderModule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;

import java.util.ArrayList;

public class ServiceProviderTaskListAdapter extends RecyclerView.Adapter<ServiceProviderTaskListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServiceProviderTaskModel> list =  new ArrayList<>();
    private ServiceProviderTaskModel taskModel;

    public ServiceProviderTaskListAdapter(Context context, ArrayList<ServiceProviderTaskModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ServiceProviderTaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_provider_task, parent, false);
        return new ServiceProviderTaskListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ServiceProviderTaskListAdapter.ViewHolder holder, int position) {

        taskModel= list.get(position);
        holder.tvTitle.setText(taskModel.getTask());
        /*holder.btnTaskDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Task");
                builder.setMessage("Do you want to mark this task as DONE?");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       M.showToast(context,"Done");
                      // holder.btnTaskDone.setBackground(context.getResources().getDrawable(R.drawable.check_image));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTitle;/*
        private AppCompatImageButton btnTaskDone;*/
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle =(TextView) itemView.findViewById(R.id.tvTaskTitle);
           // btnTaskDone=(AppCompatImageButton)itemView.findViewById(R.id.btnTaskCheck);
        }
    }
}
