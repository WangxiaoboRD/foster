package com.zd.epa.permission.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Technician;


@BussEle(name = "用户")
@Entity
@Table(name="base_user")
public class Users extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String userCode;
	private String userRealName;
	private String userPassword;
	/** 用户状态：1-启用，2-禁用 */
	private int userStatus;
	/** 超级管理员标示 */
	private Character superMark;
	/** 岗位 */
	private String post;
	/** 拼音首字母：用于模糊查询 */
	private String pinyinHead;
	
	
	
	private List<Role> roleSet = new ArrayList<Role>();// 管理角色
	
	
	//----------------技术员----------------
	private Technician technician;
	private Company company;
	
	
	@Id
	public String getUserCode() {	
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_userrole",joinColumns=@JoinColumn(name="userCode"),inverseJoinColumns=@JoinColumn(name="roleCode"))
	public List<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(List<Role> roleSet) {
		this.roleSet = roleSet;
	}
	/**
	 * 获得 superMark值
	 */
	@Column(insertable = false)
	public Character getSuperMark() {
		return superMark;
	}
	/**
	 * 将superMark设置为参数superMark
	 */
	public void setSuperMark(Character superMark) {
		this.superMark = superMark;
	}
	/**
	 * 获得 post值
	 */
	public String getPost() {
		return post;
	}
	/**
	 * 将post设置为参数post
	 */
	public void setPost(String post) {
		this.post = post;
	}
	/**
	 * 获得 pinyinHead值
	 */
	public String getPinyinHead() {
		return pinyinHead;
	}
	/**
	 * 将pinyinHead设置为参数pinyinHead
	 */
	public void setPinyinHead(String pinyinHead) {
		this.pinyinHead = pinyinHead;
	}
	
	@ManyToOne
    @JoinColumn(name="technician")
	public Technician getTechnician() {
		return technician;
	}
	public void setTechnician(Technician technician) {
		this.technician = technician;
	}
	
	@ManyToOne
	@JoinColumn(name="company")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
}
