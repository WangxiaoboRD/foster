package com.zd.foster.breed.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;

/**
 * 类名：  BatchAction
 * 功能：
 * @author 杜中良
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class BatchAction extends BaseAction<Batch, IBatchService> {
	
	/**
	 * 通过id获取对象
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-29 上午09:16:19
	 */
	public String loadById()throws Exception {
		Integer i = Integer.parseInt(id);
		e = service.selectById(i);
		result.put("e", e);
		return JSON;
	}
	
	/**
	 * 代养跟进报表
	 * @return
	 * @throws Exception
	 */
	public String loadCurrentBatchByPage()throws Exception{
		service.selectCurrentBatchByPage(e,pageBean);
		result.put("Rows", pageBean.getResult());
		result.put("Total", pageBean.getTotalCount());
		pageBean.setResult(null);
		result.put("pageBean", pageBean);
		return JSON;
	}
	
	/**
	 * 代养跟进报表合计
	 * @return
	 * @throws Exception
	 */
	public String loadAllByFarmer()throws Exception{
		elist = service.selectAllByFarmer(e);
		result.put("Rows", elist);
		return JSON;
	}
	
	/**
	 * 代养跟进报表导出
	 * @return
	 * @throws Exception
	 */
	public String exportCurrentBatch()throws Exception{
		stream = service.exportCurrentBatch(e);
		docFileName= PapUtil.getFileName("代养进度追踪表.xls");
		return DOWN;
	}
	
	/**
	 * 查找日报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-21 下午02:19:03
	 */
	public String loadDayAnalysisByPage()throws Exception{
		result = service.selectDayAnalysisByPage(e, pageBean);
		result.put("pageBean", pageBean);
		return JSON;
	}
	
	/**
	 * 根据用户查询
	 * @return
	 * @throws Exception
	 */
	public String loadByUser()throws Exception{
		//获取用户信息
		Users u = SysContainer.get();
		//根据用户名获取权限 如果是admin不能有录入权限，
		if(u==null)
			throw new SystemException("无法获取用户");
		elist = service.selectAllByUser(u,e);
		result.put("Rows", elist);
		return JSON;
	}

	/**
	 * 导出日报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public String exportDayAnalysis()throws Exception{
		stream = service.exportBook(e);
		docFileName= PapUtil.getFileName("日报表.xls");
		return DOWN;
	}
	
	/**
	 * 获取饲料厂
	 * @return
	 * @throws Exception
	 */
	public String loadByFeedFac()throws Exception{
		String msn = service.selectByFeedFac(id);
		text(msn);
		return NONE;
	}
}
