package com.example.phanmemdocbao;

import Utilities.UnCaughtException;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;


public class WebViewActivity extends Activity {
	String link;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_layout);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(WebViewActivity.this));
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("link");	
		link = bundle.getString("link");
		
		WebView w = (WebView)findViewById(R.id.webViewRSSItem);
		ImageButton buttonBrowse = (ImageButton)findViewById(R.id.buttonBrowse); 		
		
		buttonBrowse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri address = Uri.parse(link);
				Intent androidDocs = new Intent(Intent.ACTION_VIEW, address);
				startActivity(androidDocs);
			}
		});
		
		w.getSettings().setBuiltInZoomControls(true);
		w.setWebViewClient(new WebViewClient());
		w.loadUrl(link);
	}
}
