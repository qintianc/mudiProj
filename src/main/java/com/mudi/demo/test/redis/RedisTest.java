package com.mudi.demo.test.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mudi.util.RedisUtil;

@Controller
@RequestMapping("/demo/redis")
public class RedisTest {

	@Autowired
	private RedisUtil redisUtil;

	@ResponseBody
	@RequestMapping("setValue")
	public String setValue() {
		redisUtil.set("abc", "abc");
		return (String)redisUtil.get("abc");
	}
}
