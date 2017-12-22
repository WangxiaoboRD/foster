
package com.zd.foster.base.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.base.dao.IGrowStandDao;
import com.zd.foster.base.entity.GrowStand;
import com.zd.foster.base.entity.GrowStandDtl;
import com.zd.foster.base.services.IGrowStandDtlService;
import com.zd.foster.base.services.IGrowStandService;

/**
 * 生长标准服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-21 上午11:50:44
 */
public class GrowStandServiceImpl extends BaseServiceImpl<GrowStand, IGrowStandDao> implements IGrowStandService {

	@Resource
	private IGrowStandDtlService growStandDtlService;
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-23 下午02:56:38
	 */
	public Object save(GrowStand entity) throws Exception{
		if(entity == null)
			throw new SystemException("对象不能为空");
		
		if(entity.getCompany()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空");
		int number = super.selectTotalRows("company.id", entity.getCompany().getId());
		if(number>0)
			throw new SystemException("此公司生长标准已经登记！");
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("登记时间不能为空");
		
		List<GrowStandDtl> plist = entity.getPlist();
		if(plist==null || plist.size()==0)
			throw new SystemException("生长标准明细不能为空");
		
		//验证	找重复
		StringBuffer buff = new StringBuffer();
		List<String> dayList = new ArrayList<String>();
		List<Integer> reList = new ArrayList<Integer>();
		for(int i=0;i<plist.size();i++){
			GrowStandDtl ps = plist.get(i);
			
			if(ps.getDays()==null || "".equals(ps.getDays()))
				buff.append("生长标准第【"+(i+1)+"】行日龄不能为空！<br/>");
			else{
				if(!PapUtil.isNum(ps.getDays()))
					buff.append("生长标准第【"+(i+1)+"】行日龄必须输入数字！<br/>");
				else{
					if(dayList.contains(ps.getDays()))
						reList.add(i+1);
					else
						dayList.add(ps.getDays());
				}
			}
			
			if(ps.getStandWeight()==null || "".equals(ps.getStandWeight()))
				buff.append("生长标准第【"+(i+1)+"】行标准体重不能为空！<br/>");
			else{
				if(!PapUtil.isNum(ps.getStandWeight()))
					buff.append("生长标准第【"+(i+1)+"】行标准体重必须输入数字！<br/>");
				double d = Double.parseDouble(ps.getStandWeight());
				if(d < 0)
					buff.append("生长标准第【"+(i+1)+"】行标准体重不允许为负数<br/>");
			}
			
			if(ps.getFeedType()==null || ps.getFeedType().getId()==null ||"".equals(ps.getFeedType().getId()))
				buff.append("生长标准第【"+(i+1)+"】行饲料阶段不能为空！<br/>");
			if(ps.getFeedWeight()==null || "".equals(ps.getFeedWeight()))
				buff.append("生长标准第【"+(i+1)+"】行喂料量不能为空！<br/>");
			else{
				if(!PapUtil.isNum(ps.getFeedWeight()))
					buff.append("生长标准第【"+(i+1)+"】行喂料量必须输入数字！<br/>");
				double d = Double.parseDouble(ps.getFeedWeight());
				if(d < 0)
					buff.append("生长标准第【"+(i+1)+"】行喂料量不允许为负数<br/>");
			}
			
			ps.setGrowStand(entity);
		}
		if(!reList.isEmpty()){
			for(Integer n : reList)
				buff.append("肉猪饲养标准第【"+n+"】行日龄重复！<br/>");
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		
		Object obj = super.save(entity);
		growStandDtlService.save(plist);
		
		return obj;
	}
	
	
	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-23 下午05:12:45
	 */
	public int updateHql(GrowStand entity)throws Exception{
		if(entity == null)
			throw new SystemException("饲养标准对象为空");
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("登记时间不能为空");
		
		List<GrowStandDtl> plist = entity.getPlist();
		if(plist==null || plist.size()==0)
			throw new SystemException("生长标准明细不能为空");
		
		//验证	找重复
		StringBuffer buff = new StringBuffer();
		List<String> dayList = new ArrayList<String>();
		List<Integer> reList = new ArrayList<Integer>();
		for(int i=0;i<plist.size();i++){
			GrowStandDtl ps = plist.get(i);
			
			if(ps.getDays()==null || "".equals(ps.getDays()))
				buff.append("生长标准第【"+(i+1)+"】行日龄不能为空！<br/>");
			else{
				if(!PapUtil.isNum(ps.getDays()))
					buff.append("生长标准第【"+(i+1)+"】行日龄必须输入数字！<br/>");
				else{
					if(dayList.contains(ps.getDays()))
						reList.add(i+1);
					else
						dayList.add(ps.getDays());
				}
			}
			
			if(ps.getStandWeight()==null || "".equals(ps.getStandWeight()))
				buff.append("生长标准第【"+(i+1)+"】行标准体重不能为空！<br/>");
			else{
				if(!PapUtil.isNum(ps.getStandWeight()))
					buff.append("生长标准第【"+(i+1)+"】行标准体重必须输入数字！<br/>");
				double d = Double.parseDouble(ps.getStandWeight());
				if(d < 0)
					buff.append("生长标准第【"+(i+1)+"】行标准体重不允许为负数<br/>");
			}
			if(ps.getFeedType()==null || ps.getFeedType().getId()==null ||"".equals(ps.getFeedType().getId()))
				buff.append("生长标准第【"+(i+1)+"】行饲料阶段不能为空！<br/>");
			if(ps.getFeedWeight()==null || "".equals(ps.getFeedWeight()))
				buff.append("生长标准第【"+(i+1)+"】行喂料量不能为空！<br/>");
			else{
				if(!PapUtil.isNum(ps.getFeedWeight()))
					buff.append("生长标准第【"+(i+1)+"】行喂料量必须输入数字！<br/>");
				double d = Double.parseDouble(ps.getFeedWeight());
				if(d < 0)
					buff.append("生长标准第【"+(i+1)+"】行喂料量不允许为负数<br/>");
			}
			
			ps.setGrowStand(entity);
		}
		if(!reList.isEmpty()){
			for(Integer n : reList)
				buff.append("肉猪饲养标准第【"+n+"】行日龄重复！<br/>");
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		
		//修改表头
		GrowStand fs = super.selectById(entity.getId());
		fs.setCompany(entity.getCompany());
		fs.setRegistDate(entity.getRegistDate());
		fs.setRemark(entity.getRemark());
		//删除原来的明细
		growStandDtlService.deleteBySingletAll("growStand.id", fs.getId());
		//保存新明细
		growStandDtlService.save(plist);
		
		return 1;
	}
	/**
	 * 删除
	 * @Description:TODO
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-23 下午05:13:49
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id:PK){
			growStandDtlService.deleteBySingletAll("growStand.id", id);
		}
		return super.deleteByIds(PK);
	}
	
	
}
