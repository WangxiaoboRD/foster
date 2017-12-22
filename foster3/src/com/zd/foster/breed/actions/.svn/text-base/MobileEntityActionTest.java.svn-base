package com.zd.foster.breed.actions;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zd.foster.breed.entity.MobileEntity;
import com.zd.foster.breed.services.impl.MobileEntityServiceImpl;

public class MobileEntityActionTest {
	public static MobileEntityAction mea;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mea=new MobileEntityAction();
		MobileEntityServiceImpl mesi=new MobileEntityServiceImpl();
		
		Class<? extends MobileEntityAction> clazz=mea.getClass();
		Field[] ff=clazz.getDeclaredFields();
		for(Field f:ff){
			f.setAccessible(true);
			if(f.getName().equals("service"))
				f.set(mea, mesi);
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSelectFeed() throws Exception {
		MobileEntity me=new MobileEntity();
		me.setBatchId("1276");
		me.setFarmerId("F000001183");
		me.setFeedDate("2017-10-30");
		me.setDjl("1");
		mea.setE(me);
		mea.selectFeed();
	}

	@Test
	public void testSaveFeed() {
		fail("Not yet implemented");
	}

}
