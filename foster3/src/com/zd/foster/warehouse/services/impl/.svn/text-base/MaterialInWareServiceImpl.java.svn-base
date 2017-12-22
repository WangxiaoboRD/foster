/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:44:43
 * @file:MaterialInWareServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

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
import com.zd.foster.material.entity.Material;
import com.zd.foster.material.services.IMaterialService;
import com.zd.foster.warehouse.dao.IMaterialInWareDao;
import com.zd.foster.warehouse.entity.MaterialInWare;
import com.zd.foster.warehouse.entity.MaterialInWareDtl;
import com.zd.foster.warehouse.entity.MaterialWarehouse;
import com.zd.foster.warehouse.services.IMaterialInWareDtlService;
import com.zd.foster.warehouse.services.IMaterialInWareService;
import com.zd.foster.warehouse.services.IMaterialWarehouseService;

/**
 * 类名：  MaterialInWareServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午04:44:43
 * @version 1.0
 * 
 */
public class MaterialInWareServiceImpl extends BaseServiceImpl<MaterialInWare, IMaterialInWareDao>
		implements IMaterialInWareService {
	@Resource
	private IMaterialInWareDtlService materialInWareDtlService;
	@Resource
	private IMaterialWarehouseService materialWarehouseService;
	@Resource
	private IMaterialService materialService;
	
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(MaterialInWare entity, Pager<MaterialInWare> page) throws Exception {
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
	 * 功能:保存入库单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(MaterialInWare entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2养殖公司是否为空
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空！");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("入库时间不能为空！");
		//2验证明细
		List<MaterialInWareDtl> miwdList = entity.getDetails();
		//2.1明细是否为空
		if(miwdList == null || miwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<miwdList.size();i++){
			MaterialInWareDtl miwd = miwdList.get(i);
			//2.2.1验证数量是否为空，是否为0
			if(miwd.getQuantity()==null || "".equals(miwd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else{
				//转化为数值
				double d = Double.parseDouble(miwd.getQuantity());
				if(d==0){
					buff.append("第"+(i+1)+"行数值不允许为0<br>");
				}
			}
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(miwd.getSubQuantity()))
				miwd.setSubQuantity(null);
			miwd.setMaterialInWare(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		Object obj = super.save(entity);
		materialInWareDtlService.save(miwdList);
		return obj;
	}
	
	/**
	 * 
	 * 功能:修改入库单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(MaterialInWare entity)throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("入库时间不能为空！");
		//2验证明细
		List<MaterialInWareDtl> miwdList = entity.getDetails();
		//2.1明细是否为空
		if(miwdList == null || miwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<miwdList.size();i++){
			MaterialInWareDtl miwd = miwdList.get(i);
			//2.2.1验证数量是否为空，是否为0
			if(miwd.getQuantity()==null || "".equals(miwd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else{
				//转化为数值
				double d = Double.parseDouble(miwd.getQuantity());
				if(d==0){
					buff.append("第"+(i+1)+"行数值不允许为0<br>");
				}
			}
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(miwd.getSubQuantity()))
				miwd.setSubQuantity(null);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		MaterialInWare e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					materialInWareDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<MaterialInWareDtl> updateSwd = new ArrayList<MaterialInWareDtl>(); 
		List<MaterialInWareDtl> newSwd = new ArrayList<MaterialInWareDtl>(); 
		for(MaterialInWareDtl p : miwdList){
			if(p.getId()==null){
				p.setMaterialInWare(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(MaterialInWareDtl p : updateSwd){
			MaterialInWareDtl d = materialInWareDtlService.selectById(p.getId());
			d.setQuantity(p.getQuantity());
			d.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
		}
		updateSwd.clear();
		//添加
		materialInWareDtlService.save(newSwd);
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
		sqlMap.put("materialInWare.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		materialInWareDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 
	 * 功能:审核
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.foster.warehouse.services.IMaterialInWareService#check(java.lang.String[])
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String s : idArr){
			//1.获取对象
			MaterialInWare e = super.selectById(s);
			//2.获取明细并遍历明细
			List<MaterialInWareDtl> edList = materialInWareDtlService.selectBySingletAll("materialInWare.id", s);
			if(e==null ||edList==null || edList.size()==0)
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
			List<MaterialWarehouse> mwAllList = new ArrayList<MaterialWarehouse>();
			for(MaterialInWareDtl ed : edList){
				//判断入库明细是正还是负
				boolean flag=false;
				if(ArithUtil.comparison(ed.getQuantity(), "0")==1)
					flag=true;
				//2.1查找养殖公司是否有该药品库存
				sqlMap.put("company.id", "=", e.getCompany().getId());
				sqlMap.put("material.id", "=", ed.getMaterial().getId());
				List<MaterialWarehouse> dwList = materialWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//2.2.1库存无该药品
				if(dwList==null || dwList.size()==0){
					if(!flag){
						sb.append("单据【"+s+"】明细【"+ed.getMaterial()+"】无库存<br>");
						continue ;
					}
					MaterialWarehouse mw = new MaterialWarehouse();
					mw.setIsDrug("1");
					mw.setCompany(e.getCompany());
					mw.setMaterial(ed.getMaterial());
					mw.setQuantity(ed.getQuantity());
					mw.setSubQuantity(ed.getSubQuantity());
					mwAllList.add(mw);
				}else{
					//2.2.2库存有该药品库存
					MaterialWarehouse mw = dwList.get(0);
					if(mw != null){
						//原始数据
						String q_old = mw.getQuantity();
						//入库单数据
						String q_new = ed.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						if(q_new==null || "".equals(q_new))
							q_new = "0";
						//更改库存数量
						String num = ArithUtil.add(q_old, q_new);
						if(ArithUtil.comparison(num, "0")<0){
							sb.append("单据【"+s+"】明细【"+mw.getMaterial().getName()+"】库存不够<br>");
							continue ;
						}
						mw.setQuantity(num);
						//更改副单位数量
						Material material=materialService.selectById(ed.getMaterial().getId());
						String ratio=material.getRatio();
						if(ratio!=null && !"".equals(ratio))
							mw.setSubQuantity(ArithUtil.div(num, ratio, 2));
					}
				}
			}
			//审核单据 
			e.setCheckDate(PapUtil.date(new Date()));
			e.setCheckStatus("1");
			e.setCheckUser(u.getUserRealName());
			//保存其他物料库存
			if(mwAllList !=null && mwAllList.size()>0)
				materialWarehouseService.save(mwAllList);
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		
	}

	/**
	 * 
	 * 功能:撤销
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.foster.warehouse.services.IMaterialInWareService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.获取对象
		MaterialInWare e = super.selectById(id);
		//2.获取明细并遍历
		List<MaterialInWareDtl> edList = materialInWareDtlService.selectBySingletAll("materialInWare.id", id);
		if(e==null ||edList==null || edList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		StringBuffer sb = new StringBuffer();
		List<MaterialWarehouse> mwAllList = new ArrayList<MaterialWarehouse>();
		for(MaterialInWareDtl ed : edList){
			//判断入库明细是正还是负
			boolean flag=false;
			if(ArithUtil.comparison(ed.getQuantity(), "0")==1)
				flag=true;
			//2.1查询药品的库存
			sqlMap.put("company.id", "=", e.getCompany().getId());
			sqlMap.put("material.id", "=", ed.getMaterial().getId());
			List<MaterialWarehouse> mwList = materialWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			//2.2验证是否有该药品库存
			if(mwList==null || mwList.size()==0){
				if(flag){
					sb.append("单据【"+id+"】中饲料【"+ed.getMaterial().getName()+"】已经不存在<br>");
					continue;
				}else{
					MaterialWarehouse mw = new MaterialWarehouse();
					mw.setIsDrug("1");
					mw.setCompany(e.getCompany());
					mw.setMaterial(ed.getMaterial());
					mw.setQuantity(ArithUtil.sub("0", ed.getQuantity()));
					mw.setSubQuantity(ArithUtil.sub("0", ed.getSubQuantity()));
					mwAllList.add(mw);
				}
			}
			//2.3验证该药品库存是否足够撤销
			MaterialWarehouse mw = mwList.get(0);
			String dwNum= mw.getQuantity();
			if(ArithUtil.comparison(dwNum, ed.getQuantity()) == -1){
				sb.append("单据【"+id+"】中物料【"+ed.getMaterial().getName()+"】已经使用<br>");
				continue;
			}
			//2.3对库存进行调整
			//2.3.1.调整数量
			String newNum = ArithUtil.sub(dwNum, ed.getQuantity());
			mw.setQuantity(newNum);
			//2.3.2添加副单位数量
			Material material=materialService.selectById(ed.getMaterial().getId());
			String ratio=material.getRatio();
			if(ratio!=null && !"".equals(ratio))
				mw.setSubQuantity(ArithUtil.div(newNum, ratio, 2));
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		//保存其他物料库存
		if(mwAllList !=null && mwAllList.size()>0)
			materialWarehouseService.save(mwAllList);
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}

}
