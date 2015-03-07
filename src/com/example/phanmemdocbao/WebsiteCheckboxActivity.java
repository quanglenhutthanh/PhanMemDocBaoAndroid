package com.example.phanmemdocbao;

import java.util.ArrayList;
import java.util.List;
import Adapter.FeedCheckBoxAdapter;
import DataHelper.FeedDataAdapter;
import Entities.RSSFeed;
import Utilities.UnCaughtException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WebsiteCheckboxActivity extends Activity{
	List<RSSFeed> feeds;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_checkbox_layout);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(WebsiteCheckboxActivity.this));
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("categoriesId");
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids = bundle.getIntegerArrayList("categoriesId");
		for(int i:ids){
			Log.d("id",String.valueOf(i));
		}
		
		FeedDataAdapter feedDataAdaper = new FeedDataAdapter(WebsiteCheckboxActivity.this);
		feedDataAdaper.open();
		
		feeds = new ArrayList<RSSFeed>();
		for(int i:ids){
			List<RSSFeed> tmp_feed = new ArrayList<RSSFeed>();
			tmp_feed = feedDataAdaper.GetFeedsByCategoryId(i);
			for(RSSFeed f:tmp_feed){
				feeds.add(f);
			}
		}
		ListView list = (ListView)findViewById(R.id.listView1);
		TextView textview = (TextView)findViewById(R.id.list_item_subtext);
		textview.setText("Chọn báo bạn muốn đọc");
		FeedCheckBoxAdapter adapter = new FeedCheckBoxAdapter(WebsiteCheckboxActivity.this, feeds);
		list.setAdapter(adapter);
		
		Button btn = (Button)findViewById(R.id.add_content_layout_btnLuu);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int count = 0;
				ArrayList<Integer> ids = new ArrayList<Integer>();
				for(RSSFeed f:feeds){
					if(f.isChecked()){
						ids.add(f.get_id());
						count++;
					}
				}
				if(count == 0){
					Toast.makeText(WebsiteCheckboxActivity.this, "Chọn báo", Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent(WebsiteCheckboxActivity.this,AddContentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putIntegerArrayList("feedsId", ids);
				intent.putExtra("feedsId", bundle);
				startActivity(intent);
				WebsiteCheckboxActivity.this.finish();
				//Toast.makeText(CategoryCheckboxActivity.this, checked, Toast.LENGTH_LONG).show();
			}
		});
	}
}
