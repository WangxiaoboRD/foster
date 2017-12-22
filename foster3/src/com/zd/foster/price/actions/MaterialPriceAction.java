
package com.zd.foster.price.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.price.entity.MaterialPrice;
import com.zd.foster.price.services.IMaterialPriceService;

/**
 * 其他物料定价单Action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 上午09:14:21
 */
public class MaterialPriceAction extends BaseAction<MaterialPrice, IMaterialPriceService> {
	
	/**
	 * 审核
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-28 上午11:44:30
	 */
	public String check()throws Exception{	
		String[] idArr = ids.split(",");
		service.check(idArr);
		message = idArr.length + "";		
		text(message);
		return NONE;
	}
	
	/**
	 * 撤销
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-28 上午11:44:43
	 */
	public String cancelCheck()throws Exception{	
		
		service.cancelCheck(id);
		message = "1";		
		text(message);
		return NONE;
	}
	
	/**
	 * 复制新增
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-28 下午02:33:59
	 */
	public String loadCopyById()throws Exception{
		//e = service.selectById(etype,id);
		//e = service.selectById(id);
		Integer _id = TypeUtil.getIdType(id,e.getClass());
		e = _id==null?service.selectById(id):service.selectById(_id);
		return "copy";
	}
	
}
