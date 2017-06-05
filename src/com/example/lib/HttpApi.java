package com.example.lib;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class  HttpApi {
	
	public static String getApi(String url){  
        String cont = null;  
        HttpGet httpGet = new HttpGet(url);                       
        DefaultHttpClient httpClient = new DefaultHttpClient();   
        try {  
            HttpResponse httpResponse = httpClient.execute(httpGet);   
            int reCode = httpResponse.getStatusLine().getStatusCode();  
            if (reCode == HttpStatus.SC_OK) {  
                cont = EntityUtils.toString(httpResponse.getEntity());  
                return cont;  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return "";  
	}
	
	public static void MyLog(String log){
		Log.d("myapp_cunzhang", log);
	}
}
