/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午03:41:49
 * @file:PigPurchas.java
 */
package com.zd.foster.breed.entity;

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
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.entity.Driver;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.base.entity.TransportCo;

/**
 * 类名：  PigPurchas
 * 功能：猪苗登记单
 * @author wxb
 * @date 2017-8-1下午03:41:49
 * @version 1.0
 * 
 */
@BussEle(name="猪苗登记单")
@Entity
@Table(name="FS_PIGPURCHASE")
public class PigPurchase extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**登记时间*/
	private String registDate;
	/**代养户*/
	private Farmer farmer;
	/**批次*/
	private Batch batch;
	/**猪苗供应商*/
	private CustVender pigletSupplier;
	/**数量*/
	private String quantity;
	/**重量*/
	private String weight;
	
	
	/**运费*/
	private String freight;
	/**成本单价*/
	private String price;
	/**成本*/
	private String cost;
	/**日龄*/
	private String days;
	/**运输公司*/
	private TransportCo transportCo;
	/**司机*/
	private Driver driver;
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
	/**是否已经结算 N：未结算 Y：已结算*/
	private String isBalance;
	/**备注*/
	private String remark;
	/**车牌号*/
	private String carNum;
	/**是否回执 N:未回执;Y：已回执*/
	private String isReceipt;
	
	/**均量*/
	private String aveWeight;
	/**实际单价*/
	private String actPrice;
	/**赠送头数*/
	private String giveQuantity;
	
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PIGLETSUPPLIER")
	public CustVender getPigletSupplier() {
		return pigletSupplier;
	}
	public void setPigletSupplier(CustVender pigletSupplier) {
		this.pigletSupplier = pigletSupplier;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TRANSPORTCO")
	public TransportCo getTransportCo() {
		return transportCo;
	}
	public void setTransportCo(TransportCo transportCo) {
		this.transportCo = transportCo;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DRIVER")
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	@ManyToOne
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
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getIsReceipt() {
		return isReceipt;
	}
	public void setIsReceipt(String isReceipt) {
		this.isReceipt = isReceipt;
	}
	public String getGiveQuantity() {
		return giveQuantity;
	}
	public void setGiveQuantity(String giveQuantity) {
		this.giveQuantity = giveQuantity;
	}
	@Transient
	public String getAveWeight() {
		return aveWeight;
	}
	public void setAveWeight(String aveWeight) {
		this.aveWeight = aveWeight;
	}
	
	@Transient
	public String getActPrice() {
		return actPrice;
	}
	public void setActPrice(String actPrice) {
		this.actPrice = actPrice;
	}
}
