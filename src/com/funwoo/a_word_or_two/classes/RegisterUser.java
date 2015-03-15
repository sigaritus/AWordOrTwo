package com.funwoo.a_word_or_two.classes;

import java.net.URLEncoder;

public class RegisterUser implements HTTPGetAble{
	private String userName,password;
	public RegisterUser(String userName,String password)
	{
		this.userName = userName;
		this.password = password;
	}
	
	@Override
	public String GetGetString() {
		String getString = "?userName=" + java.net.URLEncoder.encode(userName) + "&password=" + password;
		return getString;
	}
}
