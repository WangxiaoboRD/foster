package com.zd.foster.price.entity;

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
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;


/**
 * 运费定价单明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 下午03:49:05
 */
@Entity
@Table(name="FS_FREIGHTDTL")
public class FreightDtl extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 编码 */
	private Integer id;
	/**定价单编号 */
	private Freight freight;
	/** 代养户 */
	private Farmer farmer;
	/** 袋装价格 */
	private String packagePrice;
	/** 散装价格 */
	private String bulkPrice;
	/** 局饲料厂距离 */
	private String tipscontent;
	/** 饲料厂 */
	private FeedFac feedFac;
	/** 原始袋装价格 */
	private String oldPackagePrice;
	/** 原始散装价格 */
	private String oldBulkPrice;
	
	
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
	@JoinColumn(name = "freight")
	public Freight getFreight() {
		return freight;
	}
	public void setFreight(Freight freight) {
		this.freight = freight;
	}
	
	@ManyToOne
	@JoinColumn(name = "farmer")
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	public String getPackagePrice() {
		return packagePrice;
	}
	public void setPackagePrice(String packagePrice) {
		this.packagePrice = packagePrice;
	}
	public String getBulkPrice() {
		return bulkPrice;
	}
	public void setBulkPrice(String bulkPrice) {
		this.bulkPrice = bulkPrice;
	}
	public String getTipscontent() {
		return tipscontent;
	}
	public void setTipscontent(String tipscontent) {
		this.tipscontent = tipscontent;
	}
	
	@ManyToOne
	@JoinColumn(name = "feedFac")
	public FeedFac getFeedFac() {
		return feedFac;
	}
	public void setFeedFac(FeedFac feedFac) {
		this.feedFac = feedFac;
	}
	
	@Transient
	public String getOldPackagePrice() {
		return oldPackagePrice;
	}
	public void setOldPackagePrice(String oldPackagePrice) {
		this.oldPackagePrice = oldPackagePrice;
	}
	
	@Transient
	public String getOldBulkPrice() {
		return oldBulkPrice;
	}
	public void setOldBulkPrice(String oldBulkPrice) {
		this.oldBulkPrice = oldBulkPrice;
	}
	
	
	
}
