package com.team.mytime.Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.team.mytime.R;
import com.team.mytime.Adpter.ChatRoomAdapter;
import com.team.mytime.Bmob.BmobHttpRequest;
import com.team.mytime.Bmob.MTUser;
import com.team.mytime.Bmob.Message;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class ChatRoomActivity extends BaseActivity {
	ListView listview;
	List<Message> dataList;
	TextView send;
	BmobHttpRequest bmob;
	EditText message_content;
	ChatRoomAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_chatroom);
		init();

	}

	void init() {
		bmob = new BmobHttpRequest();
		dataList = new ArrayList<Message>();
		adapter = new ChatRoomAdapter(this, (ArrayList<Message>) dataList);
		listview = (ListView) findViewById(R.id.chatroom_list);
		listview.setAdapter(adapter);
		datainit();
		message_content = (EditText) findViewById(R.id.send_message_content);
		send = (TextView) findViewById(R.id.send_message_btn);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message e = new Message();
				e.setContent(message_content.getText().toString());
				e.setUser(BmobUser.getCurrentUser(ChatRoomActivity.this, MTUser.class).getUsername());
				e.setTime(new Date().toLocaleString());
				e.setId("1");
				bmob.doSendMessage(ChatRoomActivity.this, e, message_content);
				dataList.add(e);
				adapter.notifyDataSetChanged();
			}
		});

	}

	void datainit() {
		BmobQuery<Message> query = new BmobQuery<Message>();
		query.addWhereEqualTo("id", "1");
		query.setLimit(100);
		query.findObjects(this, new FindListener<Message>() {

			@Override
			public void onSuccess(List<Message> list) {
				for (Message m : list) {
					dataList.add(m);
				}
				handler.sendEmptyMessage(1);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();

		}
	};

}
