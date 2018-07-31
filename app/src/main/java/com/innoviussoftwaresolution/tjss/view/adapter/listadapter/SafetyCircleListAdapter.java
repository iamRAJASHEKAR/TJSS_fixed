package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircle;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.SafetyCircleDetailsActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.circle.FragmentSafetyCircleDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 22-08-17.
 */
public class SafetyCircleListAdapter extends RecyclerView.Adapter<SafetyCircleListAdapter.SafetyCircleViewHolder> {

    private final Context context;
    private List<SafetyCircle> items;

    public SafetyCircleListAdapter(List<SafetyCircle> items, Context context) {
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
            String[] adminIds = x.admins.split(",");
            boolean isAdmin = false;
            String currentUserId = PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, "");
            if (!currentUserId.equals("")) {
                for (int i = 0; i < adminIds.length; i++) {
                    if (adminIds[i].equals(currentUserId)) {
                        isAdmin = true;
                        break;
                    }
                }
            }


            Intent detailsIntent = new Intent(context, SafetyCircleDetailsActivity.class);
            detailsIntent.putExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_ID, x.circleId);
            detailsIntent.putExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_NAME, x.circleName);
            detailsIntent.putExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_IMAGE_LINK, x.image);
            detailsIntent.putExtra(FragmentSafetyCircleDetails.IS_ADMIN, isAdmin);
            context.startActivity(detailsIntent);
        }

    }

}