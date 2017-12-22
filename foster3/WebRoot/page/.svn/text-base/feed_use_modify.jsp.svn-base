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
    <script src="../ligerUI/ligerUI/js/plugins/XGrid.js" type="text/javascript"></script>    
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridEdit.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/core/ligerAjax.js" type="text/javascript" ></script>
    <script src="../ligerUI/ligerUI/js/json2.js" type="text/javascript" ></script>
    <script type="text/javascript">
		var v;
        var form = null;
        var grid = null;
        var delIds = ""; // 删除的明细ids
        var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        $(function(){
	  		 var formOption = {
	  		 	 url: "feed_use!modifyAll",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
					{ display: "单据编号", name:"e.id",type: "hidden", options:{value: '${e.id}'}},
					{ display: "养殖公司", name:"e.company.name",  type: "text",group: "饲料录入", groupicon: groupicon, options:{value: '${e.company.name}',readonly: true}},
					{ display: "养殖公司编码", name:"e.company.id",  type: "hidden",options:{value: '${e.company.id}'}},
					{ display: "登记时间", name: "e.registDate", type: "date", validate: { required: true },newline:true,options: { value: '${e.registDate}', showTime:false }}
				]};
  	  		    var gridOption={
  	  		    	 title: '饲料登记明细',
	  		  		 url:'feed_use_detail!loadByDetail',
	   			     parms:{"e.feedUse.id":"${e.id}"},
	  		  	 	 submitDetailsPrefix: 'e.details',
	  		  	     delayLoad:false,
	  		  	     enabledEdit: true,
	  		  	     detailsIsNotNull:true,
                     columns:[
						{ display: "编码", name: 'id',hide:true},
                        { display: "代养户", name: 'batch.farmer.name'},
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
      		                { text: '添加', icon: 'add', click: addClick },
      		                { line: true },	                
      		                { text: '删除', icon:'delete', click: deleteClick }
      	                ]
      	            }
               }
  	  		   var template=$.pap.createEFormGridEdit({form:formOption,grid:gridOption});
  		       grid=template.getGrid();
  		       // 记录删除对象id集合
		  	   template.appendOtherData = function(){
		     	  var result= {'e.tempStack.deleteIds': delIds } ;
		          return result;
		       }
          });
   	      //生成明细
   	      var addClick = function(){
   			$.ligerDialog.open({ 
   			title:'批次搜索',
   			url: 'batch_search.jsp', 
   			height: 500,
   			width: 750, 
   			buttons: [ 
   				{ text: '选择', onclick:  function (item, dialog) { 
   					var selRows = dialog.frame.f_select();
   					if (selRows.length < 1) {
   						$.ligerDialog.warn('请选择记录进行操作');
	           		}else {
						$(selRows).each(function(index, data){
							var isRepeat = false;
							$(grid.rows).each(function(index, data1){
								if(data1['batch.id'] == data['id']) {
										isRepeat = true;
										return false;
									}
								});
								if (!isRepeat) {
	           						grid.addRow({
	           						    'batch.farmer.name': data.farmer.name,
	           						    'batch.id':data.id,
	           			                'batch.batchNumber':data.batchNumber,
	           			                'batch.technician.name':data.technician.name
	           			            });
								}
	           				});
           					dialog.close();
	           			}
    				}}, 
					{ text: '取消', onclick:  function (item, dialog) { dialog.close(); }}
					] 
				 });
   	 	 }
         //删除
       	 var deleteClick = function(){
       	 	  var selRows = grid.getSelecteds();
              if (selRows.length == 0) {
           		  $.ligerDialog.warn('请选择要删除的记录！');
    			      return;
              }
              $(selRows).each(function(index, data){
                  if(data['id'])
                	  delIds += data['id']+',';
              });
    		  //删除选中的行
              grid.deleteSelectedRow(); 
        }
    </script>
</head>
<body></body>
</html>

