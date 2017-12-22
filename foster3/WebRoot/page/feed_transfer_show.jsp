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
		  			 		tabid:'viewFeedTransfer'+infoId,
  	   	    				text: '饲料转接单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'feed_transfer!loadDetailById?id='+infoId
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
		  			 		tabid:'viewFeedTransfer'+infoId,
  	   	    				text: '饲料转接单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'feed_transfer!loadDetailById?id='+infoId
  	   	    		});
  	   	    		$.pap.removeTabItem();
 			      }}
 		 ]};
	   	   			      
          	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  		 var formOption = {
	  			 labelWidth: 80, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
  			        { display: "转接单号", name: "e.id", type: "text",  options: { value: '${e.id}', readonly: true }, group: "饲料转接单", groupicon: groupicon },
  			        { display: "转接时间", name: "e.registDate", type: "date", options: { value: '${e.registDate}', showTime: true ,readonly: true}},	  		  			 
					{ display: "转出代养户",name: "e.outFarmer.id", type: "text",options:{readonly:true,value: '${e.outFarmer.name}'},group: "单据信息", groupicon: groupicon},
					{ display: "转出批次",name: "e.outBatch.id", type: "text",options:{readonly:true,value: '${e.outBatch.batchNumber}'}},
					{ display: "转入代养户",name: "e.inFarmer.id", type: "text",options:{readonly:true,value: '${e.inFarmer.name}'}},
					{ display: "转入批次",newline:true,name: "e.inBatch.id", type: "text",options:{readonly:true,value: '${e.inBatch.batchNumber}'}},
					{ display: "运输公司", name:"e.transportCo", type: "text", options:{value: '${e.transportCoName}',readonly: true}},
					{ display: "司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机",name: "e.driver", type: "text",options:{readonly:true,value: '${e.driverName}'}},
				    { display: "创&nbsp;建&nbsp;人",name: "e.createUser", type: "text",newline: true,options:{readonly:true,value: '${e.createUser}'}},
				    { display: "创建时间",name: "e.createDate", type: "date",options:{readonly:true,value: '${e.createDate}'}},
				    { display: '是否已结算',name: 'e.isBalance',type: "text",options:{readonly:true,value: '${e.isBalance}',render: function(data){
    					if(data=='Y')
    						return '已结算'; 
    					else
    						return '未结算';	
	    			}}},
				    { display: "审核状态",name: "e.checkStatus", type: "text",newline: true,options:{readonly:true,value: '${e.checkStatus}',render:
					  	function(v){
							var text = v;
							switch(v) {
								case '1': text = "已审核"; break;
								case '0': text = "未审核"; break;
							}
							return text;
						}
					}},
				    { display: "审核人",name: "e.checkUser", type: "text",options:{readonly:true,value: '${e.checkUser}'}},
				    { display: "审核时间",name: "e.checkDate", type: "text",options:{readonly:true,value: '${e.checkDate}'}},
				    { display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", name: "e.remark", type: "text",newline: true,options: { readonly:true,value: '${e.remark}'},width:450}
					
               ]};

	  		 var gridOption={
	  				url:'feed_transfer_dtl!loadByEntity',
	        		parms:{ 'e.feedTransfer.id': '${e.id}' },
                    columns:[
						{ display: "编码", name: 'id',hide:true},
							{ display: "饲料id", name: 'feed.id',hide:true},
	                        { display: "饲料类型", name: 'feed.feedType.name'},
	                        { display: "饲料编码", name: 'feed.code'},
							{ display: "饲料名称", name: 'feed.name'},
							{ display: "包装方式", name: 'feed.packForm',render:function(data){
	                            if(data.packForm==1)
	                                return '散装';
	                            else
	                                return '袋装';
	                        }},
	                        { display: "饲料规格", name: 'feed.spec'},
							{ display: "单位", name: 'feed.unit.value'},
							{ display: "副单位", name: 'feed.subUnit.value'},
							{ display: '副单位重量', name: 'subQuantity', width: 150, editor: { type: 'text',options: { number: true,initSelect: true  }},render:function(data){
								if(data.subQuantity)
									return (data.subQuantity*1).toFixed(2)
							}},
		    				{ display: '重量', name: 'quantity', width: 150, editor: { type: 'text',options: { number: true,initSelect: true  }}},
		    				{ display: "换算比值", name: 'feed.ratio',hide:true}
             		],
             }
	  		 var template = $.pap.createEFormGridShow({toolbar:toolBarOption,form: formOption, grid: gridOption});
	  		 var grid=template.getGrid();
        });
	  	
    </script>
</head>
<body></body>
</html>
