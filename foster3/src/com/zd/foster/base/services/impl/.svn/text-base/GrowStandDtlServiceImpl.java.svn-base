
package com.zd.foster.base.services.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.foster.base.dao.IGrowStandDtlDao;
import com.zd.foster.base.entity.GrowStandDtl;
import com.zd.foster.base.services.IGrowStandDtlService;

/**
 * 生长标准明细服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-21 上午11:50:44
 */
public class GrowStandDtlServiceImpl extends BaseServiceImpl<GrowStandDtl, IGrowStandDtlDao> implements IGrowStandDtlService {

	@Override
	public List<GrowStandDtl> selectAll(GrowStandDtl entity) throws Exception {
		List<GrowStandDtl> gsdList=super.selectAll(entity);
		if(gsdList!=null && !gsdList.isEmpty())
			Collections.sort(gsdList, new Comparator<GrowStandDtl>() {
				@Override
				public int compare(GrowStandDtl o1, GrowStandDtl o2) {
					if(Integer.valueOf(o1.getDays())<Integer.valueOf(o2.getDays()))
						return 0;
					else
						return 1;
				}
			});

		return gsdList;
	}
	

	
	
}
