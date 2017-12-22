package com.zd.foster.sale.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.foster.breed.entity.Batch;
import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.entity.Farmer;

/**
 * 类名：  FarmerSale
 * 功能：代养户销售
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@BussEle(name = "代养户销售")
@Entity
@Table(name="FS_FARMERSALEBILL")
public class FarmerSale extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**id*/
	private String id;
	/**批次*/
	private Batch batch;
	/**代养户*/
	private Farmer farmer;
	/**养殖公司*/
	private Company company;
	/**销售日期*/
	private String registDate;
	/**销售总数*/
	private String totalQuan;
	/**总重量*/
	private String totalWeight;
	/**总金额*/
	private String totalMoney;
	//公司销售模块
	/**公司销售总数*/
	private String quantity;
	/**公司总重量*/
	private String weight;
	/**公司总金额*/
	private String amount;
	/**收购商*/
	private CustVender buyer;
	/**运输费*/
	private String tcost;
	/** 审核人 */
	private String checkUser;
	/** 审核时间 */
	private String checkDate;
	/** 审核状态 */
	private String checkStatus;
	/** 是否已经结算 */
	private String isBalance;
	/** 出栏单 */
	private OutPigsty outPigsty;
	
	/**销售单明细*/ 
	private List<FarmerSaleDtl> details;
	
	
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
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
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
	
	@Transient
	public List<FarmerSaleDtl> getDetails() {
		return details;
	}
	public void setDetails(List<FarmerSaleDtl> details) {
		this.details = details;
	}
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@ManyToOne
	@JoinColumn(name = "buyer")
	public CustVender getBuyer() {
		return buyer;
	}
	public void setBuyer(CustVender buyer) {
		this.buyer = buyer;
	}
	public String getTcost() {
		return tcost;
	}
	public void setTcost(String tcost) {
		this.tcost = tcost;
	}
	@OneToOne
	@JoinColumn(name = "outPigsty")
	public OutPigsty getOutPigsty() {
		return outPigsty;
	}
	public void setOutPigsty(OutPigsty outPigsty) {
		this.outPigsty = outPigsty;
	}
}
