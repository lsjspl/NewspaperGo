package com.Master5.main.web.record.entry;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "record")
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;// id

	private String userName;// 操作用户的名字

	private String method;// 用户操作的方法

	private String data;// 操作的数据

	private String ip;// 操作用户的ip

	private Date recordTime;// 操作的时间

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getMethod() {

		return method;
	}

	public void setMethod(String method) {

		this.method = method;
	}

	public String getData() {

		return data;
	}

	public void setData(String data) {

		this.data = data;
	}

	public String getIp() {

		return ip;
	}

	public void setIp(String ip) {

		this.ip = ip;
	}

	public Date getRecordTime() {

		return recordTime;
	}

	public void setRecordTime(Date recordTime) {

		this.recordTime = recordTime;
	}

	@Override
	public String toString() {

		return "Record [id=" + id + ", userName=" + userName + ", method=" + method + ", data=" + data + ", ip=" + ip
				+ ", recordTime=" + recordTime + "]";
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
		Record other = (Record) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
