/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26上午10:58:16
 * @file:FeedInWare.java
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
 * 类名：  FeedInWare
 * 功能：领料单表头
 * @author wxb
 * @date 2017-7-26上午10:58:16
 * @version 1.0
 * 
 */
@BussEle(name="领料单")
@Entity
@Table(name="FS_FEEDINWARE")
public class FeedInWare extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**入库时间*/
	private String registDate;
	/**代养户*/
	private Farmer farmer;
	/**运输公司*/
	private Integer transportCo;
	/**司机*/
	private Integer driver;
	/**代养批次*/
	private Batch batch;
	/**审核人*/
	private String checkUser;
	/**审核时间*/
	private String checkDate;
	/**审核状态 0：未审核；1：已审核*/
	private String checkStatus;
	/**养殖公司*/
	private Company company;
	/**饲料厂的发货单码*/
	private String invoiceCode;
	/**备注*/
	private String remark;
	/**饲料入库单明细*/
	private List<FeedInWareDtl> details;
	/**运输公司名称*/
	private String transportCoName;
	/**司机名称*/
	private String driverName;
	/**是否已经结算 N：未结算 Y：已结算*/
	private String isBalance;
	/**关联饲料转接单*/
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
	public Integer getTransportCo() {
		return transportCo;
	}
	public void setTransportCo(Integer transportCo) {
		this.transportCo = transportCo;
	}
	public Integer getDriver() {
		return driver;
	}
	public void setDriver(Integer driver) {
		this.driver = driver;
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
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public List<FeedInWareDtl> getDetails() {
		return details;
	}
	public void setDetails(List<FeedInWareDtl> details) {
		this.details = details;
	}
	@Transient
	public String getTransportCoName() {
		return transportCoName;
	}
	public void setTransportCoName(String transportCoName) {
		this.transportCoName = transportCoName;
	}
	@Transient
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	public String getFeedTransfer() {
		return feedTransfer;
	}
	public void setFeedTransfer(String feedTransfer) {
		this.feedTransfer = feedTransfer;
	}

	
}
