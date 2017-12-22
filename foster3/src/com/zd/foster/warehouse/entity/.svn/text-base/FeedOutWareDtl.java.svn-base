/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午02:21:45
 * @file:FeedOutWareDtl.java
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

import com.zd.epa.base.BaseEntity;
import com.zd.foster.material.entity.Feed;

/**
 * 类名：  FeedOutWareDtl
 * 功能：饲料出库单明细
 * @author wxb
 * @date 2017-7-26下午02:21:45
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FEEDOUTWAREDTL")
public class FeedOutWareDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**饲料出库单表头id*/
	private FeedOutWare feedOutWare;
	/**饲料*/
	private Feed feed;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	
	@Id
	@SequenceGenerator(name="feedOutWareDtlId",sequenceName="FS_FEEDOUTWAREDTL_SEQ",allocationSize=1)
	@GeneratedValue(generator="feedOutWareDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="FEEDOUTWARE")
	public FeedOutWare getFeedOutWare() {
		return feedOutWare;
	}
	public void setFeedOutWare(FeedOutWare feedOutWare) {
		this.feedOutWare = feedOutWare;
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
	
	

}
