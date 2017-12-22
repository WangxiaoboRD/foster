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
    <script src="../ligerUI/ligerUI/js/core/base.js" type="text/javascript"></script>   
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
    <script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript" ></script>       
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
    <script type="text/javascript">
    var form;
	var feed,pig;
	var toolbar = null;
	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
    $(function(){
          var currentPage = $.pap.getOpenPage(window);
  	  	  var data = currentPage.getParam("params");
  	  	  var ids=data.ids;	
  	  	  var id = data.id;	
  	  	  toolbar = $("#toolbar").ligerToolBar({ 
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
    		  			 		tabid:'viewOutPigstyOrder'+infoId,
      	   	    				text: '出栏单【'+infoId+'】',
          	   					params:{ 'ids':ids,'id':infoId},
      	   	    				url: 'out_pigsty!loadDetailById?id='+infoId
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
    	  			 		 	tabid:'viewOutPigstyOrder'+infoId,
    	   	    				text: '出栏单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
    	   	    				url: 'out_pigsty!loadDetailById?id='+infoId
    	   	    		    });
      	   	    		$.pap.removeTabItem();
     			     }}
		        ]
 		     });
  	  	     form =$("#form0").ligerForm({
  	  	    	labelWidth: 110, 
	  			 inputWidth: 170,
          		 space:20,
	  			 fields: [
					{display: "出栏单号", name: "e.id", type: "text",options:{value:'${e.id}',readonly:true},group: "出栏单号", groupicon: groupicon},
					{display: "出栏日期", name: "e.registDate", type: "date",group: "出栏单信息", groupicon: groupicon,options: {value:'${e.registDate}',readonly:true}},
	                {display: "代养农户", name:"e.farmer.name",type:"text",options:{value:'${e.farmer.name}',readonly:true}},
	                {display: "养殖批次", name:"e.batch.batchNumber",newline: true,type:"text",options:{value:'${e.batch.batchNumber}',readonly:true}},
	                {display: "出栏类型",name: "e.saleType", type: "text", options:{readonly:true,value: '${e.saleType}',
						   render: function(v){
								var text = v;
								if(text=='Q')
									return "正常出栏";
								else
									return "淘汰出栏";
					}}},
					{display: "出栏头数（头）", name: "e.quantity", type: "number",newline: true,options:{readonly:true,value:'${e.quantity}'}},
					{display: "出栏重量（KG）", name: "e.weight", type: "number",options:{readonly:true,value:'${e.weight}',render: function(data){
                 	   if(data){
						   value = (data*1);
						   return value.toFixed(2);
					   }
                	}}},
					{display: "销售单号", name: "e.farmerSale", type: "text",newline: true,options:{readonly:true,value:'${e.farmerSale}'}},
					{display: "结算状态",name: "e.isBalance",group: "单据状态", groupicon: groupicon, type: "text", options:{readonly:true,value: '${e.isBalance}',
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
				    { display: "审核人",name: "e.checkUser", newline: true,type: "text",options:{readonly:true,value: '${e.checkUser}'}},
				    { display: "审核时间",name: "e.checkDate", type: "text",options:{readonly:true,value: '${e.checkDate}'}},
					{ display: "创建人",name: "e.createUser", newline: true,type: "text",options:{readonly:true,value: '${e.createUser}'}},
					{ display: "创建时间",name: "e.createDate",type: "date",options:{readonly:true,value: '${e.createDate}'}},
               ]});
        });
    </script>
</head>
<body>
	<div id="toolbar"></div>
	<div id="form">
		<form id="form0"></form>
	</div>
</body>
</html>

