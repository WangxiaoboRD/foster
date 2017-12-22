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
	  		 	 url: "freight!modifyAll",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
					{ display: "运费定价单", name: "e.id", type: "text", validate: { required: true }, options: { value: '${e.id}', readonly: true }, group: "运费定价单", groupicon: groupicon },	  		  			 
					{ display: "养殖公司编码", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
					{ display: "养殖公司", name:"e.company.name",  type: "text", options:{value: '${e.company.name}',readonly: true},group: "单据信息", groupicon: groupicon},
					{ display: "执行时间", name: "e.registDate", type: "date", validate: { required: true }, options: { value: '${e.registDate}', format : "yyyy-MM-dd", showTime: true}},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", name: "e.remark", newline: true, type: "text",options: { value: '${e.remark}'}, width:450}

	  			    ]};

		  		var oldNum1=0;
	            var oldNum2=0;
	            var oldNum3=0;
  	  		   var gridOption={
  	  		  		 title: '运费定价单明细',
	  	  		  	 url:'freight_dtl!loadByEntity',
	        		 parms:{ 'e.freight.id': '${e.id}' },
  	  		  	 	 submitDetailsPrefix: 'e.details',
  	  		  	     enabledEdit: true,
  	  		  	     detailsIsNotNull:true,
                     columns:[
							{ display: "编码", name: 'id',hide:true},
							{ display: "代养户", name: 'farmer.name'},
							{ display: "代养户id", name: 'farmer.id',hide:true},
							{ display: '饲料厂', name: 'feedFac.id', textField: 'feedFac.name', width: 180, editor: { type: 'select',
		       					options: {
			       					url: 'feed_fac!loadByEntity',
			       					valueField: 'id',
					                textField: 'name', 
					                selectBoxHeight: 180,
					                autocomplete: true,
					                keySupport:true,
					                //alwayShowInDown: true,
					                onBeforeOpen: function() {
					                	var g = this;
					                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
				                	},
	       						}
	                 		}},
	                 		{ display: '距饲料厂距离(公里)', name: 'tipscontent', editor: { type: 'text', options: { number: true,initSelect: true  }}},
	                 		{ display: '袋装料运费单价(元/吨)', name: 'packagePrice', editor: { type: 'text', options: { number: true,initSelect: true  }}},
	                 		{ display: '散装料运费单价(元/吨)', name: 'bulkPrice', editor: { type: 'text', options: { number: true,initSelect: true  }}}
		    				
                		],
                		onBeforeEdit: function(e) { 
	        			 	var rowdata = e.record;
							if (rowdata.packagePrice) {
								oldNum1 = rowdata.packagePrice;
			  				}else {
			  					oldNum1 = 0;
				  			}
							if (rowdata.bulkPrice) {
								oldNum2 = rowdata.bulkPrice;
			  				}else {
			  					oldNum2 = 0;
				  			}
							if (rowdata.tipscontent) {
								oldNum3 = rowdata.tipscontent;
			  				}else {
			  					oldNum3 = 0;
				  			}
    	  		 		},
                  		onAfterEdit: function(e) {
    	  		 			var rowdata = e.record;
    						// 起始值
    						if (e.column.name == 'packagePrice') {
    		 					if (rowdata.packagePrice) {
    		  						var num = rowdata.packagePrice*1;
    		  						if(num < 0){
    		  							grid.updateCell('packagePrice', oldNum1, rowdata);
    		  						}
    		 					}
    	  					}
    						if (e.column.name == 'bulkPrice') {
    		 					if (rowdata.bulkPrice) {
    		  						var num = rowdata.bulkPrice*1;
    		  						if(num < 0){
    		  							grid.updateCell('bulkPrice', oldNum2, rowdata);
    		  						}
    		 					}
    	  					}
    						if (e.column.name == 'tipscontent') {
    		 					if (rowdata.tipscontent) {
    		  						var num = rowdata.tipscontent*1;
    		  						if(num < 0){
    		  							grid.updateCell('tipscontent', oldNum3, rowdata);
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
   	    	 var company = $("input[id='e.company.id']").val();
  			if(!company){
  				$.ligerDialog.warn('请选择养殖公司');
  				return ;
  			}
  			
   			$.ligerDialog.open({ 
   			title:'搜索',
   			url: 'price_farmer_list.jsp', 
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
									if (data1['farmer.id'] == data['id']) {
											//isRepeat = true;
											//return false;
										}
									});
									if (!isRepeat) {
		           						grid.addRow({
		           							'farmer.name': data.name,
		           			                'farmer.id': data.id,
		           			             	'tipscontent':'0',
		           			                'packagePrice':'0',
		           			                'bulkPrice':'0'
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

