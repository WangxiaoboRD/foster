package com.zd.foster.warehouse.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.base.BaseEntity;
import com.zd.foster.material.entity.Drug;

/**
 * 药品调拨单明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-14 下午04:36:59
 */
@Entity
@Table(name="FS_DRUGALLOCATIONDTL")
public class DrugAllocationDtl extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	/**药品调拨单表头*/
	private DrugAllocation drugAllocation;
	/**药品*/
	private Drug drug;
	/**数量*/
	private String quantity;
	/**库存数量*/
	private String stockQuantity;
	
	
	@Id
	@GeneratedValue(generator="drugAllocationDtlId",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="drugAllocationDtlId",sequenceName="FS_ALLOCATION_SEQ",allocationSize=1)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="DRUG")
	public Drug getDrug() {
		return drug;
	}
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@Transient
	public String getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	@ManyToOne
	@JoinColumn(name="DRUGALLOCATION")
	public DrugAllocation getDrugAllocation() {
		return drugAllocation;
	}
	public void setDrugAllocation(DrugAllocation drugAllocation) {
		this.drugAllocation = drugAllocation;
	}
	
	
	

}
