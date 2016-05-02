package com.team.mytime.Activity;

import com.team.mytime.R;
import com.team.mytime.Bmob.BmobHttpRequest;
import com.team.mytime.Utils.Tool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterActivity extends Activity {

	private TextView regist_btn;
	private RelativeLayout layout;
	private BmobHttpRequest bmob;
	private EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Tool.NoStateBar(this);
		Tool.NoTitleBar(this);
		setContentView(R.layout.activity_login);
		oninit();
	}

	void oninit() {
		bmob = new BmobHttpRequest();
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		regist_btn = (TextView) findViewById(R.id.login_doLOGIN);
		regist_btn.setText("注  册");
		regist_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Tool.Check(RegisterActivity.this, username, password)) {
					bmob.doRegister(RegisterActivity.this, username.getText().toString(),
							password.getText().toString(),RegisterActivity.this);
				}
			}
		});
		layout = (RelativeLayout) findViewById(R.id.regist_layout);
		layout.setVisibility(View.GONE);
	}
}
