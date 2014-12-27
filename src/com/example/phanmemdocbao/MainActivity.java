package com.example.phanmemdocbao;
import Utilities.UnCaughtException;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
ActionBar.TabListener {
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Tin mới", "Danh sách báo", "Chuyên mục"};
	ArrayAdapter<String> mSpinnerAdapter;
	NavigationListener mNavigationListener = new NavigationListener();
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_layout);
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), this);
		
		
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	    Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(MainActivity.this));
	}
	public void setup(){
		getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.action_addwebsite:
			Intent intent = new Intent(MainActivity.this,AddWebsiteActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_myWebsite:
			Intent intent2 = new Intent(MainActivity.this,ListWebsiteActivity.class);
			startActivity(intent2);
			return true;
		case R.id.action_mycategories:
			Intent intent3 = new Intent(MainActivity.this,ContentActivity.class);
			startActivity(intent3);
			return true;
		case R.id.action_websiteManager:
			Intent intent1 = new Intent(MainActivity.this,RSSItemListActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("flag", 2);
			intent1.putExtra("rssBundle", bundle);
			startActivity(intent1);
			return true;
		case R.id.action_settings:
			Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		//return false;
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}
	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	private class NavigationListener implements ActionBar.OnNavigationListener{
		@Override
		public boolean onNavigationItemSelected(int position, long itemId) {
			return false;
			
		}
	}
	
}
