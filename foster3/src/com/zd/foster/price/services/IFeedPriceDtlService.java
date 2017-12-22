
package com.zd.foster.price.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.price.entity.FeedPriceDtl;

/**
 * 饲料定价单明细服务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-27 上午10:39:42
 */
public interface IFeedPriceDtlService extends IBaseService<FeedPriceDtl> {
	/**
	 * 模板下载
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午02:49:36
	 */
	InputStream downloadTemplate(String realPath) throws Exception;
	/**
	 * 导入定价单
	 * @Description:TODO
	 * @param doc
	 * @param company
	 * @param feedFac
	 * @param startDate
	 * @param objects
	 * @return
	 * @throws Exception
	 * List<FeedPriceDtl>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午03:00:46
	 */
	List<FeedPriceDtl> operateFile(File doc, Company company, Object... objects) throws Exception;
	
	
	/**
	 * 找饲料最近的定价
	 * @Description:TODO
	 * @param f
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午03:18:47
	 */
	String lastFeedPrice(Feed f) throws Exception;
	
	
	List<FeedPriceDtl> selectAllForCopy(FeedPriceDtl e) throws Exception;

}
