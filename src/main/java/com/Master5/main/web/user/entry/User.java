
package com.Master5.main.web.user.entry;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "nickName" }) })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true)
	private String nickName = "没有名字";

	@Column(unique = true)
	private String name;

	private String pass;

	// @Column ( unique = true )
	private String email = "没有email";

	private String sex;

	private int state;

	private String headImg;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>();

	//////////////////// 透明字段

	@Transient
	private String ip;

	@Transient
	private Set<Permission> permissions;

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getPass() {

		return pass;
	}

	public void setPass(String pass) {

		this.pass = pass;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getSex() {

		return sex;
	}

	public void setSex(String sex) {

		this.sex = sex;
	}

	public String getNickName() {

		return nickName;
	}

	public void setNickName(String nickName) {

		this.nickName = nickName;
	}

	public User() {

		super();
	}

	public User(String nickName, String name, String pass, String email, String sex) {

		super();
		this.setNickName(nickName);
		this.name = name;
		this.pass = pass;
		this.email = email;
		this.sex = sex;
		this.state = 0;
	}

	public int getState() {

		return this.state;
	}

	public void setState(int state) {

		this.state = state;
	}

	public Set<Role> getRoles() {

		return roles;
	}

	public void setRoles(Set<Role> roles) {

		this.roles = roles;
	}

	public String getIp() {

		return ip;
	}

	public void setIp(String ip) {

		this.ip = ip;
	}

	public Set<Permission> getPermissions() {

		Set<Permission> pers = new HashSet<Permission>();

		if (this.id == 0) {

			return null;

		} else {

			for (Role role : getRoles()) {
				for (Permission permission : role.getPermissions()) {
					pers.add(permission);
				}
			}

		}

		return pers;
	}

	public void setPermissions(Set<Permission> permissions) {

		this.permissions = permissions;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "User [id=" + id + ", nickName=" + nickName + ", name=" + name + ", pass=" + pass + ", email=" + email
				+ ", sex=" + sex + ", state=" + state + "]";
	}

	public String getHeadImg() {

		return null == headImg || headImg.equals("") ? "defaultHead.png" : headImg;
	}

	public void setHeadImg(String headImg) {

		this.headImg = headImg;
	}

}
