package com.wht.demo.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.wht.demo.mapper.GoodsMapper;
import com.wht.demo.mapper.Ordermapper;
import com.wht.demo.mapper.UserMapper;
import com.wht.demo.pojo.Comment;
import com.wht.demo.pojo.Goods;
import com.wht.demo.pojo.Order;
import com.wht.demo.pojo.Sekill;
import com.wht.demo.pojo.Users;
import com.wht.demo.util.CaptchaUtil;
//import com.wht.demo.util.CaptchaUtil;

@Controller
public class MyController {
	@Autowired
	//Spring的自动注入，在容器中取出类型相匹配的对象，作为这个变量的值
	//这叫做控制反转，传统方式下，需要一个对象，的使用new类名()创建对象
	private UserMapper usermapper;
	@Autowired
	private GoodsMapper goodsmapper;
	@Autowired
	private Ordermapper ordermapper;
	
	@RequestMapping("/custom_server")
	public String custom_server() {
		return "custom_server";
	}
	
	@RequestMapping("/admin/manager")
	public ModelAndView manager() {
		ModelAndView mv = new ModelAndView();
		List<Order> allOrder = ordermapper.selectAllOrder();
		System.out.print(allOrder);
		mv.addObject("allOrder",allOrder);
		mv.setViewName("admin/manager");
		return mv; 
	}
	
	@RequestMapping(value="/comment",method=RequestMethod.GET)
	public ModelAndView getComment(String commentcontent, HttpSession session, Integer id) {
		session.setAttribute("orderId", id);
		Users user = (Users) session.getAttribute("userp");
		//Order orders=ordermapper.selectOrder(user.getId());
		String name = goodsmapper.selectName(id);
		int goods = goodsmapper.selectGoods(id);
		session.setAttribute("goods", goods);
		//goodsmapper.insertComment(commentcontent);
		ModelAndView mv = new ModelAndView();
		//mv.addObject("orders", orders);
		//mv.addObject("isComment",isComment);
		mv.addObject("name",name);
		mv.setViewName("comment");
		return mv;
	}
	
	@RequestMapping(value="/comment",method=RequestMethod.POST)
	public ModelAndView comment(String commentcontent, HttpSession session, Integer id, MultipartFile pic) {
		session.getAttribute("userp");
		Users user = (Users)session.getAttribute("userp");
		int orderId = (int)session.getAttribute("orderId");
		int goodsId = (int)session.getAttribute("goods");		
		try {
			byte[] buff = pic.getBytes();
			String fileName = pic.getOriginalFilename();
			if(fileName.contains(".")) {
				fileName = fileName.substring(fileName.lastIndexOf("."));
				String uuid = UUID.randomUUID().toString();
				fileName = uuid+fileName;
			}
			else {
				fileName = "default";
			}
			FileOutputStream fos = new FileOutputStream("c:\\upload\\comment\\"+fileName);
			fos.write(buff);
			fos.flush();
			fos.close();
			goodsmapper.insertComment(commentcontent,goodsId,user.getId(),orderId,fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//goodsmapper.insertIsComment(orderId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("comment");
		return mv;
	}
	
	/*@RequestMapping(value="/commentSccuess",method=RequestMethod.POST)
	public String commentSccuess() {
		return "order";
	}
	
	@RequestMapping(value="/commentSccuess",method=RequestMethod.GET)
	public ModelAndView commentSuccess(String commentcontent, HttpSession session, Integer id) {
		//Users user = (Users) session.getAttribute("userp");
		ModelAndView mv = new ModelAndView();
		//Order orders=ordermapper.selectOrder(user.getId());
		//String name = goodsmapper.selectName(id);
		//mv.addObject("orders", orders);
		System.out.print(commentcontent);
		goodsmapper.insertComment(commentcontent);
		mv.setViewName("redirect:/order");
		return mv;
	}*/
	
	@RequestMapping("/a4")
	@ResponseBody
	public List<String> ajax4(String name){
		String msg=null;
		if (name!=null);
		List<String> users_search=goodsmapper.selectByName(name);
		//ModelAndView mv=new ModelAndView();
		System.out.println(users_search);
		if (users_search!=null){
			msg="yes";
		}else {
			msg="no";   
		}
		return users_search;
	}
	
	/*@RequestMapping("/a4")
	@ResponseBody
	public ModelAndView ajax4(String name){
		ModelAndView mv = new ModelAndView();
		if (name!=null) {
			List users_search=goodsmapper.selectByName(name);
			mv.addObject("searchResult",users_search);
			System.out.println(users_search);
		}	
		mv.setViewName("redirect:/search");
		return mv;
	}*/
	
	@RequestMapping("/a3")
	@ResponseBody
	public String ajax3(String name){
		Users users=usermapper.selectUser(name);
		String msg="";
		if (users!=null){
	        if ((users.getUser()).equals(name)){
	            msg = "用户名已存在！";
	        }
	    }else {
            msg = "OK!";
        }
		return msg;
	}
	
	@RequestMapping("/")
	public ModelAndView index(String decp) {
		Sekill sekill=(Sekill) goodsmapper.selectAllSekill();
		//List<Goods> goodsname=goodsmapper.selectByName();
		ModelAndView mv=new ModelAndView();
		mv.addObject("sekill",sekill);
		long start=sekill.getStart_time().getTime();
		long end=sekill.getEnd_time().getTime();
		long now=System.currentTimeMillis();
		int count=sekill.getCount();
		double beforPrice=sekill.getBefor_price();
		double afterPrice=sekill.getAfter_price();
		int status=0;
		//开始时间倒计时
		int remailSeconds=0;
		int remailMinutes=0;
		int remainHour=0;
		
		//查看当前秒杀状态
		if(now<start) {//秒杀还未开始，--->倒计时
			status=0;
			remailSeconds=(int)((((start-now)/1000)%60));
			remailMinutes=(int) ((start-now)/(1000*60));  //毫秒转为秒
			
		}else if(now>end){ //秒杀已经结束
			status=2;
			remailSeconds=-1;  //毫秒转为秒
		}else {//秒杀正在进行
			status=1;
			remailSeconds=0;  //毫秒转为秒
		}
		mv.addObject("status", status);
		mv.addObject("remailSeconds", remailSeconds);
		mv.addObject("remailMinutes",remailMinutes);
		mv.addObject("count", count);
		mv.addObject("beforPrice",beforPrice);
		mv.addObject("afterPrice",afterPrice);
		//mv.addObject("goodsname", goodsname);
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	@ResponseBody
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CaptchaUtil.outputCaptcha(request, response);
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	@RequestMapping("/logout")
	//@ ResponseBody
	public String logout(HttpSession session) {
	session.removeAttribute("userp");
	
		return "redirect:/";
	}
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register() {
		return "register";
	}
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView register(Users user,String pass2) {
		ModelAndView mv=new ModelAndView();
		
		if(!user.getPass().equals(pass2)) {
		mv.addObject("error","密码不一致");
		mv.setViewName("register");
		return mv;
		}
	int res=usermapper.insert(user);
	if(res>0) {
		mv.setViewName("redirect:/login");
	}else {
		mv.addObject("error","注册失败");
		mv.setViewName("register");
	}
	return mv;
	}
/**
 * 数据库由MyBatis完成
 * @param user
 * @param pass
 * @return
 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(String user,String pass,HttpSession session,String valicode) {
		Users users=usermapper.select(user,pass); 	
		ModelAndView mv=new ModelAndView();
		String newValicode = valicode.toUpperCase();
		if(users!=null && session.getAttribute("randomString").equals(newValicode)	) {
			session.setAttribute("userp",users);
			mv.setViewName("redirect:/");
		}else {
			mv.addObject("error","账号和密码错误");
			mv.setViewName("login");
		}
		return mv;
	}
}
