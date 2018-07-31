package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.internal.InternalContact;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.IsdCode;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.IsdRepository;
import com.innoviussoftwaresolution.tjss.model.repository.TjssRepository;
import com.innoviussoftwaresolution.tjss.utils.AsyncContactsFetchController;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.ContactsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
            filterList(result);
            /*SharedPreferences preferences = getApplication().getSharedPreferences("Isdcodes", 0);
            SharedPreferences.Editor editor = preferences.edit();
            Set<IsdCode> set = new HashSet<IsdCode>();
            set.addAll(result);
            editor.putStringSet("isdlist",set);*/
            /*Set<IsdCode> set = result;

//Set the values
            Set<String> set = new HashSet<String>();
            set.addAll(listOfExistingScores);
            editor.putStringSet("key", set);
            editor.commit();*/
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private void filterList(List<IsdCode> isdCodeList) {
        List<String> isdList = new ArrayList<>();
        for (int i = 0; i < isdCodeList.size(); i++) {
            isdList.add(isdCodeList.get(i).countryCode);
        }
        SharedPreferences preferences = getApplication().getSharedPreferences("Isdcodes", 0);
            SharedPreferences.Editor editor = preferences.edit();
            Set<String> set = new HashSet<String>();
            set.addAll(isdList);
            editor.putStringSet("isdlist",set);
            editor.commit();
    }


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
        ContactsListAdapter adapter;

        String path = getApplication().getString(R.string.add_contact_path);
        HashMap<String, String> params = new HashMap<>(3);
        params.put("name", contact.getName());
        params.put("phone", contact.getPhone().trim());
        params.put("userId", getUserId());
        params.put("email", contact.getEmail());

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