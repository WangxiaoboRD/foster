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
          $("#toolbar").ligerToolBar ( { 
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
		  			 		tabid:'viewBatch'+infoId,
  	   	    				text: '批次查看【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'batch!loadDetailById?id='+infoId
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
		  			 		tabid:'viewBatch'+infoId,
  	   	    				text: '批次查看【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'batch!loadDetailById?id='+infoId
  	   	    		});
  	   	    		$.pap.removeTabItem();
 			      }}
 		 ]});
          	 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  		$("#form0").ligerForm ({
	  			 labelWidth: 90, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
  			        { display: "批次号", name: "e.batchNumber", type: "text",  options: { value: '${e.batchNumber}', readonly: true }, group: "批次号", groupicon: groupicon },	  		  			 
					{ display: "养殖公司", name:"e.company.id",newline: true,  type: "text", options:{value: '${e.company.name}',readonly: true},group: "批次信息", groupicon: groupicon},
					{ display: "代养户",name: "e.farmer.id", type: "text",options:{readonly:true,value: '${e.farmer.name}'}},
					{ display: "所属合同",name: "e.contract.code", type: "text",options:{readonly:true,value: '${e.contract.code}'}},
					{display: "开发人员", name: "e.developMan.name",newline: true,type: "text",options:{value:'${e.developMan.name}',readonly: true}},
					{display: "技术人员", name: "e.technician.name",type: "text",options:{value:'${e.technician.name}',readonly: true}},
					{ display: '是否已结算',name: 'e.isBalance',type: "text",options:{readonly:true,value: '${e.isBalance}',render: function(data){
    					if(data=='Y')
    						return '已结算'; 
    					else
    						return '未结算';	
	    			}}},
	    			{ display: '进猪时间',name: 'e.inDate',newline: true,type: "text",options:{readonly:true,value: '${e.inDate}'}},
    				{ display: '预计出栏时间',name: 'e.outDate',type: "text",options:{readonly:true,value: '${e.outDate}'}},
    				{ display: "料肉比",name: "e.fcr", type: "text",options:{readonly:true,value: '${e.fcr}'}},
    				{ display: "创建人",name: "e.createUser",newline: true, type: "text",options:{readonly:true,value: '${e.createUser}'}},
				    { display: "创建时间",name: "e.createDate", type: "date",options:{readonly:true,value: '${e.createDate}'}},

	    			{ display: "合同头数",name: "e.contQuan",group: "头数信息", groupicon: groupicon, type: "text",newline: true,options:{readonly:true,value: '${e.contQuan}'}},
	    			{ display: "进猪头数",name: "e.pigletQuan", type: "text",options:{readonly:true,value: '${e.pigletQuan}'}},
					{ display: "当前头数",name: "e.quantity", type: "text",options:{readonly:true,value: '${e.quantity}'}},
					{ display: "淘汰头数",name: "e.eliminateQuan",newline: true, type: "text",options:{readonly:true,value: '${e.eliminateQuan}'}},
					
					{ display: "死亡头数",name: "e.deathQuan", type: "text",options:{readonly:true,value: '${e.deathQuan}'}},
					{ display: "免责死亡数",name: "e.otherDeathQuan",type: "text",options:{readonly:true,value: '${e.otherDeathQuan}'}},
					{ display: "出栏头数",name: "e.saleQuan",newline: true, type: "text",options:{readonly:true,value: '${e.saleQuan}'}}
					
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

