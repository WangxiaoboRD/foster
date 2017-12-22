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
	<style type="text/css">
		.toolbar-pap {
		    height: 0px;
		}
		.l-toolbar {
		    height: 0px;
		    border: 1px solid #9CBAE7;
		    border-top: 1px solid #EFF7F7;
		}
		.l-dialog-win .l-dialog-content {
		    padding-top: 0px;
		    padding-bottom: 2px;
		}
	</style> 
	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script src="../ligerUI/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/ligerui.all.js"></script> 
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var grid = null;
    	var glist = null;
        $(function () {
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
			 if(!tid){
	        	 form = {
	                  labelWidth: 70,
	                  fields:[
							{ display: "技&nbsp;术&nbsp;员", name:"e.technician.id",type:"select",newline:true,options:{
							    url:'technician!loadByName',
							    valueField: 'id', 
							    textField: 'name',
							    keySupport:true,
								selectBoxHeight: 200,
								triggerToLoad: true,
								autocomplete: true,
								onBeforeOpen: function() {
							    	var g = this;
							    	if(!v){
							    		g.setParm('e.id', v);
							    	}else{
							    		g.setParm('e.company.id', company);
							    	}
							    }
							}}	
	 	    		  ],
					  buttons:[
	                    { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', width: 70, click:function(item){
	                    	var arra = $('form').formToArray();
	                    	if(grid){
	                    		grid.setParms(arra);
	                    		grid.options.newPage=1;
			           		    grid.loadData(true);
	                    	}
	                    }}
	                 ]
	             }
			 }
			
        	 var gridoption={
       			 url:'batch!loadByUser',
       			 delayLoad:true,
                 usePager: false,
                 checkbox: true,
                 width: '99.8%',
                 columns:[
                 	{ display: "批次ID", name:"id",hide:true},
                 	{ display: "代养户", name: 'farmer.name',width:130,render: function(data,rowindex){
                    	var text = data.farmer.name; 
						var q = data.quantity;
	    				if(!q || q*1 ==0){
	    					return '<font color="red">'+text+'</font> ';
	    				}else
		    				return text;
    				}},
                 	{ display: "批次号", name:"batchNumber",width:120},
                 	{ display: '养殖公司编码',name: 'company.id',hide:true},
	    			{ display: '养殖公司',name: 'company.name',width:150},
	    			
	    			{ display: '是否结算', name: 'isBalance',width:90,render:function(data){
		    			if(data.isBalance=='N')
		    				return '未结算';
		    			else
		    				return'已结算';
	    			}}
             	]
             }
            if(tid)
        		glist=$.pap.createFormGridList({grid:gridoption});
            else
            	glist=$.pap.createFormGridList({form:form,grid:gridoption});
            grid=glist.getGrid();
        });
        function f_select(){
        	return grid.getSelectedRows();
        }
    </script>
</head>
<body>
</body>
</html>
