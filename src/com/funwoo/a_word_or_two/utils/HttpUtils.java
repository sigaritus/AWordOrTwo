package com.funwoo.a_word_or_two.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.funwoo.a_word_or_two.classes.HTTPGetAble;





public class HttpUtils {

	public static String DoGetRequest(String urlStr, HTTPGetAble getableObj)
	{
		InputStreamReader ins = null;
		String response = "{\"returnState\":-1}";
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr+getableObj.GetGetString());
			
			System.out.println(urlStr+getableObj.GetGetString());
			
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			//			connection.setRequestProperty("accept", "*/*");
			//          connection.setRequestProperty("connection", "Keep-Alive");
			//          connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			int responseCode = connection.getResponseCode();
			if(200 == responseCode)
			{
				ins = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(ins);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				response = strBuffer.toString();
			}
			else
			{
				response = "{\"returnState\":" + responseCode + "}";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	//	private static String ChangeInputStream(InputStream inputStream) {
	//		// TODO Auto-generated method stub
	//		String jsonString = "";
	//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	//		int len = 0;
	//		byte[] data = new byte[1024];
	//		try {
	//			while ((len = inputStream.read(data)) != -1)
	//			{
	//				outputStream.write(data,0,len);
	//			}
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		jsonString = new String(outputStream.toByteArray());
	//		return jsonString;
	//	}

	public static String PostRequest(String urlStr, String json) {
		String response = "{\"returnState\":-1}";
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader ins = null;
		byte[] data = json.getBytes();

		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Length", String.valueOf(data.length));
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");

			connection.connect();

			DataOutputStream dos = new DataOutputStream(
					connection.getOutputStream());
			dos.write(data);
			dos.flush();
			dos.close();

			int responseCode = connection.getResponseCode();
			if(200 == responseCode)
			{
				ins = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(ins);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				response = strBuffer.toString();
			}
			else
			{
				response = "{\"returnState\":" + responseCode + "}";
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
}
