
package com.zd.foster.base.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.base.entity.GrowStand;
import com.zd.foster.base.services.IGrowStandService;

/**
 * 生长标准Action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-21 上午11:52:50
 */
public class GrowStandAction extends BaseAction<GrowStand, IGrowStandService> {
	
	/**
	 * 生长标准复制新增
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-23 下午04:35:29
	 */
	public String loadCopyById()throws Exception{
		//e = service.selectById(etype,id);
		//e = service.selectById(id);
		Integer _id = TypeUtil.getIdType(id,e.getClass());
		e = _id==null?service.selectById(id):service.selectById(_id);
		return "copy";
	}
	
}
