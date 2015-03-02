package com.example.phanmemdocbao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Adapter.RssItemAdapter;
import DataHelper.FeedDataAdapter;
import DataHelper.WebsiteDataAdapter;
import Entities.RSSFeed;
import Entities.RSSItem;
import Utilities.RSSParser;
import Utilities.XmlPullFeedParser;
import Utilities.networkUtility;

import android.content.Intent;



import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.ListFragment;

public class RSSItemListFragment extends ListFragment {
	ListView list;
	RssItemAdapter adapter;
	String xml;
	List<RSSItem> newsList;
	LoadNews mAsyncTask;
	List<RSSFeed> feeds;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FeedDataAdapter feedAdapter = new FeedDataAdapter(getActivity());
		feedAdapter.open();
		feeds = new ArrayList<RSSFeed>();
		feeds = feedAdapter.GetFeatureFeed();
		newsList = new ArrayList<RSSItem>();
		if (!networkUtility.isOnline(getActivity())) {
			Intent intent = new Intent(getActivity(), NetworkActivity.class);
			startActivity(intent);
			getActivity().finish();
			return;
		}
		if (!networkUtility.isConnectedFast(getActivity())) {
			Toast.makeText(getActivity(), "Mạng chậm", Toast.LENGTH_LONG)
					.show();
		}
		// for(int i=0; i<feeds.size();i++){
		for (RSSFeed u : feeds) {
			mAsyncTask = new LoadNews(7,u);
			mAsyncTask.execute();
		}
		
		// }
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (mAsyncTask != null) {
			// getActivity().f
			// mAsyncTask.cancel(true);
			// Toast.makeText(getActivity(),
			// String.valueOf(position),Toast.LENGTH_LONG).show();
		}
		Intent intent = new Intent(getActivity(), ArticleView.class);
		Bundle bundle = new Bundle();
		bundle.putString("title", newsList.get(position).getTitle());
		bundle.putString("pubDate", newsList.get(position).getFormatPubdate());
		bundle.putString("image", newsList.get(position).getImage());
		bundle.putString("link", newsList.get(position).getLink());
		bundle.putString("desc", newsList.get(position).getDescription());
		bundle.putInt("isDefault", newsList.get(position).getWebsite()
				.getIsDefault());
		bundle.putString("siteName", newsList.get(position).getWebsite()
				.getName());
		bundle.putString("className", newsList.get(position).getWebsite()
				.getClassName());
		bundle.putString("websiteIcon", newsList.get(position).getWebsite()
				.getIcon());
		bundle.putInt("websiteId", newsList.get(position).getWebsite().getId());
		bundle.putInt("flag", 1);
		intent.putExtra("article", bundle);
		startActivity(intent);
	}

	class LoadNews extends AsyncTask<String, String, String> {
		int limit = 5;
		RSSFeed rssFeed;
		public LoadNews(int limit,RSSFeed rssFeed) {
			this.limit = limit;
			this.rssFeed = rssFeed;
		}

		protected void onPreExecute() {

		}

		@Override
		protected void onCancelled() {
			// Clean up the UI if needed (i.e. close dialogs, etc.)
			// In API 11, you should use the onCancelled method that takes a
			// param
			// setListAdapter(adapter);
		}

		protected String doInBackground(String... args) {
			WebsiteDataAdapter websiteAdapter = new WebsiteDataAdapter(
					getActivity());
			websiteAdapter.open();
			RSSParser parser = new RSSParser();
			int count = 0;
			//for (RSSFeed u : feeds) {
				xml = parser.getXmlFromUrl(rssFeed.getLink()); // getting XML from URL
				XmlPullFeedParser pa = new XmlPullFeedParser(xml,
						rssFeed.getWebsite());
				List<RSSItem> list = new ArrayList<RSSItem>();
				list = pa.parse();
				// if(list!=null){
				for (RSSItem item : list) {
					// item.setSi(u.getSiteName());
					if (count < limit) {
						newsList.add(item);
						count++;
					}else{
						break;
					}
				}
				Set<RSSItem> set = new HashSet<RSSItem>(newsList);
				List<RSSItem> listData = new ArrayList<RSSItem>(set);
				Collections.sort(listData);
				adapter = new RssItemAdapter(getActivity(), listData, 1);
				newsList = listData;
				count = 0;
			//}

			feeds.remove(0);
			// adapter=new RssItemAdapter(getActivity(), newsList,1);
			// return null;
			// }
			/*
			 * catch(Exception ex){ //Log.d("error",ex.getMessage());
			 * getActivity().runOnUiThread(new Runnable() {
			 * 
			 * @Override public void run() { // TODO Auto-generated method stub
			 * Toast.makeText(getActivity(),
			 * "can not get data, please check your network"
			 * ,Toast.LENGTH_LONG).show(); } }); }
			 */
			return null;
		}

		// After completing background task set adapter for list
		protected void onPostExecute(String args) {
			// dismiss the dialog after getting all products
			adapter.notifyDataSetChanged();
			setListAdapter(adapter);
			// pDialog.dismiss();
		}

	}
}
