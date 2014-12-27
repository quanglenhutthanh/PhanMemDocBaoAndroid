package Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.example.phanmemdocbao.ListWebsiteActivity;
import com.example.phanmemdocbao.R;


import DataHelper.WebsiteDataAdapter;
import Entities.Website;
import Utilities.ImageLoader;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WebsiteListAdapter extends BaseAdapter{
	private Activity activity;
    private List<Entities.Website> websites;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    public WebsiteListAdapter(Activity a, List<Entities.Website> websites) {
        activity = a;
        this.websites = websites;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);
        //TextView header = (TextView)vi.findViewById(R.id.list_website_layout_header); 
        //header.setText("Danh sách báo bạn đã thêm");
        TextView name = (TextView)vi.findViewById(R.id.list_item_title); // title
        ImageView logo = (ImageView)vi.findViewById(R.id.list_image);
        TextView subTitle = (TextView)vi.findViewById(R.id.list_item_subtitle); // artist name
        ImageView sub_thumb_image=(ImageView)vi.findViewById(R.id.logobao);
        subTitle.setVisibility(View.GONE);
        sub_thumb_image.setVisibility(View.GONE);
        Entities.Website w = new Entities.Website();
        w = websites.get(position);
        final int id = w.getId();
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
        
        ImageButton btnDelete = (ImageButton)vi.findViewById(R.id.buttonDelete);
        btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog cdialog = new AlertDialog.Builder(activity).create();
			    cdialog.setTitle("Xác nhận");
			    cdialog.setMessage("Bạn có chắc muốn xóa?");
			    cdialog.setCancelable(false);
			    cdialog.setButton(DialogInterface.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int buttonId) {
			        	WebsiteDataAdapter websiteDataAdapter = new WebsiteDataAdapter(activity.getApplicationContext());
						websiteDataAdapter.open();
						websiteDataAdapter.Delete(id);
						ListWebsiteActivity act = (ListWebsiteActivity)activity;
						act.showList();
						};
			        
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
