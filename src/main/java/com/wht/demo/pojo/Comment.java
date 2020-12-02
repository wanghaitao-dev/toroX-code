package com.wht.demo.pojo;

import java.sql.Date;

public class Comment {
	
	private Integer id;
	
	private Integer describeStar;
	
	private Integer serviceStar;
	
	private Integer logisticsStar;
	
	private String commentContent;
	
	private Date commentTime;
	
	private String pic;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDescribeStar() {
		return describeStar;
	}

	public void setDescribeStar(Integer describeStar) {
		this.describeStar = describeStar;
	}

	public Integer getServiceStar() {
		return serviceStar;
	}

	public void setServiceStar(Integer serviceStar) {
		this.serviceStar = serviceStar;
	}

	public Integer getLogisticsStar() {
		return logisticsStar;
	}

	public void setLogisticsStar(Integer logisticsStar) {
		this.logisticsStar = logisticsStar;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
}
