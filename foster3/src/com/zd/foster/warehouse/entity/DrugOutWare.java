/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:03:29
 * @file:DrugOutWare.java
 */
package com.zd.foster.warehouse.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  DrugOutWare
 * 功能：药品出库单表头
 * @author wxb
 * @date 2017-7-26下午04:03:29
 * @version 1.0
 * 
 */
@BussEle(name="药品出库单")
@Entity
@Table(name="FS_DRUGOUTWARE")
public class DrugOutWare extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**出库时间*/
	private String registDate;
	/**代养户*/
	private Farmer farmer;
	/**批次*/
	private Batch batch;
	/**领药单*/
	private String drugBill;
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
	/**饲料厂*/
	private FeedFac feedFac;
	
	
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
	public String getDrugBill() {
		return drugBill;
	}
	public void setDrugBill(String drugBill) {
		this.drugBill = drugBill;
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
	@ManyToOne
	@JoinColumn(name="BATCH")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="feedFac")
	public FeedFac getFeedFac() {
		return feedFac;
	}
	public void setFeedFac(FeedFac feedFac) {
		this.feedFac = feedFac;
	}
}
