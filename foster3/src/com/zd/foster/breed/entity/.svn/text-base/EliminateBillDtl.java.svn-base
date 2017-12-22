/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午05:24:52
 * @file:EliminateBillDtl.java
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

import com.zd.epa.base.BaseEntity;

/**
 * 类名：  EliminateBillDtl
 * 功能：淘汰单明细
 * @author wxb
 * @date 2017-8-1下午05:24:52
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_ELIMINATEBILLDTL")
public class EliminateBillDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**淘汰单表头*/
	private EliminateBill eliminateBill;
	/**数量*/
	private String quantity;
	/**原因*/
	private String reason;
	
	@Id
	@SequenceGenerator(name="eliminateBillDtlId",sequenceName="FS_ELIMINATEBILLDTL_SEQUENCE",allocationSize=1)
	@GeneratedValue(generator="eliminateBillDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="ELIMINATEBILL")
	public EliminateBill getEliminateBill() {
		return eliminateBill;
	}
	public void setEliminateBill(EliminateBill eliminateBill) {
		this.eliminateBill = eliminateBill;
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
	

}
