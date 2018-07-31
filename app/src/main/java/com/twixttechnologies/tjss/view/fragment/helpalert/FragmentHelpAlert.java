package com.twixttechnologies.tjss.view.fragment.helpalert;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleContact;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.LocationUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.AddContactActivity;
import com.twixttechnologies.tjss.view.activity.HelpAlertContactEditActivity;
import com.twixttechnologies.tjss.view.activity.SelectContactActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.twixttechnologies.tjss.view.fragment.helpalert.alert.AddNewContactAlertDialog;
import com.twixttechnologies.tjss.view.fragment.helpalert.alert.CountDownAlert;
import com.twixttechnologies.tjss.view.fragment.helpalert.alert.InitHelpAlertDialog;
import com.twixttechnologies.tjss.view.viewutils.Permissions;
import com.twixttechnologies.tjss.viewmodel.HelpAlertViewModel;
import com.twixttechnologies.tjss.viewmodel.SafetyCircleContactsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20-07-17.
 */

public class FragmentHelpAlert extends BaseFragment implements
        AddNewContactAlertDialog.ContactSelectionTypeDialogCallback,
        PermissionsDetailListDialog.PermissionsDialogCallback,
        InitHelpAlertDialog.OnStatusListener,
        CountDownAlert.OnCountDownFinishedListener,
        SafetyCircleContactsAdapter.HelpAlertContactActionListener {

    @BindView(R.id.lstHelpAlertContacts)
    RecyclerView mLstHelpAlertContacts;

    Unbinder unbinder;
    private HelpAlertViewModel mViewModel;

    private Observer<List<SafetyCircleContact>> mContactsObserver
            = new Observer<List<SafetyCircleContact>>() {
        @Override
        public void onChanged(@Nullable List<SafetyCircleContact> safetyCircleContacts) {
            if (safetyCircleContacts == null) return;
            SafetyCircleContactsAdapter adapter = new SafetyCircleContactsAdapter(safetyCircleContacts, FragmentHelpAlert.this);
            mLstHelpAlertContacts.setAdapter(adapter);
            mLstHelpAlertContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    };


    private Observer<StatusMessage> mDeleteStatusObserver;

    private Observer<String> mErrorObserver
            = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String error) {
            if (error == null || error.equals("")) return;

            if (error.toLowerCase().contains("location")) {
                M.showAlert(getActivity(), "Help Alert", error, "OPEN SETTINGS", "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new LocationUtil(getActivity()).showSettingsAlert();
                    }
                }, null, false);
            }
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_alert, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new HelpAlertViewModel.HelpAlertViewModelFactory(getActivity().getApplication()))
                .get(HelpAlertViewModel.class);

        mViewModel.getContactsData().observe(this, mContactsObserver);
        mViewModel.getError().observe(this, mErrorObserver);
    }


    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getContacts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAddNewContact)
    public void onAddNewContactClicked() {
        if (!havePermission(Permissions.READ_CONTACTS)) {
            if (shouldShowPermissionReationale(Permissions.READ_CONTACTS) != null) {
                ArrayList<String> permissions = new ArrayList<>(1);
                permissions.add("Request to read contacts-TJSS requires your permission to read contact to add new contact to your help alert numbers");
                showPermissionDetails(permissions, this);
            } else {
                requestPermission(Permissions.CONTACTS_REQUEST_CODE, Permissions.READ_CONTACTS);
            }
        } else {
            showSelectContactsDialog();
        }
    }


    private void showSelectContactsDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AddNewContactAlertDialog dialog = new AddNewContactAlertDialog();
                dialog.setCallback(FragmentHelpAlert.this);
                dialog.show(getChildFragmentManager(), AddNewContactAlertDialog.class.getName());
            }
        }, 300);
    }

    @Override
    public void onContactTypeSelected(@AddNewContactAlertDialog.ContactSelectionType int type) {
        if (type == AddNewContactAlertDialog.TYPE_MANUAL) {
            startActivity(new Intent(getActivity(), AddContactActivity.class));
        } else {
            startActivity(new Intent(getActivity(), SelectContactActivity.class));
        }
    }


    @OnClick(R.id.btnSendHelpAlert)
    public void onSendHelpAlertClicked() {
        InitHelpAlertDialog dialog = new InitHelpAlertDialog();
        dialog.setListener(this);
        dialog.setCancelable(true);
        dialog.show(getChildFragmentManager(), "HelpAlert");
    }

    @Override
    public void onPermissionGranted() {
        requestPermission(Permissions.CONTACTS_REQUEST_CODE, Permissions.READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permissions.CONTACTS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showSelectContactsDialog();
            } else {
                M.showAlert(getActivity(), "Add new contact", "Unable to read contacts without permission, " +
                        "Permission denied, Please go to phone settings and change permission to " +
                        "allow read contacts, Or Add manually", "ADD MANUALLY", "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), AddContactActivity.class));
                    }
                }, null, false);
            }
        }
    }

    @Override
    public void onStatus(boolean success) {
        if (!success) {
            M.showToast(getActivity(), "Invalid data");
        } else {
            CountDownAlert dialog = new CountDownAlert();
            dialog.setCancelable(false);
            dialog.setListener(this);
            dialog.show(getChildFragmentManager(), "Counter");
        }
    }

    @Override
    public void onFinish() {
        mViewModel.sendHelpAlert();
    }

    @Override
    public void onAction(@NonNull final SafetyCircleContact contact, int action) {
        if (action == SafetyCircleContactsAdapter.ACTION_DELETE) {
            M.showAlert(getActivity(), "Delete Contact", "Are you sure, you want to remove this contact from your help alert list?\n\n" +
                            "Note : This will not remove anything from your phone contacts", "REMOVE", "NO,THANKS",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (mDeleteStatusObserver == null) {
                                mDeleteStatusObserver = new Observer<StatusMessage>() {
                                    @Override
                                    public void onChanged(@Nullable StatusMessage statusMessage) {
                                        dismissDialog();
                                        if (statusMessage == null || statusMessage.status == null ||
                                                statusMessage.status.equals("")) {
                                            M.showAlert(getActivity(), "Delete Contact", "An error occurred, Please try again later",
                                                    "OK", null, null, null, false);
                                        } else if (statusMessage.status.toLowerCase().contains("success")) {
                                            M.showToast(getActivity(), "Deleted successfully");
                                            try {
                                                //((SafetyCircleContactsAdapter) mLstHelpAlertContacts.getAdapter()).delete(contact);
                                                mViewModel.getContacts();
                                            } catch (Exception e) {
                                                M.log(e.getMessage());
                                            }
                                        }
                                    }
                                };

                                mViewModel.getDeleteStatusData().observe(FragmentHelpAlert.this, mDeleteStatusObserver);
                            }
                            initProgress();
                            initErrorObserver();
                            mViewModel.delete(contact.id);
                        }
                    }, null, false);
        } else if (action == SafetyCircleContactsAdapter.ACTION_EDIT) {
            Intent editIntent = new Intent(getActivity(), HelpAlertContactEditActivity.class);
            editIntent.putExtra(HelpAlertContactEditActivity.HELP_ALERT_CONTACT, contact);
            startActivity(editIntent);
        }
    }
}
