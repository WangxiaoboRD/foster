/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26上午11:32:24
 * @file:FeedInWareDtl.java
 */
package com.zd.foster.warehouse.entity;

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
 * 
 * 类名：  FeedTransferDtl
 * 功能：  饲料转接单明细
 * @author wxb
 * @date 2017-9-5下午03:24:34
 * @version 1.0
 *
 */
@Entity
@Table(name="FS_FEEDTRANSFERDTL")
public class FeedTransferDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**表头id*/
	private FeedTransfer feedTransfer;
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
	@SequenceGenerator(name="FeedTransferDtlId",sequenceName="FS_FEEDTRANSFERDTL_SEQ",allocationSize=1)
	@GeneratedValue(generator="FeedTransferDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="FEEDTRANSFER")
	public FeedTransfer getFeedTransfer() {
		return feedTransfer;
	}
	public void setFeedTransfer(FeedTransfer feedTransfer) {
		this.feedTransfer = feedTransfer;
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
