package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyTipCategory;
import com.innoviussoftwaresolution.tjss.utils.M;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sony Raj on 16-09-17.
 */
public class SafetyTipCategoriesAdapter extends RecyclerView.Adapter<SafetyTipCategoriesAdapter.SafetyTipCategoriesViewHolder> {

    private final Context context;
    private final RecyclerView mList;
    private final CompoundButton.OnCheckedChangeListener mCheckedChangeListener;
    private List<SafetyTipCategory> items;

    public SafetyTipCategoriesAdapter(RecyclerView recyclerView, final List<SafetyTipCategory> items, Context context) {
        this.items = items;
        this.context = context;
        mList = recyclerView;
        mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = mList.getChildAdapterPosition((View) buttonView.getParent());
                if (position < 0) {
                    M.log("Unable to get checked position of checked item");
                    return;
                }
                items.get(position).isChecked = isChecked;
            }
        };
    }

    @Override
    public SafetyTipCategoriesViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_safety_tip_category, parent, false);
        return new SafetyTipCategoriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SafetyTipCategoriesViewHolder holder, int position) {
        SafetyTipCategory item = items.get(position);
        holder.mChkSafetyTipCategory.setChecked(item.isChecked);
        holder.mChkSafetyTipCategory.setText(item.name);
        holder.mChkSafetyTipCategory.setOnCheckedChangeListener(mCheckedChangeListener);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public String getSelectedCategories() {
        StringBuilder stringBuilder = new StringBuilder("");
        boolean isFirst = true;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isChecked) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append(",");
                }
                stringBuilder.append(items.get(i).id);

            }
        }
        return stringBuilder.toString();
    }


    class SafetyTipCategoriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.chkSafetyTipCategory)
        AppCompatCheckBox mChkSafetyTipCategory;

        SafetyTipCategoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}