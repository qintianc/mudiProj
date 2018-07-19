package com.mudi.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class HelloController {

	@RequestMapping("/hello")
	@ResponseBody
	public String view() {
		return "hello world";
	}
}
