package com.example.phanmemdocbao;
import DataHelper.FeedDataAdapter;
import DataHelper.WebsiteDataAdapter;
import Entities.RSSFeed;
import Entities.Website;
import Utilities.RSSParser;
import Utilities.UnCaughtException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class AddWebsiteActivity extends Activity {
	private EditText editTextWebsiteLink;
	private EditText editTextRssLink;
	private EditText editTextWebsiteName;
	private String websiteLink;
	private String rssLink="";
	private ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_website_layout);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(AddWebsiteActivity.this));
		
		editTextWebsiteLink = (EditText)findViewById(R.id.editTextWebsiteLink);
		editTextRssLink = (EditText)findViewById(R.id.editTextRssLink);
		editTextWebsiteName = (EditText)findViewById(R.id.editTextWebsiteName);
		Button btnSave = (Button)findViewById(R.id.btnSave);
		ImageButton btnSearchRssLink = (ImageButton)findViewById(R.id.btnSearchRssLink);
		btnSearchRssLink.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				websiteLink = editTextWebsiteLink.getText().toString();
				new GetRssLink().execute();
			}
		});
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Website website = new Website(-1,editTextWebsiteName.getText().toString(),
						editTextWebsiteLink.getText()+"/favicon.ico",editTextWebsiteLink.getText().toString(),"","",0);
				WebsiteDataAdapter websiteDataAdapter = new WebsiteDataAdapter(AddWebsiteActivity.this);
				websiteDataAdapter.open();
				FeedDataAdapter feedAdapter  = new FeedDataAdapter(AddWebsiteActivity.this);
				feedAdapter.open();
				if(websiteDataAdapter.Insert(website)){
					int maxId = websiteDataAdapter.getMaxID();
					website.setId(maxId);
					RSSFeed feed = new RSSFeed(-1,website,"",editTextRssLink.getText().toString());
					if(feedAdapter.Insert(feed)){
						Toast.makeText(AddWebsiteActivity.this, "Thêm website thành công", Toast.LENGTH_LONG).show();
					}
				}
				else{
					Toast.makeText(AddWebsiteActivity.this, "Thêm website thất bại", Toast.LENGTH_LONG).show();
				}
				Intent intent  = new Intent(AddWebsiteActivity.this,ListWebsiteActivity.class);
				startActivity(intent);
				AddWebsiteActivity.this.finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	class GetRssLink extends AsyncTask<String, String, String> {
		 protected void onPreExecute() {
	        	pDialog = new ProgressDialog(AddWebsiteActivity.this);
	            pDialog.setMessage("�?ang tìm...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
		    RSSParser parser = new RSSParser();
			rssLink = parser.getRSSLinkFromURL(websiteLink);
			//Toast.makeText(AddWebsiteActivity.this, link, Toast.LENGTH_LONG).show();
			return null;
		}
		protected void onPostExecute(String args){
			if(rssLink == ""){
				Toast.makeText(AddWebsiteActivity.this, "Không tìm thấy địa chỉ RSS", Toast.LENGTH_LONG).show();
			}
			editTextRssLink.setText(rssLink);
			//Toast.makeText(AddWebsiteActivity.this, rssLink, Toast.LENGTH_LONG).show();
			pDialog.dismiss();
		}
	}
    
}
