package com.wht.demo.pojo;

import java.util.Date;

public class Order {
private Integer id;
private String comment;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
private int goods;
private String address;
private String name;
private String phone;
private Date time;
private Integer user;
public int getGoods() {
	return goods;
}
public void setGoods(int goods) {
	this.goods = goods;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public Date getTime() {
	return time;
}
public void setTime(Date time) {
	this.time = time;
}
public Integer getUser() {
	return user;
}
public void setUser(Integer user) {
	this.user = user;
}


}
