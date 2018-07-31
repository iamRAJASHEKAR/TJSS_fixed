package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.HelpAlert;
import com.innoviussoftwaresolution.tjss.view.activity.HelpAlertDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 02-11-17.
 */
public class HelpAlertsListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 263;
    private static final int NO_CONTENT = 615;
    private final Context context;
    private List<HelpAlert> items;
    private boolean gotNoContent = false;

    public HelpAlertsListingAdapter(List<HelpAlert> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setGotNoContent() {
        this.gotNoContent = true;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == ITEM ? R.layout.extra_help_alert_history_item : R.layout.extra_error,
                        parent, false);
        return viewType == ITEM ? new HelpAlertViewHolder(v) : new NoContentViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HelpAlertViewHolder) {
            HelpAlert item = items.get(position);
            ((HelpAlertViewHolder) holder).mLblHelpAlertLocationName.setText(item.displayName == null ? "Not Assigned" : item.displayName);
            ((HelpAlertViewHolder) holder).mLblHelpAlertInLocationTime.setText(item.providerName);
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

    @OnClick(R.id.appCompatImageView)
    public void onViewClicked() {
    }

    class HelpAlertViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblHelpAlertLocationName)
        AppCompatTextView mLblHelpAlertLocationName;
        @BindView(R.id.lblHelpAlertInLocationTime)
        AppCompatTextView mLblHelpAlertInLocationTime;

        HelpAlertViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onClick() {
            int index = getAdapterPosition();
            if (index < 0) return;
            HelpAlert helpAlert = items.get(index);
            if (helpAlert == null) return;
            Intent detail = new Intent(context, HelpAlertDetailsActivity.class);
            detail.putExtra("helpAlert", helpAlert);
            context.startActivity(detail);
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

}