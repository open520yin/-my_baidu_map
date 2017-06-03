package com.example.baidu;

import java.io.Serializable;

public class Bean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String number;
	private String speed;
	private double latitude;
	private double longitude;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Bean(int id, String number, String speed, double latitude,
			double longitude) {
		super();
		this.id = id;
		this.number = number;
		this.speed = speed;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
