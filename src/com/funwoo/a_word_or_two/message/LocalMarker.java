package com.funwoo.a_word_or_two.message;


import com.amap.api.maps.model.Marker;

public class LocalMarker {

	public Marker marker;
	int userID;
	public long time;
	public LocalMarker(Marker marker,long time, int id)
	{
		this.marker = marker;
		this.time = time;
		this.userID = id;
	}
	
//	public LocalMessage(Marker marker,t_userType userType)
//	{
//		this.marker = marker;
//		KeyData kd = KeyData.SharedKeyData();
//		switch(userType)
//		{
//		case ut_visitor:
//			ttl = kd.ttl_vister;
//			break;
//		case ut_commonUserMale:
//		case ut_commonUserFemale:
//		case ut_commonUserUnknow:
//			ttl = kd.ttl_commonUser;
//			break;
//		case ut_system:
//			ttl = kd.ttl_system;
//			break;
//		default: ttl = 0;
//		}
//	}

}
