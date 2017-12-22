package com.zd.foster.breed.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.base.BaseEntity;

@Entity
@Table(name="FS_PHONE")
public class MobileEntity extends BaseEntity {
	/*主键*/
	private Integer id;
	/*代养户*/
	private String farmerId;
	/*喂料日期*/
	private String feedDate;
	/*批次ID*/
	private String batchId;
	/*单据类*/
	private String djl;
	/*技术员*/
	private String technician;
	/*饲料*/
	private String feed;
	/*喂料数量*/
	private String feedQuantity;
	
	@Id
	@GeneratedValue(generator="phoneId",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="phoneId",sequenceName="HIBERNATE_SEQUENCE",allocationSize=1)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFarmerId() {
		return farmerId;
	}
	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	public String getFeedDate() {
		return feedDate;
	}
	public void setFeedDate(String feedDate) {
		this.feedDate = feedDate;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getDjl() {
		return djl;
	}
	public void setDjl(String djl) {
		this.djl = djl;
	}
	public String getTechnician() {
		return technician;
	}
	public void setTechnician(String technician) {
		this.technician = technician;
	}
	public String getFeed() {
		return feed;
	}
	public void setFeed(String feed) {
		this.feed = feed;
	}
	public String getFeedQuantity() {
		return feedQuantity;
	}
	public void setFeedQuantity(String feedQuantity) {
		this.feedQuantity = feedQuantity;
	}
	
	
	

}
