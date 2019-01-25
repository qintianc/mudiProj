package com.mudi.config.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shiro")
public class ShiroController {

	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "/login";
	}

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/myIndex")
	public String index() {
		return "/myIndex";
	}

	/**
	 * 未授权页面
	 * @return
	 */
	@RequestMapping("/403")
	public String un() {
		Subject subject = SecurityUtils.getSubject();
		return "/403";
	}

	@RequestMapping("/admin")
	@ResponseBody
	@RequiresRoles("admin-role")
	public Map<String, Object> admin(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("a", 2);

		// 操作shiro的session，也可以通过httpSession来获取值
		Object attribute = request.getSession().getAttribute("a"); // 值为2
		System.out.println(attribute);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("role", "admin-role");
		result.put("data", attribute);
		return result;
	}

	@RequestMapping("/root")
	@ResponseBody
	@RequiresRoles("root-role")
	public Map<String, Object> root() {
		Subject subject = SecurityUtils.getSubject();
		Map<String, Object> result = new HashMap<String, Object>();
		// 是否有角色
		boolean hasRole = subject.hasRole("root-role");
		// 当前登录用户名
		String currentUser = subject.getPrincipal().toString();
		// 是否拥有权限
		boolean permitted = subject.isPermitted("permits:read");
		// 是否记住我
		boolean remembered = subject.isRemembered();
		// 用户在session有效期间证明过身份，则返回true；即时使用RememberMe服务，但过了session有效期，则返回false
		boolean authenticated = subject.isAuthenticated();

		result.put("role", "root-role");
		result.put("data", hasRole + "," + currentUser + "," + permitted);
		return result;
	}

}
