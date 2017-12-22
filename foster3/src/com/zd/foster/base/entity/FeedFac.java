/**
 * 功能:
 * @author:wxb
 * @data:2017-7-19下午02:58:58
 * @file:FeedFac.java
 */
package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;

/**
 * 类名：  FeedFac
 * 功能：饲料厂基础类
 * @author wxb
 * @date 2017-7-19下午02:58:58
 * @version 1.0
 * 
 */
@BussEle(name="饲料厂")
@Entity
@Table(name="FS_FEEDFAC")
public class FeedFac extends BaseEntity {

	/**饲料厂id*/
	private String id;
	/**饲料厂编码*/
	private String code;
	/**饲料厂名称*/
	private String name;
	/**养殖公司*/
	private Company company;
	
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
	@ManyToOne
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
}
