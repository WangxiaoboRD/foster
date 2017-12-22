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
 * 系统配置
 * @author 小丁
 * 17-07-19
 */
@BussEle(name="系统配置")
@Entity
@Table(name="FS_SYSCONFIG")
public class SysConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Integer id;
	/**养殖公司 */
	private Company company;
	/** 药品价格系数 */
	private String coefficient;
	
	@Id
	@SequenceGenerator(name = "sysconfigId", sequenceName = "FS_TECHNICIAN_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "sysconfigId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="Company")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}
}
