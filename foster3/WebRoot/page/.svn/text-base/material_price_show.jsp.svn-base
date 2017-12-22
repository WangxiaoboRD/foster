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
		  			 		tabid:'viewMaterialPriceOrder'+infoId,
  	   	    				text: '物料定价单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'material_price!loadDetailById?id='+infoId
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
		  			 		tabid:'viewMaterialPriceOrder'+infoId,
  	   	    				text: '物料定价单【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
  	   	    				url: 'material_price!loadDetailById?id='+infoId
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
  			        { display: "物料定价单", name: "e.id", type: "text",  options: { value: '${e.id}', readonly: true }, group: "物料定价单", groupicon: groupicon },	  		  			 

					{ display: "养殖公司", name:"e.company.name",  type: "text", options:{value: '${e.company.name}',readonly: true},group: "单据信息", groupicon: groupicon},
					{ display: "执行时间", name: "e.startDate", type: "date", options: { value: '${e.startDate}', format : "yyyy-MM-dd", showTime: true ,readonly: true}},
					{ display: "创建时间",name: "e.createDate", type: "date",options:{readonly:true,value: '${e.createDate}'}},

					 { display: "创建人",name: "e.createUser", type: "text",newline: true,options:{readonly:true,value: '${e.createUser}'}},
					 { display: "审核状态",name: "e.checkStatus", type: "text",options:{readonly:true,value: '${e.checkStatus}',
						 render: function(v){
							var text = v;
							switch(v) {
								case '1': text = "已审核"; break;
								case '0': text = "未审核"; break;
							}
							return text;
					 }}},
					 { display: "审核人",name: "e.checkUser", type: "text",options:{readonly:true,value: '${e.checkUser}'}},

					 { display: "审核时间",name: "e.checkDate", type: "text",newline: true,options:{readonly:true,value: '${e.checkDate}'}},
					 { display: "备注", name: "e.remark", type: "text",options: { readonly:true,value: '${e.remark}'},width:450}
					
               ]};

	  		 var gridOption={
	  				title: '物料定价单明细',
	  				url:'material_price_dtl!loadByEntity',
	        		parms:{ 'e.materialPrice.id': '${e.id}' },
                    columns:[
                       { display: "编码", name: 'id',hide:true},
                       { display: '物料名称',name: 'material.name'},
                       { display: '物料编码',name: 'material.code'},
                       
						{ display: "物料规格", name: 'material.spec'},
						{ display: "单位", name: 'material.unit.value'},
						{ display: "购进单价(元)", name: 'price'},
						{ display: "销售单价(元)", name: 'salePrice'}
             		],
             }
             $.pap.createEFormGridShow({toolbar:toolBarOption,form: formOption, grid: gridOption});
        });
	  	
    </script>
</head>
<body></body>
</html>

