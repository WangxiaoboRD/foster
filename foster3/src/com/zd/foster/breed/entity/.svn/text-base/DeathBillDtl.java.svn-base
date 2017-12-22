/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午08:13:04
 * @file:DeathBillDtl.java
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
import com.zd.foster.base.entity.DeathReason;

/**
 * 类名：  DeathBillDtl
 * 功能：死亡单明细
 * @author wxb
 * @date 2017-8-1下午08:13:04
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_DEATHBILLDTL")
public class DeathBillDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**死亡单表头*/
	private DeathBill deathBill;
	/**数量*/
	private String quantity;
	/**重量*/
	private String weight;
	/**死亡原因*/
	private DeathReason reason;
	/**照片*/
	private String picture;
	/**死亡序号*/
	private String num;
	
	@Id
	@SequenceGenerator(name="deathBillDtlId",sequenceName="FS_DEATHBILLDTL_SEQUENCE",allocationSize=1)
	@GeneratedValue(generator="deathBillDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="DEATHBILL")
	public DeathBill getDeathBill() {
		return deathBill;
	}
	public void setDeathBill(DeathBill deathBill) {
		this.deathBill = deathBill;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	@ManyToOne
	@JoinColumn(name="REASON")
	public DeathReason getReason() {
		return reason;
	}
	public void setReason(DeathReason reason) {
		this.reason = reason;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}
