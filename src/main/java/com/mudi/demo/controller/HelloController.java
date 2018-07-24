package com.mudi.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mudi.demo.data.DataTest;

@Controller
@RequestMapping("/demo")
public class HelloController {

	@Autowired
	private DataTest dataTest;

	@RequestMapping("/hello")
	@ResponseBody
	public String view() {
		return "hello world";
	}

	@RequestMapping("/mybatis")
	@ResponseBody
	public Map<String, Object> mybatis() {
		Map<String, Object> info = dataTest.getInfo();
		return info;
	}
}
