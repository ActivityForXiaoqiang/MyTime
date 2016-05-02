package com.team.mytime.Activity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.team.mytime.R;
import com.team.mytime.Adpter.FragmentTimeAdapter;
import com.team.mytime.Bmob.Ftime;
import com.team.mytime.Bmob.MTUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends BaseActivity {

	ListView list;
	List<Ftime> datalist;
	FragmentTimeAdapter adapter;

	ImageView mine, add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setback();
		init();
	}

	void init() {
		datalist = new ArrayList<Ftime>();
		dataInit();
		mine = (ImageView) findViewById(R.id.main_myself_btn);
		mine.setVisibility(View.VISIBLE);
		add = (ImageView) findViewById(R.id.main_add_btn);
		add.setVisibility(View.VISIBLE);
		mine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, MySelfActivity.class));
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(MainActivity.this, AddActivity.class), 1001);
			}
		});

		list = (ListView) findViewById(R.id.main_listview);
		adapter = new FragmentTimeAdapter(this, (ArrayList<Ftime>) datalist);
		list.setAdapter(adapter);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			adapter.notifyDataSetChanged();
		}
	};

	void dataInit() {

		BmobQuery<Ftime> query = new BmobQuery<Ftime>();
		query.addWhereEqualTo("user", BmobUser.getCurrentUser(MainActivity.this, MTUser.class).getUsername());
		query.setLimit(100);
		query.findObjects(this, new FindListener<Ftime>() {

			@Override
			public void onSuccess(List<Ftime> list) {
				if (list.size() > 0) {
					for (Ftime t : list) {
						datalist.add(t);
					}
					handler.sendEmptyMessage(1); 
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(MainActivity.this, arg1, 0).show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		String result = data.getStringExtra("ftime");
		Ftime f = JSON.parseObject(result, Ftime.class);
		datalist.add(f);
		adapter.notifyDataSetChanged();

	}
}
