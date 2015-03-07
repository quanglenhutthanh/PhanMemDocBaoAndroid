package com.example.phanmemdocbao;

import java.util.ArrayList;
import java.util.List;
import DataHelper.ContentDataAdapter;
import DataHelper.FeedDataAdapter;
import Entities.Content;
import Entities.RSSFeed;
import Utilities.UnCaughtException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddContentActivity extends Activity {
	List<RSSFeed> feeds;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(AddContentActivity.this));
		setContentView(R.layout.add_content_layout);
		Button btnLuu = (Button)findViewById(R.id.add_content_layout_btnLuu);
		final TextView txtName = (TextView)findViewById(R.id.add_content_layout_name);
		final TextView txtDesc = (TextView)findViewById(R.id.add_content_layout_desc);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("feedsId");
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids = bundle.getIntegerArrayList("feedsId");
		
		FeedDataAdapter feedDataAdaper = new FeedDataAdapter(AddContentActivity.this);
		feedDataAdaper.open();
		feeds = new ArrayList<RSSFeed>();
		
		for(int id:ids){
			feeds.add(feedDataAdaper.GetFeedById(id));
		}
		btnLuu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ContentDataAdapter contentDataAdapter = new ContentDataAdapter(AddContentActivity.this);
				contentDataAdapter.open();
				Content content = new Content(-1, txtName.getText().toString(), txtDesc.getText().toString(), feeds);
				//Category category = new Category(-1, txtName.getText().toString(), txtDesc.getText().toString(),feeds);
				if(contentDataAdapter.Insert(content)){
					Intent intent = new Intent(AddContentActivity.this,ContentActivity.class);
					startActivity(intent);
					AddContentActivity.this.finish();
				}
				
			}
		});
	}

}
