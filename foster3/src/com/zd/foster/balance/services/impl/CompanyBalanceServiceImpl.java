
package com.zd.foster.balance.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.balance.dao.ICompanyBalanceDao;
import com.zd.foster.balance.entity.CompanyBalance;
import com.zd.foster.balance.services.ICompanyBalanceService;
import com.zd.foster.balance.services.ICompanyBillService;
import com.zd.foster.balance.services.ICompanyDrugCostService;
import com.zd.foster.balance.services.ICompanyFeedCostService;
import com.zd.foster.balance.services.ICompanyFreightService;
import com.zd.foster.balance.services.ICompanyOtherCostService;
import com.zd.foster.balance.services.ICompanyPigletCostService;
import com.zd.foster.balance.services.ICompanySaleIncomeService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.sale.services.ICompanySaleService;
import com.zd.foster.warehouse.services.IFeedInWareService;

/**
 * 养殖公司结算单业务层
 * @author:小丁
 * @time:2017-8-7 上午10:07:24
 */
public class CompanyBalanceServiceImpl extends BaseServiceImpl<CompanyBalance, ICompanyBalanceDao> implements ICompanyBalanceService {

	@Resource
	private IBatchService batchService;
	@Resource
	private JDBCWrapperx jdbc;
	@Resource
	private IFeedInWareService feedInWareService;
	@Resource
	private ICompanySaleService companySaleService;
	@Resource
	private ICompanyBillService companyBillService;
	@Resource
	private ICompanyDrugCostService companyDrugCostService;
	@Resource
	private ICompanyFeedCostService companyFeedCostService;
	@Resource
	private ICompanyFreightService companyFreightService;
	@Resource
	private ICompanyOtherCostService companyOtherCostService;
	@Resource
	private ICompanyPigletCostService companyPigletCostService;
	@Resource
	private ICompanySaleIncomeService companySaleIncomeService;
	@Resource
	private IFarmerService farmerService;

	/**
	 * 功能:分页查询
	 * 重写:DZL
	 * 2017-7-27
	 */
	@Override
	public void selectAll(CompanyBalance entity, Pager<CompanyBalance> page) throws Exception {
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
	 * 保存公司销售单
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 22, 2013 10:36:54 AM<br/>
	 * @param entity 被保存的实体
	 * @return <br/>
	 * @see com.zhongpin.pap.services.IBaseService#save(com.zhongpin.pap.entity.BaseEntity)
	 */
	public Object save(CompanyBalance entity) throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		//验证日期不能大于当前日期
		if(PapUtil.compareDate(entity.getRegistDate(), PapUtil.notFullDate(new Date()))==-1)
			throw new SystemException("创建日期不能大于当前日期");
		
		//获取批次对象
		Batch batch = batchService.selectById(entity.getBatch().getId());
		//验证一个批次只能有一个结算单
		int num = super.selectTotalRows("batch.id", batch.getId());
		if(num>0)
			throw new SystemException("该批次对应的结算单已经创建");
		//验证只有合同时完结状态，才能够创建
		//获取合同
		Contract c = batch.getContract();
		String code = "";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"LOST".equals(code))
			throw new SystemException("该批次对应的合同未终止");
		//保存
		entity.setCompany(batch.getCompany());
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
	public int updateHql(CompanyBalance entity)throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		
		//验证日期不能大于当前日期
		if(PapUtil.compareDate(entity.getRegistDate(), PapUtil.notFullDate(new Date()))==-1)
			throw new SystemException("创建日期不能大于当前日期");
		
		//获取批次对象
		Batch batch = batchService.selectById(entity.getBatch().getId());
		//验证一个批次只能有一个结算单
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("batch.id", "=", batch.getId()+"");
		sqlMap.put("id", "<>", entity.getId());
		int num = super.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(num>0)
			throw new SystemException("该批次对应的结算单已经创建");
		//验证只有合同时完结状态，才能够创建
		//获取合同
		Contract c = batch.getContract();
		String code = "";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"LOST".equals(code))
			throw new SystemException("该批次对应的合同未终止");
		//更新
		CompanyBalance f = super.selectById(entity.getId());
		
		f.setBatch(batch);
		f.setFarmer(entity.getFarmer());
		f.setCompany(batch.getCompany());
		f.setRegistDate(entity.getRegistDate());
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
		return super.deleteByIds(PK);
	}
	
	/**
	 * 功能：审核<br/>
	 *
	 * @author DZL 
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void balance(String id)throws Exception{
		
		String farmerbalanceSql = "select t.batch,t.farmer,t.company,t.registdate,t.checkstatus from fs_companybalance t where t.id='"+id+"'";
		Connection connection = null;
		try{
			connection=jdbc.getConnection();
			Object[][] objs = jdbc.Querry(farmerbalanceSql, connection);
			if(objs.length>0){
				String uName = SysContainer.get().getUserRealName();
				String batch = objs[0][0]==null?null:objs[0][0].toString();
				String farmer = objs[0][1]==null?null:objs[0][1].toString();
				String company = objs[0][2]==null?null:objs[0][2].toString();
				String registdate = objs[0][3]==null?null:objs[0][3].toString();
				String checkstatus = objs[0][4]==null?null:objs[0][4].toString();
				if("1".equals(checkstatus))
					throw new SystemException("该单据已经结算完成");
				if(batch==null || "".equals(batch))
					throw new SystemException("批次为空");
				//通过批次获取合同
				String sql = "select t.contract from fs_batch t where t.id='"+batch+"'";
				Object[][] cobj = jdbc.Querry(sql, connection);
				String contractId = "";
				if(cobj!=null && cobj.length>0)
					contractId = cobj[0][0]==null?"":cobj[0][0].toString();
				if("".equals(contractId))
					throw new SystemException("批次【"+batch+"】找不到对应的合同");
				
				//验证合同是不是已经终止
				String isbalanceSql = "select t.endDate,t.feedfac from fs_contract t where t.id ='"+contractId+"' and t.company='"+company+"' and t.farmer='"+farmer+"' and t.status='LOST'";
				Object[][] isBalanceObj = jdbc.Querry(isbalanceSql, connection);
				String endDate = "";
				String feedFrc="";
				if(isBalanceObj.length>0){
					endDate = isBalanceObj[0][0]==null?"":isBalanceObj[0][0].toString();
					feedFrc = isBalanceObj[0][1]==null?"":isBalanceObj[0][1].toString();
				}
				if("".equals(endDate))
					throw new SystemException("该批次合同未终止，不允许结算");
				
				//验证该批次代养户结算是不是已经完成
				String isfarmerSql = "select count(1) from fs_farmerbalance t where t.batch='"+batch+"' and t.checkstatus='1'";
				Object[][] isfarmerObj = jdbc.Querry(isfarmerSql, connection);
				int _v = Integer.parseInt(isfarmerObj[0][0].toString());
				if(_v==0)
					throw new SystemException("该批次的代养结算未完成");
				//通过batch获取出栏总头数
				String batchSql = "select t.salequan from fs_batch t where t.id='"+batch+"'"; 
				Object[][] _batchObj = jdbc.Querry(batchSql, connection);
				String saleHead="";
				if(_batchObj != null && _batchObj.length>0){
					saleHead = _batchObj[0][0]==null?"":_batchObj[0][0].toString();
				}
				if(saleHead == null || !PapUtil.checkDouble(saleHead))
					throw new SystemException("未获取销售头数");
				
				//获取代养费
				String farmerCostSql = "select t.farmercost from fs_farmerbill t where t.batch='"+batch+"'";
				Object[][] _objcost = jdbc.Querry(farmerCostSql, connection);
				String farmerCost = null;
				if(_objcost.length>0)
					farmerCost = _objcost[0][0]==null?null:_objcost[0][0].toString();
				if(farmerCost==null || "".equals(farmerCost))
					throw new SystemException("该批次代养费不存在");
				
				//开始计算业务
				jdbc.beginTransaction(connection);//开启事务
				/**---------------------------1.锁定单据-饲料入库单-养殖场销售单---------------------*/
				lockBill(connection,batch);
				/**---------------------------2.计算销售费用-----------------------*/
				String[] saleCost = balanceSaleCost(connection, batch, uName);
				/**---------------------------3.计算饲料费用-----------------------*/
				String feedCost = balanceFeedCost(connection, batch,company,feedFrc, uName);
				/**---------------------------4.计算药品费用-----------------------*/
				String drugCost = balanceDrugsCost(connection,batch,company,uName);
				/**---------------------------5.计算其猪苗费用-----------------------*/
				String pigletCost = balancePigletCost(connection,batch,uName);
				/**---------------------------6.计算其他物料费用-----------------------*/
				String otherCost = balanceOtherCost(connection,batch,endDate,company,uName);
				/**---------------------------7.计算运费费用-----------------------*/
				String[] yunFeiCost = balanceYunFeiCost(connection,batch,company,farmer,feedFrc,uName);
				/**---------------------------8.构建表头-----------------------*/
				//1.计算投入总费用 feedCost+drugCost+pigletCost+otherCost+yunFeiCost+farmerCost
				String inputCost = ArithUtil.add(feedCost, drugCost);
				inputCost = ArithUtil.add(inputCost, pigletCost);
				inputCost = ArithUtil.add(inputCost, otherCost);
				inputCost = ArithUtil.add(inputCost, yunFeiCost[0]);
				inputCost = ArithUtil.add(inputCost, farmerCost);
				//2.计算毛利 saleCost - inputCost-销售运费yunFeiCost[1]
				String profit = ArithUtil.sub(saleCost[0], inputCost);
				profit = ArithUtil.sub(profit, yunFeiCost[1]);
				//头均利润
				String avgProfit="";
				//头均成本
				String avgCost="";
				if(ArithUtil.comparison(saleHead, "0")!=0){
					avgProfit = ArithUtil.div(profit, saleCost[1],2);
					avgCost = ArithUtil.div(inputCost, saleHead,2);
				}
				
				//构建插入语句
				String in_sql = "INSERT INTO FS_COMPANYBILL(ID,REGISTDATE,BATCH,FARMER,COMPANY,SALEINCOME,FEEDCOST,DRUGSCOST,PIGLETCOST,OTHERCOST,FREIGHT,FARMERCOST,PROFIT,AVGPROFIT,AVGCOST,ALLCOST,SALEFREIGHT,CREATEUSER,CREATEDATE) "+
								"VALUES(FS_COMPANYBILL_SEQUENCE.NEXTVAL,'"+registdate+"','"+batch+"','"+farmer+"','"+company+"','"+saleCost[0]+"','"+feedCost+"','"+drugCost+"','"+pigletCost+"','"+otherCost+"','"+yunFeiCost[0]+"','"+farmerCost+"','"+profit+"','"+avgProfit+"','"+avgCost+"','"+inputCost+"','"+yunFeiCost[1]+"','"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
				//更新月结信息 
				String balance_sql = "UPDATE FS_COMPANYBALANCE T SET T.CHECKUSER='"+uName+"',T.CHECKDATE='"+PapUtil.date(new Date())+"',T.CHECKSTATUS='1' WHERE ID = '"+id+"'"; 
				//执行sql
				jdbc.insert(in_sql, connection);
				jdbc.update(balance_sql, connection);
				jdbc.commitTransaction(connection);
			}else
				throw new SystemException("未找到该单据");
		}finally{
			jdbc.destroy(connection);
		}
	}
	
	//锁定所有单据
	private void lockBill(Connection connection,String batch)throws Exception{
		String feedinwaresql = "update fs_feedinware t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		String corsalebillsql = "update fs_corsalebill t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		//执行sql
		jdbc.update(feedinwaresql, connection);
		jdbc.update(corsalebillsql, connection);
	}
	//计算销售费用
	private String[] balanceSaleCost(Connection connection,String batch,String uName)throws Exception{
		
		String[] str = new String[2];
		//查询销售单
		String sql = "select t.id, t.amount,t.weight,t.quantity from fs_corsalebill t where t.batch='"+batch+"' and t.checkstatus='1'";
		String allMany="0";
		String allHead="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_COMPANYSALEINCOME(ID,COMPANYSALE,PRICE,BATCH,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_COMPANYBALANCE_SEQ.NEXTVAL,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			Object[][] obj = jdbc.Querry(sql, connection);
			if(obj.length>0){
				 ps =jdbc.createPreparedStmt(insert_sql,connection);
				for(int i=0;i<obj.length;i++){
					String id = obj[i][0]==null?null:obj[i][0].toString();
					String money = obj[i][1]==null?"0":obj[i][1].toString();
					String weight = obj[i][2]==null?"0":obj[i][2].toString();
					String quantity = obj[i][3]==null?"0":obj[i][3].toString();
					
					//计算单件
					String price="0";
					if(ArithUtil.comparison(weight, "0") != 0){
						price = ArithUtil.div(money, weight,2);
					}else{
						sb.append("销售单【"+id+"】重量数值不对");
						continue;
					}
					//将总金额相加
					allMany = ArithUtil.add(allMany, money);
					allHead = ArithUtil.add(allHead, quantity);
					//保存对象
					ps.setString(1,id);//销售单
					ps.setString(2,price);//单价
					ps.setString(3,batch);//批次
					
					ps.addBatch();
				}
				if(sb.length()>0)
					throw new SystemException(sb.toString());
				//执行sql
				ps.executeBatch();
			}else
				throw new SystemException("该批次生猪没有销售信息");
			str[0]=allMany;
			str[1]=allHead;
		}finally{
			if(ps != null)
				ps.close();
			ps=null;
		}
		return str;
	}
	//计算饲料费用
	private String balanceFeedCost(Connection connection,String batch,String company,String feedFac,String uName)throws Exception{
		
		//查找入库单
		String dtlSql="select f.id, t.feed, sum(t.quantity), f.registdate "+
					  "from fs_feedinwaredtl t "+
					  "join fs_feedinware f on (f.id = t.feedinware) "+
					 "where f.batch = '"+batch+"' "+
					   "and f.checkstatus = '1' "+
					   "group by f.id, t.feed,f.registdate";
		
		//查找饲料转接单
		String trnSql = "select t.feedtransfer, t.feed, sum(t.quantity), f.registdate "+
					   "from fs_feedtransferdtl t "+
					   "join fs_feedtransfer f on (f.id = t.feedtransfer) "+
					   "where f.outbatch = '"+batch+"' "+
					   "and f.checkstatus = '1' "+
					   "group by t.feedtransfer, t.feed, f.registdate";
		
		//获取最后一次饲料入库时间
		String maxDateSql= "select max(f.registdate) from fs_feedinwaredtl t left join fs_feedinware f on(f.id=t.feedinware) where f.batch='"+batch+"' and f.checkstatus='1' and f.registdate<='###' and t.feed='###' and t.quantity>0";
		
		//查找定价单
		String priceSql="select f.id,t.price "+
						  "from fs_feedpricedtl t "+
						  "join fs_feedprice f on (f.id = t.feedprice) "+
						 "where t.feed='###' "+
						   "and f.company = '"+company+"' "+
						   "and f.feedfac='"+feedFac+"' "+
						   "and f.checkstatus = '1' "+
						   "and f.startdate <= '###' "+
						 "order by f.startdate desc";
		
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_COMPANYFEEDCOST(ID,FEEDINWARE,FEED,QUANTITY,PRICE,MONEY,BATCH,FEEDPRICE,BILLTYPE,REGISTDATE,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_COMPANYBALANCE_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			Object[][] obj = jdbc.Querry(dtlSql, connection);
			if(obj.length>0){
				ps =jdbc.createPreparedStmt(insert_sql,connection);
				for(int i=0;i<obj.length;i++){
					String orderId = obj[i][0]==null?null:obj[i][0].toString();
					String feed = obj[i][1]==null?null:obj[i][1].toString();
					String quantity = obj[i][2]==null?"0":obj[i][2].toString();
					String registdate = obj[i][3]==null?null:obj[i][3].toString();
					
					if(feed==null){
						sb.append("入库单【"+orderId+"】有饲料编码为空");
						continue;
					}
					if(registdate==null){
						sb.append("入库单【"+orderId+"】入库日期为空");
						continue;
					}
					//查找该饲料的定价
					//判断如果数量是正值，说明是正常的领用，饲料价格查找最接近领料时间的定价 
					//如果是负值，说明是退货，价格需要按照退货单时间最接近的领料单，并以领药单时间查询最接近领料单的定价单
					if(ArithUtil.comparison(quantity, "0")<0){
						String _maxDateSql = maxDateSql.replaceFirst("###", registdate).replaceFirst("###", feed);
						Object[][] objDate = jdbc.Querry(_maxDateSql, connection);
						String maxDate = objDate[0][0]==null?"":objDate[0][0].toString();
						
						if(maxDate != null && !"".equals(maxDate))
							registdate = maxDate;
					}
					
					String _pSql= priceSql.replaceFirst("###", feed).replaceFirst("###", registdate);
					//--1.1获取定价
					Object[][] _priceobj = jdbc.Querry(_pSql, connection);
					if(_priceobj == null || _priceobj.length==0){
						sb.append("未找到饲料【"+feed+"】对应的单价\n\r");
						continue;
					}
					String price = "";
					String pid="";
					for(int t=0;t<_priceobj.length;t++){
						pid = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
						price = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
						if(price==null || "".equals(price))
							continue;
						else
							break;
					}
					if(price==null || "".equals(price)){
						sb.append("未找到饲料【"+feed+"】对应的单价\n\r");
						continue;
					}
					String money=ArithUtil.mul(price, quantity,2);
					//费用总数相加
					allMany = ArithUtil.add(allMany, money);
					//保存对象
					ps.setString(1,orderId);//入库单号
					ps.setString(2,feed);//饲料
					ps.setString(3,quantity);//数量
					ps.setString(4,price);//单价
					ps.setString(5,money);//金额
					ps.setString(6,batch);//批次
					ps.setString(7,pid);//定价单
					ps.setString(8,"1");//单据类型
					ps.setString(9,registdate);//单据日期
					
					ps.addBatch();
							   
				}
				//计算转接单
				Object[][] _obj = jdbc.Querry(trnSql, connection);
				if(_obj.length>0){
					//首先获取最大单据时间，如果最大入库时间不存在就以转接单时间为准
					for(int i=0;i<_obj.length;i++){
						String orderId = _obj[i][0]==null?null:_obj[i][0].toString();
						String feed = _obj[i][1]==null?null:_obj[i][1].toString();
						String quantity = _obj[i][2]==null?"0":_obj[i][2].toString();
						String registdate = _obj[i][3]==null?null:_obj[i][3].toString();
						
						if(feed==null){
							sb.append("转接单【"+orderId+"】有饲料编码为空");
							continue;
						}
						String _maxDateSql = maxDateSql.replaceFirst("###", registdate).replaceFirst("###", feed);
						Object[][] objDate = jdbc.Querry(_maxDateSql, connection);
						String maxDate = objDate[0][0]==null?"":objDate[0][0].toString();
						
						if(maxDate==null || "".equals(maxDate))
							maxDate = registdate;
						if(maxDate==null){
							sb.append("获取最后一次领药单时间失败");
							continue;
						}
						//查找该饲料的定价
						String _pSql= priceSql.replaceFirst("###", feed).replaceFirst("###", maxDate);
						//--1.1获取定价
						Object[][] _priceobj = jdbc.Querry(_pSql, connection);
						if(_priceobj == null || _priceobj.length==0){
							sb.append("未找到饲料【"+feed+"】对应的单价\n\r");
							continue;
						}
						String price = "";
						String pid="";
						for(int t=0;t<_priceobj.length;t++){
							pid = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
							price = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
							if(price==null || "".equals(price))
								continue;
							else
								break;
						}
						if(price==null || "".equals(price)){
							sb.append("未找到饲料【"+feed+"】对应的单价\n\r");
							continue;
						}
						String money=ArithUtil.mul(price, quantity,2);
						//费用总数相加
						allMany = ArithUtil.sub(allMany, money);
						//保存对象
						ps.setString(1,orderId);//入库单号
						ps.setString(2,feed);//饲料
						ps.setString(3,quantity);//数量
						ps.setString(4,price);//单价
						ps.setString(5,"-"+money);//金额
						ps.setString(6,batch);//批次
						ps.setString(7,pid);//定价单
						ps.setString(8,"2");//单据类型
						ps.setString(9,registdate);//单据日期
						
						ps.addBatch();
								   
					}
				}
				if(sb.length()>0)
					throw new SystemException(sb.toString());
				//执行sql
				ps.executeBatch();
			}else
				throw new SystemException("该批次生猪没有饲料入库单");
		}finally{
			if(ps != null)
				ps.close();
			ps=null;
		}
		return allMany;
	}
	
	//计算药品费用
	private String balanceDrugsCost(Connection connection,String batch,String company,String uName)throws Exception{
		//将使用的药品按照名称进行分组
		String sql="select t.drugbill, t.drug, sum(t.quantity), f.registdate "+
				   "from fs_drugbilldtl t "+
				   "join fs_drugbill f on (f.id = t.drugbill) "+
				   "where f.batch = '"+batch+"' "+
				   "and f.checkstatus = '1' "+
				   "group by t.drugbill, t.drug, f.registdate";
		//获取转接单
		String outSql="select t.drugtransfer, t.drug, sum(t.quantity),f.registdate "+
					  "from fs_drugtransferdtl t "+
					  "join fs_drugtransfer f on (f.id = t.drugtransfer) "+
					 "where f.outbatch = '"+batch+"' "+
					   "and f.checkstatus = '1' "+
					 "group by t.drugtransfer, t.drug,f.registdate";
		
		//获取最后一次入库单日期
		String maxDateSql = "select max(f.registdate) from fs_drugbilldtl t left join fs_drugbill f on(f.id=t.drugbill) where f.batch='"+batch+"' and f.checkstatus='1' and f.registdate<='###' and t.drug='###' and t.quantity>0";
		
		//药品单价
		String priceSql = "select f.id,t.price "+
						  "from fs_drugpricedtl t "+
						  "join fs_drugprice f on (f.id = t.drugprice) "+
						 "where t.drug='###' "+
						   "and f.company = '"+company+"' "+
						   "and f.checkstatus = '1' "+
						   "and f.startdate <= '###' "+
						   "order by f.startdate desc";
		
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_COMPANYDRUGCOST(ID,DRUG,QUANTITY,PRICE,MONEY,BATCH,DRUGPRICE,BILLNUM,REGISTDATE,BILLTYPE,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_COMPANYBALANCE_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			//领药单处理
			Object[][] _obj = jdbc.Querry(sql, connection);
			if(_obj.length>0){
				ps =jdbc.createPreparedStmt(insert_sql,connection);
				for(int i=0;i<_obj.length;i++){
					String drugBill = _obj[i][0]==null?null:_obj[i][0].toString();
					String drug = _obj[i][1]==null?null:_obj[i][1].toString();
					
					String quantity = _obj[i][2]==null?"0":_obj[i][2].toString();
					String registDate = _obj[i][3]==null?"0":_obj[i][3].toString();
					
					if(drug==null){
						sb.append("领药单【"+drugBill+"】有药品编码为空");
						continue;
					}
					if(registDate==null){
						sb.append("领药单【"+drugBill+"】入库日期为空");
						continue;
					}
					//查找该药品的定价
					if(ArithUtil.comparison(quantity, "0")<0){
						String _maxDateSql = maxDateSql.replaceFirst("###", registDate).replaceFirst("###", drug);
						Object[][] objDate = jdbc.Querry(_maxDateSql, connection);
						String maxDate = objDate[0][0]==null?"":objDate[0][0].toString();
						
						if(maxDate!=null && !"".equals(maxDate))
							registDate = maxDate;
					}
					
					String _pSql= priceSql.replaceFirst("###", drug).replaceFirst("###", registDate);
					
					//--1.1获取定价
					Object[][] _priceobj = jdbc.Querry(_pSql, connection);
					if(_priceobj == null || _priceobj.length==0){
						sb.append("未找到药品【"+drug+"】对应的单价\n");
						continue;
					}
					String price = "";
					String pid="";
					for(int t=0;t<_priceobj.length;t++){
						pid = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
						price = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
						if(price==null || "".equals(price))
							continue;
						else
							break;
					}
					if(price==null || "".equals(price)){
						sb.append("未找到药品【"+drug+"】对应的单价\n\r");
						continue;
					}
					//计算费用
					String money=ArithUtil.mul(price, quantity,2);
					//费用总数相加
					allMany = ArithUtil.add(allMany, money);
					
					//保存对象
					ps.setString(1,drug);//药品
					ps.setString(2,quantity);//数量
					ps.setString(3,price);//单价
					ps.setString(4,money);//金额
					ps.setString(5,batch);//批次
					ps.setString(6,pid);//定价单
					ps.setString(7,drugBill);//单据号
					ps.setString(8,registDate);//单据日期
					ps.setString(9,"1");//单据类型
					
					ps.addBatch();
				}
				//转接单处理
				Object[][] objs = jdbc.Querry(outSql, connection);
				if(objs.length>0){
					//首先获取最大单据时间，如果最大入库时间不存在就以转接单时间为准
					for(int i=0;i<objs.length;i++){
						String drugBill = objs[i][0]==null?null:objs[i][0].toString();
						String drug = objs[i][1]==null?null:objs[i][1].toString();
						String quantity = objs[i][2]==null?"0":objs[i][2].toString();
						String registDate = objs[i][3]==null?"0":objs[i][3].toString();
						
						if(drug==null){
							sb.append("转接单【"+drugBill+"】有药品编码为空");
							continue;
						}
						String _maxDateSql = maxDateSql.replaceFirst("###", registDate).replaceFirst("###", drug);
						Object[][] objDate = jdbc.Querry(_maxDateSql, connection);
						String maxDate = objDate[0][0]==null?"":objDate[0][0].toString();
						if(maxDate==null || "".equals(maxDate))
							maxDate = registDate;
						if(maxDate==null){
							sb.append("获取最后一次领药单时间失败");
							continue;
						}
						//查找该药品的定价
						String _pSql= priceSql.replaceFirst("###", drug).replaceFirst("###", maxDate);
						
						//--1.1获取定价
						Object[][] _priceobj = jdbc.Querry(_pSql, connection);
						if(_priceobj == null || _priceobj.length==0){
							sb.append("未找到药品【"+drug+"】对应的单价\n");
							continue;
						}
						String price = "";
						String pid="";
						for(int t=0;t<_priceobj.length;t++){
							pid = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
							price = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
							
							if(price==null || "".equals(price))
								continue;
							else
								break;
						}
						if(price==null || "".equals(price)){
							sb.append("未找到药品【"+drug+"】对应的单价\n\r");
							continue;
						}
						//计算费用
						String money=ArithUtil.mul(price, quantity,2);
						//费用总数相加
						allMany = ArithUtil.sub(allMany, money);
						//保存对象
						ps.setString(1,drug);//药品
						ps.setString(2,quantity);//数量
						ps.setString(3,price);//单价
						ps.setString(4,"-"+money);//金额
						ps.setString(5,batch);//批次
						ps.setString(6,pid);//定价单
						ps.setString(7,drugBill);//单据号
						ps.setString(8,registDate);//单据日期
						ps.setString(9,"2");//单据类型
						
						ps.addBatch();
					}
				}
				if(sb.length()>0)
					throw new SystemException(sb.toString());
				//执行sql
				ps.executeBatch();
			}
		}finally{
			if(ps != null)
				ps.close();
			ps=null;
		}
		return allMany;
	}
	
	//计算其他物料费用
	private String balanceOtherCost(Connection connection,String batch,String endDate,String company,String uName)throws Exception{
		//将使用的药品按照名称进行分组
		String sql="select t.material,sum(t.quantity) from fs_materialbilldtl t join fs_materialbill f on(f.id=t.materialbill) where f.batch = '"+batch+"' and f.checkstatus = '1' group by t.material";
		//查找定价单
		String priceSql="select f.id,t.price "+
					  "from fs_materielpricedtl t "+
					  "join fs_materielprice f on (f.id = t.materialprice) "+
					 "where t.material='###' "+
					   "and f.company = '"+company+"' "+
					   "and f.checkstatus = '1' "+
					   "and f.startdate <= '"+endDate+"' "+
					 "order by f.startdate desc";
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_COMPANYOTHERCOST(ID,MATERIAL,QUANTITY,PRICE,MONEY,BATCH,MATERIALPRICE,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_COMPANYBALANCE_SEQ.NEXTVAL,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			Object[][] _obj = jdbc.Querry(sql, connection);
			if(_obj.length>0){
				ps =jdbc.createPreparedStmt(insert_sql,connection);
				for(int i=0;i<_obj.length;i++){
					String m = _obj[i][0]==null?null:_obj[i][0].toString();
					if(m==null){
						sb.append("领料单中物料编码为空");
						continue;
					}
					String quantity = _obj[i][1]==null?"0":_obj[i][1].toString();
					
					//查找该物料的定价
					String _pSql= priceSql.replace("###", m);
					//--1.1获取定价
					Object[][] _priceobj = jdbc.Querry(_pSql, connection);
					if(_priceobj == null || _priceobj.length==0){
						sb.append("未找到物料【"+m+"】对应的单价\n\r");
						continue;
					}
					String price = "";
					String pid="";
					for(int t=0;t<_priceobj.length;t++){
						pid = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
						price = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
						if(price==null || "".equals(price))
							continue;
						else
							break;
					}
					if(price==null || "".equals(price)){
						sb.append("未找到物料【"+m+"】对应的单价\n\r");
						continue;
					}
					//计算费用
					String money=ArithUtil.mul(price, quantity,2);
					
					//费用总数相加
					allMany = ArithUtil.add(allMany, money);
					//保存对象
					ps.setString(1,m);//药品
					ps.setString(2,quantity);//数量
					ps.setString(3,price);//单价
					ps.setString(4,money);//金额
					ps.setString(5,batch);//批次
					ps.setString(6,pid);//定价单
					
					ps.addBatch();
				}
				if(sb.length()>0)
					throw new SystemException(sb.toString());
				//执行sql
				ps.executeBatch();
			}
		}finally{
			if(ps != null)
				ps.close();
			ps=null;
		}
		return allMany;
	}
	
	//计算猪苗费用
	private String balancePigletCost(Connection connection,String batch,String uName)throws Exception{
		//根据批次号分组计算销售费用
		String sql = "select t.id,t.quantity,t.cost from fs_pigpurchase t where t.batch='"+batch+"' and t.checkstatus='1'";
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_COMPANYPIGLETCOST(ID,PIGPURCHASE,QUANTITY,PRICE,MONEY,BATCH,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_COMPANYBALANCE_SEQ.NEXTVAL,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			Object[][] obj = jdbc.Querry(sql, connection);
			if(obj.length>0){
				 ps =jdbc.createPreparedStmt(insert_sql,connection);
				for(int i=0;i<obj.length;i++){
					String id = obj[i][0]==null?null:obj[i][0].toString();
					String quantity = obj[i][1]==null?"0":obj[i][1].toString();
					String cost = obj[i][2]==null?"0":obj[i][2].toString();
					
					//计算单件
					String price="0";
					if(quantity != null && PapUtil.checkDouble(quantity) && ArithUtil.comparison(quantity, "0") !=0){
						price = ArithUtil.div(cost, quantity,2);
					}
					//将总金额相加
					allMany = ArithUtil.add(allMany, cost);
					//保存对象
					
					ps.setString(1,id);//采购单
					ps.setString(2,quantity);//头数
					ps.setString(3,price);//单价
					ps.setString(4,cost);//金额
					ps.setString(5,batch);//批次
					
					ps.addBatch();
				}
				if(sb.length()>0)
					throw new SystemException(sb.toString());
				//执行sql
				ps.executeBatch();
			}else
				throw new SystemException("该批次生猪没有猪苗费用");
		}finally{
			if(ps != null)
				ps.close();
			ps=null;
		}
		return allMany;
	}
	
	//计算运输费用
	private String[] balanceYunFeiCost(Connection connection,String batch,String company,String farmer,String feedFrc,String uName)throws Exception{
		
		String[] yunfei = new String[2];
		//查询销售单 运费
		String salesql = "select t.id, t.tcost,t.registdate from fs_corsalebill t where t.batch='"+batch+"' and t.checkstatus='1'";
		//查找猪苗购进单 运费
		String buySql = "select t.id,t.freight,t.registdate from fs_pigpurchase t where t.batch='"+batch+"' and t.checkstatus='1'";
		//查找饲料入库单以及饲料包装
		String insql="select f.id, f.registdate, v.packform, sum(t.quantity) "+
					 "from fs_feedinwaredtl t "+
					 "join fs_feedinware f on (f.id = t.feedinware) "+
					 "left join fs_feed v on (t.feed = v.id) "+
					 "where f.batch = '"+batch+"' "+
					 "and f.checkstatus = '1' "+
					 "and f.driver is not null " +
					 "group by f.id, f.registdate, v.packform";
		//查找运输定价单
		String pricesql = "select t.packageprice,t.bulkprice "+
						   "from fs_freightdtl t "+
						   "join fs_freight f on (f.id = t.freight) "+
						   "where f.company = '"+company+"' "+
						   "and f.checkstatus = '1' "+
						   "and t.farmer = '"+farmer+"' "+
						   "and t.feedfac = '"+feedFrc+"' "+
						   "and f.registdate <='###' "+
						   "order by f.registdate";
		String saleYunFei="0";
		String caigouYunFei="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_COMPANYFREIGHT(ID,FREIGHTTYPE,BILLID,REGISTDATE,FEEDWEIGHT,FEEDPACK,PRICE,MONEY,BATCH,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_COMPANYBALANCE_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			 ps =jdbc.createPreparedStmt(insert_sql,connection);
			//1.查询销售单运费
			Object[][] obj = jdbc.Querry(salesql, connection);
			if(obj.length>0){
				for(int i=0;i<obj.length;i++){
					String id = obj[i][0]==null?null:obj[i][0].toString();
					String money = obj[i][1]==null?"0":obj[i][1].toString();
					String date = obj[i][2]==null?"0":obj[i][2].toString();
					
					//将总金额相加
					saleYunFei = ArithUtil.add(saleYunFei, money);
					//保存对象
					ps.setString(1,"3");//运费类型
					ps.setString(2,id);//单据号
					ps.setString(3,date);//单据日期
					ps.setString(4,null);//重量
					ps.setString(5,null);//包装
					ps.setString(6,null);//单价
					ps.setString(7,money);//销售单
					ps.setString(8,batch);//批次
					
					ps.addBatch();
				}
			}
			//2.查询猪苗单运费
			Object[][] _obj = jdbc.Querry(buySql, connection);
			if(_obj.length>0){
				for(int i=0;i<_obj.length;i++){
					String id = _obj[i][0]==null?null:_obj[i][0].toString();
					String money = _obj[i][1]==null?"0":_obj[i][1].toString();
					String date = _obj[i][2]==null?"":_obj[i][2].toString();
					
					//将总金额相加
					caigouYunFei = ArithUtil.add(caigouYunFei, money);
					//保存对象
					ps.setString(1,"1");//运费类型
					ps.setString(2,id);//单据号
					ps.setString(3,date);//单据日期
					ps.setString(4,null);//重量
					ps.setString(5,null);//包装
					ps.setString(6,null);//单价
					ps.setString(7,money);//销售单
					ps.setString(8,batch);//批次
					
					ps.addBatch();
				}
			}
			//3.计算饲料运输单费用
			//查询饲料入库单
			Object[][] _inobj = jdbc.Querry(insql, connection);
			if(_inobj.length>0){
				for(int i=0;i<_inobj.length;i++){
					String id = _inobj[i][0]==null?"":_inobj[i][0].toString();
					String date = _inobj[i][1]==null?"":_inobj[i][1].toString();
					String packform = _inobj[i][2]==null?"":_inobj[i][2].toString();
					String quantity = _inobj[i][3]==null?"0":_inobj[i][3].toString();
					
					if(quantity==null || !PapUtil.checkDouble(quantity) || ArithUtil.comparison(quantity, "0")<1)
						continue;
					if(packform==null || "".equals(packform)){
						sb.append("饲料包装规格不对\n\r");
						continue;
					}
					//求单价
					String _pSql = pricesql.replace("###", date);
					//--1.1获取定价
					Object[][] _priceobj = jdbc.Querry(_pSql, connection);
					
					if(_priceobj == null || _priceobj.length==0){
						sb.append("未找到入库单【"+id+"】对应的运输费定价单\n\r");
						continue;
					}
					String packePrice = "";
					String freePrice ="";
					for(int t=0;t<_priceobj.length;t++){
						packePrice = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
						freePrice = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
						if(packePrice==null || "".equals(packePrice) || freePrice==null || "".equals(freePrice))
							continue;
						else
							break;
					}
					if(packePrice==null || "".equals(packePrice) || freePrice==null || "".equals(freePrice)){
						sb.append("未找到入库单【"+id+"】对应的运输费定价单\n\r");
						continue;
					}
					
					String money="0";
					String price="0";
					//计算费用
					if("1".equals(packform)){
						price = freePrice;
					}else if("2".equals(packform)){
						price = packePrice;
					}
					money = ArithUtil.mul(price, quantity);
					//每吨换算为每公斤
					money = ArithUtil.div(money, "1000",2);
					//将总金额相加
					caigouYunFei = ArithUtil.add(caigouYunFei, money);
					//保存对象
					ps.setString(1,"2");//运费类型
					ps.setString(2,id);//单据号
					ps.setString(3,date);//单据日期
					ps.setString(4,quantity);//重量
					ps.setString(5,packform);//包装
					ps.setString(6,price);//单价
					ps.setString(7,money);//销售单
					ps.setString(8,batch);//批次
					
					ps.addBatch();
				}
			}
			
			if(sb.length()>0)
				throw new SystemException(sb.toString());
			//执行sql
			ps.executeBatch();
			
			yunfei[0] = caigouYunFei;
			yunfei[1] = saleYunFei;
			
		}finally{
			if(ps != null)
				ps.close();
			ps=null;
		}
		return yunfei;
	}
	/**
	 * 功能：撤销<br/>
	 *
	 * @author DZL 
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void cancelCheck(String id)throws Exception{
		//验证公司结算是不是已经完成 已经完成不允许撤销
		CompanyBalance c = super.selectById(id);
		if(c == null)
			throw new SystemException("对象不存在");
		Batch batch = c.getBatch();
		//将所有单据结算状态解除
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isBalance", "N");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("batch.id", "=", batch.getId()+"");
		
		feedInWareService.updateByCod(sqlMap,map);
		companySaleService.updateByCod(sqlMap,map);
		
		//删除账单
		companyBillService.deleteBySingletAll("batch.id", batch.getId());
		companyDrugCostService.deleteBySingletAll("batch.id", batch.getId());
		companyFeedCostService.deleteBySingletAll("batch.id", batch.getId());
		companyFreightService.deleteBySingletAll("batch.id", batch.getId());
		companyOtherCostService.deleteBySingletAll("batch.id", batch.getId());
		companyPigletCostService.deleteBySingletAll("batch.id", batch.getId());
		companySaleIncomeService.deleteBySingletAll("batch.id", batch.getId());
		//更改状态
		c.setCheckStatus("0");
		c.setCheckDate(null);
		c.setCheckUser(null);
	}

	/**
	 * 
	 * 功能:下载模板
	 * 重写:wxb
	 * 2017-9-13
	 * @see com.zd.foster.balance.services.ICompanyBalanceService#downloadTemplate(java.lang.String)
	 */
	@Override
	public InputStream downloadTemplate(String realPath) throws Exception {
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "companyBalance.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
	}

	/**
	 * 
	 * 功能:导入养殖公司结算单
	 * 重写:wxb
	 * 2017-9-13
	 * @see com.zd.foster.balance.services.ICompanyBalanceService#operateFile(java.io.File, com.zd.foster.base.entity.Company, java.lang.Object[])
	 */
	@Override
	public List<CompanyBalance> operateFile(File doc, Company company,List<String> idList,
			Object... objects) throws Exception {
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		List<CompanyBalance> details = new ArrayList<CompanyBalance>();
		Workbook workbook = ExcelUtil.buildWorkbook(doc, (String)objects[0]);
		if (null != workbook) {
			Sheet sheet = workbook.getSheetAt(0); // 获得第一个Excel Sheet
			int lastRowNum = sheet.getLastRowNum(); // 最后行号，默认为索引号，即从0开始到当前行号-1，如excel有10条数据，firstRowNum=0，而lastRowNum=9
			if (lastRowNum >0) {
				int firstRowNum = sheet.getFirstRowNum();
				StringBuffer sb = new StringBuffer(); // 存储校验非法描述
				for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (null != row) {
						String farmer = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "代养户", true, sb);
						String batch = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "批次", true, sb);
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "结算日期", true, sb);
						//验证代养户
						Farmer fr=new Farmer();
						String hql_farmer="from Farmer e where e.company.id='"+company.getId()+"'and e.name='"+farmer+"'";
						List<Farmer> fList=farmerService.selectByHQL(hql_farmer);
						if(fList==null || fList.isEmpty()){
							sb.append(new SystemException("不存在代养户【"+farmer+"】"));
							continue;
						}else
							fr=fList.get(0);
						//验证批次
						Batch bh=new Batch();
						String hql_batch="from Batch e where e.company.id='"+company.getId()+"'and e.farmer.name='"+farmer+"'and e.batchNumber='"+batch+"'";
						List<Batch> bList=batchService.selectByHQL(hql_batch);
						if(bList==null || bList.isEmpty()){
							sb.append(new SystemException("不存在代养户【"+farmer+"】批次【"+batch+"】"));
							continue;
						}else
							bh=bList.get(0);
						CompanyBalance fb=new CompanyBalance();
						fb.setCompany(company);
						fb.setFarmer(fr);
						fb.setBatch(bh);
						fb.setRegistDate(registDate);
						Object obj=this.save(fb);
						idList.add((String)obj);
						details.add(fb);
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(details.isEmpty())
					throw new SystemException("无可用数据导入");
			}else
				throw new SystemException("无可用数据导入");
		}else
			throw new SystemException("无可用数据导入");
		return details;
	}
	
}
