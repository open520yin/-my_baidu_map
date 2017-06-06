package com.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.entity.Position;
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
        
        //加入到Position里
        List<Map<String, Object>> json_list =  JsonUtil.getlistForJson(api_data);
        
        List<Position> positions_list = new ArrayList<Position>();
        for (Map<String, Object> map : json_list) {
        	Position position = new Position();
        	position.setName(map.get("name").toString());
        	position.setPosition_l(Double.valueOf(map.get("position_l").toString()));
        	position.setPosition_r(Double.valueOf(map.get("position_r").toString()));
        	position.setAddress(map.get("address").toString());
        	position.setM_szAndroidID(map.get("m_szAndroidID").toString());
        	positions_list.add(position);
		}
        
        
        msg.setData(data);  
        msg.obj = positions_list;
        handler.sendMessage(msg);  
    }
	
	
}
