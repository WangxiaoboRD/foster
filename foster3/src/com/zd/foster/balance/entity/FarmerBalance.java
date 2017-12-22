package com.zd.foster.balance.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  FarmerBalance
 * 功能：代养户结算单
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@BussEle(name = "代养户结算单")
@Entity
@Table(name="FS_FARMERBALANCE")
public class FarmerBalance extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**id*/
	private String id;
	/**批次*/
	private Batch batch;
	/**代养户*/
	private Farmer farmer;
	/**养殖公司*/
	private Company company;
	/**结算日期*/
	private String registDate;
	/** 结算人 */
	private String checkUser;
	/** 操作日期 */
	private String checkDate;
	/** 结算状态 */
	private String checkStatus;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
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
	@JoinColumn(name = "farmer")
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	@ManyToOne
	@JoinColumn(name = "company")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
}
