package com.funwoo.a_word_or_two.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.R.id;
import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;

import com.amap.api.maps.model.Marker;
import com.funwoo.a_word_or_two.classes.KeyData;
import com.funwoo.a_word_or_two.classes.LBMessage;
import com.funwoo.a_word_or_two.classes.LBMessage.t_userType;
import com.funwoo.a_word_or_two.utils.JsonUtils;

public class MarkerManager {

	private ReadWriteLock lock;
	private static MarkerManager instance = null;
	private Map<Integer,LocalMarker> markerMap;
	private Map<Integer,LBMessage> msgMap;
	private KeyData keyData;

	private MarkerManager(){
		lock = new ReentrantReadWriteLock();
		markerMap = new HashMap<Integer,LocalMarker>();
		msgMap = new HashMap<Integer,LBMessage>();
		keyData = KeyData.SharedKeyData();
	}  

	public static MarkerManager SharedMarkerManager() {  
		if (instance == null) {  
			instance = new MarkerManager();
		}  
		return instance;  
	}

	public ArrayList<Marker> AddMarkerToManager(Marker marker,LBMessage msg)
	{
		lock.writeLock().lock();
		ArrayList<Marker> markers = new ArrayList<Marker>();
		LocalMarker lm =new LocalMarker(marker, msg.GetTime(), msg.GetUserID());
		try {
			msgMap.put(msg.GetUserID(), msg);
			if(markerMap.containsKey(msg.GetUserID()))
			{
				markers.add(markerMap.get(msg.GetUserID()).marker);
			}
			markerMap.put(msg.GetUserID(), lm);
		} finally {  
			lock.writeLock().unlock();
		}  
		return markers;
	}

	public ArrayList<Marker> DecreaseMarkerProgressively()
	{
		lock.writeLock().lock();
		ArrayList<Marker> markers = new ArrayList<Marker>();
		try {

			Iterator itMsg=msgMap.values().iterator();      
			while(itMsg.hasNext())
			{
				LBMessage msg = (LBMessage)itMsg.next();
				if(new Date().getTime() - msg.GetTime() > keyData.GetTimeDistance())
				{
					itMsg.remove();					
				} 
			}  

			Iterator itMarker=markerMap.values().iterator();      
			while(itMarker.hasNext())
			{
				LocalMarker lm = (LocalMarker)itMarker.next();
				if(new Date().getTime() - lm.time > keyData.GetTimeDistance())
				{
					markers.add(lm.marker);
					itMarker.remove();					
				} 
			}  
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} finally {  
			lock.writeLock().unlock();
		} 
		return markers;
	}

	//	public ArrayList<Marker> DecreaseMarkerProgressively()
	//	{
	//		lock.writeLock().lock();
	//		ArrayList<Marker> markers = new ArrayList<Marker>();
	//		try {
	//			Iterator it=msgMap.values().iterator();      
	//			while(it.hasNext())
	//			{
	//				LocalMessage lm = (LocalMessage)it.next();
	//				lm.ttl--;
	//				if(0 >= lm.ttl)
	//				{
	//					markers.add(lm.marker);
	//					it.remove();					
	//				} 
	//			}  
	//		} finally {  
	//			lock.writeLock().unlock();
	//		} 
	//		return markers;
	//	}

	public HashSet<String> GetLocalMarkers()
	{		
		lock.writeLock().lock();
		HashSet<String> messages = new HashSet<String>();
		try {
			Iterator it=msgMap.values().iterator();  
			int i = 0;
			while(it.hasNext())
			{
				LBMessage msg = (LBMessage)it.next();
				String tempStr = JsonUtils.CreatJsonStrng(msg);
				messages.add(tempStr);
				i++;
			}  
		} finally {  
			lock.writeLock().unlock();
		} 

		return messages;
	}

	public ArrayList<Marker> ClearScreen()
	{
		lock.writeLock().lock();
		ArrayList<Marker> markers = new ArrayList<Marker>();
		try {

			msgMap.clear();
			
			Iterator itMarker=markerMap.values().iterator();      
			while(itMarker.hasNext())
			{
				LocalMarker lm = (LocalMarker)itMarker.next();
				markers.add(lm.marker);
				itMarker.remove();	
			} 
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {  
			lock.writeLock().unlock();
		} 
		return markers;
	}
}
