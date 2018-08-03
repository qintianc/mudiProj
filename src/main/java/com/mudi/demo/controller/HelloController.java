package com.mudi.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mudi.demo.data.DataTest;
import com.mudi.demo.data.MyUserMapper;
import com.mudi.demo.model.MyUser;

@Controller
@RequestMapping("/demo/hello")
public class HelloController {

	@Autowired
	private DataTest dataTest;
	@Autowired
	private MyUserMapper myUserMapper;

	@RequestMapping("/hello")
	@ResponseBody
	public String view() {
		return "hello world";
	}

	@RequestMapping("/mybatis")
	@ResponseBody
	public MyUser mybatis() {
		MyUser user = myUserMapper.getUser("admin");
		return user;
	}
}
