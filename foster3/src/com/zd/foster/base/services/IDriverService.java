
package com.zd.foster.base.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Driver;
import com.zd.foster.base.entity.TransportCo;

/**
 * 司机服务处接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-20 下午03:47:52
 */
public interface IDriverService extends IBaseService<Driver> {
	List<Driver> selectAllByFarmer(Driver entity)throws Exception;

}
