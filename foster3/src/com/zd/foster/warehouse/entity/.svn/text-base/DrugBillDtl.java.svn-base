/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8上午10:05:57
 * @file:DrugBillDtl.java
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
import javax.persistence.Transient;

import com.zd.epa.base.BaseEntity;
import com.zd.foster.material.entity.Drug;

/**
 * 类名：  DrugBillDtl
 * 功能：  领药单明细
 * @author wxb
 * @date 2017-9-8上午10:05:57
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_DRUGBILLDTL")
public class DrugBillDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	/**领药单表头*/
	private DrugBill drugBill;
	/**药品*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	/**库存数量*/
	private String stockQuantity;
	/**库存副单位数量*/
	private String stockSubQuantity;
	
	@Id
	@GeneratedValue(generator="drugBillDtlId",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="drugBillDtlId",sequenceName="FS_DRUGBILLDTL_SEQUENCE",allocationSize=1)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="DRUGBILL")
	public DrugBill getDrugBill() {
		return drugBill;
	}
	public void setDrugBill(DrugBill drugBill) {
		this.drugBill = drugBill;
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
	public String getSubQuantity() {
		return subQuantity;
	}
	public void setSubQuantity(String subQuantity) {
		this.subQuantity = subQuantity;
	}
	@Transient
	public String getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	@Transient
	public String getStockSubQuantity() {
		return stockSubQuantity;
	}
	public void setStockSubQuantity(String stockSubQuantity) {
		this.stockSubQuantity = stockSubQuantity;
	}
	
	

}
