package com.example.phanmemdocbao;



import java.util.ArrayList;
import java.util.List;


import Adapter.ContentAdapter;
import Adapter.WebsiteListAdapter;
import DataHelper.ContentDataAdapter;
import DataHelper.FeedDataAdapter;
import DataHelper.WebsiteDataAdapter;
import Entities.Category;
import Entities.Content;
import Entities.RSSFeed;
import Entities.Website;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ContentListFragment extends ListFragment{
	ContentDataAdapter contentDataAdapter;
	SimpleCursorAdapter mAdapter;
	List<Content> contents;
	ContentAdapter wAdapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
		contentDataAdapter	 = new ContentDataAdapter(getActivity());
		contentDataAdapter.open();
		contents = new ArrayList<Content>();
		contents = contentDataAdapter.GetAllList();
		
		wAdapter = new ContentAdapter(getActivity(), contents);
		setListAdapter(wAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_LONG).show();
		Content selected = contents.get(position);
		ContentDataAdapter content = new ContentDataAdapter(getActivity());
		content.open();
		
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(int i:content.GetListFeedId(selected.get_id())){
			ids.add(i);
		}
		Intent intent =new Intent(this.getActivity(),RSSItemListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("flag", 1);
		bundle.putString("title", selected.getName());
		bundle.putIntegerArrayList("ids", ids);
		intent.putExtra("rssBundle", bundle);
		startActivity(intent);
	}
}
