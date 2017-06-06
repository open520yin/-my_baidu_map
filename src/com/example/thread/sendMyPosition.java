package com.example.thread;

import java.util.Map;

import com.example.entity.Position;
import com.example.lib.HttpApi;

import android.R.string;
import android.provider.Settings.Secure;

public class sendMyPosition implements Runnable {
	
	private Position position; 
	
	public sendMyPosition(Position ps) {
		position = ps;
	}
	
	@Override  
    public void run() {  
         String set_str = HttpApi.getApi("http://106.14.254.252:83/index.php?c=mymap&a=setPosition&m_szAndroidID="+position.getM_szAndroidID()
	        		+"&position_r="+position.getPosition_r()
	        		+"&position_l="+position.getPosition_l()
	        		+"&address="+position.getAddress());
         HttpApi.MyLog(set_str);
		HttpApi.MyLog("发送位置yibu");
    } 
}
