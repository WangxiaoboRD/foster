package com.zd.foster.material.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.foster.base.entity.Company;

/**
 * 药品类
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-26 上午09:26:42
 */
@BussEle(name = "药品")
@Entity
@Table(name="FS_DRUG")
public class Drug extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 编码 */
	private String code;
	/** 系统编码 */
	private String sysCode;
	/** 名称 */
	private String name;
	/** 药品类型	0：药品，1：疫苗 */
	private Integer drugType;
	/** 规格*/
	private String spec;
	/** 单位 */
	private BussinessEleDetail unit;
	/** 副单位*/
	private BussinessEleDetail subUnit;
	/** 换算比值（副单位/主单位）*/
	private String ratio;
	/** 供应商 */
	private String supplier;
	/** 所属养殖公司 */
	private Company company;
	/** 物料总类 */
	private BussinessEleDetail materialType;
	
	/** 最近一次单价  透明*/
	private String lastPrice;

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
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public Integer getDrugType() {
		return drugType;
	}
	public void setDrugType(Integer drugType) {
		this.drugType = drugType;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
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
	@JoinColumn(name="subUnit")
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
	
	@Transient
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
}
