
package com.Master5.main.web.user.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Master5.main.annotation.CheckPermission;
import com.Master5.main.utils.constant.Key;
import com.Master5.main.utils.constant.MsgKey;
import com.Master5.main.utils.constant.MsgTips;
import com.Master5.main.utils.constant.SysKey;
import com.Master5.main.web.user.entry.Permission;
import com.Master5.main.web.user.entry.Role;
import com.Master5.main.web.user.service.PermissionService;
import com.Master5.main.web.user.service.RoleService;

@Controller
@RequestMapping(value = "role")
public class RoleController {

	private static final Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	RoleService roleService;

	@Autowired
	PermissionService permissionService;

	@CheckPermission(name = "角色列表", method = "role:list", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "list")
	@RequiresPermissions(value = "role:list")
	public String findAll(Model model) {

		model.addAttribute("list", roleService.findAll());
		return "user/role";
	}
	
	@ResponseBody
	@RequestMapping(value = "me/{id}")
	public Role findAll(@PathVariable int id) {
		return roleService.findById(id);
	}

	@CheckPermission(name = "添加角色", method = "role:add", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@RequiresPermissions(value = "role:add")
	public String add(@ModelAttribute Role role, int[] perIds, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		if (StringUtils.isNotEmpty(role.getName()) && perIds.length>0) {

			Set<Permission> perSet = new HashSet<Permission>();

			for (int perId : perIds) {
				perSet.add(permissionService.findById( perId));
			}
			role.setPermissions(perSet);
			if (null == roleService.save(role)) {
				msgList.add(MsgTips.ADD_FAILY);
			} else {
				msgList.add(MsgTips.ADD_SUCCESS);
			}
		} else {
			msgList.add(MsgTips.ENTRY_EMPTY);
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:list";
	}

	@RequiresPermissions(value = "role:modify")
	@CheckPermission(name = "添加角色", method = "role:modify", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify( Role role, int[] perIds, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		if (null == role) {
			msgList.add(MsgTips.NO_THIS_DATA);
			redirectAttributes.addFlashAttribute(Key.msg, msgList);
			return "redirect:list";
		} else if (role.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
			redirectAttributes.addFlashAttribute(Key.msg, msgList);
			return "redirect:list";
		}

		if (perIds.length>0) {
			Set<Permission> perSet = new HashSet<Permission>();

			for (int perId : perIds) {
				perSet.add(permissionService.findById(perId));
			}
			role.setPermissions(perSet);
		}

		if (null == roleService.save(role)) {
			msgList.add(MsgTips.ADD_FAILY);
		} else {
			msgList.add(MsgTips.ADD_SUCCESS);
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:list";
	}

	@RequiresPermissions(value = "role:del")
	@CheckPermission(name = "删除角色", method = "role:del", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "del/{id}")
	public String del(@PathVariable int id, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		Role role = roleService.findById(id);

		if (null == role) {
			msgList.add(MsgTips.DEL_FAILY);
			msgList.add(MsgTips.NO_THIS_DATA);
		} else if (role.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
		} else if (roleService.delete(id)) {
			msgList.add(MsgTips.DEL_SUCCESS);
		} else {
			msgList.add(MsgTips.DEL_FAILY);
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:../list";
	}

	@RequiresPermissions(value = "role:lock")
	@CheckPermission(name = "锁定角色", method = "role:lock", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "lock/{id}", method = RequestMethod.GET)
	public String lock(@PathVariable int id, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		Role role = roleService.findById(id);

		if (null == role) {
			msgList.add(MsgTips.MODIFY_FAILY);
			msgList.add(MsgTips.NO_THIS_DATA);
		} else if (role.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.MODIFY_FAILY);
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
		} else {
			role.setState(role.getState() == SysKey.STATE_NROMAL ? SysKey.STATE_LOCK : SysKey.STATE_NROMAL);
			if (null != roleService.save(role)) {
				msgList.add(MsgTips.MODIFY_SUCCESS);
			} else {
				msgList.add(MsgTips.MODIFY_FAILY);
			}
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:../list";
	}

	@RequestMapping(value = "getAllRoles", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer, String> getAllRole() {

		return roleService.findRoles();
	}

}
