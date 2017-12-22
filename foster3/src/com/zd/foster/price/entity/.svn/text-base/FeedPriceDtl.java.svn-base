package com.zd.foster.price.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.material.entity.Feed;


/**
 * 饲料定价单明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-27 上午10:48:37
 */
@Entity
@Table(name="FS_FEEDPRICEDTL")
public class FeedPriceDtl extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 编码 */
	private Integer id;
	/**定价单编号 */
	private FeedPrice feedPrice;
	/** 饲料 */
	private Feed feed;
	/** 单价 */
	private String price;
	/** 原始单价 */
	private String oldPrice;
	
	@Id
	@SequenceGenerator(name = "priceDtlId", sequenceName = "FS_PRICEDTL_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "priceDtlId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "feedPrice")
	public FeedPrice getFeedPrice() {
		return feedPrice;
	}
	public void setFeedPrice(FeedPrice feedPrice) {
		this.feedPrice = feedPrice;
	}
	
	@ManyToOne
	@JoinColumn(name = "feed")
	public Feed getFeed() {
		return feed;
	}
	public void setFeed(Feed feed) {
		this.feed = feed;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}
	
	
}
