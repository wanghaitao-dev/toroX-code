package com.wht.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.wht.demo.pojo.Users;

/**
 * 访问数据库表的操作
 * @author dell inspiron
 *1.给接口加注解
 *2.在接口中定义需要的方法
 */
@Mapper
public interface UserMapper {
/**
 * 根据账号和密码查询用户
 * 需要一个和user表对应的类
 */
@Select("select * from wht where user=#{user} and pass=#{pass}")
Users select(String user,String pass);

//比对数据库用户名是否重复
@Select("select * from wht where user=#{user}")
Users selectUser(String user);
//
@Insert("insert into wht (user,pass) value (#{user},#{pass})")
int insert(Users user);
}
