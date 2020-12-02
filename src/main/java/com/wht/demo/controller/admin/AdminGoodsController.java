package com.wht.demo.controller.admin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wht.demo.mapper.GoodsMapper;
import com.wht.demo.pojo.Goods;

@Controller
@RequestMapping("/admin/goods")
public class AdminGoodsController {

	@Autowired
	private GoodsMapper goodsMapper;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "admin/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(Goods goods, MultipartFile pict) {
		
		// 接收并保存图片
		try {
			// 将上传的图片, 接收到字节数组中
			byte[] buff = pict.getBytes();
			
			String fileName = pict.getOriginalFilename();
			fileName = fileName.substring(fileName.lastIndexOf("."));	// 获取文件的后缀名
			
			String uuid = UUID.randomUUID().toString();	// 生成一个不会重复的随机字符串
			fileName = uuid + fileName;
			
			goods.setPic(fileName);
			
			// 将图片写入到服务器的硬盘上
			FileOutputStream fos = new FileOutputStream("G:\\upload\\" + fileName);
			
			fos.write(buff);
			fos.flush();
			fos.close();
			
			
			// 图片上传成功后
			
			goodsMapper.insert(goods);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("admin/add");
		return mv;
	}
}
