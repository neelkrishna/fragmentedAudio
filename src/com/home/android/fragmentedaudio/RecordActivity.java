package com.home.android.fragmentedaudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RecordActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		
		getFragmentManager().beginTransaction()
		.add(R.id.container, new RecordFragment()).commit();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.record_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.back_button) {
			
			finish();
			
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
