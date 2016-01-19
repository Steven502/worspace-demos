package com.ct.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HelloWorldController
 * @author ZhangBing
 * @Date 2014-6-25
 * @Email zhangmihuo_2007@163.com
 * @Version 1.0
 */
@Controller
@RequestMapping("/freeMarker")
public class HelloWorldController {
	
	@RequestMapping("/hello")
	public String sayHello(ModelMap map){
		System.out.println("say Hello....");
		map.addAttribute("name", "中国电信");
		return "/hello.ftl";
	}
	
	@RequestMapping("/hi")
	public String sayHi(ModelMap map){
		System.out.println("say Hi....");
		map.addAttribute("name", "上海理想");
		return "/hi.ftl";
	}
	
	@RequestMapping("/jsp")
    public String jspRequest(ModelMap map) {
        System.out.println("jspRequest ……");
        map.put("name", "jsp");
        return "/temp.jsp";
    }
	
}
