package com.funwoo.a_word_or_two.message;

public class UIMessage {

	public enum UIMessageType
	{
		uimt_login,
		uimt_vistorLogin,
		uimt_nameCheck,
		uimt_register,
		uimt_sendMessage,
		uimt_sendFeedback,
		uimt_receiveMessage,
		uimt_removeMessage;
		
	}
	
	public UIMessageType messageType;
	public Object content;
	
	public UIMessage(UIMessageType mt, Object content)
	{
		messageType = mt;
		this.content = content;
	}
}
