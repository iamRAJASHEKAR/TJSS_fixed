package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.internal.InternalContact;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.IsdCode;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.repository.IsdRepository;
import com.twixttechnologies.tjss.model.repository.TjssRepository;
import com.twixttechnologies.tjss.utils.AsyncContactsFetchController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 19-09-17.
 */

/**
 * @author Sony Raj on 22-09-17.
 */
public class ContactsViewModel extends ViewModelBase implements RequestCallback<StatusMessage> {

    private final TjssRepository mRepository;
    private final IsdRepository mIsdRepository;

    private final MutableLiveData<ArrayList<InternalContact>> mContactsData
            = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mStatusMessageData
            = new MutableLiveData<>();

    private final MutableLiveData<List<IsdCode>> mIsdCodesData
            = new MutableLiveData<>();

    private final RequestCallback<ArrayList<InternalContact>> mContactsRequestCallback
            = new RequestCallback<ArrayList<InternalContact>>() {
        @Override
        public void onSuccess(ArrayList<InternalContact> result) {
            mContactsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private final RequestCallback<List<IsdCode>> mIsdCodesCallback
            = new RequestCallback<List<IsdCode>>() {
        @Override
        public void onSuccess(List<IsdCode> result) {
            mIsdCodesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private ContactsViewModel(Application application) {
        super(application);
        mRepository = new TjssRepository();
        mIsdRepository = new IsdRepository();
        String path = getApplication().getString(R.string.isd_codes_url);
        mIsdRepository.get(path, mIsdCodesCallback);
    }

    public MutableLiveData<ArrayList<InternalContact>> getContactsData() {
        return mContactsData;
    }

    public MutableLiveData<StatusMessage> getStatusMessageData() {
        return mStatusMessageData;
    }

    public MutableLiveData<List<IsdCode>> getIsdCodesData() {
        return mIsdCodesData;
    }

    @Nullable
    public ArrayList<InternalContact> getContacts() {
        if (mContactsData.getValue() == null) {
            new AsyncContactsFetchController(this.getApplication(), mContactsRequestCallback)
                    .execute();
        }
        return mContactsData.getValue();
    }


    public void addContact(InternalContact contact) {
        String path = getApplication().getString(R.string.add_contact_path);
        HashMap<String, String> params = new HashMap<>(3);
        params.put("name", contact.getName());
        params.put("phone", contact.getPhone());
        params.put("userId", getUserId());

        mRepository.addContact(path, getHeaders(), params, this);

    }

    @Override
    public void onSuccess(StatusMessage result) {
        mStatusMessageData.setValue(result);
    }

    @Override
    public void onFailure(String reason) {
        mError.setValue(reason);
    }


    @SuppressWarnings("unchecked")
    public static class ContactsViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;


        public ContactsViewModelFactory(Application application) {
            this.mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ContactsViewModel(mApplication);
        }
    }


}