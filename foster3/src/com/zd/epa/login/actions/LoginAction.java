package com.zd.epa.login.actions;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.zd.epa.base.BaseAction;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.permission.services.IUsersService;
import com.zd.epa.utils.SysContainer;


@ParentPackage("login")
public class LoginAction extends BaseAction<Users,IUsersService>{
	
	private static final long serialVersionUID = 1L;

	/**
	 *  简单登录测试
	 */
	public String login(){
		e = service.selectLoginUser(e.getUserCode(), e.getUserPassword());
		if(e != null){
			if(e.getUserStatus() == 1){
				message ="LOGINOK";
				//将当前登录对象保存到session中
				session.put(CURRENTUSER, e);
			}else{
				message = "LOGINSTOP";
			}
		}else{
			message="LOGINERROR";
		}
		text(message);
		//result.put("message", message);
		//return JSON;
		return NONE;
	}
//	/**
//	 * 简单登录测试
//	 */
//	public String login(){
//		e = service.selectLoginUser(e.getUserName(), e.getPassword());
//		if(e != null){
//			session.put(CURRENTUSER, e);
//			return INDEX;
//		}
//		return SUCCESS;
//	}
}
