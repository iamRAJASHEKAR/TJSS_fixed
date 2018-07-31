package com.innoviussoftwaresolution.tjss.view.fragment.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.Incident;
import com.innoviussoftwaresolution.tjss.view.activity.IncidentDetailActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.incident.FragmentIncidentDetails;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 19-10-17.
 */

public class IncidentMarkerInfoWindowDialog extends AppCompatDialogFragment {
    public static final String PARTS = "parts";
    @BindView(R.id.lblIncidentDialogTitle)
    AppCompatTextView mLblIncidentDialogTitle;
    @BindView(R.id.lblIncidentDialogDescription)
    AppCompatTextView mLblIncidentDialogDescription;
    Unbinder unbinder;

    private String[] parts;

    public static IncidentMarkerInfoWindowDialog newInstance(String[] parts) {
        Bundle args = new Bundle();
        args.putStringArray(PARTS, parts);
        IncidentMarkerInfoWindowDialog fragment = new IncidentMarkerInfoWindowDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parts = getArguments().getStringArray(PARTS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extra_incidents_alert_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLblIncidentDialogTitle.setText(parts[0]);
        mLblIncidentDialogDescription.setText(parts[1]);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(layoutParams);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnIncidentDialogViewMore)
    public void onViewClicked() {

        Incident incident = new Incident();
        incident.incidentId = parts[3];
        incident.incidentTitle = parts[0];
        incident.incidentDescription = parts[1];
        incident.incidentTime = parts[2];

        Intent intent = new Intent(getActivity(), IncidentDetailActivity.class);
        intent.putExtra(FragmentIncidentDetails.INCIDENT_DETAIL, incident);
        startActivity(intent);
        dismiss();
    }

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

}
