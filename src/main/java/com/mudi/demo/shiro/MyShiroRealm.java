package com.mudi.demo.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.mudi.demo.model.MyUser;
import com.mudi.demo.service.MyUserService;

public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private MyUserService myUserService;

	/**
	 * 获取身份验证信息
	 * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
	 *
	 * @param authenticationToken 用户身份信息 token,可获取用户名，密码
	 * @return 返回封装了用户信息的 AuthenticationInfo 实例
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		System.out.println("————身份认证方法————");

		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

		// 从数据库获取对应用户名密码的用户
		String userName = token.getUsername();
		MyUser user = myUserService.getUser(userName);
		String password = user.getPassword();

		if (null == password) {
			throw new AccountException("用户名不正确");
		} else if (!password.equals(new String(token.getPassword()))) {
			throw new AccountException("密码不正确");
		}
		return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
	}

	/**
	 * 获取授权信息
	 *
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("————权限认证————");
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获得该用户角色
		MyUser user = myUserService.getUser(username);
		String role = user.getRole();
		Set<String> set = new HashSet<>();
		// 需要将 role 封装到 Set 作为 info.setRoles() 的参数
		set.add(role);
		// 设置该用户拥有的角色
		info.setRoles(set);
		return info;
	}
}