
package com.zd.foster.material.services;

import com.zd.epa.base.IBaseService;
import com.zd.foster.material.entity.FeedType;

/**
 * 饲料类型服务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-23 下午03:19:33
 */
public interface IFeedTypeService extends IBaseService<FeedType> {

	String[] selectByCor(FeedType e)throws Exception;

}
