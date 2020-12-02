package com.wht.demo.pojo;

import java.util.Date;

public class Sekill {
	private Integer id;
	
	private String goodsname;
	
	private Date start_time;
	
	private Date end_time;
	
	private double befor_price;
	
	private double after_price;
	
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public double getBefor_price() {
		return befor_price;
	}

	public void setBefor_price(double befor_price) {
		this.befor_price = befor_price;
	}

	public double getAfter_price() {
		return after_price;
	}

	public void setAfter_price(double after_price) {
		this.after_price = after_price;
	}


	
	

}
