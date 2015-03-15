package com.funwoo.a_word_or_two.classes;



import java.io.Serializable;

import com.funwoo.a_word_or_two.message.UIMessage;
import com.funwoo.a_word_or_two.message.UIMessage.UIMessageType;
import com.funwoo.a_word_or_two.utils.HttpUtils;
import com.funwoo.a_word_or_two.utils.JsonUtils;

import android.os.Handler;
import android.os.Message;

public class DataExchange {

	private Handler handler;
	private KeyData keyData;
	public DataExchange(Handler handler)
	{
		this.handler = handler;
		keyData = KeyData.SharedKeyData();
	}

	private void SendExchangeResultToUI(UIMessage result)
	{
		Message message = Message.obtain();
		message.obj = result;
		handler.sendMessage(message);
	}

	private int GetReturnCode(String str)
	{
		int returnCode = 1003;
		ServerResponse sr = JsonUtils.GetClassFromJSON(str, ServerResponse.class);
		returnCode = sr.returnState;
		return returnCode;
	}

	public void CheckUserName(UserNameClass name)
	{
		String resultStr = HttpUtils.DoGetRequest(keyData.GetCheckNameURL(), name);
		int retCode = GetReturnCode(resultStr);
		UIMessage result = new UIMessage(UIMessageType.uimt_nameCheck,retCode);
		SendExchangeResultToUI(result);
	}

	public void Register(RegisterUser ru)
	{
		String resultStr = HttpUtils.DoGetRequest(keyData.GetRegisterURL(), ru);
		int retCode = GetReturnCode(resultStr);
		UIMessage result = new UIMessage(UIMessageType.uimt_register,retCode);
		SendExchangeResultToUI(result);
	}

	public void Login(LoginUser lu)
	{
		String resultStr = HttpUtils.DoGetRequest(keyData.GetLoginURL(), lu);
		int retCode = GetReturnCode(resultStr);
		UIMessage result = new UIMessage(UIMessageType.uimt_login,retCode);
		SendExchangeResultToUI(result);
	}

	public void VisitorLogin()
	{
		UserNameClass name = new UserNameClass("游客");
		String resultStr = HttpUtils.DoGetRequest(keyData.GetVistorLoginURL(), name);
		int retCode = GetReturnCode(resultStr);
		UIMessage result = new UIMessage(UIMessageType.uimt_vistorLogin,retCode);
		SendExchangeResultToUI(result);
	}

	public void SendMessage(LBMessage msg)
	{
		String resultStr = HttpUtils.DoGetRequest(keyData.GetSendMessageURL(), msg);
		int retCode = GetReturnCode(resultStr);
		UIMessage result = new UIMessage(UIMessageType.uimt_sendMessage,retCode);
		SendExchangeResultToUI(result);
	}
	
	public void SendFeedback(Feedback fbmsg)
	{
		String resultStr = HttpUtils.DoGetRequest(keyData.GetSendFeedbackURL(), fbmsg);
		int retCode = GetReturnCode(resultStr);
		UIMessage result = new UIMessage(UIMessageType.uimt_sendFeedback,retCode);
		SendExchangeResultToUI(result);
	}
}
