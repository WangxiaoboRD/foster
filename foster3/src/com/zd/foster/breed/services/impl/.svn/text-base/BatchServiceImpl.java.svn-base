package com.zd.foster.breed.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.entity.TechBatch;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.base.services.ITechBatchService;
import com.zd.foster.breed.dao.IBatchDao;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IFeedBillService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.material.entity.FeedType;
import com.zd.foster.material.services.IFeedTypeService;
import com.zd.foster.warehouse.services.IFeedInWareService;

/**
 * 类名：  BatchServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class BatchServiceImpl extends BaseServiceImpl<Batch, IBatchDao> implements IBatchService {

	@Resource
	private JDBCWrapperx jdbc;
	@Resource
	private IPigPurchaseService pigPurchaseService;
	@Resource
	private IFeedBillService feedBillService;
	@Resource
	private IFeedInWareService feedInWareService;
	@Resource
	private IFeedTypeService feedTypeService;
	@Resource
	private ITechBatchService techBatchService;
	
	
	@Override
	public List<Batch> selectAll(Batch entity) throws Exception {
		if(entity.getContract()!=null){
			if(entity.getContract().getStatus().getDcode()!=null && !"".equals(entity.getContract().getStatus().getDcode())){
				String str=entity.getContract().getStatus().getDcode();
				if(str.contains(",")){
					String[] arr=str.split(",");
					List<Batch> bList= new ArrayList<Batch>();
					for(String s:arr){
						entity.getMap().put("e.contract.status.dcode", s);
						bList.addAll(super.selectAll(entity));
					}
					return bList;
				}
			}
		}
		
		return super.selectAll(entity);
	}
	
	/**
	 * 功能: <br/>
	 * 从页面上获取数据封装对象进行更新
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：May 19, 2013 10:14:17 AM<br/>
	 * 
	 * @param entity
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseService#updateHql(com.zhongpin.pap.base.BaseEntity)
	 */
	public int updateHql(Batch entity)throws Exception{
		Batch b = super.selectById(entity.getId());
		Farmer f = b.getFarmer();
		if(f != null)
			f.setTechnician(entity.getTechnician());
		//生成新的 技术员批次列表
		//跟新最后一个技术员的结束时间
		SqlMap<String,String,Object> sqlMap = new SqlMap<String,String,Object>();
		sqlMap.put("batch.id", "=", b.getId());
		sqlMap.put("endDate", "is null", "");
		
		List<TechBatch> tlist = techBatchService.selectHQL(sqlMap);
		sqlMap.clear();
		if(tlist != null && !tlist.isEmpty()){
			TechBatch tb = tlist.get(0);
			tb.setEndDate(PapUtil.notFullDate(new Date()));
		}
		
		//生成技术员批次列表
		TechBatch tb = new TechBatch();
		tb.setCompany(b.getCompany());
		tb.setFarmer(b.getFarmer());
		tb.setBatch(b);
		tb.setTechnician(entity.getTechnician());
		tb.setStartDate(PapUtil.notFullDate(new Date()));
		
		techBatchService.save(tb);
		
		return super.updateHql(entity);
	}
	/**
	 * 代养跟进报表
	 * @return
	 * @throws Exception
	 */
	public void selectCurrentBatchByPage(Batch batch,Pager<Batch> page)throws Exception{
		if(batch.getCompany() == null || batch.getCompany().getId() == null || "".equals(batch.getCompany().getId())){
			throw new SystemException("请选择养殖公司");
		}
		
		//查找在养客户
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(batch.getCompany()!=null && batch.getCompany().getId()!=null && !"".equals(batch.getCompany().getId()))
			sqlMap.put("company.id", "=", batch.getCompany().getId());
		else
			throw new SystemException("养殖公司不能为空！");
		if(batch.getFarmer()!=null && batch.getFarmer().getId()!=null && !"".equals(batch.getFarmer().getId()))
			sqlMap.put("farmer.id", "=", batch.getFarmer().getId());
		if( batch.getId()!=null && !"".equals(batch.getId()))
			sqlMap.put("id", "=", batch.getId());
		//sqlMap.put("pigletQuan", "is not", null);
		//sqlMap.put("quantity", "<>", "0");
		sqlMap.put("contract.status.dcode", "=", "BREED");
		sqlMap.put("isBalance", "=", "N");
		sqlMap.put("id", "order by", "desc");
		sqlMap.put("contract.feedFac,technician,inDate", "order by", "asc");
		selectHQL(sqlMap,page);
		sqlMap.clear();
		
		//Map<String,Object> m = batch.getMap();
		//m.put("isBalance", "N");
		//m.put("farmer.id,batchNumber", "order by");
		//batch.setIsBalance("N");
		//selectAll(batch,page);
		List<Batch> batchs = new ArrayList<Batch>();
		
		List<Batch> blist = page.getResult();
		//获取当前日期
		String nowDate = PapUtil.notFullDate(new Date());
		String quaryDate = batch.getRegistDate();
		if(quaryDate==null || "".equals(quaryDate))
			quaryDate = nowDate;
		//获取所有当日死亡猪头数
		String currentDatesql = "select sum(t.totalquan) from FS_DEATHBILL t where t.batch='###' and t.checkstatus='1' and t.registdate='"+quaryDate+"' ";
		String reasonSql = "select count(1),f.iscordeath,fs.name from fs_deathbilldtl t join fs_deathbill f on(f.id=t.deathbill) join fs_deathreason fs on(fs.id=t.reason)  where f.checkstatus='1' and f.batch='###' and f.registdate<='"+quaryDate+"' group by fs.name,f.iscordeath";
		//获取所有本月度饲料数
		//获取月份
		String month = quaryDate.substring(0, 7);
		String currentMonthFeedSql = "select sum(t.quantity) from fs_feedbilldtl t join fs_feedbill f on (f.id=t.feedbill) where f.checkstatus='1' and f.batch='###' and f.registdate like '"+month+"%'";
		//当月领料量
		String currentMonthInSql = "select sum(t.quantity) from fs_feedinwaredtl t join fs_feedinware f on(f.id=t.feedinware) where f.checkstatus='1' and f.batch='###' and f.registdate like '"+month+"%'";
		//查询猪苗来源与头数
		String pigSourceSql="select sum(t.quantity)+sum(nvl(t.givequantity,0)),cv.name from fs_pigpurchase t left join fs_custvender cv on (cv.id=t.pigletsupplier) where t.batch='###' and t.checkstatus='1' and t.registdate<='"+quaryDate+"' group by cv.name";
		//查询销售头数
		String saleSql="select sum(t.quantity),t.saletype from fs_outpigsty t where t.batch='###' and t.checkstatus='1' and t.registdate<='"+quaryDate+"' group by t.saletype";
		Connection connection = null;
		try{
			connection = jdbc.getConnection();
			if(blist != null && !blist.isEmpty()){
				if(nowDate.equals(quaryDate)){
					for(Batch b : blist){
						Batch bat = new Batch();
						
						Farmer f = b.getFarmer();
						//获取当日死亡头数
						String _currentDatesql = currentDatesql.replace("###", b.getId()+"");
						Object[][] obj = jdbc.Querry(_currentDatesql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentDeadNum(obj[0][0]==null?"":obj[0][0].toString());
						//当月饲料量
						obj = null;
						String _currentMonthFeedSql = currentMonthFeedSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_currentMonthFeedSql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentFeed(obj[0][0]==null?"0":obj[0][0].toString());
						//当月饲料领用量
						obj = null;
						String _currentMonthInSql = currentMonthInSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_currentMonthInSql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentInFeed(obj[0][0]==null?"0":obj[0][0].toString());
						//代养户名称
						bat.setFarmerName(f.getName());
						//所属饲料厂
						bat.setArea(b.getContract().getFeedFac().getName());
						//死亡原因
						obj = null;
						String _reasonSql = reasonSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_reasonSql, connection);
						if(obj!=null && obj.length>0){
							String v = "";
							for(int i=0;i<obj.length;i++){
								if(obj[i][0] != null){
									if("N".equals(obj[i][1]+"")){
										v += obj[i][2]+""+obj[i][0]+",";
									}
								}
							}
							if(v!=null && !"".equals(v))
								bat.setDeadReason(v.substring(0,v.length()-1));
						}
						//猪苗来源
						obj = null;
						String _pigSourceSql = pigSourceSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_pigSourceSql, connection);
						if(obj!=null && obj.length>0){
							String v = "";
							for(int i=0;i<obj.length;i++){
								if(obj[i][1] != null){
									v += obj[i][1]+",";
								}
							}
							if(!"".equals(v))
								bat.setPigSource(v.substring(0,v.length()-1));
						}
						//计算死淘率    （死亡数+淘汰数） / （进猪总数-免责死亡数）
						String dhead = b.getDeathQuan()==null?"0": b.getDeathQuan();
						String ehead = b.getEliminateQuan()==null?"0": b.getEliminateQuan();
						String deadEHead = "0";
						if(PapUtil.checkDouble(dhead) && PapUtil.checkDouble(ehead))
							deadEHead = ArithUtil.add(dhead,ehead);
						String mhead = b.getOtherDeathQuan()==null?"0":b.getOtherDeathQuan();
						String inhead = b.getPigletQuan()==null?"0":b.getPigletQuan();
						String in="0";
						if(PapUtil.checkDouble(mhead) && PapUtil.checkDouble(inhead))
							in = ArithUtil.sub(inhead, mhead);
						String deadRate = "0%";
						if(ArithUtil.comparison(in, "0") != 0){
							deadRate = ArithUtil.percent(ArithUtil.div(deadEHead, in), 2);
							bat.setDeadRate(deadRate);
						}
						
						bat.setBatchNumber(b.getBatchNumber());
						bat.setInDate(b.getInDate());
						bat.setOutDate(b.getOutDate());
						bat.setPigletQuan(b.getPigletQuan());
						bat.setOtherDeathQuan(b.getOtherDeathQuan());
						bat.setDeathQuan(b.getDeathQuan());
						bat.setSaleQuan(b.getSaleQuan());
						bat.setEliminateQuan(b.getEliminateQuan());
						bat.setQuantity(b.getQuantity());
						bat.setTechnician(b.getTechnician());
						bat.setDevelopMan(b.getDevelopMan());
						batchs.add(bat);
					}
				}else{
					for(Batch b : blist){
						Batch bat = new Batch();
						
						Farmer f = b.getFarmer();
						//获取当日死亡头数
						String _currentDatesql = currentDatesql.replace("###", b.getId()+"");
						Object[][] obj = jdbc.Querry(_currentDatesql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentDeadNum(obj[0][0]==null?"":obj[0][0].toString());
						//当月饲料量
						obj = null;
						String _currentMonthFeedSql = currentMonthFeedSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_currentMonthFeedSql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentFeed(obj[0][0]==null?"":obj[0][0].toString());
						//当月饲料领用量
						obj = null;
						String _currentMonthInSql = currentMonthInSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_currentMonthInSql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentInFeed(obj[0][0]==null?"0":obj[0][0].toString());
						//代养户名称
						bat.setFarmerName(f.getName());
						//所属饲料厂
						bat.setArea(b.getContract().getFeedFac().getName());
						//死亡原因
						obj = null;
						String _reasonSql = reasonSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_reasonSql, connection);
						if(obj!=null && obj.length>0){
							String deadHead="0";
							String mHead="0";
							
							String v = "";
							for(int i=0;i<obj.length;i++){
								if(obj[i][0] != null){
									if(!PapUtil.checkDouble(obj[i][0].toString()))
										continue;
									if("N".equals(obj[i][1]+"")){
										v += obj[i][2]+""+obj[i][0]+",";
										deadHead = ArithUtil.add(deadHead, obj[i][0].toString());
									}else{
										mHead = ArithUtil.add(mHead, obj[i][0].toString());
									}
								}
							}
							bat.setDeathQuan(deadHead);
							bat.setOtherDeathQuan(mHead);
							if(v!=null && !"".equals(v))
								bat.setDeadReason(v.substring(0,v.length()-1));
						}
						//猪苗来源与进猪头数
						obj = null;
						String _pigSourceSql = pigSourceSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_pigSourceSql, connection);
						if(obj!=null && obj.length>0){
							String inHead = "0";
							String v = "";
							for(int i=0;i<obj.length;i++){
								if(obj[i][0]!=null){
									if(PapUtil.checkDouble(obj[i][0].toString())){
										inHead = ArithUtil.add(inHead,obj[i][0].toString());
									}
								}
								if(obj[i][1] != null){
									v += obj[i][1]+",";
								}
							}
							bat.setPigletQuan(inHead);
							if(v!=null && !"".equals(v))
								bat.setPigSource(v.substring(0,v.length()-1));
						}
						//获取淘汰,销售数据
						obj = null;
						String _saleSql = saleSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_saleSql, connection);
						if(obj!=null && obj.length>0){
							String saleHead = "0";
							String ehead = "0";
							for(int i=0;i<obj.length;i++){
								if(obj[i][0]!=null){
									if(PapUtil.checkDouble(obj[i][0].toString())){
										saleHead = ArithUtil.add(saleHead,obj[i][0].toString());
										if("E".equals(obj[i][1]+"")){
											ehead = ArithUtil.add(ehead,obj[i][0].toString());
										}
									}
								}
							}
							bat.setSaleQuan(saleHead);
							bat.setEliminateQuan(ehead);
						}
						//计算库存 进猪总数-免责死亡-死亡-销售
						String inhead = bat.getPigletQuan()==null?"0":bat.getPigletQuan();
						String dhead = bat.getDeathQuan()==null?"0": bat.getDeathQuan();
						String mhead = bat.getOtherDeathQuan()==null?"0":bat.getOtherDeathQuan();
						String ehead = bat.getEliminateQuan()==null?"0": bat.getEliminateQuan();
						String shead = bat.getSaleQuan()==null?"0": bat.getSaleQuan();
						String kucun = "0";
						if(PapUtil.checkDouble(mhead) && PapUtil.checkDouble(inhead) && PapUtil.checkDouble(dhead) && PapUtil.checkDouble(ehead)){
							kucun = ArithUtil.sub(inhead, dhead);
							kucun = ArithUtil.sub(kucun, mhead);
							kucun = ArithUtil.sub(kucun, shead);
						    bat.setQuantity(kucun);
							//计算死淘率    （死亡数+淘汰数） / （进猪总数-免责死亡数）
							String deadEHead =ArithUtil.add(dhead,ehead);
							String in=ArithUtil.sub(inhead, mhead);
							String deadRate = "0%";
							if(ArithUtil.comparison(in, "0") != 0){
								deadRate = ArithUtil.percent(ArithUtil.div(deadEHead, in), 2);
								bat.setDeadRate(deadRate);
							}
						}
						bat.setBatchNumber(b.getBatchNumber());
						bat.setInDate(b.getInDate());
						bat.setOutDate(b.getOutDate());
						bat.setTechnician(b.getTechnician());
						bat.setDevelopMan(b.getDevelopMan());
						batchs.add(bat);
					}
				}
			}
			//构建合计数值 查询时间点之前的所有合计数据 如果有公司加公司 有代养户加代养户
			//进猪头数
			String inheadSql = "select sum(t.quantity) + sum(nvl(t.givequantity, 0)) "+
								  "from fs_pigpurchase t "+
								  "left join fs_batch f on (f.id = t.batch) "+
								  "left join fs_contract c on (c.id = f.contract) "+
								 "where t.checkstatus = '1' "+
								   "and f.isbalance = 'N' "+
								   "and c.status = 'BREED' "+
								   "and t.registdate <= '"+quaryDate+"'";
			
			String currentDeadSql = "select sum(t.totalquan) "+
								  "from FS_DEATHBILL t, fs_batch b,fs_contract c "+
								 "where t.batch = b.id "+
								   "and c.id=b.contract "+
								   "and t.checkstatus = '1' "+
								   "and t.registdate = '"+quaryDate+"' "+
								   "and c.status='BREED' "+
								   "and b.isbalance = 'N' ";
			String deadSql = " select count(1), f.iscordeath "+
							    "from fs_deathbilldtl t "+
				 				"join fs_deathbill f on (f.id = t.deathbill) "+
							    "left join fs_batch b on (b.id = f.batch) "+
							    "left join fs_contract c on (c.id = b.contract) "+
							   "where f.checkstatus = '1' "+
							     "and b.isbalance = 'N' "+
							     "and c.status='BREED' "+
							     "and f.registdate <= '"+quaryDate+"'";
			
			String outSql = "select sum(t.quantity), t.saletype "+
					        "from fs_outpigsty t "+
					        "left join fs_batch f on (f.id = t.batch) "+
					        "left join fs_contract c on (c.id = f.contract) "+
					      "where t.checkstatus = '1' "+
					        "and c.status='BREED' "+
					        "and f.isbalance = 'N' "+
					        "and t.registdate <= '"+quaryDate+"'";
			String monthFeedSql ="select sum(t.quantity) "+
				          "from fs_feedbilldtl t "+
				          "left join fs_feedbill f on (f.id = t.feedbill) "+
				          "left join fs_batch b on (b.id = f.batch) "+
				          "left join fs_contract c on (c.id = b.contract) "+
				         "where f.checkstatus = '1' "+
				           "and b.isbalance = 'N' "+
				           "and c.status='BREED' "+
				           "and f.registdate like '"+month+"%'";
			String monthinfeedSql = "select sum(t.quantity) "+
			             "from fs_feedinwaredtl t "+
			             "join fs_feedinware f on (f.id = t.feedinware) "+
			             "left join fs_batch b on (b.id = f.batch) "+
			             "left join fs_contract c on (c.id = b.contract) "+
			            "where f.checkstatus = '1' "+
			              "and b.isbalance = 'N' "+
			              "and c.status='BREED' "+
			              "and f.registdate like '"+month+"%'";
			//根据条件构造sql
			if(batch != null){
				if(batch.getFarmer() != null && batch.getFarmer().getId() != null && !"".equals(batch.getFarmer().getId())){
					inheadSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
					currentDeadSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
					deadSql += " and f.farmer='"+batch.getFarmer().getId()+"'";
					outSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
					monthFeedSql += " and f.farmer='"+batch.getFarmer().getId()+"'";
					monthinfeedSql += " and f.farmer='"+batch.getFarmer().getId()+"'";
				}
				
				if(batch.getCompany() != null && batch.getCompany().getId() != null && !"".equals(batch.getCompany().getId())){
					inheadSql += " and t.company='"+batch.getCompany().getId()+"'";
					currentDeadSql += " and t.company='"+batch.getCompany().getId()+"'";
					deadSql += " and f.company='"+batch.getCompany().getId()+"'";
					outSql += " and t.company='"+batch.getCompany().getId()+"'";
					monthFeedSql += " and f.company='"+batch.getCompany().getId()+"'";
					monthinfeedSql += " and f.company='"+batch.getCompany().getId()+"'";
				}
			}
			//分组
			deadSql += " group by f.iscordeath";
			outSql += " group by t.saletype";
			//查询数据库 封装对象
			//进猪总头数
			Object[][] _obj = jdbc.Querry(inheadSql, connection);
			String inNum="0";
			if(_obj != null && _obj.length>0){
				inNum = _obj[0][0]==null?"0":_obj[0][0].toString();
			}
			//当天死亡头数
			_obj=null;
			_obj = jdbc.Querry(currentDeadSql, connection);
			String currentDeadHead="0";
			if(_obj != null && _obj.length>0){
				currentDeadHead = _obj[0][0]==null?"0":_obj[0][0].toString();
			}
			//总死亡头数
			_obj=null;
			_obj = jdbc.Querry(deadSql, connection);
			String mnum ="0";
			String dnum = "0";
			if(_obj != null && _obj.length>0){
				for(int i=0;i<_obj.length;i++){
					if("N".equals(_obj[i][1]+"")){
						dnum = ArithUtil.add(dnum, _obj[i][0]+"");
					}else{
						mnum = ArithUtil.add(mnum, _obj[i][0]+"");
					}
				}
			}
			
			//总销售 淘汰头数
			_obj=null;
			_obj = jdbc.Querry(outSql, connection);
			String saleNum ="0";
			String eNum = "0";
			if(_obj != null && _obj.length>0){
				for(int i=0;i<_obj.length;i++){
					if(_obj[i][0] != null && PapUtil.checkDouble(_obj[i][0]+"")){
						if("Q".equals(_obj[i][1]+"")){
							saleNum = ArithUtil.add(saleNum, _obj[i][0]+"");
						}else{
							eNum = ArithUtil.add(eNum, _obj[i][0]+"");
						}
					}
				}
			}
			
			//总饲料数
			_obj = null;
			_obj = jdbc.Querry(monthFeedSql, connection);
			String allFeed="0";
			if(_obj != null && _obj.length>0){
				allFeed = _obj[0][0]==null?"0":_obj[0][0].toString();
			}
			//总领饲料数
			_obj = null;
			_obj = jdbc.Querry(monthinfeedSql, connection);
			String allInFeed="0";
			if(_obj != null && _obj.length>0){
				allInFeed = _obj[0][0]==null?"0":_obj[0][0].toString();
			}
			//计算总库存
			String allKucun="0";
			allKucun = ArithUtil.sub(inNum, saleNum);
			allKucun = ArithUtil.sub(allKucun, mnum);
			allKucun = ArithUtil.sub(allKucun, dnum);
			allKucun = ArithUtil.sub(allKucun, eNum);
			//总的死淘率    （死亡头数+淘汰头数）/(进猪头数-免责死亡头数)
			String allD = ArithUtil.add(dnum, eNum);
			String inD = ArithUtil.sub(inNum, mnum);
			String deadRate = "0%";
			if(ArithUtil.comparison(inD, "0") != 0){
				deadRate = ArithUtil.percent(ArithUtil.div(allD, inD), 2);
			}
			
			//封装对象
			Batch ball = new Batch();
			ball.setOutDate("合计:");
			ball.setCurrentDeadNum(currentDeadHead);
			ball.setQuantity(allKucun);
			ball.setDeathQuan(dnum);
			ball.setOtherDeathQuan(mnum);
			ball.setEliminateQuan(eNum);
			ball.setSaleQuan(ArithUtil.add(saleNum, eNum,0));
			ball.setPigletQuan(inNum);
			ball.setCurrentFeed(allFeed);
			ball.setDeadRate(deadRate);
			ball.setCurrentInFeed(allInFeed);
			
			batchs.add(ball);
			
			page.setResult(batchs);
 		}finally{
			jdbc.destroy(connection);
		}
	}
	
	/**
	 * 代养跟进报表合计
	 * @return
	 * @throws Exception
	 */
	public List<Batch> selectAllByFarmer(Batch batch)throws Exception{
		if(batch.getCompany() == null || batch.getCompany().getId() == null || "".equals(batch.getCompany().getId())){
			throw new SystemException("请选择养殖公司");
		}
		//获取当前日期
		String nowDate = PapUtil.notFullDate(new Date());
		String quaryDate = batch.getRegistDate();
		if(quaryDate==null || "".equals(quaryDate))
			quaryDate = nowDate;
		//构建合计数值 查询时间点之前的所有合计数据 如果有公司加公司 有代养户加代养户
		//养殖户数
		String batchSql="select count(1),f.feedfac,fa.name from fs_batch t " +
					    "left join fs_contract f on(f.id=t.contract) " +
					    "left join fs_feedfac fa on (fa.id=f.feedfac) " +
					    "where t.isbalance='N' and f.status='BREED' ";
		//进猪头数
		String inheadSql = "select sum(t.quantity)+sum(nvl(t.givequantity,0)),c.feedfac,fa.name from fs_pigpurchase t " +
						   "join fs_batch f on(f.id = t.batch) " +
						   "left join fs_contract c on(c.id=f.contract) " +
						   "left join fs_feedfac fa on (fa.id=c.feedfac) " +
						   "where t.checkstatus='1' and f.isbalance='N' and c.status='BREED' and t.registdate<='"+quaryDate+"'";
		
		String deadSql = "select count(1),c.feedfac,fa.name from fs_deathbilldtl t  "+
						"join fs_deathbill f on (f.id = t.deathbill) "+
						"left join fs_batch b on (b.id = f.batch) "+
						"left join fs_contract c on(c.id=b.contract) "+
						"left join fs_feedfac fa on (fa.id=c.feedfac) "+ 
						"where f.checkstatus = '1' and b.isbalance = 'N' and c.status='BREED' and f.registdate <= '"+quaryDate+"'";
		
		String outSql = "select sum(t.quantity),t.saletype,c.feedfac,fa.name from fs_outpigsty t "+
						"left join fs_batch f on(f.id=t.batch) "+
						"left join fs_contract c on(c.id=f.contract) "+
						"left join fs_feedfac fa on (fa.id=c.feedfac) "+
						"where t.checkstatus='1' and f.isbalance='N' and c.status='BREED' and t.registdate<='"+quaryDate+"'";
		
		//根据条件构造sql
		if(batch != null){
			if(batch.getFarmer() != null && batch.getFarmer().getId() != null && !"".equals(batch.getFarmer().getId())){
				
				batchSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
				inheadSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
				deadSql += " and f.farmer='"+batch.getFarmer().getId()+"'";
				outSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
			}
			
			if(batch.getCompany() != null && batch.getCompany().getId() != null && !"".equals(batch.getCompany().getId())){
				
				batchSql += " and t.company='"+batch.getCompany().getId()+"'";
				inheadSql += " and t.company='"+batch.getCompany().getId()+"'";
				deadSql += " and f.company='"+batch.getCompany().getId()+"'";
				outSql += " and t.company='"+batch.getCompany().getId()+"'";
			}
		}
		
		batchSql += " group by f.feedfac,fa.name";
		inheadSql += " group by c.feedfac,fa.name";
		deadSql += " group by c.feedfac,fa.name";
		outSql +=" group by t.saletype,c.feedfac,fa.name";
		
		//查询数据库 封装对象
		Connection connection = null;
		Map<String,Batch> map = null;
		List<Batch> blist = null;
		try{
			connection = jdbc.getConnection();
			//获取养殖户数
			Object[][] _obj = jdbc.Querry(batchSql, connection);
			if(_obj != null && _obj.length>0){
				map = new HashMap<String,Batch>();
				for(int i=0;i<_obj.length;i++){
					Batch b = new Batch();
					if(_obj[i][1] != null){
						b.setArea(_obj[i][2]==null?"":_obj[i][2]+"");
						b.setBatchNumber(_obj[i][1]+"");
						b.setFarmerName(_obj[i][0]==null?"0":_obj[i][0]+"");
						
						map.put(_obj[i][1]+"", b);
					}
				}
			}
			if(map==null || map.isEmpty())
				return blist;
			blist = new ArrayList<Batch>();
			
			//进猪总头数
			_obj = null;
			_obj = jdbc.Querry(inheadSql, connection);
			if(_obj != null && _obj.length>0){
				for(int i=0;i<_obj.length;i++){
					if(_obj[i][1] != null){
						if(map.containsKey(_obj[i][1]+"")){
							Batch b = map.get(_obj[i][1]+"");
							b.setPigletQuan(_obj[i][0]==null?"0":_obj[i][0]+"");
						}
					}
				}
			}
			
			//总死亡头数
			_obj = null;
			_obj = jdbc.Querry(deadSql, connection);
			if(_obj != null && _obj.length>0){
				for(int i=0;i<_obj.length;i++){
					if(_obj[i][1] != null){
						if(map.containsKey(_obj[i][1]+"")){
							Batch b = map.get(_obj[i][1]+"");
							b.setDeathQuan(_obj[i][0]==null?"0":_obj[i][0]+"");
						}
					}
				}
			}
			
			//总销售 淘汰头数
			_obj=null;
			_obj = jdbc.Querry(outSql, connection);
			if(_obj != null && _obj.length>0){
				for(int i=0;i<_obj.length;i++){
					if(map.containsKey(_obj[i][2]+"")){
						Batch b = map.get(_obj[i][2]+"");
						if("Q".equals(_obj[i][1]+"")){
							b.setSaleQuan(_obj[i][0]==null?"0":_obj[i][0]+"");
						}else{
							b.setEliminateQuan(_obj[i][0]==null?"0":_obj[i][0]+"");
						}
						
						String sale = b.getSaleQuan()==null?"0":b.getSaleQuan();
						String ehead = b.getEliminateQuan()==null?"0":b.getEliminateQuan(); 
						b.setSaleQuan(ArithUtil.add(sale,ehead,0));
					}
				}
			}
			
			//计算总库存 与 成活率
			for (Map.Entry<String, Batch> entry : map.entrySet()) {
				Batch b = entry.getValue();
				//计算库存
				String in = b.getPigletQuan();
				String dead = b.getDeathQuan();
				String sale = b.getSaleQuan();
				
				String kucun = "0";
				if(in != null && PapUtil.checkDouble(in)){
					kucun = in;
					if(dead != null &&  PapUtil.checkDouble(dead)){
						kucun = ArithUtil.sub(kucun,dead);
					}
					if(sale != null &&  PapUtil.checkDouble(sale)){
						kucun = ArithUtil.sub(kucun,sale);
					}
					
				}
				b.setQuantity(kucun);
				//计算成活率 （库存+销售）/进猪数
				if(sale != null &&  PapUtil.checkDouble(sale)){
					kucun = ArithUtil.add(kucun,sale);
				}
				
				if(in != null && PapUtil.checkDouble(in) && ArithUtil.comparison(in, "0")!=0){
					String v = ArithUtil.percent((ArithUtil.div(kucun,in,4)),2);
					b.setDeadRate(v);
				}
				
				blist.add(b);
			}
			return blist;
		}finally{
			jdbc.destroy(connection);
		}
	}
	
	/**
	 * 获取导出值
	 * @param batch
	 * @return
	 * @throws Exception
	 */
	private List<Batch> getExpValues(Batch batch)throws Exception{
		List<Batch> batchs = new ArrayList<Batch>();
		
		//查找在养客户
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(batch.getCompany()!=null && batch.getCompany().getId()!=null && !"".equals(batch.getCompany().getId()))
			sqlMap.put("company.id", "=", batch.getCompany().getId());
		else
			throw new SystemException("养殖公司不能为空！");
		if(batch.getFarmer()!=null && batch.getFarmer().getId()!=null && !"".equals(batch.getFarmer().getId()))
			sqlMap.put("farmer.id", "=", batch.getFarmer().getId());
		if( batch.getId()!=null && !"".equals(batch.getId()))
			sqlMap.put("id", "=", batch.getId());
		//sqlMap.put("pigletQuan", "is not", null);
		//sqlMap.put("quantity", "<>", "0");
		sqlMap.put("contract.status.dcode", "=", "BREED");
		sqlMap.put("isBalance", "=", "N");
		sqlMap.put("id", "order by", "desc");
		sqlMap.put("contract.feedFac,technician,inDate", "order by", "asc");
		List<Batch> blist = selectHQL(sqlMap);
		sqlMap.clear();
		
		//获取当前日期
		String nowDate = PapUtil.notFullDate(new Date());
		String quaryDate = batch.getRegistDate();
		if(quaryDate==null || "".equals(quaryDate))
			quaryDate = nowDate;
		//获取所有当日死亡猪头数
		String currentDatesql = "select sum(t.totalquan) from FS_DEATHBILL t where t.batch='###' and t.checkstatus='1' and t.registdate='"+quaryDate+"' ";
		String reasonSql = "select count(1),f.iscordeath,fs.name from fs_deathbilldtl t join fs_deathbill f on(f.id=t.deathbill) join fs_deathreason fs on(fs.id=t.reason)  where f.checkstatus='1' and f.batch='###' and f.registdate<='"+quaryDate+"' group by fs.name,f.iscordeath";
		//获取所有本月度饲料数
		//获取月份
		String month = quaryDate.substring(0, 7);
		String currentMonthFeedSql = "select sum(t.quantity) from fs_feedbilldtl t join fs_feedbill f on (f.id=t.feedbill) where f.checkstatus='1' and f.batch='###' and f.registdate like '"+month+"%'";
		//当月领料量
		String currentMonthInSql = "select sum(t.quantity) from fs_feedinwaredtl t join fs_feedinware f on(f.id=t.feedinware) where f.checkstatus='1' and f.batch='###' and f.registdate like '"+month+"%'";
		//查询猪苗来源与头数
		String pigSourceSql="select sum(t.quantity)+sum(nvl(t.givequantity,0)),cv.name from fs_pigpurchase t left join fs_custvender cv on (cv.id=t.pigletsupplier) where t.batch='###' and t.checkstatus='1' and t.registdate<='"+quaryDate+"' group by cv.name";
		//查询销售头数
		String saleSql="select sum(t.quantity),t.saletype from fs_outpigsty t where t.batch='###' and t.checkstatus='1' and t.registdate<='"+quaryDate+"' group by t.saletype";
		Connection connection = null;
		try{
			connection = jdbc.getConnection();
			if(blist != null && !blist.isEmpty()){
				if(nowDate.equals(quaryDate)){
					for(Batch b : blist){
						Batch bat = new Batch();
						
						Farmer f = b.getFarmer();
						//获取当日死亡头数
						String _currentDatesql = currentDatesql.replace("###", b.getId()+"");
						Object[][] obj = jdbc.Querry(_currentDatesql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentDeadNum(obj[0][0]==null?"":obj[0][0].toString());
						//当月饲料量
						obj = null;
						String _currentMonthFeedSql = currentMonthFeedSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_currentMonthFeedSql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentFeed(ArithUtil.div(obj[0][0]==null?"0":obj[0][0].toString(), "1000",3));
						//当月领饲料量
						obj = null;
						String _currentMonthInSql = currentMonthInSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_currentMonthInSql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentInFeed(ArithUtil.div(obj[0][0]==null?"0":obj[0][0].toString(), "1000",3));
						//代养户名称
						bat.setFarmerName(f.getName());
						//所属饲料厂
						bat.setArea(b.getContract().getFeedFac().getName());
						//死亡原因
						obj = null;
						String _reasonSql = reasonSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_reasonSql, connection);
						if(obj!=null && obj.length>0){
							String v = "";
							for(int i=0;i<obj.length;i++){
								if(obj[i][0] != null){
									if("N".equals(obj[i][1]+"")){
										v += obj[i][2]+""+obj[i][0]+",";
									}
								}
							}
							if(v!=null && !"".equals(v))
								bat.setDeadReason(v.substring(0,v.length()-1));
						}
						//猪苗来源
						obj = null;
						String _pigSourceSql = pigSourceSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_pigSourceSql, connection);
						if(obj!=null && obj.length>0){
							String v = "";
							for(int i=0;i<obj.length;i++){
								if(obj[i][1] != null){
									v += obj[i][1]+",";
								}
							}
							if(v!=null && !"".equals(v))
								bat.setPigSource(v.substring(0,v.length()-1));
						}
						//计算死淘率    （死亡数+淘汰数） / （进猪总数-免责死亡数）
						String dhead = b.getDeathQuan()==null?"0": b.getDeathQuan();
						String ehead = b.getEliminateQuan()==null?"0": b.getEliminateQuan();
						String deadEHead = "0";
						if(PapUtil.checkDouble(dhead) && PapUtil.checkDouble(ehead))
							deadEHead = ArithUtil.add(dhead,ehead);
						String mhead = b.getOtherDeathQuan()==null?"0":b.getOtherDeathQuan();
						String inhead = b.getPigletQuan()==null?"0":b.getPigletQuan();
						String in="0";
						if(PapUtil.checkDouble(mhead) && PapUtil.checkDouble(inhead))
							in = ArithUtil.sub(inhead, mhead);
						String deadRate = "0%";
						if(ArithUtil.comparison(in, "0") != 0){
							deadRate = ArithUtil.percent(ArithUtil.div(deadEHead, in), 2);
							bat.setDeadRate(deadRate);
						}
						
						bat.setBatchNumber(b.getBatchNumber());
						bat.setInDate(b.getInDate());
						bat.setOutDate(b.getOutDate());
						bat.setPigletQuan(b.getPigletQuan());
						bat.setOtherDeathQuan(b.getOtherDeathQuan());
						bat.setDeathQuan(b.getDeathQuan());
						bat.setSaleQuan(b.getSaleQuan());
						bat.setEliminateQuan(b.getEliminateQuan());
						bat.setQuantity(b.getQuantity());
						bat.setTechnician(b.getTechnician());
						bat.setDevelopMan(b.getDevelopMan());
						batchs.add(bat);
					}
				}else{
					for(Batch b : blist){
						Batch bat = new Batch();
						
						Farmer f = b.getFarmer();
						//获取当日死亡头数
						String _currentDatesql = currentDatesql.replace("###", b.getId()+"");
						Object[][] obj = jdbc.Querry(_currentDatesql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentDeadNum(obj[0][0]==null?"":obj[0][0].toString());
						//当月饲料量
						obj = null;
						String _currentMonthFeedSql = currentMonthFeedSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_currentMonthFeedSql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentFeed(ArithUtil.div(obj[0][0]==null?"0":obj[0][0].toString(), "1000",3));
						//当月领饲料量
						obj = null;
						String _currentMonthInSql = currentMonthInSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_currentMonthInSql, connection);
						if(obj!=null && obj.length>0)
							bat.setCurrentInFeed(ArithUtil.div(obj[0][0]==null?"0":obj[0][0].toString(), "1000",3));
						
						//代养户名称
						bat.setFarmerName(f.getName());
						//所属饲料厂
						bat.setArea(b.getContract().getFeedFac().getName());
						//死亡原因
						obj = null;
						String _reasonSql = reasonSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_reasonSql, connection);
						if(obj!=null && obj.length>0){
							String deadHead="0";
							String mHead="0";
							
							String v = "";
							for(int i=0;i<obj.length;i++){
								if(obj[i][0] != null){
									if(!PapUtil.checkDouble(obj[i][0].toString()))
										continue;
									if("N".equals(obj[i][1]+"")){
										v += obj[i][2]+""+obj[i][0]+",";
										deadHead = ArithUtil.add(deadHead, obj[i][0].toString());
									}else{
										mHead = ArithUtil.add(mHead, obj[i][0].toString());
									}
								}
							}
							bat.setDeathQuan(deadHead);
							bat.setOtherDeathQuan(mHead);
							if(v!=null && !"".equals(v))
								bat.setDeadReason(v.substring(0,v.length()-1));
						}
						//猪苗来源与进猪头数
						obj = null;
						String _pigSourceSql = pigSourceSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_pigSourceSql, connection);
						if(obj!=null && obj.length>0){
							String inHead = "0";
							String v = "";
							for(int i=0;i<obj.length;i++){
								if(obj[i][0]!=null){
									if(PapUtil.checkDouble(obj[i][0].toString())){
										inHead = ArithUtil.add(inHead,obj[i][0].toString());
									}
								}
								if(obj[i][1] != null){
									v += obj[i][1]+",";
								}
							}
							bat.setPigletQuan(inHead);
							if(v!=null && !"".equals(v))
								bat.setPigSource(v.substring(0,v.length()-1));
						}
						//获取淘汰,销售数据
						obj = null;
						String _saleSql = saleSql.replace("###", b.getId()+"");
						obj = jdbc.Querry(_saleSql, connection);
						if(obj!=null && obj.length>0){
							String saleHead = "0";
							String ehead = "0";
							for(int i=0;i<obj.length;i++){
								if(obj[i][0]!=null){
									if(PapUtil.checkDouble(obj[i][0].toString())){
										saleHead = ArithUtil.add(saleHead,obj[i][0].toString());
										if("E".equals(obj[i][1]+"")){
											ehead = ArithUtil.add(ehead,obj[i][0].toString());
										}
									}
								}
							}
							bat.setSaleQuan(saleHead);
							bat.setEliminateQuan(ehead);
						}
						//计算库存 进猪总数-免责死亡-死亡-销售
						String inhead = bat.getPigletQuan()==null?"0":bat.getPigletQuan();
						String dhead = bat.getDeathQuan()==null?"0": bat.getDeathQuan();
						String mhead = bat.getOtherDeathQuan()==null?"0":bat.getOtherDeathQuan();
						String ehead = bat.getEliminateQuan()==null?"0": bat.getEliminateQuan();
						String shead = bat.getSaleQuan()==null?"0": bat.getSaleQuan();
						String kucun = "0";
						if(PapUtil.checkDouble(mhead) && PapUtil.checkDouble(inhead) && PapUtil.checkDouble(dhead) && PapUtil.checkDouble(ehead)){
							kucun = ArithUtil.sub(inhead, dhead);
							kucun = ArithUtil.sub(kucun, mhead);
							kucun = ArithUtil.sub(kucun, shead);
						    bat.setQuantity(kucun);
							//计算死淘率    （死亡数+淘汰数） / （进猪总数-免责死亡数）
							String deadEHead =ArithUtil.add(dhead,ehead);
							String in=ArithUtil.sub(inhead, mhead);
							String deadRate = "0%";
							if(ArithUtil.comparison(in, "0") != 0){
								deadRate = ArithUtil.percent(ArithUtil.div(deadEHead, in), 2);
								bat.setDeadRate(deadRate);
							}
						}
						
						bat.setBatchNumber(b.getBatchNumber());
						bat.setInDate(b.getInDate());
						bat.setOutDate(b.getOutDate());
						bat.setTechnician(b.getTechnician());
						bat.setDevelopMan(b.getDevelopMan());
						batchs.add(bat);
					}
				}
			}
			//构建合计数值 查询时间点之前的所有合计数据 如果有公司加公司 有代养户加代养户
			//进猪头数
			String inheadSql = "select sum(t.quantity)+sum(nvl(t.givequantity,0)) from fs_pigpurchase t join fs_batch f on(f.id = t.batch) where t.checkstatus='1' and f.isbalance='N' and t.registdate<='"+quaryDate+"'";
			String currentDeadSql = "select sum(t.totalquan) from FS_DEATHBILL t,fs_batch b where t.batch=b.id and t.checkstatus='1' and t.registdate='"+quaryDate+"' and b.isbalance='N' ";
			String deadSql = "select count(1),f.iscordeath from fs_deathbilldtl t join fs_deathbill f on (f.id = t.deathbill) join fs_batch b on (b.id = f.batch) where f.checkstatus = '1' and b.isbalance = 'N' and f.registdate <= '"+quaryDate+"'";
			String outSql = "select sum(t.quantity),t.saletype from fs_outpigsty t join fs_batch f on(f.id=t.batch) where t.checkstatus='1' and f.isbalance='N' and t.registdate<='"+quaryDate+"'";
			String monthFeedSql ="select sum(t.quantity) from fs_feedbilldtl t join fs_feedbill f on (f.id=t.feedbill) join fs_batch b on(b.id=f.batch) where f.checkstatus='1' and b.isbalance='N' and f.registdate like '"+month+"%'";
			String monthinfeedSql = "select sum(t.quantity) from fs_feedinwaredtl t join fs_feedinware f on(f.id=t.feedinware) left join fs_batch b on (b.id = f.batch) where f.checkstatus = '1' and b.isbalance = 'N' and f.registdate like '"+month+"%'";
			//根据条件构造sql
			if(batch != null){
				if(batch.getFarmer() != null && batch.getFarmer().getId() != null && !"".equals(batch.getFarmer().getId())){
					inheadSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
					currentDeadSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
					deadSql += " and f.farmer='"+batch.getFarmer().getId()+"'";
					outSql += " and t.farmer='"+batch.getFarmer().getId()+"'";
					monthFeedSql += " and f.farmer='"+batch.getFarmer().getId()+"'";
					monthinfeedSql += " and f.farmer='"+batch.getFarmer().getId()+"'";
				}
				
				if(batch.getCompany() != null && batch.getCompany().getId() != null && !"".equals(batch.getCompany().getId())){
					inheadSql += " and t.company='"+batch.getCompany().getId()+"'";
					currentDeadSql += " and t.company='"+batch.getCompany().getId()+"'";
					deadSql += " and f.company='"+batch.getCompany().getId()+"'";
					outSql += " and t.company='"+batch.getCompany().getId()+"'";
					monthFeedSql += " and f.company='"+batch.getCompany().getId()+"'";
					monthinfeedSql += " and f.company='"+batch.getCompany().getId()+"'";
				}
			}
			//分组
			deadSql += " group by f.iscordeath";
			outSql += " group by t.saletype";
			//查询数据库 封装对象
			//进猪总头数
			Object[][] _obj = jdbc.Querry(inheadSql, connection);
			String inNum="0";
			if(_obj != null && _obj.length>0){
				inNum = _obj[0][0]==null?"0":_obj[0][0].toString();
			}
			//当天死亡头数
			_obj=null;
			_obj = jdbc.Querry(currentDeadSql, connection);
			String currentDeadHead="0";
			if(_obj != null && _obj.length>0){
				currentDeadHead = _obj[0][0]==null?"0":_obj[0][0].toString();
			}
			//总死亡头数
			_obj=null;
			_obj = jdbc.Querry(deadSql, connection);
			String mnum ="0";
			String dnum = "0";
			if(_obj != null && _obj.length>0){
				for(int i=0;i<_obj.length;i++){
					if("N".equals(_obj[i][1]+"")){
						dnum = ArithUtil.add(dnum, _obj[i][0]+"");
					}else{
						mnum = ArithUtil.add(mnum, _obj[i][0]+"");
					}
				}
			}
			
			//总销售 淘汰头数
			_obj=null;
			_obj = jdbc.Querry(outSql, connection);
			String saleNum ="0";
			String eNum = "0";
			if(_obj != null && _obj.length>0){
				for(int i=0;i<_obj.length;i++){
					if(_obj[i][0] != null && PapUtil.checkDouble(_obj[i][0]+"")){
						if("Q".equals(_obj[i][1]+"")){
							saleNum = ArithUtil.add(saleNum, _obj[i][0]+"");
						}else{
							eNum = ArithUtil.add(eNum, _obj[i][0]+"");
						}
						String sale = saleNum==null?"0":saleNum;
						String ehead = eNum==null?"0":eNum; 
						saleNum=ArithUtil.add(sale,ehead,0);
					}
				}
			}
			
			//总饲料数
			_obj = null;
			_obj = jdbc.Querry(monthFeedSql, connection);
			String allFeed="0";
			if(_obj != null && _obj.length>0){
				allFeed = _obj[0][0]==null?"0":_obj[0][0].toString();
			}
			
			//总进饲料数
			_obj = null;
			_obj = jdbc.Querry(monthinfeedSql, connection);
			String allInFeed="0";
			if(_obj != null && _obj.length>0){
				allInFeed = _obj[0][0]==null?"0":_obj[0][0].toString();
			}
			//计算总库存
			String allKucun="0";
			allKucun = ArithUtil.sub(inNum, saleNum);
			allKucun = ArithUtil.sub(allKucun, mnum);
			allKucun = ArithUtil.sub(allKucun, dnum);
			
			//总的死淘率    （死亡头数+淘汰头数）/(进猪头数-免责死亡头数)
			String allD = ArithUtil.add(dnum, eNum);
			String inD = ArithUtil.add(inNum, mnum);
			String deadRate = "0%";
			if(ArithUtil.comparison(inD, "0") != 0){
				deadRate = ArithUtil.percent(ArithUtil.div(allD, inD), 2);
			}
			
			//封装对象
			Batch ball = new Batch();
			ball.setOutDate("合计:");
			ball.setCurrentDeadNum(currentDeadHead);
			ball.setQuantity(allKucun);
			ball.setDeathQuan(dnum);
			ball.setOtherDeathQuan(mnum);
			ball.setEliminateQuan(eNum);
			ball.setSaleQuan(saleNum);
			ball.setPigletQuan(inNum);
			ball.setCurrentFeed(ArithUtil.div(allFeed, "1000",3));
			ball.setDeadRate(deadRate);
			ball.setCurrentInFeed(ArithUtil.div(allInFeed, "1000",3));
			
			batchs.add(ball);
			
			return batchs;
 		}finally{
			jdbc.destroy(connection);
		}
	}
	
	/**
	 * 
	 * 功能： 导出进度追踪表<br/>
	 * @author:DZL<br/>
	 * @version:2016-12-2上午09:55:02
	 * @param @param e
	 * @param @return
	 * @param @throws Exception  
	 * @throws
	 * @return InputStream
	 * @see com.zd.pb.breed.services
	 */
	public InputStream exportCurrentBatch(Batch batch)throws Exception{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet1 = workbook.createSheet("代养进度追踪报表");
		HSSFSheet sheet2 = workbook.createSheet("汇总信息");
		// 设置表格默认列宽度为15个字节
		sheet1.setDefaultColumnWidth(19);
		sheet2.setDefaultColumnWidth(7);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		StringBuilder exportFields1 = new StringBuilder();
		StringBuilder exportFields2 = new StringBuilder();
		String spareFields = "area:区域,farmerName:代养户,batchNumber:批次号,inDate:进猪时间,outDate:预出栏日期,pigletQuan:进猪数,currentDeadNum:当日死亡,otherDeathQuan:免责死亡," +
				"deathQuan:累计死亡,saleQuan:出栏总数,eliminateQuan:淘汰总数,quantity:库存总数,deadRate:死淘率,deadReason:备注,currentFeed:当月耗料用量（吨）,currentInFeed:当月领料量（吨）,developMan.name:开发员,technician.name:技术员,pigSource:猪苗来源";
		String mainFields = "area:区域,farmerName:代养户,pigletQuan:进猪数,quantity:库存总数,saleQuan:出栏总数," +
				"eliminateQuan:淘汰总数,deadRate:成活率";
		exportFields1.append(spareFields);
		exportFields2.append(mainFields);
		
		String[] fields1 = exportFields1.toString().split(",");
		String[] fields2 = exportFields2.toString().split(",");
		// 生成表头标题行
		List<String> fieldNames1 = new ArrayList<String>();
		List<String> fieldNames2 = new ArrayList<String>();
		int cellIndex1 = -1;
		for (String field : fields1) {
			String display = field.split(":")[1];
			fieldNames1.add(field.split(":")[0]);
			ExcelUtil.setCellValue(sheet1, headerStyle, 0, ++cellIndex1, display);
		}
		int cellIndex2 = -1;
		for (String field : fields2) {
			String display = field.split(":")[1];
			fieldNames2.add(field.split(":")[0]);
			ExcelUtil.setCellValue(sheet2, headerStyle, 0, ++cellIndex2, display);
		}
		// 生成数据行
		int index1 = 0;
		List<Batch> datas1 = getExpValues(batch);
		if (null != datas1 && datas1.size() > 0) {
			for (Batch b: datas1) {
				index1 ++;
				HSSFRow row = sheet1.createRow(index1);
				for (int i = 0; i < fieldNames1.size(); i ++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(cellStyle);
					Object obj = TypeUtil.getFieldValue(b,fieldNames1.get(i));
					ExcelUtil.setCellValue(cell, obj);
				}
			}
		}
		// 生成数据行
		int index2 = 0;
		List<Batch> datas2 = selectAllByFarmer(batch) ;
		if (null != datas2 && !datas2.isEmpty()) {
			for (Batch b : datas2) {
				index2 ++;
				HSSFRow row = sheet2.createRow(index2);
				for (int i = 0; i < fieldNames2.size(); i ++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(cellStyle);
					Object obj = TypeUtil.getFieldValue(b,fieldNames2.get(i));
					ExcelUtil.setCellValue(cell, obj);
				}
			}
		}
		// 生成数据行
		ByteArrayOutputStream out = null;
		InputStream inputStream = null;
		try {
			out = new ByteArrayOutputStream();
			workbook.write(out);
			out.flush();
			byte[] outByte = out.toByteArray();
			inputStream = new ByteArrayInputStream(outByte, 0, outByte.length);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputStream;
	}
	
	
	/**
	 * 查找日报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-21 下午02:19:03
	 */
	public Map<String, Object> selectDayAnalysisByPage(Batch entity, Pager<Batch> page)throws Exception{
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		String date = null;
		if(entity.getTempStack().get("startTime")!=null && !"".equals(entity.getTempStack().get("startTime")))
			date = entity.getTempStack().get("startTime");
		else
			throw new SystemException("查询日期不能为空！");
		
		//查找在养客户
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId()))
			sqlMap.put("company.id", "=", entity.getCompany().getId());
		else
			throw new SystemException("养殖公司不能为空！");
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			sqlMap.put("farmer.id", "=", entity.getFarmer().getId());
		if( entity.getId()!=null && !"".equals(entity.getId()))
			sqlMap.put("id", "=", entity.getId());
		sqlMap.put("pigletQuan", "<>", null);
		sqlMap.put("isBalance", "=", "N");
		
		//总条数
		int totalRow = selectTotalRows(sqlMap);
		//排序
		sqlMap.put("contract.feedFac.id,inDate", "order by", "asc");
		
		selectHQL(sqlMap,page);
		
		sqlMap.clear();
		//查找公司饲料类型
		List<FeedType> feedTypes = feedTypeService.selectBySingletAll("company.id", entity.getCompany().getId());
		if(feedTypes==null || feedTypes.isEmpty())
			throw new SystemException("未找到公司的饲料类型！");
		
		List<Batch> batchs = page.getResult();
		//遍历在养批次
		List<Map<String, Object>> objs = new ArrayList<Map<String, Object>>();
		if(batchs!=null && batchs.size()>0){
			for(Batch bat : batchs){
				Map<String, Object> map = new HashMap<String, Object>();
				//固定字段
				pigPurchaseService.selectPigDay(bat,date,map);
				if(entity.getShowColumns()!=null && !"".equals(entity.getShowColumns())){
					//勾选库存时
					if(entity.getShowColumns().indexOf("Warehouse")!=-1){
						//也勾选未拉时
						if(entity.getShowColumns().indexOf("noFeed")!=-1){
							entity.setShowColumns("feed,InWare,Warehouse,noFeed") ;
						}else{
							entity.setShowColumns("feed,InWare,Warehouse") ;
						}
					}
					//勾选未拉时
					if(entity.getShowColumns().indexOf("noFeed")!=-1){
						entity.setShowColumns("feed,InWare,Warehouse,noFeed") ;
					}
					
					//饲料耗用
					if(entity.getShowColumns().indexOf("feed")!=-1)
						feedBillService.selectFeedDay(bat,date,map);
					//饲料入库
					if(entity.getShowColumns().indexOf("InWare")!=-1)
						feedInWareService.selectFeedInDay(bat,date,map);
					
					//饲料库存
					if(entity.getShowColumns().indexOf("Warehouse")!=-1){
						String total = "0";
						for(FeedType ft : feedTypes){
							String wareQuan = "0";
							
							String name = ft.getName();
							String inQuan = (String) map.get(name+"_in");
							String quan = (String) map.get(name);
							if(inQuan != null && !"".equals(inQuan)){
								if(quan == null || "".equals(quan))
									quan = "0";
								wareQuan = ArithUtil.sub(inQuan, quan, 2);
								map.put(name+"_ware", wareQuan);
								total = ArithUtil.add(total, wareQuan,2);
							}
						}
						map.put("all_ware", total);
					}
						
					//饲料未拉
					if(entity.getShowColumns().indexOf("noFeed")!=-1){
						String total = "0";
						for(FeedType ft : feedTypes){
							String name = ft.getName();
							String inQuan = (String) map.get(name+"_in");
							if(inQuan == null || "".equals(inQuan))
								inQuan = "0";
							
							String stand = ArithUtil.mul(bat.getPigletQuan(), ft.getFeedStand(),2);
							String feed_no = ArithUtil.sub(stand, inQuan, 2);
							map.put(name+"_no", feed_no);
							total = ArithUtil.add(total, feed_no,2);
						}
						map.put("all_no", total);
					}
				}
				
				objs.add(map);
			}
		}
		//合计
		if(objs.size() > 0 && entity.getShowColumns()!=null && !"".equals(entity.getShowColumns())){
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("sdate", "合计");
			//饲料耗用
			if(entity.getShowColumns().indexOf("feed")!=-1)
				feedBillService.selectAllFeedDay(entity,date,totalMap);
			//饲料入库
			if(entity.getShowColumns().indexOf("InWare")!=-1)
				feedInWareService.selectAllFeedInDay(entity,date,totalMap);
			//饲料库存
			if(entity.getShowColumns().indexOf("Warehouse")!=-1){
				String total = "0";
				for(FeedType ft : feedTypes){
					String wareQuan = "0";
					
					String name = ft.getName();
					String inQuan = (String) totalMap.get(name+"_in");
					String quan = (String) totalMap.get(name);
					if(inQuan != null && !"".equals(inQuan)){
						if(quan == null || "".equals(quan))
							quan = "0";
						wareQuan = ArithUtil.sub(inQuan, quan, 2);
						totalMap.put(name+"_ware", wareQuan);
						total = ArithUtil.add(total, wareQuan,2);
					}
				}
				totalMap.put("all_ware", total);
			}
				
			//饲料未拉
			if(entity.getShowColumns().indexOf("noFeed")!=-1){
				String total = "0";
				String totalQuan = selectTotalBreedQuan(entity);
				for(FeedType ft : feedTypes){
					String name = ft.getName();
					String inQuan = (String) totalMap.get(name+"_in");
					if(inQuan == null || "".equals(inQuan))
						inQuan = "0";
					
					String stand = ArithUtil.mul(totalQuan, ft.getFeedStand(),2);
					String feed_no = ArithUtil.sub(stand, inQuan, 2);
					totalMap.put(name+"_no", feed_no);
					total = ArithUtil.add(total, feed_no,2);
				}
				totalMap.put("all_no", total);
			}
			
			objs.add(totalMap);
		}
		
		page.setTotalCount(totalRow);
		
		result.put("Rows", objs); // 返回查询结果集
		result.put("Total", totalRow); // 返回数据总条数
		
		return result;	
	}
	/**
	 * 获取在养批次总进猪数
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 下午04:44:24
	 */
	private String selectTotalBreedQuan(Batch entity)throws Exception {
		String total = "0";
		String countSql = "select sum(t.pigletquan) from FS_BATCH t where t.pigletquan is not null and t.isbalance='N' ";
		
		if(entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId()))
			countSql += "and t.company='"+entity.getCompany().getId()+"' ";
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			countSql += "and t.farmer='"+entity.getFarmer().getId()+"' ";
		if( entity.getId()!=null && !"".equals(entity.getId()))
			countSql += "and t.id='"+entity.getId()+"' ";
		
		Connection con = null;
		try {
			con = jdbc.getConnection();
			Object[][] rows = jdbc.doQuerry(countSql, con);
			if (null != rows && rows.length > 1){
				total = ArithUtil.add(total, rows[1][0].toString(),2);
			}
		} finally {
			jdbc.destroy(con);
		}
		return total;
	}
	
	/**
	 * 根据用户查询批次
	 * @return
	 * @throws Exception
	 */
	public List<Batch> selectAllByUser(Users u,Batch b)throws Exception{
		List<Batch> blist = null;
		Company company = u.getCompany();
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("用户不能加载数据");
		else{
			String id = "";
			if(b != null && b.getTechnician() != null && b.getTechnician().getId() != null && !"".equals(b.getTechnician().getId()))
				id = b.getTechnician().getId()+"";
			Technician t = u.getTechnician();
			//查询一下有没有所管理的再养批次
			if(t==null){
				//获取有没有技术员
				if(id == null || "".equals(id))
					//查询该公司所有的再养批次
					blist = super.selectByHQL("from Batch e where e.contract.status='BREED' and e.company.id='"+company.getId()+"' order by e.technician.id,e.inDate");
				else 
					blist = super.selectByHQL("from Batch e where e.contract.status='BREED' and e.company.id='"+company.getId()+"' and e.technician.id='"+id+"' order by e.inDate");
			}else{
				//查询技术员所管理的再养批次
				blist = super.selectByHQL("from Batch e where e.contract.status='BREED' and e.company.id='"+company.getId()+"' and e.technician.id='"+t.getId()+"' order by e.inDate");
			}
		}
		return blist;
	}
	
	/**
	 * 导出日报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public InputStream exportBook(Batch entity)throws Exception{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("日报表");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		//获取饲料类型
		FeedType fty = new FeedType();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("company", entity.getCompany().getId());
		fty.setMap(map);
		String[] names = feedTypeService.selectByCor(fty);
		
		StringBuilder exportFields = new StringBuilder();
		String spareFields = "sdate:查询日期,feedFac:区域,company:养殖公司,farmer:代养户,batch:批次,";
		String spareFields2 = "inDate:进猪日期,breedDays:用户饲养天数,weeks:周龄,";
		//耗料
		String feed = "";
		for(String name : names){
			feed = feed + name+":"+name+"耗用,";
		}
		feed = feed + "all:耗用合计,";
		//入库
		String in = "";
		for(String name : names){
			in = in + name+"_in"+":"+name+"入库,";
		}
		in = in + "all_in:入库合计,";
		//库存
		String ware = "";
		for(String name : names){
			ware = ware + name+"_ware"+":"+name+"库存,";
		}
		ware = ware + "all_ware:库存合计,";
		//未拉
		String no = "";
		for(String name : names){
			no = no + name+"_no"+":"+name+"未拉,";
		}
		no = no + "all_no:未拉合计";
		
		exportFields.append(spareFields).append(feed).append(in).append(ware).append(spareFields2).append(no);
		
		String[] fields = exportFields.toString().split(",");
		// 生成表头标题行
		List<String> fieldNames = new ArrayList<String>();
		int cellIndex = -1;
		for (String field : fields) {
			String display = field.split(":")[1];
			fieldNames.add(field.split(":")[0]);
			ExcelUtil.setCellValue(sheet, headerStyle, 0, ++cellIndex, display);
		}
		// 生成数据行
		int index = 0;
		List<Map<String, Object>> datas = selectBook(entity);
		if (null != datas && datas.size() > 0) {
			for (Map<String, Object> obj : datas) {
				index ++;
				HSSFRow row = sheet.createRow(index);
				for (int i = 0; i < fieldNames.size(); i ++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(cellStyle);
					Object v = obj.get(fieldNames.get(i));
					ExcelUtil.setCellValue(cell, v);
				}
			}
		}
		// 生成数据行
		ByteArrayOutputStream out = null;
		InputStream inputStream = null;
		try {
			out = new ByteArrayOutputStream();
			workbook.write(out);
			out.flush();
			byte[] outByte = out.toByteArray();
			inputStream = new ByteArrayInputStream(outByte, 0, outByte.length);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputStream;
		
	}
	/**
	 * 查询日报表不分页
	 * @Description:TODO
	 * @param entity
	 * @return
	 * List<Map<String,Object>>
	 * @exception:
	 * @author: 小丁
	 * @throws Exception 
	 * @time:2017-10-11 上午11:28:22
	 */
	private List<Map<String, Object>> selectBook(Batch entity) throws Exception {
		String date = null;
		if(entity.getTempStack().get("startTime")!=null && !"".equals(entity.getTempStack().get("startTime")))
			date = entity.getTempStack().get("startTime");
		else
			throw new SystemException("查询日期不能为空！");
		
		//查找在养客户
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId()))
			sqlMap.put("company.id", "=", entity.getCompany().getId());
		else
			throw new SystemException("养殖公司不能为空！");
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			sqlMap.put("farmer.id", "=", entity.getFarmer().getId());
		if( entity.getId()!=null && !"".equals(entity.getId()))
			sqlMap.put("id", "=", entity.getId());
		sqlMap.put("pigletQuan", "<>", null);
		sqlMap.put("isBalance", "=", "N");
		sqlMap.put("contract.feedFac.id,inDate", "order by", "asc");
		List<Batch> batchs = selectHQL(sqlMap);
		sqlMap.clear();
		//查找公司饲料类型
		List<FeedType> feedTypes = feedTypeService.selectBySingletAll("company.id", entity.getCompany().getId());
		if(feedTypes==null || feedTypes.isEmpty())
			throw new SystemException("未找到公司的饲料类型！");
		
		//遍历在养批次
		List<Map<String, Object>> objs = new ArrayList<Map<String, Object>>();
		if(batchs!=null && batchs.size()>0){
			for(Batch bat : batchs){
				Map<String, Object> map = new HashMap<String, Object>();
				//固定字段
				pigPurchaseService.selectPigDay(bat,date,map);
				if(entity.getShowColumns()!=null && !"".equals(entity.getShowColumns())){
					
					entity.setShowColumns("feed,InWare,Warehouse,noFeed") ;
					//饲料耗用
					if(entity.getShowColumns().indexOf("feed")!=-1)
						feedBillService.selectFeedDay(bat,date,map);
					//饲料入库
					if(entity.getShowColumns().indexOf("InWare")!=-1)
						feedInWareService.selectFeedInDay(bat,date,map);
					
					//饲料库存
					if(entity.getShowColumns().indexOf("Warehouse")!=-1){
						String total = "0";
						for(FeedType ft : feedTypes){
							String wareQuan = "0";
							
							String name = ft.getName();
							String inQuan = (String) map.get(name+"_in");
							String quan = (String) map.get(name);
							if(inQuan != null && !"".equals(inQuan)){
								if(quan == null || "".equals(quan))
									quan = "0";
								wareQuan = ArithUtil.sub(inQuan, quan, 2);
								map.put(name+"_ware", wareQuan);
								total = ArithUtil.add(total, wareQuan,2);
							}
						}
						map.put("all_ware", total);
					}
						
					//饲料未拉
					if(entity.getShowColumns().indexOf("noFeed")!=-1){
						String total = "0";
						for(FeedType ft : feedTypes){
							String name = ft.getName();
							String inQuan = (String) map.get(name+"_in");
							if(inQuan == null || "".equals(inQuan))
								inQuan = "0";
							
							String stand = ArithUtil.mul(bat.getPigletQuan(), ft.getFeedStand(),2);
							String feed_no = ArithUtil.sub(stand, inQuan, 2);
							map.put(name+"_no", feed_no);
							total = ArithUtil.add(total, feed_no,2);
						}
						map.put("all_no", total);
					}
				}
				
				objs.add(map);
			}
		}
		//合计
		if(objs.size() > 0 && entity.getShowColumns()!=null && !"".equals(entity.getShowColumns())){
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("sdate", "合计");
			//饲料耗用
			if(entity.getShowColumns().indexOf("feed")!=-1)
				feedBillService.selectAllFeedDay(entity,date,totalMap);
			//饲料入库
			if(entity.getShowColumns().indexOf("InWare")!=-1)
				feedInWareService.selectAllFeedInDay(entity,date,totalMap);
			//饲料库存
			if(entity.getShowColumns().indexOf("Warehouse")!=-1){
				String total = "0";
				for(FeedType ft : feedTypes){
					String wareQuan = "0";
					
					String name = ft.getName();
					String inQuan = (String) totalMap.get(name+"_in");
					String quan = (String) totalMap.get(name);
					if(inQuan != null && !"".equals(inQuan)){
						if(quan == null || "".equals(quan))
							quan = "0";
						wareQuan = ArithUtil.sub(inQuan, quan, 2);
						totalMap.put(name+"_ware", wareQuan);
						total = ArithUtil.add(total, wareQuan,2);
					}
				}
				totalMap.put("all_ware", total);
			}
				
			//饲料未拉
			if(entity.getShowColumns().indexOf("noFeed")!=-1){
				String total = "0";
				String totalQuan = selectTotalBreedQuan(entity);
				for(FeedType ft : feedTypes){
					String name = ft.getName();
					String inQuan = (String) totalMap.get(name+"_in");
					if(inQuan == null || "".equals(inQuan))
						inQuan = "0";
					
					String stand = ArithUtil.mul(totalQuan, ft.getFeedStand(),2);
					String feed_no = ArithUtil.sub(stand, inQuan, 2);
					totalMap.put(name+"_no", feed_no);
					total = ArithUtil.add(total, feed_no,2);
				}
				totalMap.put("all_no", total);
			}
			
			objs.add(totalMap);
		}
		
		return objs;
	}
	
	/**
	 * 获取工厂
	 */
	public String selectByFeedFac(String id)throws Exception{
		if(id == null || "".equals(id) || !PapUtil.checkDouble(id))
			return null;
		Batch bt = super.selectById(Integer.parseInt(id));
		if(bt != null){
			FeedFac f = bt.getFeedFac();
			if(f != null)
				return f.getId();
		}
		return null;
	}

}
