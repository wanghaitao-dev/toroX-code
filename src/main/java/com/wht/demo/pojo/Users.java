package com.wht.demo.pojo;
/**
 * 实体类规则
 * 为了将数据表中的数据保存到Java程序中
 * 1.类名与表名一致
 * 2.类中属性与表中字段保持一致
 *   1）属性名与表的字段要保持完全一样
 *   2）数据类型是匹配
 * 3.所有的属性要私有化，提供公共的Get和Set方法
 * 4.实体类要提供一个无参的构造方法
 * @author dell inspiron
 *
 */
public class Users {
	private Integer id;
	private String user;
	private String pass;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
}
