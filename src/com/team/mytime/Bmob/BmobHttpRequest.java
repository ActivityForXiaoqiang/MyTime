package com.team.mytime.Bmob;

import android.content.Context;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class BmobHttpRequest {
	private final static String ApplicationID = "68f4988defcdff61326d845ba54939f4";

	public static void doBmobInit(Context context) {
		Bmob.initialize(context, ApplicationID);
	}

	public void doRegister(Context context, String email, String password) {
		MTUser user = new MTUser();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.signUp(context, new SaveListener() {

			@Override
			public void onSuccess() {

			}

			@Override
			public void onFailure(int arg0, String arg1) {

			}
		});

	}

	public void doLogin(Context context,String email, String password) {
		MTUser user=new MTUser();
		user.setUsername(email);
		user.setPassword(password);
		user.login(context, new SaveListener() {
			
			@Override
			public void onSuccess() {
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				
			}
		});
	}

}
