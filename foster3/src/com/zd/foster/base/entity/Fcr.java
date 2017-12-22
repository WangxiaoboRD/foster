package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.base.BaseEntity;

/**
 * 标准料肉比
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-6 下午06:50:43
 */
@Entity
@Table(name="FS_FCR")
public class Fcr extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	/** 猪苗重 */
	private String pigletWei;
	/** 出猪重 */
	private String pigWei;
	/** fcr */
	private String fcr;
	
	@Id
	@SequenceGenerator(name = "fcrId", sequenceName = "FS_FCR_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "fcrId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPigletWei() {
		return pigletWei;
	}
	public void setPigletWei(String pigletWei) {
		this.pigletWei = pigletWei;
	}
	public String getPigWei() {
		return pigWei;
	}
	public void setPigWei(String pigWei) {
		this.pigWei = pigWei;
	}
	public String getFcr() {
		return fcr;
	}
	public void setFcr(String fcr) {
		this.fcr = fcr;
	}
	
	
	
}
