/**
 * 功能:
 * @author:wxb
 * @data:2017-9-7下午01:36:58
 * @file:DeathReasonServiceImpl.java
 */
package com.zd.foster.base.services.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.IDeathReasonDao;
import com.zd.foster.base.entity.DeathReason;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.Variety;
import com.zd.foster.base.services.IDeathReasonService;
import com.zd.foster.breed.services.IDeathBillDtlService;
import com.zd.foster.utils.FosterUtil;

/**
 * 类名：  DeathReasonServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-9-7下午01:36:58
 * @version 1.0
 * 
 */
public class DeathReasonServiceImpl extends BaseServiceImpl<DeathReason, IDeathReasonDao> implements
		IDeathReasonService {
	@Resource
	private IDeathBillDtlService deathBillDtlService;
	@Resource
	private JDBCWrapperx jdbc;
	/**
	 * 
	 * 功能:保存
	 * 重写:wxb
	 * 2017-9-7
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(DeathReason entity) throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity==null)
			throw new SystemException("对象不允许为空");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
		else{
			sqlMap.put("name", "=", entity.getName());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("名称不允许重复");
		}
		
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			sqlMap.put("code", "=", entity.getCode());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		//保存对象
		return dao.insert(entity);
	}

	/**
	 * 
	 * 功能:修改
	 * 重写:wxb
	 * 2017-9-7
	 * @see com.zd.epa.base.BaseServiceImpl#updateHql(com.zd.epa.base.BaseEntity)
	 */
	public int updateHql(DeathReason entity)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity == null)
			throw new SystemException("对象不允许为空");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
		else{
			sqlMap.put("name", "=", entity.getName());
			sqlMap.put("id", "<>", entity.getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("名称不允许重复");
		}
		
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			sqlMap.put("code", "=", entity.getCode());
			sqlMap.put("id", "<>", entity.getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		return super.updateHql(entity);
	}
	/**
	 * 
	 * 功能:删除
	 * 重写:wxb
	 * 2017-9-7
	 * @see com.zd.epa.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//删除死亡原因：验证使用死亡原因的地方：死亡单
			int cnNum = deathBillDtlService.selectTotalRows("reason",id);
			if(cnNum>0)
				throw new SystemException("此死亡原因正在被使用，不允许删除");
		}
		return dao.deleteByIds(PK);
	}

	/**
	 * 
	 * 功能:按拼音查询
	 * 重写:wxb
	 * 2017-9-7
	 * @see com.zd.foster.base.services.IDeathReasonService#selectByPinyin(com.zd.foster.base.entity.DeathReason, java.lang.String)
	 */
	@Override
	public List<DeathReason> selectByPinyin(DeathReason e, String key)
			throws Exception {
//		SqlMap<String,String,Object> sqlMap=new SqlMap<String,String,Object>();
		List<DeathReason> blist=new ArrayList<DeathReason>();
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		String sql="select t.name,t.id from FS_DEATHREASON t where t.name like '%"+key+"%'";
		String sql2="select t.name,t.id from FS_DEATHREASON t where t.code like '%"+key+"%'";
		if(key == null || "".equals(key))
			blist = super.selectAll();
		else{
			try{
				conn=jdbc.getConnection();
				st=conn.createStatement();
				rs=st.executeQuery(sql);
				while(rs.next()){
					DeathReason dr=new DeathReason();
					dr.setName(rs.getString(1));
					dr.setId(rs.getInt(2));
					blist.add(dr);
				}
				rs=st.executeQuery(sql2);
				while(rs.next()){
					DeathReason dr=new DeathReason();
					dr.setName(rs.getString(1));
					dr.setId(rs.getInt(2));
					blist.add(dr);
				}
			}finally{
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
				jdbc.destroy(conn);
			}
		}
		return blist;
	}
}
