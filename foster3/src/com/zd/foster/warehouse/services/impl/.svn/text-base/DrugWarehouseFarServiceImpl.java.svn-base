/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午03:18:05
 * @file:DrugWarehouseFarServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.warehouse.dao.IDrugWarehouseFarDao;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;
import com.zd.foster.warehouse.services.IDrugWarehouseFarService;

/**
 * 类名：  DrugWarehouseFarServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-9-8下午03:18:05
 * @version 1.0
 * 
 */
public class DrugWarehouseFarServiceImpl extends BaseServiceImpl<DrugWarehouseFar, IDrugWarehouseFarDao>
		implements IDrugWarehouseFarService {

//	/**
//	 * 
//	 * 功能:药品查询时，isDrug属性需为0
//	 * 重写:wxb
//	 * 2017-7-29
//	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
//	 */
//	@Override
//	public void selectAll(DrugWarehouseFar entity, Pager<DrugWarehouseFar> page)
//			throws Exception {
//		Map<String,Object> map=entity.getMap();
//		map.put("e.isDrug", "0");
////		//根据代养户查询对应公司的库存
////		if(entity.getFarmer()!=null && !"".equals(entity.getFarmer())){
////			Farmer f=farmerService.selectById(entity.getFarmer());
////			map.put("e.company", f.getCompany().getId());
////		}
//		super.selectAll(entity, page);
//	}
	/**
	 * 根据code加载药品
	 * @Description:TODO
	 * @param e
	 * @return
	 * @throws Exception
	 * List<Drug>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-10-18 上午09:23:20
	 */
	public List<DrugWarehouseFar> selectByCodes(DrugWarehouseFar entity)throws Exception{
		List<DrugWarehouseFar> drugs = new ArrayList<DrugWarehouseFar>();
		SqlMap<String,String,Object> sqlmap =new SqlMap<String,String,Object>();
		
		//sqlmap.put("farmer.id", "=", entity.getFarmer().getId());
		sqlmap.put("batch.id", "=", entity.getBatch().getId());
		if(entity.getDrug().getDrugType()!=null){
			sqlmap.put("drug.drugType", "=", entity.getDrug().getDrugType());
		}
		if(entity.getDrug().getName()!=null && !"".equals(entity.getDrug().getName())){
			sqlmap.put("drug.name", "=", entity.getDrug().getName());
		}
		if(entity.getDrug().getCode()!=null && !"".equals(entity.getDrug().getCode())){
			String codes = entity.getDrug().getCode().replaceAll(" ", "").replaceAll("，", ",").replaceAll("[',']+", ",").replaceAll("\\.",",");
			String[] strCode=codes.split(",");
			
			
			sqlmap.put("drug.code","in",PapUtil.arrayToSQLStr(strCode));
			
//			List<String> codeList = new ArrayList<String>();
//			for(String code : strCode){
//				if(!codeList.contains(code))
//					codeList.add(code);
//			}
//			
//			for(String code : codeList){
//				sqlmap.put("drug.code", "=", code);
//				List<DrugWarehouseFar> drugList = selectHQL(sqlmap);
//				sqlmap.remove("drug.code");
//				
//				drugs.addAll(drugList);
//			}
		}
		//排序
		sqlmap.put("drug.code","order by","asc");
		drugs = dao.selectHQLSingle(sqlmap);
		
		return drugs;
	}
}
