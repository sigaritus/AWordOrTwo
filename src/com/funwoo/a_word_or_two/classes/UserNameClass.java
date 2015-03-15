package com.funwoo.a_word_or_two.classes;

public class UserNameClass implements HTTPGetAble{

	public String userName;
	public UserNameClass(String un)
	{
		userName = un;
	}
	
	@Override
	public String GetGetString() {
		String getString = "?userName=" + java.net.URLEncoder.encode(userName);
		return getString;
	}
}
