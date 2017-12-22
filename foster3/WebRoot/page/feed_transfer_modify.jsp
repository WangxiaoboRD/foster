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
	  		 	 url: "feed_transfer!modifyAll",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
	  			    { display: "单据号", name: "e.id", type: "text", validate: { required: true }, options: { value: '${e.id}', readonly: true }, group: "物料转接单", groupicon: groupicon },	  		  			 
	  			    { display: "转接时间", name: "e.registDate", type: "date", validate: { required: true },options: { value: '${e.registDate}', showTime:false }},
					{ display: "养殖公司id", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
					/**{ display: "养殖公司", name:"e.company.name",  type: "text", options:{value: '${e.company.name}',readonly: true}},*/
					{ display: "转出代养户id", name:"e.outFarmer.id", type: "hidden", options:{value: '${e.outFarmer.id}'}},
					{ display: "转出代养户", name:"e.outFarmer.name", type: "text", options:{value: '${e.outFarmer.name}',readonly: true},group: "单据信息", groupicon: groupicon},
					{ display: "转出批次id", name:"e.outBatch.id", type: "hidden", options:{value: '${e.outBatch.id}'}},
					{ display: "转出批次", name:"e.outBatch.batchNumber", type: "text", options:{value: '${e.outBatch.batchNumber}',readonly: true}},
					{ display: "转入代养户", name:"e.inFarmer.id",comboboxName: 'inFarmer',type:"select",newline:true,options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        initValue: '${e.inFarmer.id}',
  					    initText: '${e.inFarmer.name}',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                },
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
				        initValue: '${e.inBatch.id}',
  					    initText: '${e.inBatch.batchNumber}',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                	g.setParm('e.farmer.id', $("input[id='e.inFarmer.id']").val());
		                	g.setParm('e.isBalance', 'N');
		                	g.setParm('e.contract.status.dcode', 'BREED,EFFECT');
		                }
					}},
					{ display: "运输公司", name:"e.transportCo",comboboxName: 'transportCo',type:"select",newline: true,options:{
					    url:'transport_co!loadByEntity',
					    valueField: 'id', 
				        textField: 'name',
				        initValue: '${e.transportCo}',
  					    initText: '${e.transportCoName}',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                },
		                onSelected: function (nv){
		                	var _driver = liger.get("driver");
		                	if(_driver)
								_driver.setValue("");
		                }
					}},
					{ display: "司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机", name:"e.driver",comboboxName: 'driver',type:"select",options:{
					    url:'driver!loadByEntity',
					    valueField: 'id', 
				        textField: 'name',
				        initValue: '${e.driver}',
  					    initText: '${e.driverName}',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						autocomplete: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                	g.setParm('e.transportCo.id', $("input[id='e.transportCo']").val());
		                }
					 }},
					 { display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注", newline:true,name: "e.remark", type: "text",width:450,options: { value: '${e.remark}'}},
					 ]};
	  		   var oldNum=0;
	  		   var oldSubNum=0;
  	  		   var gridOption={
  	  		  		 title: '转接单明细',
	  	  		  	 url:'feed_transfer_dtl!loadByEntity',
	        		 parms:{ 'e.feedTransfer.id': '${e.id}' },
  	  		  	 	 submitDetailsPrefix: 'e.details',
  	  		  	     enabledEdit: true,
  	  		  	     detailsIsNotNull:true,
                     columns:[
							{ display: "明细id", name: 'id',hide:true},
							{ display: "饲料id", name: 'feed.id',hide:true},
	                        { display: "饲料类型", name: 'feed.feedType.name'},
	                        { display: "饲料编码", name: 'feed.code'},
							{ display: "饲料名称", name: 'feed.name'},
							{ display: "包装方式", name: 'feed.packForm',render:function(data){
	                            if(data.packForm==1)
	                                return '散装';
	                            else
	                                return '袋装';
	                        }},
	                        { display: "饲料规格", name: 'feed.spec'},
	                        { display: "库存数量", name: 'stockQuantity'},
							{ display: "单位", name: 'feed.unit.value'},
							{ display: "库存副单位数量", name: 'stockSubQuantity'},
							{ display: "副单位", name: 'feed.subUnit.value'},
							{ display: '副单位重量', name: 'subQuantity', width: 150, editor: { type: 'text',options: { number: true,initSelect: true  }},render:function(data){
								if(data.subQuantity)
									return (data.subQuantity*1).toFixed(2)
							}},
		    				{ display: '重量', name: 'quantity', width: 150, editor: { type: 'text',options: { number: true,initSelect: true  }}},
		    				{ display: "换算比值", name: 'feed.ratio',hide:true}
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
	 	 		 					/*
	 	 		 					//数值不能超过库存
	 	 		 					if(num>rowdata.stockQuantity*1){
 	 	 	 		 				    grid.updateCell('quantity', oldNum, rowdata);
 	 	 	 							$.ligerDialog.warn("数值不能超过库存数！") ;
 	 	 	 							return;
	 	 	 	 		 			}
	 	 	 	 		 			*/
			  						if(rowdata["feed.ratio"]){
			  						    var ratio=rowdata["feed.ratio"]*1;
			  							grid.updateCell('subQuantity',(num/ratio).toFixed(2), rowdata);
			  						}
			 					}
		  					}
		  					if (e.column.name == 'subQuantity') {
			 					if (rowdata.subQuantity) {
			  						var num = rowdata.subQuantity*1;
			  						if(rowdata["feed.ratio"]){
				  						//数量不能为负
				  						if(num<=0){
		 	 		 						grid.updateCell('subQuantity', oldSubNum, rowdata);
		 	 	 	 						$.ligerDialog.warn("数值不能为非正数！") ;
		 	 	 	 						return;
		 	 		 					}
		 	 		 					/*
		 	 		 					//数值不能超过库存
		 	 		 					if(num>rowdata.stockSubQuantity*1){
	 	 	 	 		 				    grid.updateCell('subQuantity', oldSubNum, rowdata);
	 	 	 	 							$.ligerDialog.warn("数值不能超过库存数！") ;
	 	 	 	 							return;
		 	 	 	 		 			}
		 	 	 	 		 			*/
			  						    var ratio=rowdata["feed.ratio"]*1;
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
   	    	var inBatch = $("input[id='e.inBatch.id']").val();
   	    	if(!inBatch){
   	    		$.ligerDialog.warn('转入批次不能为空！');
   	    		return;
   	    	}
   			$.ligerDialog.open({ 
   			title:'饲料搜索',
   			url: 'feed_transfer_search.jsp', 
   			height: 500,
   			width: 750, 
   			onLoaded: function(param){
		        var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
		          /** $('input[id="e.company.id"]',documentF).attr('value', $('input[id="e.company.id"]').val()); */
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
									if (data1['feed.id'] == data['feed.id']) {
											isRepeat = true;
											return false;
										}
									});
									var subUnitValue='';
									if (!isRepeat) {
									    if(data.feed.subUnit!=null && data.feed.subUnit!=''){
										    if(data.feed.subUnit.value!=null && data.feed.subUnit.value!='')
										    	subUnitValue=data.feed.subUnit.value;
									    }	
		           						grid.addRow({
		           			                'feed.id': data.feed.id,
		           						    'feed.feedType.name': data.feed.feedType.name,
		           						    'feed.code':data.feed.code,
		           			                'feed.name': data.feed.name,
		           			                'feed.packForm':data.feed.packForm,
		           			                'feed.spec': data.feed.spec,
		           			                'feed.unit.value':data.feed.unit.value,
		           			                'stockQuantity':data.quantity,
		           			                'feed.subUnit.value':subUnitValue,
		           			                'stockSubQuantity':data.subQuantity,
		           			                'feed.ratio':data.feed.ratio,
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

