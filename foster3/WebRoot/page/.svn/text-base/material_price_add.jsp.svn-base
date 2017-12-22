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
	  		 var formOption = {
	  		 	 url: "material_price!save",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
           		 excludeClearFields:['company','e.registDate','e.remark'],
	  			 fields: [
					{ display: "养殖公司", name:"e.company.id", type:"select",comboboxName: 'company', group: "物料定价单", groupicon: groupicon,validate: { required: true }, options:{
						url: 'company!loadByEntity',
					    valueField: 'id',
					    textField: 'name', 
					    autocomplete: true,
					    keySupport:true,
					    triggerToLoad: true
					}},
					{ display: "执行时间", name: "e.startDate", type: "date", validate: { required: true }, options: { value: new Date(), format : "yyyy-MM-dd", showTime: true}},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", name: "e.remark", type: "text",newline: true,width:450}

  			        ]};
             var oldNum=0;
	  		 var gridOption={
	  		  		 title: '物料定价单明细',
	  		  	 	 submitDetailsPrefix: 'e.details',
	  		  	     enabledEdit: true,
	  		  	     detailsIsNotNull:true,
                     columns:[
						{ display: "物料名称", name: 'material.name'},
						{ display: "物料id", name: 'material.id',hide:true},
						{ display: '物料编码',name: 'material.code'},
						
						{ display: "药品规格", name: 'material.spec'},
						{ display: "单位", name: 'material.unit.value'},
	    				{ display: '购进单价(元)', name: 'price', editor: { type: 'text', options: { number: true,initSelect: true  }}},
	    				{ display: '销售单价(元)', name: 'salePrice', editor: { type: 'text', options: { number: true,initSelect: true  }}}
	    				
              		],
              		onBeforeEdit: function(e) { 
			  			var rowdata = e.record;
							if (rowdata.price) {
								oldNum = rowdata.price;
		  				}else {
		  					oldNum = 0;
			  			}
	  		 		},
              		onAfterEdit: function(e) {
	  		 			var rowdata = e.record;
						// 起始值
						if (e.column.name == 'price') {
		 					if (rowdata.price) {
		  						var num = rowdata.price*1;
		  						if(num < 0){
		  							grid.updateCell('price', oldNum, rowdata);
		  						}
		 					}
	  					}
					},
                    toolbar: {
                       items: [
      		                { text: '添加', icon: 'add', click: addClick },
      		                { line: true },	                
      		                { text: '删除', icon:'delete', click: deleteClick }
      	                ]
      	            }
             }
	  		var glist=$.pap.createEFormGridSave({form:formOption,grid:gridOption});
		    grid=glist.getGrid();
        });
        //生成明细
   	    var addClick = function(){
   	    	var company = $("input[id='e.company.id']").val();
			if(!company){
				$.ligerDialog.warn('请选择养殖公司');
				return ;
			}
   			$.ligerDialog.open({ 
   			title:'搜索',
   			url: 'price_material_list.jsp', 
   			height: 500,
   			width: 750, 
   			onLoaded: function(param){
		        var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
		           //$('div.toolbar-pap',documentF).hide();
		           $('input[id="e.company.id"]',documentF).attr('value', $('input[id="e.company.id"]').val()); 
   			},
   			buttons: [ 
   				{ text: '选择', onclick:  function (item, dialog) { 
   					var selRows = dialog.frame.f_select();
   					if (selRows.length < 1) {
   						$.ligerDialog.warn('请选择记录进行操作');
	           		}else {
							$(selRows).each(function(index, data){
								var isRepeat = false;
								$(grid.rows).each(function(index, data1){
									if (data1['material.id'] == data['id']) {
											isRepeat = true;
											return false;
										}
									});
									if (!isRepeat) {
		           						grid.addRow({
		           			                'material.name': data.name,
		           			                'material.id': data.id,
		           			             	'material.code': data.code,
		           			                
		           			                'material.spec':data.spec,
		           			                'material.unit.value':data.unit.value,
		           			                'price':'0',
		           			             	'salePrice':'0'
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
   		     //删除选中的行
             grid.deleteSelectedRow(); 
      	 }
	  	
    </script>
</head>
<body></body>
</html>

