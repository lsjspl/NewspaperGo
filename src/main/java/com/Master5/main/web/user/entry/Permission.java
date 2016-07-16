
package com.Master5.main.web.user.entry;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "permission", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "method" }) })
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "权限名称不能为空")
	private String name;// 权限名称

	@NotBlank(message = "权限不能为空")
	private String method;// 权限对应的方法名称

	private int state;

	public int getState() {

		return state;
	}

	public void setState(int state) {

		this.state = state;
	}

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

	public String getMethod() {

		return method;
	}

	public void setMethod(String method) {

		this.method = method;
	}

	public Permission() {

		super();
	}

	public Permission(String name, String method) {

		super();
		this.name = name;
		this.method = method;
		this.state = 1;
	}

	@Override
	public String toString() {

		return "Permission [id=" + id + ", name=" + name + ", method=" + method + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Permission other = (Permission) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
