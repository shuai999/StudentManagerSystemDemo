package com.itheima.studentinfo.domain;

/**
 * 学生信息的业务bean
 */
public class StudentInfo {
	private String id;
	private String name;
	private String phone;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "StudentInfo [id=" + id + ", name=" + name + ", phone=" + phone
				+ "]";
	}

}
