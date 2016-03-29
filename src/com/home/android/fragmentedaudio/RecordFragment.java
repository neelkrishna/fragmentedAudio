package com.home.android.fragmentedaudio;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;

import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class RecordFragment extends Fragment{
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssZ");
	StopWatch sw = new StopWatch();
	long watchTime = 0;
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;

	private static final String LOG_TAG = "Playback";
    private String mFileName = null;
    
    private String mTempFile = Environment.getExternalStorageDirectory().getAbsolutePath() + ""
    		+ "/audiorecordtestTEMP.3gp";
    
    ArrayList<AudioItem> mAudioItems;
    public static final String EXTRA_AUDIO_ITEM_ID = "fragmentedaudio.AUDIO_ITEM_ID";
    
	ImageButton recordButton;
	ImageButton playButton;
	ImageButton stopButton;
	Button keepButton;
	EditText titleText;
	
	boolean mStartRecording = true;
	boolean mStartPlaying = true;
	

    public static RecordFragment newInstance(UUID audioItemId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_AUDIO_ITEM_ID, audioItemId);

        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mAudioItems = AudioSingleton.get(getActivity()).getAudioItems();

        setHasOptionsMenu(true);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.record_fragment, container, false);
        
        recordButton = (ImageButton)v.findViewById(R.id.record_button);
        
        playButton = (ImageButton)v.findViewById(R.id.play_button);
        playButton.setVisibility(View.GONE);
        
        stopButton = (ImageButton)v.findViewById(R.id.stop_button);
        stopButton.setEnabled(false);
        
        titleText = (EditText)v.findViewById(R.id.title_text);

        keepButton = (Button)v.findViewById(R.id.keep_button);
        keepButton.setVisibility(View.GONE);
        
        recordButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				stopButton.setEnabled(true);
				recordButton.setEnabled(false);
				onRecord(mStartRecording);
                if (mStartRecording) {
                	stopButton.setEnabled(true);
                } else {
                	playButton.setVisibility(View.VISIBLE);
                	stopButton.setVisibility(View.GONE);
                }
                mStartRecording = !mStartRecording;
                if(mStartRecording == true){
                	keepButton.setVisibility(View.VISIBLE);
                }else{
                	keepButton.setVisibility(View.GONE);
                }
                
			}
        	
        });
        
        playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				onPlay(mStartPlaying);
				
                playButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
                
                mStartPlaying = !mStartPlaying;
			}
        	
        });
        
        stopButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mStartPlaying){
					onPlay(!mStartPlaying);
					playButton.setVisibility(View.VISIBLE);
					stopButton.setVisibility(View.GONE);
	                
	                mStartPlaying = !mStartPlaying;
				}
				if(mStartRecording){
					onRecord(!mStartRecording);
					playButton.setVisibility(View.VISIBLE);
					stopButton.setVisibility(View.GONE);
					
					mStartRecording = !mStartRecording;
				}
				
			}
        	
        });
        
        keepButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + ""
			    		+ "/audiorecordtest"+ sdf.format(new Date()) +".3gp";
				
				try {
					copyAudioFile(mTempFile, mFileName);
				} catch (IOException e) {
					System.out.println("Failed to copy file onKeep");
					e.printStackTrace();
				}
				
				AudioItem x = new AudioItem();
				if(titleText.getText().toString() != null){
					x.setTitle(titleText.getText().toString());
				}else{
					x.setTitle("Untitled");
				}
				x.setDate(new Date());
				x.setAudioFile(mFileName);
				
				AudioSingleton.get(getActivity()).addAudioItem(x);
				
				int temp = AudioSingleton.get(getActivity()).getNumberOfItems();
				Log.d("Num Items", temp + "");
				
		        
				getActivity().finish();
				
			}
        	
        });
        
        return v;
    }
	
	private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
            //startWatch();
        } else {
            stopPlaying();
            //watchTime = stopWatch();
        }
    }
    
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mTempFile);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
    	if(mPlayer != null){
    		mPlayer.release();
            mPlayer = null;
    	}
        
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mTempFile);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
    	if (mRecorder !=null){
    		mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
    	}
        
    }
    public String shortenDate(){
    	String shortDate = new Date().toString();
    	int length = shortDate.length();
    	for(int i = 0; i < length; i++){
    		if(shortDate.substring(i, i+1).equals(" ")){
    			shortDate = shortDate.substring(0, i) + shortDate.substring(i+1, length);
    			length--;
    		}
    	}
    	
    	return shortDate;
    }
    
    @Override
    public void onPause() {
        super.onPause();
        AudioSingleton.get(getActivity()).saveAudioItems();
    }
    
    public static void copyAudioFile(String fileName1, String fileName2) throws IOException{
		FileUtils.copyFile(new File(fileName1), new File(fileName2));
		
	}
    public int startWatch(){
    	int timer = 0;
		while(mStartPlaying){
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			    break;
			}
			return timer++;
		}
		return timer;
	}
    public long stopWatch(){
    	sw.stop();
    	return sw.getTime();
    }
    
    
    
}
