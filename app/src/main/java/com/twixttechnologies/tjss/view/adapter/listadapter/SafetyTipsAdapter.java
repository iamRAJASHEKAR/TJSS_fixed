package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyTip;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sony Raj on 23-09-17.
 */
public class SafetyTipsAdapter extends RecyclerView.Adapter<SafetyTipsAdapter.SafetyTipViewHolder> {

    private final Context context;

    private List<SafetyTip> items;

    public SafetyTipsAdapter(List<SafetyTip> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public SafetyTipViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_safety_tip_item, parent, false);
        return new SafetyTipViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SafetyTipViewHolder holder, int position) {
        SafetyTip item = items.get(position);
        holder.mLblSafetyTipNumber.setText(String.valueOf(position + 1));
        holder.mLblSafetyTipDescription.setText(item.description);
        holder.mLblSafetyTipTitle.setText(item.title);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class SafetyTipViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblSafetyTipNumber)
        AppCompatTextView mLblSafetyTipNumber;
        @BindView(R.id.lblSafetyTipTitle)
        AppCompatTextView mLblSafetyTipTitle;
        @BindView(R.id.lblSafetyTipDescription)
        AppCompatTextView mLblSafetyTipDescription;

        SafetyTipViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}