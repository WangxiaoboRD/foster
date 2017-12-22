/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午02:42:42
 * @file:DrugWarehouse.java
 */
package com.zd.foster.warehouse.entity;

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
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.material.entity.Drug;

/**
 * 类名：  DrugWarehouse
 * 功能：药品库存
 * @author wxb
 * @date 2017-7-26下午02:42:42
 * @version 1.0
 * 
 */
@BussEle(name="养殖公司药品库存")
@Entity
@Table(name="FS_DRUGWAREHOUSE")
public class DrugWarehouse extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**是否药品 0：药品；1：其他物料*/
	private String isDrug;
	/**药品*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**养殖公司*/
	private Company company;
	/**副单位数量*/
	private String subQuantity;
	/**代养户*/
	private String farmer;
	/** 饲料厂*/
	private FeedFac feedFac;
	
	@Id
	@SequenceGenerator(name="drugWarehouseId",sequenceName="FS_DRUGWAREHOUSE_SEQ",allocationSize=1)
	@GeneratedValue(generator="drugWarehouseId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIsDrug() {
		return isDrug;
	}
	public void setIsDrug(String isDrug) {
		this.isDrug = isDrug;
	}
	@ManyToOne
	@JoinColumn(name="DRUG")
	public Drug getDrug() {
		return drug;
	}
	public void setDrug(Drug drug) {
		this.drug = drug;
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
	@Transient
	public String getFarmer() {
		return farmer;
	}
	public void setFarmer(String farmer) {
		this.farmer = farmer;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="feedFac")
	public FeedFac getFeedFac() {
		return feedFac;
	}
	public void setFeedFac(FeedFac feedFac) {
		this.feedFac = feedFac;
	}
}
