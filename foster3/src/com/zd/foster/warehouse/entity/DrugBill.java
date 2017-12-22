/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8上午10:03:00
 * @file:DrugBill.java
 */
package com.zd.foster.warehouse.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  DrugBill
 * 功能： 领药单
 * @author wxb
 * @date 2017-9-8上午10:03:00
 * @version 1.0
 * 
 */
@BussEle(name="领药单")
@Entity
@Table(name="FS_DRUGBILL")
public class DrugBill extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	/**领药时间*/
	private String registDate;
	/**代养户*/
	private Farmer farmer;
	/**批次*/
	private Batch batch;
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
	/**领药单明细*/
	private List<DrugBillDtl> details;
	/**是否已经结算 N：未结算 Y：已结算*/
	private String isBalance;
	/**关联转接单*/
	private String drugTransfer;
	/**是否回执 N:未回执;Y：已回执*/
	private String isReceipt;
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
	@ManyToOne
	@JoinColumn(name="BATCH")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
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
	@Transient
	public List<DrugBillDtl> getDetails() {
		return details;
	}
	public void setDetails(List<DrugBillDtl> details) {
		this.details = details;
	}
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	public String getDrugTransfer() {
		return drugTransfer;
	}
	public void setDrugTransfer(String drugTransfer) {
		this.drugTransfer = drugTransfer;
	}
	public String getIsReceipt() {
		return isReceipt;
	}
	public void setIsReceipt(String isReceipt) {
		this.isReceipt = isReceipt;
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
