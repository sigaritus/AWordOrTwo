package com.funwoo.a_word_or_two.utils;

import com.google.gson.Gson;

public class JsonUtils {
	public static String CreatJsonStrng(Object value)
	{
		Gson gson = new Gson();
		String str = gson.toJson(value);
		return str;
	}

	public static <T> T GetClassFromJSON(String str, Class<T> cls)
	{
		T t = null;
		try{
			Gson gson = new Gson();
			t = gson.fromJson(str, cls);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return t;
	}   
}
