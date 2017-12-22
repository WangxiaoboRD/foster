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
import org.springframework.jdbc.core.JdbcTemplate;
import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.balance.dao.IFarmerBalanceDao;
import com.zd.foster.balance.entity.FarmerBalance;
import com.zd.foster.balance.services.ICompanyBalanceService;
import com.zd.foster.balance.services.IFarmerBalanceService;
import com.zd.foster.balance.services.IFarmerBillService;
import com.zd.foster.balance.services.IFarmerDrugsCostService;
import com.zd.foster.balance.services.IFarmerFeedCostService;
import com.zd.foster.balance.services.IFarmerOtherCostService;
import com.zd.foster.balance.services.IFarmerPigletCostService;
import com.zd.foster.balance.services.IFarmerRewardCostService;
import com.zd.foster.balance.services.IFarmerSaleIncomeService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.IFcrService;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IDeathBillService;
import com.zd.foster.breed.services.IFeedBillService;
import com.zd.foster.breed.services.IMaterialBillService;
import com.zd.foster.breed.services.IMedicalBillService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.sale.services.IFarmerSaleService;
import com.zd.foster.sale.services.IOutPigstyService;
import com.zd.foster.warehouse.services.IDrugBillService;

/**
 * 类名：  FarmerBalanceServiceImpl
 * 功能：
 * @author dzl
 * @date 2017-7-19下午02:54:38
 * @version 1.0
 * 
 */
public class FarmerBalanceServiceImpl extends BaseServiceImpl<FarmerBalance, IFarmerBalanceDao> implements IFarmerBalanceService {

	@Resource
	private IBatchService batchService;
	@Resource
	private JDBCWrapperx jdbc;
	@Resource
	private IFeedBillService feedBillService;
	@Resource
	private IMaterialBillService materialBillService;
	@Resource
	private IMedicalBillService medicalBillService;
	@Resource
	private IPigPurchaseService pigPurchaseService;
	@Resource
	private IDeathBillService deathBillService;
	//@Resource
	//private IEliminateBillService eliminateBillService;
	@Resource
	private IFarmerSaleService farmerSaleService;
	@Resource
	private IFarmerSaleIncomeService farmerSaleIncomeService;
	@Resource
	private IFarmerFeedCostService farmerFeedCostService;
	@Resource
	private IFarmerDrugsCostService farmerDrugsCostService;
	@Resource
	private IFarmerOtherCostService farmerOtherCostService;
	@Resource
	private IFarmerPigletCostService farmerPigletCostService;
	@Resource
	private IFarmerRewardCostService farmerRewardCostService;
	@Resource
	private IFarmerBillService farmerBillService;
	@Resource
	private ICompanyBalanceService companyBalanceService;
	@Resource
	private IFcrService fcrService;
	@Resource
	private IOutPigstyService outPigstyService;
	@Resource
	private IDrugBillService drugBillService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	protected JdbcTemplate jdbcTemplate;
	@Resource
	private IContractService contractService;
	

	/**
	 * 功能:分页查询
	 * 重写:DZL
	 * 2017-7-27
	 */
	@Override
	public void selectAll(FarmerBalance entity, Pager<FarmerBalance> page) throws Exception {
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
	public Object save(FarmerBalance entity) throws Exception{
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
		entity.setCheckStatus("0");
		entity.setCompany(batch.getCompany());
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
	public int updateHql(FarmerBalance entity)throws Exception{
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
		FarmerBalance f = super.selectById(entity.getId());
		
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
	 * 功能：结算<br/>
	 *
	 * @author DZL 
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void balance(String id)throws Exception{
		
		String farmerbalanceSql = "select t.batch,t.farmer,t.company,t.registdate,t.checkstatus from fs_farmerbalance t  where t.id='"+id+"'";
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
				String isbalanceSql = "select t.endDate,t.pigletprice,t.standpigletweight,t.standsaleweight,t.marketprice,t.allowdiff,t.firstdiff,t.firstprice,t.overfirstprice from fs_contract t where t.id='"+contractId+"' and t.company='"+company+"' and t.farmer='"+farmer+"' and t.status='LOST'";
				Object[][] isBalanceObj = jdbc.Querry(isbalanceSql, connection);
				String endDate = "";
				String pigletPrice="0";
				
				
				String pigletWeight="0";
				String pigSaleWeight="0";
				String marketPrice="0";
				
				String standOffset=null;
				String oneOffset=null;
				String onePrice="0";
				String superPrice="0";
				
				if(isBalanceObj.length>0){
					endDate = isBalanceObj[0][0]==null?"":isBalanceObj[0][0].toString();
					pigletPrice = isBalanceObj[0][1]==null?"0":isBalanceObj[0][1].toString();
					
					pigletWeight = isBalanceObj[0][2]==null?"":isBalanceObj[0][2].toString();
					pigSaleWeight = isBalanceObj[0][3]==null?"":isBalanceObj[0][3].toString();
					marketPrice = isBalanceObj[0][4]==null?"":isBalanceObj[0][4].toString();
					
					standOffset = isBalanceObj[0][5]==null?"":isBalanceObj[0][5].toString();
					oneOffset = isBalanceObj[0][6]==null?"":isBalanceObj[0][6].toString();
					onePrice = isBalanceObj[0][7]==null?"":isBalanceObj[0][7].toString();
					superPrice = isBalanceObj[0][8]==null?"":isBalanceObj[0][8].toString();
				}
				if("".equals(endDate))
					throw new SystemException("该批次合同未终止，不允许结算");
				
				//开始计算业务
				jdbc.beginTransaction(connection);//开启事务
				/**---------------------------1.锁定单据-----------------------*/
				lockBill(connection,batch);
				/**---------------------------2.计算销售费用-----------------------*/
				String saleCost = balanceSaleCost(connection, batch, uName);
				/**---------------------------3.计算饲料费用-----------------------*/
				String feedCost = balanceFeedCost(connection, batch, uName);
				/**---------------------------4.计算药品费用-----------------------*/
				String drugCost = balanceDrugsCost(connection,batch,company,uName);
				/**---------------------------5.计算其猪苗费用-----------------------*/
				String pigletCost = balancePigletCost(connection,batch,pigletPrice,uName);
				/**---------------------------6.计算其他物料费用-----------------------*/
				String otherCost = balanceOtherCost(connection,batch,endDate,company,uName);
				/**---------------------------7.计算奖罚费用-----------------------*/
				String[] rewardCost = balanceRewardCost(connection,batch,pigletWeight,pigSaleWeight,standOffset,oneOffset,onePrice,superPrice,marketPrice,contractId,uName);
				/**---------------------------8.构建表头-----------------------*/
				//1.计算投入总费用 feedCost+drugCost+pigletCost+otherCost+rewardCost[1]
				String inputCost = ArithUtil.add(feedCost, drugCost);
				inputCost = ArithUtil.add(inputCost, pigletCost);
				inputCost = ArithUtil.add(inputCost, otherCost);
				inputCost = ArithUtil.add(inputCost, rewardCost[0]);
				//2.计算代养费 saleCost - inputCost
				String fosterCost = ArithUtil.sub(saleCost, inputCost);
				
				String inPigDate="";
				String outPigDate="";
				//通过sql语句查询
				String inPigDateSql="select min(t.registdate) from fs_pigpurchase t where t.batch='"+batch+"' and t.checkstatus='1'";
				String outPigDateSql="select max(t.registdate) from fs_farmersalebill t where t.batch='"+batch+"' and t.checkstatus='1'";
				Object[][] data_in = jdbc.Querry(inPigDateSql, connection);
				Object[][] data_out = jdbc.Querry(outPigDateSql, connection);
				if(data_in != null && data_in.length>0)
					inPigDate = data_in[0][0]==null?"":data_in[0][0].toString();
				if(data_out != null && data_out.length>0)
					outPigDate = data_out[0][0]==null?"":data_out[0][0].toString();
				
				String ralefcr=rewardCost[1];
				String inPigAvgWeight=rewardCost[2];
				String inPigNum=rewardCost[3];
				String outPigAllWeight=rewardCost[4];
				String outPigNum=rewardCost[5];
				String outPigAvgWeight=rewardCost[6];
				String netGin=rewardCost[7];
				String avgNetGin="";
				if(outPigAvgWeight != null && PapUtil.checkDouble(outPigAvgWeight) && inPigAvgWeight!= null && PapUtil.checkDouble(inPigAvgWeight))
					avgNetGin = ArithUtil.sub(outPigAvgWeight, inPigAvgWeight);
				String survivalRate="";
				if(outPigNum != null && PapUtil.checkDouble(outPigNum) && inPigNum!= null && PapUtil.checkDouble(inPigNum))
					survivalRate = ArithUtil.percent(ArithUtil.div(outPigNum, inPigNum),2);
				String avgPigCost="0";
				if(fosterCost != null && PapUtil.checkDouble(fosterCost) && outPigNum != null && PapUtil.checkDouble(outPigNum))
					avgPigCost = ArithUtil.div(fosterCost, outPigNum,2);
				//构建插入语句
				String in_sql = "INSERT INTO FS_FARMERBILL(ID,REGISTDATE,BATCH,FARMER,COMPANY,SALEINCOME,FEEDCOST,DRUGSCOST,PIGLETCOST,OTHERCOST,REWARDCOST,ALLBREEDCOST,FARMERCOST,FCR,INPIGNUM,INPIGAVGWEIGHT,INPIGDATE,OUTPIGNUM,OUTPIGAVGWEIGHT,OUTPIGDATE,OUTPIGALLWEIGHT,NETGIN,AVGNETGIN,SURVIVALRATE,AVGFARMERCOST,CREATEUSER,CREATEDATE) "+
								"VALUES(FS_FARMERBILL_SEQUENCE.NEXTVAL,'"+registdate+"','"+batch+"','"+farmer+"','"+company+"','"+saleCost+"','"+feedCost+"','"+drugCost+"','"+pigletCost+"','"+otherCost+"','"+rewardCost[0]+"','"+inputCost+"','"+fosterCost+"'," +
								"'"+ralefcr+"','"+inPigNum+"','"+inPigAvgWeight+"','"+inPigDate+"','"+outPigNum+"','"+outPigAvgWeight+"','"+outPigDate+"','"+outPigAllWeight+"','"+netGin+"','"+avgNetGin+"','"+survivalRate+"','"+avgPigCost+"','"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
				//更新批次上的料肉比
				String update_batch = "UPDATE FS_BATCH T SET T.FCR='"+ralefcr+"',T.ISBALANCE='Y' WHERE T.ID='"+batch+"'";
				//更新月结信息 两个更新
				String balance_sql = "UPDATE FS_FARMERBALANCE T SET T.CHECKUSER='"+uName+"',T.CHECKDATE='"+PapUtil.date(new Date())+"',T.CHECKSTATUS='1' WHERE ID = '"+id+"'"; 
				//执行sql
				jdbc.insert(in_sql, connection);
				jdbc.update(update_batch, connection);
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
		
		String feedbillsql = "update fs_feedbill t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		String materialbillsql = "update fs_materialbill t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		String medicalbillsql = "update fs_medicalbill t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		String pigpurchasesql = "update fs_pigpurchase t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		String deathbillsql = "update fs_deathbill t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		//String eliminatebillsql = "update fs_eliminatebill t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		String farmersalebillsql = "update fs_farmersalebill t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		String outPigstysql = "update fs_outpigsty t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		//药品领药单
		String drugsql = "update fs_drugbill t set t.isbalance='Y' where t.batch='"+batch+"' and t.checkstatus='1'";
		
		//执行sql
		jdbc.update(feedbillsql, connection);
		jdbc.update(materialbillsql, connection);
		jdbc.update(medicalbillsql, connection);
		jdbc.update(pigpurchasesql, connection);
		jdbc.update(deathbillsql, connection);
		//jdbc.update(eliminatebillsql, connection);
		jdbc.update(farmersalebillsql, connection);
		jdbc.update(outPigstysql, connection);
		jdbc.update(drugsql, connection);
	}
	
	//计算销售费用
	private String balanceSaleCost(Connection connection,String batch,String uName)throws Exception{
		//根据批次号分组计算销售费用
		String sql = "select t.piglevel,sum(t.quantity),sum(t.weight),sum(t.amount) from fs_farmersalebilldtl t join fs_farmersalebill f on (f.id=t.farmersale) where f.batch='"+batch+"' and f.checkstatus='1' group by t.piglevel";
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_FARMERSALEINCOME(ID,FARMERBILL,PIGLEVEL,QUANTITY,WEIGHT,PRICE,MONEY,BATCH,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_BALANCE_SEQUENCE.NEXTVAL,?,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			Object[][] obj = jdbc.Querry(sql, connection);
			if(obj.length>0){
				 ps =jdbc.createPreparedStmt(insert_sql,connection);
				for(int i=0;i<obj.length;i++){
					String levelCode = obj[i][0]==null?null:obj[i][0].toString();
					if(levelCode==null){
						sb.append("销售级别为空");
						continue;
					}
					String quantity = obj[i][1]==null?"0":obj[i][1].toString();
					String weight = obj[i][2]==null?"0":obj[i][2].toString();
					String money = obj[i][3]==null?"0":obj[i][3].toString();
					
					//计算单件
					String price="0";
					if(ArithUtil.comparison(weight, "0") != 0){
						price = ArithUtil.div(money, weight,2);
					}
					//将总金额相加
					allMany = ArithUtil.add(allMany, money);
					
					//保存对象
					ps.setString(1,null);//账单号
					ps.setString(2,levelCode);//级别
					ps.setString(3,quantity);//头数
					ps.setString(4,weight);//重量
					ps.setString(5,price);//单价
					ps.setString(6,money);//金额
					ps.setString(7,batch);//批次
					
					ps.addBatch();
				}
				if(sb.length()>0)
					throw new SystemException(sb.toString());
				//执行sql
				ps.executeBatch();
			}else
				throw new SystemException("该批次生猪没有销售费用");
		}finally{
			if(ps != null)
				ps.close();
			ps=null;
		}
		return allMany;
	}
	
	//计算饲料费用
	private String balanceFeedCost(Connection connection,String batch,String uName)throws Exception{
		//获取合同饲料单价
		String feedpriceSql = "select t.feed,t.price from fs_contactfeeddtl t join fs_contract f on(f.id = t.contract) join fs_batch b on(f.id = b.contract) where b.id='"+batch+"'";
		String feedQuantitySql = "select t.feed,sum(t.quantity) from fs_feedbilldtl t join fs_feedbill f on (f.id = t.feedbill) where f.batch='"+batch+"' and f.checkstatus='1' group by t.feed";
		
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_FARMERFEEDCOST(ID,FARMERBILL,FEED,QUANTITY,PRICE,MONEY,BATCH,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_BALANCE_SEQUENCE.NEXTVAL,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			Object[][] obj = jdbc.Querry(feedpriceSql, connection);
			//封装单价
			Map<String,String> map = new HashMap<String,String>();
			if(obj.length>0){
				for(int i=0;i<obj.length;i++){
					String feed = obj[i][0]==null?null:obj[i][0].toString();
					if(feed==null){
						sb.append("合同中饲料定价中饲料编码为空");
						continue;
					}
					String price = obj[i][1]==null?"0":obj[i][1].toString();
					map.put(feed, price);
				}
				
			}else{
				throw new SystemException("未发现饲料在合同中的定价");
			}
			
			Object[][] _obj = jdbc.Querry(feedQuantitySql, connection);
			if(_obj.length>0){
				ps =jdbc.createPreparedStmt(insert_sql,connection);
				for(int i=0;i<_obj.length;i++){
					String feed = _obj[i][0]==null?null:_obj[i][0].toString();
					if(feed==null){
						sb.append("饲料编码为空");
						continue;
					}
					String quantity = _obj[i][1]==null?"0":_obj[i][1].toString();
					//计算费用
					//判断饲料是否是合同中的饲料
					if(map.containsKey(feed)){
						String price = map.get(feed);
						String money=ArithUtil.mul(price, quantity,2);
						
						//费用总数相加
						allMany = ArithUtil.add(allMany, money);
						//保存对象
						ps.setString(1,null);//账单号
						ps.setString(2,feed);//饲料
						ps.setString(3,quantity);//数量
						ps.setString(4,price);//单价
						ps.setString(5,money);//金额
						ps.setString(6,batch);//批次
						
						ps.addBatch();
						
					}else{
						sb.append("饲料编码【"+feed+"】未在合同中定价");
						continue;
					}
				}
				if(sb.length()>0)
					throw new SystemException(sb.toString());
				//执行sql
				ps.executeBatch();
			}else
				throw new SystemException("该批次生猪没有饲料费用");
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
					 "group by t.drugtransfer,t.drug,f.registdate";
		
		//获取最后一次入库单日期
		String maxDateSql = "select max(f.registdate) from fs_drugbilldtl t left join fs_drugbill f on(f.id=t.drugbill) where f.batch='"+batch+"' and f.checkstatus='1' and f.registdate<='###' and t.drug='###' and t.quantity>0";
		
		
		//药品单价
		String priceSql = "select f.id,t.price,t.saleprice "+
						  "from fs_drugpricedtl t "+
						  "join fs_drugprice f on (f.id = t.drugprice) "+
						 "where t.drug='###' "+
						   "and f.company = '"+company+"' "+
						   "and f.checkstatus = '1' "+
						   "and f.startdate <= '###' "+
						   "order by f.startdate desc";
		
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_FARMERDRUGSCOST(ID,FARMERBILL,DRUG,QUANTITY,PRICE,SALEPRICE,MONEY,BATCH,DRUGPRICE,DRUGBILL,REGISTDATE,BILLTYPE,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_BALANCE_SEQUENCE.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
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
					// 判断如果数量是正值，说明是正常的领用，药品价格查找最接近领药时间的定价 
					//如果是负值，说明是退货，价格需要按照退货单时间最接近的领药单，并以领药单时间查询最接近领药单的定价单
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
					String endPrice="";
					String pid="";
					for(int t=0;t<_priceobj.length;t++){
						pid = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
						price = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
						endPrice = _priceobj[t][2]==null?"":_priceobj[t][2].toString();
						if(endPrice==null || "".equals(endPrice))
							continue;
						else
							break;
					}
					if(endPrice==null || "".equals(endPrice)){
						sb.append("未找到药品【"+drug+"】对应的单价\n\r");
						continue;
					}
					//计算费用
					String money=ArithUtil.mul(endPrice, quantity,2);
					//费用总数相加
					allMany = ArithUtil.add(allMany, money);
					//保存对象
					ps.setString(1,null);//账单号
					ps.setString(2,drug);//药品
					ps.setString(3,quantity);//数量
					ps.setString(4,price);//单价
					ps.setString(5,endPrice);//最终单价
					ps.setString(6,money);//金额
					ps.setString(7,batch);//批次
					ps.setString(8,pid);//定价单
					ps.setString(9,drugBill);//单据号
					ps.setString(10,registDate);//单据日期
					ps.setString(11,"1");//单据类型
					
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
						String registDate = objs[i][3]==null?"":objs[i][3].toString();
						
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
						String endPrice="";
						String pid="";
						for(int t=0;t<_priceobj.length;t++){
							pid = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
							price = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
							endPrice = _priceobj[t][2]==null?"":_priceobj[t][2].toString();
							if(endPrice==null || "".equals(endPrice))
								continue;
							else
								break;
						}
						if(endPrice==null || "".equals(endPrice)){
							sb.append("未找到药品【"+drug+"】对应的单价\n\r");
							continue;
						}
						//计算费用
						String money=ArithUtil.mul(endPrice, quantity,2);
						//费用总数相加
						allMany = ArithUtil.sub(allMany, money);
						//保存对象
						ps.setString(1,null);//账单号
						ps.setString(2,drug);//药品
						ps.setString(3,quantity);//数量
						ps.setString(4,price);//单价
						ps.setString(5,endPrice);//最终单价
						ps.setString(6,"-"+money);//金额
						ps.setString(7,batch);//批次
						ps.setString(8,pid);//定价单
						ps.setString(9,drugBill);//单据号
						ps.setString(10,registDate);//单据日期
						ps.setString(11,"2");//单据类型
						
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
		//将使用的物料按照名称进行分组
		String sql="select t.material,sum(t.quantity) from fs_materialbilldtl t join fs_materialbill f on(f.id=t.materialbill) where f.batch = '"+batch+"' and f.checkstatus = '1' group by t.material";
		//查找定价单
		String priceSql="select f.id,t.saleprice "+
					  "from fs_materielpricedtl t "+
					  "join fs_materielprice f on (f.id = t.materialprice) "+
					 "where t.material='###' "+
					   "and f.company = '"+company+"' "+
					   "and f.checkstatus = '1' "+
					   "and f.startdate <= '"+endDate+"' "+
					 "order by f.startdate desc";
		
		
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_FARMEROTHERCOST(ID,FARMERBILL,MATERIAL,QUANTITY,PRICE,MONEY,BATCH,MATERIALPRICE,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_BALANCE_SEQUENCE.NEXTVAL,?,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			Object[][] _obj = jdbc.Querry(sql, connection);
			if(_obj.length>0){
				//查询物料
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
					ps.setString(1,null);//账单号
					ps.setString(2,m);//药品
					ps.setString(3,quantity);//数量
					ps.setString(4,price);//单价
					ps.setString(5,money);//金额
					ps.setString(6,batch);//批次
					ps.setString(7,pid);//批次
					
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
	private String balancePigletCost(Connection connection,String batch,String pigletPrice,String uName)throws Exception{
		//根据批次号分组计算销售费用
		String sql = "select t.id,t.quantity from fs_pigpurchase t where t.batch='"+batch+"' and t.checkstatus='1'";
		//查找该批次属于公司死亡的小猪
		String deathHeadSql = "select t.otherdeathquan from fs_batch t where t.id='"+batch+"'"; 
		
		String allMany="0";
		StringBuffer sb = new StringBuffer();
		String insert_sql = "INSERT INTO FS_FARMERPIGLETCOST(ID,FARMERBILL,PIGPURCHASE,QUANTITY,DEADHEAD,BALANCEHEAD,PRICE,MONEY,BATCH,CREATEUSER,CREATEDATE) " +
		 					"VALUES(FS_BALANCE_SEQUENCE.NEXTVAL,?,?,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		PreparedStatement ps = null;
		try{
			//查询死亡头数
			Object[][] deadobj = jdbc.Querry(deathHeadSql, connection);
			String deathhead=null;
			if(deadobj.length>0){
				deathhead = deadobj[0][0]==null?null:deadobj[0][0].toString();
			}
			
			Object[][] obj = jdbc.Querry(sql, connection);
			if(obj.length>0){
				ps =jdbc.createPreparedStmt(insert_sql,connection);
				for(int i=0;i<obj.length;i++){
					String id = obj[i][0]==null?null:obj[i][0].toString();
					String quantity = obj[i][1]==null?"0":obj[i][1].toString();
					
					//处理死亡头数，将其算到第一个猪苗登记单上
					String balanceHead=quantity;
					String deadNum = null;
					if(i==0){
						deadNum = deathhead;
						if(deadNum != null && PapUtil.checkDouble(deadNum) && ArithUtil.comparison(deadNum, "0")==1){
							balanceHead = ArithUtil.sub(quantity, deadNum);
						}
					}
					
					//计算单件
					String price="0";
					if(pigletPrice != null && PapUtil.checkDouble(pigletPrice)){
						price = pigletPrice;
					}
					//计算金额
					String money = ArithUtil.mul(price, balanceHead,2);
					
					//将总金额相加
					allMany = ArithUtil.add(allMany, money);
					//保存对象
					ps.setString(1,null);//账单号
					ps.setString(2,id);//采购单
					ps.setString(3,quantity);//头数
					ps.setString(4,deadNum);//死亡头数
					ps.setString(5,balanceHead);//计算头数
					ps.setString(6,price);//单价
					ps.setString(7,money);//金额
					ps.setString(8,batch);//批次
					
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
	
	//计算奖罚费用
	private String[] balanceRewardCost(Connection connection,String batch,String pigWeight,String pigSaleWeight,String standOffset,String oneOffset,String onePrice,String superPrice,String marketPrice,String contractId,String uName)throws Exception{
		
		String[] fcrAndAllMoney = new String[8];
		String insert_sql = "INSERT INTO FS_FARMERREWARDCOST(ID,FARMERBILL,REWARD,STANDWEIGHT,AVGWEIGHT,NETGAIN,QUANTITY,PRICE,MONEY,BATCH,STANDFCR,CREATEUSER,CREATEDATE) " +
			                "VALUES(FS_BALANCE_SEQUENCE.NEXTVAL,?,?,?,?,?,?,?,?,?,?,'"+uName+"',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'))";
		//1.获取猪苗平均重量
		String avglet="select sum(t.weight),sum(t.quantity) from fs_pigpurchase t where t.batch='"+batch+"' and t.checkstatus='1'";
		//1.1 获取免责死亡的头数与重量
		String deadNoFarmerSql="select sum(t.totalweight),sum(t.totalquan) from fs_deathbill t where t.batch='"+batch+"' and t.checkstatus='1' and t.iscordeath='Y'";
		
		//2.获取不同级别出栏猪单价以及不同级别出栏猪数量
		String avgSalepig="select sum(t.quantity),sum(t.weight) from fs_farmersalebilldtl t join fs_farmersalebill f on(f.id=t.farmersale) where f.batch='"+batch+"' and f.checkstatus='1'";
		//3.获取饲料总重量
		String allFeed ="select sum(t.quantity) from fs_feedbilldtl t join fs_feedbill f on(f.id=t.feedbill) where f.batch='"+batch+"' and f.checkstatus='1'";
		
		//4.获取正品猪的合同销售价
		String ApriceSql = "select t.price from fs_contactpigpricedtl t where t.contract='"+contractId+"' and t.piglevel='A'";
		
		String allMany = "0";
		PreparedStatement ps = null;
		try{
			ps =jdbc.createPreparedStmt(insert_sql,connection);
			Object[][] avgpigobj = jdbc.Querry(avglet, connection);
			Object[][] avgsaleobj = jdbc.Querry(avgSalepig, connection);
			Object[][] allfeedobj = jdbc.Querry(allFeed, connection);
			
			//猪苗所需参数
			String allPigWeight = "0";
			String avgpigWeight = "0";
			String inAllHeadNum = "0";
			if(avgpigobj.length>0){
				allPigWeight = avgpigobj[0][0]==null?"0":avgpigobj[0][0].toString();
				inAllHeadNum = avgpigobj[0][1]==null?"0":avgpigobj[0][1].toString();
				
				if(PapUtil.checkDouble(allPigWeight) && PapUtil.checkDouble(inAllHeadNum) && ArithUtil.comparison(allPigWeight, "0") != 0 && ArithUtil.comparison(inAllHeadNum, "0") != 0){
					avgpigWeight = ArithUtil.div(allPigWeight, inAllHeadNum,2);
					
					//获取免责死亡的头数与重量
					Object[][] nodeadobj = jdbc.Querry(deadNoFarmerSql, connection);
					if(nodeadobj.length>0){
						String mweight = nodeadobj[0][0]==null?"0":nodeadobj[0][0].toString();
						String mhead = nodeadobj[0][1]==null?"0":nodeadobj[0][1].toString();
						
						if(PapUtil.checkDouble(mweight) && PapUtil.checkDouble(mhead) && ArithUtil.comparison(mweight, "0") != 0 && ArithUtil.comparison(mhead, "0") != 0){
							allPigWeight = ArithUtil.sub(allPigWeight, mweight);
							inAllHeadNum = ArithUtil.sub(inAllHeadNum, mhead);
							if(ArithUtil.comparison(inAllHeadNum, "0") != 0){
								avgpigWeight = ArithUtil.div(allPigWeight, inAllHeadNum,2);
							}
						}
					}
				}else
					throw new SystemException("采购猪苗头数与重量为空或等于0");
			}
			
			//出栏猪参数
			String allSaleWeight="0";
			String allSaleNum="0";
			
			if(avgsaleobj.length>0){
				allSaleNum = avgsaleobj[0][0]==null?"0":avgsaleobj[0][0].toString();
				allSaleWeight = avgsaleobj[0][1]==null?"0":avgsaleobj[0][1].toString();
			}
			//饲料总重量
			String allFeedWeight = "0";
			if(allfeedobj.length>0){
				allFeedWeight = allfeedobj[0][0]==null?"0":allfeedobj[0][0].toString();
			}
			//计算料肉比  总饲料/(总出栏重-总猪苗中)
			String netGain = ArithUtil.sub(allSaleWeight, allPigWeight);
			String raleFcr = "0";
			if(netGain != null && ArithUtil.comparison(netGain, "0") !=0)
				raleFcr = ArithUtil.div(allFeedWeight, netGain,4);
			
			//获取正品猪单价
			Object[][] ApObject = jdbc.Querry(ApriceSql, connection);
			String Aprice = "0";
			if(ApObject.length>0){
				Aprice = allfeedobj[0][0]==null?"0":ApObject[0][0].toString();
			}
			//----计算猪苗增减费用
			//1.首先获取采购均重与公司标准之间的差值
			if(pigWeight != null && PapUtil.checkDouble(pigWeight) && ArithUtil.comparison(pigWeight, "0") != 0){
				String letweight = ArithUtil.sub(avgpigWeight, pigWeight);
			    //计算 公式  letweight * （出栏数量 * 正品单价）
			    String _value = ArithUtil.mul(letweight, allSaleNum,6);
			    String _allValue = ArithUtil.mul(_value, Aprice,2);
			    
			    //保存对象
			    ps.setString(1,null);//账单号
				ps.setString(2,"PIGLETREWARD");//奖罚类型
				ps.setString(3,pigWeight);//标准重
				ps.setString(4,avgpigWeight);//均重
				ps.setString(5,letweight);//净增重
				ps.setString(6,allSaleNum);//数量
				ps.setString(7,Aprice);//单件
				ps.setString(8,_allValue);//金额
				ps.setString(9,batch);//批次
				ps.setString(10,null);//批次
				
				ps.addBatch();
				
				allMany = ArithUtil.add(allMany,_allValue);
			}
			//----计算出栏猪增减费用
			//1.计算出栏猪均重
			String avgSaleWeight = "0";
			if(allSaleNum != null && ArithUtil.comparison(allSaleNum, "0") != 0)
				avgSaleWeight = ArithUtil.div(allSaleWeight, allSaleNum,2);
			if(pigSaleWeight != null && PapUtil.checkDouble(pigSaleWeight) && ArithUtil.comparison(pigSaleWeight, "0") != 0){
				//2.计算 公式  （出栏猪均重-养殖公司设定的标准重）*出栏猪数量*市场价
				//获取净增重 
				String _netg = ArithUtil.sub(avgSaleWeight, pigSaleWeight);
				//如果均重小于或等于公司标准，不做处理
				if(ArithUtil.comparison(_netg, "0")==1){
					//计算 出栏猪数量*（正品猪收购价-市场价）
					if(Aprice==null || !PapUtil.checkDouble(Aprice) || ArithUtil.comparison(Aprice, "0")==0)
						throw new SystemException("正品猪销售价必须为数值且不为0");
					if(marketPrice==null || !PapUtil.checkDouble(marketPrice) || ArithUtil.comparison(marketPrice, "0")==0)
						throw new SystemException("销售市场价必须为数值且不为0");
					String standPrice = ArithUtil.sub(Aprice, marketPrice);
					if(ArithUtil.comparison(standPrice, "0") ==-1)
						throw new SystemException("正品猪销售价小于设置的市场价");
					String _vale = ArithUtil.mul(allSaleNum, standPrice);
					//计算费用 
					String salePigCost = ArithUtil.mul(_netg, _vale,2);
					 //保存对象
				    ps.setString(1,null);//账单号
					ps.setString(2,"FATPIGREWARD");//奖罚类型
					ps.setString(3,pigSaleWeight);//标准重
					ps.setString(4,avgSaleWeight);//均重
					ps.setString(5,_netg);//净增重
					ps.setString(6,allSaleNum);//数量
					ps.setString(7,standPrice);//单件
					ps.setString(8,salePigCost);//金额
					ps.setString(9,batch);//批次
					ps.setString(10,null);
					
					ps.addBatch();
					allMany = ArithUtil.add(allMany,salePigCost);
				}
			}
			//-----计算料肉比费用
			//0.计算标准料肉比
			String fcr = fcrService.getFcr(avgpigWeight, avgSaleWeight);
			//1.先判断标准料肉比是不是为空
			if(fcr!=null && PapUtil.checkDouble(fcr) && ArithUtil.comparison(fcr, "0")!=0){
				//--1.计算料肉比差异值
				String offer = ArithUtil.sub(fcr, raleFcr);
				//--2.判断允许偏差值是不是为空 如果不为空计算一级偏移费用
				//计算 净增重/1000
				String _neg = ArithUtil.div(netGain, 1000+"",6);
				Boolean isNeg = false; 
				if(ArithUtil.comparison(offer, "0")==-1)
					isNeg = true;
				offer = ArithUtil.abs(offer);
				if(standOffset != null && !"".equals(standOffset)){
					String all="0";
					if(ArithUtil.comparison(offer, standOffset)==1){
						
						String allowOffer = ArithUtil.sub(offer, standOffset);
						//计算 差异值*一级扣费
						String _oneV = ArithUtil.mul(allowOffer, onePrice,6);
						String oneMoney = ArithUtil.mul(_oneV, _neg,2);
						all = ArithUtil.add(all, oneMoney);
					}
					if(isNeg)
						all = ArithUtil.sub("0", all);
					//保存对象
					ps.setString(1,null);//账单号
					ps.setString(2,"FCRREWARD");//奖罚类型
					ps.setString(3,fcr);//标准重
					ps.setString(4,raleFcr);//均重
					if(isNeg)
						ps.setString(5,"-"+offer);//净增重
					else
						ps.setString(5,offer);//净增重
					
					ps.setString(6,_neg);//数量
					ps.setString(7,onePrice);//单件
					ps.setString(8,all);//金额
					ps.setString(9,batch);//批次
					ps.setString(10,standOffset);//偏移量
					
					ps.addBatch();
					allMany = ArithUtil.add(allMany,all);
					   
				}
				if(oneOffset != null && !"".equals(oneOffset)){
					String all="0";
					if(ArithUtil.comparison(offer, oneOffset)==1){
						String _oneOffer = ArithUtil.sub(offer, oneOffset);
						//计算 二级差异值*二级扣费
						String _superV = ArithUtil.mul(_oneOffer, superPrice,6);
						
						String superMoney = ArithUtil.mul(_superV, _neg,2);
						all = ArithUtil.add(all, superMoney);
					}
					if(isNeg)
						all = ArithUtil.sub("0", all);
					//保存对象
					ps.setString(1,null);//账单号
					ps.setString(2,"FCRREWARD");//奖罚类型
					ps.setString(3,fcr);//标准重
					ps.setString(4,raleFcr);//均重
					if(isNeg)
						ps.setString(5,"-"+offer);//净增重
					else
						ps.setString(5,offer);//净增重
					ps.setString(6,_neg);//数量
					ps.setString(7,superPrice);//单件
					ps.setString(8,all);//金额
					ps.setString(9,batch);//批次
					ps.setString(10,oneOffset);//偏移量
					
					ps.addBatch();
					allMany = ArithUtil.add(allMany,all);
				}
			}
			
			fcrAndAllMoney[0] = allMany;
			fcrAndAllMoney[1] = raleFcr;
			fcrAndAllMoney[2] = avgpigWeight;
			fcrAndAllMoney[3] = inAllHeadNum;
			fcrAndAllMoney[4] = allSaleWeight;
			fcrAndAllMoney[5] = allSaleNum;
			fcrAndAllMoney[6] = avgSaleWeight;
			fcrAndAllMoney[7] = netGain;
			//执行sql
			ps.executeBatch();
		}finally{
			if(ps != null)
				ps.close();
			ps=null;
		}
		return fcrAndAllMoney;
	}
	
	/**
	 * 功能：撤销<br/>
	 *
	 * @author DZL 
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void cancelCheck(String id)throws Exception{
		//验证公司结算是不是已经完成 已经完成不允许撤销
		FarmerBalance f = super.selectById(id);
		if(f == null)
			throw new SystemException("对象不存在");
		Batch batch = f.getBatch();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("batch.id", "=", batch.getId()+"");
		sqlMap.put("checkStatus", "=", "1");
		
		int num = companyBalanceService.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(num>0)
			throw new SystemException("该批次已经进行养殖公司结算，不允许撤销");
		
		//将所有单据结算状态解除
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isBalance", "N");
		
		sqlMap.put("batch.id", "=", batch.getId()+"");
		
		feedBillService.updateByCod(sqlMap,map);
		materialBillService.updateByCod(sqlMap,map);
		medicalBillService.updateByCod(sqlMap,map);
		pigPurchaseService.updateByCod(sqlMap,map);
		deathBillService.updateByCod(sqlMap,map);
		//eliminateBillService.updateByCod(sqlMap,map);
		farmerSaleService.updateByCod(sqlMap,map);
		outPigstyService.updateByCod(sqlMap,map);
		drugBillService.updateByCod(sqlMap,map);
		
		batch.setIsBalance("N");
		batch.setFcr(null);
		//删除账单
		farmerBillService.deleteBySingletAll("batch.id", batch.getId());
		farmerSaleIncomeService.deleteBySingletAll("batch.id", batch.getId());
		farmerFeedCostService.deleteBySingletAll("batch.id", batch.getId());
		farmerDrugsCostService.deleteBySingletAll("batch.id", batch.getId());
		farmerOtherCostService.deleteBySingletAll("batch.id", batch.getId());
		farmerPigletCostService.deleteBySingletAll("batch.id", batch.getId());
		farmerRewardCostService.deleteBySingletAll("batch.id", batch.getId());
		//更改状态
		f.setCheckStatus("0");
		f.setCheckDate(null);
		f.setCheckUser(null);
	}

	/**
	 * 
	 * 功能:下载模板
	 * 重写:wxb
	 * 2017-9-12
	 * @see com.zd.foster.balance.services.IFarmerBalanceService#downloadTemplate(java.lang.String)
	 */
	@Override
	public InputStream downloadTemplate(String realPath) throws Exception {
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "farmerBalance.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
	}

	/**
	 * 
	 * 功能:导入代养户结算单
	 * 重写:wxb
	 * 2017-9-13
	 * @see com.zd.foster.balance.services.IFarmerBalanceService#operateFile(java.io.File, com.zd.foster.base.entity.Company, java.lang.Object[])
	 */
	@Override
	public List<FarmerBalance> operateFile(File doc, Company company,List<String> idList,
			Object... objects) throws Exception {
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		List<FarmerBalance> details = new ArrayList<FarmerBalance>();
//		List<String> idList=new ArrayList<String>();
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
							sb.append(new SystemException("第"+(i+1)+"行不存在代养户【"+farmer+"】"));
							continue;
						}else
							fr=fList.get(0);
						//验证批次
						Batch bh=new Batch();
						String hql_batch="from Batch e where e.company.id='"+company.getId()+"'and e.farmer.name='"+farmer+"'and e.batchNumber='"+batch+"'";
						List<Batch> bList=batchService.selectByHQL(hql_batch);
						if(bList==null || bList.isEmpty()){
							sb.append(new SystemException("第"+(i+1)+"行不存在代养户【"+farmer+"】批次【"+batch+"】"));
							continue;
						}else
							bh=bList.get(0);
						FarmerBalance fb=new FarmerBalance();
						fb.setCompany(company);
						fb.setFarmer(fr);
						fb.setBatch(bh);
						fb.setRegistDate(registDate);
						//改变合同状态
						String id=bh.getContract().getId();
						try{
							contractService.enableEffect(id);
						}catch(Exception e){
							sb.append(new SystemException("第"+(i+1)+"行"+e.getMessage()));
							continue;
						}
						Object obj=null;
						try{
							obj=this.save(fb);
						}catch(Exception e){
							sb.append(new SystemException("第"+(i+1)+"行"+e.getMessage()));
							continue;
						}
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
