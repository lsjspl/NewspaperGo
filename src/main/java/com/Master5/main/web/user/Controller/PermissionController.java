
package com.Master5.main.web.user.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.Master5.main.web.user.service.PermissionService;

@Controller
@RequestMapping(value = "permission")
public class PermissionController {

	private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	PermissionService permissionService;

	@RequiresPermissions(value = "permission:list")
	@CheckPermission(name = "权限列表", method = "permission:list", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "list")
	public String findAll(Model model) {

		List<Permission> list = permissionService.findAll();

		logger.info(list.toString());

		model.addAttribute("list", list);

		return "user/permission";
	}

	@RequiresPermissions(value = "permission:add")
	@CheckPermission(name = "增加权限", method = "permission:add", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@ModelAttribute Permission permission, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		if (StringUtils.isNotEmpty(permission.getName()) && StringUtils.isNotEmpty(permission.getMethod())) {
			if (null == permissionService.save(permission)) {
				msgList.add(MsgTips.ADD_FAILY);
			} else {
				msgList.add(MsgTips.ADD_SUCCESS);
			}
		} else {
			msgList.add(MsgTips.ENTRY_EMPTY);
		}
		redirectAttributes.addFlashAttribute(Key.msg, msgList);
		return "redirect:permission";
	}

	@RequiresPermissions(value = "permission:modify")
	@CheckPermission(name = "修改权限", method = "permission:modify", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify(int id, String name, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		Permission permission = permissionService.findById(id);

		if (null == permission) {
			msgList.add(MsgTips.NO_THIS_DATA);
			redirectAttributes.addFlashAttribute(Key.msg, msgList);
			return "redirect:list";
		} else if (permission.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
			redirectAttributes.addFlashAttribute(Key.msg, msgList);
			return "redirect:list";
		}

		if (StringUtils.isNotEmpty(name))
			permission.setName(name);
		if (null == permissionService.save(permission)) {
			msgList.add(MsgTips.MODIFY_FAILY);
		} else {
			msgList.add(MsgTips.MODIFY_SUCCESS);
		}
		redirectAttributes.addFlashAttribute(Key.msg, msgList);
		return "redirect:list";
	}

	@RequiresPermissions(value = "permission:del")
	@CheckPermission(name = "删除权限", method = "permission:del", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "del/{id}")
	public String del(@PathVariable int id, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		Permission permission = permissionService.findById(id);

		if (null == permission) {
			msgList.add(MsgTips.DEL_FAILY);
			msgList.add(MsgTips.NO_THIS_DATA);
		} else if (permission.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
		} else if (permissionService.delete(id)) {
			msgList.add(MsgTips.DEL_SUCCESS);
		} else {
			msgList.add(MsgTips.DEL_FAILY);
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:../list";
	}

	@RequiresPermissions(value = "permission:lock")
	@CheckPermission(name = "锁定权限", method = "permission:lock", state = SysKey.STATE_DEFAULT_ADMIN)
	@RequestMapping(value = "lock/{id}", method = RequestMethod.GET)
	public String lock(@PathVariable int id, RedirectAttributes redirectAttributes) {

		List<String> msgList = new ArrayList<String>();

		Permission per = permissionService.findById(id);
		if (null == per) {
			msgList.add(MsgTips.MODIFY_FAILY);
			msgList.add(MsgTips.NO_THIS_DATA);
		} else if (per.getState() < SysKey.STATE_NROMAL) {
			msgList.add(MsgTips.MODIFY_FAILY);
			msgList.add(MsgTips.DEFAULT_SYSTEM_DATA);
		} else {
			per.setState(per.getState() == SysKey.STATE_NROMAL ? SysKey.STATE_LOCK : SysKey.STATE_NROMAL);
			if (null != permissionService.save(per)) {
				msgList.add(MsgTips.MODIFY_SUCCESS);
			} else {
				msgList.add(MsgTips.MODIFY_FAILY);
			}
		}
		redirectAttributes.addFlashAttribute(MsgKey.msg, msgList);
		return "redirect:../list";
	}

	@RequestMapping(value = "getAllPers", method = RequestMethod.POST)
	@ResponseBody
	public List<Permission> getAllPermission() {

		return permissionService.findAll();
	}

	@RequestMapping(value = "getPerGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPerGroup() {

		return permissionService.findAllByMethod();
	}

}
