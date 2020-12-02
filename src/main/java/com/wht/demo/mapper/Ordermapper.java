package com.wht.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.wht.demo.pojo.Goods;
import com.wht.demo.pojo.Order;

@Mapper
public interface Ordermapper {
	@Insert("insert into `order`(goods,address,name,phone,user) value(#{goods},#{address},#{name},#{phone},#{user})")
	int insert(Order order);
	@Select("select `order`.*, "
			+ "goods.name as goods_name, goods.decp, goods.price, goods.pic, goods.id "
			+ "from `order`, goods "
			+ "where `order`.goods = goods.id and user=#{id} "
			+ "order by time desc")
	
	List<Map> selectByUser(Integer id);
	
	
	@Select("select goods.name,goods.sellCount,buyCar.goodsSize,buyCar.goodsColor,buyCar.id "
			+"from goods, buyCar "
			+"where buyCar.goods = goods.id and buyCar.user=#{id}")
	
	List<Map> selectBuyCar(Integer id);
	
	@Select("select * from `order`")
	List<Order> selectAllOrder();

}
