package com.example.thread;

import java.util.List;
import java.util.Map;

import com.example.lib.HttpApi;
import com.example.lib.JsonUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class initData implements Runnable {
    
	private Handler handler=null;
	
	public initData(Handler hd) {
		handler = hd;
	}
	
	@Override  
	public void run() {  
        // TODO  
        // 在这里进行 http request.网络请求相关操作  
        Message msg = new Message();  
        Bundle data = new Bundle(); 
        
//        String api_data = HttpApi.getApi("http://bit.le-gou.com/test.php");
        String api_data = HttpApi.getApi("http://106.14.254.252:83/index.php?c=mymap&a=getPosition");

        HttpApi.MyLog(api_data);
        
        List<Map<String, Object>> json_list =  JsonUtil.getlistForJson(api_data);
        
        
        
        msg.setData(data);  
        msg.obj = json_list;
        handler.sendMessage(msg);  
    }
	
	
}
