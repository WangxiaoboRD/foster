package com.zd.foster.contract.entity;

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
 * 类名：  Contract
 * 功能：代养合同
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_CONTACTPIGPRICEDTL")
public class ContractPigPriced extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**合同编码*/
	private Contract contract;
	/**销售级别*/
	private BussinessEleDetail pigLevel;
	/**定价*/
	private String price;
	
	@Id
	@SequenceGenerator(name = "contractPigId", sequenceName = "FS_CONTRACTPIG_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "contractPigId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "contract")
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	@ManyToOne
	@JoinColumn(name = "pigLevel")
	public BussinessEleDetail getPigLevel() {
		return pigLevel;
	}
	public void setPigLevel(BussinessEleDetail pigLevel) {
		this.pigLevel = pigLevel;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
