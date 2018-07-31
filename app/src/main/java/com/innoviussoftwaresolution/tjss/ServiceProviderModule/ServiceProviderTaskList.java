package com.innoviussoftwaresolution.tjss.ServiceProviderModule;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.RecyclerViewTouchListener;
import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.RecyclerviewClickListner;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.CheckInHistoryAdapter;

import java.util.ArrayList;


public class ServiceProviderTaskList extends Fragment {


    private RecyclerView rvServiceProviderTaskList;
    private ArrayList<ServiceProviderTaskModel> taskLists;
    private ServiceProviderTaskModel taskModel;
    private ServiceProviderTaskListAdapter adapter;

    public ServiceProviderTaskList() {
        // Required empty public constructor
    }


    public static ServiceProviderTaskList newInstance(String param1, String param2) {
        ServiceProviderTaskList fragment = new ServiceProviderTaskList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskLists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            taskModel = new ServiceProviderTaskModel();
            taskModel.setTask("Task " + String.valueOf(i+1));
            taskLists.add(taskModel);
        }
        adapter = new ServiceProviderTaskListAdapter(getContext(), taskLists);
        rvServiceProviderTaskList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvServiceProviderTaskList.setAdapter(adapter);

//        rvServiceProviderTaskList.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), rvServiceProviderTaskList, new RecyclerviewClickListner() {
//            @Override
//            public void onClick(View view, int position) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Task");
//                builder.setMessage("Do you want to mark this task as DONE?");
//
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        M.showToast(getContext(),"Done");
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_provider_task_list, container, false);
        rvServiceProviderTaskList = (RecyclerView) view.findViewById(R.id.rvServiceProviderTaskList);
        return view;
    }

}
