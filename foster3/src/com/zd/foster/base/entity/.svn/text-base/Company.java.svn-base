/**
 * 功能:
 * @author:wxb
 * @data:2017-7-19下午02:01:10
 * @file:Company.java
 */
package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;

/**
 * 类名：  Company
 * 功能：养殖公司基础类
 * @author wxb
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@BussEle(name = "养殖公司")
@Entity
@Table(name="FS_COMPANY")
public class Company extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**养殖公司id*/
	private String id;
	/**养殖公司编码*/
	private String code;
	/**养殖公司名称*/
	private String name;
	/**养殖公司地址*/
	private String address;
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
