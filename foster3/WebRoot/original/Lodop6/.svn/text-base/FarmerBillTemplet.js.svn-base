// 模板
function FarmerBIll(obj,iCurLine){
	var high = 18;
	LODOP.PRINT_INIT("FarmerBIll");
	LODOP.SET_PRINT_PAGESIZE(1,"0","0","A4");
	LODOP.SET_PRINT_STYLE("FontSize",11);
	LODOP.ADD_PRINT_TEXT(iCurLine+15,5,750,20,obj.farmer.name+"第"+obj.batch.batchNumber+"批养殖结算单NO."+obj.id);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",14);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.ADD_PRINT_TEXT(iCurLine+50,30,173,high,"进猪日期:"+obj.inPigDate);
	LODOP.ADD_PRINT_TEXT(iCurLine+50,208,173,high,"进猪头数:"+obj.inPigNum);
	LODOP.ADD_PRINT_TEXT(iCurLine+50,416,173,high,"入栏均重:"+obj.inPigAvgWeight+"KG");
	LODOP.ADD_PRINT_TEXT(iCurLine+50,624,173,high,"成 活 率:"+obj.survivalRate);
	LODOP.ADD_PRINT_TEXT(iCurLine+70,30,173,high,"出栏日期:"+obj.outPigDate);
	LODOP.ADD_PRINT_TEXT(iCurLine+70,208,173,high,"出栏头数:"+obj.outPigNum);
	LODOP.ADD_PRINT_TEXT(iCurLine+70,416,173,high,"料 肉 比:"+obj.fcr);
	LODOP.ADD_PRINT_TEXT(iCurLine+70,624,173,high,"毛猪净重:"+obj.outPigAllWeight+"KG");
	LODOP.ADD_PRINT_TEXT(iCurLine+90,30,173,high,"毛猪均重:"+obj.outPigAvgWeight+"KG");
	LODOP.ADD_PRINT_TEXT(iCurLine+90,208,173,high,"净 增 重:"+obj.netGin+"KG");
	LODOP.ADD_PRINT_TEXT(iCurLine+90,416,173,high,"净增均重:"+obj.avgNetGin+"KG");
	LODOP.ADD_PRINT_LINE(iCurLine+110,15,iCurLine+110,745,0,1);
	
	LODOP.ADD_PRINT_TEXT(iCurLine+135,30,180,high,"猪苗成本");
	LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+155,15,iCurLine+155,745,0,1);
	LODOP.ADD_PRINT_TEXT(iCurLine+158,30,170,high,"进猪头数");
	LODOP.ADD_PRINT_TEXT(iCurLine+158,200,170,high,"进猪总重（KG）");
	LODOP.ADD_PRINT_TEXT(iCurLine+158,400,170,high,"单   价（元）");
	LODOP.ADD_PRINT_TEXT(iCurLine+158,615,170,high,"总 金 额（元）");
	LODOP.ADD_PRINT_LINE(iCurLine+179,15,iCurLine+179,745,0,1);
	LODOP.SET_PRINT_STYLE("Alignment",3);
	LODOP.ADD_PRINT_TEXT(iCurLine+182,20,60,high,obj.inPigNum);
	LODOP.ADD_PRINT_TEXT(iCurLine+182,195,100,high,((obj.inPigNum*1)*(obj.inPigAvgWeight*1)).toFixed(2));
	LODOP.ADD_PRINT_TEXT(iCurLine+182,280,210,high,obj.inPigPrice);
	LODOP.ADD_PRINT_TEXT(iCurLine+182,500,210,high,obj.pigletCost);
	LODOP.ADD_PRINT_LINE(iCurLine+202,15,iCurLine+202,745,0,1);
	LODOP.ADD_PRINT_TEXT(iCurLine+204,30,170,high,"合   计:");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
	LODOP.ADD_PRINT_TEXT(iCurLine+204,500,210,high,obj.pigletCost);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",3);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+224,15,iCurLine+224,745,0,1);
	
	LODOP.ADD_PRINT_TEXT(iCurLine+250,30,180,high,"饲料成本");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+270,15,iCurLine+270,745,0,1);
	LODOP.SET_PRINT_STYLE("Alignment",3);
	LODOP.ADD_PRINT_TEXT(iCurLine+273,0,80,high,"饲料名称");
	LODOP.ADD_PRINT_TEXT(iCurLine+273,62,230,high,"饲料用量（KG）");
	LODOP.ADD_PRINT_TEXT(iCurLine+273,232,254,high,"单   价（元）");
	LODOP.ADD_PRINT_TEXT(iCurLine+273,274,432,high,"总 金 额（元）");
	LODOP.ADD_PRINT_LINE(iCurLine+294,15,iCurLine+294,745,0,1);
	var iCurLine=iCurLine+297;
	var items = obj.feedList;
	if(items.length>0){
		for (var i = 0; i < items.length; i++) {
			LODOP.ADD_PRINT_TEXT(iCurLine,0,80,high,items[i].feed.name);
			LODOP.ADD_PRINT_TEXT(iCurLine,62,230,high,items[i].quantity);
			LODOP.ADD_PRINT_TEXT(iCurLine,230,254,high,(items[i].price*1).toFixed(2));
			LODOP.ADD_PRINT_TEXT(iCurLine,274,432,high,items[i].money);
			iCurLine=iCurLine+high;//每行占16px
		}
		LODOP.ADD_PRINT_LINE(iCurLine,15,iCurLine,745,0,1);
		LODOP.ADD_PRINT_TEXT(iCurLine+3,30,170,high,"合    计:");
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		LODOP.ADD_PRINT_TEXT(iCurLine+3,274,432,high,(obj.feedCost*1).toFixed(2));
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_LINE(iCurLine+23,15,iCurLine+23,745,0,1);
		
		iCurLine = iCurLine+23;
	}		
	LODOP.ADD_PRINT_TEXT(iCurLine+25,30,180,high,"药品疫苗成本");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+46,15,iCurLine+46,745,0,1);
	LODOP.SET_PRINT_STYLE("Alignment",1);
	LODOP.ADD_PRINT_TEXT(iCurLine+49,30,100,high,"药品疫苗");
	LODOP.ADD_PRINT_TEXT(iCurLine+49,290,220,high,"头均耗药:"+((obj.drugsCost*1)/(obj.outPigNum*1)).toFixed(2)+"元");
	LODOP.ADD_PRINT_TEXT(iCurLine+49,570,220,high,"总 金 额:"+obj.drugsCost+"元");
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+69,15,iCurLine+69,745,0,1);
	iCurLine = iCurLine+69;
	
	LODOP.ADD_PRINT_TEXT(iCurLine+25,30,180,high,"增补费用");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+48,15,iCurLine+48,745,0,1);
	LODOP.SET_PRINT_STYLE("Alignment",3);
	LODOP.ADD_PRINT_TEXT(iCurLine+51,0,80,high,"增补类型");
	LODOP.ADD_PRINT_TEXT(iCurLine+51,80,150,high,"标准值");
	LODOP.ADD_PRINT_TEXT(iCurLine+51,215,150,high,"实际值");
	LODOP.ADD_PRINT_TEXT(iCurLine+51,355,150,high,"差   额");
	LODOP.ADD_PRINT_TEXT(iCurLine+51,555,150,high,"增补费用（元）");
	LODOP.ADD_PRINT_LINE(iCurLine+72,15,iCurLine+72,745,0,1);
	var iCurLine=iCurLine+72;
	var items = obj.rewardList;
	if(items.length>0){
		for (var i = 0; i < items.length; i++) {
			LODOP.ADD_PRINT_TEXT(iCurLine,30,100,high,items[i].reward.value);
			LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
			LODOP.ADD_PRINT_TEXT(iCurLine,80,148,high,items[i].standWeight);
			LODOP.ADD_PRINT_TEXT(iCurLine,215,148,high,items[i].avgWeight);
			LODOP.ADD_PRINT_TEXT(iCurLine,355,148,high,items[i].netgain);
			LODOP.ADD_PRINT_TEXT(iCurLine,555,145,high,items[i].money);
			iCurLine=iCurLine+high;//每行占16px
		}
		LODOP.ADD_PRINT_LINE(iCurLine,15,iCurLine,745,0,1);
		LODOP.ADD_PRINT_TEXT(iCurLine+3,30,170,high,"合    计:");
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		LODOP.ADD_PRINT_TEXT(iCurLine+3,555,145,high,(obj.rewardCost*1).toFixed(2));
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_LINE(iCurLine+23,15,iCurLine+23,745,0,1);
		iCurLine = iCurLine+23;
	}	
	LODOP.ADD_PRINT_TEXT(iCurLine+18,580,220,high,"总成本:"+obj.allBreedCost+"元");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	
	LODOP.ADD_PRINT_TEXT(iCurLine+50,30,180,high,"销售收入");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+71,15,iCurLine+71,745,0,1);
	LODOP.SET_PRINT_STYLE("Alignment",3);
	LODOP.ADD_PRINT_TEXT(iCurLine+74,0,80,high,"销售级别");
	LODOP.ADD_PRINT_TEXT(iCurLine+74,80,145,high,"销售头数");
	LODOP.ADD_PRINT_TEXT(iCurLine+74,240,150,high,"出场重量（KG）");
	LODOP.ADD_PRINT_TEXT(iCurLine+74,390,150,high,"收购价格（元）");
	LODOP.ADD_PRINT_TEXT(iCurLine+74,555,150,high,"销售总额（元）");
	LODOP.ADD_PRINT_LINE(iCurLine+97,15,iCurLine+97,745,0,1);
	var iCurLine=iCurLine+100;
	var items = obj.saleList;
	if(items.length>0){
		for (var i = 0; i < items.length; i++) {
			LODOP.ADD_PRINT_TEXT(iCurLine,0,80,high,items[i].pigLevel.value);
			LODOP.ADD_PRINT_TEXT(iCurLine,80,145,high,items[i].quantity);
			LODOP.ADD_PRINT_TEXT(iCurLine,240,148,high,(items[i].weight*1).toFixed(2));
			LODOP.ADD_PRINT_TEXT(iCurLine,390,148,high,(items[i].price*1).toFixed(2));
			LODOP.ADD_PRINT_TEXT(iCurLine,555,148,high,(items[i].money*1).toFixed(2));
			
			iCurLine=iCurLine+high;//每行占16px
		}
		LODOP.ADD_PRINT_LINE(iCurLine,15,iCurLine,745,0,1);
		LODOP.ADD_PRINT_TEXT(iCurLine+3,30,170,high,"合    计:");
		LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
		LODOP.ADD_PRINT_TEXT(iCurLine+3,555,145,high,(obj.saleIncome*1).toFixed(2));
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_LINE(iCurLine+23,15,iCurLine+23,745,0,1);
		iCurLine = iCurLine+23;
	}	
	LODOP.ADD_PRINT_TEXT(iCurLine+18,580,220,high,"总收入:"+(obj.saleIncome*1).toFixed(2)+"元");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	
	LODOP.ADD_PRINT_TEXT(iCurLine+50,30,180,high,"养殖户毛利");
	LODOP.SET_PRINT_STYLEA(0,"Alignment",1);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+71,15,iCurLine+71,745,0,1);
	LODOP.ADD_PRINT_TEXT(iCurLine+74,0,110,high,"元/KG");
	LODOP.ADD_PRINT_TEXT(iCurLine+74,280,150,high,"头均毛利（元）");
	LODOP.ADD_PRINT_TEXT(iCurLine+74,540,150,high,"总毛利（元）");
	LODOP.ADD_PRINT_LINE(iCurLine+95,15,iCurLine+95,745,0,1);
	LODOP.SET_PRINT_STYLE("Alignment",3);
	LODOP.ADD_PRINT_TEXT(iCurLine+98,0,110,high,((obj.farmerCost*1)/(obj.outPigAllWeight*1)).toFixed(2));
	LODOP.ADD_PRINT_TEXT(iCurLine+98,280,150,high,(obj.avgFarmerCost*1).toFixed(2));
	LODOP.ADD_PRINT_TEXT(iCurLine+98,540,150,high,(obj.farmerCost*1).toFixed(2));
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	LODOP.ADD_PRINT_LINE(iCurLine+121,15,iCurLine+121,745,0,1);
	
	LODOP.SET_PRINT_STYLE("Alignment",1);
	LODOP.ADD_PRINT_TEXT(iCurLine+140,45,150,high,"技术员:");
	LODOP.ADD_PRINT_TEXT(iCurLine+140,337,150,high,"财   务:");
	LODOP.ADD_PRINT_TEXT(iCurLine+140,557,150,high,"制表人:"+obj.createUser);
	LODOP.ADD_PRINT_TEXT(iCurLine+175,45,150,high,"部门主管:");
	LODOP.ADD_PRINT_TEXT(iCurLine+175,557,150,high,"饲养户:");
	
	
	
	
}