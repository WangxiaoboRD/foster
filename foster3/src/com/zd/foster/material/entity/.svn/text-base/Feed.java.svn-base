package com.zd.foster.material.entity;

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

/**
 * 饲料
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-21 上午11:36:11
 */
@BussEle(name = "饲料")
@Entity
@Table(name="FS_FEED")
public class Feed extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/** 饲料ID */
	private String id;
	/** 饲料编码 */
	private String code;
	/** 饲料名称 */
	private String name;
	/** 单位 */
	private BussinessEleDetail unit;
	/** 包装方式 1代表散装，2代表袋装 */
	private int packForm; 
	/** 规格*/
	private String spec;
	/** 饲料类型 */
	private FeedType feedType;
	/** 所属法人公司 */
	private Company company;
	/** 副单位*/
	private BussinessEleDetail subUnit;
	/** 换算比值（副单位/主单位）*/
	private String ratio;

	/** 最近一次单价  透明*/
	private String lastPrice;
	
	/** 饲料大类 */
	private BussinessEleDetail feedClass;
	
	public Feed (String id){
		this.id=id;
	}
	public Feed (){}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name = "unit")
	public BussinessEleDetail getUnit() {
		return unit;
	}
	public void setUnit(BussinessEleDetail unit) {
		this.unit = unit;
	}
	public int getPackForm() {
		return packForm;
	}
	public void setPackForm(int packForm) {
		this.packForm = packForm;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	@ManyToOne
	@JoinColumn(name="feedType")
	public FeedType getFeedType() {
		return feedType;
	}
	public void setFeedType(FeedType feedType) {
		this.feedType = feedType;
	}
	@ManyToOne
	@JoinColumn(name="company")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@ManyToOne
	@JoinColumn(name="subUnit")
	public BussinessEleDetail getSubUnit() {
		return subUnit;
	}
	public void setSubUnit(BussinessEleDetail subUnit) {
		this.subUnit = subUnit;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	
	@Transient
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	
	@ManyToOne
	@JoinColumn(name = "feedClass")
	public BussinessEleDetail getFeedClass() {
		return feedClass;
	}
	public void setFeedClass(BussinessEleDetail feedClass) {
		this.feedClass = feedClass;
	}
}
