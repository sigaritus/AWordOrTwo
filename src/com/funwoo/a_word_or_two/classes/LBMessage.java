package com.funwoo.a_word_or_two.classes;

import android.R.integer;

import com.funwoo.a_word_or_two.utils.JsonUtils;

public class LBMessage implements HTTPGetAble{

	 public enum t_userType {
		 ut_visitor,ut_commonUserMale,ut_commonUserFemale,ut_commonUserUnknow,ut_myself,ut_system;
	    }
	private t_userType userType;
	private int userID;
	private String userName;
	private String content;
	private JCYLocation location;
	private int portraitType;
	private long time;
	
	public LBMessage(t_userType userType, int userID, String userName, String message, JCYLocation userLocation, int portraitType, long time)
	{
		this.portraitType = portraitType;
		this.userType = userType;
		this.userID = userID;
		this.userName = userName;
		content = message;
		location = userLocation;
		this.time = time;
	}
	
	public t_userType GetUserType()
	{
		return userType;
	}
	
	public int GetUserID()
	{
		return userID;
	}
	
	public String GetUserName()
	{
		return userName;
	}
	
	public String GetMessage()
	{
		return content;
	}
	
	public JCYLocation GetLocation()
	{
		return location;
	}
	
	public int GetPortraitType()
	{
		return portraitType;
	}
	
	public long GetTime()
	{
		return time;
	}

	@Override
	public String GetGetString() {		
		String getString = "?message=" + java.net.URLEncoder.encode(JsonUtils.CreatJsonStrng(this));
//		String getString = "?message=" + JsonUtils.CreatJsonStrng(this);
//		System.out.println(getString);
		return getString;
	}
	
	
}
