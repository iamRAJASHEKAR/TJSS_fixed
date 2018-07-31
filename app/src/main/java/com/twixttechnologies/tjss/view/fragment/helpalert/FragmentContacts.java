package com.twixttechnologies.tjss.view.fragment.helpalert;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.SortedList;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.internal.InternalContact;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.AdapterCallback;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.adapter.listadapter.ContactsListAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.twixttechnologies.tjss.view.viewutils.Permissions;
import com.twixttechnologies.tjss.view.viewutils.TextChangeAdapter;
import com.twixttechnologies.tjss.viewmodel.ContactsViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 22-09-17.
 */

public class FragmentContacts extends BaseFragment implements
        PermissionsDetailListDialog.PermissionsDialogCallback,
        AdapterCallback<InternalContact> {

    public static final String TAG = "FragmentContacts";
    private final Comparator<InternalContact> mContactComparator = new Comparator<InternalContact>() {
        @Override
        public int compare(InternalContact o1, InternalContact o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };
    @BindView(R.id.lstContacts)
    RecyclerView mLstContacts;
    private final SortedList.Callback<InternalContact> mSortedListCallback
            = new SortedList.Callback<InternalContact>() {
        @Override
        public int compare(InternalContact o1, InternalContact o2) {
            return mContactComparator.compare(o1, o2);
        }

        @Override
        public void onChanged(int position, int count) {
            if (mLstContacts.getAdapter() == null) return;
            mLstContacts.getAdapter().notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(InternalContact oldItem, InternalContact newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(InternalContact item1, InternalContact item2) {
            return item1.getId().equals(item2.getId());
        }

        @Override
        public void onInserted(int position, int count) {
            if (mLstContacts.getAdapter() != null)
                mLstContacts.getAdapter().notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            if (mLstContacts.getAdapter() != null)
                mLstContacts.getAdapter().notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            if (mLstContacts.getAdapter() != null)
                mLstContacts.getAdapter().notifyItemMoved(fromPosition, toPosition);
        }
    };
    Unbinder unbinder;
    @BindView(R.id.txtSearchContacts)
    AppCompatEditText mTxtSearchContacts;
    private InternalContact mSelectedContact = null;
    private final Observer<StatusMessage> mStatusObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            if (statusMessage == null) {
                dismissDialog();
                return;
            }
            if (statusMessage.status == null) {
                dismissDialog();
                return;
            }
            if (statusMessage.status.equals("")) {
                dismissDialog();
                return;
            }
            if (statusMessage.status.toLowerCase().equals("success")) {
                try {
                    M.showToast(getActivity(), "Added");
                } catch (Exception e) {
                    M.log(e.getMessage());
                }
            } else
                M.showAlert(getActivity(), "Add Contact", statusMessage.status, "OK", null, null, null, false);
            mSelectedContact = null;
            dismissDialog();
        }
    };
    private ContactsViewModel mViewModel;
    private List<InternalContact> mContacts;
    private final Observer<ArrayList<InternalContact>> mContactsObserver
            = new Observer<ArrayList<InternalContact>>() {
        @Override
        public void onChanged(@Nullable ArrayList<InternalContact> internalContacts) {
            dismissDialog();
            if (internalContacts == null || internalContacts.size() <= 0) return;
            mContacts = internalContacts;
            bindContacts(internalContacts);
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_contacts, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new ContactsViewModel.ContactsViewModelFactory(getActivity().getApplication()))
                .get(ContactsViewModel.class);
        mViewModel.getContactsData().observe(this, mContactsObserver);
        mViewModel.getStatusMessageData().observe(this, mStatusObserver);
        initProgress();
        initErrorObserver();
        mViewModel.getContacts();
        mTxtSearchContacts.addTextChangedListener(new TextChangeAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (TextUtils.isEmpty(mTxtSearchContacts.getText())) return;
                List<InternalContact> contacts = filter(mContacts, mTxtSearchContacts.getText().toString());
                ((ContactsListAdapter) mLstContacts.getAdapter()).edit().replaceAll(contacts).commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAdapterCall(InternalContact selectedContact) {
        mSelectedContact = selectedContact;
        initErrorObserver();
        initProgress();
        mViewModel.addContact(selectedContact);
    }

    @Override
    public void onPermissionGranted() {
        if (shouldShowRequestPermissionRationale(Permissions.READ_CONTACTS)) {
            showPermissionDetails();
        } else {
            requestPermissions(new String[]{Permissions.READ_CONTACTS}, Permissions.CONTACTS_REQUEST_CODE);
        }
    }


    //region Observers

    private void showPermissionDetails() {
        ArrayList<String> permissionDetails = new ArrayList<>(1);
        permissionDetails.add("Permission to read Contacts-TJSS will need your permission to read your contacts to add new member from your contacts");
        showPermissionDetails(permissionDetails, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != Permissions.CONTACTS_REQUEST_CODE) return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContacts();
        } else {
            Toast.makeText(getActivity(), "Permission denied,Please add manually, or change permission in settings", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    //endregion Observers


    //region filtering

    private void getContacts() {
        ArrayList<InternalContact> contacts = mViewModel.getContacts();
        if (contacts == null || contacts.size() <= 0) return;
        bindContacts(contacts);
    }

    private void bindContacts(@NonNull ArrayList<InternalContact> contacts) {
        ContactsListAdapter adapter = new ContactsListAdapter(getActivity(), this, mContactComparator);
        mLstContacts.setAdapter(adapter);
        mLstContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.edit().replaceAll(contacts).commit();
    }

    private List<InternalContact> filter(List<InternalContact> list, String query) {
        final String lowerCaseQuery = query.toLowerCase();
        final ArrayList<InternalContact> contacts = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            contacts.addAll(mContacts);
        } else {
            for (InternalContact contact : list) {
                final String name = contact.getName().toLowerCase();
                final String phone = contact.getPhone().toLowerCase();

                if (name.contains(lowerCaseQuery) || phone.contains(lowerCaseQuery)) {
                    contacts.add(contact);
                }
            }
        }
        return contacts;
    }

    //endregion filtering


}
