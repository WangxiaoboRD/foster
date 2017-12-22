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
import com.zd.foster.price.entity.DrugPrice;

/**
 * 公司账单药品明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 下午02:51:19
 */
@Entity
@Table(name="FS_COMPANYDRUGCOST")
public class CompanyDrugCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	
	/**账单*/
	private CompanyBill companyBill;
	/**药品*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**单件*/
	private String price;
	/**金额*/
	private String money;
	/** 批次 */
	private Batch batch;
	/** 药品定价单 */
	private DrugPrice drugPrice;
	/** 单据号*/
	private String billNum;
	/** 单据日期*/
	private String registDate;
	/** 单据类型 1 入库单 2 转接单*/
	private String billType;
	
	@Id
	@SequenceGenerator(name = "companyDrugCostId", sequenceName = "FS_COMPANYBALANCE_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "companyDrugCostId", strategy = GenerationType.SEQUENCE)
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
	@JoinColumn(name = "drug")
	public Drug getDrug() {
		return drug;
	}
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	
	@ManyToOne
	@JoinColumn(name = "drugPrice")
	public DrugPrice getDrugPrice() {
		return drugPrice;
	}
	public void setDrugPrice(DrugPrice drugPrice) {
		this.drugPrice = drugPrice;
	}
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	
}
