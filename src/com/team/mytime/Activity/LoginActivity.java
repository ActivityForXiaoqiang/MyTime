package com.team.mytime.Activity;

import com.team.mytime.R;
import com.team.mytime.Bmob.BmobHttpRequest;
import com.team.mytime.Bmob.MTUser;
import com.team.mytime.Utils.Tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

public class LoginActivity extends Activity {
	private TextView register, login;
	BmobHttpRequest bomb;
	EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Tool.NoTitleBar(this);
		Tool.NoStateBar(this);
		setContentView(R.layout.activity_login);
		BmobHttpRequest.doBmobInit(this);

		if (isLogin()) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
			return;
		}

		init();
	}

	void init() {
		bomb = new BmobHttpRequest();
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		register = (TextView) findViewById(R.id.login_register_btn);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			}
		});
		login = (TextView) findViewById(R.id.login_doLOGIN);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Tool.Check(LoginActivity.this, username, password)) {
					bomb.doLogin(LoginActivity.this, username.getText().toString(), password.getText().toString());
				}
			}
		});

	}

	boolean isLogin() {

		MTUser user = BmobUser.getCurrentUser(this, MTUser.class);
		if (user != null) {
			return true;
		}

		return false;

	}

}
