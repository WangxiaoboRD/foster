/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:25:10
 * @file:DrugInWare.java
 */
package com.zd.foster.warehouse.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.FeedFac;

/**
 * 类名：  DrugInWare
 * 功能：药品入库单表头
 * @author wxb
 * @date 2017-7-26下午03:25:10
 * @version 1.0
 * 
 */
@BussEle(name="药品入库单")
@Entity
@Table(name="FS_DRUGINWARE")
public class DrugInWare extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**药品入库时间*/
	private String registDate;
	/**审核人*/
	private String checkUser;
	/**审核时间*/
	private String checkDate;
	/**审核状态 0：未审核；1：已审核*/
	private String checkStatus;
	/**养殖公司*/
	private Company company;
	/**饲料厂*/
	private FeedFac feedFac;
	/**备注*/
	private String remark;
	/**药品入库单明细*/
	private List<DrugInWareDtl> details;
	
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
	@ManyToOne
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="feedFac")
	public FeedFac getFeedFac() {
		return feedFac;
	}
	public void setFeedFac(FeedFac feedFac) {
		this.feedFac = feedFac;
	}
	
	@Transient
	public List<DrugInWareDtl> getDetails() {
		return details;
	}
	public void setDetails(List<DrugInWareDtl> details) {
		this.details = details;
	}
}
