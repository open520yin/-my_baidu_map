package com.example.baidu;


import java.util.ArrayList;
import java.util.List;

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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
//		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_main);

		tv_option = (TextView) findViewById(R.id.tv_option);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(13.0f);
		mBaiduMap.setMapStatus(msu);
		
		//开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		//定位初始化
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开GPS
		option.setCoorType("bd09ll");//设置坐标类型
		option.setScanSpan(10000);//设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效	
		option.setIsNeedAddress(true);//设置地址信息，默认无地址信息
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				return true;
			}
		});
		
		initData();
	}
	
	
	BitmapDescriptor bdGround = BitmapDescriptorFactory.fromResource(R.drawable.ground_overlay);

	private void initData() {
		// TODO Auto-generated method stub
		//设置中心点
		LatLng southwest = new LatLng(39.92235, 116.380338);
        LatLng northeast = new LatLng(39.947246, 116.414977);
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
        OverlayOptions ooGround = new GroundOverlayOptions().positionFromBounds(bounds).image(bdGround).transparency(0.7f);
        mBaiduMap.addOverlay(ooGround);
		//添加marker
		mapBeans = new ArrayList<Bean>();
		mapBeans.add(new Bean(1, "皖111111", "111", 30.82943, 116.89586));
		mapBeans.add(new Bean(2, "皖222222", "222", 39.942821, 116.369199));
		mapBeans.add(new Bean(3, "皖333333", "333", 39.939723, 116.425541));
		mapBeans.add(new Bean(4, "皖444444", "444", 39.906965, 116.401394));
		mapBeans.add(new Bean(5, "皖555555", "555", 39.82943, 116.42586));
		mapBeans.add(new Bean(6, "皖666666", "666", 38.92943,116.89586));
		mapBeans.add(new Bean(7, "皖777777", "777", 39.92235, 116.414977));
		mapBeans.add(new Bean(8, "皖888888", "888", 39.947246, 116.89586));
		for(Bean bean : mapBeans){
			LatLng latLng = new LatLng(bean.getLatitude(), bean.getLongitude());
			Bundle bundle = new Bundle();
			bundle.putSerializable("BEAN", bean);
			View view = View.inflate(getApplicationContext(), R.layout.item_bean, null);
			TextView tView = (TextView)view.findViewById(R.id.item_bean);
			tView.setText(bean.getNumber() + "");
			//将View转化为Bitmap
			BitmapDescriptor descriptor = BitmapDescriptorFactory.fromView(view);
			OverlayOptions options = new MarkerOptions().position(latLng).icon(descriptor).extraInfo(bundle).zIndex(9).draggable(true);
			mBaiduMap.addOverlay(options);
		}
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
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
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

