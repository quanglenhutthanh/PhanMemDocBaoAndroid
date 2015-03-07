package com.example.phanmemdocbao;
import java.util.ArrayList;
import java.util.List;
import Adapter.WebsiteListAdapter;
import DataHelper.FeedDataAdapter;
import DataHelper.WebsiteDataAdapter;
import Entities.RSSFeed;
import Entities.Website;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;


public class WebsiteListFragment extends ListFragment{
	WebsiteDataAdapter websiteDataAdapter;
	SimpleCursorAdapter mAdapter;
	List<Website> websites;
	WebsiteListAdapter wAdapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
		websiteDataAdapter	 = new WebsiteDataAdapter(getActivity());
		websiteDataAdapter.open();
		websites = new ArrayList<Website>();
		websites = websiteDataAdapter.GetWebsitesByType(0);
		
		wAdapter = new WebsiteListAdapter(getActivity(), websites);
		setListAdapter(wAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_LONG).show();
		Website selected = websites.get(position);
		FeedDataAdapter feed = new FeedDataAdapter(getActivity());
		feed.open();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(RSSFeed f:feed.GetFeedsByWebsiteId(selected.getId())){
			ids.add(f.get_id());
		}
		//Toast.makeText(getActivity(), feed.GetFeedByWebsiteId(2), Toast.LENGTH_LONG).show();
		Intent intent =new Intent(getActivity(),RSSItemListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("flag", 1);
		bundle.putString("title", selected.getName());
		bundle.putIntegerArrayList("ids", ids);
		intent.putExtra("rssBundle", bundle);
		startActivity(intent);
	}
}
