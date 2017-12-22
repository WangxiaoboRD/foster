/**
 * 功能:
 * @author:wxb
 * @data:2017-9-7下午01:17:12
 * @file:DetahReason.java
 */
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
import com.zd.epa.bussobj.entity.BussinessEleDetail;

/**
 * 类名：  DetahReason
 * 功能： 死亡原因
 * @author wxb
 * @date 2017-9-7下午01:17:12
 * @version 1.0
 * 
 */
@BussEle(name = "死亡原因")
@Entity
@Table(name="FS_DEATHREASON")
public class DeathReason extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/**id*/
	private Integer id;
	/**原因编码*/
	private String code;
	/**原因名称*/
	private String name;
	/**原因类型*/
	private DeathReasonType deathReasonType;
	/**急慢性*/
	private BussinessEleDetail acuteChronic; 
	
	@Id
	@SequenceGenerator(name = "varietyId", sequenceName = "HIBERNATE_SEQUENCE", allocationSize = 1)
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
	@ManyToOne
	@JoinColumn(name="deathReasonType")
	public DeathReasonType getDeathReasonType() {
		return deathReasonType;
	}
	public void setDeathReasonType(DeathReasonType deathReasonType) {
		this.deathReasonType = deathReasonType;
	}
	
	@ManyToOne
	@JoinColumn(name="acuteChronic")
	public BussinessEleDetail getAcuteChronic() {
		return acuteChronic;
	}
	public void setAcuteChronic(BussinessEleDetail acuteChronic) {
		this.acuteChronic = acuteChronic;
	}
}
