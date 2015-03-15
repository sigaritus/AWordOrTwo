package com.funwoo.a_word_or_two.message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.funwoo.a_word_or_two.classes.KeyData;


public class RegisterOnline{

	private DatagramSocket clientSocket;
	private String serverIP;
	private int serverPort;

//	private static RegisterOnline instance = null;  
	public RegisterOnline (DatagramSocket socket){
		clientSocket = socket;
		KeyData kd = KeyData.SharedKeyData();
		serverIP = kd.GetUDPServerIP();
		serverPort = kd.GetUDPServerPort();
	}  

//	public static RegisterOnline SharedRegisterOnline(DatagramSocket socket) {  
//		if (instance == null) {  
//			instance = new RegisterOnline(socket);
//		}  
//		return instance;  
//	}
//	
//	public static RegisterOnline SharedRegisterOnline() { 
//		return instance;  
//	}

	public void DoRegisterOnLine()
	{
		try {
			InetAddress inet = InetAddress.getByName(serverIP);
			String str = "";
			DatagramPacket sendPacket = new DatagramPacket(str.getBytes(),str.getBytes().length,inet,serverPort);
			clientSocket.send(sendPacket);

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}
}
