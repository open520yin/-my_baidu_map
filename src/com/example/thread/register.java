package com.example.thread;

import java.util.Map;

import com.example.entity.Position;
import com.example.lib.HttpApi;
import com.example.lib.JsonUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class register implements Runnable {
	Position position;
	private Handler handler=null;

	public register(Position ps,Handler hand) {
		position = ps;
		handler = hand;
	}
	
	@Override  
    public void run() {  
		Message msg = new Message();  
        
		String string = HttpApi.getApi("http://106.14.254.252:83/index.php?c=mymap&a=register"+
		"&m_szAndroidID="+position.getM_szAndroidID()+
		"&name="+position.getName()
				);
		Map<String, Object> map = JsonUtil.getMapForJson(string);
		
		msg.obj = map;
		handler.sendMessage(msg);
    } 
}
