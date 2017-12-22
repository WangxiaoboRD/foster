/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26上午10:11:58
 * @file:FeedWarehouse.java
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

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.material.entity.Feed;

/**
 * 类名：  FeedWarehouse
 * 功能：饲料库存
 * @author wxb
 * @date 2017-7-26上午10:11:58
 * @version 1.0
 * 
 */
@BussEle(name="饲料库存")
@Entity
@Table(name="FS_FEEDWAREHOUSE")
public class FeedWarehouse extends BaseEntity implements Comparable<FeedWarehouse> {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**代养户*/
	private Farmer farmer;
	/**饲料*/
	private Feed feed;
	/**数量*/
	private String quantity;
	/**养殖公司*/
	private Company company;
	/**副单位数量*/
	private String subQuantity;
	/**批次*/
	private Batch batch;
	
	@Id
	@SequenceGenerator(name="feedWarehoseId",sequenceName="FS_FEEDWAREHOUSE_SEQ",allocationSize=1)
	@GeneratedValue(generator="feedWarehoseId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="FARMER")
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
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
	@ManyToOne
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getSubQuantity() {
		return subQuantity;
	}
	public void setSubQuantity(String subQuantity) {
		this.subQuantity = subQuantity;
	}
	@ManyToOne
	@JoinColumn(name="BATCH")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	@Override
	public int compareTo(FeedWarehouse o) {
		if(this==o)
			return 0;
		else if(!this.getCompany().getId().equals(o.getCompany().getId())){
			return this.getCompany().getId().hashCode()-o.getCompany().getId().hashCode();
		}else if(!this.getFarmer().getId().equals(o.getFarmer().getId())){
			return this.getFarmer().getId().hashCode()-o.getFarmer().getId().hashCode();
		}else if(!this.getBatch().getId().equals(o.getBatch().getId())){
			return this.getBatch().getId().hashCode()-o.getBatch().getId().hashCode();
		}else 
			return this.getFeed().getFeedType().getId()-o.getFeed().getFeedType().getId();
	}
	
	

}
