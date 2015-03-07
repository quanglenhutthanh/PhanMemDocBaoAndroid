package com.example.phanmemdocbao;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	Context mContext;
	public TabsPagerAdapter(FragmentManager fm,Context context) {
		super(fm);
		mContext = context;
		
	}

	@Override
	public Fragment getItem(int index) {
		//return Fragment.instantiate(mContext, fragments.get(index));
		switch (index) {
		case 0:
			// Top Rated fragment activity
			//Fragment fr = (Fragment)RSSItemListFragment();
			//return new TopRatedFragment();
			//return new WebsiteGridFragment();
			return new RSSItemListFragment();
		case 1:
			// Games fragment activity
			return new WebsiteGridFragment();
		case 2:
			// Movies fragment activity
			//return new mListFragment();
			return new CategoryListFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
