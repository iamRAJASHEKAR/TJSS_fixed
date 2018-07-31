package com.innoviussoftwaresolution.tjss.view.fragment.invoice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innoviussoftwaresolution.tjss.R;

import java.util.List;

public class InvoiceAdaptor extends RecyclerView.Adapter<InvoiceAdaptor.ViewHolder> {

    private Context context;

    private List<TaskResponce.getdata> responseList;

    TaskResponce.getdata getdata;

    public InvoiceAdaptor(Context context, List<TaskResponce.getdata> responseList) {

        this.context = context;
        this.responseList = responseList;
    }

    @Override
    public InvoiceAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_invoice, parent, false);
        return new InvoiceAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InvoiceAdaptor.ViewHolder holder, final int position) {


        getdata = responseList.get(position);


        holder.tvTiltle.setText("Request Provider:  " + getdata.getProvideName());
        holder.tvDescription.setText("Description:  "+getdata.getDesc());
        holder.tvAmount.setText("₦ "+getdata.getAmount());



    }

    @Override
    public int getItemCount() {
        return responseList.size();
        //return responseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTiltle, tvDescription,tvAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTiltle = (TextView) itemView.findViewById(R.id.tv_c_i_Title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_c_i_Description);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_c_i_Amount);

        }

    }


}
