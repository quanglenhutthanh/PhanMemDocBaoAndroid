package Adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.phanmemdocbao.ListWebsiteActivity;
import com.example.phanmemdocbao.R;
import com.example.phanmemdocbao.R.id;
import com.example.phanmemdocbao.R.layout;
import com.example.phanmemdocbao.RSSItemListActivity;

import DataHelper.RSSItemDataAdapter;
import DataHelper.WebsiteDataAdapter;
import Entities.*;
import Utilities.ImageLoader;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RssItemAdapter extends BaseAdapter {
	private Activity activity;
    //private ArrayList<HashMap<String, String>> data;
    private List<RSSItem> rssItems;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    int flag = 1;
    public RssItemAdapter(Activity a, List<RSSItem> d,int flag) {
        activity = a;
        rssItems = new ArrayList<RSSItem>();
        rssItems=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
        this.flag = flag;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);

        TextView title = (TextView)vi.findViewById(R.id.list_item_title); // title
        TextView subTitle = (TextView)vi.findViewById(R.id.list_item_subtitle); // artist name
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        ImageView sub_thumb_image=(ImageView)vi.findViewById(R.id.logobao); 
        ImageButton button = (ImageButton)vi.findViewById(R.id.buttonDelete);
        
        RSSItem item = new RSSItem();
        item = rssItems.get(position);
        final int id = item.get_id();
        // Setting all values in listview
        title.setText(item.getTitle());
        subTitle.setText(item.getWebsite().getName() + " - " +item.getFormatPubdate());
        
       
        if(flag==1){
        	button.setVisibility(View.GONE);
        	if(item.getWebsite().getIsDefault()==1){
	        	 Resources res = activity.getResources();
	             String mDrawableName = item.getWebsite().getIcon();
	             int resID = res.getIdentifier(mDrawableName , "drawable", activity.getPackageName());
	             Drawable drawable = res.getDrawable(resID );
	             
	             sub_thumb_image.setImageDrawable(drawable);
        	}
        }
        if(flag==2){
        	button.setVisibility(View.VISIBLE);
        	imageLoader.DisplayImage(item.getWebsite().getIcon(), sub_thumb_image);
        	button.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				AlertDialog cdialog = new AlertDialog.Builder(activity).create();
    			    cdialog.setTitle("Xác nhận");
    			    cdialog.setMessage("Bạn có chắc muốn xóa?");
    			    cdialog.setCancelable(false);
    			    cdialog.setButton(DialogInterface.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
    			        public void onClick(DialogInterface dialog, int buttonId) {
    			        	RSSItemDataAdapter rssItemDataAdapter = new RSSItemDataAdapter(activity.getApplicationContext());
    						rssItemDataAdapter.open();
    						rssItemDataAdapter.Delete(id);
    						RSSItemListActivity act = (RSSItemListActivity)activity;
    						act.onCreate(null);
    						//act.finish();
    			        }
    				});
    			    cdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Không", new DialogInterface.OnClickListener() {
    			        public void onClick(DialogInterface dialog, int buttonId) {
    			            dialog.dismiss();
    			        }
    			    });
    			    cdialog.setIcon(android.R.drawable.ic_dialog_alert);
    			    cdialog.show();
    			}
    		});
        }
        
        if(item.getImage()!=null){
        	//Log.d("title", item.getImage());
        	imageLoader.DisplayImage(item.getImage(), thumb_image);
        	
        }
        return vi;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rssItems.size();
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
