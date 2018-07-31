package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;

import com.twixttechnologies.tjss.model.repository.ServiceProviderRepository;

/**
 * @author Sony Raj on 21-10-17.
 */

abstract class ServiceProviderViewModelBase extends ViewModelBase {

    final ServiceProviderRepository mRepository;

    ServiceProviderViewModelBase(Application application) {
        super(application);
        mRepository = new ServiceProviderRepository();
    }


}
