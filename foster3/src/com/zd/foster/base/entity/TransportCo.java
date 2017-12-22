
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
 * 类名：  TransportCo
 * 功能：运输公司基础表
 * @author wxb
 * @date 2017-7-19下午03:41:03
 * @version 1.0
 * 
 */
@BussEle(name="运输公司")
@Entity
@Table(name="FS_TRANSPORTCO")
public class TransportCo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**运输公司id*/
	private Integer id;
	/**运输公司编码*/
	private String code;
	/**运输公司名称*/
	private String name;
	/**养殖公司*/
	private Company company;
	
	@Id
	@SequenceGenerator(name = "transId", sequenceName = "FS_DRIVER_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "transId", strategy = GenerationType.SEQUENCE)
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
	
	@ManyToOne
	@JoinColumn(name="Company")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	

}
