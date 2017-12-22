package com.zd.foster.utils;

public class FosterUtil {

	/**
	 * 验证编码为至多十位的数字和字母
	 * @Description:TODO
	 * @param str
	 * @return
	 * boolean
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:37:51
	 */
	public static boolean idCode(String str) {
		Boolean bn = false;
		String regex = "[a-zA-Z\\d]+";
		if( str.matches(regex)){
			if(str.length()<=20)
				bn = true;
		}
		
		return bn;
	}
	
}
