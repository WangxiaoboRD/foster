/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午08:02:58
 * @file:DeathBill.java
 */
package com.zd.foster.breed.entity;

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
import com.zd.foster.base.entity.Technician;

/**
 * 类名：  DeathBill
 * 功能：死亡单表头
 * @author wxb
 * @date 2017-8-1下午08:02:58
 * @version 1.0
 * 
 */
@BussEle(name="死亡单")
@Entity
@Table(name="FS_DEATHBILL")
public class DeathBill extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**死亡时间*/
	private String registDate;
	/**代养户*/
	private Farmer farmer;
	/**批次*/
	private Batch batch;
	/**死亡头数*/
	private String totalQuan;
	/**死亡总重量*/
	private String totalWeight;
	/**技术员*/
	private Technician technician;
	/**养殖公司*/
	private Company company;
	/**审核人*/
	private String checkUser;
	/**审核时间*/
	private String checkDate;
	/**审核状态 0：未审核；1：已审核*/
	private String checkStatus;
	/**备注*/
	private String remark;
	/**死亡明细*/
	private List<DeathBillDtl> details;
	/**是否已经结算 N：未结算 Y：已结算*/
	private String isBalance;
	/** 附件上传状态 Y:已上传，N：未上传*/
	private String isAnnex;
	/**是否免责死亡Y:免责死亡，N:非免责死亡*/
	private String isCorDeath;
	/**是否回执 N:未回执;Y：已回执*/
	private String isReceipt;
	/**存栏头数*/
	private String stockNum;
	
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
	public String getTotalQuan() {
		return totalQuan;
	}
	public void setTotalQuan(String totalQuan) {
		this.totalQuan = totalQuan;
	}
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TECHNICIAN")
	public Technician getTechnician() {
		return technician;
	}
	public void setTechnician(Technician technician) {
		this.technician = technician;
	}
	@ManyToOne
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public List<DeathBillDtl> getDetails() {
		return details;
	}
	public void setDetails(List<DeathBillDtl> details) {
		this.details = details;
	}
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	public String getIsAnnex() {
		return isAnnex;
	}
	public void setIsAnnex(String isAnnex) {
		this.isAnnex = isAnnex;
	}
	public String getIsCorDeath() {
		return isCorDeath;
	}
	public void setIsCorDeath(String isCorDeath) {
		this.isCorDeath = isCorDeath;
	}
	public String getIsReceipt() {
		return isReceipt;
	}
	public void setIsReceipt(String isReceipt) {
		this.isReceipt = isReceipt;
	}
	@Transient
	public String getStockNum() {
		return stockNum;
	}
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	
	
}
