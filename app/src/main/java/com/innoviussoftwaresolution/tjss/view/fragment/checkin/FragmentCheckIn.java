package com.innoviussoftwaresolution.tjss.view.fragment.checkin;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.DeleteHistory;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.RecyclerViewTouchListener;
import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.RecyclerviewClickListner;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.CheckInHistory;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircleMember;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.CheckInHistoryAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.CheckInViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Sony Raj on 01-11-17.
 */

public class FragmentCheckIn extends BaseFragment {

    public static final String TAG = "CheckIn";

    @BindView(R.id.lstCheckInHistory)
    RecyclerView mLstCheckInHistory;

    Unbinder unbinder;

    List<CheckInHistory> list;
    CheckInHistoryAdapter adapter;

    boolean isDeleted = false;

    private SafetyCircleMember mCircleMember;


    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        mCircleMember = getArguments().getParcelable("member");
        Bugsnag.init(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_in_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static FragmentCheckIn newInstance(SafetyCircleMember member) {

        Bundle args = new Bundle();
        args.putParcelable("member", member);
        FragmentCheckIn fragment = new FragmentCheckIn();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Checkin History");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckInViewModel viewModel = ViewModelProviders.of(this,
                new CheckInViewModel.CheckInViewModelFactory(getActivity().getApplication()))
                .get(CheckInViewModel.class);

        list = new ArrayList<>();

        viewModel.getHistoryData().observe(this, new Observer<List<CheckInHistory>>() {
            @Override
            public void onChanged(@Nullable List<CheckInHistory> checkInHistories) {
                if (checkInHistories == null) return;
                if (checkInHistories.size() <= 0) {
                    // list = new ArrayList<>();

                    adapter = new CheckInHistoryAdapter(checkInHistories);
                    adapter.setGotNoContent();
                    mLstCheckInHistory.setAdapter(adapter);
                    mLstCheckInHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                    return;
                } else {

                    mLstCheckInHistory.setAdapter(new CheckInHistoryAdapter(checkInHistories));
                    mLstCheckInHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

                    list.addAll(checkInHistories);
//                    for(int i=0; i<checkInHistories.size();i++)
//                    {
//                        list=new ArrayList<>();
//                        list.add(checkInHistories.get(i));
//
//                    }
                }
            }
        });
        viewModel.getHistory(mCircleMember.userId);


        mLstCheckInHistory.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mLstCheckInHistory, new RecyclerviewClickListner() {
            @Override
            public void onClick(View view, int position) {
                String id = list.get(position).id;
                //Toast.makeText(getActivity(),"Check Id===> "+ position, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onLongClick(View view1, final int position) {
                final String id = list.get(position).id;
                // Toast.makeText(getActivity(),"Check Id===> "+ position, Toast.LENGTH_LONG).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete");
                builder.setMessage("Do you want to clear this check in history?");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean delete = deleteChecHistory(id);

                        //mLstCheckInHistory.removeViewAt(position);
                        //adapter.removeItem(position);

                        list.remove(position);
                        CheckInHistoryAdapter newAdapter = new CheckInHistoryAdapter(list);
                        mLstCheckInHistory.setAdapter(newAdapter);


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }


        }));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean deleteChecHistory(final String id) {

        DeleteHistory deleteHistory = new DeleteHistory();
        deleteHistory.setId(id);

        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIconstants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create()).build();
//        EndpointsServices apiInterFace = retrofit.create(EndpointsServices.class);
        Call<CheckInHistory> call = services.deleteCheckIn(deleteHistory);

        call.enqueue(new Callback<CheckInHistory>() {
            @Override
            public void onResponse(Call<CheckInHistory> call, Response<CheckInHistory> response) {
                isDeleted = true;
                M.showToast(getContext(), "History removed");
            }

            @Override
            public void onFailure(Call<CheckInHistory> call, Throwable t) {
                Bugsnag.notify(new RuntimeException(t));
                isDeleted = false;
                M.showToast(getContext(), "Faild to remove history");
            }


        });
        return isDeleted;
    }


}
