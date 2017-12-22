package com.zd.foster.breed.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.zd.epa.base.BaseAction;
import com.zd.foster.breed.entity.MobileEntity;
import com.zd.foster.breed.services.IMobileEntityService;

public class MobileEntityAction extends BaseAction<MobileEntity, IMobileEntityService> {
	//保存的数据
	private List<MobileEntity> meList;
	//删除的列表
	private String deleteIds;
	public List<MobileEntity> getMeList() {
		return meList;
	}
	public void setMeList(List<MobileEntity> meList) {
		this.meList = meList;
	}	
	public String getDeleteIds() {
		return deleteIds;
	}
	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectFeed()throws Exception{
		Boolean flag=true;
		elist=service.selectDj(e,flag);
		String djl=e.getDjl();
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		//生成简易的json字符串
		if(djl.equals("1")){
			//djl=1；喂料单
			Iterator<MobileEntity> it=elist.iterator();
			while(it.hasNext()){
				MobileEntity me=it.next();
				Map<String, String> map=new HashMap<String, String>();
				map.put("farmerId", me.getFarmerId());
				map.put("batchId", me.getBatchId());
				map.put("feedDate", me.getFeedDate());
				map.put("djl", me.getDjl());
				map.put("feed", me.getFeed());
				map.put("feedQuantity", me.getFeedQuantity());
				list.add(map);
			}
		}
		result.put("Rows", list);
		result.put("nowday", flag);
		return JSON;
	}
	/**
	 * 保存数据
	 * @return
	 * @throws Exception
	 */
	public String saveFeed()throws Exception{
		try{
//			super.save();
			service.savemobile(meList,deleteIds);
		}catch(Exception e){
			result.put("Return", "1");
		}
		result.put("Return", "0");
		return JSON;
		
	}

}
