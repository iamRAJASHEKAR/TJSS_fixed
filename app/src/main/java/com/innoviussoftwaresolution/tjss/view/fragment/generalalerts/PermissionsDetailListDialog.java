package com.innoviussoftwaresolution.tjss.view.fragment.generalalerts;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sony Raj on 05-08-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class PermissionsDetailListDialog extends AppCompatDialogFragment {

    public static final String TAG = "PermissionsLIstDialog";
    private static final String PERMISSIONS = "permissions";
    private ArrayList<String> permissions;
    private PermissionsDialogCallback mCallback;

    /**
     * List of permissions details
     * <p>Construct the string like this, "Heading-Details"</p>
     *
     * @param permissionDetails array chatItems of permission details
     * @return a new instance of {@link PermissionsDetailListDialog}
     */
    public static PermissionsDetailListDialog newInstance(ArrayList<String> permissionDetails) {
        Bundle args = new Bundle();
        args.putStringArrayList(PERMISSIONS, permissionDetails);
        PermissionsDetailListDialog fragment = new PermissionsDetailListDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCallback(PermissionsDialogCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        permissions = getArguments().getStringArrayList(PERMISSIONS);
        if (permissions == null)
            throw new IllegalArgumentException("Permissions chatItems should not be null");
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setAdapter(new PermissionsListAdapter(permissions));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Request for permission")
                .setCancelable(false)
                .setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.onPermissionGranted();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();

        dialog.setView(recyclerView);
        return dialog;
    }

    public interface PermissionsDialogCallback {
        void onPermissionGranted();
    }

    class PermissionsListAdapter extends RecyclerView.Adapter<PermissionsListAdapter.PermissionViewHolder> {

        private final ArrayList<String> mPermissionsList;

        private PermissionsListAdapter(ArrayList<String> mPermissionsList) {
            this.mPermissionsList = mPermissionsList;
        }

        @Override
        public PermissionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PermissionViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.extra_permission_request_item, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(PermissionViewHolder holder, int position) {
            String[] parts = mPermissionsList.get(position).split("-");
            if (parts.length < 2)
                throw new IllegalArgumentException("Please see the documentation about " +
                        "how the strings should be constructed to pass into this dialog");

            holder.mLblHeading.setText(parts[0]);
            holder.mLblDescription.setText(parts[1]);
        }

        @Override
        public int getItemCount() {
            return mPermissionsList.size();
        }

        class PermissionViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.lblHeading)
            AppCompatTextView mLblHeading;
            @BindView(R.id.lblDescription)
            AppCompatTextView mLblDescription;

            PermissionViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }


}
