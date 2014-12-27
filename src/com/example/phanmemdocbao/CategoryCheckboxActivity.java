package com.example.phanmemdocbao;

import java.util.ArrayList;
import java.util.List;



import Adapter.CategoryCheckBoxAdapter;
import DataHelper.CategoryDataAdapter;
import Entities.Category;
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

public class CategoryCheckboxActivity extends Activity{
	List<Category> categories;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_checkbox_layout);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(CategoryCheckboxActivity.this));
		
		categories = new ArrayList<Category>(); 
		CategoryDataAdapter categoryDataAdapter  = new CategoryDataAdapter(CategoryCheckboxActivity.this);
		categoryDataAdapter.open();
		categories = categoryDataAdapter.GetAllList();
		CategoryCheckBoxAdapter adapter = new CategoryCheckBoxAdapter(CategoryCheckboxActivity.this, categories);
		
		ListView list = (ListView)findViewById(R.id.listView1);
		TextView textview = (TextView)findViewById(R.id.list_item_subtext);
		textview.setText("Chọn chuyên mục bạn muốn đọc");
		Button btn = (Button)findViewById(R.id.add_content_layout_btnLuu);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(categories.contains(object))
				int count = 0;
				ArrayList<Integer> ids = new ArrayList<Integer>();
				for(Category c:categories){
					if(c.isChecked()){
						ids.add(c.get_id());
						count++;
					}
				}
				if(count == 0){
					Toast.makeText(CategoryCheckboxActivity.this, "Ch�?n một chuyên mục", Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent(CategoryCheckboxActivity.this,WebsiteCheckboxActivity.class);
				Bundle bundle = new Bundle();
				bundle.putIntegerArrayList("categoriesId", ids);
				intent.putExtra("categoriesId", bundle);
				startActivity(intent);
				CategoryCheckboxActivity.this.finish();
				//Toast.makeText(CategoryCheckboxActivity.this, checked, Toast.LENGTH_LONG).show();
			}
		});
		list.setAdapter(adapter);
	}
}
