/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午07:55:32
 * @file:EliminateBillServiceImpl.java
 */
package com.zd.foster.breed.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.breed.dao.IEliminateBillDao;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.EliminateBill;
import com.zd.foster.breed.entity.EliminateBillDtl;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IEliminateBillDtlService;
import com.zd.foster.breed.services.IEliminateBillService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;

/**
 * 类名：  EliminateBillServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-8-1下午07:55:32
 * @version 1.0
 * 
 */
public class EliminateBillServiceImpl extends BaseServiceImpl<EliminateBill, IEliminateBillDao> implements
		IEliminateBillService {
	@Resource
	private IEliminateBillDtlService eliminateBillDtlService;
	@Resource
	private IContractService contractService;
	@Resource
	private IBatchService batchService;
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(EliminateBill entity, Pager<EliminateBill> page) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				sqlMap.put("registDate", ">=", startDate+" 00:00:00");
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				sqlMap.put("registDate", "<=", endDate+" 23:59:59");
			}
		}
		dao.selectByConditionHQL(entity, sqlMap, page);
	}
	
	/**
	 * 
	 * 功能:保存淘汰单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(EliminateBill entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2养殖公司是否为空
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空！");
		//1.3代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.5淘汰时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("淘汰时间不能为空！");
		//1.6淘汰头数是否超过现存头数
		Batch batch=batchService.selectById(entity.getBatch().getId());
		if(ArithUtil.comparison(entity.getTotalQuan(), batch.getQuantity())>0)
			throw new SystemException("淘汰头数不能超过存栏头数！");
		//2验证明细
		List<EliminateBillDtl> ebdList = entity.getDetails();
		//2.1明细是否为空
		if(ebdList == null || ebdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<ebdList.size();i++){
			EliminateBillDtl ebd = ebdList.get(i);
			//2.2.1验证数量是否为空，是否为非正数
			if(ebd.getQuantity()==null || "".equals(ebd.getQuantity()))
				buff.append("第"+(i+1)+"行头数不能为空<br>");
			else if(ArithUtil.comparison(ebd.getQuantity(), "0")<=0)
					buff.append("第"+(i+1)+"行头数必须为正数！<br>");
			ebd.setEliminateBill(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		Object obj = super.save(entity);
		eliminateBillDtlService.save(ebdList);
		return obj;
	}
	
	/**
	 * 
	 * 功能:修改淘汰单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(EliminateBill entity)throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2养殖公司是否为空
		//1.3代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("淘汰时间不能为空！");
		//1.6淘汰头数是否超过现存头数
		Batch batch=batchService.selectById(entity.getBatch().getId());
		if(ArithUtil.comparison(entity.getTotalQuan(), batch.getQuantity())>0)
			throw new SystemException("淘汰头数不能超过存栏头数！");
		//2验证明细
		List<EliminateBillDtl> ebdList = entity.getDetails();
		//2.1明细是否为空
		if(ebdList == null || ebdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<ebdList.size();i++){
			EliminateBillDtl ebd = ebdList.get(i);
			//2.2.1验证数量是否为空，是否为非正
			if(ebd.getQuantity()==null || "".equals(ebd.getQuantity()))
				buff.append("第"+(i+1)+"行头数不能为空<br>");
			else if(ArithUtil.comparison(ebd.getQuantity(), "0")<=0)
				buff.append("第"+(i+1)+"行头数必须为正数！<br>");
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		EliminateBill e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setFarmer(entity.getFarmer());
		e.setBatch(entity.getBatch());
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		e.setTotalQuan(entity.getTotalQuan());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					eliminateBillDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<EliminateBillDtl> updateSwd = new ArrayList<EliminateBillDtl>(); 
		List<EliminateBillDtl> newSwd = new ArrayList<EliminateBillDtl>(); 
		for(EliminateBillDtl p : ebdList){
			if(p.getId()==null){
				p.setEliminateBill(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(EliminateBillDtl p : updateSwd){
			EliminateBillDtl ed = eliminateBillDtlService.selectById(p.getId());
			ed.setQuantity(p.getQuantity());
			ed.setReason(p.getReason());
		}
		updateSwd.clear();
		//添加
		eliminateBillDtlService.save(newSwd);
	}
	
	/**
	 * 
	 * 功能:删除
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.epa.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("eliminateBill.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		eliminateBillDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 
	 * 功能:淘汰单审核
	 * 重写:wxb
	 * 2017-8-5
	 * @see com.zd.foster.breed.services.IEliminateBillService#check(java.lang.String[])
	 */
	@Override
	public void check(String[] idArr) throws Exception {
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		for(String s : idArr){
			//1.获取对象
			EliminateBill e = super.selectById(s);
			//1.1验证合同是否完结
			Contract ct=contractService.selectByHQLSingle("from Contract e where e.code='"+e.getBatch().getBatchNumber()+"'");
			if(ct.getStatus()==null || !ct.getStatus().getDcode().equals("BREED")){
				sb.append("单据【"+s+"】对应的合同不在养殖状态<br>");
				continue;
			}
			//1.2验证总淘汰头数是否超出存栏头数
			Batch batch=batchService.selectById(e.getBatch().getId());
			if(ArithUtil.comparison(e.getTotalQuan(), batch.getQuantity())>0)
				throw new SystemException("到单据【"+s+"】累计淘汰头数超过存栏头数<br>");
			//1.3更改批次信息
			batch.setEliminateQuan(ArithUtil.add(batch.getEliminateQuan()==null?"0":batch.getEliminateQuan(), e.getTotalQuan(), 0));
//			batch.setQuantity(ArithUtil.sub(batch.getQuantity(), e.getTotalQuan(), 0));
			//4.审核单据 
			e.setCheckDate(PapUtil.date(new Date()));
			e.setCheckStatus("1");
			e.setCheckUser(u.getUserRealName());
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		
	}

	/**
	 * 
	 * 功能:撤销淘汰单
	 * 重写:wxb
	 * 2017-8-6
	 * @see com.zd.foster.breed.services.IEliminateBillService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		//1.获取对象
		EliminateBill e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		Contract ct=contractService.selectByHQLSingle("from Contract e where e.code='"+e.getBatch().getBatchNumber()+"'");
		if(ct.getStatus().getDcode().equals("LOST"))
			throw new SystemException("单据【"+id+"】对应的合同已经终止<br>");
		//2.更改批次信息
		Batch batch=batchService.selectById(e.getBatch().getId());
		batch.setEliminateQuan(ArithUtil.sub(batch.getEliminateQuan(), e.getTotalQuan(),0));
//		batch.setQuantity(ArithUtil.add(batch.getQuantity(), e.getTotalQuan(), 0));
		//5.撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}
	
	


}
