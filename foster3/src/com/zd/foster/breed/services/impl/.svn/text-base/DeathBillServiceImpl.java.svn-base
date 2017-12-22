package com.zd.foster.breed.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.sysconfig.entity.SysParam;
import com.zd.epa.sysconfig.utils.SysConfigContext;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.DeathReason;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.base.services.IDeathReasonService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.ITechnicianService;
import com.zd.foster.breed.dao.IDeathBillDao;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.DeathBill;
import com.zd.foster.breed.entity.DeathBillDtl;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IDeathBillDtlService;
import com.zd.foster.breed.services.IDeathBillService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;

/**
 * 类名：  DeathBillServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-8-1下午08:28:35
 * @version 1.0
 * 
 */
public class DeathBillServiceImpl extends BaseServiceImpl<DeathBill, IDeathBillDao> implements
		IDeathBillService {
	@Resource
	private IDeathBillDtlService deathBillDtlService;
	@Resource
	private IBatchService batchService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private ITechnicianService technicianService;
	@Resource
	private IDeathReasonService deathReasonService;
	
	
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(DeathBill entity, Pager<DeathBill> page) throws Exception {
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
	 * 
	 * 功能:保存死亡单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(DeathBill entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.3代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.3获取养殖公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("死亡时间不能为空！");
		//1.6技术员是否为空
		if(entity.getTechnician()==null || entity.getTechnician().getId()==null || "".equals(entity.getTechnician().getId()))
			throw new SystemException("技术员不能为空！");
		//2验证明细
		List<DeathBillDtl> dbdList = entity.getDetails();
		//2.1明细是否为空
		if(dbdList == null || dbdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<dbdList.size();i++){
			DeathBillDtl dbd = dbdList.get(i);
//			//2.2.1验证数量是否为空，是否为非正数
//			if(dbd.getQuantity()==null || "".equals(dbd.getQuantity()))
//				buff.append("第"+(i+1)+"行头数不能为空<br>");
//			else if(ArithUtil.comparison(dbd.getQuantity(), "0")<=0)
//				buff.append("第"+(i+1)+"行头数必须为正数！<br>");
//			//2.2.2验证重量是否为空，是否为非正数
//			if(dbd.getWeight()==null || "".equals(dbd.getWeight()))
//				buff.append("第"+(i+1)+"行重量不能为空<br>");
//			else if(ArithUtil.comparison(dbd.getWeight(), "0")<=0)
//				buff.append("第"+(i+1)+"行重量必须为正数！<br>");
			if(dbd.getReason()==null || dbd.getReason().getId()==null || "".equals(dbd.getReason().getId()))
				buff.append("第"+(i+1)+"行必须输入原因！<br>");
			dbd.setDeathBill(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setTotalQuan(dbdList.size()+"");
		//1.7死亡头数是否超过现存头数
		Batch batch=batchService.selectById(entity.getBatch().getId());
		if(ArithUtil.comparison(entity.getTotalQuan(), batch.getQuantity())>0)
			throw new SystemException("【"+farmer.getName()+"】第【"+batch.getBatchNumber()+"】批死亡头数不能超过存栏头数！");
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		entity.setIsAnnex("N");
		entity.setIsReceipt("N");
		Object obj = super.save(entity);
		deathBillDtlService.save(dbdList);
		return obj;
	}
	
	/**
	 * 
	 * 功能:修改死亡单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(DeathBill entity)throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2养殖公司是否为空
		//1.3代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("死亡时间不能为空！");
		//1.6技术员是否为空
		if(entity.getTechnician()==null || entity.getTechnician().getId()==null || "".equals(entity.getTechnician().getId()))
			throw new SystemException("技术员不能为空！");
//		//1.7死亡头数是否超过现存头数
//		Batch batch=batchService.selectById(entity.getBatch().getId());
//		if(ArithUtil.comparison(entity.getTotalQuan(), batch.getQuantity())>0)
//			throw new SystemException("死亡头数不能超过存栏头数！");
		//2验证明细
		List<DeathBillDtl> dbdList = entity.getDetails();
		//2.1明细是否为空
		if(dbdList == null || dbdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<dbdList.size();i++){
			DeathBillDtl dbd = dbdList.get(i);
			//2.2.1验证数量是否为空，是否为非正
//			if(dbd.getQuantity()==null || "".equals(dbd.getQuantity()))
//				buff.append("第"+(i+1)+"行头数不能为空<br>");
//			else if(ArithUtil.comparison(dbd.getQuantity(), "0")<=0)
//				buff.append("第"+(i+1)+"行头数必须为正数！<br>");
			//2.2.2验证重量是否为空，是否为非正数
			if(dbd.getWeight()==null || "".equals(dbd.getWeight()))
				buff.append("第"+(i+1)+"行重量不能为空<br>");
			else if(ArithUtil.comparison(dbd.getWeight(), "0")<=0)
				buff.append("第"+(i+1)+"行重量必须为正数！<br>");
			if(dbd.getReason()==null || dbd.getReason().getId()==null || "".equals(dbd.getReason().getId()))
				buff.append("第"+(i+1)+"行必须输入原因！<br>");
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		
		// 保存表头
		// 1 、获取数据库里的对象
		DeathBill e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setTotalQuan(dbdList.size()+"");
		//1.7死亡头数是否超过现存头数
		entity.setTotalQuan(dbdList.size()+"");
		Batch batch=batchService.selectById(entity.getBatch().getId());
		if(ArithUtil.comparison(entity.getTotalQuan(), batch.getQuantity())>0)
			throw new SystemException("死亡头数不能超过存栏头数！");
		e.setFarmer(entity.getFarmer());
		e.setBatch(entity.getBatch());
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		e.setTotalQuan(entity.getTotalQuan());
		e.setTotalWeight(entity.getTotalWeight());
		e.setTechnician(entity.getTechnician());
		e.setIsCorDeath(entity.getIsCorDeath());
		e.setTotalQuan(entity.getTotalQuan());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					deathBillDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<DeathBillDtl> updateSwd = new ArrayList<DeathBillDtl>(); 
		List<DeathBillDtl> newSwd = new ArrayList<DeathBillDtl>(); 
		for(DeathBillDtl p : dbdList){
			if(p.getId()==null){
				p.setDeathBill(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(DeathBillDtl p : updateSwd){
			DeathBillDtl ed = deathBillDtlService.selectById(p.getId());
			ed.setQuantity(p.getQuantity());
			ed.setReason(p.getReason());
			ed.setWeight(p.getWeight());
		}
		updateSwd.clear();
		//添加
		deathBillDtlService.save(newSwd);
	}
	
	/**
	 * 
	 * 功能:删除
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.epa.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("deathBill.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		deathBillDtlService.delete(sqlMap);
		for(ID id:PK){
			DeathBill e=super.selectById(id);
			//3,删除照片
			String path=ServletActionContext.getServletContext().getRealPath("/WEB-INF/deathBill");
			File file=new File(path);
			final String filename=e.getId();
			File[] files=file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.contains(filename))
						return true;
					return false;
				}
			});
			if(files!=null && files.length>0){
				for(File f: files){
					f.delete();
				}
			}
		}
		return dao.deleteByIds(PK);
	}

	/**
	 * 
	 * 功能:死亡单审核
	 * 重写:wxb
	 * 2017-8-5
	 * @see com.zd.foster.breed.services.IEliminateBillService#check(java.lang.String[])
	 */
	@Override
	public void check(String[] idArr) throws Exception {
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		for(String s : idArr){
			//1.获取对象
			DeathBill e = super.selectById(s);
			//1.1验证合同是否完结
			Contract ct=e.getBatch().getContract();
			if(ct!=null && ct.getStatus()!=null ){
				if(!"BREED".equals(ct.getStatus().getDcode())){
					sb.append("单据【"+s+"】【"+e.getFarmer().getName()+"】第【"+e.getBatch().getBatchNumber()+"】批对应的合同不在养殖状态<br>");
					continue;
				}
			}
			
			//1.2验证总死亡头数是否超出存栏头数
			Batch batch=batchService.selectById(e.getBatch().getId());
			if(ArithUtil.comparison(e.getTotalQuan(), batch.getQuantity())>0)
				throw new SystemException("到单据【"+s+"】累计死亡头数超过存栏头数<br>");
			//1.3验证照片是否上传
//			if(e.getIsAnnex().equals("N")){
//				sb.append("单据【"+s+"】照片未上传<br>");
//				continue;
//			}
			//1.4更改批次信息
			if(e.getIsCorDeath().equals("N"))
				batch.setDeathQuan(ArithUtil.add(batch.getDeathQuan()==null?"0":batch.getDeathQuan(), e.getTotalQuan(), 0));
			else
				batch.setOtherDeathQuan(ArithUtil.add(batch.getOtherDeathQuan()==null?"0":batch.getOtherDeathQuan(), e.getTotalQuan(), 0));
			batch.setQuantity(ArithUtil.sub(batch.getQuantity(), e.getTotalQuan(), 0));
			//1.5给非免责死亡猪生成批次
			if(e.getIsCorDeath().equals("N")){
				String totalNum="0";
//				String sql="select sum(t.totalquan) from FS_DEATHBILL t where t.checkstatus=1 and t.batch='"+e.getBatch().getId()+"'";
//				Connection conn=null;
//				Statement st=null;
//				ResultSet rs=null;
//				try{
//					conn=jdbc.getConnection();
//					st=conn.createStatement();
//					rs=st.executeQuery(sql);
//					while(rs.next()){
//						totalNum=rs.getString("1");
//					}
//				}finally{
//					if(rs!=null)
//						rs.close();
//					if(st!=null)
//						st.close();
//					jdbc.destroy(conn);
//				}
				//hibernate
//				String hql="select sum(e.totalQuan) from DeathBill e where e.checkStatus=1 and e.batch.id='"+e.getBatch().getId()+"'and e.isCorDeath='N'";
//				totalNum=String.valueOf(dao.selectByAggregateHQL(hql));
				String hql="select max(e.num) from DeathBillDtl e " +
						" where e.deathBill.checkStatus=1 " +
						" and e.deathBill.batch.id='"+e.getBatch().getId()+"'";
				totalNum=String.valueOf(dao.selectByAggregateHQL(hql));
				//每头赋值序号
				List<DeathBillDtl> dbdList=deathBillDtlService.selectBySingletAll("deathBill.id", s);
				if(dbdList!=null && dbdList.size()>0){
					for(int i=0;i<dbdList.size();i++){
						DeathBillDtl dbd=dbdList.get(i);
						dbd.setNum(ArithUtil.add(totalNum, (i+1)+"", 0));
					}
				}
			}
			//4.审核单据 
			e.setCheckDate(PapUtil.date(new Date()));
			e.setCheckStatus("1");
			e.setCheckUser(u.getUserRealName());
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		
	}

	/**
	 * 
	 * 功能:撤销死亡单
	 * 重写:wxb
	 * 2017-8-6
	 * @see com.zd.foster.breed.services.IDeathBillService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		//1.获取对象
		DeathBill e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		
		Contract ct=e.getBatch().getContract();
		if(ct!=null && ct.getStatus()!=null ){
			if("LOST".equals(ct.getStatus().getDcode()))
				throw new SystemException("单据【"+id+"】对应的合同已经终止<br>");
		}
		
		//2.更改批次信息
		Batch batch=batchService.selectById(e.getBatch().getId());
		if(e.getIsCorDeath().equals("N"))
			batch.setDeathQuan(ArithUtil.sub(batch.getDeathQuan(), e.getTotalQuan(),0));
		else
			batch.setOtherDeathQuan(ArithUtil.sub(batch.getOtherDeathQuan(), e.getTotalQuan(), 0));
		batch.setQuantity(ArithUtil.add(batch.getQuantity(), e.getTotalQuan(), 0));
		//撤销序号
		if(e.getIsCorDeath().equals("N")){
			List<DeathBillDtl> dbdList=deathBillDtlService.selectBySingletAll("deathBill.id", id);
			for(DeathBillDtl dbd:dbdList){
				dbd.setNum(null);
			}
		}
		//5.撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}

	/**
	 * 
	 * 功能:上传照片
	 * 重写:wxb
	 * 2017-8-8
	 * @see com.zd.foster.breed.services.IDeathBillService#upload(java.io.File, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean upload(File[] files, String path,String[] fileNames, String id)throws Exception {
		// 获得系统参数附件文件大小设置
		SysParam sysParam = SysConfigContext.getParam("DeathBillAnnex_Size");
		if (null == sysParam)
			throw new SystemException("死亡单附件大小[DeathBillAnnex_Size]在系统参数中未进行配置，获取失败！");
		if(files == null || files.length==0)
			throw new SystemException("未获取死亡单图片");
		String value = sysParam.getValue();
		
		//生成文件名称以及文件包名称 文件包是以每个养殖公司每个批次的id构成
		DeathBill c = super.selectById(id);
		String _fileName = "";
		String _companyId = "";
		int _batchId = 0;
		if(c != null){
			_fileName = c.getId();
			_companyId = c.getCompany().getId();
			_batchId = c.getBatch().getId();
		}else{
			throw new SystemException("死亡单信息不存在");
		}
		//构建文件包名称
		String filePackage = _companyId+"-"+_batchId;
		//构建文件包路径
		filePackage = path+"/"+filePackage+"/";
		File dir = new File(filePackage);
		//判断文件夹是不是存在
		if (!dir.exists()) {// 判断目录是否存在
			if (!dir.mkdirs()) // 创建目标目录
				throw new SystemException("文件目录创建失败");
		}
		File _file = new File(filePackage);
		File[] _files = _file.listFiles();
		//删除所有文件
		if(_files != null && _files.length>0){
			for(File f : _files){
				if(f.isFile()){
					//判断文件是不是这个单据的
					String fname = f.getName();
					if(fname.contains(_fileName))
						//删除文件
						f.delete();
				}
			}
		}
		long v = 0;
		if (null != value && !"".equals(value)) {
			int limitSize = Integer.parseInt(value);
			v = limitSize*1024*1204;
		}
		//处理图片
		for(int i=0;i<files.length;i++){
			File file = files[i];
			if(v>0){
				long fileSize = file.length();
				if (fileSize > v) {
					throw new SystemException("死亡单图片大小不能超过" + value + "M，上传失败！");
				}
			}
			//获取原始文件的后缀名
			String suffix = "";
			String fileName = fileNames[i];
			if(fileName != null && !"".equals(fileName)){
				String[] str = fileName.split("\\.");
				suffix=str[1];
			}
			//重新构建文件名
			String fileName_ = _fileName+"-"+(i+1);
			//保存文件
			 //输出流  
	        OutputStream os = new FileOutputStream(new File(filePackage,fileName_+"."+suffix));  
	        //输入流  
	        InputStream is = new FileInputStream(file);  
	          
	        byte[] buf = new byte[1024];  
	        int length = 0 ;  
	          
		    while(-1 != (length = is.read(buf))) {  
		    	os.write(buf, 0, length) ;  
		    }
		    is.close();  
		    os.close();  
		}
		//文件上传完成以后，更改合同文件上传状态
	    c.setIsAnnex("Y");
		return true;
	}

	/**
	 * 
	 * 功能:下载
	 * 重写:wxb
	 * 2017-8-8
	 * @see com.zd.foster.breed.services.IDeathBillService#down(java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public InputStream down(String path, String id, String[] fname)
			throws Exception {
		//生成文件名称
		DeathBill c = super.selectById(id);
		if(c == null)
			return null;
		String _fileName = c.getId();
		InputStream is = null;
		try{
			//获得文件
			File file = new File(path);
			File[] files = file.listFiles();
			File _file = null;
			if(files != null && files.length>0){
				for(File f : files){
					if(f.isFile()){
						//获取文件名比较文件名
						String oldname = f.getName();
						//取出后缀
						String[] _strname = oldname.split("\\.");
						if(_strname != null && _strname.length>0 && (_strname[0].equals(_fileName))){
							_file = f;
							_fileName = oldname;
							break;
						}
					}
				}
			}
	        //输入流  
			if(_file == null)
				return null;
			fname[0]=_fileName;
//	        is = new FileInputStream(_file);
	        is=ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/deathBill/" + _fileName);
		}finally{
			if(is!=null)
				is.close();
		}
        return is;
	}

	/**
	 * 
	 * 功能:确认回执（IsReceipt设为Y）
	 * 重写:wxb
	 * 2017-9-9
	 * @see com.zd.foster.breed.services.IDeathBillService#receipt(java.lang.String)
	 */
	@Override
	public void receipt(String[] idArr) throws Exception {
		for(String s:idArr){
			DeathBill c = super.selectById(s);
			c.setIsReceipt("Y");
		}
	}

	/**
	 * 
	 * 功能:撤销回执（IsReceipt设为N）
	 * 重写:wxb
	 * 2017-9-9
	 * @see com.zd.foster.breed.services.IDeathBillService#cancelReceipt(java.lang.String)
	 */
	@Override
	public void cancelReceipt(String id) throws Exception {
		DeathBill c = super.selectById(id);
		c.setIsReceipt("N");
		
	}
	
	/**
	 * 导入模板下载
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:24:15
	 */
	public InputStream downloadTemplate(String realPath)throws Exception{
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "deathbill.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
		
	}
	
	/**
	 * 导入死亡单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	public List<DeathBill> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		List<DeathBill> deathBills = new ArrayList<DeathBill>();
		Map<String,Object> map = new HashMap<String, Object>();
		Workbook workbook = ExcelUtil.buildWorkbook(file, (String)objects[0]);
		if (null != workbook) {
			Sheet sheet = workbook.getSheetAt(0); // 获得第一个Excel Sheet
			int lastRowNum = sheet.getLastRowNum(); // 最后行号，默认为索引号，即从0开始到当前行号-1，如excel有10条数据，firstRowNum=0，而lastRowNum=9
			if (lastRowNum >0) {
				int firstRowNum = sheet.getFirstRowNum();
				StringBuffer sb = new StringBuffer(); // 存储校验非法描述
				for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (null != row) {
						//验证是否为空
						String farmer = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "代养户", true, sb);
						String batch = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "批次", true, sb);
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "死亡日期", true, sb);
						String technician = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "技术员", true, sb);
						String isCorDeath = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "死亡归属", true, sb);
						String isReceipt = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "是否回执", true, sb);
						String weight = ExcelUtil.checkCellValue(row.getCell(6), i + 1, "重量(KG)", true, sb);
						String reason = ExcelUtil.checkCellValue(row.getCell(7), i + 1, "死亡原因", true, sb);
						//验证
						//验证代养户
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", farmer);
						List<Farmer> farmers = farmerService.selectHQL(sqlMap);
						sqlMap.clear();
						if(farmers==null || farmers.isEmpty())
							throw new SystemException("不存在代养户【"+farmer+"】");
						//验证批次
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("farmer.name", "=", farmer);
						sqlMap.put("batchNumber", "=", batch);
						List<Batch> batchs = batchService.selectHQL(sqlMap);
						sqlMap.clear();
						if(batchs==null || batchs.isEmpty())
							throw new SystemException("不存在代养户【"+farmer+"】批次【"+batch+"】");
						//验证及技术员
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", technician);
						List<Technician> technicians = technicianService.selectHQL(sqlMap);
						sqlMap.clear();
						if(technicians==null || technicians.isEmpty())
							throw new SystemException("不存在技术员【"+technician+"】");
						//死亡原因
						DeathReason dr = null;
						sqlMap.put("name", "=", reason);
						List<DeathReason> deathReasons = deathReasonService.selectHQL(sqlMap);
						sqlMap.clear();
						if(deathReasons==null || deathReasons.isEmpty())
							throw new SystemException("不存在死亡原因【"+reason+"】");
						else
							dr = deathReasons.get(0);
						
						//封装明细
						DeathBillDtl dbd = new DeathBillDtl();
						dbd.setWeight(weight);
						dbd.setReason(dr);
						List<DeathBillDtl> deathBillDtls = new ArrayList<DeathBillDtl>();
						deathBillDtls.add(dbd);
						//放入map
						String key = farmer+","+batch+","+registDate+","+technician+","+isCorDeath+","+isReceipt;
						if(map.containsKey(key)){
							List<DeathBillDtl> dbds = (List<DeathBillDtl>) map.get(key);
							dbds.add(dbd);
						}else{
							map.put(key, deathBillDtls);
						}
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(map.isEmpty())
					throw new SystemException("无可用数据导入");
				//封装死亡单
				for(Map.Entry<String, Object> entry : map.entrySet() ){
					String key = entry.getKey();
					List<DeathBillDtl> deathDtls = (List<DeathBillDtl>) entry.getValue();
					if(deathDtls != null && deathDtls.size()>0){
						String[] fiw = key.split(",");
						//农户
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", fiw[0]);
						List<Farmer> farmers = farmerService.selectHQL(sqlMap);
						sqlMap.clear();
						Farmer fmr = farmers.get(0);
						//批次
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("farmer.name", "=", fiw[0]);
						sqlMap.put("batchNumber", "=", fiw[1]);
						List<Batch> batchs = batchService.selectHQL(sqlMap);
						sqlMap.clear();
						Batch bat = batchs.get(0);
						//技术员
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", fiw[3]);
						List<Technician> technicians = technicianService.selectHQL(sqlMap);
						sqlMap.clear();
						Technician technician = technicians.get(0);
						
						DeathBill db = new DeathBill();
						db.setRegistDate(fiw[2]);
						db.setFarmer(fmr);
						db.setBatch(bat);
						db.setTotalQuan(Integer.toString(deathDtls.size()));
						
						String totalWeight = "0";
						for(DeathBillDtl dbd : deathDtls){
							totalWeight = ArithUtil.add(totalWeight, dbd.getWeight(),2);
						}
						db.setTotalWeight(totalWeight);
						db.setTechnician(technician);
						db.setCompany(company);
						if("养殖公司".equals(fiw[4]))
							db.setIsCorDeath("Y");
						else if("代养户".equals(fiw[4]))
							db.setIsCorDeath("N");
						else
							throw new SystemException("有死亡归属值类型不正确");
						db.setDetails(deathDtls);
						
						deathBills.add(db);
						String id = (String) save(db);
						String[] ids = {id};
						check(ids);
						
						if("是".equals(fiw[5]))
							receipt(ids);
					}
				}
			}else
				throw new SystemException("无可用数据导入");
		}
		return deathBills;
	}
	
	/**
	 * 查询图片
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: dzl
	 * @time:2017-9-5 下午03:26:07
	 */
	public Map<String,String> selectByImg(DeathBill deathBill,String path)throws Exception{
		if(deathBill == null)
			throw new SystemException("请选择公司");
		if(path==null || "".equals(path))
			throw new SystemException("路径不能为空");
		if(deathBill.getCompany() == null || deathBill.getCompany().getId()==null || "".equals(deathBill.getCompany().getId()))
			throw new SystemException("请选择公司");
		String companyId = deathBill.getCompany().getId();
		
		List<String> list = new ArrayList<String>();
		//构建地址
		if(deathBill.getBatch() != null && deathBill.getBatch().getId() != null && !"".equals(deathBill.getBatch().getId())){
			int batchId = deathBill.getBatch().getId();
			//构建文件夹路径
			String filePackage = companyId+"-"+batchId;
			list.add(filePackage);
		}else{
			if(deathBill.getFarmer() != null && deathBill.getFarmer().getId() != null && !"".equals(deathBill.getFarmer().getId())){
				//查找该农户的所有批次
				List<Batch> blist = batchService.selectBySingletAll("farmer.id", deathBill.getFarmer().getId());
				if(blist != null && !blist.isEmpty()){
					//构建文件路径
					for(Batch b : blist){
						String filePackage = companyId+"-"+b.getId();
						list.add(filePackage);
					}
				}
			}else{
				//查找该公司的所有批次
				List<Batch> blist = batchService.selectBySingletAll("company.id", companyId);
				if(blist != null && !blist.isEmpty()){
					//构建文件路径
					for(Batch b : blist){
						String filePackage = companyId+"-"+b.getId();
						list.add(filePackage);
					}
				}
			}
		}
		//构建文件夹
		Map<String,String> map=null;
		if(list != null && !list.isEmpty()){
			map = new HashMap<String,String>();
			for(String _path : list){
				//构建文件
				String pakPath = path+"/"+_path+"/";
				File _file = new File(pakPath);
				File[] _files = _file.listFiles();
				if(_files != null && _files.length>0){
					for(File f : _files){
						if(f.isFile()){
							//获取文件名
							String pname = f.getName();
							//根据文件名的ID号获取该单据的养殖户名称以及批次与ID号
							String bid = pname.substring(0,pname.indexOf("-"));
							
							//获取单据信息
							DeathBill db = super.selectById(bid);
							String value = "";
							if(db != null){
								String fName = db.getFarmer().getName();
								String batchN = db.getBatch().getBatchNumber();
								value = fName+"第"+batchN+"批,单据号:"+bid;
							}
							map.put(_path+"/"+pname, value);
						}
					}
				}
			}
		}
		return map;
	}

	@Override
	public <ID extends Serializable> DeathBill selectById(ID PK)
			throws Exception {
		DeathBill db=super.selectById(PK);
		db.setStockNum(db.getBatch().getQuantity());
		return db;
	}
	
	
}
