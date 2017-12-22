
package com.zd.foster.base.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;

/**
 * 生长标准
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-21 上午09:29:42
 */
@BussEle(name="生长标准")
@Entity
@Table(name="FS_GROWSTAND")
public class GrowStand extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**公司*/
	private Company company;
	/**登记日期*/
	private String registDate;
	/**备注*/
	private String remark;
	/**明细 */
	private List<GrowStandDtl> plist;
	
	@Id
	@SequenceGenerator(name = "growStandardId", sequenceName = "FS_DRIVER_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "growStandardId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRegistDate() {
		return registDate;
	}
	
	@ManyToOne
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Transient
	public List<GrowStandDtl> getPlist() {
		return plist;
	}
	public void setPlist(List<GrowStandDtl> plist) {
		this.plist = plist;
	}
	
}
