package com.funwoo.a_word_or_two.message;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.UnknownHostException;

import com.funwoo.a_word_or_two.R.id;
import com.funwoo.a_word_or_two.MainActivity;
import com.funwoo.a_word_or_two.classes.KeyData;
import com.funwoo.a_word_or_two.classes.LBMessage;
import com.funwoo.a_word_or_two.message.UIMessage.UIMessageType;
import com.funwoo.a_word_or_two.utils.JsonUtils;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class ClientMsg extends Thread {

	private Handler handler;
	private MainActivity mainActivity;

	private int basePort;
	private DatagramSocket clientSocket;
	private boolean socketAvaliable;
	private InnerClock innerClock = null;
	private int userID;
	//	private static ClientMsg instance = null;  
	public ClientMsg (MainActivity ma,Handler hd){
//		if(!ma.isDead)
		{
		mainActivity = ma;
		handler = hd;
		userID = -1;
		KeyData kd = KeyData.SharedKeyData();
		basePort = kd.GetClientBasePort();
		InitMultiThread();
		}
	}  

	private void InitMultiThread()
	{
		if(FindAvailablePort(0))
		{
			socketAvaliable = true;
			innerClock = new InnerClock(handler,clientSocket);
		}
		else
		{
			socketAvaliable = false;
		}
	}

	//	public static ClientMsg SharedClientMsg(MainActivity ma,Handler hd) {  
	//		if (instance == null) {  
	//			instance = new ClientMsg(ma,hd);
	//		}  
	//		return instance;  
	//	}

	public void SetUserID(int id)
	{
		this.userID = id;
	}


	private boolean FindAvailablePort(int time)
	{
		int port = basePort+time;
		time++;
		if(port>65535)
		{
			Toast.makeText(mainActivity, "没有可用端口", Toast.LENGTH_LONG).show();
			return false;
		}
		try {
			clientSocket = new DatagramSocket(port);
		} catch (BindException e) {
			FindAvailablePort(time);
		} catch (SocketException e) {
			socketAvaliable = false;
			Toast.makeText(mainActivity, "网络连接不可用", Toast.LENGTH_LONG).show();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void ReceiveAMessage()
	{
		if(socketAvaliable)
		{
			try {

				DatagramPacket receivePacket;

				receivePacket = new DatagramPacket(new byte[1024],1024);
				clientSocket.receive(receivePacket);
				String str = new String(receivePacket.getData()).trim();
				String newString = java.net.URLDecoder.decode(str);
				LBMessage lbm =JsonUtils.GetClassFromJSON(newString, LBMessage.class);
				if(lbm.GetUserID() != userID)
				{
					UIMessage uimsg = new UIMessage(UIMessageType.uimt_receiveMessage,lbm);
					Message message = Message.obtain();
					message.obj = uimsg;
					handler.sendMessage(message);
				}

			} catch (SocketException e) {
				e.printStackTrace();  
			} catch (IOException e) {
				e.printStackTrace();  
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			finally
			{
				if(null == clientSocket && socketAvaliable)
				{
					if(null != innerClock)
					{
						innerClock.StopClock();
					}
					InitMultiThread();
				}
			}
		}
	}
	@Override
	public void run() {
		while(true)
		{
			ReceiveAMessage();
		}
	}

	@Override
	public void interrupt() {
		
		if(socketAvaliable)
		{
			if(clientSocket != null)
			{
				socketAvaliable = false;
				clientSocket.close();
				clientSocket = null;
			}
		}

		if(null != innerClock)
		{
			innerClock.StopClock();
		}
		
		super.interrupt();
	}

	public void SendMsg(String message)
	{
		KeyData kd = KeyData.SharedKeyData();
		String serverIP = kd.GetUDPServerIP();
		int serverPort = kd.GetUDPServerPort();
		try {
			InetAddress inet = InetAddress.getByName(serverIP);
			DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.getBytes().length,inet,serverPort);
			clientSocket.send(sendPacket);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
