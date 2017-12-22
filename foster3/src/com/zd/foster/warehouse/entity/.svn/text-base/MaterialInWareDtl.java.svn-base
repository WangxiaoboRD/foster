/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:47:01
 * @file:MaterialInWareDtl.java
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
 * 类名：  MaterialInWareDtl
 * 功能：其他物料入库单明细
 * @author wxb
 * @date 2017-7-26下午04:47:01
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_MATERIALINWAREDTL")
public class MaterialInWareDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**表头id*/
	private MaterialInWare materialInWare;
	/**其他物料*/
	private Material material;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	
	@Id
	@SequenceGenerator(name="materialInWareDtlId",sequenceName="FS_MATERIALINWAREDTL_SEQ",allocationSize=1)
	@GeneratedValue(generator="materialInWareDtlId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="MATERIALINWARE")
	public MaterialInWare getMaterialInWare() {
		return materialInWare;
	}
	public void setMaterialInWare(MaterialInWare materialInWare) {
		this.materialInWare = materialInWare;
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
