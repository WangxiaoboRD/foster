package com.zd.foster.contract.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.DevelopMan;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.base.entity.Variety;

/**
 * 类名：  Contract
 * 功能：代养合同
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@BussEle(name = "代养合同")
@Entity
@Table(name="FS_CONTRACT")
public class Contract extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**合同id*/
	private String id;
	/**合同编码*/
	private String code;
	/**签订生效日期*/
	private String registDate;
	/**当前状态*/
	private BussinessEleDetail status;
	/**养殖品种*/
	private Variety variety;
	/**养殖数量*/
	private String pigletQuan;
	/**猪苗单价*/
	private String pigletPrice;
	/**代养户*/
	private Farmer farmer;
	/**养殖公司*/
	private Company company;
	/**饲料厂*/
	private FeedFac feedFac;
	/**终止时间*/
	private String endDate;
	/** 附件上传状态*/
	private String isAnnex;
	/**标准小猪重*/
	private String standPigletWeight;
	/**标准出栏重*/
	private String standSaleWeight;
	/**市场价*/
	private String marketPrice;
	/** 允许偏差值 */
	private String allowDiff;
	/** 一级扣款偏差值 */
	private String firstDiff;
	/** 一级扣款价（元/吨） */
	private String firstPrice;
	/** 超过一级扣款价（元/吨） */
	private String overFirstPrice;
	
	/** 审核人 */
	private String checkUser;
	/** 审核时间 */
	private String checkDate;
	/** 审核状态 */
	private String checkStatus;
	/**饲料价格单*/ 
	private List<ContractFeedPriced> feedPriced;
	/**销售价格单*/ 
	private List<ContractPigPriced> pigPriced;
	/**技术员*/
	private Technician technician;
	/**开发人员*/
	private DevelopMan developMan;
	/**批次号*/
	private String batch;
	/**批次编号*/
	private String batchNumber;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	@ManyToOne
	@JoinColumn(name = "status")
	public BussinessEleDetail getStatus() {
		return status;
	}
	public void setStatus(BussinessEleDetail status) {
		this.status = status;
	}
	@ManyToOne
	@JoinColumn(name = "variety")
	public Variety getVariety() {
		return variety;
	}
	public void setVariety(Variety variety) {
		this.variety = variety;
	}
	public String getPigletQuan() {
		return pigletQuan;
	}
	public void setPigletQuan(String pigletQuan) {
		this.pigletQuan = pigletQuan;
	}
	public String getPigletPrice() {
		return pigletPrice;
	}
	public void setPigletPrice(String pigletPrice) {
		this.pigletPrice = pigletPrice;
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
	
	@ManyToOne
	@JoinColumn(name = "feedFac")
	public FeedFac getFeedFac() {
		return feedFac;
	}
	public void setFeedFac(FeedFac feedFac) {
		this.feedFac = feedFac;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getIsAnnex() {
		return isAnnex;
	}
	public void setIsAnnex(String isAnnex) {
		this.isAnnex = isAnnex;
	}
	public String getStandPigletWeight() {
		return standPigletWeight;
	}
	public void setStandPigletWeight(String standPigletWeight) {
		this.standPigletWeight = standPigletWeight;
	}
	public String getStandSaleWeight() {
		return standSaleWeight;
	}
	public void setStandSaleWeight(String standSaleWeight) {
		this.standSaleWeight = standSaleWeight;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	@Transient
	public List<ContractFeedPriced> getFeedPriced() {
		return feedPriced;
	}
	public void setFeedPriced(List<ContractFeedPriced> feedPriced) {
		this.feedPriced = feedPriced;
	}
	@Transient
	public List<ContractPigPriced> getPigPriced() {
		return pigPriced;
	}
	public void setPigPriced(List<ContractPigPriced> pigPriced) {
		this.pigPriced = pigPriced;
	}
	
	public String getAllowDiff() {
		return allowDiff;
	}
	public void setAllowDiff(String allowDiff) {
		this.allowDiff = allowDiff;
	}
	public String getFirstDiff() {
		return firstDiff;
	}
	public void setFirstDiff(String firstDiff) {
		this.firstDiff = firstDiff;
	}
	public String getFirstPrice() {
		return firstPrice;
	}
	public void setFirstPrice(String firstPrice) {
		this.firstPrice = firstPrice;
	}
	public String getOverFirstPrice() {
		return overFirstPrice;
	}
	public void setOverFirstPrice(String overFirstPrice) {
		this.overFirstPrice = overFirstPrice;
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
	@JoinColumn(name="developMan")
	public DevelopMan getDevelopMan() {
		return developMan;
	}
	public void setDevelopMan(DevelopMan developMan) {
		this.developMan = developMan;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	@Transient
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
}
