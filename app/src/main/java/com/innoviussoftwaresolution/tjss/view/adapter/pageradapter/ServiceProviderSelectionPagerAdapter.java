package com.innoviussoftwaresolution.tjss.view.adapter.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.serviceprovider.FragmentSelectServiceProvider;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.serviceprovider.FragmentServiceProviderCategories;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.serviceprovider.FragmentServiceProviderSubCategories;

import java.util.ArrayList;

/**
 * @author Sony Raj on 21-10-17.
 */

public class ServiceProviderSelectionPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> mFragments = new ArrayList<>(3);


    public ServiceProviderSelectionPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new FragmentServiceProviderCategories());
        mFragments.add(new FragmentServiceProviderSubCategories());
        mFragments.add(new FragmentSelectServiceProvider());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
