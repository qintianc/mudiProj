package com.mudi.demo.test.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.mudi.demo.data.MyUserMapper;
import com.mudi.demo.model.MyUser;

@Controller
@RequestMapping("/demo/mybatis")
public class MyBatisTest {

	@Autowired
	private MyUserMapper myUserMapper;

	@RequestMapping("/mybatis")
	@ResponseBody
	public MyUser mybatis() {
		MyUser user = myUserMapper.getUser("admin");
		return user;
	}

	@RequestMapping("/testPageHelper")
	@ResponseBody
	public List<Map<String, Object>> getList() {
		// 设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(1, 1);
		List<Map<String, Object>> list = myUserMapper.getList();
		return list;
	}
}
