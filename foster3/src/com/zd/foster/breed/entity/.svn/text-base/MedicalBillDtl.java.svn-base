/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:33:51
 * @file:MedicalBillDtl.java
 */
package com.zd.foster.breed.entity;

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
 * 类名：  MedicalBillDtl
 * 功能：医疗单明细
 * @author wxb
 * @date 2017-7-27上午09:33:51
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_MEDICALBILLDTL")
public class MedicalBillDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**医疗单表头*/
	private MedicalBill medicalBill;
	/**药品*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**原因*/
	private String reason;
	/**副单位数量*/
	private String subQuantity;
	/**库存数量*/
	private String stockQuantity;
	/**库存副单位数量*/
	private String stockSubQuantity;
	
	@Id
	@GeneratedValue(generator="medicalBillDtlId",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="medicalBillDtlId",sequenceName="FS_MEDICALBILLDTL_SEQUENCE",allocationSize=1)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="MEDICALBILL")
	public MedicalBill getMedicalBill() {
		return medicalBill;
	}
	public void setMedicalBill(MedicalBill medicalBill) {
		this.medicalBill = medicalBill;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
