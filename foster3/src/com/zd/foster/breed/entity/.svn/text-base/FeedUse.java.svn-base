/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午08:02:58
 * @file:DeathBill.java
 */
package com.zd.foster.breed.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
/**
 * 类名：  FeedUse
 * 功能：饲料耗用矩阵
 * @author DZL
 * @date 2017-8-1下午08:02:58
 * @version 1.0
 * 
 */
@BussEle(name="饲料录入")
@Entity
@Table(name="FS_FEEDUSE")
public class FeedUse extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**时间*/
	private String registDate;
	/**养殖公司*/
	private Company company;
	/**审核人*/
	private String checkUser;
	/**审核时间*/
	private String checkDate;
	/**审核状态 0：未审核；1：已审核*/
	private String checkStatus;
	/**明细*/
	private List<FeedUseDetail> details;
	
	@Id
	@SequenceGenerator(name = "feedUseID", sequenceName = "FS_FEEDUSE_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "feedUseID", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
	@ManyToOne
	@JoinColumn(name="COMPANY")
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
	@Transient
	public List<FeedUseDetail> getDetails() {
		return details;
	}
	public void setDetails(List<FeedUseDetail> details) {
		this.details = details;
	}
}
