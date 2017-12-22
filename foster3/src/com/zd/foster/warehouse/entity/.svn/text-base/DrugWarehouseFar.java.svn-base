/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午02:49:52
 * @file:DrugWarehouseFar.java
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

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.material.entity.Drug;

/**
 * 类名：  DrugWarehouseFar
 * 功能：  代养户药品库存
 * @author wxb
 * @date 2017-9-8下午02:49:52
 * @version 1.0
 * 
 */
@BussEle(name="代养户药品库存")
@Entity
@Table(name="FS_DRUGWAREHOUSEFAR")
public class DrugWarehouseFar extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	/**是否药品 0：药品；1：其他物料*/
	private String isDrug;
	/**药品*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**副单位数量*/
	private String subQuantity;
	/**养殖公司*/
	private Company company;
	/**代养户*/
	private Farmer farmer;
	/**批次*/
	private Batch batch;
	
	@Id
	@SequenceGenerator(name="drugWarehouseFarId",sequenceName="FS_DRUGWAREHOUSEFAR_SEQ",allocationSize=1)
	@GeneratedValue(generator="drugWarehouseFarId",strategy=GenerationType.SEQUENCE)
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
	@ManyToOne
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@ManyToOne
	@JoinColumn(name="FARMER")
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	@ManyToOne
	@JoinColumn(name="BATCH")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	

}
