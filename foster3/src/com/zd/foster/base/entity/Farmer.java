
package com.zd.foster.base.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zd.epa.annotation.BussEle;
import com.zd.epa.base.BaseEntity;
import com.zd.epa.bussobj.entity.BussinessEleDetail;

/**
 * 类名：  Farmer
 * 功能：代养户基础类
 * @author wxb
 * @date 2017-7-19下午02:21:51
 * @version 1.0
 * 
 */
@BussEle(name="代养户")
@Entity
@Table(name="FS_FARMER")
public class Farmer extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**代养户id*/
	private String id;
	/**代养户变编码*/
	private String code;
	/**代养户姓名*/
	private String name;
	/**拼音*/
	private String pinyin;
	/**身份证*/
	private String idCard;
	/**家庭住址*/
	private String homeAddress;
	/**电话*/
	private String phone;
	/**农场地址*/
	private String farmAddress;
	/**农场面积*/
	private String farmArea;
	/**养殖舍数量*/
	private String piggeryQuan;
	/**可以代养猪的数量*/
	private String breedQuan;
	/**阶段*/
	private BussinessEleDetail stage;
	/**已代养的批次*/
	private String batchNum;
	/**养殖公司*/
	private Company company;
	/**技术员*/
	private Technician technician;
	/**是否是自养场	Y：自养	N:代养*/
	private String isOwnBreed;
	
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
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFarmAddress() {
		return farmAddress;
	}
	public void setFarmAddress(String farmAddress) {
		this.farmAddress = farmAddress;
	}
	public String getFarmArea() {
		return farmArea;
	}
	public void setFarmArea(String farmArea) {
		this.farmArea = farmArea;
	}
	public String getPiggeryQuan() {
		return piggeryQuan;
	}
	public void setPiggeryQuan(String piggeryQuan) {
		this.piggeryQuan = piggeryQuan;
	}
	public String getBreedQuan() {
		return breedQuan;
	}
	public void setBreedQuan(String breedQuan) {
		this.breedQuan = breedQuan;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="STAGE")
	public BussinessEleDetail getStage() {
		return stage;
	}
	public void setStage(BussinessEleDetail stage) {
		this.stage = stage;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COMPANY")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="technician")
	public Technician getTechnician() {
		return technician;
	}
	public void setTechnician(Technician technician) {
		this.technician = technician;
	}
	public String getIsOwnBreed() {
		return isOwnBreed;
	}
	public void setIsOwnBreed(String isOwnBreed) {
		this.isOwnBreed = isOwnBreed;
	}
	
	
}
