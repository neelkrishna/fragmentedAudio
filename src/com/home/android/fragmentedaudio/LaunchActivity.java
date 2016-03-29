package com.home.android.fragmentedaudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LaunchActivity extends Activity{
//	@Override
//	protected void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
//
//		
//		Intent i = new Intent(LaunchActivity.this, MainActivity.class);
//		startActivity(i);
//		
//	}
	@Override
	public void onResume(){
		super.onResume();
		Intent i = new Intent(LaunchActivity.this, MainActivity.class);
		startActivity(i);
	}
//	@Override
//	public void onStart(){
//		super.onStart();
//		Intent i = new Intent(LaunchActivity.this, MainActivity.class);
//		startActivity(i);
//	}
	
}
