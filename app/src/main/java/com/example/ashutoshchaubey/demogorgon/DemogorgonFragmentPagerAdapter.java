package com.example.ashutoshchaubey.demogorgon;

/**
 * Created by ashutoshchaubey on 03/01/18.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ashutoshchaubey on 28/12/17.
 */

public class DemogorgonFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "VISIONS", "CHARACTERS" };
    private Context context;

    public DemogorgonFragmentPagerAdapter(FragmentManager fm, Context context) {

        super(fm);
        this.context=context;

    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new VisionsFragment();
        }else{
            return new CharactersFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}

