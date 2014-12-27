package Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.example.phanmemdocbao.R;

import Entities.Website;
import Utilities.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class WebsiteGridAdapter extends BaseAdapter{
	private Activity activity;
    private List<Entities.Website> websites;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    public WebsiteGridAdapter(Activity a, List<Entities.Website> websites) {
        activity = a;
        this.websites = websites;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null){
            vi = inflater.inflate(R.layout.grid_item, null);
            //vi.setLayoutParams(new GridView.LayoutParams(200,200));
        }
        TextView name = (TextView)vi.findViewById(R.id.grid_item_text); // title
        ImageView logo = (ImageView)vi.findViewById(R.id.grid_item_image);
        Entities.Website w = new Entities.Website();
        w = websites.get(position);
        name.setText(w.getName());
        if(w.getIsDefault()==1)
        {
	        Resources res = activity.getResources();
	        String mDrawableName = w.getIcon();
	        int resID = res.getIdentifier(mDrawableName , "drawable", activity.getPackageName());
	        Drawable drawable = res.getDrawable(resID );
	        //icon.setImageDrawable(drawable );
	        logo.setImageDrawable(drawable);
        }
        if(w.getIsDefault()==0)
        {
        	ImageLoader imageLoader = new ImageLoader(activity.getApplicationContext());
        	imageLoader.DisplayImage(w.getIcon(), logo);
        }
        return vi;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return websites.size();
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
