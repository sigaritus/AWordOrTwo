package com.funwoo.a_word_or_two.message;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.amap.api.maps.model.Marker;
import com.funwoo.a_word_or_two.message.UIMessage.UIMessageType;

import android.os.Handler;
import android.os.Message;

public class InnerClock {
//	private static InnerClock instance = null;
	private Timer timer = null;
	private static int counter;
	private MarkerManager markerManager;
	private Handler handler;
	private DatagramSocket socket;
	private RegisterOnline register;
	public InnerClock(Handler handler, DatagramSocket socket)
	{
		counter = 0;
		this.handler = handler;
		this.socket = socket;
		markerManager = MarkerManager.SharedMarkerManager();
		register = new RegisterOnline(socket);
		StartClock();
	}

//	public static InnerClock SharedInnerClock(Handler handler) {  
//		if (instance == null) {  
//			instance = new InnerClock(handler);
//		}  
//		return instance;  
//	}

	public void StartClock()
	{
		if(null == timer)
		{
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					ClockWork();
				}
			},0,1000*170);
		}
	}

	public void StopClock()
	{
		if(null != timer)
		{
			timer.cancel();
			timer = null;
		}
	}

	private void ClockWork()
	{
		register.DoRegisterOnLine();

		ArrayList<Marker> markers = markerManager.DecreaseMarkerProgressively();
		if(markers.size() > 0)
		{
			UIMessage uimsg = new UIMessage(UIMessageType.uimt_removeMessage,markers);
			Message message = Message.obtain();
			message.obj = uimsg;
			handler.sendMessage(message);
		}
	}
}
