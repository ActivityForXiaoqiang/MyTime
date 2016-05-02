package com.team.mytime.Adpter;

import java.util.ArrayList;
import java.util.List;

import com.team.mytime.R;
import com.team.mytime.Bmob.Ftime;
import com.team.mytime.Bmob.Message;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatRoomAdapter extends BaseAdapter {
	Context con;
	List<Message> data;

	public ChatRoomAdapter(Context con, ArrayList<Message> data) {
		this.con = con;
		this.data = data;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		viewHolder holder = null;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = View.inflate(con, R.layout.chat_list_item, null);
			holder.content = (TextView) convertView.findViewById(R.id.chat_message_content);
			holder.time = (TextView) convertView.findViewById(R.id.chat_message_time);
			holder.user = (TextView) convertView.findViewById(R.id.chat_message_user);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		if (data != null & data.size() > 0) {
			Message m = data.get(position);
			holder.content.setText(m.getContent());
			holder.time.setText("   于"+m.getTime()+" 说：");
			holder.user.setText(m.getUser());
		}

		return convertView;

	}

	class viewHolder {
		TextView content, user, time;
	}

}
