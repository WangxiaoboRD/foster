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
import com.zd.foster.material.entity.Feed;
import com.zd.foster.price.entity.FeedPrice;
import com.zd.foster.warehouse.entity.FeedInWare;

/**
 * 公司账单饲料明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 下午02:37:28
 */
@Entity
@Table(name="FS_COMPANYFEEDCOST")
public class CompanyFeedCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/** 单据号 */
	private String feedInWare;
	/**账单*/
	private CompanyBill companyBill;
	/**饲料*/
	private Feed feed;
	/**数量*/
	private String quantity;
	/**单件*/
	private String price;
	/**金额*/
	private String money;
	/** 批次 */
	private Batch batch;
	/** 饲料定价单 */
	private FeedPrice feedPrice;
	/** 单据类型*/
	private String billType;
	/** 单据时间*/
	private String registDate;
	
	@Id
	@SequenceGenerator(name = "companyFeedCostId", sequenceName = "FS_COMPANYBALANCE_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "companyFeedCostId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "feed")
	public Feed getFeed() {
		return feed;
	}
	public void setFeed(Feed feed) {
		this.feed = feed;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
	public String getFeedInWare() {
		return feedInWare;
	}
	public void setFeedInWare(String feedInWare) {
		this.feedInWare = feedInWare;
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
	
	@ManyToOne
	@JoinColumn(name = "feedPrice")
	public FeedPrice getFeedPrice() {
		return feedPrice;
	}
	public void setFeedPrice(FeedPrice feedPrice) {
		this.feedPrice = feedPrice;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
}
