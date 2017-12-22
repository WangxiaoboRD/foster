package com.zd.foster.breed.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.breed.dao.IFeedUseDetailDao;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.FeedUseDetail;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IFeedUseDetailService;

/**
 * 类名：  FeedUseDetailServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class FeedUseDetailServiceImpl extends BaseServiceImpl<FeedUseDetail, IFeedUseDetailDao> implements IFeedUseDetailService {

	@Resource
	private IBatchService batchService;
	/**
	 * 通过用户获取需要录入的代养户批次
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public List<FeedUseDetail> loadBatch(Users u,String id)throws Exception{
		
		List<FeedUseDetail> list= null;
		Company company = u.getCompany();
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("用户不具备录入权限");
		else{
			list= new ArrayList<FeedUseDetail>();
			List<Batch> blist = null;
			Technician t = u.getTechnician();
			//查询一下有没有所管理的再养批次
			if(t==null){
				//获取有没有技术员
				if(id == null || "".equals(id))
					//查询该公司所有的再养批次
					blist = batchService.selectByHQL("from Batch e where e.contract.status='BREED' and e.company.id='"+company.getId()+"' order by e.technician.id,e.inDate");
				else 
					blist = batchService.selectByHQL("from Batch e where e.contract.status='BREED' and e.company.id='"+company.getId()+"' and e.technician.id='"+id+"' order by e.inDate");
			}else{
				//查询技术员所管理的再养批次
				blist = batchService.selectByHQL("from Batch e where e.contract.status='BREED' and e.company.id='"+company.getId()+"' and e.technician.id='"+t.getId()+"' order by e.inDate");
			}
			//封装对象
			if(blist != null && !blist.isEmpty()){
				for(Batch b : blist){
					FeedUseDetail fd = new FeedUseDetail();
					fd.setBatch(b);
					list.add(fd);
				}
			}
		}
		return list;
	}
}
