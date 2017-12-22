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
import com.zd.foster.breed.entity.PigPurchase;

/**
 * 公司账单猪苗明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 下午03:53:48
 */
@Entity
@Table(name="FS_COMPANYPIGLETCOST")
public class CompanyPigletCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**账单*/
	private CompanyBill companyBill;
	/**猪苗采购单*/
	private PigPurchase pigPurchase;
	/**数量*/
	private String quantity;
	/**金额*/
	private String money;
	
	private String price;
	/** 批次 */
	private Batch batch;
	
	@Id
	@SequenceGenerator(name = "companyPigletCostId", sequenceName = "FS_COMPANYBALANCE_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "companyPigletCostId", strategy = GenerationType.SEQUENCE)
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
	@JoinColumn(name = "pigPurchase")
	public PigPurchase getPigPurchase() {
		return pigPurchase;
	}
	public void setPigPurchase(PigPurchase pigPurchase) {
		this.pigPurchase = pigPurchase;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
