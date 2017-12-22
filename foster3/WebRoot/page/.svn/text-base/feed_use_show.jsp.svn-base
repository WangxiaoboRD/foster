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
		  			 		tabid:'viewFeedUseOrder'+infoId,
  	   	    				text: '饲料定价单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'feed_use!loadDetailById?id='+infoId
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
		  			 		tabid:'viewFeedUseOrder'+infoId,
  	   	    				text: '饲料定价单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'feed_use!loadDetailById?id='+infoId
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
  			        { display: "单据编号", name:"e.id",type: "text",group: "饲料录入", groupicon: groupicon, options:{value: '${e.id}',readonly: true}},
					{ display: "养殖公司", name:"e.company.name",  type: "text", options:{value: '${e.company.name}',readonly: true}},
					{ display: "登记时间", name: "e.registDate", type: "text", validate: { required: true },options: { value: '${e.registDate}',readonly: true }},
					{ display: "审核状态",name: "e.checkStatus",newline:true, type: "text",options:{readonly:true,value: '${e.checkStatus}',
						 render: function(v){
							var text = v;
							switch(v) {
								case '1': text = "已审核"; break;
								case '0': text = "未审核"; break;
							}
							return text;
					 }}},
					 { display: "审核人",name: "e.checkUser", type: "text",options:{readonly:true,value: '${e.checkUser}'}},
					 { display: "审核时间",name: "e.checkDate", type: "text",options:{readonly:true,value: '${e.checkDate}'}},
					 { display: "创建人",name: "e.createUser", type: "text",newline: true,options:{readonly:true,value: '${e.createUser}'}},
					 { display: "创建时间",name: "e.createDate", type: "text",options:{readonly:true,value: '${e.createDate}'}}
               ]};

	  		 var gridOption={
	  				 title: '饲料登记明细',
	  		  		 url:'feed_use_detail!loadByEntity',
	   			     parms:{"e.feedUse.id":"${e.id}"},
                     columns:[
						{ display: "编码", name: 'id'},
                        { display: "代养户", name: 'batch.farmer.name'},
                        { display: "批次号", name: 'batch.batchNumber'},
						{ display: "代乳宝（KG）", name: 'drb'},
                        { display: "乳猪宝（KG）", name: 'rzb'},
                        { display: "仔猪宝（KG）", name: 'zzb'},
						{ display: "551（KG）", name: 'feedA'},
	    				{ display: '552（KG）', name: 'feedB'},
	    				{ display: "553（KG）", name: 'feedC'},
	    				{ display: "554（KG）", name: 'feedD'}
              		]
             }
             $.pap.createEFormGridShow({toolbar:toolBarOption,form: formOption, grid: gridOption});
        });
	  	
    </script>
</head>
<body></body>
</html>

