package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.PlanDetails;
import com.innoviussoftwaresolution.tjss.model.repository.PlansRepository;

/**
 * @author Sony Raj on 01-11-17.
 */
public class PlanDetailsViewModel extends ViewModelBase {

    private final PlansRepository mRepository;

    private final MutableLiveData<PlanDetails> mPlansData
            = new MutableLiveData<>();

    private final RequestCallback<PlanDetails> mPlanDetailsRequestCallback
            = new RequestCallback<PlanDetails>() {
        @Override
        public void onSuccess(PlanDetails result) {
            mPlansData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private PlanDetailsViewModel(Application application) {
        super(application);
        mRepository = new PlansRepository();
    }

    public MutableLiveData<PlanDetails> getPlansData() {
        return mPlansData;
    }

    public void getPlanDetails(String planId) {
        String path = getApplication().getString(R.string.plan_details_path);
        mRepository.getPlanDetails(path, planId, mPlanDetailsRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class PlanDetailsViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public PlanDetailsViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new PlanDetailsViewModel(mApplication);
        }
    }


}