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
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  FarmerSaleIncome
 * 功能：代养户销售收入账单明细
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FARMERSALEINCOME")
public class FarmerSaleIncome extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**账单*/
	private FarmerBill farmerBill;
	/**销售级别*/
	private BussinessEleDetail pigLevel;
	/**头数*/
	private String quantity;
	/**重量*/
	private String weight;
	/**单件*/
	private String price;
	/**金额*/
	private String money;
	/** 批次*/
	private Batch batch;
	
	@Id
	@SequenceGenerator(name = "farmerSaleIncomeId", sequenceName = "FS_BALANCE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "farmerSaleIncomeId", strategy = GenerationType.SEQUENCE)
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
	@JoinColumn(name = "pigLevel")
	public BussinessEleDetail getPigLevel() {
		return pigLevel;
	}
	public void setPigLevel(BussinessEleDetail pigLevel) {
		this.pigLevel = pigLevel;
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
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	@ManyToOne
	@JoinColumn(name = "batch")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
}
