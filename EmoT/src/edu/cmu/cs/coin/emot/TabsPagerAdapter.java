package edu.cmu.cs.coin.emot;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new FragmentHomeTab();
		case 1:
			return new FragmentPatternTab();
		}
		
		return null;
	}
	
	@Override
	public int getCount() {
		// of items, or tabs
		return 2;
	}
}
