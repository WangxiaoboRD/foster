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
 * 类名：  FeedBill
 * 功能：喂料单表头
 * @author wxb
 * @date 2017-7-27上午08:42:24
 * @version 1.0
 * 
 */
@BussEle(name="喂料单")
@Entity
@Table(name="FS_FEEDBILL")
public class FeedBill extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**喂料时间*/
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
	/**喂料单明细*/
	private List<FeedBillDtl> details;
	/**是否已经结算 N：未结算 Y：已结算*/
	private String isBalance;
	/**饲料录入单ID*/
	private String feedUseId;
	/**技术员*/
	private Technician technician;
	/**转接单*/
	private String feedTransfer;

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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FARMER")
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	@ManyToOne(fetch=FetchType.LAZY)
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
	@ManyToOne(fetch=FetchType.LAZY)
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
	public List<FeedBillDtl> getDetails() {
		return details;
	}
	public void setDetails(List<FeedBillDtl> details) {
		this.details = details;
	}
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	public String getFeedUseId() {
		return feedUseId;
	}
	public void setFeedUseId(String feedUseId) {
		this.feedUseId = feedUseId;
	}
	@Transient
	public Technician getTechnician() {
		return technician;
	}
	public void setTechnician(Technician technician) {
		this.technician = technician;
	}
	public String getFeedTransfer() {
		return feedTransfer;
	}
	public void setFeedTransfer(String feedTransfer) {
		this.feedTransfer = feedTransfer;
	}
	
}
