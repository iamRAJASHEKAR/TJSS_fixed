package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sony Raj on 31-10-17.
 */
public class IncidentLogAdapter extends RecyclerView.Adapter<IncidentLogAdapter.LogViewHolder> {

    private final Context context;
    private List<Log> items;

    public IncidentLogAdapter(List<Log> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_log, parent, false);
        return new LogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        Log item = items.get(position);
        if (item == null) return;
        holder.mLblLogTime.setText(item.time);
        holder.mLblLogDescription.setText(item.action);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class LogViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblLogTime)
        AppCompatTextView mLblLogTime;
        @BindView(R.id.lblLogDescription)
        AppCompatTextView mLblLogDescription;

        LogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}