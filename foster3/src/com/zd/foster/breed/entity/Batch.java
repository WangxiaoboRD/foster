package com.zd.foster.breed.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.DevelopMan;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.contract.entity.Contract;

/**
 * 类名：  Batch
 * 功能：养殖批次
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@BussEle(name = "养殖批次")
@Entity
@Table(name="FS_BATCH")
public class Batch extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**批次号*/
	private String batchNumber;
	/**所属合同*/
	private Contract contract;
	/**当前头数*/
	private String quantity;
	/**死亡头数*/
	private String deathQuan;
	/**非代养户死亡*/
	private String otherDeathQuan;
	/**淘汰数量*/
	private String eliminateQuan;
	/**销售头数*/
	private String saleQuan;
	/**进猪头数*/
	private String pigletQuan;
	/**合同头数*/
	private String contQuan;
	/**代养户*/
	private Farmer farmer;
	/**养殖公司*/
	private Company company;
	/**是否已经结算*/
	private String isBalance;
	/**料肉比*/
	private String fcr;
	/**出栏日期*/
	private String outDate;
	/**进猪时间*/
	private String inDate;
	/**技术员*/
	private Technician technician;
	/**开发人员*/
	private DevelopMan developMan;
	/**所属饲料厂*/
	private FeedFac feedFac; 
	
	//报表数据
	private String area;//区域
	private String currentDeadNum;//当日死亡头数
	private String deadRate;//死淘率
	private String deadReason;//死亡原因
	private String currentFeed;//当月饲料耗用
	private String pigSource;//猪苗来源
	private String farmerName;//代养户名称
	private String registDate;//查询时间点
	private String showColumns;//日报列
	private String currentInFeed;//当月饲料领用
	
	@Id
	@SequenceGenerator(name = "batchId", sequenceName = "FS_BATCH_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "batchId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "contract")
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDeathQuan() {
		return deathQuan;
	}
	public void setDeathQuan(String deathQuan) {
		this.deathQuan = deathQuan;
	}
	public String getOtherDeathQuan() {
		return otherDeathQuan;
	}
	public void setOtherDeathQuan(String otherDeathQuan) {
		this.otherDeathQuan = otherDeathQuan;
	}
	public String getEliminateQuan() {
		return eliminateQuan;
	}
	public void setEliminateQuan(String eliminateQuan) {
		this.eliminateQuan = eliminateQuan;
	}
	public String getSaleQuan() {
		return saleQuan;
	}
	public void setSaleQuan(String saleQuan) {
		this.saleQuan = saleQuan;
	}
	public String getContQuan() {
		return contQuan;
	}
	public void setContQuan(String contQuan) {
		this.contQuan = contQuan;
	}
	public String getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	public String getFcr() {
		return fcr;
	}
	public void setFcr(String fcr) {
		this.fcr = fcr;
	}
	public String getPigletQuan() {
		return pigletQuan;
	}
	public void setPigletQuan(String pigletQuan) {
		this.pigletQuan = pigletQuan;
	}
	@ManyToOne(fetch=FetchType.LAZY)
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
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TECHNICIAN")
	public Technician getTechnician() {
		return technician;
	}
	public void setTechnician(Technician technician) {
		this.technician = technician;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="developMan")
	public DevelopMan getDevelopMan() {
		return developMan;
	}
	public void setDevelopMan(DevelopMan developMan) {
		this.developMan = developMan;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="feedFac")
	public FeedFac getFeedFac() {
		return feedFac;
	}
	public void setFeedFac(FeedFac feedFac) {
		this.feedFac = feedFac;
	}
	@Transient
	public String getCurrentDeadNum() {
		return currentDeadNum;
	}
	
	public void setCurrentDeadNum(String currentDeadNum) {
		this.currentDeadNum = currentDeadNum;
	}
	@Transient
	public String getDeadRate() {
		return deadRate;
	}
	public void setDeadRate(String deadRate) {
		this.deadRate = deadRate;
	}
	@Transient
	public String getDeadReason() {
		return deadReason;
	}
	public void setDeadReason(String deadReason) {
		this.deadReason = deadReason;
	}
	@Transient
	public String getCurrentFeed() {
		return currentFeed;
	}
	public void setCurrentFeed(String currentFeed) {
		this.currentFeed = currentFeed;
	}
	@Transient
	public String getPigSource() {
		return pigSource;
	}
	public void setPigSource(String pigSource) {
		this.pigSource = pigSource;
	}
	@Transient
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Transient
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	@Transient
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
	@Transient
	public String getShowColumns() {
		return showColumns;
	}
	public void setShowColumns(String showColumns) {
		this.showColumns = showColumns;
	}
	
	@Transient
	public String getCurrentInFeed() {
		return currentInFeed;
	}
	public void setCurrentInFeed(String currentInFeed) {
		this.currentInFeed = currentInFeed;
	}
	
}
