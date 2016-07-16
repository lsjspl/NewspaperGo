
package com.Master5.main.web.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Master5.main.utils.constant.SysKey;
import com.Master5.main.web.user.dao.RoleDao;
import com.Master5.main.web.user.entry.Role;

@Service
public class RoleService {

	@Autowired
	RoleDao roleDao;

	public List<Role> findAll() {

		return roleDao.findAll();
	}

	public Map<Integer, String> findRoles() {

		Map<Integer, String> map = new HashMap<Integer, String>();

		List<Role> roles = roleDao.findByStateLessThanEqual(SysKey.STATE_NROMAL);

		for (Role role : roles) {
			map.put(role.getId(), role.getName());
		}

		return map;
	}

	public Role save(Role role) {

		try {
			return roleDao.save(role);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean delete(int id) {

		try {
			roleDao.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Role findById(int id) {

		return roleDao.findOne(id);
	}

	public Role findByState(int state) {

		return roleDao.findByState(state);
	}

}
