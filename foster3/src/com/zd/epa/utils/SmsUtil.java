/**
 * 文件名：@SmsUtil.java <br/>
 * 包名：com.zhongpin.hr.sms.utils <br/>
 * 项目名：hr <br/>
 * @author 孟雪勤 <br/>
 */
package com.zd.epa.utils;

import java.text.SimpleDateFormat;

import com.xtwin.basic.standard.Mobile;
import com.xtwin.basic.standard.Unicom;
import com.xtwin.dao.model.Message;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;


/**
 * 类名：SmsUtil  <br />
 *
 * 功能：短信发送工具类
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-12-10 下午03:20:31  <br />
 * @version 2014-12-10
 */
public class SmsUtil {

	/** yyyy-MM-dd HH:mm:ss日期格式 */
	public static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** yyyy-MM-dd日期格式 */
	public static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	/** 短信配置文件 */
	private static PropertiesUtil propUtil = new PropertiesUtil("sms");
	
	/**
	 * 功能：获得当前用户<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-3-7 上午09:42:59 <br/>
	 */
	public static Users getCurrentUser() {
		Users u = SysContainer.get();
		if (null == u)
			throw new SystemException("当前用户信息获取失败！");

		return u;
	}
	
	/**
	 * 功能：判断是否是手机号<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-12-10 下午03:23:52 <br/>
	 */
	public static boolean isMobileNo(String phone) throws Exception {
		String phoneRgx = propUtil.getProperty("phoneRgx");
		if (phone.matches(phoneRgx)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 功能：判断是否为移动号。返回true为移动号，否则为联通号<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-12-10 下午03:54:09 <br/>
	 */
	public static boolean isMobile(String phone) throws Exception {
		String phoneRegMob = propUtil.getProperty("phoneRegMob");
		if (phone.matches(phoneRegMob)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 功能：获得移动短号<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-12-10 下午03:56:30 <br/>
	 */
	public static String getMobileSmsNo() throws Exception {
		return propUtil.getProperty("smsNo_mobile");
	}
	
	/**
	 * 功能：获得联通短号<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-12-10 下午03:58:21 <br/>
	 */
	public static String getUnicomSmsNo() throws Exception {
		return propUtil.getProperty("smsNo_unicom");
	}
	
	/**
	 * 功能：获得短信发送方<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-12-10 下午04:11:45 <br/>
	 */
	public static String getSender() throws Exception {
		return propUtil.getProperty("sender");
	}
	
	/**
	 * 功能：发送短信，返回OK=成功，ERROR=失败<br/>
	 * @param 1、mobile 手机号
	 * 		  2、message 短信内容
	 * @author 孟雪勤
	 * @version 2014-12-10 下午04:03:26 <br/>
	 */
	public static String sendMsg(String mobile, String message) throws Exception {
		Message msg = new Message();
		msg.setSender(getSender()); // 发送者名称
		msg.setSvcnum(mobile); // 接收号码
		msg.setContent(message); // 内容
		String smsNo = getUnicomSmsNo();
		boolean isUnicom = true;
		if (isMobile(mobile)) {
			smsNo = getMobileSmsNo();
			isUnicom = false;
		}
		msg.setOrgaddr(smsNo); // 发送号码
		msg.setUnicom(isUnicom); // 是否为联通号

		// 返回信息处理
		String ret = "ERROR";
		if (isUnicom) {
			ret = new Unicom().newUnicomSend(msg); // 联通发送
		}else {
			ret = new Mobile().send(msg); // 移动发送
		}
		return ret;
	}
}
