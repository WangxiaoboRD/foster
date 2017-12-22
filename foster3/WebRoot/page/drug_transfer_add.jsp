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
	  		 	 url: "drug_transfer!save",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
           		 excludeClearFields:['e.remark','e.registDate','outBatch','outFarmer','inFarmer','inBatch'],
	  			 fields: [
					{ display: "转接时间", name: "e.registDate", group: "药品转接单", groupicon: groupicon,type: "date",validate: { required: true },options: { value: new Date(), showTime: false }},
					{ display: "转出代养户", name:"e.outFarmer.id",newline:true,comboboxName: 'outFarmer',type:"select",options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
					    onSelected: function (nv){
					        var _batch = liger.get("outBatch");
							if(_batch)
								_batch.setValue("");
					    }
					}},
					{ display: "转出批次", name: "e.outBatch.id",comboboxName: 'outBatch', type: "select",options:{
					    url:'batch!loadByEntity',
					    valueField: 'id', 
				        textField: 'batchNumber',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.farmer.id', $("input[id='e.outFarmer.id']").val());
		                	g.setParm('e.isBalance', 'N');
		                	g.setParm('e.contract.status.dcode', 'BREED,EFFECT');
		                }
					}},
					{ display: "转入代养户", name:"e.inFarmer.id",comboboxName: 'inFarmer',type:"select",newline:true,options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
					    onSelected: function (nv){
					        var _batch = liger.get("inBatch");
							if(_batch)
								_batch.setValue("");
					    }
					}},
					{ display: "转入批次", name: "e.inBatch.id",comboboxName: 'inBatch', type: "select",options:{
					    url:'batch!loadByEntity',
					    valueField: 'id', 
				        textField: 'batchNumber',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.farmer.id', $("input[id='e.inFarmer.id']").val());
		                	g.setParm('e.isBalance', 'N');
		                	g.setParm('e.contract.status.dcode', 'BREED,EFFECT');
		                }
					}},
					{ display: "备注",newline:true, name: "e.remark", type: "text",width:450},
               ]};
             var oldNum=0;
             var oldSubNum=0;
	  		 var gridOption={
	  		  		 title: '转接单明细',
	  		  	 	 submitDetailsPrefix: 'e.details',
	  		  	     enabledEdit: true,
	  		  	     detailsIsNotNull:true,
                     columns:[
                        { display: "药品id", name: 'drug.id',hide:true},
                        { display: "药品类型", name: 'drug.drugType',render:function(data){
	                        if(data['drug.drugType']==1)
	                        	return '疫苗';
	                        else
	                        	return '药品';
                        }},
                        { display: "系统编码", name: 'drug.sysCode'},
                        { display: "药品编码", name: 'drug.code'},
						{ display: "药品名称", name: 'drug.name'},
                        { display: "药品规格", name: 'drug.spec'},
                        { display: "库存数量", name: 'stockQuantity'},
						{ display: "单位", name: 'drug.unit.value'},
						{ display: "库存副单位数量", name: 'stockSubQuantity'},
						{ display: "副单位", name: 'drug.subUnit.value'},
						{ display: '副单位数量', name: 'subQuantity', width: 100, editor: { type: 'text',options: { number: true,initSelect: true  }},render:function(data){
							if(data.subQuantity)
								return (data.subQuantity*1).toFixed(2)
						}},
	    				{ display: '数量', name: 'quantity', width: 100, editor: { type: 'text',options: { number: true,initSelect: true  }}},
	    				{ display: "换算比值", name: 'drug.ratio',hide:true}

              		],
              		onBeforeEdit: function(e) { 
	  					var rowdata = e.record;
	  					if (rowdata.quantity) {
	  						oldNum = rowdata.quantity;
		  				}else {
		  					oldNum = 0;
			  			}
			  			if (rowdata.subQuantity) {
	  						oldSubNum = rowdata.subQuantity;
		  				}else {
		  					oldSubNum = 0;
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
 	 		 					//数值不能超过库存
 	 		 					if(num>rowdata.stockQuantity*1){
 	 	 		 				    grid.updateCell('quantity', oldNum, rowdata);
 	 	 							$.ligerDialog.warn("数值不能超过库存数！") ;
 	 	 							return;
 	 	 	 		 			}
		  						if(rowdata["drug.ratio"]){
		  						    var ratio=rowdata["feed.ratio"]*1;
		  							grid.updateCell('subQuantity',(num/ratio).toFixed(2), rowdata);
		  						}
		 					}
	  					}
	  					if (e.column.name == 'subQuantity') {
		 					if (rowdata.subQuantity) {
		  						var num = rowdata.subQuantity*1;
		  						if(rowdata["drug.ratio"]){
			  						//数量不能为负
			  						if(num<=0){
	 	 		 						grid.updateCell('subQuantity', oldSubNum, rowdata);
	 	 	 	 						$.ligerDialog.warn("数值不能为非正数！") ;
	 	 	 	 						return;
	 	 		 					}
	 	 		 					//数值不能超过库存
	 	 		 					if(num>rowdata.stockSubQuantity*1){
	 	 	 		 				    grid.updateCell('subQuantity', oldSubNum, rowdata);
	 	 	 							$.ligerDialog.warn("数值不能超过库存数！") ;
	 	 	 							return;
	 	 	 	 		 			}
		  						    var ratio=rowdata["drug.ratio"]*1;
		  							grid.updateCell('quantity',(num*ratio).toFixed(2), rowdata);
		  						}else{
		  							grid.updateCell('subQuantity','', rowdata);
		  							$.ligerDialog.warn('请先设置副单位！');
		  							return;
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
   	    	
   	    	var outBatch = $("input[id='e.outBatch.id']").val();
   	    	if(!outBatch){
   	    		$.ligerDialog.warn('转出批次不能为空！');
   	    		return;
   	    	}
   	    	var inBatch = $("input[id='e.inBatch.id']").val();
   	    	if(!inBatch){
   	    		$.ligerDialog.warn('转入批次不能为空！');
   	    		return;
   	    	}
   			$.ligerDialog.open({ 
   			title:'药品搜索',
   			url: 'drug_transfer_search.jsp', 
   			height: 530,
   			width: 750, 
   			onLoaded: function(param){
		        var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
		        $('input[id="e.batch.id"]',documentF).attr('value', $('input[id="e.outBatch.id"]').val()); 
		           
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
		           			                'drug.unit.value':data.drug.unit.value,
		           			                'stockSubQuantity':data.subQuantity,
		           			                'drug.subUnit.value':subUnitValue,
		           			                'drug.ratio':data.drug.ratio
		           			                
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
