package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.PaymentStatus;
import com.innoviussoftwaresolution.tjss.model.network.response.Plan;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.HomeActivity;
import com.innoviussoftwaresolution.tjss.view.activity.PaymentActivity;
import com.innoviussoftwaresolution.tjss.view.activity.ViewPlanDetailsActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.payment.FragmentPurchaseBase;
import com.innoviussoftwaresolution.tjss.view.fragment.plans.FragmentViewPlanDetails;
import com.innoviussoftwaresolution.tjss.viewmodel.PlansViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 07-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class SignUpStepSixSelectPlan extends FragmentPurchaseBase {

    Unbinder unbinder;

    @BindView(R.id.lblSelectedPlanType)
    AppCompatTextView mLblSelectedPlanType;

    @BindView(R.id.lblSelectedPlanExtra)
    AppCompatTextView mLblSelectedPlanExtra;

    @BindView(R.id.lblSelectedPlanFee)
    AppCompatTextView mLblSelectedPlanFee;

    @BindView(R.id.lblSelectedPlanDescription)
    AppCompatTextView mLblSelectedPlanDescription;

    @BindView(R.id.btnSelectedPlanDetails)
    AppCompatButton mBtnSelectedPlanDetails;

    @BindView(R.id.lblSelectedPLan)
    AppCompatTextView mLblSelectedPLan;

    @BindView(R.id.ltSelectedPlanCard)
    CardView mLtSelectedPlanCard;

    @BindView(R.id.lrPlans)
    LinearLayout mLtPlans;

    private Plan mSelectedPlan;

    private boolean mShowPayment = false;

    private PlansViewModel mViewModel;
    private View.OnClickListener mShowMoreClickListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() == null) {
                // user clicked on the view more button the selected plan item
                // Don't worry , just get the selected plan from the view model
                // If the selected plan is null, inform the user to select  one plan

                Plan selectedPlan = mViewModel.getSelectedPlanData().getValue();
                if (selectedPlan == null) {
                    Toast t = Toast.makeText(getActivity(), "Please select one plan first",
                            Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.TOP, 0, 200);
                    t.show();
                } else {
                    showPlanDetails(selectedPlan);
                }

            } else {
                int index = ((int) v.getTag());
                if (index < 0) return;
                if (mViewModel.getPlansLiveData().getValue() == null) return;
                if (mViewModel.getPlansLiveData().getValue().size() <= 0) return;
                showPlanDetails(mViewModel.getPlansLiveData().getValue().get(index));
            }
        }
    };
    private ArrayList<View> mPlanViews;
    private View.OnClickListener mPlanSelectedListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            if (index < 0) return;
            Plan selectedPlan = mViewModel.getPlansLiveData().getValue().get(index);
            if (index >= 0) {
                v.setBackgroundResource(R.drawable.selected_plan_bg);
                if (mViewModel.getPlansLiveData().getValue() != null)
                    mViewModel.setSelectedPlan(selectedPlan);
            }
            if (mPlanViews == null) {
                return;
            }
            if (mViewModel.getPlansLiveData().getValue() != null) {
                for (int i = 0; i < mViewModel.getPlansLiveData().getValue().size(); i++) {
                    if (i == index) continue;
                    mPlanViews.get(i).setBackgroundResource(android.R.color.white);
                }
            }
            if (!mShowPayment) {
                mShowPayment = true;
                return;
            }

            if (selectedPlan == null) {
                return;
            }
            if (selectedPlan.id == null || selectedPlan.id.equals("")) {
                return;
            }
            if (selectedPlan.planName.toLowerCase().contains("basic")) {

                return;
            }
            showPaymentTypeSelector(new BigDecimal(0), true);
        }
    };
    private Observer<List<Plan>> mPlansListObserver
            = new Observer<List<Plan>>() {
        @Override
        public void onChanged(@Nullable List<Plan> plans) {

            if (plans == null) return;
            if (plans.size() <= 0) return;
            mPlanViews = new ArrayList<>(plans.size());
            String selectedPlanId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, "-1");
            int index = 0;
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < plans.size(); i++) {
                Plan plan = plans.get(i);
                if (plan == null) continue;
                if (selectedPlanId.equals(plan.id)) {
                    index = i;
                    mLblSelectedPlanType.setText(plan.planName);
                   mLblSelectedPlanFee.setText(plan.planPrice + " "+getActivity().getResources().getString(R.string.niaraSign));
                    //mLblSelectedPlanFee.setText(plan.planPrice+"₦");

                    mLblSelectedPlanDescription.setText(plan.planDescription);
                    mLblSelectedPlanExtra.setText("");
                }
                View view = inflater.inflate(R.layout.extra_plan, mLtPlans, false);
                view.setTag(i);
                AppCompatTextView planType = (AppCompatTextView) view.findViewById(R.id.lblPlanType);
                AppCompatTextView planDuration = (AppCompatTextView) view.findViewById(R.id.lblPlanDuration);
                AppCompatTextView planFee = (AppCompatTextView) view.findViewById(R.id.lblPlanTypeFee);
                AppCompatTextView planDescription = (AppCompatTextView) view.findViewById(R.id.lblPlanDescription);
                AppCompatButton planDetails = (AppCompatButton) view.findViewById(R.id.btnPlanDetails);

                planDetails.setTag(i);

                planType.setText(plan.planName);
                planDescription.setText(plan.planDescription);
                planFee.setText(plan.planPrice+" "+getActivity().getResources().getString(R.string.niaraSign));
                planDuration.setText("");
                planDetails.setOnClickListener(mShowMoreClickListener);
                view.setOnClickListener(mPlanSelectedListener);
                mLtPlans.removeView(view);
                mLtPlans.addView(view);

                mPlanViews.add(view);
            }
            mPlanViews.get(index).callOnClick();

        }
    };
    private Observer<Plan> mSelectedPlanChangeObserver
            = new Observer<Plan>() {
        @Override
        public void onChanged(@Nullable Plan plan) {
            mSelectedPlan = plan;
            bindSelectedPlan(plan);
        }
    };
    private Observer<PaymentStatus> mSaveDataObserver
            = new Observer<PaymentStatus>() {
        @Override
        public void onChanged(@Nullable PaymentStatus statusMessage) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
            if (statusMessage == null || statusMessage.paymentStatus == null || statusMessage.paymentStatus.equals("") || !statusMessage.paymentStatus.toLowerCase().contains("success")) {
                M.showAlert(getActivity(), "Save payment", "An error occurred while saving your payment, Please contact our support, We are sorry for the inconveniences caused.",
                        "OK", null, null, null, false);
            } else {
                String planId = getSelectedPlan().id;
                PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, planId);

                M.showAlert(getActivity(), "Save payment", "Payment saved successfully.",
                        "OK", null, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mSelectedPlan != null) {
                                    PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, mSelectedPlan.id);
                                }
                                Intent home = new Intent(getActivity(), HomeActivity.class);
                                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(home);
                                getActivity().finish();
                            }
                        }, null, false);
            }
        }
    };

    public Plan getSelectedPlan() {
        return mViewModel.getSelectedPlanData().getValue();
    }

    private void showPlanDetails(Plan plan) {
        if (plan == null) return;
        Intent planDetailIntent = new Intent(getActivity(), ViewPlanDetailsActivity.class);
        planDetailIntent.putExtra(FragmentViewPlanDetails.PLAN_ID, plan.id);
        startActivity(planDetailIntent);
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_step_six_select_plan, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLblSelectedPLan.setVisibility(View.GONE);
        mLtSelectedPlanCard.setVisibility(View.GONE);
        mBtnSelectedPlanDetails.setOnClickListener(mShowMoreClickListener);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders
                .of(this, new PlansViewModel.PlansViewmodelFacory(getActivity().getApplication()))
                .get(PlansViewModel.class);

        mViewModel.getPlansLiveData().observe(this, mPlansListObserver);
        mViewModel.getSelectedPlanData().observe(this, mSelectedPlanChangeObserver);
        mViewModel.getStatusMessageData().observe(this, mSaveDataObserver);
    }

    private void bindSelectedPlan(@Nullable Plan plan) {
        if (plan == null) {
            mLblSelectedPLan.setVisibility(View.GONE);
            mLtSelectedPlanCard.setVisibility(View.GONE);
        } else {
            mLblSelectedPLan.setVisibility(View.VISIBLE);
            mLtSelectedPlanCard.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setDataOnResume();
    }

    void setDataOnResume() {
        if (mViewModel.getSelectedPlanData().getValue() != null)
            bindSelectedPlan(mViewModel.getSelectedPlanData().getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void makeBankPayment() {
        if (mSelectedPlan == null) {
            M.showAlert(getActivity(), "Select Plan", "Please select a plan", "OK", null, null, null, false);
            return;
        }
        Intent planPurchaseIntent = new Intent(getActivity(), PaymentActivity.class);
        planPurchaseIntent.putExtra(PaymentActivity.PAYMENT_TYPE, PaymentActivity.PAYMENT_TYPE_PLAN);
        planPurchaseIntent.putExtra(PaymentActivity.PLAIN_ID, mSelectedPlan.id);
        planPurchaseIntent.putExtra(PaymentActivity.STRIPE_PLAN, mSelectedPlan.stripePlan);
        planPurchaseIntent.putExtra(PaymentActivity.PLAN_NAME, mSelectedPlan.planName);
        startActivity(planPurchaseIntent);
    }

    @Override
    public void updateStatus(HashMap<String, String> data) {
        data.put("planId", mSelectedPlan.id);
        if (mViewModel == null)
            mViewModel = ViewModelProviders
                    .of(this, new PlansViewModel.PlansViewmodelFacory(getActivity().getApplication()))
                    .get(PlansViewModel.class);
        mViewModel.savePaypalData(data);
    }
}
