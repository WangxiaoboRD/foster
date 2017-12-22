
package com.zd.foster.material.services.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.breed.services.IMaterialBillDtlService;
import com.zd.foster.material.dao.IMaterialDao;
import com.zd.foster.material.entity.Material;
import com.zd.foster.material.services.IMaterialService;
import com.zd.foster.price.services.IMaterialPriceDtlService;
import com.zd.foster.utils.FosterUtil;
import com.zd.foster.warehouse.services.IMaterialInWareDtlService;
import com.zd.foster.warehouse.services.IMaterialOutWareDtlService;
import com.zd.foster.warehouse.services.IMaterialWarehouseService;

/**
 * 其他物料服务层实现
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-26 下午02:29:01
 */
public class MaterialServiceImpl extends BaseServiceImpl<Material, IMaterialDao> implements IMaterialService {
	
	@Resource
	private IMaterialInWareDtlService materialInWareDtlService;
	@Resource
	private IMaterialWarehouseService materialWarehouseService;
	@Resource
	private IMaterialOutWareDtlService materialOutWareDtlService;
	@Resource
	private IMaterialPriceDtlService materialPriceDtlService;
	@Resource
	private IMaterialBillDtlService materialBillDtlService;
	
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-25 下午02:19:09
	 */
	public Object save(Material entity) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
		else{
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("name", "=", entity.getName());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("名称不允许重复");
		}
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			//1.验证编码
			if(!FosterUtil.idCode(entity.getCode()))
				throw new SystemException("编码应为十位内的数字和字母！");
			
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("code", "=", entity.getCode());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		if(entity.getUnit()==null || entity.getUnit().getDcode()==null || "".equals(entity.getUnit().getDcode()))
			throw new SystemException("单位不允许为空");
		entity.setMaterialType(new BussinessEleDetail("material"));
		
		//保存对象
		return dao.insert(entity);
	}
	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-25 下午02:29:16
	 */
	public int updateHql(Material entity)throws Exception{
		if(entity.getUnit()==null || entity.getUnit().getDcode()==null || "".equals(entity.getUnit().getDcode()))
			throw new SystemException("单位不允许为空");
		
		return super.updateHql(entity);
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
	 * @time:2017-7-25 下午02:41:38
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//删除：验证单据上上的其他物料:物料入库单明细、物料仓库、物料出库单明细、物料定价单明细、物料领用单明细、
			int inwn = materialInWareDtlService.selectTotalRows("material.id", id);
			if(inwn>0)
				throw new SystemException("此物料正在被使用，不允许删除");
			
			int wn = materialWarehouseService.selectTotalRows("material.id", id);
			if(wn>0)
				throw new SystemException("此物料正在被使用，不允许删除");
			
			int outwn = materialOutWareDtlService.selectTotalRows("material.id", id);
			if(outwn>0)
				throw new SystemException("此物料正在被使用，不允许删除");
			
			int pn = materialPriceDtlService.selectTotalRows("material.id", id);
			if(pn>0)
				throw new SystemException("此物料正在被使用，不允许删除");
			
			int mbn = materialBillDtlService.selectTotalRows("material.id", id);
			if(mbn>0)
				throw new SystemException("此物料正在被使用，不允许删除");
		
		}
		
		return dao.deleteByIds(PK);
	}
	
	
	
}
