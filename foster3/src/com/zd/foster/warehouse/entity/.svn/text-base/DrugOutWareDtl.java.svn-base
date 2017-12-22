/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:16:13
 * @file:DrugOutWareDtl.java
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
import com.zd.foster.material.entity.Drug;

/**
 * 类名：  DrugOutWareDtl
 * 功能：药品出库单明细
 * @author wxb
 * @date 2017-7-26下午04:16:13
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_DRUGOUTWAREDTL")
public class DrugOutWareDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**饲料出库单表头id*/
	private DrugOutWare drugOutWare;
	/**饲料*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	
	@Id
	@SequenceGenerator(name="drugOutWareDtlId",sequenceName="FS_DRUGOUTWAREDTL_SEQ",allocationSize=1)
	@GeneratedValue(generator="drugOutWareDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="DRUGOUTWARE")
	public DrugOutWare getDrugOutWare() {
		return drugOutWare;
	}
	public void setDrugOutWare(DrugOutWare drugOutWare) {
		this.drugOutWare = drugOutWare;
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
	
	

}
