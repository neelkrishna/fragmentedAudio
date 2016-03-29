package com.home.android.fragmentedaudio;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListFragment extends ListFragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = super.onCreateView(inflater, container, savedInstanceState);
		ListView listView = (ListView)v.findViewById(android.R.id.list);
		
		listView.setClickable(true);
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
			}
			
		});
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<AudioItem> fromUser = AudioSingleton.get(getActivity()).getAudioItems();
        Log.d("LIST", fromUser.size()+"");

        AudioItemAdapter adapter = new AudioItemAdapter(fromUser);
        setListAdapter(adapter);
        setRetainInstance(true);
        updateUI();
    }
	
	public void updateUI(){
		((AudioItemAdapter)getListAdapter()).notifyDataSetChanged();
		
	}
	
	private class AudioItemAdapter extends ArrayAdapter<AudioItem> {
        public AudioItemAdapter(ArrayList<AudioItem> audioItems) {
            super(getActivity(), android.R.layout.simple_list_item_1, audioItems);
            
        }
        int count = 0;
        MediaPlayer mp2 = null;
        
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_audio_item, null);
            }
            final AudioItem c = getItem(position);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy, ' at ' H:m");
            String simpleDate = sdf.format(c.getDate());
            
            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.audio_item_titleTextView);
            titleTextView.setText(c.getTitle());
            TextView dateTextView =
                (TextView)convertView.findViewById(R.id.audio_item_dateTextView);
            dateTextView.setText(simpleDate);
            
            View v = (View)convertView.findViewById(R.id.item_relative_layout);
            Color color = new Color();
            int color1 = getResources().getColor(android.R.color.darker_gray);
            int color2 = getResources().getColor(android.R.color.holo_blue_light);
            int textWhite = getResources().getColor(android.R.color.white);
            int color3 = getResources().getColor(android.R.color.holo_blue_dark);
            if(count%2==0){
            	//v.setBackgroundColor(color2);
            }else{
            	v.setBackgroundColor(color3);
            	titleTextView.setTextColor(textWhite);
            	dateTextView.setTextColor(textWhite);
            }
            count++;
            ((AudioItemAdapter)getListAdapter()).notifyDataSetChanged();
            
            ImageButton trash = (ImageButton)convertView.findViewById(R.id.list_trash_button);
            trash.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					c.setTitle("");
					AudioSingleton.get(getActivity()).deleteAudioItem(c);
					updateUI();
					getActivity().finish();
			
				}
            	
            });
            
            
            ImageButton play = (ImageButton)convertView.findViewById(R.id.list_play_button); 
            play.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					Context context = getActivity().getApplicationContext();
					CharSequence text = "Play!";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
					mp2 = new MediaPlayer();
					try {
						mp2.setDataSource(c.getAudioFile());
						try {
			                
			                mp2.prepare();
			                
			            } catch (IOException e) {
			                Log.e("Player", "prepare() failed");
			            }
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					mp2.start();
				}
            	
            });
            ImageButton stop = (ImageButton)convertView.findViewById(R.id.list_stop_button);
            
            stop.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					Context context = getActivity().getApplicationContext();
					CharSequence text = "Stop!";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
					if (mp2 != null) {
			            mp2.release();
			            mp2 = null;
			        }
					
				}
            	
            });

            return convertView;
        }
       
    }
	
}
