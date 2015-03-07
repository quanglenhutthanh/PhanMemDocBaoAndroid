package com.example.phanmemdocbao;
import java.util.ArrayList;
import java.util.List;
import Adapter.CategoryAdapter;
import DataHelper.CategoryDataAdapter;
import DataHelper.FeedDataAdapter;
import Entities.Category;
import Entities.RSSFeed;
import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

public class CategoryListFragment extends ListFragment{
	CategoryDataAdapter categoryDataAdapter;
	SimpleCursorAdapter mAdapter;
	List<Category> categories;
	CategoryAdapter cAdapter;
	public CategoryListFragment(){
		
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
		categoryDataAdapter	 = new CategoryDataAdapter(getActivity());
		categoryDataAdapter.open();
		categories = new ArrayList<Category>();
		categories = categoryDataAdapter.GetAllList();
		
		cAdapter = new CategoryAdapter(getActivity(), categories);
		setListAdapter(cAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_LONG).show();
		Category selected = categories.get(position);
		FeedDataAdapter feed = new FeedDataAdapter(getActivity());
		feed.open();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(RSSFeed f:feed.GetFeedsByCategoryId(selected.get_id())){
			ids.add(f.get_id());
		}
		Intent intent =new Intent(this.getActivity(),RSSItemListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putIntegerArrayList("ids", ids);
		bundle.putInt("flag", 1);
		bundle.putString("title", selected.getName());
		intent.putExtra("rssBundle", bundle);
		startActivity(intent);
	}
	
}
