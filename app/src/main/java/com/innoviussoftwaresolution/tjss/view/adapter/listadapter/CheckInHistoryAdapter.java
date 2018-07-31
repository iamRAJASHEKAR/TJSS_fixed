package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.CheckInHistory;
import com.innoviussoftwaresolution.tjss.model.network.response.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sony Raj on 01-11-17.
 */
public class CheckInHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 263;
    private static final int NO_CONTENT = 615;
    public static String placeId = "";
    private List<CheckInHistory> items;
    private boolean gotNoContent = false;

    public CheckInHistoryAdapter(List<CheckInHistory> items) {
        this.items = items;
    }

    public void setGotNoContent() {
        this.gotNoContent = true;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == ITEM ?
                        R.layout.extra_check_in_history_item : R.layout.extra_error, parent, false);
        return viewType == ITEM ? new CheckInViewHolder(v) : new NoContentViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CheckInViewHolder) {
            CheckInHistory item = items.get(position);
            ((CheckInViewHolder) holder).mLblCheckInLocationName.setText(item.locName);
            ((CheckInViewHolder) holder).mLblCheckInLocationTime.setText(item.createdAt);


            placeId = item.id;

        } else if (holder instanceof NoContentViewHolder) {
            ((NoContentViewHolder) holder).mLblError.setText("No History available for now");
        }
    }

    @Override
    public int getItemCount() {
        if (items == null || items.size() <= 0) {
            return 1;
        }
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return gotNoContent ? NO_CONTENT : ITEM;
    }


    public class CheckInViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout viewBackground, viewForeground;
        @BindView(R.id.lblCheckInLocationName)
        AppCompatTextView mLblCheckInLocationName;
        @BindView(R.id.lblCheckInLocationTime)
        AppCompatTextView mLblCheckInLocationTime;

        CheckInViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class NoContentViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.lblError)
        AppCompatTextView mLblError;

        NoContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void removeItem(int position) {
        items.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}