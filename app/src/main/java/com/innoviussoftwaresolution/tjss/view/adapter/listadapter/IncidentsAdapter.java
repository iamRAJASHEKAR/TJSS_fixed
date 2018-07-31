package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.Incident;
import com.innoviussoftwaresolution.tjss.view.activity.IncidentDetailActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.incident.FragmentIncidentDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 19-10-17.
 */
public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsAdapter.IncidentViewHolder> {

    private List<Incident> items;


    public IncidentsAdapter(List<Incident> items) {
        this.items = items;
    }

    @Override
    public IncidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_incident_item, parent, false);
        return new IncidentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IncidentViewHolder holder, int position) {
        Incident item = items.get(position);
        holder.mLblIncidentTitle.setText(item.incidentTitle);
        holder.mLblIncidentTime.setText(item.incidentTime);
        holder.mLblIncidentType.setText(item.incidentType);
        holder.mLblIncidentDescription.setText(item.incidentDescription);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class IncidentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblIncidentTitle)
        AppCompatTextView mLblIncidentTitle;
        @BindView(R.id.lblIncidentTime)
        AppCompatTextView mLblIncidentTime;
        @BindView(R.id.lblIncidentType)
        AppCompatTextView mLblIncidentType;
        @BindView(R.id.lblIncidentDescription)
        AppCompatTextView mLblIncidentDescription;

        IncidentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.lblIncidentTitle)
        public void onClick(View view) {
            boolean isVisible = mLblIncidentDescription.getVisibility() == View.VISIBLE;
            mLblIncidentDescription.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            mLblIncidentTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, isVisible ? R.drawable.ic_arrow_up_thin : R.drawable.ic_arrow_down_thin, 0);
        }


        @OnClick
        public void onItemClick(View view) {
            int index = getAdapterPosition();
            if (index < 0) return;
            Incident incident = items.get(index);
            if (incident == null) return;
            Intent details = new Intent(view.getContext(), IncidentDetailActivity.class);
            details.putExtra(FragmentIncidentDetails.INCIDENT_DETAIL, incident);
            view.getContext().startActivity(details);

        }

    }

}