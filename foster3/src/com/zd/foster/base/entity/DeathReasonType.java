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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;

/**
 * 类名：  DetahReason
 * 功能： 死亡原因
 * @author wxb
 * @date 2017-9-7下午01:17:12
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_DEATHREASONTYPE")
public class DeathReasonType extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/**id*/
	private Integer id;
	/**原因类型名称*/
	private String name;
	@Id
	@SequenceGenerator(name = "deathReasonTypeId", sequenceName = "HIBERNATE_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "deathReasonTypeId", strategy = GenerationType.SEQUENCE)
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
}
