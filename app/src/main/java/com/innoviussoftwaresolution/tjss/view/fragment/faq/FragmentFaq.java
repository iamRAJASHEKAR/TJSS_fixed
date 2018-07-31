package com.innoviussoftwaresolution.tjss.view.fragment.faq;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.internal.FaqQuestion;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.FaqAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.FaqViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 13-09-17.
 */

public class FragmentFaq extends BaseFragment {

    public static final String TAG = "FragmentFaq";
    @BindView(R.id.lstFaq)
    RecyclerView mLstFaq;
    Unbinder unbinder;

    private FaqAdapter mAdapter;
    private FaqViewModel mViewModel;
    private Observer<List<FaqQuestion>> mFaqsObserver
            = new Observer<List<FaqQuestion>>() {
        @Override
        public void onChanged(@Nullable List<FaqQuestion> faqQuestions) {
            if (faqQuestions == null || faqQuestions.size() <= 0) return;
            bindFaqs(faqQuestions);
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        unbinder = ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this, new FaqViewModel.FaqViewModelFactory(getActivity().getApplication()))
                .get(FaqViewModel.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Faq");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getFaqs().observe(this, mFaqsObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewModel.getFaqs().getValue() == null) return;
        if (mAdapter == null) return;
        if (mAdapter.getItemCount() <= 0) {
            List<FaqQuestion> faqs = mViewModel.getFaqs().getValue();
            bindFaqs(faqs);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void bindFaqs(@Nullable List<FaqQuestion> faqQuestions) {
        M.log("Binding faqs");
        mAdapter = new FaqAdapter(faqQuestions);
        if (mLstFaq != null) {
            mLstFaq.setAdapter(mAdapter);
            mLstFaq.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }


}
