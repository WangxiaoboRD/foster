
package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.base.BaseEntity;
import com.zd.foster.material.entity.FeedType;

/**
 * 生长标准明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-21 上午11:37:11
 */
@Entity
@Table(name="FS_GROWSTANDDTL")
public class GrowStandDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**表头*/
	private GrowStand growStand;
	/**日龄*/
	private String days;
	/**标准体重*/
	private String standWeight;
	/**耗料量*/
	private String feedWeight;
	/**饲料阶段*/
	private FeedType feedType;
	/**累计耗料量*/
	//private String totalFeedWeight;
	
	
	@Id
	@SequenceGenerator(name = "pigStandId", sequenceName = "FS_DRIVER_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "pigStandId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="growStand")
	public GrowStand getGrowStand() {
		return growStand;
	}
	public void setGrowStand(GrowStand growStand) {
		this.growStand = growStand;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getStandWeight() {
		return standWeight;
	}
	public void setStandWeight(String standWeight) {
		this.standWeight = standWeight;
	}
	public String getFeedWeight() {
		return feedWeight;
	}
	public void setFeedWeight(String feedWeight) {
		this.feedWeight = feedWeight;
	}
	
	@ManyToOne
	@JoinColumn(name="FEEDTYPE")
	public FeedType getFeedType() {
		return feedType;
	}
	public void setFeedType(FeedType feedType) {
		this.feedType = feedType;
	}
//	public String getTotalFeedWeight() {
//		return totalFeedWeight;
//	}
//	public void setTotalFeedWeight(String totalFeedWeight) {
//		this.totalFeedWeight = totalFeedWeight;
//	}
	
}
