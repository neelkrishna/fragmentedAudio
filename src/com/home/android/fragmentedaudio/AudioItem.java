package com.home.android.fragmentedaudio;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("ValidFragment")
public class AudioItem{
	private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    public static final String JSON_FILENAME = "fileName";
    
	private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mFileName;

    
    public AudioItem(){
    	mId = UUID.randomUUID();
        mDate = new Date();
    }
    
    public AudioItem(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mDate = new Date(json.getLong(JSON_DATE));
        mFileName = json.getString(JSON_FILENAME);
    }
    
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_FILENAME, mFileName);
        return json;
    }
    
    @Override
    public String toString() {
        return mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }
    
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
    
    public void setAudioFile(String fileName){
    	mFileName = fileName;
    }
    
    public String getAudioFile(){
    	return mFileName;
    }
}
