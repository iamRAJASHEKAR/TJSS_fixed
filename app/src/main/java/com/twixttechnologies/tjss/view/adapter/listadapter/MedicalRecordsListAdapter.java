package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.MedicalRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 21-10-17.
 */
public class MedicalRecordsListAdapter extends RecyclerView.Adapter<MedicalRecordsListAdapter.MedicalRecordViewHolder> {


    private List<MedicalRecord> items;
    private DeleteRecordCallback mCallback;

    public MedicalRecordsListAdapter(List<MedicalRecord> items, DeleteRecordCallback callback) {
        this.items = items;
        this.mCallback = callback;
    }

    public void remove(int index) {
        if (index < 0 && index >= items.size()) return;
        items.remove(index);
        notifyItemRemoved(index);
    }


    @Override
    public MedicalRecordViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_medical_record_item, parent, false);
        return new MedicalRecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MedicalRecordViewHolder holder, int position) {
        MedicalRecord item = items.get(position);
        holder.mLblMedicalRecordTitle.setText(item.title);
        holder.mLblMedicalRecordName.setText(item.record);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    public interface DeleteRecordCallback {
        void delete(MedicalRecord record, int index);
    }

    class MedicalRecordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lblMedicalRecordTitle)
        AppCompatTextView mLblMedicalRecordTitle;
        @BindView(R.id.lblMedicalRecordName)
        AppCompatTextView mLblMedicalRecordName;

        MedicalRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.btnDelete)
        public void onViewClicked() {
            int index = getAdapterPosition();
            if (index < 0 || index >= items.size()) return;
            MedicalRecord record = items.get(index);
            if (record == null) return;
            mCallback.delete(record, index);
        }


    }


}