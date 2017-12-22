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
import com.zd.foster.material.entity.Drug;


/**
 * 药品定价单明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-28 下午04:01:35
 */
@Entity
@Table(name="FS_DRUGPRICEDTL")
public class DrugPriceDtl extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 编码 */
	private Integer id;
	/**定价单编号 */
	private DrugPrice drugPrice;
	/** 药品 */
	private Drug drug;
	/** 单价 */
	private String price;
	/** 原始单价 */
	private String oldPrice;
	/** 销售单价 */
	private String salePrice;
	
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
	@JoinColumn(name = "drugPrice")
	public DrugPrice getDrugPrice() {
		return drugPrice;
	}
	public void setDrugPrice(DrugPrice drugPrice) {
		this.drugPrice = drugPrice;
	}
	
	@ManyToOne
	@JoinColumn(name = "drug")
	public Drug getDrug() {
		return drug;
	}
	public void setDrug(Drug drug) {
		this.drug = drug;
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
	
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
}
