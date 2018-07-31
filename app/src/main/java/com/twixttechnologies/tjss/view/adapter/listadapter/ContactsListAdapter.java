package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.internal.InternalContact;
import com.twixttechnologies.tjss.utils.AdapterCallback;

import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 22-09-17.
 */
public class ContactsListAdapter extends SortedListAdapter<InternalContact> {


    private AdapterCallback<InternalContact> mListener;


    public ContactsListAdapter(@NonNull Context context, AdapterCallback<InternalContact> listener,
                               @NonNull Comparator<InternalContact> comparator) {
        super(context, InternalContact.class, comparator);
        mListener = listener;
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
            mListener.onAdapterCall(getCurrentItem());
        }

    }

}