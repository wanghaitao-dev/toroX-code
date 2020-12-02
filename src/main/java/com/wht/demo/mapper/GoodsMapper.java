package com.wht.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wht.demo.pojo.Goods;
import com.wht.demo.pojo.Sekill;

/**
 * 访问Goods的相关操作
 * 
 * @author dell inspiron
 *
 */
@Mapper
public interface GoodsMapper {
	/**
	 * 查询所有商品信息
	 */
	@Update("update goods set count=count-1 where id=#{id}")
	int updateCount(Goods goods);
	
	@Update("update goods set sellCount=sellCount+1 where id=#{id}")
	int updateSellCount(Goods goods);

	@Insert("insert into goods (name, decp, price, count, pic, sellCount,type) value (#{name}, #{decp}, #{price}, #{count}, #{pic}, #{sellCount},#{type})")
	int insert(Goods goods);
	@Select("select * from goods where goods.type=#{id}")
	List<Goods> selectAll(Integer id);

	@Select("select * from goods where id=${id}")
	Goods selectById(Integer id);
	
	@Select("select goods.name from `order`,goods where order.id=${id} and `order`.goods=goods.id")
	String selectName(Integer id);
	
	@Select("select `order`.goods from `order` where order.id=${id}")
	int selectGoods(Integer id);
	
	@Select("SELECT name FROM goods WHERE name like concat('%${content}%')")
	List<String> selectByName(String content);
	
	@Select("select * from sekill where id=${id}")
	Sekill selectBySekillId(Integer id);
	
	
	@Select("select * from sekill")
	Sekill selectAllSekill();
	
	@Delete("delete from `order` where id=#{id}")
	int deleteOrder(Integer id);
	
	@Insert("insert into buyCar (goods,user,goodsSize,goodsColor) values (#{goods},#{user},#{size},#{color})")
	int insertBuyCar(Integer goods, Integer user, String size, String color);
	
	@Insert("insert into comment (commentContent,goods,user,orderId,isComment,pic) values (#{commentContent},#{goods},#{user},#{order},1,#{fileName})")
	int insertComment(String commentContent, Integer goods, Integer user, Integer order, String fileName);
	
	//@Select("select comment.isComment from comment where user=#{user} and order.id=#{order}")
	//List<Map> selectIsComment(Integer user, Integer order);
	
	@Select("select comment.commentContent, comment.commentTime, comment.pic, wht.user from comment,wht where comment.user=wht.id and comment.goods=#{id}")
	List<Map> selectGoodsComment(Integer id);
	
	@Delete("delete from buycar where id=#{id}")
	int deleteBuyCarGoods(Integer id);
	

}
