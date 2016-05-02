package com.team.mytime.Bmob;

import com.team.mytime.Activity.LoginActivity;
import com.team.mytime.Activity.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class BmobHttpRequest {
	private final static String ApplicationID = "68f4988defcdff61326d845ba54939f4";

	public static void doBmobInit(Context context) {
		Bmob.initialize(context, ApplicationID);
	}

	public void doRegister(final Context context, String email, String password, final Activity activity) {
		MTUser user = new MTUser();
		user.setUsername(email);
		user.setPassword(password);
		// user.setEmail(email);
		user.signUp(context, new SaveListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(context, "注册成功", 0).show();
				// context.startActivity(new
				// Intent(context,LoginActivity.class));
				activity.finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(context, "注册失败" + arg1, 0).show();
			}
		});

	}

	public void doLogin(final Context context, String email, String password) {
		MTUser user = new MTUser();
		user.setUsername(email);
		user.setPassword(password);
		user.login(context, new SaveListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(context, "成功登录", 0).show();
				Activity activity = (Activity) context;
				activity.startActivity(new Intent(context, MainActivity.class));
				activity.finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(context, arg1, 0).show();
			}
		});
	}

	public void doAddFtime(final Context context, Ftime t) {
		t.save(context, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(context, "添加成功", 0).show();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(context, arg1, 0).show();
			}
		});
	}

	public void doSendMessage(final Context con, Message m, final EditText ed) {
		m.save(con, new SaveListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(con, "添加成功", 0).show();
				ed.setText("");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(con, arg1, 0).show();
			}
		});
	}
}
