package com.example.baidu;

import java.util.Map;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.example.lib.HttpApi;
import com.example.thread.Home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.view.Window;
import android.widget.GridView;

public class HomeActivity extends Activity {  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏  
		setContentView(R.layout.activity_home);
		HttpApi.MyLog("进入homeActivity。。");
		
		//异步加载
        String m_szAndroidID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        Home home = new Home(Home_handler,m_szAndroidID);
	    new Thread(home).start();
	}
	
	/*
	 * 异步初始化界面
	 * */
	@SuppressLint("HandlerLeak")
	Handler Home_handler = new Handler(){

		@Override  
	    public void handleMessage(Message msg) {  
	        super.handleMessage(msg);  
	        @SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) msg.obj;
	        HttpApi.MyLog("验证结果："+map.get("isFirst").toString());
        	Intent intent = new Intent();
	        if (Boolean.valueOf(map.get("isFirst").toString()) ) {
		        intent.setClass(HomeActivity.this, RegisterActivity.class);
			} else {
		        intent.setClass(HomeActivity.this, MainActivity.class);
			}
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        startActivity(intent);

	    }  
	};
	
    

}
