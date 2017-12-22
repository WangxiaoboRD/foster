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
	  		 	 url: "feed_bill!save",	// 此处也是必须的
	  			 labelWidth: 85, 
	  			 inputWidth: 170,
           		 space:20,
           		 excludeClearFields:['e.remark','e.registDate','farmer','batch'],
	  			 fields: [
					{ display: "登记时间", name: "e.registDate", type: "date",group: "饲料耗用单", groupicon: groupicon,validate: { required: true },options: { value: new Date(), showTime: false }},
					{ display: "代&nbsp;养&nbsp;户", name:"e.farmer.id",newline:true,comboboxName: 'farmer',type:"select",validate: { required: true },options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,

						autocomplete: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.company.id']").val());
		                },

		                onSelected: function (nv){
					        var _batch = liger.get("batch");
							if(_batch)
								_batch.setValue("");
					    }
					}},
					{ display: "批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", name: "e.batch.id",comboboxName: 'batch', type: "select",options:{
					    url:'batch!loadByEntity',
					    valueField: 'id', 
				        textField: 'batchNumber',
				        keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
		                	g.setParm('e.isBalance', 'N');
		                	g.setParm('e.contract.status.dcode', 'BREED');
		                }
					}},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注",newline:true, name: "e.remark", type: "text",width:450}
               ]};
             var oldNum=0;
             var oldSubNum=0;
	  		 var gridOption={
	  		  		 title: '耗料单明细',
	  		  	 	 submitDetailsPrefix: 'e.details',
	  		  	     enabledEdit: true,
	  		  	     detailsIsNotNull:true,
                     columns:[
                        { display: "饲料id", name: 'feed.id',hide:true},
                        { display: "饲料类型", name: 'feed.feedType.name'},
                        { display: "饲料编码", name: 'feed.code'},
						{ display: "饲料名称", name: 'feed.name'},
						{ display: "包装方式", name: 'feed.packForm',render:function(data){
                            if(data["feed.packForm"]=='1')
                                return '散装';
                            else
                                return '袋装';
                        }},
                        { display: "饲料规格", name: 'feed.spec'},
                        { display: "库存数量", name: 'stockQuantity'},
						{ display: "单位", name: 'feed.unit.value'},
						{ display: "库存副单位数量", name: 'stockSubQuantity'},
						{ display: "副单位", name: 'feed.subUnit.value'},
						{ display: '副单位数量', name: 'subQuantity', width: 150, editor: { type: 'text',options: { number: true,initSelect: true  }},render:function(data){
							if(data.subQuantity)
								return (data.subQuantity*1).toFixed(2)
						}},
	    				{ display: '重量（KG）', name: 'quantity', width: 150, editor: { type: 'text',options: { number: true,initSelect: true  }}},
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
		  						
 	 		 					//数值不能超过库存
 	 		 					if(num>rowdata.stockQuantity*1){
 	 	 		 				    grid.updateCell('quantity', oldNum, rowdata);
 	 	 							$.ligerDialog.warn("数值不能超过库存数！") ;
 	 	 							return;
 	 	 	 		 			}
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
	 	 		 					//数值不能超过库存
	 	 		 					if(num>rowdata.stockSubQuantity*1){
	 	 	 		 				    grid.updateCell('subQuantity', oldSubNum, rowdata);
	 	 	 							$.ligerDialog.warn("数值不能超过库存数！") ;
	 	 	 							return;
	 	 	 	 		 			}
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
	  		var glist=$.pap.createEFormGridSave({form:formOption,grid:gridOption});
		    grid=glist.getGrid();
        });
        //生成明细
   	    var addClick = function(){
   	    	var farmer = $("input[id='e.farmer.id']").val();
   	    	if(!farmer){
   	    		$.ligerDialog.warn('请选择代养户！');
   	    		return;
   	    	}
   	    	var batch = $("input[id='e.batch.id']").val();
   	    	if(!batch){
   	    		$.ligerDialog.warn('请选择批次！');
   	    		return;
   	    	}
   			$.ligerDialog.open({ 
   			title:'饲料搜索',
   			url: 'feed_bill_search.jsp', 
   			height: 500,
   			width: 750, 
   			onLoaded: function(param){
		        var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
		           $('input[id="e.farmer.id"]',documentF).attr('value', $('input[id="e.farmer.id"]').val());
		           $('input[id="e.batch.id"]',documentF).attr('value', $('input[id="e.batch.id"]').val());
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
		           			                'stockQuantity':data.quantity,
		           			                'feed.unit.value':data.feed.unit.value,
		           			                'stockSubQuantity':data.subQuantity,
		           			                'feed.subUnit.value':subUnitValue,
		           			                'feed.ratio':data.feed.ratio
		           			                
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

