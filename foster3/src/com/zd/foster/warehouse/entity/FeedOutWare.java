/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午02:04:17
 * @file:FeedOutWare.java
 */
package com.zd.foster.warehouse.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.FeedBill;

/**
 * 类名：  FeedOutWare
 * 功能：饲料出库单表头
 * @author wxb
 * @date 2017-7-26下午02:04:17
 * @version 1.0
 * 
 */
@BussEle(name="饲料出库单")
@Entity
@Table(name="FS_FEEDOUTWARE")
public class FeedOutWare extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**出库时间*/
	private String registDate;
	/**代养户*/
	private Farmer farmer;
	/**喂料单*/
	private FeedBill feedBill;
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
	/**出库单明细*/
	private List<FeedOutWareDtl> details;
	/**批次*/
	private Batch batch;
	/**关联转接单*/
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
	@ManyToOne
	@JoinColumn(name="FARMER")
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	@ManyToOne
	@JoinColumn(name="FEEDBILL")
	public FeedBill getFeedBill() {
		return feedBill;
	}
	public void setFeedBill(FeedBill feedBill) {
		this.feedBill = feedBill;
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
	public List<FeedOutWareDtl> getDetails() {
		return details;
	}
	public void setDetails(List<FeedOutWareDtl> details) {
		this.details = details;
	}
	@ManyToOne
	@JoinColumn(name="BATCH")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public String getFeedTransfer() {
		return feedTransfer;
	}
	public void setFeedTransfer(String feedTransfer) {
		this.feedTransfer = feedTransfer;
	}
	
	
	
	
	

}
