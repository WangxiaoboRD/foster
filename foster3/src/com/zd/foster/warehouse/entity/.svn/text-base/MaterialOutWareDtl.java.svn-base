/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午05:23:52
 * @file:MaterialOutWareDtl.java
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
import com.zd.foster.material.entity.Material;

/**
 * 类名：  MaterialOutWareDtl
 * 功能：其他物料出库单明细
 * @author wxb
 * @date 2017-7-26下午05:23:52
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_MATERIALOUTWAREDTL")
public class MaterialOutWareDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**药品出库单表头id*/
	private MaterialOutWare materialOutWare;
	/**其他物料*/
	private Material material;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	
	@Id
	@SequenceGenerator(name="materialOutWareDtlId",sequenceName="FS_MATERIALOUTWAREDTL_SEQ",allocationSize=1)
	@GeneratedValue(generator="materialOutWareDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="MATERIALOUTWARE")
	public MaterialOutWare getMaterialOutWare() {
		return materialOutWare;
	}
	public void setMaterialOutWare(MaterialOutWare materialOutWare) {
		this.materialOutWare = materialOutWare;
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

	
	
}
