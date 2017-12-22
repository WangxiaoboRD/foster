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
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridSave.js" type="text/javascript"></script>
    <script type="text/javascript">
		var v;
        var form = null;
        var grid = null;
        var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        $(function(){
			 //获取用户
			 //发出ajax请求获取用户信息
			 var v;
			 var company;
			 var tid;
			 $.ligerui.ligerAjax({
		     	type:"POST",
				async: false,
				url:"feed_use!getUser",	
				dataType:"text",
				success:function(result, textStatus){
					if(result){
						v = result.split(","); 
						tid = v[0];
						company = v[1];
					}
				},
				error: function(XMLHttpRequest,textStatus){
						$.ligerDialog.error('操作出现异常');
				}
				
			 }); 
			 if(tid){
		  		 var formOption = {
		  		 	 url: "feed_use!save",	// 此处也是必须的
		  			 labelWidth: 85, 
		  			 inputWidth: 170,
	           		 space:20,
	           		 excludeClearFields:['e.registDate'],
		  			 fields: [
						{ display: "公司", name: "e.company.id", type: "hidden",options: { value:company}},
						{ display: "登记时间", name: "e.registDate", type: "date",validate: { required: true },newline:true,options: { value: new Date(), showTime: false }},
	             ]};
			 }else{
				 var formOption = {
			  		 	 url: "feed_use!save",	// 此处也是必须的
			  			 labelWidth: 85, 
			  			 inputWidth: 170,
		           		 space:20,
		           		 excludeClearFields:['e.registDate'],
			  			 fields: [
							{ display: "公司", name: "e.company.id", type: "hidden",options: { value:company}},
							{ display: "登记时间", name: "e.registDate", type: "date",validate: { required: true },newline:true,options: { value: new Date(), showTime: false }},
							{ display: "技&nbsp;术&nbsp;员", name:"technician",type:"select",options:{
							    url:'technician!loadByName',
							    valueField: 'id', 
						        textField: 'name',
						        keySupport:true,
								selectBoxHeight: 200,
								triggerToLoad: true,
								autocomplete: true,
								onBeforeOpen: function() {
				                	var g = this;
				                	g.setParm('e.company.id', company);
				                }
							}}
		             ]};
			 }
	  		 var gridOption={
	  		  		 title: '饲料登记明细',
	  		  		 url:'feed_use_detail!loadBatch',
	  		  	 	 submitDetailsPrefix: 'e.details',
	  		  	     delayLoad:false,
	  		  	     enabledEdit: true,
	  		  	     detailsIsNotNull:true,
                     columns:[
                        { display: "代养户", name: 'batch.farmer.name',render: function(data,rowindex){
                        	var text = data.batch.farmer.name; 
							var q = data.batch.quantity;
		    				if(!q || q*1 == 0){
		    					return '<font color="red">'+text+'</font> ';
		    				}else
			    				return text;
	    				}},
                        { display: "批次号", name: 'batch.id',hide:true},
                        { display: "批次号", name: 'batch.batchNumber'},
                        { display: "技术员", name: 'batch.technician.name'},
						{ display: "代乳宝(KG)", name: 'drb',editor: { type: 'text',options: { number: true,initSelect: true  }}},
                        { display: "乳猪宝(KG)", name: 'rzb',editor: { type: 'text',options: { number: true,initSelect: true  }}},
                        { display: "仔猪宝(KG)", name: 'zzb',editor: { type: 'text',options: { number: true,initSelect: true  }}},
						{ display: "551(KG)", name: 'feedA',editor: { type: 'text',options: { number: true,initSelect: true  }}},
	    				{ display: '552(KG)', name: 'feedB',editor: { type: 'text',options: { number: true,initSelect: true  }}},
	    				{ display: "553(KG)", name: 'feedC',editor: { type: 'text',options: { number: true,initSelect: true  }}},
	    				{ display: "554(KG)", name: 'feedD',editor: { type: 'text',options: { number: true,initSelect: true  }}}
              		],
                    toolbar: {
                       items: [
							{ text: '加载客户', icon: 'monitor_go', click: loadClick },
      		                { line: true },	                
      		                { text: '删除', icon:'delete', click: deleteClick }
      	                ]
      	            }
             }
	  		var glist=$.pap.createEFormGridSave({form:formOption,grid:gridOption});
		    grid=glist.getGrid();
        });

         //加载明细
     	 var loadClick = function(){
     		$.ligerDialog.confirm('重新加载客户将清空现有录入,是否要继续?', function(data) {
     			if(data){
     				var technician = $("input[id='technician']").val();
     	     	 	var arra ={'id':technician};
     	         	if(grid){
     	         		grid.setParms(arra);
     	         		grid.options.newPage=1;
     	  		   	    grid.loadData(true);
     	         	}	
 	    		}
        	});
     	}
         //删除
      	 var deleteClick = function(){
      	 	 var selRows = grid.getSelecteds();
             if (selRows.length == 0) {
          		 $.ligerDialog.warn('请选择要删除的记录！');
   			     return;
             }
             grid.deleteSelectedRow(); 
      	 }
	  	
    </script>
</head>
<body>
</body>
</html>

