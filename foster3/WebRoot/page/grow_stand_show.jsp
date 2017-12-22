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
	<script type="text/javascript" src="../ligerUI/ligerUI/js/ligerui.all.js"></script>
	<script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript" ></script> 
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
    <script type="text/javascript">

    	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
    	var toolbar = null;
    	var gridarea,gridnoarea,gridfcr;
    	 // 获得当前页面对象
		 //var currentPage = $.pap.getOpenPage(window);
		 //var currentParam = currentPage.getParam("param");
        $(function () {
            toolbar = $("#toolbar").ligerToolBar({
            	items: [
                	 { text: '关闭', icon: 'delete', click: function(){
                		 $.pap.removeTabItem();
                	 }}
                 ]
             });
            $("#form0").ligerForm({
                fields: [
					{ display: "编码",name: "e.id", type: "hidden",options:{value:"${e.id}"}},
                    { display: "养殖公司", name:"e.company.id",group: "生长标准", groupicon: groupicon, type:"select", options:{
						readonly:true,
					    url:'company!load',
					    valueField: 'id', 
				        textField: 'name',
				        selectBoxWidth:180,
						selectBoxHeight: 150,
						value: '${e.company.id}' 
					}},
					{ display: "登记时间", name: "e.registDate",type: "date",options:{format : "yyyy-MM-dd hh:mm",showTime: true,value:"${e.registDate}",readonly:true}},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注",newline: true, name: "e.remark", type: "text",width:490,options:{value:"${e.remark}",readonly:true}}
				 	
                 ]
            });
            gridarea = $("#form1").ligerGrid({
  				 detailsIsNotNull:true,
    			 url:"grow_stand_dtl!loadByEntity",
    			 parms:{"e.growStand.id":"${e.id}"},
                 columns:[
 	       				{ display:'日龄(天)',name:'days'},
 		       			{ display:'标准体重(kg)',name:'standWeight',width:150},
	       				{ display:'饲料阶段',name:'feedType.name',width:150},
	       		 		{ display:'饲喂量(kg)',name:'feedWeight',width:150}
 	       				
         			 ],
         			 usePager: false, 
         			 checkbox:false,
         			 height:'99%',
                     rownumbers:true
            });
           
        	$("#tab").ligerTab();
        	$.each(toolbar.toolButtons, function(i, item){
            	if (i == 1) {
	        		this.set('visible', false);
               	}
			});
			//分组 收缩/展开  
		    $(".togglebtn").live('click', function (){
		    	gridarea.setHeight(gridarea.options.height);
		    	gridnoarea.setHeight(gridnoarea.options.height);
		    	gridfcr.setHeight(gridfcr.options.height);
		    	
		    });
        });
    </script>
</head>
<body>
	<div id="toolbar"></div>
	<div id="form">
		<form id="form0"></form>
	</div>
	<div id="tab">
		<div id="tab1" title="肉猪饲养标准">
			<form id="form1"></form>
		</div>
		
	</div>
	<div id="loading" class="l-sumbit-loading" style="display: none;"></div>
</body>
</html>
