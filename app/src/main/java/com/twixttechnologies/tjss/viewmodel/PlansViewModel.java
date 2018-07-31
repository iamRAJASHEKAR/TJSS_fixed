package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.Plan;
import com.twixttechnologies.tjss.model.repository.PlansRepository;
import com.twixttechnologies.tjss.utils.M;

import java.util.List;

/**
 * @author Sony Raj on 05-08-17.
 */

public class PlansViewModel extends PaymentsViewModelBase implements RequestCallback<List<Plan>> {

    private final MutableLiveData<List<Plan>> mPlansLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> mError = new MutableLiveData<>();

    private final MutableLiveData<Plan> mSelectedPlanData = new MutableLiveData<>();

    public PlansViewModel(Application application) {
        super(application);
        PlansRepository repository = new PlansRepository();
        repository.get(getApplication().getString(R.string.plans_list), this);
    }

    public MutableLiveData<String> getError() {
        return mError;
    }

    public void setSelectedPlan(Plan selectedPlan) {
        mSelectedPlanData.setValue(selectedPlan);
    }

    public MutableLiveData<Plan> getSelectedPlanData() {
        return mSelectedPlanData;
    }

    public MutableLiveData<List<Plan>> getPlansLiveData() {
        return mPlansLiveData;
    }

    @Override
    public void onSuccess(List<Plan> result) {
        mPlansLiveData.setValue(result);
    }

    @Override
    public void onFailure(String reason) {
        mError.setValue(reason);
        M.log(reason);
    }


    @SuppressWarnings("unchecked")
    public static class PlansViewmodelFacory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public PlansViewmodelFacory(Application mApplication) {
            this.mApplication = mApplication;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new PlansViewModel(mApplication);
        }
    }

}
