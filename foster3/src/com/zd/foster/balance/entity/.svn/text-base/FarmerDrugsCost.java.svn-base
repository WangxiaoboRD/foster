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
import com.zd.foster.material.entity.Drug;

/**
 * 类名：  FarmerDrugsCost
 * 功能：代养户药品账单明细
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FARMERDRUGSCOST")
public class FarmerDrugsCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**账单*/
	private FarmerBill farmerBill;
	/**药品*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**单价*/
	private String price;
	/**最终单价*/
	private String salePrice;
	/**金额*/
	private String money;
	/** 批次*/
	private Batch batch;
	/** 药品定价单*/
	private String drugPrice;
	/** 单据编号*/
	private String drugBill;
	/** 单据日期*/
	private String registDate;
	/** 单据类型  1 领药单 2 转接单*/
	private String billType;
	
	@Id
	@SequenceGenerator(name = "farmerDrugsCostId", sequenceName = "FS_BALANCE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "farmerDrugsCostId", strategy = GenerationType.SEQUENCE)
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
	@JoinColumn(name = "drug")
	public Drug getDrug() {
		return drug;
	}
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	@ManyToOne
	@JoinColumn(name = "batch")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
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
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getDrugBill() {
		return drugBill;
	}
	public void setDrugBill(String drugBill) {
		this.drugBill = drugBill;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDrugPrice() {
		return drugPrice;
	}
	public void setDrugPrice(String drugPrice) {
		this.drugPrice = drugPrice;
	}
}
