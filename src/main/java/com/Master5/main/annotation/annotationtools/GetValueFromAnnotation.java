
package com.Master5.main.annotation.annotationtools;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.Master5.main.annotation.CheckPermission;
import com.Master5.main.web.user.entry.Permission;

public class GetValueFromAnnotation {

	private static GetValueFromAnnotation getValueFromAnnotation;

	private GetValueFromAnnotation() {

	}

	public static GetValueFromAnnotation getInstance() {

		if (null == getValueFromAnnotation) {
			getValueFromAnnotation = new GetValueFromAnnotation();
		}

		return getValueFromAnnotation;
	}

	public Set<Permission> getPermissions(Set<Class<?>> classes) {

		Set<Permission> set = new HashSet<Permission>();

		for (Class<?> cla : classes) {
			Method[] methods = cla.getMethods();
			for (Method m : methods) {
				CheckPermission meta = m.getAnnotation(CheckPermission.class);
				if (meta != null) {
					Permission per = new Permission(meta.name(), meta.method());
					per.setState(meta.state());
					set.add(per);
				}
			}
		}
		return set;
	}

}
