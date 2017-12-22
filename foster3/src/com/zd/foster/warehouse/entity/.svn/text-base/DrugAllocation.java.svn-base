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
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.entity.FeedFac;

/**
 * 药品调拨单
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-14 下午04:27:19
 */
@BussEle(name="药品调拨单")
@Entity
@Table(name="FS_DRUGALLOCATION")
public class DrugAllocation extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	/**时间*/
	private String registDate;
	/**客户*/
	private CustVender custv;
	/**调拨类型*/
	private String allotType;
	
	/**审核人*/
	private String checkUser;
	/**审核时间*/
	private String checkDate;
	/**审核状态 0：未审核；1：已审核*/
	private String checkStatus;
	/**养殖公司*/
	private Company company;
	/**备注*/
	private String remark;
	/**药品调拨单明细*/
	private List<DrugAllocationDtl> details;
	/**饲料厂*/
	private FeedFac feedFac;
	
	
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
	
	@ManyToOne
	@JoinColumn(name="CUSTV")
	public CustVender getCustv() {
		return custv;
	}
	public void setCustv(CustVender custv) {
		this.custv = custv;
	}
	
	public String getAllotType() {
		return allotType;
	}
	public void setAllotType(String allotType) {
		this.allotType = allotType;
	}
	
	@Transient
	public List<DrugAllocationDtl> getDetails() {
		return details;
	}
	public void setDetails(List<DrugAllocationDtl> details) {
		this.details = details;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="feedFac")
	public FeedFac getFeedFac() {
		return feedFac;
	}
	public void setFeedFac(FeedFac feedFac) {
		this.feedFac = feedFac;
	}
	
	

	
}
