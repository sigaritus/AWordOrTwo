package com.funwoo.a_word_or_two.classes;

import android.R.integer;
import android.R.string;

public class Feedback implements HTTPGetAble {
	public int userID;
	public JCYLocation location;
	public String content;

	public Feedback(int userID, JCYLocation location, String content)
	{
		this.userID = userID;
		this.location = location;
		this.content = content;
	}

@Override
public String GetGetString() {
	String getString = "?userID=" + userID + "&latitude=" + location.latitude + "&longitude=" + 
location.longitude + "&content=" + java.net.URLEncoder.encode(content);
	return getString;
}

}