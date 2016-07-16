
package com.Master5.main.web.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Master5.main.annotation.CheckPermission;
import com.Master5.main.annotation.annotationtools.GetValueFromAnnotation;
import com.Master5.main.utils.GetClassByPackage;
import com.Master5.main.utils.IPTools;
import com.Master5.main.utils.constant.Key;
import com.Master5.main.utils.constant.MsgKey;
import com.Master5.main.utils.constant.SysKey;
import com.Master5.main.utils.encrypt.MD5;
import com.Master5.main.web.user.entry.Permission;
import com.Master5.main.web.user.entry.Role;
import com.Master5.main.web.user.entry.User;
import com.Master5.main.web.user.service.PermissionService;
import com.Master5.main.web.user.service.RoleService;
import com.Master5.main.web.user.service.UserService;

@Controller
@RequestMapping(value = "login")
public class Login {

	private static final Logger logger = LoggerFactory.getLogger(Login.class);

	@Autowired
	UserService userService;

	@Autowired
	PermissionService permissionService;

	@Autowired
	RoleService roleService;

	// @RequiresGuest
	// @RequestMapping
	// public String loginMe() {
	//
	// return "/login/regist";
	// }

	/**
	 * 登录主页面
	 */
	@RequiresGuest
	@RequestMapping
	public String login() {

		return "/login/regist";
	}

	/**
	 * 登录处理方法
	 */
	@RequestMapping(value = "loging", method = RequestMethod.POST)
	public String login(@ModelAttribute User loginUser, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		loginUser.setPass(MD5.getMD5Pass(loginUser.getPass()));

		// subject理解成权限对象。类似user
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		// 创建用户名和密码的令牌
		UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getName(), loginUser.getPass());
		// 记录该令牌，如果不记录则类似购物车功能不能使用。
		token.setRememberMe(true);
		subject.getPrincipal();
		try {
			subject.login(token);
			loginUser = userService.findByName(loginUser.getName());
			// 验证是否成功登录的方法
			if (subject.isAuthenticated()) {
				msgList.add("欢迎回来" + loginUser.getNickName());
				loginUser.setIp(IPTools.getClientIp(request));
				session.setAttribute(Key.LOGINED, loginUser);
				redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
				logger.info("登录成功：" + loginUser.getNickName());
				return "redirect:/user/info";
			}
		} catch (UnknownAccountException ex) {
			msgList.add("用户不存在");
		} catch (IncorrectCredentialsException ex) {
			msgList.add("密码错误");
		} catch (AuthenticationException ex) {
			msgList.add("服务器繁忙");
		} catch (Exception e) {
			msgList.add("未知错误");
		}

		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:/login";

	}

	@RequestMapping(value = "init")
	public String initSystem(RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		String pack = "com.Master5.main.web";

		Set<Class<?>> classes = GetClassByPackage.getInstance().getClasses(pack);
		Set<Permission> permissions = GetValueFromAnnotation.getInstance().getPermissions(classes);
		List<Permission> haspermissions = permissionService.findAll();

		if (haspermissions.size() != 0) {

			for (Permission per : haspermissions) {
				for (Iterator<Permission> ite = permissions.iterator();ite.hasNext();) {
					if (per.getMethod().equals(ite.next().getMethod())) {
						ite.remove();
					}
				}
			}
			permissionService.save(permissions);
			return "redirect:/login";
		}

		permissionService.save(permissions);
		Role role = new Role(Key.ROLE_DEFAULT_ADMIN,
				new HashSet<Permission>(permissionService.findByStateLessThanEqual(Key.STATE_DEFAULT_ADMIN)));
		role.setState(Key.STATE_DEFAULT_ADMIN);
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleService.save(role));
		User user = new User(Key.ROLE_DEFAULT_ADMIN, Key.ADMIN_DEFAULT_NAME, MD5.getMD5Pass(Key.ADMIN_DEFAULT_PASS),
				Key.ADMIN_DEFAULT_EMAIL, Key.SEX_MAN);
		user.setRoles(roles);
		user.setState(Key.STATE_DEFAULT_ADMIN);
		userService.save(user);
		role = new Role(Key.ROLE_DEFAULT_USER,
				new HashSet<Permission>(permissionService.findByStateLessThanEqual(Key.STATE_DEFAULT_USER)));
		role.setState(Key.STATE_DEFAULT_USER);
		roleService.save(role);
		role = new Role(Key.ROLE_DEFAULT_DARK,
				new HashSet<Permission>(permissionService.findByStateLessThanEqual(Key.STATE_DEFAULT_DARK)));
		role.setState(Key.STATE_DEFAULT_DARK);
		roleService.save(role);

		msgList.add(Key.SYSTEM_INIT_SUCCESS);
		redirectAttributes.addFlashAttribute(Key.msg, msgList);
		return "redirect:/login";
	}

	@RequiresGuest
	@RequestMapping(value = "aa")
	public String getLngLats() {

		return "/login/map";
	}

	@RequestMapping("/getLngLat")
	@ResponseBody
	public List<Map<String, Object>> getLngLat() {
		Map<String, Object> conditonMap = new HashMap<String, Object>();
		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
		List<String> tempList = new ArrayList<String>();
		tempList.add("113.67566,34.753844");
		tempList.add("113.675381,34.75391");
		tempList.add("113.675306,34.75372");
		tempList.add("113.675585,34.75365");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.675424,34.7538");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.721805,34.769826");
		tempList.add("113.721386,34.769429");
		tempList.add("113.721982,34.768962");
		tempList.add("113.72247,34.769346");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.721934,34.769366");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.722507,34.769275");
		tempList.add("113.722094,34.768923");
		tempList.add("113.722792,34.768389");
		tempList.add("113.723216,34.768724");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.722674,34.768797");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.729953,34.777006");
		tempList.add("113.729326,34.777196");
		tempList.add("113.729256,34.777006");
		tempList.add("113.729873,34.776817");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.729508,34.777037");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.730935,34.776874");
		tempList.add("113.73064,34.77702");
		tempList.add("113.730388,34.776641");
		tempList.add("113.730683,34.776508");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.730608,34.776729");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.732571,34.769414");
		tempList.add("113.731713,34.768691");
		tempList.add("113.73218,34.76822");
		tempList.add("113.733145,34.769198");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.732469,34.768819");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.7316,34.768497");
		tempList.add("113.73079,34.768057");
		tempList.add("113.731187,34.767621");
		tempList.add("113.731944,34.768096");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.731252,34.767982");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.723634,34.777945");
		tempList.add("113.722459,34.777526");
		tempList.add("113.722819,34.777125");
		tempList.add("113.723822,34.777456");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.722926,34.777376");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.722036,34.777174");
		tempList.add("113.721537,34.776843");
		tempList.add("113.721998,34.77653");
		tempList.add("113.722406,34.776856");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.721966,34.776804");
		resList.add(conditonMap);

		tempList = new ArrayList<String>();
		conditonMap = new HashMap<String, Object>();
		tempList.add("113.727899,34.771119");
		tempList.add("113.727277,34.771393");
		tempList.add("113.726665,34.771093");
		tempList.add("113.72666,34.770674");
		tempList.add("113.726633,34.770361");
		tempList.add("113.727298,34.770234");
		tempList.add("113.727985,34.770388");
		tempList.add("113.727899,34.770727");
		conditonMap.put("LngLat", tempList);
		conditonMap.put("center", "113.727566,34.770802");
		resList.add(conditonMap);

		// tempList=new ArrayList<String>();
		// conditonMap=new HashMap<String,Object>();
		// tempList.add("113.646268,34.755067");
		// tempList.add("113.645989,34.755058");
		// tempList.add("113.645834,34.75493");
		// tempList.add("113.645635,34.754864");
		// tempList.add("113.645501,34.754736");
		// tempList.add("113.645501,34.754512");
		// tempList.add("113.646113,34.754503");
		// tempList.add("113.646258,34.755049");
		// conditonMap.put("LngLat", tempList);
		// conditonMap.put("center", "113.64578,34.754776");
		// resList.add(conditonMap);
		return resList;
	}
	
	@RequiresPermissions(value = "login:exit")
	@CheckPermission(name = "退出", method = "login:exit", state = SysKey.STATE_DEFAULT_DARK)
	@RequestMapping(value = "exit", method = RequestMethod.GET)
	public String exit(HttpSession session, Model model) {

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		return "/login/regist";
	}
}
