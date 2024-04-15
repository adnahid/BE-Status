package com.delower.bestatus;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class pageAdapter extends FragmentPagerAdapter {


    private int tabsNumber;
    public pageAdapter(@NonNull FragmentManager fm, int behavior,int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new FragmentLayout();
            case 1:
                return new FragmentEng();

            default: return null;
        }



    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
