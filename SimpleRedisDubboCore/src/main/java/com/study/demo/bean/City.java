package com.study.demo.bean;

public class City {

	
	private String name;
	private String address;
	@Override
	public String toString() {
		return "City [name=" + name + ", address=" + address + "]";
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
