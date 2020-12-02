package com.wht.demo.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wht.demo.mapper.GoodsMapper;
import com.wht.demo.mapper.Ordermapper;
import com.wht.demo.pojo.BuyCar;
import com.wht.demo.pojo.Comment;
import com.wht.demo.pojo.Goods;
import com.wht.demo.pojo.Order;
import com.wht.demo.pojo.Sekill;
import com.wht.demo.pojo.Users;

/**
 * 对商品的操作
 * 
 * @author dell inspiron
 *
 */
@Controller
public class GoodsController {
	@Autowired
	private GoodsMapper goodsmapper;
	@Autowired
	private Ordermapper ordermapper;

	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public ModelAndView buy(HttpSession session, Integer id) {
		ModelAndView mv = new ModelAndView();
		Goods goods = goodsmapper.selectById(id);
		Users user = (Users)session.getAttribute("userp");
		if(user == null) {
			mv.setViewName("redirect:/login");
			return mv;
		}
		mv.addObject("goods", goods);
		mv.setViewName("buy");
		return mv;
	}
	
	@RequestMapping("buyCarDelete")
	public ModelAndView buyCarPost(Integer id) {
		ModelAndView mv = new ModelAndView();
		goodsmapper.deleteBuyCarGoods(id);
		mv.setViewName("redirect:/buyCar");
		return mv;
	}
	
	@RequestMapping(value = "/buyCar", method = RequestMethod.GET)
	public ModelAndView buyCarGet(BuyCar buycar, HttpSession session, Integer id) {
		ModelAndView mv = new ModelAndView();
		Users user = (Users)session.getAttribute("userp");
		if(user == null) {
			mv.setViewName("redirect:/login");
			return mv;
		}
		/*Goods goods = goodsmapper.selectById(buycar.getGoods());
		if (goods.getCount() == 0) {
			mv.addObject("goods", goods);
			mv.addObject("error", "库存不足，加入购物车失败！");
			mv.setViewName("detail");
			return mv;
		}*/
		//goodsmapper.insertBuyCar(id,user.getId());
		List buyCarGoods = ordermapper.selectBuyCar(user.getId());
		mv.addObject("buyCarGoods",buyCarGoods);
		mv.setViewName("buyCar");
		return mv;
		
	}

	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public ModelAndView buy(Order order, HttpSession session, String address, String name, String phone) {
		ModelAndView mv = new ModelAndView();
		Users user = (Users) session.getAttribute("userp");
		if (user == null) {
			mv.setViewName("redirect:/login");
			return mv;
		}
		order.setUser(user.getId());
		Goods goods = goodsmapper.selectById(order.getGoods());
		if (goods.getCount() == 0) {
			mv.addObject("goods", goods);
			mv.addObject("error", "库存不足，购买失败！");
			mv.setViewName("buy");
			return mv;
		}
		if(address != null && name != null && phone != null) {
			int res1 = goodsmapper.updateSellCount(goods);
			int res = goodsmapper.updateCount(goods);
			res = ordermapper.insert(order);
			mv.setViewName("redirect:/order");
		}else {
			mv.addObject("errorInfo");
		}
		
		return mv;
	}

	@RequestMapping(value="/order")
	public ModelAndView order(HttpSession session, Integer id) {
		Users user = (Users) session.getAttribute("userp");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/login");
			return mv;
		}
		List orders=ordermapper.selectByUser(user.getId());
		//List isComment = goodsmapper.selectIsComment(user.getId(),orders);
		//mv.addObject("isComment",isComment);
		mv.addObject("orders", orders);
		mv.setViewName("order");
		return mv;
	}
	
	@RequestMapping("delete")
	public ModelAndView delete(Integer id) {
		ModelAndView mv=new ModelAndView();	
		goodsmapper.deleteOrder(id);
		mv.setViewName("redirect:/order");
		return mv;
	}
	
	@RequestMapping(value="/detail", method = RequestMethod.POST)
	public ModelAndView buyCar(BuyCar buycar, HttpSession session, Integer id ,String optionsRadiosinline, String optionsRadiosinline1) {
		ModelAndView mv = new ModelAndView();
		Users user = (Users)session.getAttribute("userp");
		if(user == null) {
			mv.setViewName("redirect:/login");
			return mv;
		}
		/*Goods goods = goodsmapper.selectById(buycar.getGoods());
		if (goods.getCount() == 0) {
			mv.addObject("goods", goods);
			mv.addObject("error", "库存不足，加入购物车失败！");
			mv.setViewName("detail");
			return mv;
		}*/
		goodsmapper.insertBuyCar(id,user.getId(),optionsRadiosinline,optionsRadiosinline1);
		mv.setViewName("redirect:/buyCar");
		return mv;
		
	}
	
	

	@RequestMapping(value="/detail", method = RequestMethod.GET)
	public ModelAndView goods(Integer id) {
		List comment = goodsmapper.selectGoodsComment(id);
		System.out.print(comment);
		Goods goods = goodsmapper.selectById(id);
		Sekill sekill = goodsmapper.selectBySekillId(id); 
		ModelAndView mv = new ModelAndView();
		mv.addObject("comment",comment);
		mv.addObject("goods", goods);
		mv.addObject("sekill",sekill);
		mv.setViewName("detail");
		return mv;
	}
	
	@RequestMapping("content")
	public ModelAndView content(Integer id) {
		ModelAndView mv = new ModelAndView();
		List<Goods> goodslist=goodsmapper.selectAll(id);
		mv.addObject("goodslist", goodslist);
		mv.setViewName("content");
		return mv;
		
	}
	
	@RequestMapping(value="/admin/add",method=RequestMethod.GET)
	public String add() {	
		return "admin/add";
	}
	
	@RequestMapping(value="/admin/add",method=RequestMethod.POST)
	public ModelAndView goods(Goods goods, MultipartFile pict) {
		try {
			byte[] buff = pict.getBytes();
			String fileName = pict.getOriginalFilename();
			fileName = fileName.substring(fileName.lastIndexOf("."));
			String uuid = UUID.randomUUID().toString();
			fileName = uuid+fileName;
			FileOutputStream fos = new FileOutputStream("c:\\upload\\"+fileName);
			fos.write(buff);
			fos.flush();
			fos.close();
			goods.setPic(fileName);
			goodsmapper.insert(goods);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/add");
		return mv;
	}

}
