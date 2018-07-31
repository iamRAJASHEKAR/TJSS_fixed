package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * @author Sony Raj on 03-10-17.
 */
public class CreateFriendChatViewModel extends CreateChatViewModelBase {

    private CreateFriendChatViewModel(Application application) {
        super(application);
    }


    @SuppressWarnings("unchecked")
    public static class CreateFriendChatViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public CreateFriendChatViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CreateFriendChatViewModel(mApplication);
        }
    }


}