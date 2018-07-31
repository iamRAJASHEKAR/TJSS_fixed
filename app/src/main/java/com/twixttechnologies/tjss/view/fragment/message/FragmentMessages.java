package com.twixttechnologies.tjss.view.fragment.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.internal.ChatInfo;
import com.twixttechnologies.tjss.model.internal.ChatList;
import com.twixttechnologies.tjss.model.internal.ChatListItem;
import com.twixttechnologies.tjss.service.ChatConstants;
import com.twixttechnologies.tjss.service.ChatListenerService;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.CreateFriendChatActivity;
import com.twixttechnologies.tjss.view.activity.CreateGroupChatActivity;
import com.twixttechnologies.tjss.view.adapter.listadapter.ChatContactsAdapter;
import com.twixttechnologies.tjss.view.widget.MessagesFab;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 21-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FragmentMessages extends MessageFragmentBase {


    public static final String TAG = "FragmentMessages";
    @BindView(R.id.lblNoMessage)
    AppCompatTextView mLblNoMessage;
    @BindView(R.id.lstChats)
    RecyclerView mLstChats;
    Unbinder unbinder;
    @BindView(R.id.btnMessage)
    MessagesFab mBtnMessage;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        getContacts();
    }

    private void getContacts() {
        Intent intent = new Intent(getActivity(), ChatListenerService.class);
        intent.putExtra(ChatListenerService.EXTRA, ChatListenerService.CONTACTS_ACTION);
        getActivity().startService(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Messages");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        unbinder = ButterKnife.bind(this, view);
        mBtnMessage.setOnGroupClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateGroupChatActivity.class));
            }
        });

        mBtnMessage.setOnFriendClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateFriendChatActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onNewMessage(ChatInfo message) {
        if (message == null) {
            mLblNoMessage.setVisibility(View.VISIBLE);
            mLstChats.setVisibility(View.GONE);
            return;
        }
        if (message.type.equals(ChatConstants.CONTACT_LIST)) {
            String history = message.payload;
            if (history == null || history.equals("")) return;
            try {
                ChatList chatList = new ObjectMapper().readValue(history, ChatList.class);
                if (chatList == null) return;
                List<ChatListItem> chatListItems = chatList.chatItems;
                if (chatListItems == null || chatListItems.size() <= 0) {
                    mLblNoMessage.setVisibility(View.VISIBLE);
                    mLstChats.setVisibility(View.GONE);
                } else {
                    mLblNoMessage.setVisibility(View.GONE);
                    mLstChats.setVisibility(View.VISIBLE);
                }
                mLstChats.setAdapter(new ChatContactsAdapter(chatListItems, getActivity()));
                mLstChats.setLayoutManager(new LinearLayoutManager(getActivity()));
                mLstChats.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            } catch (Exception e) {
                M.log(e.getMessage());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
