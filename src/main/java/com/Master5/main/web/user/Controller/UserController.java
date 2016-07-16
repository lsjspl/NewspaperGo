package com.Master5.main.web.user.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Master5.main.annotation.CheckPermission;
import com.Master5.main.utils.ValidateTools;
import com.Master5.main.utils.constant.Key;
import com.Master5.main.utils.constant.MsgKey;
import com.Master5.main.utils.constant.MsgTips;
import com.Master5.main.utils.constant.SysKey;
import com.Master5.main.utils.encrypt.MD5;
import com.Master5.main.web.user.entry.Role;
import com.Master5.main.web.user.entry.User;
import com.Master5.main.web.user.service.RoleService;
import com.Master5.main.web.user.service.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;

	@RequiresPermissions(value = "user:list")
	@CheckPermission(name = "用户列表", method = "user:list", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "list")
	public String findAll(Model model) {

		model.addAttribute("list", userService.findAll());

		return "user/list";
	}

	@CheckPermission(name = "添加用户", method = "user:add", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@RequiresPermissions(value = "user:add")
	public String add(String nickName, String name, String pass, String sex, String roleIds,
			RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		User user = new User();

		String checkRs = checkReg(nickName, name, pass);
		if (StringUtils.isNotEmpty(checkRs)) {
			msgList.add(checkRs);
			redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
			return "redirect:list";
		}

		Set<Role> roleSet = new HashSet<Role>();

		if (StringUtils.isEmpty(roleIds)) {
			roleSet.add(roleService.findByState(Key.STATE_DEFAULT_USER));
			user.setRoles(roleSet);
		} else {
			String[] perIdArray = roleIds.split(",");
			for (String perId : perIdArray) {
				roleSet.add(roleService.findById(Integer.parseInt(perId)));
			}
			user.setRoles(roleSet);
		}
		user.setSex(sex);
		user.setPass(MD5.getMD5Pass(pass));
		user.setName(name);
		user.setNickName(nickName);
		if (null != userService.save(user)) {
			msgList.add(MsgTips.ADD_SUCCESS);
		} else {
			msgList.add(MsgTips.ADD_FAILY);
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:list";
	}

	@RequiresPermissions(value = "user:modify")
	@CheckPermission(name = "添加用户", method = "user:modify", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify(long id, String nickName, String name, String pass, String sex, String roleIds,
			RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		User user = userService.findById(id);

		if (null == user) {
			msgList.add(MsgTips.NO_THIS_DATA);
			redirectAttributes.addFlashAttribute(Key.msg, msgList);
			return "redirect:list";
		} else if (user.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
			redirectAttributes.addFlashAttribute(Key.msg, msgList);
			return "redirect:list";
		}

		String checkRs = checkReg(user, nickName, name, pass);

		if (StringUtils.isNotEmpty(checkRs)) {
			msgList.add(checkRs);
			redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
			return "redirect:list";
		}

		// 如果参数不为空///
		Set<Role> roleSet = new HashSet<Role>();

		if (StringUtils.isNotEmpty(roleIds)) {// 如果是修改操作 并且角色串不是空 解析角色

			String[] perIdArray = roleIds.split(",");

			for (String perId : perIdArray) {
				roleSet.add(roleService.findById(Integer.parseInt(perId)));
			}

			user.setRoles(roleSet);
		}

		if (null != userService.save(user)) {

			msgList.add(MsgTips.MODIFY_SUCCESS);
		} else {

			msgList.add(MsgTips.MODIFY_FAILY);
		}

		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);

		return "redirect:list";
	}

	@RequiresPermissions(value = "user:del")
	@CheckPermission(name = "删除用户", method = "user:del", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "del/{id}")
	public String del(@PathVariable long id, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		User user = userService.findById(id);

		if (null == user) {
			msgList.add(MsgTips.DEL_FAILY);
			msgList.add(MsgTips.NO_THIS_DATA);
		} else if (user.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
		} else if (userService.delete(id)) {
			msgList.add(MsgTips.DEL_SUCCESS);
		} else {
			msgList.add(MsgTips.DEL_FAILY);
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:../list";
	}

	@RequiresPermissions(value = "user:lock")
	@CheckPermission(name = "锁定用户", method = "user:lock", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "lock/{id}", method = RequestMethod.GET)
	public String lock(@PathVariable long id, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		User user = userService.findById(id);
		if (null == user) {
			msgList.add(MsgTips.MODIFY_FAILY);
			msgList.add(MsgTips.NO_THIS_DATA);
		} else if (user.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.MODIFY_FAILY);
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
		} else {
			user.setState(user.getState() == SysKey.STATE_NROMAL ? SysKey.STATE_LOCK : SysKey.STATE_NROMAL);
			if (null != userService.save(user)) {
				msgList.add(MsgTips.MODIFY_SUCCESS);
			} else {
				msgList.add(MsgTips.MODIFY_FAILY);
			}
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:../list";
	}

	@RequiresPermissions(value = "user:info")
	@CheckPermission(name = "当前用户信息", method = "user:info", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String info(Model model) {

		return "/user/info";
	}

	/**
	 * 异步验证注册数据的正确性
	 */
	@RequestMapping(value = "checkAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkRegAjax(long id, String nickName, String name, String pass) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("errMsg", id == 0 ? checkReg(name, pass) : checkReg(userService.findById(id), nickName, name, pass));

		return map;
	}

	/**
	 * 修改用户验证的合法性
	 */
	private String checkReg(User user, String nickName, String name, String pass) {

		if (null == user)
			return "修改的用户不存在";

		if (!user.getName().equals(name) && null != name && !"".equals(name)) {
			if (null != userService.findByName(name))
				return "用户名已经存在";
			else if (!ValidateTools.userName(name))
				return "用户名只能为英文和数字";
			else if (null == name || name.length() > 18 || name.length() < 6)
				return "用户名的长度必须在6到18之间";
			else {
				user.setName(name);
			}
		} else if (!user.getNickName().equals(nickName) && null != nickName && !"".equals(nickName)) {

			if (!ValidateTools.nickName(nickName)) {
				return "昵称只能为英文数字和汉字";
			} else if (null == nickName || nickName.length() > 8 || nickName.length() < 2) {
				return "昵称长度必须2-10";
			} else {
				user.setNickName(nickName);
			}
		} else if (!user.getPass().equals(MD5.getMD5(pass)) && null != pass && !"".equals(pass)) {
			if (!ValidateTools.password_reg(pass))
				return "密码只能为英文字母和数字";
			else if (null == pass || pass.length() > 18 || pass.length() < 6)
				return "密码的长度必须在6到18之间";
			else {
				user.setPass(MD5.getMD5Pass(pass));
			}
		}
		return "";

	}

	/**
	 * 添加用户字段验证的合法性
	 */
	private String checkReg(String nickName, String name, String pass) {

		if (!ValidateTools.nickName(nickName))
			return "昵称只能为英文数字和汉字";
		else if (null == nickName || nickName.length() > 8 || nickName.length() < 2)
			return "昵称长度必须2-10";

		return checkReg(name, pass);

	}

	private String checkReg(String name, String pass) {

		if (null != userService.findByName(name))
			return "用户名已经存在";
		else if (!ValidateTools.userName(name))
			return "用户名只能为英文和数字";
		else if (null == name || name.length() > 18 || name.length() < 6)
			return "用户名的长度必须在6到18之间";

		else if (!ValidateTools.password_reg(pass))
			return "密码只能为英文字母和数字";
		else if (null == pass || pass.length() > 18 || pass.length() < 6)
			return "密码的长度必须在6到18之间";
		return "";

	}

}
