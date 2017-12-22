/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:36:14
 * @file:DrugInWareDtl.java
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
 * 类名：  DrugInWareDtl
 * 功能：药品入库单明细
 * @author wxb
 * @date 2017-7-26下午03:36:14
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_DRUGINWAREDTL")
public class DrugInWareDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**表头id*/
	private DrugInWare drugInWare;
	/**药品*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	
	@Id
	@SequenceGenerator(name="drugInWareDtlId",sequenceName="FS_DRUGINWAREDTL_SEQ",allocationSize=1)
	@GeneratedValue(generator="drugInWareDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="DRUGINWARE")
	public DrugInWare getDrugInWare() {
		return drugInWare;
	}
	public void setDrugInWare(DrugInWare drugInWare) {
		this.drugInWare = drugInWare;
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
