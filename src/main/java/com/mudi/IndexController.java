package com.mudi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 项目首页
 * @author csd
 * @date 2019年1月24日
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String view() {
		return "/myIndex";
	}
}
