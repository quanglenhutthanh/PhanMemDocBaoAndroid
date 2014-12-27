package Adapter;

import java.util.List;


import com.example.phanmemdocbao.R;

import Entities.Category;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryCheckBoxAdapter extends BaseAdapter{

	private Activity activity;
	private static LayoutInflater inflater = null;
	private Category c;
	private List<Category> categories;
	public CategoryCheckBoxAdapter(Activity a, List<Entities.Category> categories){
		activity = a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.categories = categories;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categories.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder{
		CheckBox checkbox;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi = convertView;
		c = categories.get(position);
		ViewHolder holder = null;
		if(convertView == null)
			vi = inflater.inflate(R.layout.category_checkbox_layout, null);
		CheckBox checkbox = (CheckBox)vi.findViewById(R.id.category_checkbox_layout_checkbox);	
		checkbox.setText(c.getName());
		checkbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox cb = (CheckBox) v ;
				//Category cat = new Category(); 
				//cat = (Category)cb.getTag();
				//Toast.makeText(activity,
					//       "Clicked on Checkbox: " + cb.getText() +
					  //     " is " + cb.isChecked(),Toast.LENGTH_LONG).show();
				for(Category c:categories){
					if(c.getName()==cb.getText()){
						c.setChecked(cb.isChecked());
					}
				}
			}
		});
		checkbox.setChecked(c.isChecked());
		return vi;
	}

}
