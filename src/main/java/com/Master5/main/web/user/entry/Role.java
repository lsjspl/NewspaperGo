
package com.Master5.main.web.user.entry;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 权限验证准备使用注解 权限 为一个方法的字符串 权限验证将细化到按钮级别
 * 
 * @author 刘三军
 *
 */
@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;// id

	@NotBlank(message = "角色名称不能为空")
	private String name;// 角色名称

	private int state;// 默认角色不锁定

//	@ManyToMany(mappedBy = "roles")
//	private Set<User> users = new HashSet<User>();// 角色对于用户是多对多

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "permission_id") })
	private Set<Permission> permissions = new HashSet<Permission>();// 权限对于角色是多对多

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

//	public Set<User> getUsers() {
//
//		return users;
//	}
//
//	public void setUsers(Set<User> users) {
//
//		this.users = users;
//	}

	public Set<Permission> getPermissions() {

		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {

		this.permissions = permissions;
	}

	public int getState() {

		return state;
	}

	public void setState(int state) {

		this.state = state;
	}

	public Role() {

		super();
	}

	public Role(String name, Set<Permission> permissions) {

		super();
		this.name = name;
		this.permissions = permissions;
		this.state = 1;
	}

	@Override
	public String toString() {

		return "Role [id=" + id + ", name=" + name + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
