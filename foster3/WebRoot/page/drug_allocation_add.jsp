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
	  		 	 url: "drug_allocation!save",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
           		 excludeClearFields:['e.remark','e.registDate','custv','allotType'],
	  			 fields: [
					{ display: "养殖公司", name:"e.company.id", group: "药品调拨单", groupicon: groupicon,type:"select",comboboxName: 'company', validate: { required: true }, options:{
						url: 'company!loadByEntity',
					    valueField: 'id',
					    textField: 'name', 
					    autocomplete: true,
					    keySupport:true,
					    triggerToLoad: true,
					    onSelected: function (nv){
							var _custv = liger.get("custv");
							if(_custv)
								_custv.setValue("");
						} 
					}},
					{ display: "饲料厂", name:"e.feedFac.id", type:"select",comboboxName: 'feedFac', validate: { required: true }, options:{
						url: 'feed_fac!loadByEntity',
					    valueField: 'id',
					    textField: 'name', 
					    autocomplete: true,
					    keySupport:true,
					    triggerToLoad: true,
					    onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                }
					   
					}},
					{ display: "客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户", name: "e.custv.id",comboboxName: 'custv',validate: { required: true },type: "select",options:{
					    url:'cust_vender!loadByEntity',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                	g.setParm('e.custVenderType.dcode',"DRUGDEALER");
		                }
					}},
					{ display: "调拨类型",name: "e.allotType",validate: { required: true },newline: true,type: "select", options:{
						  keySupport:true,
 	                	  data: [
 	                       	{ text: '调入', id: '0' },
 	               	  		{ text: '调出', id: '1' }
 		                 ]
             		}},
					{ display: "调拨时间", name: "e.registDate", type: "date",validate: { required: true },options: { value: new Date(), showTime: false }},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", newline:true,name: "e.remark", type: "text",width:450},
               ]};
             var oldNum=0;
	  		 var gridOption={
	  		  		 title: '调拨单明细',
	  		  	 	 submitDetailsPrefix: 'e.details',
	  		  	     enabledEdit: true,
	  		  	     detailsIsNotNull:true,
                     columns:[
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
                        { display: "单位", name: 'drug.unit.value'},
                        { display: "库存数量", name: 'stockQuantity'},
	    				{ display: '数量', name: 'quantity', width: 100, editor: { type: 'text',options: { number: true,initSelect: true  }}}

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
	  		var glist=$.pap.createEFormGridSave({form:formOption,grid:gridOption});
		    grid=glist.getGrid();
        });
        //生成明细
   	    var addClick = function(){
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
   	    	if(allotType==1){
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
				 }else{
						 $.ligerDialog.open({ 
				   			title:'药品搜索',
				   			url: 'drug_in_ware_search.jsp', 
				   			height: 500,
				   			width: 750, 
				   			onLoaded: function(param){
						        var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
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
													if (data1['drug.id'] == data['id']) {
															isRepeat = true;
															return false;
														}
													});
													var subUnitValue='';
													if (!isRepeat) {
													    if(data.subUnit!=null && data.subUnit!=''){
														    if(data.subUnit.value!=null && data.subUnit.value!='')
														    	subUnitValue=data.subUnit.value;
													    }	
						           						grid.addRow({
						           						    'drug.id': data.id,
						           						    'drug.drugType': data.drugType,
						           						    'drug.sysCode':data.sysCode,
						           						    'drug.code':data.code,
						           			                'drug.name': data.name,
						           			                'drug.spec': data.spec,
						           			                'drug.unit.value':data.unit.value,
						           			                'drug.subUnit.value':subUnitValue,
						           			                'drug.ratio':data.ratio
						           			                
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

