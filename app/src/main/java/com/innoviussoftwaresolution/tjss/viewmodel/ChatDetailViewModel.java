package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoviussoftwaresolution.tjss.model.internal.Chat;
import com.innoviussoftwaresolution.tjss.utils.M;

/**
 * @author Sony Raj on 03-10-17.
 */
public class ChatDetailViewModel extends ViewModelBase implements LifecycleObserver {

    private final ObjectMapper mObjectMapper = new ObjectMapper();
    private final MutableLiveData<Chat> mOldMessages
            = new MutableLiveData<>();


    private String mGroupId = null;

    private ChatDetailViewModel(Application application) {
        super(application);

    }

    public void setMessages(String oldMessages) {
        try {
            if (oldMessages == null || oldMessages.equals("")) return;
            Chat chat = mObjectMapper.readValue(oldMessages, Chat.class);
            mGroupId = chat.groupId;
            mOldMessages.setValue(chat);
        } catch (Exception e) {
            M.log(e.getMessage());
        }
    }


    public String getGroupId() {
        return mGroupId;
    }

    public MutableLiveData<Chat> getOldMessagesData() {
        return mOldMessages;
    }

    public ObjectMapper getObjectMapper() {
        return mObjectMapper;
    }


    @SuppressWarnings("unchecked")
    public static class ChatDetailViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public ChatDetailViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ChatDetailViewModel(mApplication);
        }
    }


}