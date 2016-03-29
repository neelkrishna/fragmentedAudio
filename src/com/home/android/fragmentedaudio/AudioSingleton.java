package com.home.android.fragmentedaudio;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.content.Context;
import android.util.Log;


public class AudioSingleton {
	private static final String TAG = "AudioSingleton";
    private static final String FILENAME = "audioFiles.json";

    private ArrayList<AudioItem> mAudioItems = new ArrayList<AudioItem>();
    private AudioCopyJSONSerializer mSerializer;

    private static AudioSingleton sAudioSingleton;
    private Context mAppContext;

    private AudioSingleton(Context appContext) {
        mAppContext = appContext;
        mSerializer = new AudioCopyJSONSerializer(mAppContext, FILENAME);
        
        try {
            mAudioItems = mSerializer.loadAudioItems();
        } catch (Exception e) {
            mAudioItems = new ArrayList<AudioItem>();
            Log.e(TAG, "Error loading files: ", e);
        }
        sAudioSingleton = this;
    }

    public static AudioSingleton get(Context c) {
    	
        if (sAudioSingleton == null) {
            sAudioSingleton = new AudioSingleton(c.getApplicationContext());
        }
        return sAudioSingleton;
    }

    public String getAudioFile(UUID id) {
        for (AudioItem c : mAudioItems) {
            if (c.getId().equals(id))
                return c.getAudioFile();
        }
        return null;
    }
    
    public void addAudioItem(AudioItem c) {
        mAudioItems.add(c);
        saveAudioItems();
    }

    public ArrayList<AudioItem> getAudioItems() {
        return mAudioItems;
    }

    public void deleteAudioItem(AudioItem c) {
        mAudioItems.remove(c);
        saveAudioItems();
    }

    public boolean saveAudioItems() {
        try {
            mSerializer.saveAudioItems(mAudioItems);
            Log.d(TAG, "Files saved");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving files: " + e);
            return false;
        }
    }
    
    public int getNumberOfItems(){
    	return mAudioItems.size();
    }
}
