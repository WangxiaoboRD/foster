package com.zd.foster.price.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.base.entity.Company;

/**
 * 运费定价单
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 下午03:29:50
 */
@BussEle(name = "运费定价单")
@Entity
@Table(name="FS_FREIGHT")
public class Freight extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 登记日期 */
	private String registDate;
	
	/** 所属养殖公司 */
	private Company company;
	/** 备注 */
	private String remark;
	/** 审核人 */
	private String checkUser;
	/** 审核时间 */
	private String checkDate;
	/** 审核状态 */
	private String checkStatus;
	/**明细 */ 
	private List<FreightDtl> details;
	

	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="company")
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
	public List<FreightDtl> getDetails() {
		return details;
	}
	public void setDetails(List<FreightDtl> details) {
		this.details = details;
	}
	
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
	
	
	
	
	
}
