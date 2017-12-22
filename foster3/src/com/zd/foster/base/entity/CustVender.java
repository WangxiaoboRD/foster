
package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.epa.bussobj.entity.BussinessEleDetail;

/**
 * 类名：  CustVender
 * 功能：供应商经销商基础表
 * @author wxb
 * @date 2017-7-19下午03:20:14
 * @version 1.0
 * 
 */
@BussEle(name="供应商经销商")
@Entity
@Table(name="FS_CUSTVENDER")
public class CustVender extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**供应商经销商id*/
	private String id;
	/**供应商经销商编码*/
	private String code;
	/**供应商经销商名称*/
	private String name;
	/**供应商经销商类型*/
	private BussinessEleDetail custVenderType;
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
	@JoinColumn(name="CUSTVENDERTYPE")
	public BussinessEleDetail getCustVenderType() {
		return custVenderType;
	}
	public void setCustVenderType(BussinessEleDetail custVenderType) {
		this.custVenderType = custVenderType;
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
