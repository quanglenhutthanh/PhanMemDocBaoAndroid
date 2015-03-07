package com.example.phanmemdocbao;
import Utilities.UnCaughtException;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ListWebsiteActivity extends Activity{
	//private final Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_manager_layout);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(ListWebsiteActivity.this));
		
		TextView header = (TextView)findViewById(R.id.list_website_layout_header);
		ImageButton btnAddWebsite = (ImageButton)findViewById(R.id.list_website_layout_button);
		btnAddWebsite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListWebsiteActivity.this, AddWebsiteActivity.class);
				startActivity(intent);
				ListWebsiteActivity.this.finish();
			}
		});
        header.setText("Danh sách báo bạn đã thêm");
		showList();
		
		
	}
	public void showList(){
		WebsiteListFragment mListFragment = new WebsiteListFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.list_manager_layout_listview, mListFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}
	
	
}
