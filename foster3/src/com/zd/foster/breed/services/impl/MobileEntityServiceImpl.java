package com.zd.foster.breed.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.breed.dao.IMobileEntityDao;
import com.zd.foster.breed.entity.MobileEntity;
import com.zd.foster.breed.services.IMobileEntityService;

public class MobileEntityServiceImpl extends BaseServiceImpl<MobileEntity, IMobileEntityDao> implements IMobileEntityService {
	@Resource
	private DataSource dataSource;//数据源
	/**
	 * 查询单据
	 */
	@Override
	public List<MobileEntity> selectDj(MobileEntity e,Boolean flag) throws Exception {
		
		if(e.getFarmerId()==null ||"".equals(e.getFarmerId()))
			throw new SystemException("无代养户");
		if(e.getBatchId()==null ||"".equals(e.getBatchId()))
			throw new SystemException("无批次");
		if(e.getFeedDate()==null ||"".equals(e.getFeedDate()))
			throw new SystemException("无喂料日期");
		if(e.getDjl()==null ||"".equals(e.getDjl()))
			throw new SystemException("无单据类");
		if(e.getDjl().equals("1")){
			//先到fs_phone 查询
			String hql="from MobileEntity e where e.farmerId='"+e.getFarmerId()+"'and e.feedDate='"+e.getFeedDate()+"'and e.batchId='"+e.getBatchId()+"'and e.djl='"+e.getDjl()+"'";
			List<MobileEntity> mList=selectByHQL(hql);
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			if(mList!=null && mList.size()>0)
				return mList;
			else{
				//fs_phone查不到数据，查询fs_feedbill
				try{
					conn=dataSource.getConnection();
					String feed_sql="select f.farmer,f.registdate,f.batch,fd.feed,fd.quantity " +
							" from fs_feedbilldtl fd join fs_feedbill f on fd.feedbill=f.id" +
	                        " where f.farmer=? and f.registdate=?and f.batch=?";
					ps=conn.prepareStatement(feed_sql);
					ps.setString(1, e.getFarmerId());
					ps.setString(2, e.getFeedDate());
					ps.setString(3, e.getBatchId());
					rs=ps.executeQuery();
					while(rs.next()){
						MobileEntity me =new MobileEntity();
						me.setFarmerId(rs.getString(1));
						me.setFeedDate(rs.getString(2));
						me.setBatchId(rs.getString(3));
						me.setDjl("1");
						me.setFeed(rs.getString(4));
						me.setFeedQuantity(rs.getString(5));
						mList.add(me);
					}
					flag=false;
					return mList;
				}catch(SQLException se){
					throw new SystemException("查询fs_feedbill,sql异常");
				}catch(Exception se){
					throw new SystemException("查询fs_feedbill异常");
				}finally{
					if(rs!=null)
						rs.close();
					if(ps!=null)
						ps.close();
					if(conn!=null)
						conn.close();
				}
			}
		}
		return null;
	}
	@Override
	public String savemobile(List<MobileEntity> meList,String deleteIds) throws Exception {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			//保存或修改
			if(meList!=null && meList.size()>0){
				for(MobileEntity me:meList){
					String djl=me.getDjl();
					String farmerId=me.getFarmerId();
					String batchId=me.getBatchId();
					String feedDate=me.getFeedDate();
					String feed=me.getFeed();
					String feedQuantity=me.getFeedQuantity();
					int haveNum=0;
					if(djl.equals("1")){
						//喂料单保存;查询是否已有记录
						String sql_count="select count(1) from fs_phone where feed=? and farmerId=? and batchId=? and feedDate=? ";
						String sql_update="update fs_phone set feedQuantity=?,createuser=?,createdate=? where feed=? and farmerId=? and batchId=? and feedDate=?";
						String sql_insert="insert into FS_PHONE (ID,FARMER,REGISTDATE,BATCH,DJL,TECHNICIAN,FEED,FEEDQUANTITY,CREATEUSER,CREATEDATE)values(hibernate_sequence.nextval,?,?,?,?,?,?,?,?,?)";
						ps=conn.prepareStatement(sql_count);
						ps.setString(1, feed);
						ps.setString(2, SysContainer.get().getUserRealName());
						ps.setString(3, PapUtil.date(new Date()));
						ps.setString(4, farmerId);
						ps.setString(5, batchId);
						ps.setString(6, feedDate);
						rs=ps.executeQuery();
						while(rs.next())
							haveNum=rs.getInt(1);
						//该饲料已经记录过了，更新
						if(haveNum>0){
							ps=conn.prepareStatement(sql_update);
							ps.setString(1, feedQuantity);
							ps.setString(2, feed);
							ps.setString(3, farmerId);
							ps.setString(4, batchId);
							ps.setString(5, feedDate);
							ps.addBatch();
						}else{
							ps=conn.prepareStatement(sql_insert);
							ps.setString(1, farmerId);
							ps.setString(2, feedDate);
							ps.setString(3, batchId);
							ps.setString(4, "1");
							ps.setString(5, feed);
							ps.setString(6, feedQuantity);
							ps.setString(7, SysContainer.get().getUserRealName());
							ps.setString(8, PapUtil.date(new Date()));
							ps.addBatch();
						}
					}
				}
			}
			//删除记录
			String sql_delete="delete from fs_phone where id=?";
			String[] ids=deleteIds.split(",");
			for(String id :ids){
				ps=conn.prepareStatement(sql_delete);
				ps.setString(1, id);
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		}
//		catch(SQLException se){
//			throw new SystemException("sql异常");
//		}catch(Exception se){
//			throw new SystemException("其他异常");
//		}
		finally{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return null;
	}
	
	
	

}
