package com.team.mytime.Activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.team.mytime.R;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends BaseActivity implements OnClickListener {
	MapView mMapView = null;
	BaiduMap mBaidumap;
	MapStatus mMapStatus = null;
	MapStatusUpdate mMapStatusUpdate;
	private LinearLayout searchLinLayout;
	public final static int SEARCH_CODE = 111;
	private TextView searchAddress;
	private String cityname = "";

	public LocationClient mLocationClient = null;
	private Vibrator mVibrator;
	private NotifyLister mNotifyer = null;
	
	private boolean haveNotify = false;
	
	private LatLng myLatLng = null;
	private NtfBean ntfBean = null;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_baidu_map);
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		searchAddress = (TextView) findViewById(R.id.searchAddress);
		searchLinLayout = (LinearLayout) findViewById(R.id.search);
		searchLinLayout.setOnClickListener(this);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaidumap = mMapView.getMap();
		// 开启交通图
		mBaidumap.setTrafficEnabled(true);
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(mListener); // 注册监听函数
	}

	Overlay myicon = null;
	Overlay mbicon = null;

	/**
	 * map调到指定坐标
	 * 
	 * @param lat
	 * @param lng
	 */
	private void moToCenter(double lat, double lng) {
		LatLng cenpt = new LatLng(lat, lng);
		// 定义地图状态
		mMapStatus = new MapStatus.Builder().target(cenpt).zoom(18).build();
		// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaidumap.setMapStatus(mMapStatusUpdate);
	}


	private void setIndex(double lat, double lng, boolean isstart) {
		// 设定中心点坐标
		if (mMapStatus == null) {
			moToCenter(lat, lng);
		}
		// 定义Maker坐标点
		LatLng point = new LatLng(lat, lng);
		// 构建Marker图标
		BitmapDescriptor bitmap;
		if (isstart) {
			bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
		} else {
			bitmap = BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_search);
		}
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
		// 在地图上添加Marker，并显示
		if (isstart) {
			if (myicon != null) {
				myicon.remove();
			}
			myicon = mBaidumap.addOverlay(option);
		} else {
			if (mbicon != null) {
				mbicon.remove();
			}
			mbicon = mBaidumap.addOverlay(option);
			moToCenter(lat, lng);
		}
	}

	/***
	 * Stop location service
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	
	private void addNotifyLocation(double lat,double lnt){
		//位置提醒相关代码
		if(mNotifyer == null){
			mNotifyer = new NotifyLister();
			mNotifyer.SetNotifyLocation(lat,lnt,1000,mLocationClient.getLocOption().getCoorType());//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
			mLocationClient.registerNotify(mNotifyer);
		}else{
			mNotifyer.SetNotifyLocation(lat,lnt,1000,mLocationClient.getLocOption().getCoorType());//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
		}
		//注册位置提醒监听事件后，可以通过SetNotifyLocation 来修改位置提醒设置，修改后立刻生效。
		//BDNotifyListner实现
		//取消位置提醒
//		mLocationClient.removeNotifyEvent(mNotifyer);
	};
	public class NotifyLister extends BDNotifyListener{
		public void onNotify(BDLocation mlocation, float distance){
			mVibrator.vibrate(1000);//振动提醒已到设定位置附近
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(mListener); // 注册监听函数
		initLocation();											// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
		mLocationClient.start();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	/*****
	 * @see copy funtion to you project
	 *      定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 *
	 */
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				/**
				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
				 */
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				sb.append("\nCountryCode : ");
				sb.append(location.getCountryCode());
				sb.append("\nCountry : ");
				sb.append(location.getCountry());
				sb.append("\ncitycode : ");
				sb.append(location.getCityCode());
				sb.append("\ncity : ");
				sb.append(location.getCity());
				cityname = location.getCity();
				sb.append("\nDistrict : ");
				sb.append(location.getDistrict());
				sb.append("\nStreet : ");
				sb.append(location.getStreet());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\nDescribe: ");
				sb.append(location.getLocationDescribe());
				sb.append("\nDirection(not all devices have value): ");
				sb.append(location.getDirection());
				sb.append("\nPoi: ");
				if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
					for (int i = 0; i < location.getPoiList().size(); i++) {
						Poi poi = (Poi) location.getPoiList().get(i);
						sb.append(poi.getName() + ";");
					}
				}
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 单位：km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 单位：米
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					// 运营商信息
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("网络定位成功");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\ndescribe : ");
					sb.append("离线定位成功，离线定位结果也是有效的");
				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				}
				setIndex(location.getLatitude(), location.getLongitude(), true);
				myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
				if(haveNotify){
					LatLng p1 = ntfBean.latLng;
					LatLng p2 = myLatLng;
					int m = (int) DistanceUtil. getDistance(p1, p2);
					searchAddress.setText("距离："+ntfBean.name+"("+m+"M)");
				}
			}
		}

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			if(cityname.equals("")){
				Toast.makeText(MapActivity.this, "定位有误", 1).show();
			}else{
				Intent it = new Intent(MapActivity.this, SearchActivity.class);
				it.putExtra("cityname", cityname);
				startActivityForResult(it, SEARCH_CODE);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent it) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, it);
		switch (arg0) {
		case SEARCH_CODE:
			if (arg1 == SearchActivity.GD_OKCODE) {
				haveNotify = true;
				double lat = Double.parseDouble(it.getStringExtra("lat"));
				double lng = Double.parseDouble(it.getStringExtra("lnt"));
				setIndex(lat, lng, false);
				addNotifyLocation(lat,lng);
				ntfBean = new NtfBean();
				ntfBean.latLng = new LatLng(lat, lng);
				ntfBean.name = it.getStringExtra("address");
				LatLng p2 = myLatLng;
				
				int m = (int) DistanceUtil. getDistance(ntfBean.latLng, p2);
				searchAddress.setText("距离："+ntfBean.name+"("+m+"M)");
				Toast.makeText(MapActivity.this, "到了我会提醒你哦", 1).show();
			}
			break;

		default:
			break;
		}
	}
}
class NtfBean{
	LatLng latLng;
    String name;
}