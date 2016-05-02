package com.team.mytime.Activity;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.team.mytime.R;
import com.team.mytime.Bmob.BmobHttpRequest;
import com.team.mytime.Bmob.Ftime;
import com.team.mytime.Bmob.MTUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

public class AddActivity extends BaseActivity {

	Spinner time, type;
	EditText miaoshu;
	Ftime t;
	TextView add;
	BmobHttpRequest bmob;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_add);
		setback();
		
		init();
	}

	void init() {
		bmob = new BmobHttpRequest();
		t = new Ftime();
		time = (Spinner) findViewById(R.id.add_length);
		time.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String[] length = getResources().getStringArray(R.array.time);
				Log.i("xiaoqiang", length[position]);
				t.setLength(length[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		type = (Spinner) findViewById(R.id.add_type);
		type.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String[] ty = getResources().getStringArray(R.array.type);
				t.setType(ty[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		miaoshu = (EditText) findViewById(R.id.add_content);
		add = (TextView) findViewById(R.id.add_btn);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MTUser user = BmobUser.getCurrentUser(AddActivity.this, MTUser.class);
				t.setUser(user.getUsername());
				t.setDescribe(miaoshu.getText().toString());
				t.setTime(new Date().toLocaleString());
				bmob.doAddFtime(AddActivity.this, t);

				Intent it = new Intent();
				it.putExtra("ftime", JSON.toJSONString(t));
				setResult(RESULT_OK, it);
				finish();
			}
		});
	}

}
