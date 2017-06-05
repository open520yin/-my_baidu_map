package com.example.baidu;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.example.lib.HttpApi;
import com.example.lib.JsonUtil;
import com.example.thread.sendMyPosition;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {  
	private TextView tv_option;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private List<Bean> mapBeans;

	//定位相关
	private LocationClient mLocClient;
	private boolean isFirstLoc = true;//是否首次定位
	private MyLocationListenner myListener = new MyLocationListenner();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_option = (TextView) findViewById(R.id.tv_option);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		//开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		//定位初始化
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开GPS
		option.setCoorType("bd09ll");//设置坐标类型
		option.setScanSpan(2000);//设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效	
		option.setIsNeedAddress(true);//设置地址信息，默认无地址信息
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(3).build()));

//        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//			
//			@Override
//			public boolean onMarkerClick(Marker marker) {
//				return true;
//			}
//		});
//		
		
		
	    new Thread(networkTask).start();  
//	    Thread thread = new networkTask();
//        thread.start();   
		
	}
	
	/*
	 * 获取到数据源更新地图make
	 * */
	Handler handler = new Handler() {  
	    @Override  
	    public void handleMessage(Message msg) {  
	        HttpApi.MyLog("回调情况～～～star");

	        super.handleMessage(msg);  
	        
	        List<Map<String, Object>> json_list = (List<Map<String, Object>>) msg.obj;
	        for (Map<String, Object> map : json_list) {
				HttpApi.MyLog(map.get("name").toString());
			}
	        
	    	initData(json_list);

	        
	        HttpApi.MyLog("回调情况～～～end");
	        // TODO  
	        // UI界面的更新等相关操作  
	    }  
	};  
	
	/** 
	 * 获取数据源
	 */  
	Runnable networkTask = new Runnable() {  
	    @Override  
	    public void run() {  
	        // TODO  
	        // 在这里进行 http request.网络请求相关操作  
	        Message msg = new Message();  
	        Bundle data = new Bundle(); 
	        
//	        String api_data = HttpApi.getApi("http://bit.le-gou.com/test.php");
	        String api_data = HttpApi.getApi("http://106.14.254.252:83/index.php?c=mymap&a=getPosition");

	        HttpApi.MyLog(api_data);
	        
	        List<Map<String, Object>> json_list =  JsonUtil.getlistForJson(api_data);
	        
	        
	        
	        msg.setData(data);  
	        msg.obj = json_list;
	        handler.sendMessage(msg);  
	    }  
	};  

	/*
	 * 添加marker
	 * */
	BitmapDescriptor bdGround = BitmapDescriptorFactory.fromResource(R.drawable.ground_overlay);
	private void initData(List<Map<String, Object>>  json_list) {
		//添加marker
		for (Map<String, Object> map : json_list) {			
			LatLng latLng = new LatLng(Double.valueOf(map.get("position_l").toString()), Double.valueOf(map.get("position_r").toString()));
			
			View view = View.inflate(getApplicationContext(), R.layout.item_bean, null);
			TextView tView = (TextView)view.findViewById(R.id.item_bean);
			tView.setText(map.get("name").toString() + "");

			//将View转化为Bitmap
			BitmapDescriptor descriptor = BitmapDescriptorFactory.fromView(view);
			OverlayOptions options = new MarkerOptions().position(latLng).icon(descriptor).draggable(true);
			mBaiduMap.addOverlay(options);
			
			HttpApi.MyLog(map.get("name").toString()+","+map.get("position_l").toString()+","+map.get("position_r").toString());
			HttpApi.MyLog(Double.valueOf(map.get("position_l").toString()).toString());
		}
		
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(3).build()));
	    
	}
	
    /**
     * 定位SDK监听函数
     */
	private class MyLocationListenner implements BDLocationListener{
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			// map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
            .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
            .direction(100).latitude(location.getLatitude())
            .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                tv_option.setText(ll +"详情地点：" + location.getAddrStr() + "   城市：" +location.getCity());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(13.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build())); 
                HttpApi.MyLog(location.getLatitude()+","+ location.getLongitude() +"详情地点：" + location.getAddrStr() + "   城市：" +location.getCity());
        		
                //发送位置异步
                sendMyPosition sr1 = new sendMyPosition();  
                new Thread(sr1).start();
                
            }
		}
	}
	
	
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
		mBaiduMap = null;
		bdGround.recycle();
	}
  
}

