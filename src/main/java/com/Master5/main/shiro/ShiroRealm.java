
package com.Master5.main.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.Master5.main.web.user.dao.UserDao;
import com.Master5.main.web.user.entry.Permission;
import com.Master5.main.web.user.entry.Role;
import com.Master5.main.web.user.entry.User;

public class ShiroRealm extends AuthorizingRealm {

	protected UserDao userDao;

	@Resource(name = "userDao")
	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取当前登录的用户名
		String name = (String) super.getAvailablePrincipal(principals);

		List<String> roles = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		User user = userDao.findByName(name);

		if (user != null) {
			if (user.getRoles() != null && user.getRoles().size() > 0) {
				for (Role role : user.getRoles()) {
					roles.add(role.getName());
					if (role.getPermissions() != null && role.getPermissions().size() > 0) {
						for (Permission pmss : role.getPermissions()) {
							if (StringUtils.isNotEmpty(pmss.getMethod())) {
								permissions.add(pmss.getMethod());
							}
						}
					}
				}
			}
		} else {
			throw new AuthorizationException();
		}
		// 给当前用户设置角色
		info.addRoles(roles);
		// 给当前用户设置权限
		info.addStringPermissions(permissions);

		return info;

	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {

		// token中储存着输入的用户名和密码
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;

		User user = userDao.findByName(token.getUsername());
		if (null != user) {
			return new SimpleAuthenticationInfo(user.getName(), user.getPass(), user.getNickName());
		} else {
			return null;
		}
	}

}
