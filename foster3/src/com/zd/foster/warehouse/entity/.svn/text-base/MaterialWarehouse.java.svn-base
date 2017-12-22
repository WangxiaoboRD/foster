/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:07:05
 * @file:MaterialWarehouse.java
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

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.material.entity.Material;

/**
 * 类名：  MaterialWarehouse
 * 功能：其他物料库存
 * @author wxb
 * @date 2017-7-26下午03:07:05
 * @version 1.0
 * 
 */
@BussEle(name="其他物料库存")
@Entity
@Table(name="FS_DRUGWAREHOUSE")
public class MaterialWarehouse extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**是否药品 0：药品；1：其他物料*/
	private String isDrug;
	/**其他物料*/
	private Material material;
	/**数量*/
	private String quantity;
	/**养殖公司*/
	private Company company;
	/**副单位数量*/
	private String subQuantity;
	/**代养户*/
	private String farmer;
	
	@Id
	@SequenceGenerator(name="materialWarehouseId",sequenceName="FS_DRUGWAREHOUSE_SEQ",allocationSize=1)
	@GeneratedValue(generator="materialWarehouseId",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIsDrug() {
		return isDrug;
	}
	public void setIsDrug(String isDrug) {
		this.isDrug = isDrug;
	}
	@ManyToOne
	@JoinColumn(name="DRUG")
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
	@ManyToOne
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getSubQuantity() {
		return subQuantity;
	}
	public void setSubQuantity(String subQuantity) {
		this.subQuantity = subQuantity;
	}
	@Transient
	public String getFarmer() {
		return farmer;
	}
	public void setFarmer(String farmer) {
		this.farmer = farmer;
	}
	
	

}
