package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;
import com.twixttechnologies.tjss.view.activity.SafetyCircleAlertOptionActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 22-08-17.
 */
public class SafetyCircleListAdapterForSettings extends RecyclerView.Adapter<SafetyCircleListAdapterForSettings.SafetyCircleViewHolder> {

    private final Context context;
    private List<SafetyCircle> items;

    public SafetyCircleListAdapterForSettings(List<SafetyCircle> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public SafetyCircleViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_safety_circle_list_item, parent, false);
        return new SafetyCircleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SafetyCircleViewHolder holder, int position) {
        SafetyCircle item = items.get(position);
        if (item == null) return;
        holder.mLblSafetyCircleName.setText(item.circleName);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class SafetyCircleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblSafetyCircleName)
        AppCompatTextView mLblSafetyCircleName;

        SafetyCircleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onClick(View view) {
            SafetyCircle x = items.get(getAdapterPosition());
            Intent intent = new Intent(context, SafetyCircleAlertOptionActivity.class);
            intent.putExtra(SafetyCircleAlertOptionActivity.SAFETY_CIRCLE, x);
            context.startActivity(intent);
        }

    }

}