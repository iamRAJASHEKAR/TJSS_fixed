package com.twixttechnologies.tjss.view.fragment.safetytip;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyTip;
import com.twixttechnologies.tjss.model.network.response.SafetyTipCategory;
import com.twixttechnologies.tjss.view.adapter.listadapter.SafetyTipsAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.viewutils.TabSelectedAdapter;
import com.twixttechnologies.tjss.viewmodel.SafetyTipViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 16-09-17.
 */

public class FragmentSafetyTipsListing extends BaseFragment {

    public static final String TAG = "fragmentSafetyTipListing";
    @BindView(R.id.tabSafetyTips)
    TabLayout mTabSafetyTips;
    private final Observer<List<SafetyTipCategory>> mCategoryListObserver
            = new Observer<List<SafetyTipCategory>>() {
        @Override
        public void onChanged(@Nullable List<SafetyTipCategory> safetyTipCategories) {
            if (safetyTipCategories == null || safetyTipCategories.size() <= 0) return;
            for (SafetyTipCategory category : safetyTipCategories) {
                if (category.isChecked)
                    mTabSafetyTips.addTab(mTabSafetyTips.newTab().setText(category.name));
            }
        }
    };
    Unbinder unbinder;
    @BindView(R.id.lstSafetyTips)
    RecyclerView mLstSafetyTips;
    private final Observer<List<SafetyTip>> mSafetyTipsObserver
            = new Observer<List<SafetyTip>>() {
        @Override
        public void onChanged(@Nullable List<SafetyTip> safetyTips) {
            if (safetyTips == null || safetyTips.size() <= 0) return;
            mLstSafetyTips.setAdapter(new SafetyTipsAdapter(safetyTips, getActivity()));
            mLstSafetyTips.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    };
    private SafetyTipViewModel mViewModel;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety_tip_listing, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTabSafetyTips.addOnTabSelectedListener(new TabSelectedAdapter() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int index = tab.getPosition();
                if (index < 0) return;
                List<SafetyTipCategory> categories = mViewModel.getSelectedCategoriesData().getValue();
                if (categories == null || categories.size() <= 0) return;
                final SafetyTipCategory category = categories.get(index);
                mViewModel.getSafetyTips(category.id);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new SafetyTipViewModel.SafetyTipViewModelFactory(getActivity().getApplication()))
                .get(SafetyTipViewModel.class);
        mViewModel.getSelectedCategoriesData().observe(this, mCategoryListObserver);
        mViewModel.getSafetyTipSData().observe(this, mSafetyTipsObserver);
        mViewModel.getSelectedCategoriesData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Safety Tips");
    }
}
