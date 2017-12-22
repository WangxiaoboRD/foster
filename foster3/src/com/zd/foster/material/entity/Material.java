package com.zd.foster.material.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.foster.base.entity.Company;

/**
 * 其他物料
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-26 下午02:23:55
 */
@BussEle(name = "其他物料")
@Entity
@Table(name="FS_MATERIAL")
public class Material extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 编码 */
	private String code;
	/** 名称 */
	private String name;
	/** 规格*/
	private String spec;
	/** 单位 */
	private BussinessEleDetail unit;
	/** 所属养殖公司 */
	private Company company;
	/** 物料总类 */
	private BussinessEleDetail materialType;
	/** 副单位*/
	private BussinessEleDetail subUnit;
	/** 换算比值（副单位/主单位）*/
	private String ratio;

	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	@JoinColumn(name = "unit")
	public BussinessEleDetail getUnit() {
		return unit;
	}
	public void setUnit(BussinessEleDetail unit) {
		this.unit = unit;
	}
	
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
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
	@JoinColumn(name = "MATERIALTYPE")
	public BussinessEleDetail getMaterialType() {
		return materialType;
	}
	public void setMaterialType(BussinessEleDetail materialType) {
		this.materialType = materialType;
	}
	@ManyToOne
	@JoinColumn(name = "SUBUNIT")
	public BussinessEleDetail getSubUnit() {
		return subUnit;
	}
	public void setSubUnit(BussinessEleDetail subUnit) {
		this.subUnit = subUnit;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	
	
	
}
