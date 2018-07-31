package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.internal.InternalContact;
import com.innoviussoftwaresolution.tjss.utils.AdapterCallback;
import com.innoviussoftwaresolution.tjss.utils.M;

import java.util.Comparator;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 22-09-17.
 */
public class ContactsListAdapter extends SortedListAdapter<InternalContact> {

    private Context mContext;
    private AdapterCallback<InternalContact> mListener;
    private String emailId;


    public ContactsListAdapter(@NonNull Context context, AdapterCallback<InternalContact> listener,
                               @NonNull Comparator<InternalContact> comparator) {

        super(context, InternalContact.class, comparator);
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    protected ViewHolder<? extends InternalContact> onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.extra_contact_item, viewGroup, false);
        return new ContactsViewHolder(view);
    }

    class ContactsViewHolder extends SortedListAdapter.ViewHolder<InternalContact> {

        @BindView(R.id.lblPhoneContactName)
        AppCompatTextView mLblPhoneContactName;
        @BindView(R.id.lblPhoneContactNumber)
        AppCompatTextView mLblPhoneContactNumber;
        @BindView(R.id.btnAddContactToCircle)
        AppCompatImageButton mBtnAddContactToCircle;

        ContactsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void performBind(@NonNull InternalContact contact) {
            mLblPhoneContactName.setText(contact.getName());
            mLblPhoneContactNumber.setText(contact.getPhone());


        }


        @OnClick(R.id.btnAddContactToCircle)
        public void onClick() {
            showInputDialog();

        }

        public void getEmail(String string) {

        }
//        String email = "";

        protected void showInputDialog() {

            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View promptView = layoutInflater.inflate(R.layout.insert_email_dialog_layout, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setView(promptView);

            final TextInputEditText etEmail = (TextInputEditText) promptView.findViewById(R.id.txtEnterEmailContacts);
            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            String email = etEmail.getText().toString();
                            InternalContact contact = getCurrentItem();
                            InternalContact contact1 = new InternalContact("", contact.getName(), contact.getPhone(), email);
                            mListener.onAdapterCall(contact1);

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create an alert dialog
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

    }
}