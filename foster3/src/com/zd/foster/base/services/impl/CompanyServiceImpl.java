package com.zd.foster.base.services.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.ICompanyDao;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.services.ICompanyService;
import com.zd.foster.base.services.ICustVenderService;
import com.zd.foster.base.services.IDriverService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.IFeedFacService;
import com.zd.foster.base.services.IGrowStandService;
import com.zd.foster.base.services.ISysConfigService;
import com.zd.foster.base.services.ITechnicianService;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.material.services.IDrugService;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.material.services.IFeedTypeService;
import com.zd.foster.material.services.IMaterialService;
import com.zd.foster.price.services.IDrugPriceService;
import com.zd.foster.price.services.IFeedPriceService;
import com.zd.foster.price.services.IFreightService;
import com.zd.foster.price.services.IMaterialPriceService;
import com.zd.foster.utils.FosterUtil;

/**
 * 类名：  CompanyServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class CompanyServiceImpl extends BaseServiceImpl<Company, ICompanyDao> implements ICompanyService {
	@Resource
	private ICustVenderService custVenderService;
	@Resource
	private IDriverService driverService;
	@Resource
	private IFeedFacService feedFacService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IGrowStandService growStandService;
	@Resource
	private ISysConfigService sysConfigService;
	@Resource
	private ITechnicianService technicianService;
	@Resource
	private IContractService contractService;
	@Resource
	private IFeedService feedService;
	@Resource
	private IFeedTypeService feedTypeService;
	@Resource
	private IDrugService drugService;
	@Resource
	private IMaterialService materialService;
	@Resource
	IFeedPriceService feedPriceService;
	@Resource
	IDrugPriceService drugPriceService;
	@Resource
	IMaterialPriceService materialPriceService;
	@Resource
	private IFreightService freightService;
	
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:29:52
	 */
	@Override
	public Object save(Company entity) throws Exception {
		
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		
		//2.验证company编码是否重复
		int codeNum=super.selectTotalRows("code", entity.getCode());
		if(codeNum>0)
			throw new SystemException("养殖公司编码重复！");
		//3.验证company名称是否重复
		int nameNum=super.selectTotalRows("name", entity.getName());
		if(nameNum>0)
			throw new SystemException("养殖公司名称重复！");
		return super.save(entity);
	}
	
	/**
	 * 删除
	 * @Description:TODO
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-6 上午10:54:24
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		Boolean isUse = false;
		for(ID id : PK){
			//1.验证客户、司机、饲料厂、代养户、生长标准、系统配置、技术员是否引用
			int cvNumber = custVenderService.selectTotalRows("company.id", PK[0]);
			if(cvNumber>0){
				isUse = true;
				break;
			}
			int drNum = driverService.selectTotalRows("company.id", PK[0]);	
			if(drNum>0){
				isUse = true;
				break;
			}
			int fdNum = feedFacService.selectTotalRows("company.id", PK[0]);
			if(fdNum>0){
				isUse = true;
				break;
			}
			int fmNum = farmerService.selectTotalRows("company.id", PK[0]);
			if(fmNum>0){
				isUse = true;
				break;
			}
			int gdNum = growStandService.selectTotalRows("company.id", PK[0]);
			if(gdNum>0){
				isUse = true;
				break;
			}
			int syNum = sysConfigService.selectTotalRows("company.id", PK[0]);
			if(syNum>0){
				isUse = true;
				break;
			}
			int teNum = technicianService.selectTotalRows("company.id", PK[0]);
			if(teNum>0){
				isUse = true;
				break;
			}
			//2.验证合同
			int cnNum = contractService.selectTotalRows("company.id", PK[0]);
			if(cnNum>0){
				isUse = true;
				break;
			}
			//3/物料、定价单
			int feNum = feedService.selectTotalRows("company.id", PK[0]);
			if(feNum>0){
				isUse = true;
				break;
			}
			int fyNum = feedTypeService.selectTotalRows("company.id", PK[0]);
			if(fyNum>0){
				isUse = true;
				break;
			}
			int dgNum = drugService.selectTotalRows("company.id", PK[0]);
			if(dgNum>0){
				isUse = true;
				break;
			}
			int myNum = materialService.selectTotalRows("company.id", PK[0]);
			if(myNum>0){
				isUse = true;
				break;
			}
			int fpNum = feedPriceService.selectTotalRows("company.id", PK[0]);
			if(fpNum>0){
				isUse = true;
				break;
			}
			int dpNum = drugPriceService.selectTotalRows("company.id", PK[0]);
			if(dpNum>0){
				isUse = true;
				break;
			}
			int mpNum = materialPriceService.selectTotalRows("company.id", PK[0]);
			if(mpNum>0){
				isUse = true;
				break;
			}
			int fgNum = freightService.selectTotalRows("company.id", PK[0]);
			if(fgNum>0){
				isUse = true;
				break;
			}
		}
		if(isUse)
			throw new SystemException("此公司正在被使用，不允许删除");
		
		return super.deleteByIds(PK);
	}

	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:40:44
	 */
	public int updateHql(Company entity) throws Exception {
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		//2.验证company编码除自己以外是否重复
		sqlMap.put("code", "=", entity.getCode());
		sqlMap.put("id", "<>", entity.getId());
		int codeNum = super.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(codeNum>0)
			throw new SystemException("养殖公司编码重复！");
		//3.验证company名称除自己以外是否重复
		sqlMap.put("name", "=", entity.getName());
		sqlMap.put("id", "<>", entity.getId());
		int nameNum = super.selectTotalRows(sqlMap);
		if(nameNum>0)
			throw new SystemException("养殖公司名称重复！");
		super.update(entity);
		
		return 1;
	}
}
