/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午05:00:45
 * @file:MaterialOutWare.java
 */
package com.zd.foster.warehouse.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.MaterialBill;

/**
 * 类名：  MaterialOutWare
 * 功能：其他物理出库单表头
 * @author wxb
 * @date 2017-7-26下午05:00:45
 * @version 1.0
 * 
 */
@BussEle(name="其他物料出库单")
@Entity
@Table(name="FS_MATERIALOUTWARE")
public class MaterialOutWare extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**出库时间*/
	private String registDate;
	/**代养户*/
	private Farmer farmer;
	/**领用单*/
	private MaterialBill materialBill;
	/**审核人*/
	private String checkUser;
	/**审核时间*/
	private String checkDate;
	/**审核状态 0：未审核；1：已审核*/
	private String checkStatus;
	/**养殖公司*/
	private Company company;
	/**备注*/
	private String remark;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	@ManyToOne
	@JoinColumn(name="FARMER")
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	@ManyToOne
	@JoinColumn(name="MATERIALBILL")
	public MaterialBill getMaterialBill() {
		return materialBill;
	}
	public void setMaterialBill(MaterialBill materialBill) {
		this.materialBill = materialBill;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	@ManyToOne
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
