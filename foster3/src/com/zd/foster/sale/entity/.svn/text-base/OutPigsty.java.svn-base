package com.zd.foster.sale.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  OutPigsty
 * 功能：出栏单
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@BussEle(name = "出栏单")
@Entity
@Table(name="FS_OUTPIGSTY")
public class OutPigsty extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**id*/
	private String id;
	/**批次*/
	private Batch batch;
	/**代养户*/
	private Farmer farmer;
	/**养殖公司*/
	private Company company;
	/**出栏日期*/
	private String registDate;
	/**出栏总数*/
	private String quantity;
	/**出栏重量*/
	private String weight;
	
	/** 审核人 */
	private String checkUser;
	/** 审核时间 */
	private String checkDate;
	/** 审核状态 */
	private String checkStatus;
	/** 是否已经结算 */
	private String isBalance;
	/** 销售单 */
	private String farmerSale;
	
	/** 销售类型    Q 正常出栏 E 淘汰出栏 */
	private String saleType;
	
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
	@JoinColumn(name = "batch")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
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
	@ManyToOne
	@JoinColumn(name = "farmer")
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	@ManyToOne
	@JoinColumn(name = "company")
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
	public String getFarmerSale() {
		return farmerSale;
	}
	public void setFarmerSale(String farmerSale) {
		this.farmerSale = farmerSale;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
}
