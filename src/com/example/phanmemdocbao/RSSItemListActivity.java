package com.example.phanmemdocbao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Adapter.RssItemAdapter;
import DataHelper.FeedDataAdapter;
import DataHelper.RSSItemDataAdapter;
import DataHelper.WebsiteDataAdapter;
import Entities.RSSFeed;
import Entities.RSSItem;
import Utilities.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RSSItemListActivity extends Activity {
	// XML node keys
	int flag = 1;
	String title;
	String icon;
	ProgressDialog pDialog;
	ListView list;
	RssItemAdapter adapter;
	String xml;
	List<RSSItem> newsList;
	List<RSSFeed> feedList;
	ArrayList<Integer> ids;
	boolean firsttime = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(
				RSSItemListActivity.this));

		newsList = new ArrayList<RSSItem>();
		feedList = new ArrayList<RSSFeed>();
		if (!networkUtility.isOnline(this)) {
			Intent intent = new Intent(RSSItemListActivity.this,
					NetworkActivity.class);
			startActivity(intent);
			this.finish();
			return;
		}
		setContentView(R.layout.layout_main);
		list = (ListView) findViewById(R.id.listView1);

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("rssBundle");
		flag = bundle.getInt("flag");
		title = bundle.getString("title");
		icon = bundle.getString("icon");
		if (icon != null) {
			Resources res = getResources();
			int resID = res.getIdentifier(icon, "drawable", getPackageName());
			Drawable drawable = res.getDrawable(resID);
			getActionBar().setIcon(drawable);
		}
		setTitle(" " + title);
		if (flag == 2) {
			setTitle("Các tin đã lưu");
			new LoadNews(100).execute();
		}
		if (flag == 1) {
			ids = bundle.getIntegerArrayList("ids");
			FeedDataAdapter feedDataAdapter = new FeedDataAdapter(this);
			feedDataAdapter.open();
			WebsiteDataAdapter websiteDataAdapter = new WebsiteDataAdapter(this);
			websiteDataAdapter.open();

			for (int id : ids) {
				feedList.add(feedDataAdapter.GetFeedById(id));
			}
			new LoadNews(20).execute();
			firsttime = false;
			//for (int i = 0; i < feedList.size(); i++) {
			//}
		}
	}

	public boolean isOnline() {
		ConnectivityManager connectManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	class LoadNews extends AsyncTask<String, String, String> {
		int limit = 20;

		public LoadNews(int limit) {
			this.limit = limit;
		}

		// Before starting background thread Show Progress Dialog
		protected void onPreExecute() {
			if (firsttime) {
				pDialog = new ProgressDialog(RSSItemListActivity.this);
				pDialog.setMessage("Đang tải...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.setCanceledOnTouchOutside(false);
				pDialog.show();
			}
		}

		protected String doInBackground(String... args) {
			// updating UI from Background Thread
			try {
				SetupListView(limit);

			} catch (Exception ex) {
				// Log.d("error",ex.getMessage());
				RSSItemListActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// Toast.makeText(RSSItemListActivity.this,
						// "can not get data, please check your network",Toast.LENGTH_LONG).show();
					}
				});
			}
			return null;
		}

		// After completing background task Dismiss the progress dialog
		protected void onPostExecute(String args) {
			// dismiss the dialog after getting all products
			if (pDialog != null) {
				pDialog.dismiss();
			}
			try {
				adapter.notifyDataSetInvalidated();
				list.setAdapter(null);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(RSSItemListActivity.this,
								ArticleView.class);
						Bundle bundle = new Bundle();
						bundle.putString("siteName", newsList.get(position)
								.getWebsite().getName());
						bundle.putString("title", newsList.get(position)
								.getTitle());
						bundle.putString("image", newsList.get(position)
								.getImage());
						bundle.putString("pubDate", newsList.get(position)
								.getFormatPubdate());
						bundle.putString("image", newsList.get(position)
								.getImage());
						bundle.putString("link", newsList.get(position)
								.getLink());
						bundle.putString("className", newsList.get(position)
								.getWebsite().getClassName());
						bundle.putInt("isDefault", newsList.get(position)
								.getWebsite().getIsDefault());
						bundle.putInt("websiteId", newsList.get(position)
								.getWebsite().getId());
						bundle.putInt("flag", flag);
						// Log.d("websiteId",String.valueOf(newsList.get(position).getWebsite().getId()));
						bundle.putString("websiteIcon", newsList.get(position)
								.getWebsite().getIcon());
						bundle.putString("desc", newsList.get(position)
								.getDescription());
						intent.putExtra("article", bundle);
						startActivity(intent);
					}
				});

			} catch (Exception ex) {
				// Log.d("error",ex.getMessage());

			}
		}

	}

	public boolean checkItem(List<RSSItem> list, RSSItem item) {
		for (RSSItem i : list) {
			if (i.getTitle() == item.getTitle())
				return false;
		}
		return true;
	}

	public void SetupListView(int limit) {
		try {
			if (flag == 2) {
				RSSItemDataAdapter rssItemDataAdapter = new RSSItemDataAdapter(
						RSSItemListActivity.this);
				rssItemDataAdapter.open();
				newsList = rssItemDataAdapter.GetAllList();
				adapter = new RssItemAdapter(RSSItemListActivity.this,
						newsList, 2);
			}
			if (flag == 1) {
				RSSParser parser = new RSSParser();
				WebsiteDataAdapter website = new WebsiteDataAdapter(this);
				website.open();
				int count = 0;
				for (RSSFeed feed : feedList) {
					Log.d("FEED", feed.getLink());
					List<RSSItem> itemList = new ArrayList<RSSItem>();
					xml = parser.getXmlFromUrl(feed.getLink()); // getting XML
																// from URL
					//Log.d("Xml",xml);
					XmlPullFeedParser pa = new XmlPullFeedParser(xml,
							feed.getWebsite());
					try {
						itemList = pa.parse();// parse xml into RSSItemlist
					} catch (Exception ex) {
						break;
					}
					for (final RSSItem item : itemList) {
						Log.d("ADD", feed.getLink());
						newsList.add(item);
					}

					/*if (count > limit) {
						Log.d("break", feed.getLink());
						break;
					}*/
				}
				
				Set<RSSItem> set = new HashSet<RSSItem>(newsList);
				List<RSSItem> list = new ArrayList<RSSItem>(set);
				Collections.sort(list);
				adapter = new RssItemAdapter(RSSItemListActivity.this, list, 1);
				newsList = list;
				feedList.remove(0);
				Log.d("break", String.valueOf(feedList.size()), null);
			}
			// list=(ListView)findViewById(R.id.listView1);
			// sort list to get newest news
			// Collections.sort(newsList);
			// adapter=new RssItemAdapter(this, newsList,flag);
		} catch (Exception ex) {
			// Log.d("error",ex.getMessage());
			RSSItemListActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(RSSItemListActivity.this,
					// "can not get data, please check your network",Toast.LENGTH_LONG).show();
				}
			});
		}
	}

}
