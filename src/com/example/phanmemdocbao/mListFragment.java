package com.example.phanmemdocbao;

import DataHelper.WebsiteDataAdapter;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;


public class mListFragment extends ListFragment{
	public interface onListItemClickListener {
		public void onListItemClick(ListView l, View v, int position, long id);
	}
	Cursor mCursor;
	SimpleCursorAdapter mAdapter;
	WebsiteDataAdapter userAdapter;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
		userAdapter = new WebsiteDataAdapter(getActivity());
		//userAdapter = new FeedDataAdapter(getActivity());
		userAdapter.open();
		mCursor = userAdapter.GetAll();
		mAdapter = new SimpleCursorAdapter(getActivity(), 
				android.R.layout.simple_list_item_1, 
				mCursor, //Cursor 
				new String[] {"isDefault"},
				new int[] { android.R.id.text1},0); 
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	
	
}

