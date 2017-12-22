package com.zd.foster.price.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.base.BaseEntity;
import com.zd.foster.material.entity.Material;


/**
 * 其他物料定价单明细
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 上午09:19:48
 */
@Entity
@Table(name="FS_MATERIELPRICEDTL")
public class MaterialPriceDtl extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 编码 */
	private Integer id;
	/**定价单编号 */
	private MaterialPrice materialPrice;
	/** 药品 */
	private Material material;
	/** 购进单价 */
	private String Price;
	/** 销售单价 */
	private String salePrice;
	
	@Id
	@SequenceGenerator(name = "priceDtlId", sequenceName = "FS_PRICEDTL_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "priceDtlId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	
	@ManyToOne
	@JoinColumn(name = "materialPrice")
	public MaterialPrice getMaterialPrice() {
		return materialPrice;
	}
	public void setMaterialPrice(MaterialPrice materialPrice) {
		this.materialPrice = materialPrice;
	}
	
	@ManyToOne
	@JoinColumn(name = "material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	
	
}
