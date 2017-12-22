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
import com.zd.foster.sale.entity.CompanySale;

/**
 * 公司账单销售明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-9 上午10:32:03
 */
@Entity
@Table(name="FS_COMPANYSALEINCOME")
public class CompanySaleIncome extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**账单*/
	private CompanyBill companyBill;
	/**销售单*/
	private CompanySale companySale;
	/**单件*/
	private String price;
	/** 批次 */
	private Batch batch;
	
	@Id
	@SequenceGenerator(name = "companySaleIncomeId", sequenceName = "FS_COMPANYBALANCE_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "companySaleIncomeId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	@JoinColumn(name = "companySale")
	public CompanySale getCompanySale() {
		return companySale;
	}
	public void setCompanySale(CompanySale companySale) {
		this.companySale = companySale;
	}
	
	
}
