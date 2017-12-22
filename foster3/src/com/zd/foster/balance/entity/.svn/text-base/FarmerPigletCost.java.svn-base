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
 * 类名：  FarmerPigletCost
 * 功能：代养户采购猪苗账单明细
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FARMERPIGLETCOST")
public class FarmerPigletCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**账单*/
	private FarmerBill farmerBill;
	/**猪苗采购单*/
	private PigPurchase PigPurchase;
	/**数量*/
	private String quantity;
	/**单价*/
	private String price;
	/**金额*/
	private String money;
	/** 批次*/
	private Batch batch;
	/**死亡头数*/
	private String deadHead;
	/**计算头数*/
	private String balanceHead;
	
	@Id
	@SequenceGenerator(name = "farmerPigletCostId", sequenceName = "FS_BALANCE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "farmerPigletCostId", strategy = GenerationType.SEQUENCE)
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
	@JoinColumn(name = "PigPurchase")
	public PigPurchase getPigPurchase() {
		return PigPurchase;
	}
	public void setPigPurchase(PigPurchase pigPurchase) {
		PigPurchase = pigPurchase;
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
	public String getDeadHead() {
		return deadHead;
	}
	public void setDeadHead(String deadHead) {
		this.deadHead = deadHead;
	}
	public String getBalanceHead() {
		return balanceHead;
	}
	public void setBalanceHead(String balanceHead) {
		this.balanceHead = balanceHead;
	}
}
