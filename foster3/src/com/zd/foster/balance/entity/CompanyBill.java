package com.zd.foster.balance.entity;

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
 * 公司账单
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 上午11:50:04
 */
@BussEle(name = "公司账单")
@Entity
@Table(name="FS_COMPANYBILL")
public class CompanyBill extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**id*/
	private String id;
	/**批次*/
	private Batch batch;
	/**代养户*/
	private Farmer farmer;
	/**养殖公司*/
	private Company company;
	/**账单日期*/
	private String registDate;
	/**销售收入*/
	private String saleIncome;
	/**饲料费用*/
	private String feedCost;
	/**药品费用*/
	private String drugsCost;
	/**猪苗费用*/
	private String pigletCost;
	/**其他物料费用*/
	private String otherCost;
	/** 采购运费 */
	private String freight;
	/** 销售运费 */
	private String salefreight;
	/** 代养费用 */
	private String farmerCost;
	/** 总成本 */
	private String allCost;
	/** 头均成本 */
	private String avgCost;
	/** 毛利 */
	private String profit;
	/** 头均利润 */
	private String avgProfit;
	
	
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
	public String getSaleIncome() {
		return saleIncome;
	}
	public void setSaleIncome(String saleIncome) {
		this.saleIncome = saleIncome;
	}
	public String getFeedCost() {
		return feedCost;
	}
	public void setFeedCost(String feedCost) {
		this.feedCost = feedCost;
	}
	public String getDrugsCost() {
		return drugsCost;
	}
	public void setDrugsCost(String drugsCost) {
		this.drugsCost = drugsCost;
	}
	public String getPigletCost() {
		return pigletCost;
	}
	public void setPigletCost(String pigletCost) {
		this.pigletCost = pigletCost;
	}
	public String getOtherCost() {
		return otherCost;
	}
	public void setOtherCost(String otherCost) {
		this.otherCost = otherCost;
	}
	
	
	public String getFarmerCost() {
		return farmerCost;
	}
	public void setFarmerCost(String farmerCost) {
		this.farmerCost = farmerCost;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getAvgProfit() {
		return avgProfit;
	}
	public void setAvgProfit(String avgProfit) {
		this.avgProfit = avgProfit;
	}
	public String getAvgCost() {
		return avgCost;
	}
	public void setAvgCost(String avgCost) {
		this.avgCost = avgCost;
	}
	public String getAllCost() {
		return allCost;
	}
	public void setAllCost(String allCost) {
		this.allCost = allCost;
	}
	public String getSalefreight() {
		return salefreight;
	}
	public void setSalefreight(String salefreight) {
		this.salefreight = salefreight;
	}
}
