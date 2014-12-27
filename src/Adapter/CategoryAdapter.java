package Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.phanmemdocbao.R;
import com.example.phanmemdocbao.R.id;

import Entities.*;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter{
	private Activity activity;
    private List<Category> categories;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    public CategoryAdapter(Activity a, List<Entities.Category> categories) {
        activity = a;
        this.categories = categories;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.text_item, null);
        TextView name = (TextView)vi.findViewById(R.id.categoryName); // title
        TextView desc = (TextView)vi.findViewById(R.id.categoryDesc); 
        Category w = new Category();
        w = categories.get(position);
        name.setText(w.getName());
       
        desc.setText(w.getDescription());
        return vi;
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
}
