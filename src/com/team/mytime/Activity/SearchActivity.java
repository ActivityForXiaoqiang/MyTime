package com.team.mytime.Activity;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.team.mytime.R;
import com.team.mytime.Adpter.GaoDeAdpter;
import com.team.mytime.Bean.SignInAddressBean;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends BaseActivity implements OnItemClickListener{

	private EditText edittext;
	private ListView listview;
//	private TextView bt_commit;
	private ArrayList<SignInAddressBean> list = new ArrayList<SignInAddressBean>();
	private GaoDeAdpter adpter;
	/* 高德成功返回地址 */
	public final static int GD_OKCODE = 900;
	private PoiSearch mPoiSearch;
	
	private String cityname = "";
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.gd_address_activity);
		init();
		adpter = new GaoDeAdpter(this, list);
		listview.setAdapter(adpter);
		listview.setOnItemClickListener(this);
		cityname = getIntent().getStringExtra("cityname");
	}

	private void init() {
		edittext = (EditText) findViewById(R.id.editText1);
		edittext.addTextChangedListener(textWatcher);
		listview = (ListView) findViewById(R.id.listView1);
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
	}
	OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
		public void onGetPoiResult(PoiResult result) {
			
			   if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			        //详情检索失败
			        // result.error请参考SearchResult.ERRORNO 
				  
			    } 
			    else {
			        //检索成功
			    	list.clear();
					for (int i = 0; i < result.getAllPoi().size(); i++) {
					SignInAddressBean object = new SignInAddressBean();
					object.setLat(result.getAllPoi().get(i).location.latitude + "");
					object.setLnt(result.getAllPoi().get(i).location.longitude + "");
					object.setSignAddress(result.getAllPoi().get(i).name);
					object.setWorkAddress(result.getAllPoi().get(i).city);
					list.add(object);
				}
				adpter.notifyDataSetChanged();
			    }
		}

		public void onGetPoiDetailResult(PoiDetailResult result) {
			// 获取Place详情页检索结果
		}
	};


	TextWatcher textWatcher = new TextWatcher() {

		@SuppressLint("NewApi")
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mPoiSearch.searchInCity((new PoiCitySearchOption()).city("cityname").keyword(s.toString()).pageNum(0));
			 Log.d("yjz", "onTextChanged== s"+s.toString());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// Log.d("yjz", "beforeTextChanged== s"+s.toString());

		}

		@Override
		public void afterTextChanged(Editable s) {
			// Log.d("yjz", "afterTextChanged== s"+s.toString());
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		edittext.setText("" + list.get(arg2).getSignAddress());
		Log.e("yjz", "lnt:" + list.get(arg2).getLnt() + ", lat:" + list.get(arg2).getLat());
		Intent it = new Intent(SearchActivity.this, MapActivity.class);
		it.putExtra("address", edittext.getText().toString());
		it.putExtra("lat", list.get(arg2).getLat() + "");
		it.putExtra("lnt", list.get(arg2).getLnt() + "");
		setResult(GD_OKCODE, it);
		finish();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPoiSearch.destroy();
	}
}
