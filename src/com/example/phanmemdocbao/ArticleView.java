package com.example.phanmemdocbao;
import java.io.IOException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import DataHelper.RSSItemDataAdapter;
import DataHelper.WebsiteDataAdapter;
import Entities.RSSItem;
import Utilities.UnCaughtException;
import Utilities.networkUtility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ArticleView extends Activity{
	
	Handler mVolHandler;
	Runnable mVolRunnable;
	LinearLayout layout_action;
	Document doc;
	WebView w;
	String test = "";
	String title;
	String pubDate;
	String description;
	String link;
	String className;
	String websiteIcon;
	int websiteId;
	int isDefault;
	RSSItem item ;
	ProgressDialog pDialog;
	boolean flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_layout);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(ArticleView.this));
		
		if(!networkUtility.isOnline(this)){
			Intent intent = new Intent(ArticleView.this, NetworkActivity.class);
			startActivity(intent);
			this.finish();
			return;
		}
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("article");
		String siteName = bundle.getString("siteName");
		websiteId = bundle.getInt("websiteId");
		
		WebsiteDataAdapter websiteDataAdaper = new WebsiteDataAdapter(ArticleView.this);
		websiteDataAdaper.open();
		RSSItemDataAdapter rssItemAdapter = new RSSItemDataAdapter(ArticleView.this);
		rssItemAdapter.open();
		
		item = new RSSItem(-1, bundle.getString("title"), 
				bundle.getString("image"), 
				bundle.getString("link"), 
				bundle.getString("desc"), 
				bundle.getString("pubDate"), ""); 
		item.setWebsite(websiteDataAdaper.GetWebsiteById(websiteId));
		Log.d("websiteId",String.valueOf(websiteId));
		
		title = bundle.getString("title");
		pubDate = bundle.getString("pubDate");
		description = bundle.getString("desc");
		link = bundle.getString("link");
		isDefault = bundle.getInt("isDefault");
		className = bundle.getString("className");
		websiteIcon = bundle.getString("websiteIcon");
		
		ImageButton browser = (ImageButton)findViewById(R.id.buttonBrowse);
		ImageButton share = (ImageButton)findViewById(R.id.buttonShare);
		final ImageButton add = (ImageButton)findViewById(R.id.buttonAdd);
		
		//LinearLayout layout_main = (LinearLayout)findViewById(R.id.article_layout_main);
		layout_action = (LinearLayout)findViewById(R.id.article_layout_action);	
		final WebView w= (WebView)findViewById(R.id.webViewRSSItem);
		layout_action.setVisibility(View.VISIBLE);
		
		mVolHandler = new Handler();
        mVolRunnable = new Runnable() {
            public void run() {
            	layout_action.setVisibility(View.GONE);
              //  layout_action.setLayoutTransition(Transition.);
            }
        };
		w.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent touchevent) {
				// TODO Auto-generated method stub
				 switch (touchevent.getAction())
                 {
                        // when user first touches the screen we get x and y coordinate
                         case MotionEvent.ACTION_DOWN: 
                         {
                             x1 = touchevent.getX();
                             y1 = touchevent.getY();
                             break;
                        }
                         case MotionEvent.ACTION_UP: 
                         {
                             x2 = touchevent.getX();
                             y2 = touchevent.getY(); 

                             
                             if (y1 < y2) 
                             {
                                 //Toast.makeText(ArticleView.this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                            	 layout_action.setVisibility(View.VISIBLE);
                             }
                            
                             //if Down to UP sweep event on screen
                             if (y1 > y2)
                             {
                            	 layout_action.setVisibility(View.GONE);
                                 //Toast.makeText(ArticleView.this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                              }
                             break;
                         }
                 }
                 return false;
			}
		});
		browser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), link, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ArticleView.this,WebViewActivity.class); 
				Bundle bundle  = new Bundle();
				bundle.putString("link", link);
				intent.putExtra("link", bundle);
				startActivity(intent);
			}
		});
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = link;
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
			    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			    startActivity(Intent.createChooser(sharingIntent, "Share via")); 
			}
		});
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RSSItemDataAdapter rssItemDataAdapter = new RSSItemDataAdapter(ArticleView.this);
				rssItemDataAdapter.open();
				rssItemDataAdapter.Insert(item);
				Toast.makeText(ArticleView.this, "Bài viết đánh dấu thành công", Toast.LENGTH_LONG).show();
				add.setVisibility(View.GONE);
			}
		});
		//atitle.setText(title);
		//apubDate.setText(pubDate);
		test += "<!DOCTYPE html><html><head><style type=\"text/css\"> " +
				"img{width:100%}</style></head>" +
				"<body>";
				
				
		test += "<h3>"+title + "</h3>";
		test += "<div align=\"right\"><small>"+pubDate + "</small></div>";
		test+="<hr align=\"left\" width=\"50%\">";
		if(isDefault ==1){
			Resources res = getResources();
			int resID = res.getIdentifier(websiteIcon , "drawable", getPackageName());
	        Drawable drawable = res.getDrawable(resID );
	        getActionBar().setIcon(drawable);
			setTitle(siteName);
		}
		if(rssItemAdapter.getbyLink(link).getCount() > 0){
			add.setVisibility(View.GONE);
		}
		new LoadNews().execute();
	}
	
	float x1, x2;
	float y1, y2;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

class LoadNews extends AsyncTask<String, String, String> {
		
        protected void onPreExecute() {
        	pDialog = new ProgressDialog(ArticleView.this);
            pDialog.setMessage("Đang tải...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }
 
        protected String doInBackground(String... args) {
        	
    		try {
    			//Response response = 
    			//	    Jsoup.connect("http://kenh14.vn/xa-hoi/con-do-de-doa-bac-si-tan-cong-canh-sat-113-o-sai-son-20140429083643483.chn").execute();
    			Response response= Jsoup.connect(link)
    			           .ignoreContentType(true)
    			           .userAgent("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")  
    			           .referrer("http://www.google.com")   
    			           .timeout(12000) 
    			           .followRedirects(true)
    			           .execute();
    			//doc = Jsoup.connect(link).followRedirects(true).get();
    			doc = response.parse();
    			try{
    				Element content = doc.getElementsByClass(className).first();
    				test += "<div style=\"width:100%;\">"+content.html()+"</div>";
    			}
    			catch(Exception ex)
    			{
    				test += description;
    			}
    			test+="</body></html>";
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			test = description;
    			e.printStackTrace();
    		}
        	/*
        	try
        	{
	        	String html = "";
	        	html = GET(link);
	        	doc = Jsoup.parse(html);
	        	Element content = doc.getElementsByClass(className).first();
				test = content.html();
        	}
        	catch (Exception e) {
    			// TODO Auto-generated catch block
    			test = description;
    			e.printStackTrace();
    		}*/
        	return null;
        }
 
        
        //After completing background task set adapter for list
        protected void onPostExecute(String args) {
        	w = (WebView)findViewById(R.id.webViewRSSItem);
        	w.getSettings().setTextSize(WebSettings.TextSize.LARGER);
    		w.getSettings().setBuiltInZoomControls(false);
        	w.getSettings().setDefaultFontSize(13);
        	//w.getSettings().setUseWideViewPort(true);
        	//settings.setLoadWithOverviewMode(true);
        	w.getSettings().setLoadWithOverviewMode(true);
    		w.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
    		w.loadData(test, "text/html; charset=UTF-8", null);
    		pDialog.dismiss();
            //WebSettings settings = w.getSettings();
    		//settings.setUseWideViewPort(true);
    		//settings.setLoadWithOverviewMode(true);
        }
 
    }





}
