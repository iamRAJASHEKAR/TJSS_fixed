package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * @author Sony Raj on 19-09-17.
 */

/**
 * @author Sony Raj on 02-10-17.
 */
public class CreateGroupChatViewModel extends CreateChatViewModelBase {

    private CreateGroupChatViewModel(Application application) {
        super(application);
    }


    @SuppressWarnings("unchecked")
    public static class CreateGroupChatViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public CreateGroupChatViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CreateGroupChatViewModel(mApplication);
        }
    }


}