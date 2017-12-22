/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午03:18:32
 * @file:MaterialBillDtl.java
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
import com.zd.foster.material.entity.Material;

/**
 * 类名：  MaterialBillDtl
 * 功能：
 * @author wxb
 * @date 2017-8-1下午03:18:32
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_MATERIALBILLDTL")
public class MaterialBillDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**领用单表头*/
	private MaterialBill materialBill;
	/**其他物料*/
	private Material material;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	/**库存数量*/
	private String stockQuantity;
	/**库存副单位数量*/
	private String stockSubQuantity;
	
	@Id
	@SequenceGenerator(name="materialBillDtlId",sequenceName="FS_MATERIALBILLDTL_SEQ",allocationSize=1)
	@GeneratedValue(generator="materialBillDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="MATERIALBILL")
	public MaterialBill getMaterialBill() {
		return materialBill;
	}
	public void setMaterialBill(MaterialBill materialBill) {
		this.materialBill = materialBill;
	}
	@ManyToOne
	@JoinColumn(name="MATERIAL")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
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
