package com.team.mytime.Activity;

import com.team.mytime.R;
import com.team.mytime.Bmob.MTUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

public class MySelfActivity extends BaseActivity {

	RelativeLayout chat;
	TextView loginOut;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myself);
		setback();
		init();
	}

	void init() {
		chat = (RelativeLayout) findViewById(R.id.myself_taolun);
		chat.setOnClickListener(btn_click_listenr);
		loginOut = (TextView) findViewById(R.id.mysef_login_out);
		loginOut.setOnClickListener(btn_click_listenr);

	}

	OnClickListener btn_click_listenr = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myself_taolun:
				startActivity(new Intent(MySelfActivity.this, ChatRoomActivity.class));
				break;
			case R.id.mysef_login_out:
				BmobUser.logOut(MySelfActivity.this);
				if (BmobUser.getCurrentUser(MySelfActivity.this, MTUser.class) == null) {
					startActivity(new Intent(MySelfActivity.this, LoginActivity.class));
					finish();
				}
				break;
			default:
				break;
			}
		}
	};

}
