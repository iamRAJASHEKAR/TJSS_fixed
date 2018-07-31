package com.twixttechnologies.tjss.view.fragment.user.settings.safetytip;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyTipCategory;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.adapter.listadapter.SafetyTipCategoriesAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.SafetyTipCategoriesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 16-09-17.
 */

public class FragmentSafetyTipSettings extends BaseFragment {

    public static final String TAG = "SafetyTipSettings";
    @BindView(R.id.lstSafetyTipsCategories)
    RecyclerView mLstSafetyTipsCategories;
    private final Observer<List<SafetyTipCategory>> mCategoriesObserver
            = new Observer<List<SafetyTipCategory>>() {
        @Override
        public void onChanged(@Nullable List<SafetyTipCategory> safetyTipCategories) {
            if (safetyTipCategories == null || safetyTipCategories.size() <= 0) {
                M.log("categories count is zero");
                return;
            }

            SafetyTipCategoriesAdapter adapter
                    = new SafetyTipCategoriesAdapter(mLstSafetyTipsCategories,
                    safetyTipCategories,
                    getActivity());
            mLstSafetyTipsCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstSafetyTipsCategories.setAdapter(adapter);
        }
    };
    Unbinder unbinder;
    @BindView(R.id.btnSafetyTipCategoriesUpdate)
    AppCompatButton mBtnSafetyTipCategoriesUpdate;
    private final Observer<StatusMessage> mUpdateStatusObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            dismissDialog();
            onDialogDismissed();
            if (statusMessage == null) {
                M.showToast(getActivity(), "An error occurred, Please try again later");
                return;
            }
            if (statusMessage.status.toLowerCase().contains("Success")) {
                M.showToast(getActivity(), "Updated Successfully");
            } else {
                M.showToast(getActivity(), statusMessage.status);
            }
        }
    };
    private SafetyTipCategoriesViewModel mViewModel;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety_tip_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new SafetyTipCategoriesViewModel
                        .SafetyTipCategoriesViewModelFactory(getActivity().getApplication()))
                .get(SafetyTipCategoriesViewModel.class);

        mViewModel.getSafetyCategoriesData().observe(this, mCategoriesObserver);
        initErrorObserver();
        mViewModel.getError().observe(this, mErrorObserver);
        mViewModel.getUpdateSelectedCategoryData().observe(this, mUpdateStatusObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //region Observers

    @OnClick(R.id.btnSafetyTipCategoriesUpdate)
    public void onViewClicked() {
        String categories = ((SafetyTipCategoriesAdapter) mLstSafetyTipsCategories.getAdapter())
                .getSelectedCategories();

        if (categories.equals("")) {
            M.showToast(getActivity(), "Please select at-least one category");
            return;
        }

        mBtnSafetyTipCategoriesUpdate.setEnabled(false);
        initProgress();

        mViewModel.updateCategories(categories);
    }

    @Override
    protected void onDialogDismissed() {
        super.onDialogDismissed();
        if (mBtnSafetyTipCategoriesUpdate != null)
            mBtnSafetyTipCategoriesUpdate.setEnabled(true);
    }


    //endregion

}
