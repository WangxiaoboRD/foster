/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:15:58
 * @file:IFeedBillDtlService.java
 */
package com.zd.foster.breed.services;

import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.Pager;
import com.zd.foster.breed.entity.FeedBillDtl;
import com.zd.foster.dto.FeedCal;

/**
 * 类名：  IFeedBillDtlService
 * 功能：
 * @author wxb
 * @date 2017-7-27上午09:15:58
 * @version 1.0
 * 
 */
public interface IFeedBillDtlService extends IBaseService<FeedBillDtl> {
	
	//public List<FeedCal> selectFeedCalAnalysisByPage(FeedBillDtl e,Pager<FeedBillDtl> page) throws Exception;
	public List<FeedCal> selectFeedCalAnalysis(FeedBillDtl e,Pager<FeedBillDtl> page) throws Exception;
	public InputStream exportBook(FeedBillDtl e,Pager<FeedBillDtl> page)throws Exception;

}
