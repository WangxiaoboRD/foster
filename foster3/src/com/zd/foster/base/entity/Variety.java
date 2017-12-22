package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.zd.epa.base.BaseEntity;

/**
 * 生猪品种
 * @author 小丁
 * 17-07-19
 */
@Entity
@Table(name="FS_VARIETY")
public class Variety extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	/** 品种编码 */
	private String code;
	/** 名字 */
	private String name;
	
	@Id
	@SequenceGenerator(name = "varietyId", sequenceName = "FS_TECHNICIAN_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "varietyId", strategy = GenerationType.SEQUENCE)
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
	
	
}
