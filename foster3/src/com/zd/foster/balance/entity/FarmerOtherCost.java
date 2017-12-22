package com.zd.foster.balance.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.base.BaseEntity;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.material.entity.Material;

/**
 * 类名：  FarmerOtherCost
 * 功能：代养户其他物料账单明细
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FARMEROTHERCOST")
public class FarmerOtherCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**账单*/
	private FarmerBill farmerBill;
	/**饲料*/
	private Material material;
	/**数量*/
	private String quantity;
	/**单件*/
	private String price;
	/**金额*/
	private String money;
	/** 批次*/
	private Batch batch;
	
	private String materialPrice;
	
	@Id
	@SequenceGenerator(name = "farmerOtherCostId", sequenceName = "FS_BALANCE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "farmerOtherCostId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "farmerBill")
	public FarmerBill getFarmerBill() {
		return farmerBill;
	}
	public void setFarmerBill(FarmerBill farmerBill) {
		this.farmerBill = farmerBill;
	}
	@ManyToOne
	@JoinColumn(name = "material")
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@ManyToOne
	@JoinColumn(name = "batch")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public String getMaterialPrice() {
		return materialPrice;
	}
	public void setMaterialPrice(String materialPrice) {
		this.materialPrice = materialPrice;
	}
}
