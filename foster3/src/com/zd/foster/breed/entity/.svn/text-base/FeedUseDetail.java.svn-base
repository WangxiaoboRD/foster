/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午08:02:58
 * @file:DeathBill.java
 */
package com.zd.foster.breed.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.Technician;

/**
 * 类名：  FeedUse
 * 功能：饲料耗用矩阵明细
 * @author DZL
 * @date 2017-8-1下午08:02:58
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FEEDUSEDetail")
public class FeedUseDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**单据头*/
	private FeedUse feedUse;
	/**批次*/
	private Batch batch;
	/**DRB*/
	private Double drb;
	/**RZB*/
	private Double rzb;
	/**ZZB*/
	private Double zzb;
	/**551*/
	private Double feedA;
	/**552*/
	private Double feedB;
	/**553*/
	private Double feedC;
	/**554*/
	private Double feedD;
	
	@Id
	@SequenceGenerator(name = "feedUseDetailID", sequenceName = "FS_FEEDUSE_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "feedUseDetailID", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="FEEDUSE")
	public FeedUse getFeedUse() {
		return feedUse;
	}
	public void setFeedUse(FeedUse feedUse) {
		this.feedUse = feedUse;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BATCh")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public Double getDrb() {
		return drb;
	}
	public void setDrb(Double drb) {
		this.drb = drb;
	}
	public Double getRzb() {
		return rzb;
	}
	public void setRzb(Double rzb) {
		this.rzb = rzb;
	}
	public Double getZzb() {
		return zzb;
	}
	public void setZzb(Double zzb) {
		this.zzb = zzb;
	}
	public Double getFeedA() {
		return feedA;
	}
	public void setFeedA(Double feedA) {
		this.feedA = feedA;
	}
	public Double getFeedB() {
		return feedB;
	}
	public void setFeedB(Double feedB) {
		this.feedB = feedB;
	}
	public Double getFeedC() {
		return feedC;
	}
	public void setFeedC(Double feedC) {
		this.feedC = feedC;
	}
	public Double getFeedD() {
		return feedD;
	}
	public void setFeedD(Double feedD) {
		this.feedD = feedD;
	}
}
