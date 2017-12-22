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
   		  			 		tabid:'sysConfigOrder'+infoId,
     	   	    			text: '系统参数【'+infoId+'】',
         	   				params:{ 'ids':ids,'id':infoId},
     	   	    			url: 'sys_config!loadDetailById?id='+infoId
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
   		  			 		tabid:'sysConfigOrder'+infoId,
     	   	    			text: '系统参数【'+infoId+'】',
         	   				params:{ 'ids':ids,'id':infoId},
     	   	    			url: 'sys_config!loadDetailById?id='+infoId
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
					 { display: "养殖公司", name:"s_name",group: "参数设置", groupicon: groupicon,options:{value: '${e.company.name}',readonly:true}, type: "text"},
	                 { display: "销售系数",group: '药品价格销售系数', groupicon: groupicon, name: "e.coefficient",type: "text",options:{value:'${e.coefficient}',readonly:true}}, 
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

