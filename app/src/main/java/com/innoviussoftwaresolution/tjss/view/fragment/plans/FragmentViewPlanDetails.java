package com.innoviussoftwaresolution.tjss.view.fragment.plans;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.PlanDetails;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.PlanDetailsViewModel;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 24-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FragmentViewPlanDetails extends BaseFragment
{

    public static final String PLAN_ID = "planId";
    @BindView(R.id.lblPlanName)
    AppCompatTextView mLblPlanName;

    @BindView(R.id.lblPlanAmount)
    AppCompatTextView mLblPlanAmount;

    @BindView(R.id.lblNumberOfSafetyCirclesHeading)
    AppCompatTextView mLblNumberOfSafetyCirclesHeading;

    @BindView(R.id.lblNumberOfSafetyCircles)
    AppCompatTextView mLblNumberOfSafetyCircles;

    @BindView(R.id.lblLocationHistoryDurationHeading)
    AppCompatTextView mLblLocationHistoryDurationHeading;

    @BindView(R.id.lblLocationHistoryDuration)
    AppCompatTextView mLblLocationHistoryDuration;

    @BindView(R.id.lblFavPlacesCountHeading)
    AppCompatTextView mLblFavPlacesCountHeading;

    @BindView(R.id.lblFavPlacesCount)
    AppCompatTextView mLblFavPlacesCount;

    @BindView(R.id.lblMinPointsRequiredHeading)
    AppCompatTextView mLblMinPointsRequiredHeading;

    @BindView(R.id.lblMinPointsRequired)
    AppCompatTextView mLblMinPointsRequired;

    @BindView(R.id.lblSafetyTipNameHeading)
    AppCompatTextView mLblSafetyTipNameHeading;

    @BindView(R.id.lblSafetyTipName)
    AppCompatTextView mLblSafetyTipName;

    @BindView(R.id.lblHelpAlertsHeading)
    AppCompatTextView mLblHelpAlertsHeading;

    @BindView(R.id.lblHelpAlerts)
    AppCompatTextView mLblHelpAlerts;

    @BindView(R.id.lblEmergencyCallOutRequestHeading)
    AppCompatTextView mLblEmergencyCallOutRequestHeading;

    @BindView(R.id.lblEmergencyCallOutRequest)
    AppCompatTextView mLblEmergencyCallOutRequest;

    @BindView(R.id.lblCheckinsHeading)
    AppCompatTextView mLblCheckinsHeading;

    @BindView(R.id.lblCheckins)
    AppCompatTextView mLblCheckins;

    @BindView(R.id.lblSecureMessagingHeading)
    AppCompatTextView mLblSecureMessagingHeading;

    @BindView(R.id.lblSecureMessaging)
    AppCompatTextView mLblSecureMessaging;

    @BindView(R.id.lblIncidentAlertNotificationsHeading)
    AppCompatTextView mLblIncidentAlertNotificationsHeading;

    @BindView(R.id.lblIncidentAlertNotifications)
    AppCompatTextView mLblIncidentAlertNotifications;

    @BindView(R.id.lblSafetyPalHeading)
    AppCompatTextView mLblSafetyPalHeading;

    @BindView(R.id.lblSafetyPal)
    AppCompatTextView mLblSafetyPal;

    @BindView(R.id.lblMemberCountHeading)
    AppCompatTextView mLblMemberCountHeading;

    @BindView(R.id.lblMemberCount)
    AppCompatTextView mLblMemberCount;

    Unbinder unbinder;


    PlanDetailsViewModel viewModel;

    private Observer<PlanDetails> mPlanDetailsObserver
            = new Observer<PlanDetails>()
    {
        @Override
        public void onChanged(@Nullable PlanDetails planDetails) {
            dismissDialog();
            if (planDetails == null) {
                M.showToast(getActivity(), "Something went wrong, please try again");
            } else {
                mLblPlanName.setText(planDetails.planName);
                mLblPlanAmount.setText(MessageFormat.format("{0} $", planDetails.planPrice));

                if (!TextUtils.isEmpty(planDetails.noOfSafetyCircles)) {
                    mLblNumberOfSafetyCircles.setText(planDetails.noOfSafetyCircles);
                } else {
                    hide(mLblNumberOfSafetyCircles, mLblNumberOfSafetyCirclesHeading);
                }

                if (!TextUtils.isEmpty(planDetails.locationHistoryDuration)) {
                    mLblLocationHistoryDuration.setText(planDetails.locationHistoryDuration);
                } else {
                    hide(mLblLocationHistoryDuration, mLblLocationHistoryDurationHeading);
                }

                if (!TextUtils.isEmpty(planDetails.favoritePlaces)) {
                    mLblFavPlacesCount.setText(planDetails.favoritePlaces);
                } else {
                    hide(mLblFavPlacesCount, mLblFavPlacesCountHeading);
                }

                if (!TextUtils.isEmpty(planDetails.minPointsRequired)) {
                    mLblMinPointsRequired.setText(planDetails.minPointsRequired);
                } else {
                    hide(mLblMinPointsRequired, mLblMinPointsRequiredHeading);
                }

                if (!TextUtils.isEmpty(planDetails.safetytipName)) {
                    mLblSafetyTipName.setText(planDetails.safetytipName);
                } else {
                    hide(mLblSafetyTipName, mLblSafetyTipNameHeading);
                }

                if (!TextUtils.isEmpty(planDetails.helpAlerts)) {
                    mLblHelpAlerts.setText(planDetails.helpAlerts);
                } else {
                    hide(mLblHelpAlerts, mLblHelpAlertsHeading);
                }

                if (!TextUtils.isEmpty(planDetails.emergencyCallOutRequest)) {
                    mLblEmergencyCallOutRequest.setText(planDetails.emergencyCallOutRequest);
                } else {
                    hide(mLblEmergencyCallOutRequest, mLblEmergencyCallOutRequestHeading);
                }

                if (!TextUtils.isEmpty(planDetails.checkIn)) {
                    mLblCheckins.setText(planDetails.checkIn);
                } else {
                    hide(mLblCheckins, mLblCheckinsHeading);
                }

                if (!TextUtils.isEmpty(planDetails.secureMessaging)) {
                    mLblSecureMessaging.setText(planDetails.secureMessaging);
                } else {
                    hide(mLblSecureMessaging, mLblSecureMessagingHeading);
                }

                if (!TextUtils.isEmpty(planDetails.incidentAlertNotification)) {
                    mLblIncidentAlertNotifications.setText(planDetails.incidentAlertNotification);
                } else {
                    hide(mLblIncidentAlertNotifications, mLblIncidentAlertNotificationsHeading);
                }

                if (!TextUtils.isEmpty(planDetails.safetyPal)) {
                    mLblSafetyPal.setText(planDetails.safetyPal);
                } else {
                    hide(mLblSafetyPal, mLblSafetyPalHeading);
                }

                if (!TextUtils.isEmpty(planDetails.memberCount)) {
                    mLblMemberCount.setText(planDetails.memberCount);
                } else {
                    hide(mLblMemberCount, mLblMemberCountHeading);
                }

            }
        }
    };


    private String planId;

    public static FragmentViewPlanDetails newInstance(String planId) {

        Bundle args = new Bundle();
        args.putString(PLAN_ID, planId);
        FragmentViewPlanDetails fragment = new FragmentViewPlanDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        planId = getArguments().getString(PLAN_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_plan_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this,
                new PlanDetailsViewModel.PlanDetailsViewModelFactory(getActivity().getApplication()))
                .get(PlanDetailsViewModel.class);

        viewModel.getPlansData().observe(this, mPlanDetailsObserver);
    }

    @Override
    public void onResume() {
        super.onResume();

        initProgress();
        viewModel.getPlanDetails(planId);
    }

    private void hide(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
