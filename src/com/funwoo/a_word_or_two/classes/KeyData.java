package com.funwoo.a_word_or_two.classes;


public class KeyData {
	private String UDPServerIP;
	private int UDPServerPort;
	private String loginURL;
	private String vistorLoginURL;
	private String checkNameURL;
	private String registerURL;
	private String sendMessageURL;
	private String sendFeedbackURL;
	private int clientBasePort; 
	public int ttl_vister;
	public int ttl_commonUser;
	public int ttl_system;
	private static KeyData instance = null; 
	private long timeDistance;
	private KeyData (){
//		UDPServerPort = 37777;
//		UDPServerIP = "192.168.1.111";
//		loginURL = "http://192.168.1.111:8080/JCY1/login";
//		vistorLoginURL = "http://192.168.1.111:8080/JCY1/visitorLogin";
//		checkNameURL = "http://192.168.1.111:8080/JCY1/usernameCheck";
//		registerURL = "http://192.168.1.111:8080/JCY1/register";
//		sendMessageURL = "http://192.168.1.111:8080/JCY1/newMessage";
//		sendFeedbackURL = "http://192.168.1.111:8080/JCY1/feedbackAdd";
	
		UDPServerPort = 37777;
		UDPServerIP = "115.28.244.62";
		loginURL = "http://115.28.244.62:8080/JCY1/login";
		vistorLoginURL = "http://115.28.244.62:8080/JCY1/visitorLogin";
		checkNameURL = "http://115.28.244.62:8080/JCY1/usernameCheck";
		registerURL = "http://115.28.244.62:8080/JCY1/register";
		sendMessageURL = "http://115.28.244.62:8080/JCY1/newMessage";
		sendFeedbackURL = "http://115.28.244.62:8080/JCY1/feedbackAdd";
//		
		clientBasePort = 3000;
		ttl_vister = 40;
		ttl_commonUser = 60;
		ttl_system = 60*30;
		timeDistance = 1000*60*60*24;
	}  

	public static KeyData SharedKeyData() {  
		if (instance == null) {  
			instance = new KeyData();  
		}  
		return instance;  
	}

	public String GetUDPServerIP()
	{
		return UDPServerIP;
	}
	
	public int GetUDPServerPort()
	{
		return UDPServerPort;
	}

	public String GetLoginURL()
	{
		return loginURL;
	}
	
	public String GetVistorLoginURL()
	{
		return vistorLoginURL;
	}
	

	public String GetCheckNameURL()
	{
		return checkNameURL;
	}

	public String GetRegisterURL()
	{
		return registerURL;
	}

	public String GetSendMessageURL()
	{
		return sendMessageURL;
	}
	
	public String GetSendFeedbackURL()
	{
		return sendFeedbackURL;
	}
	
	public int GetClientBasePort()
	{
		return clientBasePort;
	}
	
	public long GetTimeDistance()
	{
		return timeDistance;
	}
}
