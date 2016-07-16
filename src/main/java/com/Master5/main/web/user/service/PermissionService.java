
package com.Master5.main.web.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Master5.main.web.user.dao.PermissionDao;
import com.Master5.main.web.user.entry.Permission;

@Service
public class PermissionService {

	@Autowired
	PermissionDao permissionDao;

	public Permission findById(int id) {

		return permissionDao.findOne(id);
	}

	public List<Permission> findByStateLessThanEqual(int state) {

		return permissionDao.findByStateLessThanEqual(state);
	}

	public List<Permission> findAll() {

		return permissionDao.findAll();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> findAllByMethod() {

		List<Permission> perList = permissionDao.findAll();

		Map<String, Object> data = new HashMap<String, Object>();

		List<Permission> dataList = null;

		for (Permission per : perList) {

			if (per.getState() == 0)
				continue;

			String method = per.getMethod();
			method = method.substring(0, method.indexOf(":"));
			if (data.containsKey(method)) {
				dataList = (List<Permission>) data.get(method);
				dataList.add(per);
			} else {
				dataList = new ArrayList<Permission>();
				dataList.add(per);
				data.put(method, dataList);
			}
		}

		return data;
	}

	public Permission save(Permission permission) {

		try {
			return permissionDao.save(permission);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Permission> save(Collection<Permission> permissions) {

		try {
			return permissionDao.save(permissions);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean delete(int id) {

		try {
			permissionDao.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
