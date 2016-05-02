package com.team.mytime.Bean;

/**
 * 签到需要的工作地址对象
 * 
 * @author yujinzhao
 *
 */
public final class SignInAddressBean {

	private String wid;

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	private String lat;
	private String lnt;
	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLnt() {
		return lnt;
	}

	public void setLnt(String lnt) {
		this.lnt = lnt;
	}

	private String workAddress = "";// 工作地址
	private String signAddress = "";// 签到地址

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getSignAddress() {
		return signAddress;
	}

	public void setSignAddress(String signAddress) {
		this.signAddress = signAddress;
	}

	public boolean isok() {// 数据是否进行 签到地址与 工作地址绑定
		if (workAddress.equals("") || signAddress.equals("")) {
			return false;
		} else {
			return true;
		}

	}
}
