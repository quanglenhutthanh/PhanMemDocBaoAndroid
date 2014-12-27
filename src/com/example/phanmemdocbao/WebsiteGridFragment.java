package com.example.phanmemdocbao;

import java.util.ArrayList;
import java.util.List;

import Adapter.WebsiteGridAdapter;
import Adapter.WebsiteListAdapter;
import DataHelper.FeedDataAdapter;
import DataHelper.WebsiteDataAdapter;
import Entities.RSSFeed;
import Entities.Website;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class WebsiteGridFragment extends Fragment{
	WebsiteDataAdapter websiteDataAdapter;
	SimpleCursorAdapter mAdapter;
	List<Website> websites;
	WebsiteGridAdapter wAdapter;
	GridView gridView;
	View view;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.grid_fragment, container, false);
        new LoadWebsites().execute();
        return view;
        
    }
	class LoadWebsites extends AsyncTask<String, String, String>{

		
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			gridView = (GridView) view.findViewById(R.id.gridView1);
	        websiteDataAdapter	 = new WebsiteDataAdapter(view.getContext());
			websiteDataAdapter.open();
			websites = new ArrayList<Website>();
			websites = websiteDataAdapter.GetWebsitesByType(1);
			wAdapter = new WebsiteGridAdapter(getActivity(), websites);
	        return null;
		}
		protected void onPostExecute(String args){
			gridView.setAdapter(wAdapter);
	        gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					// TODO Auto-generated method stub
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
					bundle.putString("icon", selected.getIcon());
					bundle.putString("title", selected.getName());
					bundle.putIntegerArrayList("ids", ids);
					intent.putExtra("rssBundle", bundle);
					startActivity(intent);
				}
			});
		}
	}
	/*@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        websiteDataAdapter	 = new WebsiteDataAdapter(this.);
			websiteDataAdapter.open();
			websites = new ArrayList<Website>();
			websites = websiteDataAdapter.GetAllList();
			wAdapter = new WebsiteGridAdapter(getActivity(), websites);
	        GridView gridview = (GridView) this.getActivity().findViewById(R.id.photogridview);
	        gridview.setAdapter(new PhotoImageAdapter(this.getActivity()));
	 }*/

	
	
	
	

}
