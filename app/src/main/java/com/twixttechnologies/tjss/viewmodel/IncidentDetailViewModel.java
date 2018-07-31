package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.Incident;
import com.twixttechnologies.tjss.model.network.response.IncidentDetail;
import com.twixttechnologies.tjss.model.repository.IncidentsRepository;

import java.util.HashMap;

/**
 * @author Sony Raj on 31-10-17.
 */
public class IncidentDetailViewModel extends ViewModelBase {

    private final IncidentsRepository mIncidentsRepository;

    private final MutableLiveData<IncidentDetail> mIncidentDetailData
            = new MutableLiveData<>();

    private final RequestCallback<IncidentDetail> mDetailRequestCallback
            = new RequestCallback<IncidentDetail>() {
        @Override
        public void onSuccess(IncidentDetail result) {
            mIncidentDetailData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private IncidentDetailViewModel(Application application) {
        super(application);
        mIncidentsRepository = new IncidentsRepository();
    }

    public MutableLiveData<IncidentDetail> getIncidentDetailData() {
        return mIncidentDetailData;
    }

    public void getIncidentDetails(Incident incident) {
        String path = getApplication().getString(R.string.incident_detail_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("incidentId", incident.incidentId);
        mIncidentsRepository.getIncidentDetail(path, getHeaders(), params, mDetailRequestCallback);
    }

    @SuppressWarnings("unchecked")
    public static class IncidentDetailViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public IncidentDetailViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new IncidentDetailViewModel(mApplication);
        }
    }


}