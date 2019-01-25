package com.mudi.config.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

/**
 * 自定义拦截器
 * @author csd
 * @date 2019年1月24日
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

	/**
	 * 登录成功后跳转
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		// String successUrl = "/shiro/myIndex";

		// 获取登录前的请求路径
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		String url = savedRequest.getRequestUrl(); // url为域名后的路径，包含项目名
		url = url.substring(url.indexOf("/", 1), url.length());

		WebUtils.issueRedirect(request, response, url);
		return false;
	}
}
