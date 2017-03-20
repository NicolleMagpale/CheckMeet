package com.example.checkmeet.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.checkmeet.view.ViewAllContactsFragment;
import com.example.checkmeet.view.ViewAllGroupsFragment;
import com.example.checkmeet.view.ViewContactsBaseFragment;
import com.example.checkmeet.view.ViewContactsBaseType;

/**
 * Created by Hazel on 19/03/2017.
 */

public class ContactsPagerAdapter extends FragmentPagerAdapter {

    private String[] tab_titles = {"Contacts", "Groups"};

    public ContactsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public ViewContactsBaseType getItem(int position) {

        switch (position) {
            case 0:
                return new ViewAllContactsFragment();
            default:
                return new ViewAllGroupsFragment();
        }
    }

    @Override
    public int getCount() {
        return tab_titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_titles[position];
    }
}
