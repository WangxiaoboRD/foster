package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  TechBatch
 * 功能：技术员批次列表
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@BussEle(name = "技术员批次列表")
@Entity
@Table(name="FS_TECHBATCH")
public class TechBatch extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**批次号*/
	private Batch batch;
	/**代养户*/
	private Farmer farmer;
	/**养殖公司*/
	private Company company;
	/**起始日期*/
	private String startDate;
	/**结束时间*/
	private String endDate;
	/**技术员*/
	private Technician technician;
	
	
	@Id
	@SequenceGenerator(name = "tbatchId", sequenceName = "FS_TECHNICIAN_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "tbatchId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TECHNICIAN")
	public Technician getTechnician() {
		return technician;
	}
	public void setTechnician(Technician technician) {
		this.technician = technician;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BATCH")
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
