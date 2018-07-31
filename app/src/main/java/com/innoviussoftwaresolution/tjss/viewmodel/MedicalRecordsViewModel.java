package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.helper.RequestHelper;
import com.innoviussoftwaresolution.tjss.model.network.response.MedicalRecord;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.MedicalRecordsRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Sony Raj on 20-10-17.
 */
public class MedicalRecordsViewModel extends ViewModelBase {

    private final MedicalRecordsRepository mRepository;

    //region upload
    private final MutableLiveData<StatusMessage> mRecordUploadResultData
            = new MutableLiveData<>();

    private final RequestCallback<StatusMessage> mDataUploadRequestCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mRecordUploadResultData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private final MutableLiveData<ArrayList<MedicalRecord>> mMedicalRecordsData
            = new MutableLiveData<>();

    //endregion

    //region listing
    private final RequestCallback<ArrayList<MedicalRecord>> mMedicalRecordsRequestCallback
            = new RequestCallback<ArrayList<MedicalRecord>>() {
        @Override
        public void onSuccess(ArrayList<MedicalRecord> result) {
            mMedicalRecordsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            //mError.setValue(reason);
        }
    };
    private final MutableLiveData<StatusMessage> mDeleteStatusData
            = new MutableLiveData<>();
    private final RequestCallback<StatusMessage> mDeleteStatusRequestCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mDeleteStatusData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    //endregion

    //region delete

    private MedicalRecordsViewModel(Application application) {
        super(application);
        mRepository = new MedicalRecordsRepository();
        list();
    }

    public MutableLiveData<StatusMessage> getRecordUploadResultData() {
        return mRecordUploadResultData;
    }

    public MutableLiveData<ArrayList<MedicalRecord>> getMedicalRecordsData() {
        return mMedicalRecordsData;
    }

    //endregion

    public MutableLiveData<StatusMessage> getDeleteStatusData() {
        return mDeleteStatusData;
    }

    public void uploadData(String title, String filePath) {
        String uploadPath = getApplication().getString(R.string.upload_medical_record_path);
        HashMap<String, RequestBody> params = new HashMap<>(2);
        params.put("userId", RequestHelper.getRequestBody(getUserId()));
        params.put("title", RequestHelper.getRequestBody(title));
        MultipartBody.Part file = RequestHelper.getMultipartBodyPart("record", new File(filePath));
        mRepository.uploadRecords(uploadPath, getHeaders(), params, file, mDataUploadRequestCallback);
    }


    public void list() {
        String path = getApplication().getString(R.string.medical_records_listing_path);
        mRepository.list(path, getHeaders(), getUserIdParamsMap(), mMedicalRecordsRequestCallback);
    }

    public void delete(String id) {
        String path = getApplication().getString(R.string.medical_record_delete_path);
        HashMap<String, String> params = new HashMap<>(1);
        params.put("recordId", id);
        mRepository.delete(path, getHeaders(), params, mDeleteStatusRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class MedicalRecordsViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public MedicalRecordsViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MedicalRecordsViewModel(mApplication);
        }
    }


}