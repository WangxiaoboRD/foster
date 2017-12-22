package com.zd.foster.material.entity;

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
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.foster.base.entity.Company;

/**
 * 饲料类型
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-21 上午11:36:11
 */
@BussEle(name = "饲料类型")
@Entity
@Table(name="FS_FEEDTYPE")
public class FeedType extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/** 名称 */
	private String name;
	/** 编码 */
	private String code;
	/** 耗料标准 */
	private String feedStand;
	/** 所属法人公司 */
	private Company company;
	/** 物料总类 */
	private BussinessEleDetail materialType;
	
	
	@Id
	@SequenceGenerator(name = "feedTypeId", sequenceName = "FS_DRIVER_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "feedTypeId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@ManyToOne
	@JoinColumn(name="company")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@ManyToOne
	@JoinColumn(name="MATERIALTYPE")
	public BussinessEleDetail getMaterialType() {
		return materialType;
	}
	public void setMaterialType(BussinessEleDetail materialType) {
		this.materialType = materialType;
	}
	public String getFeedStand() {
		return feedStand;
	}
	public void setFeedStand(String feedStand) {
		this.feedStand = feedStand;
	}
	
	
}
