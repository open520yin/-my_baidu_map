package com.example.baidu;

import java.util.Map;

import org.apache.http.Header;

import com.example.entity.Position;
import com.example.lib.HttpApi;
import com.example.lib.JsonUtil;
import com.example.thread.register;

import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		HttpApi.MyLog("进入RegisterActivity。。");
		
		Button button = (Button) findViewById(R.id.btn_register);
		OnClickListener onClickListener = new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText editext=(EditText) findViewById(R.id.edit_register_name);
				String name =  editext.getText().toString();
				if (name.length() == 0) {
					Toast.makeText(getApplicationContext(), "必须输入您的大名啊～～", Toast.LENGTH_SHORT).show();
				} else {
					
					String m_szAndroidID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
					Position position = new Position();
					position.setM_szAndroidID(m_szAndroidID);
					position.setName(name);
					register register =new register(position,reg_handler);
					new Thread(register).start();
					
				}
			}
		};
		button.setOnClickListener(onClickListener);

	}
	
    @SuppressLint("HandlerLeak")
	@SuppressWarnings("unchecked")
	Handler reg_handler = new Handler(){
		@Override  
	    public void handleMessage(Message msg) {  
	        super.handleMessage(msg);
			Map<string, Object> map = (Map<string, Object>) msg.obj;
			
			if(Integer.valueOf(map.get("code").toString()).equals(200)){
				Intent intent = new Intent();
		        intent.setClass(RegisterActivity.this, MainActivity.class);
		        startActivity(intent);
			}
			
			HttpApi.MyLog("注册结果"+map.get("msg").toString());
        }
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
	}

}
