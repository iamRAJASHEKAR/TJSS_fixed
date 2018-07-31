package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.CheckInHistory;
import com.innoviussoftwaresolution.tjss.model.network.response.InAppPurchaseServiceItem;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 09-10-17.
 */
public class InAppServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final OnInAppServicesSelectedListener mItemSelectedListener;
    private List<InAppPurchaseServiceItem> items;

    private static final int ITEM = 263;
    private static final int NO_CONTENT = 615;
    private boolean gotNoContent = false;

    public InAppServicesAdapter(List<InAppPurchaseServiceItem> items, Context context,
                                OnInAppServicesSelectedListener listener) {
        this.items = items;
        this.context = context;
        this.mItemSelectedListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.extra_service, parent, false);
//        return new InAppServicesViewHolder(v);

        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == ITEM ?
                        R.layout.extra_service : R.layout.no_services, parent, false);
        return viewType == ITEM ? new InAppServicesViewHolder(v) : new NoContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof InAppServicesViewHolder) {
            InAppPurchaseServiceItem item = items.get(position);
            ((InAppServicesViewHolder)  holder).mLblServiceHeader.setText(item.name);
            ((InAppServicesViewHolder)  holder).mLblServiceDescription.setText(item.description);
            ((InAppServicesViewHolder)  holder).mLblServiceAddressLine1.setText(item.address);
            ((InAppServicesViewHolder)  holder).mLblServicePhone.setText(item.phone);
            ((InAppServicesViewHolder)  holder).mLblServicesCoinsRequired.setText(MessageFormat.format("{0} Coins", item.amount));
            ((InAppServicesViewHolder)  holder).mLblServicesPriceTag.setText(MessageFormat.format("{0} Prices", item.points));




        } else if (holder instanceof CheckInHistoryAdapter.NoContentViewHolder) {
            ((InAppServicesAdapter.NoContentViewHolder) holder).mLblError.setText("You have no coins and amount yet");

        }

    }

//    @Override
//    public void onBindViewHolder(InAppServicesViewHolder holder, int position) {
//        InAppPurchaseServiceItem item = items.get(position);
//        holder.mLblServiceHeader.setText(item.name);
//        holder.mLblServiceDescription.setText(item.description);
//        holder.mLblServiceAddressLine1.setText(item.address);
//        holder.mLblServicePhone.setText(item.phone);
//        holder.mLblServicesCoinsRequired.setText(MessageFormat.format("{0} Coins", item.amount));
//        holder.mLblServicesPriceTag.setText(MessageFormat.format("{0} Prices", item.points));
//    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public interface OnInAppServicesSelectedListener {
        void onSelected(InAppPurchaseServiceItem item, boolean call);
    }

    class InAppServicesViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.lblServiceHeader)
        AppCompatTextView mLblServiceHeader;

        @BindView(R.id.lblServiceDescription)
        AppCompatTextView mLblServiceDescription;


        @BindView(R.id.lblServiceContactInfo)
        AppCompatTextView mLblServiceContactInfo;

        @BindView(R.id.lblServiceAddressLine1)
        AppCompatTextView mLblServiceAddressLine1;

        @BindView(R.id.lblServicePhone)
        AppCompatTextView mLblServicePhone;

        @BindView(R.id.lblServicesCoinsRequired)
        AppCompatTextView mLblServicesCoinsRequired;

        @BindView(R.id.lblServicesPriceTag)
        AppCompatTextView mLblServicesPriceTag;

        @BindView(R.id.viewServiceSeparator)
        View mViewServiceSeparator;

        @BindView(R.id.btnServiceCall)
        AppCompatButton mBtnServiceCall;

        @BindView(R.id.btnServicesPay)
        AppCompatButton mBtnServicesPay;

        InAppServicesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick({R.id.lblServiceHeader, R.id.btnServiceCall, R.id.btnServicesPay})
        public void onViewClicked(View view) {

            int index = getAdapterPosition();
            if (index < 0) return;
            InAppPurchaseServiceItem item = items.get(index);
            switch (view.getId()) {
                case R.id.lblServiceHeader:
                    mLblServiceHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            item.isOpen ? R.drawable.ic_arrow_up_thin : R.drawable.ic_arrow_down_thin,
                            0);
                    mLblServiceDescription.setMaxLines(item.isOpen ? 100 : 1);
                    mLblServiceContactInfo.setVisibility(item.isOpen ? View.VISIBLE : View.GONE);
                    mLblServiceAddressLine1.setVisibility(item.isOpen ? View.VISIBLE : View.GONE);
                    mLblServicePhone.setVisibility(item.isOpen ? View.VISIBLE : View.GONE);
                    mLblServicesCoinsRequired.setVisibility(item.isOpen ? View.VISIBLE : View.GONE);
                    mLblServicesPriceTag.setVisibility(item.isOpen ? View.VISIBLE : View.GONE);
                    mBtnServiceCall.setVisibility(item.isOpen ? View.VISIBLE : View.GONE);
                    mBtnServicesPay.setVisibility(item.isOpen ? View.VISIBLE : View.GONE);
                    mViewServiceSeparator.setVisibility(item.isOpen ? View.VISIBLE : View.GONE);
                    item.isOpen = !item.isOpen;
                    break;
                case R.id.btnServiceCall:
                    mItemSelectedListener.onSelected(item, true);
                    break;
                case R.id.btnServicesPay:
                    if (mItemSelectedListener != null)
                        mItemSelectedListener.onSelected(item, false);
                    break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        return gotNoContent ? NO_CONTENT : ITEM;
    }

    class NoContentViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.lblNoServiceCoins)
        AppCompatTextView mLblError;

        NoContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}