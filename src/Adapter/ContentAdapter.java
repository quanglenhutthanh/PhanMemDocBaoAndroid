package Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.phanmemdocbao.ContentActivity;
import com.example.phanmemdocbao.ListWebsiteActivity;
import com.example.phanmemdocbao.R;
import com.example.phanmemdocbao.R.id;


import DataHelper.ContentDataAdapter;
import Entities.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentAdapter extends BaseAdapter{
	private Activity activity;
    private List<Content> contents;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    public ContentAdapter(Activity a, List<Content> contents) {
        activity = a;
        this.contents = contents;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);
        TextView title = (TextView)vi.findViewById(R.id.list_item_title); // title
        TextView subtile = (TextView)vi.findViewById(R.id.list_item_subtitle); // title
        //TextView desc = (TextView)vi.findViewById(R.id.categoryDesc); 
        ImageView logo = (ImageView)vi.findViewById(R.id.list_image);
        ImageView sub_thumb_image=(ImageView)vi.findViewById(R.id.logobao); 
        sub_thumb_image.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        Content c = new Content();
        c = contents.get(position);
        final int id = c.get_id();
        title.setText(c.getName());
        subtile.setVisibility(View.GONE);
        ImageButton btnDelete = (ImageButton)vi.findViewById(R.id.buttonDelete);
        btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog cdialog = new AlertDialog.Builder(activity).create();
			    cdialog.setTitle("Xác nhận");
			    cdialog.setMessage("Bạn có chắc muốn xóa?");
			    cdialog.setCancelable(false);
			    cdialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int buttonId) {
			        	ContentDataAdapter contentDataAdapter = new ContentDataAdapter(activity.getApplicationContext());
			        	contentDataAdapter.open();
			        	contentDataAdapter.Delete(id);
			        	ContentActivity act = (ContentActivity)activity;
			        	act.showList();
						};
			        
			    });
			    cdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int buttonId) {
			            dialog.dismiss();
			        }
			    });
			    cdialog.setIcon(android.R.drawable.ic_dialog_alert);
			    cdialog.show();
			}
		});
        //desc.setText(c.getDescription());
        return vi;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contents.size();
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
