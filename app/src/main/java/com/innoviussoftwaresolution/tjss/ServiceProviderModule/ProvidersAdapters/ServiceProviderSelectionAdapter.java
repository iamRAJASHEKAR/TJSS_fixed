package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities.NormalServiceProviderSubCatSelection;
import com.innoviussoftwaresolution.tjss.model.network.response.Log;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderCategory;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.ServiceProviderCategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceProviderSelectionAdapter extends RecyclerView.Adapter<ServiceProviderSelectionAdapter.ViewHolder> {


    private List<ServiceProviderCategory> items;

    private int mSelectedCategoryIndex = -1;

    public ServiceProviderSelectionAdapter(List<ServiceProviderCategory> items) {
        this.items = items;
    }

    @Override
    public ServiceProviderSelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_service_provider_item_title_only, parent, false);
        return new ServiceProviderSelectionAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ServiceProviderSelectionAdapter.ViewHolder holder, int position) {
        ServiceProviderCategory item = items.get(position);
        holder.mLblServiceProviderTitle.setText(item.displayName);
        holder.mImgBtnSelected.setVisibility(View.INVISIBLE);
        //  holder.mLblServiceProviderTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, item.isSelected ? R.drawable.ic_check_accent : 0, 0);

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblServiceProviderTitle)
        AppCompatTextView mLblServiceProviderTitle;
        @BindView(R.id.imgBtnSelected)
        ImageButton mImgBtnSelected;

        //
        public ViewHolder(View itemView) {
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
//            Toast.makeText(itemView.getContext(), items.get(index).id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(itemView.getContext(), NormalServiceProviderSubCatSelection.class);
            intent.putExtra("id", items.get(index).id);
            itemView.getContext().startActivity(intent);

        }
    }
}
