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
	  			 		 	tabid:'viewpigPurchaseOrder'+infoId,
	   	    				text: '生猪采购【'+infoId+'】',
	  	   					params:{ 'ids':ids,'id':infoId},
		   	    				url: 'pig_purchase!loadDetailById?id='+infoId
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
    	  			 		 	tabid:'viewpigPurchaseOrder'+infoId,
    	   	    				text: '生猪采购【'+infoId+'】',
      	   						params:{ 'ids':ids,'id':infoId},
    	   	    				url: 'pig_purchase!loadDetailById?id='+infoId
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
					{ display: "单&nbsp;据&nbsp;号", name: "e.id",type: "text",options: {readonly:true, value: '${e.id}' },group: "猪苗登记单", groupicon: groupicon},
					{ display: "登记时间", name: "e.registDate", type: "date",validate: { required: true },options: {readonly:true, value: '${e.registDate}', showTime: false }},
					{ display: "养殖公司", name:"e.company.id",newline:true, type: "text", options:{readonly:true,value: '${e.company.name}'}},
					{ display: "代&nbsp;养&nbsp;户", name:"e.farmer.name",  type: "text",  options:{value: '${e.farmer.name}',readonly: true}},
					{ display: "批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", name:"e.batch.batchNumber", newline:true, type: "text",  options:{value: '${e.batch.batchNumber}',readonly: true}},
					{ display: "运输公司", name:"e.transportCo.id",type:"text",options:{readonly: true, value: '${e.transportCo.name}'}},
					{ display: "司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机",newline: true, name:"e.driver.id",type:"text",options:{readonly: true, value: '${e.driver.name}'}},
					{ display: "车&nbsp;牌&nbsp;号", name: "e.carNum",type: "text",options: {readonly: true, value: '${e.carNum}'} },
					
					{ display: "供&nbsp;应&nbsp;商", name:"e.pigletSupplier.id",type:"text",group: "猪苗采购信息", groupicon: groupicon,options: {readonly: true, value: '${e.pigletSupplier.name}'}},
					{ display: "头&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数", name: "e.quantity", type: "text",validate: { required: true },options: {readonly: true, value: '${e.quantity}'}},
					{ display: "重量(kg)", name: "e.weight", type: "text",newline: true,options: {readonly: true, value: '${e.weight}' }},
					{ display: "运费(元)", name: "e.freight", type: "text",options: {readonly: true, value: '${e.freight}' }},
					{ display: "成本单价", name: "e.price", type: "text",newline: true,width:170,options:{readonly: true,value:'${e.price}'}},
					{ display: "成本(元)", name: "e.cost", type: "text",options: {readonly: true, value: '${e.cost}' }},
					{ display: "日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄",newline: true, name: "e.days", type: "text",options: {readonly: true, value: '${e.days}' }},
					{ display: "赠送头数", name: "e.giveQuantity", type: "text",options: {readonly: true, value: '${e.giveQuantity}' }},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", width:472,name: "e.remark", type: "text",newline:true,options: { readonly: true,value: '${e.remark}'}},
					{ display: "审核状态",name: "e.checkStatus", type: "text",group: "状态信息", groupicon: groupicon,options:{readonly:true,value: '${e.checkStatus}',render:
					  	function(v){
							var text = v;
							switch(v) {
								case '1': text = "已审核"; break;
								case '0': text = "未审核"; break;
							}
							return text;
						}
					}},
					{ display: "审&nbsp;核&nbsp;人",name: "e.checkUser", type: "text",options:{readonly:true,value: '${e.checkUser}'}},
					{ display: "审核时间",newline:true,name: "e.checkDate", type: "text",options:{readonly:true,value: '${e.checkDate}'}},
					{ display: "回执状态", name:"e.isReceipt",type:"text",options:{readonly:true,value: '${e.isReceipt}',render:
					  	function(v){
							var text = v;
							switch(v) {
								case 'Y': text = "已回执"; break;
								case 'N': text = "未回执"; break;
							}
							return text;
						}
					}},
					{ display: '结算状态',name: 'e.isBalance',type: "text",newline: true,options:{readonly:true,value: '${e.isBalance}',render: function(data){
    					if(data=='Y')
    						return '已结算'; 
    					else
    						return '未结算';	
	    			}}},
	    			{ display: "创&nbsp;建&nbsp;人",name: "e.createUser", type: "text",options:{readonly:true,value: '${e.createUser}'}},
				    { display: "创建时间",newline:true,name: "e.createDate", type: "date",options:{readonly:true,value: '${e.createDate}'}}
					
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

