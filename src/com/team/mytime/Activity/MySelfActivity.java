package com.team.mytime.Activity;

import com.team.mytime.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class MySelfActivity extends BaseActivity {

	RelativeLayout chat;

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
	}

	OnClickListener btn_click_listenr = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myself_taolun:
				startActivity(new Intent(MySelfActivity.this, ChatRoomActivity.class));
				break;

			default:
				break;
			}
		}
	};

}
