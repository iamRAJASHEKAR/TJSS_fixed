package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.RequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.SubmitResponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderTaskListAdapter extends RecyclerView.Adapter<ServiceProviderTaskListAdapter.ViewHolder> {

    private Context context;
    private List<RequestModel> responseList;
    private RequestModel model;

    public ServiceProviderTaskListAdapter(Context context, List<RequestModel> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    @Override
    public ServiceProviderTaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_provider_task, parent, false);
        return new ServiceProviderTaskListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ServiceProviderTaskListAdapter.ViewHolder holder, final int position) {


         model = responseList.get(position);

    /*    Log.e("aj", model.getCreated_at()+
        "----"+model.getId()+"----"+
        model.getProviderId()+"----"+
        model.getRequestStatus()+"----"+
        model.getUpdated_at()+"----"+
        model.getUserId()+"-----"+
        model.getFname()+"----"+
        model.getLname()+"----"+
        model.getEmail()+"----"+
        model.getUserPhone()+"----"+
        model.getUserLat()+"----"+
        model.getUserLong());
*/
        holder.tvTitle.setText("Task " + String.valueOf(position + 1));



        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_task_details, null, false);

                dialog.setCancelable(false);
                TextView name = view.findViewById(R.id.tv_username_task);
                TextView phone = view.findViewById(R.id.tv_userphone_task);
                TextView email = view.findViewById(R.id.tv_useremail_task);

                    name.setText( model.getFname()+" "+model.getLname());


                phone.setText(model.getUserPhone());
                email.setText(model.getEmail());

                 final EditText desc = view.findViewById(R.id.et_desc_task);
                 final EditText amount = view.findViewById(R.id.et_price_task);

                Button cancle = view.findViewById(R.id.btn_cancel_task);
                Button submit = view.findViewById(R.id.btn_submit_task);

                final String de = String.valueOf(desc.getText());
                final String am = String.valueOf(amount.getText());


                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                                        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);

                                        Call<SubmitResponce> call = services.settaskdescandamount(model.getProviderId(),model.getUserId(),desc.getText().toString(),amount.getText().toString());


                                        call.enqueue(new Callback<SubmitResponce>() {
                                            @Override
                                            public void onResponse(Call<SubmitResponce> call, Response<SubmitResponce> response) {


                                                dialog.dismiss();
                                                Log.e("aa","added Task");

                                            }

                                            @Override
                                            public void onFailure(Call<SubmitResponce> call, Throwable t) {

                                                dialog.dismiss();
                                            }
                                        });

                            }



                });
                ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.setContentView(view);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, 500);
                window.setBackgroundDrawableResource(R.color.colorTransparent);
                window.setGravity(Gravity.BOTTOM);
                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private AppCompatImageButton btnTaskDone;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTaskTitle);

        }
    }
}
