/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:17:58
 * @file:FeedBillDtlAction.java
 */
package com.zd.foster.breed.actions;

import java.util.List;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.breed.entity.FeedBillDtl;
import com.zd.foster.breed.services.IFeedBillDtlService;
import com.zd.foster.dto.DeathAnalysis;
import com.zd.foster.dto.FeedCal;

/**
 * 类名：  FeedBillDtlAction
 * 功能：
 * @author wxb
 * @date 2017-7-27上午09:17:58
 * @version 1.0
 * 
 */
public class FeedBillDtlAction extends BaseAction<FeedBillDtl, IFeedBillDtlService> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * 功能:分页查询喂料分析
	 * @author:wxb
	 * @data:2017-10-16上午11:52:21
	 * @file:FeedBillDtlAction.java
	 * @return
	 * @throws Exception
	 */
	public String loadFeedCalAnalysisByPage()throws Exception{
		
		List<FeedCal> dlist = service.selectFeedCalAnalysis(e,pageBean);
		result.put("Rows", dlist);
		result.put("Total", dlist.size());
//		result.put("pageBean", pageBean);
		return JSON;
	}
	
	/**
	 * 
	 * 功能:导出喂料分析报表
	 * @author:wxb
	 * @data:2017-10-16上午11:56:06
	 * @file:FeedBillDtlAction.java
	 * @return
	 * @throws Exception
	 */
	public String exportFeedCalAnalysis()throws Exception{
		stream = service.exportBook(e,pageBean);
		docFileName= PapUtil.getFileName("喂料分析报表.xls");
		return DOWN;
	}

}
