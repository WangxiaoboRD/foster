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
	  		 	 url: "drug_allocation!modifyAll",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
	  			    { display: "调拨单号", name: "e.id", type: "text", validate: { required: true }, options: { value: '${e.id}', readonly: true }, group: "药品调拨单", groupicon: groupicon },	  		  			 
					{ display: "养殖公司id", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
					{ display: "饲料厂id", name:"e.feedFac.id", type: "hidden", options:{value: '${e.feedFac.id}'}},
					{ display: "饲料厂", name:"e.feedFac.name", options:{value: '${e.feedFac.name}',readonly: true }},
					{ display: "客户id", name:"e.custv.id", type: "hidden", options:{value: '${e.custv.id}'}},
					{ display: "客户", name:"e.custv.name", options:{value: '${e.custv.name}',readonly: true }},
					{ display: '调拨类型',name: 'e.allotType',newline:true,options:{value: '${e.allotType}',render: function(data){
    					if(data=='0')
    						return '调入'; 
    					else
    						return '调出';	
    				},readonly: true}},
					{ display: "调拨时间", name: "e.registDate", type: "date", validate: { required: true },options: { value: '${e.registDate}', showTime:false }},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", newline:true,name: "e.remark", type: "text",width:450,options: { value: '${e.remark}'}}
					 ]};
	  		   var oldNum=0;
  	  		   var gridOption={
  	  		  		 title: '调拨单明细',
	  	  		  	 url:'drug_allocation_dtl!loadByEntity',
	        		 parms:{ 'e.drugAllocation.id': '${e.id}' },
  	  		  	 	 submitDetailsPrefix: 'e.details',
  	  		  	     enabledEdit: true,
  	  		  	     detailsIsNotNull:true,
                     columns:[
							{ display: "明细id", name: 'id',hide:true},
							{ display: "药品id", name: 'drug.id',hide:true},
	                        { display: "药品类型", name: 'drug.drugType',hide:true,render:function(data){
		                        if(data['drug.drugType']==1)
		                        	return '疫苗';
		                        else
		                        	return '药品';
	                        }},
	                        { display: "系统编码", name: 'drug.sysCode',hide:true},
	                        { display: "药品编码", name: 'drug.code'},
							{ display: "药品名称", name: 'drug.name'},
	                        { display: "药品规格", name: 'drug.spec'},
	                        { display: "库存数量", name: 'stockQuantity'},
							{ display: "单位", name: 'drug.unit.value'},
		    				{ display: '数量', name: 'quantity', width: 150, editor: { type: 'text',options: { number: true,initSelect: true  }}}
		    				
                		],
                		onBeforeEdit: function(e) { 
		  					var rowdata = e.record;
		  					if (rowdata.quantity) {
		  						oldNum = rowdata.quantity;
			  				}else {
			  					oldNum = 0;
				  			}
				  			
		  		 		},
                  		onAfterEdit: function(e) {
							var rowdata = e.record;
							// 起始值
							if (e.column.name == 'quantity') {
			 					if (rowdata.quantity) {
			  						var num = rowdata.quantity*1;
			  						//数量不能为负
			  						if(num<=0){
	 	 		 						grid.updateCell('quantity', oldNum, rowdata);
	 	 	 	 						$.ligerDialog.warn("数值不能为非正数！") ;
	 	 	 	 						return;
	 	 		 					}
	 	 		 					/*数值不能超过库存
	 	 		 					if(num>rowdata.stockQuantity*1){
 	 	 	 		 				    grid.updateCell('quantity', oldNum, rowdata);
 	 	 	 							$.ligerDialog.warn("数值不能超过库存数！") ;
 	 	 	 							return;
	 	 	 	 		 			}*/
			  						
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
   	    	var custv = $("input[id='e.custv.id']").val();
   	    	if(!custv){
   	    		$.ligerDialog.warn('请选择客户！');
   	    		return;
   	    	}
   	    	var allotType = $("input[id='e.allotType']").val();
   	    	if(!allotType){
   	    		$.ligerDialog.warn('请选择调拨类型！');
   	    		return;
   	    	}
   	    	var feedFac = $("input[id='e.feedFac.id']").val();
   	    	if(!feedFac){
   	    		$.ligerDialog.warn('请选择饲料厂！');
   	    		return;
   	    	}
   			$.ligerDialog.open({ 
   			title:'药品搜索',
   			url: 'drug_bill_search.jsp', 
   			height: 500,
   			width: 750, 
   			onLoaded: function(param){
		        var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
		          $('input[id="e.company.id"]',documentF).attr('value', $('input[id="e.company.id"]').val()); 
		          $('input[id="e.feedFac.id"]',documentF).attr('value', $('input[id="e.feedFac.id"]').val());
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
									if (data1['drug.id'] == data['drug.id']) {
											isRepeat = true;
											return false;
										}
									});
									var subUnitValue='';
									if (!isRepeat) {
									    if(data.drug.subUnit!=null && data.drug.subUnit!=''){
										    if(data.drug.subUnit.value!=null && data.drug.subUnit.value!='')
										    	subUnitValue=data.drug.subUnit.value;
									    }	
		           						grid.addRow({
		           			                'drug.id': data.drug.id,
		           						    'drug.drugType': data.drug.drugType,
		           						    'drug.sysCode':data.drug.sysCode,
		           						    'drug.code':data.drug.code,
		           			                'drug.name': data.drug.name,
		           			                'drug.packForm':data.drug.packForm,
		           			                'drug.spec': data.drug.spec,
		           			                'stockQuantity':data.quantity,
		           			                'drug.unit.value':data.drug.unit.value
		           			                
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

