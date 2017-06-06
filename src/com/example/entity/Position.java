package com.example.entity;

public class Position {
	private double position_r;
	private double position_l;
	private String name;
	private String address;
    private String m_szAndroidID; //唯一标示号
    
	public String getM_szAndroidID() {
		return m_szAndroidID;
	}
	public void setM_szAndroidID(String m_szAndroidID) {
		this.m_szAndroidID = m_szAndroidID;
	}
	public double getPosition_r() {
		return position_r;
	}
	public void setPosition_r(double position_r) {
		this.position_r = position_r;
	}
	public double getPosition_l() {
		return position_l;
	}
	public void setPosition_l(double position_l) {
		this.position_l = position_l;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
