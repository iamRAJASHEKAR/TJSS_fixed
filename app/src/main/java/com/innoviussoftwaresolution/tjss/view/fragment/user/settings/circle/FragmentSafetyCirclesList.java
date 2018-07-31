package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.circle;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircle;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.SafetyCircleListAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.SafetyCircleViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentSafetyCirclesList extends BaseFragment {

    @BindView(R.id.lstSafetyCircles)
    RecyclerView mLstSafetyCircles;
    Unbinder unbinder;
    private SafetyCircleViewModel mViewModel;
    private Observer<List<SafetyCircle>> mSafetyCircleListObserver
            = new Observer<List<SafetyCircle>>() {
        @Override
        public void onChanged(@Nullable List<SafetyCircle> safetyCircleList) {
            if (safetyCircleList == null || safetyCircleList.size() <= 0) return;

            SafetyCircleListAdapter adapter = new SafetyCircleListAdapter(safetyCircleList, getActivity());
            mLstSafetyCircles.setAdapter(adapter);
            mLstSafetyCircles.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstSafetyCircles.addItemDecoration(new DividerItemDecoration(getActivity(),
                    LinearLayoutManager.VERTICAL));

        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }


    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getSafetyCircleList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety_circle_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    //region Observers

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new SafetyCircleViewModel
                .SafetyCircleViewModelFactory(getActivity().getApplication()))
                .get(SafetyCircleViewModel.class);

        mViewModel.getSafetyCirclesListData().observe(this, mSafetyCircleListObserver);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //endregion Observers

}
