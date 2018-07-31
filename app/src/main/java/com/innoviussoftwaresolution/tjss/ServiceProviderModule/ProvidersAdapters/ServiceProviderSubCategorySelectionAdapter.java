package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities.NormalServiceProviderSubCatSelection;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities.Test_help_alert_activity;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderSubCategory;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.ServiceProviderSubCategoriesAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.helpalert.alert.InitHelpAlertDialog;
import com.innoviussoftwaresolution.tjss.viewmodel.HelpAlertViewModel;
import com.innoviussoftwaresolution.tjss.viewmodel.ServiceProvidersSubCategoriesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ServiceProviderSubCategorySelectionAdapter extends RecyclerView.Adapter<ServiceProviderSubCategorySelectionAdapter.ViewHolder> implements
        InitHelpAlertDialog.OnStatusListener{

    private List<ServiceProviderSubCategory> items;
    private FragmentManager supportFragmentManager;
    private int mSelectedCategoryIndex = -1;

    public ServiceProviderSubCategorySelectionAdapter(HelpAlertViewModel mViewModel, FragmentManager supportFragmentManager, List<ServiceProviderSubCategory> items) {
        this.items = items;
        this.supportFragmentManager=supportFragmentManager;
        this.mViewModel = mViewModel;
    }

    @Override
    public ServiceProviderSubCategorySelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_service_provider_item_title_only, parent, false);
        return new ServiceProviderSubCategorySelectionAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ServiceProviderSubCategorySelectionAdapter.ViewHolder holder, int position) {

        ServiceProviderSubCategory item = items.get(position);
        holder.mLblServiceProviderTitle.setText(item.name);
        holder.mImgBtnSelected.setVisibility(View.INVISIBLE);
        //holder.mLblServiceProviderTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, item.isSelected ? R.drawable.ic_check_accent : 0, 0);
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


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    @Override
    public void onStatus(boolean success) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements InitHelpAlertDialog.OnStatusListener {

        @BindView(R.id.lblServiceProviderTitle)
        AppCompatTextView mLblServiceProviderTitle;
        @BindView(R.id.imgBtnSelected)
        ImageButton mImgBtnSelected;

        public ViewHolder(View itemView) {
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
//            Toast.makeText(itemView.getContext(), items.get(index).id, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(itemView.getContext(), Test_help_alert_activity.class);
//            intent.putExtra("status","");
            SharedPreferences preferences = itemView.getContext().getSharedPreferences("selectedSubCat", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("subCatId", items.get(index).id);
            editor.commit();
            InitHelpAlertDialog dialog = new InitHelpAlertDialog();
            dialog.setListener(this);
            dialog.setCancelable(true);
             dialog.show(supportFragmentManager, "HelpAlert");
            //itemView.getContext().startActivity(intent);


        }
        @Override
        public void onStatus(boolean success) {
            if (!success) {
                M.showToast(itemView.getContext(), "Invalid data");
            } else {
//            CountDownAlert dialog = new CountDownAlert();
//            dialog.setCancelable(false);
//            dialog.setListener(this);
//            dialog.show(getChildFragmentManager(), "Counter");
                mViewModel.sendHelpAlert(itemView.getContext());
            }
        }
    }
    Unbinder unbinder;
    private HelpAlertViewModel mViewModel;
}
