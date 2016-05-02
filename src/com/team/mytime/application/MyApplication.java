package com.team.mytime.application;

import com.baidu.mapapi.SDKInitializer;
import com.team.mytime.Service.LocationService;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

public class MyApplication extends Application{

	public LocationService locationService;
    public Vibrator mVibrator;
	
	@Override
	public void onCreate() {
		super.onCreate();
		  //在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());  
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
	}
}
