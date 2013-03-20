package cn.eoe.app.https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.NameValuePair;

import android.util.Log;

public class CustomHttpURLConnection {
	private static String TAG = "CustomHttpUrlConnection";
	private static HttpURLConnection conn;
	
	public CustomHttpURLConnection() {
	}

	public static String GetFromWebByHttpUrlConnection(String strUrl,
			NameValuePair... nameValuePairs) {
		String result="";
		try {
			URL url = new URL(strUrl);
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(4000);
			conn.setRequestProperty("accept", "*/*");
//			int resCode=conn.getResponseCode();
			conn.connect();
			InputStream stream=conn.getInputStream();
			InputStreamReader inReader=new InputStreamReader(stream);
			BufferedReader buffer=new BufferedReader(inReader);
			String strLine=null;
			while((strLine=buffer.readLine())!=null)
			{
				result+=strLine;
			}
			inReader.close();
			conn.disconnect();
			return result;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "getFromWebByHttpUrlCOnnection:"+e.getMessage());
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "getFromWebByHttpUrlCOnnection:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static String PostFromWebByHttpURLConnection(String strUrl,
			NameValuePair... nameValuePairs) {
		String result="";
		try {
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url
					.openConnection();
			// 设置是否从httpUrlConnection读入，默认情况下是true; 
			conn.setDoInput(true);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在   
			// http正文内，因此需要设为true, 默认情况下是false; 
			conn.setDoOutput(true);
			// 设定请求的方法为"POST"，默认是GET 
			conn.setRequestMethod("POST");
			//设置超时
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(4000);
			// Post 请求不能使用缓存 
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设定传送的内容类型是可序列化的java对象   
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)  
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
//			urlConn.connect();
			
			InputStream in = conn.getInputStream();
			InputStreamReader inStream=new InputStreamReader(in);
			BufferedReader buffer=new BufferedReader(inStream);
			String strLine=null;
			while((strLine=buffer.readLine())!=null)
			{
				result+=strLine;
			}
			return result;
		} catch (IOException ex) {
			Log.e(TAG,"PostFromWebByHttpURLConnection："+ ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

}
