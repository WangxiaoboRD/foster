
package com.zd.foster.material.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseDaoImpl;
import com.zd.epa.utils.HqlModel;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.AbstractSqlMap.Condition;
import com.zd.foster.material.dao.IDrugDao;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.warehouse.entity.DrugWarehouse;

/**
 * 药品dao实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-26 上午09:28:49
 */
public class DrugDaoImpl extends BaseDaoImpl<Drug>implements IDrugDao {

	@Resource
	private HqlModel hqlModel;

	/**
	 * 带条件类型的查询采用HQL模式
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 11:54:57 AM <br/>
	 */
	public List<Drug> selectHQLSingle(SqlMap<String, String, Object> sqlMap)throws Exception  {
		// TODO Auto-generated method stub
		// String hql = "from "+ clazz.getName()+" e where 1=1";
		String _hql = hqlModel.buildClassSelectHql(Drug.class);
		String hql="";
		if (null != sqlMap) {
			List<Object> list = sqlMap.get();
			if (list != null && list.size() != 0) {
				for (Object _o : list) {
					Condition<String, String, Object> _c = (Condition) _o;
					String operator = (String) _c.getOperator();
					if (!"".equals(operator) && operator != null)
						operator = operator.replaceAll(" ", "");
					if ("IN".equalsIgnoreCase(operator) || "NOTIN".equalsIgnoreCase(operator))
						hql += " and e." + _c.getKey() + " " + _c.getOperator() + " (" + _c.getValue() + ")";
					else if ("BETWEEN".equalsIgnoreCase(operator)) {
						String[] value = (String[]) (_c.getValue());
						if (value != null && value.length > 1) {
							hql += " and e." + _c.getKey() + " " + _c.getOperator() + " '" + value[0] + "' and '" + value[1] + "'";
						}
					}else if ("ISNULL".equalsIgnoreCase(operator) || "ISNOTNULL".equalsIgnoreCase(operator))
						hql += " and e." + _c.getKey() + " " + _c.getOperator() + "";
					else if ("ISEMPTY".equalsIgnoreCase(operator) || "ISNOTEMPTY".equalsIgnoreCase(operator))
						hql += " and e." + _c.getKey() + " " + _c.getOperator() + "";
					else if ("ORDERBY".equalsIgnoreCase(operator)) {
						if(hql.contains("order by")){
							String _shql = hql.substring(0, hql.indexOf("order by")+8);
							String _ehql = hql.substring(hql.indexOf("order by")+8);
							hql = _shql +" to_number(regexp_substr(e."+_c.getKey()+",'[0-9]*[0-9]',1)) "+_c.getValue()+","+_ehql;
							
						}else
							hql += " order by "+" to_number(regexp_substr(e."+_c.getKey()+",'[0-9]*[0-9]',1)) "+_c.getValue();
					}else{
						String opera = (_c.getOperator()+"").trim();
						if(">".equals(opera) || "<".equals(opera) || ">=".equals(opera) || "<=".equals(opera) ){
							if(PapUtil.checkDouble(_c.getValue()+"") )
								hql += " and e." + _c.getKey() + _c.getOperator() + _c.getValue();
							else
								hql += " and e." + _c.getKey() + " " + _c.getOperator() + " '" + _c.getValue() + "'";
						}else
							hql += " and e." + _c.getKey() + " " + _c.getOperator() + " '" + _c.getValue() + "'";
					}
				}
			}
		}
		String ahql = _hql+hql;
		return selectAll(ahql);
	}

}
