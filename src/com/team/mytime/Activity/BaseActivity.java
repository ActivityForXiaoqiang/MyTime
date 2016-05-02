package com.team.mytime.Activity;

import com.team.mytime.R;
import com.team.mytime.Utils.Tool;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class BaseActivity extends FragmentActivity {
	LinearLayout back;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		Tool.NoTitleBar(this);

	}

	void setback() {
		back = (LinearLayout) findViewById(R.id.top_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.i("xiaoqiang", "test");
				finish();

			}
		});
	}
}
