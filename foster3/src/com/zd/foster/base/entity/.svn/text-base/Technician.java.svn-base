package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;

/**
 * 技术员
 * @author 小丁
 *
 */
@BussEle(name="技术员")
@Entity
@Table(name="FS_TECHNICIAN")
public class Technician extends BaseEntity {

	private Integer id;
	/** 技术员编码 */
	private String code;
	/** 名字 */
	private String name;
	/** 电话 */
	private String phone;
	/** 养殖公司 */
	private Company company;
	
	@Id
	@SequenceGenerator(name = "technicianId", sequenceName = "FS_TECHNICIAN_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "technicianId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ManyToOne
	@JoinColumn(name="Company")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
}
