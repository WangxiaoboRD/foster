<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
	<title>${initParam.title}</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridShow.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
          var currentPage = $.pap.getOpenPage(window);
  	  	  var data = currentPage.getParam("params");
  	  	  var ids=data.ids;	
  	  	  var id = data.id;	
          var toolBarOption = { 
                items: [
                 { text: '关闭', icon: 'delete', click: function(item){
                   $.pap.removeTabItem();
                 }},
                  { line:true },
 	              { text: '上一单', icon: 'up', click: function(item){
 	                 var infoId="";
 	                 for(var i=0;i<ids.length;i++){
 	                    if(ids[i]==id){
 	                      if(i==0){
	 	                     $.ligerDialog.warn('到顶啦！');
	 	                      return;
 	                      }else{
 	                        infoId=ids[i-1];
 	                      }
 	                      continue;
 	                    }
 	                 }
		  			$.pap.addTabItem({ 
		  			 		tabid:'viewFarmerSaleOrder'+infoId,
  	   	    				text: '代养销售单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'farmer_sale!loadDetailById?id='+infoId
  	   	    		});
  	   	    		$.pap.removeTabItem();
 			      }},
                  { line:true },
 	              { text: '下一单', icon: 'down', click: function(item){
 	                 var infoId="";
 	                 for(var i=0;i<ids.length;i++){
 	                    if(ids[i]==id){
 	                      if(i==(ids.length-1)){
	 	                     $.ligerDialog.warn('到底啦！');
	 	                      return;
 	                      }else{
 	                        infoId=ids[i+1];
 	                      }
 	                      continue;
 	                    }
 	                 }
 	           		
		  			$.pap.addTabItem({ 
		  			 		tabid:'viewFarmerSaleOrder'+infoId,
  	   	    				text: '代养销售单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'farmer_sale!loadDetailById?id='+infoId
  	   	    		});
  	   	    		$.pap.removeTabItem();
 			      }}
 		     ]};
          	 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  		 var formOption = {
	  			 labelWidth: 98, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
					{ display: "代养销售单", name:"e.id",type: "text",options: { value: '${e.id}', readonly: true },group: "销售单", groupicon: groupicon},
					{ display: "代养农户", name:"e.farmer.name",type: "text", options: { value: '${e.farmer.name}',readonly:true},group: "销售信息", groupicon: groupicon},
					{ display: "养殖批次", name:"e.batch.batchNumber",type: "text", options: { value: '${e.batch.batchNumber}', readonly: true }},
					{ display: "销售日期", name: "e.registDate", type: "date",options: {value:'${e.registDate}',readonly: true}},
					{ display: "出栏单", name:"e.outPigsty.id",type:"text",newline: true,options:{value:'${e.outPigsty.id}',readonly: true}},
					{ display: "出栏类型", name: "v", type: "text",options: {readonly: true,value:'${e.outPigsty.saleType}',render:function(data){
						if(data=='Q')
							return '正常出栏';
						else if(data=='E')
							return '淘汰出栏';
					}}},
					{ display: "出栏头数", name: "head", type: "text",options: {readonly: true,value:'${e.outPigsty.quantity}'}},
					
					{ display: "销售头数(头)", name: "e.totalQuan",newline: true, type: "text",options:{value:'${e.totalQuan}',readonly:true}},
					{ display: "销售重量(KG)", name: "e.totalWeight", type: "text",options: {value:'${e.totalWeight}',readonly: true,render: function(data){
	                 	   if(data){
							   value = (data*1);
							   return value.toFixed(2);
						   }
	                 }}},
					{ display: "销售金额(元)", name: "e.totalMoney", type: "text",options: {value:'${e.totalMoney}',readonly: true,render: function(data){
	                 	   if(data){
							   value = (data*1);
							   return value.toFixed(2);
						   }
	                }}},

					{display: "销售商", name:"e.buyer.name",group: "公司销售信息", groupicon: groupicon,type:"text",options:{value:'${e.buyer.name}',readonly:true}},
					{display: "销售头数(头)", name: "e.quantity", type: "number",options:{readonly:true,value:'${e.quantity}'}},
					{display: "销售重量(KG)", name: "e.weight", type: "number",options:{readonly:true,value:'${e.weight}',render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{display: "销售金额(元)", name: "e.amount",newline: true, type: "number",options:{readonly:true,value:'${e.amount}',render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{display: "运输费用(元)", name: "e.tcost", type: "number",options:{readonly:true,value:'${e.tcost}',render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},

				    { display: "结算状态",name: "e.isBalance",group: "单据状态", groupicon: groupicon, type: "text", options:{readonly:true,value: '${e.isBalance}',
						   render: function(v){
								var text = v;
								if(text=='1')
									return "已结算";
								else
									return "未结算";
					}}},
				    {display: "审核状态",name: "e.checkStatus", type: "text", options:{readonly:true,value: '${e.checkStatus}',
					   render: function(v){
							var text = v;
							if(text=='1')
								return "已审核";
							else
								return "未审核";
					}}},
				    { display: "审核人",name: "e.checkUser", type: "text",options:{readonly:true,value: '${e.checkUser}'}},
				    { display: "审核时间",name: "e.checkDate", newline: true,type: "text",options:{readonly:true,value: '${e.checkDate}'}},
					{ display: "创建人",name: "e.createUser",type: "text",options:{readonly:true,value: '${e.createUser}'}},
					{ display: "创建时间",name: "e.createDate", type: "date",options:{readonly:true,value: '${e.createDate}'}}
               ]};
	  		   var gridOption={
	  				title: '销售单明细',
	  				url:'farmer_sale_dtl!loadByEntity',
	        		parms:{ 'e.farmerSale.id': '${e.id}'},
                    columns:[
						{ display: "销售级别", name: 'pigLevel.dcode',textField: 'pigLevel.value'},
						{ display: "合同定价", name: 'contPrice',align:'right',totalSummary:{
	                        render: function (suminf, column, cell){
		                        return '<div>合计:</div>';
		                    },align: 'left'
		                }},
						{ display: "交易价", name: 'actualPrice',align:'right'},
						{ display: "销售头数", name: 'quantity',align:'right',totalSummary:{
	                        render: function (suminf, column, cell){
	                            return '<div>'+(suminf.sum).toFixed(0) + '</div>';
	                        },
	                        align: 'left'
	                    }},
						{ display: "销售重量(KG)", name: 'weight',align:'right',render: function(data){
		                  	   if(data.weight){
								   value = (data.weight*1);
								   return value.toFixed(2);
							   }
		                },totalSummary:{
	                        render: function (suminf, column, cell){
	                            return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                        },
	                        align: 'left'
	                    }},
						{ display: "销售金额(元)", name: 'amount',align:'right',render: function(data){
		                  	   if(data.amount){
								   value = (data.amount*1);
								   return value.toFixed(2);
							   }
		                },totalSummary:{
	                        render: function (suminf, column, cell){
	                            return '<div>'+(suminf.sum).toFixed(2) + '</div>';
	                        },
	                        align: 'left'
	                    }}
             		],
             }
             $.pap.createEFormGridShow({toolbar:toolBarOption,form: formOption, grid: gridOption});
        });
    </script>
</head>
<body></body>
</html>

