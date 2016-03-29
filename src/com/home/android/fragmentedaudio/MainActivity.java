package com.home.android.fragmentedaudio;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		android.app.FragmentManager fm = getFragmentManager();  
		  
		  if (fm.findFragmentById(android.R.id.content) == null) {  
		   MyListFragment list = new MyListFragment();  
		   fm.beginTransaction().add(android.R.id.content, list).commit();  
		  }  
		AudioSingleton.get(getApplicationContext()).getAudioItems();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.new_entry) {
			
			Intent intent = new Intent(MainActivity.this, RecordActivity.class);
	        startActivity(intent);
	        //this.finish();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		android.app.FragmentManager fm = getFragmentManager();  
		MyListFragment mlf = new MyListFragment();
		fm.beginTransaction().add(android.R.id.content, mlf).commit(); 
		//mlf.updateUI();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		AudioSingleton.get(getApplicationContext()).saveAudioItems();
	}
	
	
	
}
