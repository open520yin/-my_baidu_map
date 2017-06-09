package com.example.thread;

import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import com.example.lib.HttpApi;
import com.example.lib.JsonUtil;

import android.os.Handler;
import android.os.Message;

public class Home implements Runnable {
	
	private Handler handler=null;
	private String m_szAndroidID;
	
	public Home(Handler hd,String szAndroidID) {
		handler = hd;
		m_szAndroidID = szAndroidID;
	}
	
	@Override  
    public void run() {  
        Message msg = new Message();  

		String api_data = HttpApi.getApi("http://106.14.254.252:83/index.php?c=mymap&a=isFirst&m_szAndroidID="+m_szAndroidID);
		Map<String, Object> json_list =  JsonUtil.getMapForJson(api_data);
		msg.obj = json_list;
		
		HttpApi.MyLog("注册验证"+json_list.get("msg").toString());
		handler.sendMessage(msg);
    }
}
