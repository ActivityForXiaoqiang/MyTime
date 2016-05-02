package com.team.mytime.Adpter;

import java.util.ArrayList;
import java.util.List;

import com.team.mytime.R;
import com.team.mytime.Bmob.Ftime;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FragmentTimeAdapter extends BaseAdapter {
	Context con;
	List<Ftime> data;

	public FragmentTimeAdapter(Context con, ArrayList<Ftime> data) {
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
			convertView = View.inflate(con, R.layout.main_list_item, null);
			holder.content=(TextView) convertView.findViewById(R.id.main_list_item_miaoshu);
			holder.length=(TextView) convertView.findViewById(R.id.main_list_item_length);
			holder.time=(TextView) convertView.findViewById(R.id.main_list_item_time);
			holder.type=(TextView) convertView.findViewById(R.id.main_list_item_type);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		if (data!=null&data.size()>0) {
			Ftime f=data.get(position);
			holder.content.setText(f.getDescribe());
			holder.length.setText("坚持了"+f.getLength()+"分钟");
			holder.time.setText(f.getTime());
			holder.type.setText(f.getType());
		}
		
		
		return convertView;

	}

	class viewHolder {
		TextView length, content, type, user, time;
	}

}
