package com.zd.foster.balance.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  FarmerBill
 * 功能：代养户账单
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@BussEle(name = "代养户账单")
@Entity
@Table(name="FS_FARMERBILL")
public class FarmerBill extends BaseEntity {
	
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
	/** 奖罚费用 */
	private String rewardCost;
	/** 调整费用 */
	private String adjustCost;
	/** 调整人 */
	private String adjustMen;
	/** 调整原因 */
	private String adjustReason;
	/** 总投入费用 */
	private String allBreedCost;
	/** 代养费用 */
	private String farmerCost;
	/** 料肉比 */
	private String fcr;
	/** 进猪头数 */
	private String inPigNum;
	/** 进猪均重 */
	private String inPigAvgWeight;
	/** 进猪日期 */
	private String inPigDate;
	/** 出栏头数*/
	private String outPigNum;
	/** 出栏均重*/
	private String outPigAvgWeight;
	/** 出栏日期*/
	private String outPigDate;
	/** 出栏总重*/
	private String outPigAllWeight;
	/** 出栏净增重*/
	private String netGin;
	/** 净增均重*/
	private String avgNetGin;
	/** 成活率*/
	private String survivalRate;
	/** 平均生猪代养费*/
	private String avgFarmerCost;
	
	/**明细 */
	private List<FarmerSaleIncome> saleList;
	private List<FarmerFeedCost> feedList;
	private List<FarmerDrugsCost> drugList;
	private List<FarmerOtherCost> otherList;
	private List<FarmerPigletCost> pigletList;
	private List<FarmerRewardCost> rewardList;
	
	//----------打印所需字段
	private String inPigPrice;
	
	@Id
	@SequenceGenerator(name = "farmerBillId", sequenceName = "FS_FARMERBILL_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "farmerBillId", strategy = GenerationType.SEQUENCE)
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
	public String getRewardCost() {
		return rewardCost;
	}
	public void setRewardCost(String rewardCost) {
		this.rewardCost = rewardCost;
	}
	public String getAdjustCost() {
		return adjustCost;
	}
	public void setAdjustCost(String adjustCost) {
		this.adjustCost = adjustCost;
	}
	
	public String getAdjustReason() {
		return adjustReason;
	}
	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}
	public String getAllBreedCost() {
		return allBreedCost;
	}
	public void setAllBreedCost(String allBreedCost) {
		this.allBreedCost = allBreedCost;
	}
	public String getFarmerCost() {
		return farmerCost;
	}
	public void setFarmerCost(String farmerCost) {
		this.farmerCost = farmerCost;
	}
	public String getFcr() {
		return fcr;
	}
	public void setFcr(String fcr) {
		this.fcr = fcr;
	}
	public String getAdjustMen() {
		return adjustMen;
	}
	public void setAdjustMen(String adjustMen) {
		this.adjustMen = adjustMen;
	}
	public void setFeedList(List<FarmerFeedCost> feedList) {
		this.feedList = feedList;
	}
	public String getInPigNum() {
		return inPigNum;
	}
	public void setInPigNum(String inPigNum) {
		this.inPigNum = inPigNum;
	}
	public String getInPigAvgWeight() {
		return inPigAvgWeight;
	}
	public void setInPigAvgWeight(String inPigAvgWeight) {
		this.inPigAvgWeight = inPigAvgWeight;
	}
	public String getInPigDate() {
		return inPigDate;
	}
	public void setInPigDate(String inPigDate) {
		this.inPigDate = inPigDate;
	}
	public String getOutPigNum() {
		return outPigNum;
	}
	public void setOutPigNum(String outPigNum) {
		this.outPigNum = outPigNum;
	}
	public String getOutPigAvgWeight() {
		return outPigAvgWeight;
	}
	public void setOutPigAvgWeight(String outPigAvgWeight) {
		this.outPigAvgWeight = outPigAvgWeight;
	}
	public String getOutPigDate() {
		return outPigDate;
	}
	public void setOutPigDate(String outPigDate) {
		this.outPigDate = outPigDate;
	}
	public String getOutPigAllWeight() {
		return outPigAllWeight;
	}
	public void setOutPigAllWeight(String outPigAllWeight) {
		this.outPigAllWeight = outPigAllWeight;
	}
	public String getNetGin() {
		return netGin;
	}
	public void setNetGin(String netGin) {
		this.netGin = netGin;
	}
	public String getAvgNetGin() {
		return avgNetGin;
	}
	public void setAvgNetGin(String avgNetGin) {
		this.avgNetGin = avgNetGin;
	}
	public String getSurvivalRate() {
		return survivalRate;
	}
	public void setSurvivalRate(String survivalRate) {
		this.survivalRate = survivalRate;
	}
	@Transient
	public List<FarmerSaleIncome> getSaleList() {
		return saleList;
	}
	public void setSaleList(List<FarmerSaleIncome> saleList) {
		this.saleList = saleList;
	}
	@Transient
	public List<FarmerFeedCost> getFeedList() {
		return feedList;
	}
	@Transient
	public List<FarmerDrugsCost> getDrugList() {
		return drugList;
	}
	public void setDrugList(List<FarmerDrugsCost> drugList) {
		this.drugList = drugList;
	}
	@Transient
	public List<FarmerOtherCost> getOtherList() {
		return otherList;
	}
	public void setOtherList(List<FarmerOtherCost> otherList) {
		this.otherList = otherList;
	}
	@Transient
	public List<FarmerPigletCost> getPigletList() {
		return pigletList;
	}
	public void setPigletList(List<FarmerPigletCost> pigletList) {
		this.pigletList = pigletList;
	}
	@Transient
	public List<FarmerRewardCost> getRewardList() {
		return rewardList;
	}
	public void setRewardList(List<FarmerRewardCost> rewardList) {
		this.rewardList = rewardList;
	}
	public String getAvgFarmerCost() {
		return avgFarmerCost;
	}
	public void setAvgFarmerCost(String avgFarmerCost) {
		this.avgFarmerCost = avgFarmerCost;
	}
	
	@Transient
	public String getInPigPrice() {
		return inPigPrice;
	}
	public void setInPigPrice(String inPigPrice) {
		this.inPigPrice = inPigPrice;
	}
}
