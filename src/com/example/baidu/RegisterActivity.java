package com.example.baidu;

import com.example.lib.HttpApi;

import android.app.Activity;
import android.os.Bundle;

public class RegisterActivity extends Activity {  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		HttpApi.MyLog("进入RegisterActivity。。");
	}

}
