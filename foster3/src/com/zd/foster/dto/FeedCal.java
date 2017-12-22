package com.zd.foster.dto;

import com.zd.epa.base.BaseEntity;

/**
 * 
 * 类名：  FeedCal
 * 功能： 喂料分析
 * @author wxb
 * @date 2017-10-13下午05:25:57
 * @version 1.0
 *
 */
public class FeedCal extends BaseEntity {
	/** 公司 */
	private String company;
	/**饲料厂*/
	private String feedFac;
	/** 代养户 */ 
	private String farmer;
	/** 批次 */
	private String batch;
	/**技术员*/
	private String technician;
	/**当日存栏*/
	private String pigNum;
	/** 当日喂料数量 */
	private String dQty;
	/** 当日喂料标准 */
	private String dayStd;
	/** 当日喂料偏差 */
	private String dayDif;
	/** 当日喂料偏差比例 */
	private String dayDifPro;
	/** 累计喂料数量 */
	private String tolQty;
	/** 累计喂料标准 */
	private String stdtq;
	/** 累计喂料偏差 */
	private String tolDif;
	/** 累计喂料偏差比例 */
	private String difPro;
	/** 当日库存*/
	private String stock;
	/**合同头数*/
	private String contQuan;
	/**进猪日期*/
	private String piginDate;
	
	 
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFeedFac() {
		return feedFac;
	}
	public void setFeedFac(String feedFac) {
		this.feedFac = feedFac;
	}
	public String getFarmer() {
		return farmer;
	}
	public void setFarmer(String farmer) {
		this.farmer = farmer;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getTechnician() {
		return technician;
	}
	public void setTechnician(String technician) {
		this.technician = technician;
	}	
	public String getPigNum() {
		return pigNum;
	}
	public void setPigNum(String pigNum) {
		this.pigNum = pigNum;
	}
	
	public String getDayStd() {
		return dayStd;
	}
	public void setDayStd(String dayStd) {
		this.dayStd = dayStd;
	}
	public String getDayDif() {
		return dayDif;
	}
	public void setDayDif(String dayDif) {
		this.dayDif = dayDif;
	}
	public String getDayDifPro() {
		return dayDifPro;
	}
	public void setDayDifPro(String dayDifPro) {
		this.dayDifPro = dayDifPro;
	}
	
	
	public String getdQty() {
		return dQty;
	}
	public void setdQty(String dQty) {
		this.dQty = dQty;
	}
	public String getTolQty() {
		return tolQty;
	}
	public void setTolQty(String tolQty) {
		this.tolQty = tolQty;
	}
	public String getStdtq() {
		return stdtq;
	}
	public void setStdtq(String stdtq) {
		this.stdtq = stdtq;
	}
	public String getTolDif() {
		return tolDif;
	}
	public void setTolDif(String tolDif) {
		this.tolDif = tolDif;
	}
	public String getDifPro() {
		return difPro;
	}
	public void setDifPro(String difPro) {
		this.difPro = difPro;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getContQuan() {
		return contQuan;
	}
	public void setContQuan(String contQuan) {
		this.contQuan = contQuan;
	}
	public String getPiginDate() {
		return piginDate;
	}
	public void setPiginDate(String piginDate) {
		this.piginDate = piginDate;
	}
	
	
}
