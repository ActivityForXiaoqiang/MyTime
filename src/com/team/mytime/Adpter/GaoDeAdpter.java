 package com.team.mytime.Adpter;

import java.util.ArrayList;

import com.team.mytime.R;
import com.team.mytime.Bean.SignInAddressBean;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GaoDeAdpter extends BaseAdapter {

	private Context context;
	private ArrayList<SignInAddressBean> data;

	public GaoDeAdpter() {
		// TODO Auto-generated constructor stub
	}

	public GaoDeAdpter(Context context, ArrayList<SignInAddressBean> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context, R.layout.gd_address_item, null);
			holder.address = (TextView) convertView.findViewById(R.id.gd_address);
			holder.city = (TextView) convertView.findViewById(R.id.gd_city);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.address.setText(data.get(position).getSignAddress());
		holder.city.setText(data.get(position).getWorkAddress());
		return convertView;
	}


	class Holder {
		TextView address,city;
	}
}
