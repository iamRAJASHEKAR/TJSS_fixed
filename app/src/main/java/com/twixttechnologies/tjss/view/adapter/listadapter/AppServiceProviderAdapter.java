package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.DialogInterface;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.AppServiceProvider;
import com.twixttechnologies.tjss.utils.M;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * @author Sony Raj on 25-09-17.
 */
public class AppServiceProviderAdapter extends RecyclerView.Adapter<AppServiceProviderAdapter.AppServiceProviderViewHolder> {
    private List<AppServiceProvider> items;

    private int mLastSelectedIndex = -1;

    private onDeleteListener mOnDeleteListener;

    public AppServiceProviderAdapter(List<AppServiceProvider> items) {
        this.items = items;
    }

    public void setOnDeleteListener(onDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }

    @Override
    public AppServiceProviderViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_service_provider_item, parent, false);
        return new AppServiceProviderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AppServiceProviderViewHolder holder, int position) {
        AppServiceProvider item = items.get(position);
        holder.mLblServiceProviderTitle.setText(item.provideName);
        holder.mLblServiceProviderDescription.setText(item.description);
        holder.mImgBtnSelected.setVisibility(item.isSelected ? View.VISIBLE : View.INVISIBLE);
        holder.mLblServiceProviderCategoryAndSubCategory.setText(MessageFormat.format("({0}-{1})", item.category, item.subCategory));
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    public String getSelectedIds() {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (AppServiceProvider ser : items) {
            if (ser.isSelected) {
                if (first) {
                    first = false;
                } else {
                    sb.append(",");
                }
                sb.append(ser.id);
            }
        }
        return sb.toString();
    }

    public void clear() {
        items = new ArrayList<>();
    }

    public void remove(int index) {
        items.remove(index);
        notifyItemChanged(index);
    }

    class AppServiceProviderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblServiceProviderTitle)
        AppCompatTextView mLblServiceProviderTitle;
        @BindView(R.id.lblServiceProviderDescription)
        AppCompatTextView mLblServiceProviderDescription;
        @BindView(R.id.imgBtnSelected)
        ImageButton mImgBtnSelected;
        @BindView(R.id.lblServiceProviderCategoryAndSubCategory)
        AppCompatTextView mLblServiceProviderCategoryAndSubCategory;

        AppServiceProviderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick
        public void onClick() {
            int index = getAdapterPosition();
            if (index < 0 || index >= items.size()) return;
            AppServiceProvider item = items.get(index);
            if (item == null) return;

            if (mLastSelectedIndex >= 0) {
                items.get(mLastSelectedIndex).isSelected = false;
                try {
                    notifyItemChanged(mLastSelectedIndex);
                } catch (Exception e) {
                    M.log(e.getMessage());
                }
            }
            mLastSelectedIndex = index;
            item.isSelected = !item.isSelected;
            notifyItemChanged(index);
        }

        @OnLongClick
        public boolean onLongClick(View view) {
            M.showAlert(view.getContext(), "Delete Provider", "Are you sure you want to delete selected provider?",
                    "DELETE", "DON'T DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int index = getAdapterPosition();
                            if (index < 0 || index >= items.size()) return;
                            AppServiceProvider item = items.get(index);
                            if (item == null) return;

                            if (mOnDeleteListener != null)
                                mOnDeleteListener.onDelete(item, index);

                        }
                    }, null, false);
            return true;
        }
    }

    public interface onDeleteListener {
        void onDelete(AppServiceProvider provider, int index);
    }


}