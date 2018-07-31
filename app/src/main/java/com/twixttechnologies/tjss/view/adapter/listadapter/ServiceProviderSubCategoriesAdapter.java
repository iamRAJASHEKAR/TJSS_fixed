package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.ServiceProviderSubCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 21-10-17.
 */
public class ServiceProviderSubCategoriesAdapter extends RecyclerView.Adapter<ServiceProviderSubCategoriesAdapter.ServiceProviderSubCategoryViewHolder> {

    private List<ServiceProviderSubCategory> items;

    private int mSelectedCategoryIndex = -1;

    public ServiceProviderSubCategoriesAdapter(List<ServiceProviderSubCategory> items) {
        this.items = items;
    }

    @Override
    public ServiceProviderSubCategoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_service_provider_item_title_only, parent, false);
        return new ServiceProviderSubCategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ServiceProviderSubCategoryViewHolder holder, int position) {
        ServiceProviderSubCategory item = items.get(position);
        holder.mLblServiceProviderTitle.setText(item.name);
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
    public ServiceProviderSubCategory getSelectedSubCategory() {
        if (items == null) return null;
        if (mSelectedCategoryIndex < 0) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).isLocallySelected) {
                    mSelectedCategoryIndex = i;
                    break;
                }
            }
        }
        return items.get(mSelectedCategoryIndex);
    }

    class ServiceProviderSubCategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblServiceProviderTitle)
        AppCompatTextView mLblServiceProviderTitle;
        @BindView(R.id.imgBtnSelected)
        ImageButton mImgBtnSelected;

        ServiceProviderSubCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onViewClicked() {
            int index = getAdapterPosition();
            if (index < 0 || index > items.size()) return;
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