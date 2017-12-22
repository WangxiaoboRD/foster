/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午03:33:29
 * @file:MaterialBillServiceImpl.java
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
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.dao.IMaterialBillDao;
import com.zd.foster.breed.entity.MaterialBill;
import com.zd.foster.breed.entity.MaterialBillDtl;
import com.zd.foster.breed.services.IMaterialBillDtlService;
import com.zd.foster.breed.services.IMaterialBillService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.material.entity.Material;
import com.zd.foster.material.services.IMaterialService;
import com.zd.foster.warehouse.entity.MaterialOutWare;
import com.zd.foster.warehouse.entity.MaterialOutWareDtl;
import com.zd.foster.warehouse.entity.MaterialWarehouse;
import com.zd.foster.warehouse.services.IMaterialOutWareDtlService;
import com.zd.foster.warehouse.services.IMaterialOutWareService;
import com.zd.foster.warehouse.services.IMaterialWarehouseService;

/**
 * 类名：  MaterialBillServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-8-1下午03:33:29
 * @version 1.0
 * 
 */
public class MaterialBillServiceImpl extends BaseServiceImpl<MaterialBill, IMaterialBillDao> implements
		IMaterialBillService {
    @Resource
	private IMaterialBillDtlService materialBillDtlService; 
	@Resource
	private IMaterialWarehouseService materialWarehouseService;
	@Resource
	private IMaterialService materialService;
	@Resource
	private IMaterialOutWareService materialOutWareService;
	@Resource
	private IMaterialOutWareDtlService materialOutWareDtlService;
	@Resource
	private IContractService contractService;
	@Resource
	private IFarmerService farmerService;
	
	
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(MaterialBill entity, Pager<MaterialBill> page) throws Exception {
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
	 * 功能:保存领用单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(MaterialBill entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.3代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.3获取养殖公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("领用时间不能为空！");
		//2验证明细
		List<MaterialBillDtl> mbdList = entity.getDetails();
		//2.1明细是否为空
		if(mbdList == null || mbdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<mbdList.size();i++){
			MaterialBillDtl mbd = mbdList.get(i);
			//2.2.1验证数量是否为空，是否为非正数
			if(mbd.getQuantity()==null || "".equals(mbd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(mbd.getQuantity(), "0")<=0)
					buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(mbd.getSubQuantity()))
				mbd.setSubQuantity(null);
			mbd.setMaterialBill(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		Object obj = super.save(entity);
		materialBillDtlService.save(mbdList);
		return obj;
	}

	/**
	 * 
	 * 功能:修改喂料单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(MaterialBill entity)throws Exception{
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
			throw new SystemException("入库时间不能为空！");
		//2验证明细
		List<MaterialBillDtl> mbdList = entity.getDetails();
		//2.1明细是否为空
		if(mbdList == null || mbdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<mbdList.size();i++){
			MaterialBillDtl mbd = mbdList.get(i);
			//2.2.1验证数量是否为空，是否为非正
			if(mbd.getQuantity()==null || "".equals(mbd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(mbd.getQuantity(), "0")<=0)
				buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(mbd.getSubQuantity()))
				mbd.setSubQuantity(null);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		MaterialBill e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setFarmer(entity.getFarmer());
		e.setBatch(entity.getBatch());
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					materialBillDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<MaterialBillDtl> updateSwd = new ArrayList<MaterialBillDtl>(); 
		List<MaterialBillDtl> newSwd = new ArrayList<MaterialBillDtl>(); 
		for(MaterialBillDtl p : mbdList){
			if(p.getId()==null){
				p.setMaterialBill(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(MaterialBillDtl p : updateSwd){
			MaterialBillDtl ed = materialBillDtlService.selectById(p.getId());
			ed.setQuantity(p.getQuantity());
			ed.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
		}
		updateSwd.clear();
		//添加
		materialBillDtlService.save(newSwd);
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
		sqlMap.put("materialBill.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		materialBillDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	/**
	 * 
	 * 功能:审核领用单,建立其他物料出库单,扣其他物料库存
	 * 重写:wxb
	 * 2017-8-1
	 * @see com.zd.foster.breed.services.IMaterialBillService#check(java.lang.String[])
	 */
	@Override
	public void check(String[] idArr) throws Exception {
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String s : idArr){
			//1.获取对象
			MaterialBill e = super.selectById(s);
			
			Contract ct=e.getBatch().getContract();
			if(ct!=null && ct.getStatus()!=null ){
				if(!"BREED".equals(ct.getStatus().getDcode())){
					sb.append("单据【"+s+"】【"+e.getFarmer().getName()+"】第【"+e.getBatch().getBatchNumber()+"】批对应的合同不在养殖状态<br>");
					continue;
				}
			}
			
			//2.获取明细并遍历明细
			List<MaterialBillDtl> edList = materialBillDtlService.selectBySingletAll("materialBill.id", s);
			if(e==null ||edList==null || edList.size()==0)
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
			//2.**建立其他物料出库单表头
			MaterialOutWare mow=new MaterialOutWare();
			List<MaterialOutWareDtl> mowdList=new ArrayList<MaterialOutWareDtl>();
			for(MaterialBillDtl ed : edList){
				//2.1查询代养户该其他物料库存
				sqlMap.put("company.id", "=", e.getCompany().getId());
				sqlMap.put("material.id", "=", ed.getMaterial().getId());
				List<MaterialWarehouse> mwList = materialWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//2.2判断库存是否存在
				if(mwList==null || mwList.size()==0){
					sb.append("单据【"+s+"】其他物料【"+ed.getMaterial().getId()+"】没有库存<br>");
					continue;
				}else{
					//2.3该其他物料库存是否足够出库，足够则减库存,建其他物料出库单
					MaterialWarehouse mw = mwList.get(0);
					if(mw != null){
						//原始数据
						String q_old = mw.getQuantity();
						//领用单数据
						String q_new = ed.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						if(q_new==null || "".equals(q_new))
							q_new = "0";
						//2.3.1验证库存是否足够出库
						if(ArithUtil.comparison(q_old, q_new)<0){
							sb.append("单据【"+s+"】其他物料【"+mw.getMaterial().getName()+"】库存不够<br>");
							continue;
						}
						//2.3.2建立其他物料出库单明细
						MaterialOutWareDtl mowd=new MaterialOutWareDtl();
						mowd.setMaterialOutWare(mow);
						mowd.setMaterial(ed.getMaterial());
						mowd.setQuantity(ed.getQuantity());
						mowd.setSubQuantity(ed.getSubQuantity());
						mowdList.add(mowd);
						//2.3.3扣库存
						String num = ArithUtil.sub(q_old, q_new,2);
						mw.setQuantity(num);
						//2.3.4添加副单位数量
						Material material=materialService.selectById(ed.getMaterial().getId());
						String ratio=material.getRatio();
						if(ratio!=null && !"".equals(ratio))
							mw.setSubQuantity(ArithUtil.div(num, ratio, 2));
					}
				}
			}
			//3.1保存其他物料出库单表头和明细
			mow.setCompany(e.getCompany());
			mow.setFarmer(e.getFarmer());
			mow.setRegistDate(e.getRegistDate());
			mow.setMaterialBill(e);
			mow.setCheckDate(PapUtil.date(new Date()));
			mow.setCheckStatus("1");
			mow.setCheckUser(u.getUserRealName());
			materialOutWareService.save(mow);
			materialOutWareDtlService.save(mowdList);
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
	 * 功能:撤销
	 * 重写:wxb
	 * 2017-8-1
	 * @see com.zd.foster.breed.services.IMaterialBillService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.获取对象
		MaterialBill e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		
		Contract ct=e.getBatch().getContract();
		if(ct!=null && ct.getStatus()!=null ){
			if("LOST".equals(ct.getStatus().getDcode()))
				throw new SystemException("单据【"+id+"】对应的合同已经终止<br>");
		}
		
		//2.获取明细并遍历
		List<MaterialBillDtl> edList = materialBillDtlService.selectBySingletAll("materialBill.id", id);
		if(e==null ||edList==null || edList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		List<MaterialWarehouse> mwAllList = new ArrayList<MaterialWarehouse>();
		for(MaterialBillDtl ed : edList){
			//2.1查询其他物料的库存
			sqlMap.put("company.id", "=", e.getCompany().getId());
			sqlMap.put("material.id", "=", ed.getMaterial().getId());
			List<MaterialWarehouse> mwList = materialWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			//2.2没有库存新建库存
			if(mwList==null || mwList.size()==0){
				MaterialWarehouse mw = new MaterialWarehouse();
				mw.setCompany(e.getCompany());
				mw.setMaterial(ed.getMaterial());
				mw.setQuantity(ed.getQuantity());
				mw.setSubQuantity(ed.getSubQuantity());
				mwAllList.add(mw);
			}else{
				//2.3有库存则增加库存
				MaterialWarehouse mw=mwList.get(0);
				//2.3.1.调整数量
				String newNum = ArithUtil.add(mw.getQuantity(), ed.getQuantity());
				mw.setQuantity(newNum);
				//2.3.2添加副单位数量
				Material material=materialService.selectById(ed.getMaterial().getId());
				String ratio=material.getRatio();
				if(ratio!=null && !"".equals(ratio))
					mw.setSubQuantity(ArithUtil.div(newNum, ratio, 2));
			}
		}
		//3.保存新的其他物料库存
		if(mwAllList !=null && mwAllList.size()>0)
			materialWarehouseService.save(mwAllList);
		//4.删除其他物料出库单
		MaterialOutWare mow=materialOutWareService.selectByHQLSingle("from MaterialOutWare e where e.materialBill.id='"+id+"'");
		materialOutWareService.deleteById(mow.getId());
		//5.撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
		
	}

}
