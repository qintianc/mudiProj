package com.mudi.demo.test.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo/session")
public class SessionTest {

	@RequestMapping("set")
	@ResponseBody
	public Object setAttr(HttpServletRequest request) {
		long time = System.currentTimeMillis();
		System.out.println(time);
		request.getSession().setAttribute("time", time);
		Object time1 = request.getSession().getAttribute("time");
		System.out.println(time1);
		return time1;
	}

	@RequestMapping("get")
	@ResponseBody
	public Object getAttr(HttpServletRequest request) {
		Object time = request.getSession().getAttribute("time");
		System.out.println(time);
		return time;
	}
}
