/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:00:56
 * @file:FeedBillDtl.java
 */
package com.zd.foster.breed.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.base.BaseEntity;
import com.zd.foster.material.entity.Feed;

/**
 * 类名：  FeedBillDtl
 * 功能：喂料单明细
 * @author wxb
 * @date 2017-7-27上午09:00:56
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FEEDBILLDTL")
public class FeedBillDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**喂料单表头*/
	private FeedBill feedBill;
	/**饲料*/
	private Feed feed;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	/**库存数量*/
	private String stockQuantity;
	/**库存副单位数量*/
	private String stockSubQuantity;
	
	@Id
	@GeneratedValue(generator="feedBillDtlId",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="feedBillDtlId",sequenceName="FS_FEEDBILLDTL_SEQUENCE",allocationSize=1)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="FEEDBILL")
	public FeedBill getFeedBill() {
		return feedBill;
	}
	public void setFeedBill(FeedBill feedBill) {
		this.feedBill = feedBill;
	}
	@ManyToOne
	@JoinColumn(name="FEED")
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
	public String getSubQuantity() {
		return subQuantity;
	}
	public void setSubQuantity(String subQuantity) {
		this.subQuantity = subQuantity;
	}
	@Transient
	public String getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	@Transient
	public String getStockSubQuantity() {
		return stockSubQuantity;
	}
	public void setStockSubQuantity(String stockSubQuantity) {
		this.stockSubQuantity = stockSubQuantity;
	}
	
	 

}
