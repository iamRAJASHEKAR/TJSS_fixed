package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.Incident;
import com.twixttechnologies.tjss.model.repository.IncidentsRepository;

/**
 * @author Sony Raj on 19-10-17.
 */
public class IncidentsViewModel extends ViewModelBase {


    private final IncidentsRepository mIncidentsRepository;

    private final MutableLiveData<Incident[]> mIncidentsData
            = new MutableLiveData<>();


    private final RequestCallback<Incident[]> mIncidentsRequestCallbak
            = new RequestCallback<Incident[]>() {
        @Override
        public void onSuccess(Incident[] result) {
            mIncidentsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private IncidentsViewModel(Application application) {
        super(application);
        mIncidentsRepository = new IncidentsRepository();
        getAllIncidents();
    }

    private void getAllIncidents() {
        String path = getApplication().getString(R.string.incident_log_path);
        mIncidentsRepository.getIncidentLog(path, getToken(), getUserId(), mIncidentsRequestCallbak);
    }

    public MutableLiveData<Incident[]> getIncidentsData() {
        return mIncidentsData;
    }

    @SuppressWarnings("unchecked")
    public static class IncidentsViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public IncidentsViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new IncidentsViewModel(mApplication);
        }
    }


}