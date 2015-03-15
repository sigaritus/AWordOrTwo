package com.funwoo.a_word_or_two.classes;

public class LoginUser implements HTTPGetAble{
	private String userName,password;
	public LoginUser(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String GetGetString() {
		String getString = "?userName=" + java.net.URLEncoder.encode(userName)
				+ "&password=" + password;
		return getString;
	}

}
