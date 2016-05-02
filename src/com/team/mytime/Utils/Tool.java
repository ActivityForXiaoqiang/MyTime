package com.team.mytime.Utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Tool {
	/**
	 * 工具类  
	 * 隐藏标题栏或状态栏
	 * 数据的简单存储
	 */
	public static final String KEY = "MEISHI";

	/**
	 * 去掉标题栏
	 * 
	 * @param activity
	 */

	public static void NoTitleBar(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	/**
	 * 去掉状态栏
	 * 
	 * @param activity
	 */
	public static void NoStateBar(Activity activity) {
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 数据存储
	 * 
	 * @param con
	 * @param dataKey
	 * @param str
	 */
	public static void save(Context con, String dataKey, String str) {
		SharedPreferences sp = con.getSharedPreferences(KEY, 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(dataKey, str);
		editor.commit();
	}

	/**
	 * 数据读取
	 * 
	 * @param con
	 * @param dataKey
	 * @return
	 */

	public static String read(Context con, String dataKey) {
		SharedPreferences sp = con.getSharedPreferences(KEY, 0);

		return sp.getString(dataKey, null);
	}

	/**
	 * 数据删除
	 * 
	 * @param con
	 */
	public static void delete(Context con) {
		SharedPreferences sp = con.getSharedPreferences(KEY, 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(KEY);
		editor.clear();
		editor.commit();
	}
	
	
	public static boolean Check(Context con, EditText ed, EditText pwd) {
		String str = ed.getText().toString();
		if (TextUtils.isEmpty(str)) {
			Toast.makeText(con, "手机号不能为空", 0).show();
			return false;
		}

		if (str.length() != 11) {
			Toast.makeText(con, "请输入正确手机号", 0).show();
			return false;
		}
		if (TextUtils.isEmpty(pwd.getText().toString())) {
			Toast.makeText(con, "密码不能为空", 0).show();
			return false;
		}
		return true;

	}
}
