package com.zd.foster.balance.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.base.BaseEntity;
import com.zd.foster.breed.entity.Batch;

/**
 * 公司账单运费明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 下午03:18:52
 */
@Entity
@Table(name="FS_COMPANYFREIGHT")
public class CompanyFreight extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/** 运费类型  1.猪苗运费 2.饲料运费 3.销售运费*/
	private String freightType;
	/**账单*/
	private CompanyBill companyBill;
	/**单据号*/
	private String billId;
	/**单据日期*/
	private String registDate;
	/**饲料重量*/
	private String feedWeight;
	/**饲料包装*/
	private String feedPack;
	/**单件*/
	private String price;
	/**金额*/
	private String money;
	/** 批次 */
	private Batch batch;
	
	@Id
	@SequenceGenerator(name = "companyFreightId", sequenceName = "FS_COMPANYBALANCE_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "companyFreightId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	@ManyToOne
	@JoinColumn(name = "companyBill")
	public CompanyBill getCompanyBill() {
		return companyBill;
	}
	public void setCompanyBill(CompanyBill companyBill) {
		this.companyBill = companyBill;
	}
	
	@ManyToOne
	@JoinColumn(name = "batch")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public String getFreightType() {
		return freightType;
	}
	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getFeedWeight() {
		return feedWeight;
	}
	public void setFeedWeight(String feedWeight) {
		this.feedWeight = feedWeight;
	}
	public String getFeedPack() {
		return feedPack;
	}
	public void setFeedPack(String feedPack) {
		this.feedPack = feedPack;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
}
