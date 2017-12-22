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
 * 司机
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-20 下午03:42:49
 */
@BussEle(name="司机")
@Entity
@Table(name="FS_DRIVER")
public class Driver extends BaseEntity {

	private static final long serialVersionUID = -6991724550071716122L;
	private Integer id;
	/** 技术员编码 */
	private String code;
	/** 名字 */
	private String name;
	/** 电话 */
	private String phone;
	/** 运输公司 */
	private TransportCo transportCo;
	/** 养殖公司 */
	private Company company;
	
	@Id
	@SequenceGenerator(name = "driverId", sequenceName = "FS_DRIVER_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "driverId", strategy = GenerationType.SEQUENCE)
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
	
	@ManyToOne
	@JoinColumn(name="transportCo")
	public TransportCo getTransportCo() {
		return transportCo;
	}
	public void setTransportCo(TransportCo transportCo) {
		this.transportCo = transportCo;
	}
	
	
	
}
