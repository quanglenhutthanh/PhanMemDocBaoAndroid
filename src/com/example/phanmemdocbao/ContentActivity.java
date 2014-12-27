package com.example.phanmemdocbao;

import Utilities.UnCaughtException;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ContentActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_manager_layout);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(ContentActivity.this));
		
		ImageButton btnAdd = (ImageButton)findViewById(R.id.list_website_layout_button);
	    TextView textViewHeader = (TextView)findViewById(R.id.list_website_layout_header);
	    textViewHeader.setText("Chuyên mục bạn đã tạo");
		btnAdd.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContentActivity.this, CategoryCheckboxActivity.class);
				startActivity(intent);
				ContentActivity.this.finish();
			}
		});
		showList();
	}
	public void showList(){
		ContentListFragment mListFragment = new ContentListFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.list_manager_layout_listview, mListFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}
}
