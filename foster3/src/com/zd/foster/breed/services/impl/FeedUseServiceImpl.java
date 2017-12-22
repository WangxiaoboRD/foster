package com.zd.foster.breed.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.dao.IFeedUseDao;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.FeedBill;
import com.zd.foster.breed.entity.FeedBillDtl;
import com.zd.foster.breed.entity.FeedUse;
import com.zd.foster.breed.entity.FeedUseDetail;
import com.zd.foster.breed.services.IFeedBillDtlService;
import com.zd.foster.breed.services.IFeedBillService;
import com.zd.foster.breed.services.IFeedUseDetailService;
import com.zd.foster.breed.services.IFeedUseService;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.warehouse.services.IFeedWarehouseService;

/**
 * 类名：  FeedUseServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class FeedUseServiceImpl extends BaseServiceImpl<FeedUse, IFeedUseDao> implements IFeedUseService {

	@Resource
	private IFeedUseDetailService feedUseDetailService;
	@Resource
	private IFeedWarehouseService feedWarehouseService;
	@Resource
	private IFeedService feedService;
	@Resource
	private IFeedBillService feedBillService;
	@Resource
	private IFeedBillDtlService feedBillDtlService;
	
	
	@Override
	public void selectAll(FeedUse entity, Pager<FeedUse> page) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				sqlMap.put("registDate", ">=", startDate);
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				sqlMap.put("registDate", "<=", endDate);
			}
		}
		dao.selectByConditionHQL(entity, sqlMap, page);
	}
	/**
	 * 功能:保存录入单
	 * 重写:DZL
	 * 
	 */
	public Object save(FeedUse entity) throws Exception{
		
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		Company c = entity.getCompany();
		if(c == null || c.getId()==null || "".equals(c.getId()))
			throw new SystemException("公司不能为空");
		//2验证明细
		List<FeedUseDetail> fdList = entity.getDetails();
		//2.1明细是否为空
		if(fdList == null || fdList.isEmpty())
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		String hql = "select sum(e.quantity) from FeedWarehouse e where e.feed.feedClass.dcode='###' and e.batch.id='###'";
		Iterator<FeedUseDetail> iter = fdList.iterator();
		int i=0;
		while(iter.hasNext()){  //执行过程中会执行数据锁定，性能稍差，若在循环过程中要去掉某个元素只能调用iter.remove()方法。
			i++;
			FeedUseDetail fd = iter.next();
			
			//验证 1.如果所有数据都为空跳过，如果有值负数提出
			Double drb = fd.getDrb();
			Double rzb = fd.getRzb();
			Double zzb = fd.getZzb();
			Double _551 = fd.getFeedA();
			Double _552 = fd.getFeedB();
			Double _553 = fd.getFeedC();
			Double _554 = fd.getFeedD();
			int bid = 0;
			Batch batch = fd.getBatch();
			if(batch != null)
				bid = batch.getId();
			if((drb==null || drb==0) && (rzb==null || rzb==0) && (zzb==null || zzb==0) && 
			   (_551==null || _551==0) && (_552==null || _552==0) && (_553==null || _553==0) &&
			   (_554==null || _554==0)){
				iter.remove();
				continue;
			}
			//对每一种饲料进行验证
			if(drb != null ){
				if(drb < 0){
					buff.append("第"+i+"行代乳宝不能为负值\n\r");
					continue;
				}else if(drb >0){
					//验证库存
					vaild(hql,"DRB",bid,drb+"",buff,i);
				}
			}
			if(rzb != null){
				if(rzb < 0){
					buff.append("第"+i+"行乳猪宝不能为负值\n\r");
					continue;
				}else if(rzb >0){
					//验证库存
					vaild(hql,"RZB",bid,rzb+"",buff,i);
				}
			}
			if(zzb != null){
				if(zzb < 0){
					buff.append("第"+i+"行仔猪宝不能为负值\n\r");
					continue;
				}else if(zzb >0){
					//验证库存
					vaild(hql,"ZZB",bid,zzb+"",buff,i);
				}
			}
			if(_551 != null){
				if(_551 < 0){
					buff.append("第"+i+"行551不能为负值\n\r");
					continue;
				}else if(_551 >0){
					//验证库存
					vaild(hql,"551",bid,_551+"",buff,i);
				}
			}
			if(_552 != null){
				if(_552 < 0){
					buff.append("第"+i+"行552不能为负值\n\r");
					continue;
				}else if(_552 >0){
					//验证库存
					vaild(hql,"552",bid,_552+"",buff,i);
				}
			}
			if(_553 != null){
				if(_553 < 0){
					buff.append("第"+i+"行553不能为负值\n\r");
					continue;
				}else if(_553 >0){
					//验证库存
					vaild(hql,"553",bid,_553+"",buff,i);
				}
			}
			if(_554 != null){
				if(_554 < 0){
					buff.append("第"+i+"行554不能为负值\n\r");
					continue;
				}else if(_554 >0){
					//验证库存
					vaild(hql,"554",bid,_554+"",buff,i);
				}
			}
			
			fd.setFeedUse(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		Object obj = super.save(entity);
		feedUseDetailService.save(fdList);
		return obj;
	}
	
	/**
	 * 
	 * 功能:修改饲料录入
	 * 重写:DZL
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(FeedUse entity)throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//2验证明细
		List<FeedUseDetail> fdList = entity.getDetails();
		//2.1明细是否为空
		if(fdList == null || fdList.isEmpty())
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		String hql = "select sum(e.quantity) from FeedWarehouse e where e.feed.feedClass.dcode='###' and e.batch.id='###'";
		Iterator<FeedUseDetail> iter = fdList.iterator();
		
		int i=0;
		while(iter.hasNext()){  //执行过程中会执行数据锁定，性能稍差，若在循环过程中要去掉某个元素只能调用iter.remove()方法。
			i++;
			FeedUseDetail fd = iter.next();
			
			//验证 1.如果所有数据都为空跳过，如果有值负数提出
			Double drb = fd.getDrb();
			Double rzb = fd.getRzb();
			Double zzb = fd.getZzb();
			Double _551 = fd.getFeedA();
			Double _552 = fd.getFeedB();
			Double _553 = fd.getFeedC();
			Double _554 = fd.getFeedD();
			
			int bid = 0;
			Batch batch = fd.getBatch();
			if(batch != null)
				bid = batch.getId();
			
			if((drb==null || drb==0) && (rzb==null || rzb==0) && (zzb==null || zzb==0) && 
			   (_551==null || _551==0) && (_552==null || _552==0) && (_553==null || _553==0) &&
			   (_554==null || _554==0)){
				iter.remove();
				continue;
			}
			//对每一种饲料进行验证
			if(drb != null ){
				if(drb < 0){
					buff.append("第"+i+"行代乳宝不能为负值\n\r");
					continue;
				}else if(drb >0){
					//验证库存
					vaild(hql,"DRB",bid,drb+"",buff,i);
				}
			}
			if(rzb != null){
				if(rzb < 0){
					buff.append("第"+i+"行乳猪宝不能为负值\n\r");
					continue;
				}else if(rzb >0){
					//验证库存
					vaild(hql,"RZB",bid,rzb+"",buff,i);
				}
			}
			if(zzb != null){
				if(zzb < 0){
					buff.append("第"+i+"行仔猪宝不能为负值\n\r");
					continue;
				}else if(zzb >0){
					//验证库存
					vaild(hql,"ZZB",bid,zzb+"",buff,i);
				}
			}
			if(_551 != null){
				if(_551 < 0){
					buff.append("第"+i+"行551不能为负值\n\r");
					continue;
				}else if(_551 >0){
					//验证库存
					vaild(hql,"551",bid,_551+"",buff,i);
				}
			}
			if(_552 != null){
				if(_552 < 0){
					buff.append("第"+i+"行552不能为负值\n\r");
					continue;
				}else if(_552 >0){
					//验证库存
					vaild(hql,"552",bid,_552+"",buff,i);
				}
			}
			if(_553 != null){
				if(_553 < 0){
					buff.append("第"+i+"行553不能为负值\n\r");
					continue;
				}else if(_553 >0){
					//验证库存
					vaild(hql,"553",bid,_553+"",buff,i);
				}
			}
			if(_554 != null){
				if(_554 < 0){
					buff.append("第"+i+"行554不能为负值\n\r");
					continue;
				}else if(_554 >0){
					//验证库存
					vaild(hql,"554",bid,_554+"",buff,i);
				}
			}
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		FeedUse e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setRegistDate(entity.getRegistDate());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					feedUseDetailService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<FeedUseDetail> updateSwd = new ArrayList<FeedUseDetail>(); 
		List<FeedUseDetail> newSwd = new ArrayList<FeedUseDetail>(); 
		for(FeedUseDetail p : fdList){
			if(p.getId()==null){
				p.setFeedUse(e);
				newSwd.add(p);
			}else{
				updateSwd.add(p);
			}
		}
		//修改	
		for(FeedUseDetail p : updateSwd){
			FeedUseDetail d = feedUseDetailService.selectById(p.getId());
			d.setDrb(p.getDrb());
			d.setRzb(p.getRzb());
			d.setZzb(p.getZzb());
			d.setFeedA(p.getFeedA());
			d.setFeedB(p.getFeedB());
			d.setFeedC(p.getFeedC());
			d.setFeedD(p.getFeedD());
		}
		//添加
		feedUseDetailService.save(newSwd);
	}
	
	//验证库存
	private void vaild(String hql,String feedType,int batchId,String quantity,StringBuffer sb,int num)throws Exception{
		String _hql = hql.replaceFirst("###", feedType).replaceFirst("###", batchId+"");
		String all= (String)feedWarehouseService.selectByHQLFun(_hql);
		if(all == null || "".equals(all))
			sb.append("第"+num+"行"+feedType+"库存不足\n\r");
		else if(ArithUtil.comparison(all, quantity) == -1)
			sb.append("第"+num+"行"+feedType+"库存不足\n\r");
	}
	
	//验证库存
	private boolean vailds(String hql,String feedType,int batchId,String quantity)throws Exception{
		String _hql = hql.replaceFirst("###", feedType).replaceFirst("###", batchId+"");
		String all= (String)feedWarehouseService.selectByHQLFun(_hql);
		if(all == null || "".equals(all)){
			return false;
		}else if(ArithUtil.comparison(all, quantity) == -1){
			return false;
		}
		return true;
	}
	
	//获取单重饲料的数量
	private Object[] getValue(String hql,String feedType,int batchId,String pack)throws Exception{
		String _hql = hql.replaceFirst("###", feedType).replaceFirst("###", batchId+"").replaceFirst("###", pack);
		Object obj = feedWarehouseService.selectByHQLFun(_hql);
		//转化为数组
		if(obj == null)
			return null;
		Object[] ob = (Object[])obj;
		return ob;
	}
	
	//创建耗料单
	private void buildList(String id,Company c, String hql,String hqlnum,FeedBill fb,List<FeedBillDtl> fudlist,Batch b,String feedType,double inData,String feedName,StringBuffer sb)throws Exception{
		String farmerName=b.getFarmer().getName();
		boolean bs = vailds(hql,feedType,b.getId(),inData+"");
		if(!bs){
			sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"】饲料"+feedName+"库存不足<br>");
			return;
		}
		//生成明细
		//1.先验证散装料是不是够，不够的话用袋装料
		Object[] ob = getValue(hqlnum,feedType,b.getId(),"1");
		double vs = 0;
		Feed feed = null;
		if(ob != null){
			if(ob[0] != null){
				vs = Double.parseDouble(ob[0].toString());
				if(ob[1] != null)
					feed = (Feed)ob[1];
			}
		}
		if(vs > 0){
			//用库存数-录入数
			double cy = vs - inData;
			if(cy>=0){
				//创建明细
				FeedBillDtl fbd = new FeedBillDtl();
				fbd.setFeedBill(fb);
				fbd.setFeed(feed);
				fbd.setQuantity(inData+"");
				//获取换算比例
				if(feed == null){
					sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"批】"+feedName+"散装饲料对象找不到<br>");
					return;
				}
				String rio = feed.getRatio();	
				if(rio == null){
					sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"批】"+feedName+"散装饲料对象主副单位换算比例找不到<br>");
					return;
				}
				fbd.setSubQuantity(ArithUtil.div(inData+"", rio,2));
				
				fudlist.add(fbd);
			}else{
				//创建cy的袋装
				//1.1先生成散装料
				FeedBillDtl fbd = new FeedBillDtl();
				fbd.setFeedBill(fb);
				fbd.setFeed(feed);
				fbd.setQuantity(vs+"");
				//获取换算比例
				if(feed == null){
					sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"批】"+feedName+"散装饲料对象找不到<br>");
					return;
				}
				String rio = feed.getRatio();	
				if(rio == null){
					sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"批】"+feedName+"散装饲料对象主副单位换算比例找不到<br>");
					return;
				}
				fbd.setSubQuantity(ArithUtil.div(vs+"", rio,2));
				fudlist.add(fbd);
				
				//1.2在生成袋装料
				//获取袋装料的对象
				Feed f = feedService.selectByHQLSingle("from Feed e where e.feedClass.dcode='"+feedType+"' and e.company.id='"+c.getId()+"' and e.packForm='2'");
				FeedBillDtl fbd1 = new FeedBillDtl();
				fbd1.setFeedBill(fb);
				fbd1.setFeed(f);
				fbd1.setQuantity(-cy+"");
				//获取换算比例
				if(f == null){
					sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"批】"+feedName+"散装饲料对象找不到<br>");
					return;
				}
				String rio_ = f.getRatio();	
				if(rio_ == null){
					sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"批】"+feedName+"散装饲料对象主副单位换算比例找不到<br>");
					return;
				}
				fbd1.setSubQuantity(ArithUtil.div(-cy+"", rio_,2));
				fudlist.add(fbd1);
			}
		}else{
			//全部创建为袋装
			//1.2在生成袋装料
			//获取袋装料的对象
			Feed f = feedService.selectByHQLSingle("from Feed e where e.feedClass.dcode='"+feedType+"' and e.company.id='"+c.getId()+"' and e.packForm='2'");
			FeedBillDtl fbd = new FeedBillDtl();
			fbd.setFeedBill(fb);
			fbd.setFeed(f);
			fbd.setQuantity(inData+"");
			//获取换算比例
			if(f == null){
				sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"批】"+feedName+"散装饲料对象找不到<br>");
				return;
			}
			String rio_ = f.getRatio();	
			if(rio_ == null){
				sb.append("单据"+id+"【"+farmerName+"第"+b.getBatchNumber()+"批】"+feedName+"散装饲料对象主副单位换算比例找不到<br>");
				return;
			}
			fbd.setSubQuantity(ArithUtil.div(inData+"", rio_,2));
			fudlist.add(fbd);
		}
	}
	
	/**
	 * 
	 * 功能:审核
	 * @author:DZL
	 * @data:2017-7-31下午04:43:02
	 * @file:FeedBillServiceImpl.java
	 * @param idArr
	 * @throws Exception
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		String hql = "select sum(e.quantity) from FeedWarehouse e where e.feed.feedClass.dcode='###' and e.batch.id='###'";
		String hqlsum = "select e.quantity,e.feed from FeedWarehouse e where e.feed.feedClass.dcode='###' and e.batch.id='###' and e.feed.packForm='###'";
		
		
		List<FeedBill> flist = new ArrayList<FeedBill>();
		List<FeedBillDtl> fudlist = new ArrayList<FeedBillDtl>();
		
		for(String s : idArr){
			//1.获取对象
			FeedUse e = super.selectById(Integer.parseInt(s));
			Company c = e.getCompany();
			//2.获取明细并遍历明细
			List<FeedUseDetail> fdList = feedUseDetailService.selectBySingletAll("feedUse.id",Integer.parseInt(s));
			if(e==null ||fdList==null || fdList.isEmpty())
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
			//2.**建立饲料出库单表头
			for(FeedUseDetail fd : fdList){
				Batch b = fd.getBatch();
				//验证库存
				//验证 1.如果所有数据都为空跳过，如果有值负数提出
				Double drb = fd.getDrb();
				Double rzb = fd.getRzb();
				Double zzb = fd.getZzb();
				Double _551 = fd.getFeedA();
				Double _552 = fd.getFeedB();
				Double _553 = fd.getFeedC();
				Double _554 = fd.getFeedD();
				
				//生成喂料单
				FeedBill fb = new FeedBill();
				fb.setBatch(b);
				fb.setRegistDate(e.getRegistDate());
				fb.setFarmer(b.getFarmer());
				fb.setCompany(e.getCompany());
				fb.setCheckUser(u.getUserRealName());
				fb.setCheckDate(PapUtil.date(new Date()));
				fb.setCheckStatus("1");
				fb.setIsBalance("N");
				fb.setFeedUseId(e.getId()+"");
				
				flist.add(fb);
				//对每一种饲料进行验证 并自动分配生成喂料单
				if(drb != null && drb != 0){
					buildList(s,c,hql,hqlsum,fb,fudlist,b,"DRB",drb,"代乳宝",sb);
				}
				if(rzb != null && rzb != 0){
					buildList(s,c,hql,hqlsum,fb,fudlist,b,"RZB",rzb,"乳猪宝",sb);
				}
				if(zzb != null && zzb != 0){
					buildList(s,c,hql,hqlsum,fb,fudlist,b,"ZZB",zzb,"仔猪宝",sb);
				}
				if(_551 != null && _551 != 0) {
					buildList(s,c,hql,hqlsum,fb,fudlist,b,"551",_551,"551",sb);
				}
				if(_552 != null && _552 != 0){
					buildList(s,c,hql,hqlsum,fb,fudlist,b,"552",_552,"552",sb);
				}
				if(_553 != null && _553 != 0){
					buildList(s,c,hql,hqlsum,fb,fudlist,b,"553",_553,"553",sb);
				}
				if(_554 != null && _554 != 0){
					buildList(s,c,hql,hqlsum,fb,fudlist,b,"554",_554,"554",sb);
				}
			}
			
			//审核 
			e.setCheckDate(PapUtil.date(new Date()));
			e.setCheckStatus("1");
			e.setCheckUser(u.getUserRealName());
			
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		if(flist != null && !flist.isEmpty()){
			if(fudlist == null || fudlist.isEmpty())
				throw new SystemException("明细信息不能为空");
			//保存对象
			String[] str = new String[flist.size()];
			for(int i=0;i<flist.size();i++){
				FeedBill _f = flist.get(i);
				String id = (String)feedBillService.saveHead(_f);
				str[i]= id;
			}
			//保存明细
			feedBillDtlService.save(fudlist);
			//审核喂料单
			feedBillService.check(str);
		}
	}
	
	/**
	 * 
	 * 功能:撤销
	 * 重写:DZL
	 * 2017-7-31
	 * @see com.zd.foster.breed.services.IFeedBillService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		//1.获取对象
		FeedUse e = super.selectById(Integer.parseInt(id));
		//验证是不是已经结算
		List<FeedUseDetail> fdList = feedUseDetailService.selectBySingletAll("feedUse.id", Integer.parseInt(id));
		if(e==null ||fdList==null || fdList.isEmpty())
			throw new SystemException("单据【"+id+"】对象为空或明细不存在<br>");
		//获取所有这个单据生成的喂料单
		List<FeedBill> fblist = feedBillService.selectBySingletAll("feedUseId", e.getId()+"");
		if(fblist != null && !fblist.isEmpty()){
			//撤销单据
			String[] str = new String[fblist.size()];
			for(int i=0;i<fblist.size();i++){
				FeedBill _f = fblist.get(i);
				feedBillService.cancelCheck(_f.getId());
				str[i]=_f.getId();
			}
			//删除单据
			feedBillService.deleteByIds(str);
		}
		//撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}

}
