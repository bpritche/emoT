package edu.cmu.cs.coin.emot;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Home", "Patterns"};
	
	//Action bar stuff
	//This tab will tell you your emotions during a particular event                  
	ActionBar.Tab homeTab, patternTab;
	Fragment fragmentPatternTab = new FragmentPatternTab();
	Fragment fragmentHomeTab = new FragmentHomeTab();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Create a new action bar and a tab for event info
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(mAdapter);
	
	// Adding Tabs
    for (String tab_name : tabs) {
        actionBar.addTab(actionBar.newTab().setText(tab_name)
                .setTabListener(this));
        }
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		//On tab selected
		//show its respective fragment view
		Log.d("Debug", "Tab selected");
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
}