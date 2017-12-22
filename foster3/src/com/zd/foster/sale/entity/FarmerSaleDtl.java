package com.zd.foster.sale.entity;

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

/**
 * 类名：  FarmerSaleDtl
 * 功能：销售明细
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_FARMERSALEBILLDTL")
public class FarmerSaleDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**销售表头*/
	private FarmerSale farmerSale;
	/**销售级别*/
	private BussinessEleDetail pigLevel;
	/**合同定价*/
	private String contPrice;
	/**交易价*/
	private String actualPrice;
	/**头数*/
	private String quantity;
	/**重量*/
	private String weight;
	/**金额*/
	private String amount;
	
	@Id
	@SequenceGenerator(name = "farmerSaleDtlId", sequenceName = "FS_SALE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "farmerSaleDtlId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "farmerSale")
	public FarmerSale getFarmerSale() {
		return farmerSale;
	}
	public void setFarmerSale(FarmerSale farmerSale) {
		this.farmerSale = farmerSale;
	}
	@ManyToOne
	@JoinColumn(name = "pigLevel")
	public BussinessEleDetail getPigLevel() {
		return pigLevel;
	}
	public void setPigLevel(BussinessEleDetail pigLevel) {
		this.pigLevel = pigLevel;
	}
	public String getContPrice() {
		return contPrice;
	}
	public void setContPrice(String contPrice) {
		this.contPrice = contPrice;
	}
	public String getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
