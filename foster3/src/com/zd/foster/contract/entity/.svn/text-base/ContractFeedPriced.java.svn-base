package com.zd.foster.contract.entity;

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
import com.zd.foster.material.entity.Feed;
/**
 * 类名：  ContractFeedPriced
 * 功能：合同饲料定价
 * @author DZL
 * @date 2017-7-19下午02:01:10
 * @version 1.0
 * 
 */
@Entity
@Table(name="FS_CONTACTFEEDDTL")
public class ContractFeedPriced extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**id*/
	private Integer id;
	/**合同编码*/
	private Contract contract;
	/**饲料*/
	private Feed feed;
	/**定价*/
	private String price;
	
	@Id
	@SequenceGenerator(name = "contractFeedId", sequenceName = "FS_CONTRACTFEED_SEQUENCE", allocationSize = 1)
	@GeneratedValue(generator = "contractFeedId", strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "contract")
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	@ManyToOne
	@JoinColumn(name = "feed")
	public Feed getFeed() {
		return feed;
	}
	public void setFeed(Feed feed) {
		this.feed = feed;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
