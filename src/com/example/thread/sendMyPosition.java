package com.example.thread;

import com.example.lib.HttpApi;

import android.provider.Settings.Secure;

public class sendMyPosition implements Runnable {
	@Override  
    public void run() {  
//    	 String m_szAndroidID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
//         String set_str = HttpApi.getApi("http://106.14.254.252:83/index.php?c=mymap&a=setPosition&m_szAndroidID="+m_szAndroidID
//	        		+"&position_r="+location.getLongitude()
//	        		+"&position_l="+location.getLatitude()
//	        		+"&address="+location.getAddrStr());
//         HttpApi.MyLog(set_str);
		HttpApi.MyLog("发送位置yibu");
    } 
}
