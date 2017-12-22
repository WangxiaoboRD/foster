/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8上午11:51:59
 * @file:DrugTransfer.java
 */
package com.zd.foster.warehouse.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  DrugTransfer
 * 功能：  药品转接单
 * @author wxb
 * @date 2017-9-8上午11:51:59
 * @version 1.0
 * 
 */
@BussEle(name="药品转接单")
@Entity
@Table(name="FS_DRUGTRANSFER")
public class DrugTransfer extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private String id;
	/**转接时间*/
	private String registDate;
	/**转接出代养户*/
	private Farmer outFarmer;
	/**转接入代养户*/
	private Farmer inFarmer;
	/**转出代养批次*/
	private Batch outBatch;
	/**转入批次*/
	private Batch inBatch;
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
	/**药品转接单明细*/
	private List<DrugTransferDtl> details;
	/**是否已经结算 N：未结算 Y：已结算*/
	private String isBalance;
	
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
	@JoinColumn(name="OUTFARMER")
	public Farmer getOutFarmer() {
		return outFarmer;
	}
	public void setOutFarmer(Farmer outFarmer) {
		this.outFarmer = outFarmer;
	}
	@ManyToOne
	@JoinColumn(name="INFARMER")
	public Farmer getInFarmer() {
		return inFarmer;
	}
	public void setInFarmer(Farmer inFarmer) {
		this.inFarmer = inFarmer;
	}
	@ManyToOne
	@JoinColumn(name="OUTBATCH")
	public Batch getOutBatch() {
		return outBatch;
	}
	public void setOutBatch(Batch outBatch) {
		this.outBatch = outBatch;
	}
	@ManyToOne
	@JoinColumn(name="INBATCH")
	public Batch getInBatch() {
		return inBatch;
	}
	public void setInBatch(Batch inBatch) {
		this.inBatch = inBatch;
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
	public List<DrugTransferDtl> getDetails() {
		return details;
	}
	public void setDetails(List<DrugTransferDtl> details) {
		this.details = details;
	}
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
}
