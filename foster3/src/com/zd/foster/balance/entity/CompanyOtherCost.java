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
import com.zd.foster.price.entity.MaterialPrice;

/**
 * 公司账单其他物料明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 下午03:03:48
 */
@Entity
@Table(name="FS_COMPANYOTHERCOST")
public class CompanyOtherCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	
	/**账单*/
	private CompanyBill companyBill;
	/**其他物料*/
	private Material material;
	/**数量*/
	private String quantity;
	/**单件*/
	private String price;
	/**金额*/
	private String money;
	/** 批次 */
	private Batch batch;
	/** 物料定价单 */
	private MaterialPrice materialPrice;
	
	
	
	@Id
	@SequenceGenerator(name = "companyOtherCostId", sequenceName = "FS_COMPANYBALANCE_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "companyOtherCostId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	@JoinColumn(name = "companyBill")
	public CompanyBill getCompanyBill() {
		return companyBill;
	}
	public void setCompanyBill(CompanyBill companyBill) {
		this.companyBill = companyBill;
	}
	
	@ManyToOne
	@JoinColumn(name = "batch")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	
	@ManyToOne
	@JoinColumn(name = "material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@ManyToOne
	@JoinColumn(name = "materialPrice")
	public MaterialPrice getMaterialPrice() {
		return materialPrice;
	}
	public void setMaterialPrice(MaterialPrice materialPrice) {
		this.materialPrice = materialPrice;
	}
	
	
	
	
	
	
	
	
	
	
	
}
