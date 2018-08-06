package com.mudi.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo/shiro")
public class ShiroController {

	@RequestMapping("/login")
	public String view() {
		return "/demo/login";
	}

	@RequestMapping("/admin")
	@ResponseBody
	@RequiresRoles("admin-role")
	public String admin() {
		Subject subject = SecurityUtils.getSubject();
		return "admin into";
	}

	@RequestMapping("/root")
	@ResponseBody
	@RequiresRoles("root-role")
	public String root() {
		return "root into";
	}

}
