package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 21-10-17.
 */
public class ServiceProviderCategoriesAdapter extends RecyclerView.Adapter<ServiceProviderCategoriesAdapter.ServiceProviderCategoryViewHolder> {

    private List<ServiceProviderCategory> items;

    private int mSelectedCategoryIndex = -1;

    public ServiceProviderCategoriesAdapter(List<ServiceProviderCategory> items) {
        this.items = items;
    }

    @Override
    public ServiceProviderCategoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_service_provider_item_title_only, parent, false);
        return new ServiceProviderCategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ServiceProviderCategoryViewHolder holder, int position) {
        ServiceProviderCategory item = items.get(position);
        holder.mLblServiceProviderTitle.setText(item.displayName);
        holder.mImgBtnSelected.setVisibility(item.isLocallySelected ? View.VISIBLE : View.INVISIBLE);
        holder.mLblServiceProviderTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, item.isSelected ? R.drawable.ic_check_accent : 0, 0);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    @Nullable
    public ServiceProviderCategory getSelectedCategory() {
        if (items == null) return null;
        if (mSelectedCategoryIndex < 0) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).isLocallySelected) {
                    mSelectedCategoryIndex = i;
                    break;
                }
            }
        }
        return mSelectedCategoryIndex == -1 ? null : items.get(mSelectedCategoryIndex);
    }

    public void setData(ArrayList<ServiceProviderCategory> serviceProviderCategories) {
        items = serviceProviderCategories;
        notifyDataSetChanged();
    }

    class ServiceProviderCategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblServiceProviderTitle)
        AppCompatTextView mLblServiceProviderTitle;
        @BindView(R.id.imgBtnSelected)
        ImageButton mImgBtnSelected;

        ServiceProviderCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onViewClicked() {
            int index = getAdapterPosition();
            if (index < 0 || index >= items.size()) return;
            items.get(index).isLocallySelected = !items.get(index).isLocallySelected;
            notifyItemChanged(index);
            if (mSelectedCategoryIndex >= 0 && index != mSelectedCategoryIndex) {
                items.get(mSelectedCategoryIndex).isLocallySelected = false;
                notifyItemChanged(mSelectedCategoryIndex);
            }
            mSelectedCategoryIndex = index == mSelectedCategoryIndex ? -1 : index;
        }

    }

}