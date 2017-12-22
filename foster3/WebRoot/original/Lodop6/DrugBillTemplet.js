//模板1
//领药单
function DRUG_BILL_TEMPLET(obj,iCurLine){
	var high = 16;
	var date = obj.date;
	var items = obj.value;
	
	LODOP.PRINT_INIT("DRUG_BILL_TEMPLET");
//	LODOP.SET_PRINT_PAGESIZE(2,"0","0","A4");
	LODOP.SET_PRINT_STYLE("FontSize",9);
	
	LODOP.ADD_PRINT_TEXT(iCurLine+8,291,258,21,"领药单");
	LODOP.SET_PRINT_STYLEA(0,"FontSize",13);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	
	LODOP.ADD_PRINT_TEXT(iCurLine+55,35,200,high,"部门:");
	LODOP.ADD_PRINT_TEXT(iCurLine+55,400,500,high,"日期:"+date);
	LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	
	LODOP.ADD_PRINT_LINE(iCurLine+70,35,iCurLine+70,735,0,1);
	
	var top1=iCurLine+75;
	LODOP.SET_PRINT_STYLE("Alignment",2);
	LODOP.ADD_PRINT_TEXT(iCurLine+75,50,50,high,"编号");
	LODOP.ADD_PRINT_TEXT(iCurLine+75,100,200,high,"品名");
	LODOP.ADD_PRINT_TEXT(iCurLine+75,300,150,high,"规格");
	LODOP.ADD_PRINT_TEXT(iCurLine+75,450,100,high,"单位");
	LODOP.ADD_PRINT_TEXT(iCurLine+75,550,100,high,"请领");
	
	LODOP.ADD_PRINT_LINE(iCurLine+87,5,iCurLine+87,1060,0,1);
	
	iCurLine=iCurLine+100;
	high = 15;
	if(items.length>0){
		for (var i = 0; i < items.length; i++) {
			LODOP.ADD_PRINT_TEXT(iCurLine,50,50,high,items[i].num);
			LODOP.ADD_PRINT_TEXT(iCurLine,100,200,high,items[i].drugName);
			LODOP.ADD_PRINT_TEXT(iCurLine,300,150,high,items[i].spec);
			LODOP.ADD_PRINT_TEXT(iCurLine,450,100,high,items[i].unit);
			LODOP.ADD_PRINT_TEXT(iCurLine,550,100,high,items[i].quantity);
			iCurLine=iCurLine+high;//每行占15px
		}
		//LODOP.ADD_PRINT_LINE(iCurLine,5,iCurLine,1060,0,1);
		LODOP.ADD_PRINT_LINE(iCurLine-3,35,iCurLine-3,735,0,1);
	}
	//竖线
	LODOP.ADD_PRINT_LINE(top1,50,iCurLine+18,50,0,1);
	LODOP.ADD_PRINT_LINE(top1,100,iCurLine+18,100,0,1);
	LODOP.ADD_PRINT_LINE(top1,300,iCurLine+18,300,0,1);
	LODOP.ADD_PRINT_LINE(top1,450,iCurLine+18,450,0,1);
	LODOP.ADD_PRINT_LINE(top1,550,iCurLine+18,550,0,1);
	LODOP.ADD_PRINT_LINE(top1,650,iCurLine+18,650,0,1);
	return iCurLine;
}
