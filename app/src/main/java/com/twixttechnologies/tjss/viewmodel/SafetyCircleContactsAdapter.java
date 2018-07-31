package com.twixttechnologies.tjss.viewmodel;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleContact;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 22-09-17.
 */
public class SafetyCircleContactsAdapter extends RecyclerView.Adapter<SafetyCircleContactsAdapter.SafetyCircleContactViewHolder> {

    private List<SafetyCircleContact> items;
    private final HelpAlertContactActionListener mListener;

    public SafetyCircleContactsAdapter(List<SafetyCircleContact> items,
                                       HelpAlertContactActionListener listener) {
        this.items = items;
        this.mListener = listener;
    }

    @Override
    public SafetyCircleContactViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_contact_item, parent, false);
        return new SafetyCircleContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SafetyCircleContactViewHolder holder, int position) {
        SafetyCircleContact item = items.get(position);
        holder.mLblPhoneContactName.setText(item.name);
        holder.mLblPhoneContactNumber.setText(item.phone);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void delete(SafetyCircleContact contact) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id.equals(contact.id)) {
                items.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    class SafetyCircleContactViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.lblPhoneContactName)
        AppCompatTextView mLblPhoneContactName;
        @BindView(R.id.lblPhoneContactNumber)
        AppCompatTextView mLblPhoneContactNumber;
        @BindView(R.id.btnAddContactToCircle)
        AppCompatImageButton mBtnAddContactToCircle;

        @BindView(R.id.btnEditHelpAlertContact)
        AppCompatImageButton mBtnEdit;

        @BindView(R.id.btnDeleteHelpAlertContact)
        AppCompatImageButton mBtnDelete;


        SafetyCircleContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBtnAddContactToCircle.setVisibility(View.GONE);
            mBtnEdit.setVisibility(View.VISIBLE);
            mBtnDelete.setVisibility(View.VISIBLE);
        }


        @OnClick({R.id.btnEditHelpAlertContact, R.id.btnDeleteHelpAlertContact})
        void click(View v) {

            int index = getAdapterPosition();
            if (index < 0) return;
            SafetyCircleContact contact = items.get(index);
            if (contact == null) return;
            switch (v.getId()) {
                case R.id.btnEditHelpAlertContact:
                    mListener.onAction(contact, ACTION_EDIT);
                    break;

                case R.id.btnDeleteHelpAlertContact:
                    mListener.onAction(contact, ACTION_DELETE);
                    break;
            }
        }

    }


    public interface HelpAlertContactActionListener {
        void onAction(@NonNull SafetyCircleContact contact, @ContactAction int action);
    }

    public static final int ACTION_EDIT = 1;
    public static final int ACTION_DELETE = 2;

    @IntDef({ACTION_DELETE, ACTION_EDIT})
    @Retention(RetentionPolicy.SOURCE)
    @interface ContactAction {
    }

}