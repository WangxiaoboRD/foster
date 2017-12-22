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

/**
 * 类名：  FarmerFeedCost
 * 功能：代养户饲料账单明细
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FARMERFEEDCOST")
public class FarmerFeedCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**账单*/
	private FarmerBill farmerBill;
	/**饲料*/
	private Feed feed;
	/**数量*/
	private String quantity;
	/**单件*/
	private String price;
	/**金额*/
	private String money;
	/** 批次*/
	private Batch batch;
	
	@Id
	@SequenceGenerator(name = "farmerFeedCostId", sequenceName = "FS_BALANCE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "farmerFeedCostId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "farmerBill")
	public FarmerBill getFarmerBill() {
		return farmerBill;
	}
	public void setFarmerBill(FarmerBill farmerBill) {
		this.farmerBill = farmerBill;
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
	@ManyToOne
	@JoinColumn(name = "batch")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
}
