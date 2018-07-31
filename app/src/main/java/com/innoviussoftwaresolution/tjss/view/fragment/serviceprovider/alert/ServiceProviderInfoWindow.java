package com.innoviussoftwaresolution.tjss.view.fragment.serviceprovider.alert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.AppServiceProvider;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20/11/17.
 */

public class ServiceProviderInfoWindow extends AppCompatDialogFragment {

    @BindView(R.id.lblServiceProviderTitle)
    AppCompatTextView mLblServiceProviderTitle;

    @BindView(R.id.lblServiceProviderCategoryAndSubCategory)
    AppCompatTextView mLblServiceProviderCategoryAndSubCategory;

    @BindView(R.id.separator)
    View mSeparator;

    @BindView(R.id.lblServiceProviderDescription)
    AppCompatTextView mLblServiceProviderDescription;
    Unbinder unbinder;

    private AppServiceProvider mProvider;

    private ProviderActionListener mListener;

    public void setListener(ProviderActionListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProvider = getArguments().getParcelable("provider");
    }

    public static ServiceProviderInfoWindow newInstance(AppServiceProvider provider) {
        Bundle args = new Bundle();
        args.putParcelable("provider", provider);
        ServiceProviderInfoWindow fragment = new ServiceProviderInfoWindow();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLblServiceProviderTitle.setText(mProvider.provideName);
        mLblServiceProviderCategoryAndSubCategory.setText(MessageFormat.format("({0}-{1})", mProvider.category, mProvider.subCategory));
        mLblServiceProviderDescription.setText(mProvider.description);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_provider_info_window, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnShare)
    public void onShareClicked() {
        mListener.onShare(mProvider);
        dismiss();
    }


    @OnClick(R.id.btnCall)
    public void onCallClicked(){
        mListener.onCall(mProvider);
        dismiss();
    }


    public interface ProviderActionListener {
        void onShare(AppServiceProvider provider);

        void onCall(AppServiceProvider provider);
    }


}
