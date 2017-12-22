package com.zd.foster.sale.services.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.sale.dao.IOutPigstyDao;
import com.zd.foster.sale.entity.OutPigsty;
import com.zd.foster.sale.services.IFarmerSaleService;
import com.zd.foster.sale.services.IOutPigstyService;

/**
 * 类名：  OutPigstyServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class OutPigstyServiceImpl extends BaseServiceImpl<OutPigsty, IOutPigstyDao> implements IOutPigstyService {

	@Resource
	private IBatchService batchService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IFarmerSaleService farmerSaleService;
	
	/**
	 * 功能:分页查询
	 * 重写:DZL
	 * 2017-7-27
	 */
	@Override
	public void selectAll(OutPigsty entity, Pager<OutPigsty> page) throws Exception {
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
	 * 保存出栏单
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 22, 2013 10:36:54 AM<br/>
	 * @param entity 被保存的实体
	 * @return <br/>
	 * @see com.zhongpin.pap.services.IBaseService#save(com.zhongpin.pap.entity.BaseEntity)
	 */
	public Object save(OutPigsty entity) throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		//生猪头数，重量不能够为空或为0 金额不能为空
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("出栏头数不允许为空");
		if(ArithUtil.comparison(entity.getQuantity(), "0")<1)
			throw new SystemException("出栏头数不允许为0或负数");
		
		//验证销售总头数必须小于再养彼此当前生猪总头数
		//1.获取批次对象
		Batch batch = batchService.selectById(entity.getBatch().getId());
		Contract c = batch.getContract();
		String code ="";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code))
			throw new SystemException("批次【"+batch.getBatchNumber()+"】不是养殖状态，不允许出栏\n");
		if("Y".equals(batch.getIsBalance()))
			throw new SystemException("批次【"+batch.getBatchNumber()+"】已经结算，不允许出栏\n");
		if(batch == null || batch.getQuantity()==null || !PapUtil.checkDouble(batch.getQuantity()))
			throw new SystemException("批次或当前生猪头数信息不对");
		if(ArithUtil.comparison(batch.getQuantity(), entity.getQuantity())==-1)
			throw new SystemException("出栏头数大于批次当前生猪头数");
		//获取公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		//保存
		entity.setIsBalance("N");
		entity.setCheckStatus("0");
		Object obj = super.save(entity);
		return obj;
	}
	/**
	 * 修改
	 * DZL
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int updateHql(OutPigsty entity)throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		//生猪头数，重量不能够为空或为0 金额不能为空
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("出栏头数不允许为空");
		if(ArithUtil.comparison(entity.getQuantity(), "0")<1)
			throw new SystemException("出栏头数不允许为0或负数");
		
		//验证销售总头数必须小于再养彼此当前生猪总头数
		//1.获取批次对象
		Batch batch = batchService.selectById(entity.getBatch().getId());
		Contract c = batch.getContract();
		String code ="";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code))
			throw new SystemException("批次【"+batch.getBatchNumber()+"】不是养殖状态，不允许出栏\n");
		if("Y".equals(batch.getIsBalance()))
			throw new SystemException("批次【"+batch.getBatchNumber()+"】已经结算，不允许出栏\n");
		if(batch == null || batch.getQuantity()==null || !PapUtil.checkDouble(batch.getQuantity()))
			throw new SystemException("批次或当前生猪头数信息不对");
		if(ArithUtil.comparison(batch.getQuantity(), entity.getQuantity())==-1)
			throw new SystemException("出栏头数大于批次当前生猪头数");
		//获取公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		//修改表头
		OutPigsty op = super.selectById(entity.getId());
		op.setBatch(batch);
		op.setFarmer(farmer);
		op.setCompany(farmer.getCompany());
		op.setRegistDate(entity.getRegistDate());
		op.setQuantity(entity.getQuantity());
		op.setWeight(entity.getWeight());
		op.setSaleType(entity.getSaleType());
			
		return 1;
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		for(ID id : PK){
			int num = farmerSaleService.selectTotalRows("outPigsty.id", id);
			if(num>0)
				throw new SystemException("出栏单【"+id+"】对应的销售单未删除");
		}
		return super.deleteByIds(PK);
	}
	
	/**
	 * 功能：审核<br/>
	 *
	 * @author DZL 审核时 
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length==0)
			throw new SystemException("未发现要审核的单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		for(String s : idArr){
			OutPigsty cs = super.selectById(s);
			if(cs == null){
				sb.append("编号为【"+s+"】的出栏不存在\n");
				continue;
			}else{
				//验证合同状态以及批次状态
				Batch batch = cs.getBatch();
				Contract c = batch.getContract();
				String code ="";
				if(c != null && c.getStatus() != null)
					code = c.getStatus().getDcode();
				if(!"BREED".equals(code)){
					sb.append("批次【"+batch.getBatchNumber()+"】不是养殖状态，不允许审核\n");
					continue;
				}
				if("Y".equals(batch.getIsBalance())){
					sb.append("批次【"+batch.getBatchNumber()+"】生猪已经进行过结算\n");
					continue;
				}
				//验证养殖头数与销售头数
				String quan = batch.getQuantity();
				String outNum = cs.getQuantity();
				
				if(quan==null || outNum==null || !PapUtil.checkDouble(quan) || !PapUtil.checkDouble(outNum)){
					sb.append("批次【"+batch.getBatchNumber()+"】头数与出栏头数都必须是数值类型\n");
					continue;
				}
				if(ArithUtil.comparison(quan, outNum)==-1){
					sb.append("批次【"+batch.getBatchNumber()+"】出栏头数大于养殖头数\n");
					continue;
				}
				//跟新批次上当前头数
				batch.setQuantity(ArithUtil.sub(quan, outNum,0));
				//更新批次上销售头数
				String saleQuan = batch.getSaleQuan();
				if(saleQuan==null || !PapUtil.checkDouble(saleQuan))
					batch.setSaleQuan(outNum);
				else{
					batch.setSaleQuan(ArithUtil.add(outNum,saleQuan,0));
				}
				//判断如果是淘汰，需要跟新批次上淘汰头数
				if("E".equals(cs.getSaleType())){
					//跟新批次上淘汰猪头数
					if(batch.getEliminateQuan()==null || "".equals(batch.getEliminateQuan()) || ArithUtil.comparison(batch.getEliminateQuan(), "0")==0)
						batch.setEliminateQuan(outNum);
					else{
						if(!PapUtil.checkDouble(batch.getEliminateQuan())){
							sb.append("批次【"+batch.getBatchNumber()+"】当前淘汰头数非数值类型\n");
							continue;
						}else{
							batch.setEliminateQuan(ArithUtil.add(outNum,batch.getEliminateQuan(),0));
						}
					} 
				}
				
				//变更状态
				//改变审核状态
				cs.setCheckStatus("1");
				cs.setCheckUser(u.getUserRealName());
				cs.setCheckDate(PapUtil.date(new Date()));
			}
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
	}
	/**
	 * 功能：撤销<br/>
	 *
	 * @author DZL 撤销时需要验证 该批次生猪的公司结算是不是已经完成，已完成的不允许撤销
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void cancelCheck(String id)throws Exception{
		if(id==null || "".equals(id))
			throw new SystemException("未发现要撤销的出栏单据");
		OutPigsty f = super.selectById(id);
		if(f == null)
			throw new SystemException("编号为【"+id+"】的出栏单不存在");
		//验证单据自身已经结算不允许撤销
		if("Y".equals(f.getIsBalance()))
			throw new SystemException("编号为【"+id+"】的出栏单已经结算不允许撤销");
		//验证合同状态以及批次状态
		Batch batch = f.getBatch();
		Contract c = batch.getContract();
		String code ="";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code))
			throw new SystemException("该批次不是养殖状态，不允许撤销\n");
		if("Y".equals(batch.getIsBalance()))
			throw new SystemException("该批次生猪已经进行过结算\n");
		if(f.getFarmerSale() != null && !"".equals(f.getFarmerSale()))
			throw new SystemException("该出栏单已经生成销售单，请先撤销销售单\n");
		
		
		//更新批次当前头数 与销售当前头数
		String quan = batch.getQuantity();
		String saleNum = f.getQuantity();
		if(saleNum==null || "".equals(saleNum) || !PapUtil.checkDouble(saleNum))
			throw new SystemException("出栏头数必须是数值类型\n");
		//更新批次当前头数
		if(quan==null || !PapUtil.checkDouble(quan) || ArithUtil.comparison(quan, "0")==0)
			batch.setQuantity(saleNum);
		else
			batch.setQuantity(ArithUtil.add(quan, saleNum,0));
		
		//更新批次上销售头数
		String saleQuan = batch.getSaleQuan();
		if(saleQuan==null || !PapUtil.checkDouble(saleQuan))
			throw new SystemException("【"+batch.getBatchNumber()+"】出栏头数不能为空且必须为数值\n");
		else{
			//验证如果批次上的销售头数 比 销售单据上的头数少 报异常
			if(ArithUtil.comparison(saleQuan, saleNum)==-1)
				throw new SystemException("【"+batch.getBatchNumber()+"】出栏头数不能小于单据上头数\n");
			batch.setSaleQuan(ArithUtil.sub(saleQuan, saleNum,0));
		}
		//如果是淘汰生猪，需要还原淘汰生猪头数
		if("E".equals(f.getSaleType())){
			String ehead = batch.getEliminateQuan();
			if(ehead==null || !PapUtil.checkDouble(ehead))
				throw new SystemException("批次上当前淘汰猪头数为空或非数值");
			if(ArithUtil.comparison(ehead, saleNum)==-1)
				throw new SystemException("批次上当前淘汰猪头数小于出栏单淘汰头数");
			batch.setEliminateQuan(ArithUtil.sub(ehead, saleNum,0));
		}
		//变更状态
		//改变审核状态
		f.setCheckStatus("0");
		f.setCheckUser(null);
		f.setCheckDate(null);
	}
	
	/**
	 * 获取对象 按照没有设置
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public List<OutPigsty> selectEntity(OutPigsty entity)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("batch.id", "=", entity.getBatch().getId()+"");
		sqlMap.put("farmerSale", "IS NULL", "");
		sqlMap.put("checkStatus", "=", "1");
		
		List<OutPigsty> list = super.selectHQL(sqlMap);
		return list;
	}
	
	/**
	 * 功能：通过ID获取对象<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public OutPigsty selectId(String id)throws Exception{
		return super.selectById(id);
	}
}
