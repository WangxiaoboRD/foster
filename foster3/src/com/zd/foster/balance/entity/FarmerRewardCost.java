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
 * 类名：  FarmerRewardCost
 * 功能：代养户奖罚账单明细
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FARMERREWARDCOST")
public class FarmerRewardCost extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**账单*/
	private FarmerBill farmerBill;
	/**奖罚类型*/
	private BussinessEleDetail reward;
	/**标准重量*/
	private String standWeight;
	/**平均重量*/
	private String avgWeight;
	/**平均净重*/
	private String netgain;
	/**数量*/
	private String quantity;
	/**单件*/
	private String price;
	/**金额*/
	private String money;
	/** 批次*/
	private Batch batch;
	/** 允许偏移量*/
	private String standFcr;
	
	@Id
	@SequenceGenerator(name = "farmerRewardCostId", sequenceName = "FS_BALANCE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "farmerRewardCostId", strategy = GenerationType.SEQUENCE)
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
	@JoinColumn(name = "reward")
	public BussinessEleDetail getReward() {
		return reward;
	}
	public void setReward(BussinessEleDetail reward) {
		this.reward = reward;
	}
	public String getStandWeight() {
		return standWeight;
	}
	public void setStandWeight(String standWeight) {
		this.standWeight = standWeight;
	}
	public String getAvgWeight() {
		return avgWeight;
	}
	public void setAvgWeight(String avgWeight) {
		this.avgWeight = avgWeight;
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
	public String getNetgain() {
		return netgain;
	}
	public void setNetgain(String netgain) {
		this.netgain = netgain;
	}
	public String getStandFcr() {
		return standFcr;
	}
	public void setStandFcr(String standFcr) {
		this.standFcr = standFcr;
	}
}
