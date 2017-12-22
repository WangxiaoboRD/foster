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
	  		 	 url: "farmer_sale!modify",	// 此处也是必须的
	  			 labelWidth: 98, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
				    { display: "代养农户", name:"e.farmer.id",type: "hidden", options: { value: '${e.farmer.id}'}},
				    { display: "养殖批次", name:"e.batch.id",type: "hidden",options: { value: '${e.batch.id}'}},
					{ display: "销售单号", name:"e.id",type: "hidden", validate: { required: true }, options: { value: '${e.id}', readonly: true }},
					
					{ display: "代养农户", name:"e.farmer.name",type: "text", validate: { required: true },group: "销售单", groupicon: groupicon,options: {value: '${e.farmer.name}',readonly:true}},
					{ display: "养殖批次", name:"e.batch.batchNumber",type: "text", validate: { required: true },options: { value: '${e.batch.batchNumber}', readonly: true }},
					{ display: "销售日期", name: "e.registDate", type: "date",validate: {required:true},options: {value:'${e.registDate}',showTime:true,format:"yyyy-MM-dd"}},
					{ display: "出栏单", name:"e.outPigsty.id",type:"select",newline: true,comboboxName: 'outPigsty',options:{
					    url:'out_pigsty!loadEntity',
					    valueField: 'id', 
				        textField: 'id',
				        keySupport:true,
						selectBoxHeight: 100,
						triggerToLoad: true,
						autocomplete: true,
						initValue: '${e.outPigsty.id}',
  					    initText: '${e.outPigsty.id}', 
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.batch.id', $("input[id='e.batch.id']").val());
		                },
		                onSelected : function(value, oldValue) {
			                if(value){
				                //获取出栏单对应的状态
				                $.ligerui.ligerAjax({
									type: "POST",
									async:  false,
									url: "out_pigsty!loadById",
									data: {id:value},
									dataType: "json",
									success: function(result, status){
										if(result != "") {
											if("Q"==result.e.saleType)
												$("input[id='v']").val("正常出栏");
											else if("E"==result.e.saleType)
												$("input[id='v']").val("淘汰出栏");
											$("input[id='head']").val(result.e.quantity);
										}
									},
									error: function(XMLHttpRequest,status){
										$.ligerDialog.error('操作出现异常');
									},
									complete:function(){}
							   	})
			                }
		                }
					}},
					{ display: "出栏类型", name: "v", type: "text",options: {readonly: true,value:'${e.outPigsty.saleType}',render:function(data){
						if(data=='Q')
							return '正常出栏';
						else if(data=='E')
							return '淘汰出栏';
					}}},
					{ display: "出栏头数", name: "head", type: "text",options: {readonly: true,value:'${e.outPigsty.quantity}'}},
					{ display: "销售头数", name: "e.totalQuan", newline: true,type: "text",options:{value:'${e.totalQuan}',readonly:true}},
					{ display: "销售重量(KG)", name: "e.totalWeight", type: "text",options: {value:'${e.totalWeight}',readonly: true}},
					{ display: "销售金额(元)", name: "e.totalMoney", type: "text",options: {value:'${e.totalMoney}',readonly: true}},

					{display: "销售商", name:"e.buyer.id",group: "公司销售信息", groupicon: groupicon,type:"select",comboboxName: 'customer',newline: true,options:{
						url:'cust_vender!loadByCustomer',
					    valueField: 'id', 
				        textField: 'name',
				        keySupport:true,
				        autocomplete: true,
						selectBoxHeight: 100,
						initValue: '${e.buyer.id}',
  					    initText: '${e.buyer.name}', 
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.custVenderType.dcode', 'PIGDEALER');
		                	g.setParm('e.company.id', $("input[id='e.farmer.id']").val());
		                }
					}},
					{display: "销售头数(头)", name: "e.quantity",type: "digits",validate: { required: true },options:{initSelect: true,digits:true,value:'${e.quantity}'}},
					{display: "销售重量(KG)", name: "e.weight", type: "number",validate: { required: true },options:{initSelect: true,number:true,value:'${e.weight}'}},
					{display: "销售金额(元)", name: "e.amount", type: "number",validate: { required: true },newline: true,options:{initSelect: true,number:true,value:'${e.amount}'}},
					{display: "运输费用(元)", name: "e.tcost", type: "number",validate: { required: true },options:{initSelect: true,number:true,value:'${e.tcost}'}}
			   ]};
	  		   var oldQuantity=0;
               var oldWeight=0;
               var oldActualPrice=0;
               var oldAmount=0;
  	  		   var gridOption={
  	  		  		 title: '销售单明细',
	  	  		  	 url:'farmer_sale_dtl!loadByEntity',
	        		 parms:{ 'e.farmerSale.id': '${e.id}'},
  	  		  	 	 submitDetailsPrefix: 'e.details',
  	  		  	     enabledEdit: true,
  	  		  	     detailsIsNotNull:true,
                     columns:[
						{ display: "编号", name: 'id',hide:true},
						{ display: "销售级别", name: 'pigLevel.dcode',textField: 'pigLevel.value'},
						{ display: "合同定价", name: 'contPrice'},
						{ display: "交易价", name: 'actualPrice',editor: { type: 'text',options: { number: true,initSelect: true }}},
						{ display: "销售头数", name: 'quantity',editor: { type: 'text',options: {digits: true,initSelect: true}}},
						{ display: "销售重量(KG)", name: 'weight',editor: { type: 'text',options: { number: true,initSelect: true }}},
						{ display: "销售金额(元)", name: 'amount',editor: { type: 'text',options: { number: true,initSelect: true }}}
                	  ],
                      toolbar: {
                         items: [
        		                { text: '添加', icon: 'add', click: addClick },
        		                { line: true },	                
        		                { text: '删除', icon:'delete', click: deleteClick }
        	                ]
        	          },
        	          onBeforeEdit: function(e) { 
   	  					var rowdata = e.record;
   	  					if (rowdata.quantity) 
   	  						oldQuantity = rowdata.quantity;
   	  					else
   	  						oldQuantity = 0;	
   	  					if (rowdata.weight) 
   	  						oldWeight = rowdata.weight;
   	  					else
   	  						oldWeight = 0;
   	  					if (rowdata.actualPrice) 
   	  						oldActualPrice = rowdata.actualPrice;
   	  					else
   	  						oldActualPrice = 0;
   	  					if (rowdata.amount) 
   	  						oldAmount = rowdata.amount;
   	  					else
   	  						oldAmount = 0;
   	  		 		},
        	            onAfterEdit: function(e) {
        	            	var rowdata = e.record;
        	            	//单价发生变化
        	            	if (e.column.name == 'actualPrice'){
        	            		if (rowdata.actualPrice) {
        	            			var actualPrice = rowdata.actualPrice*1;
  		  						if(actualPrice<0)
  		  							actualPrice = oldActualPrice;
  		  						grid.updateCell('actualPrice',actualPrice, rowdata);
  								//更新金额
  								//获取原始金额
  								var oldv = 0;
  								if(rowdata.amount)
  									oldv = rowdata.amount;
  								//获取重量
  								var weight = 0;
  								if(rowdata.weight)
  									weight = rowdata.weight*1;
  								//金额
  								var v = actualPrice*weight;
  								grid.updateCell('amount',v.toFixed(2), rowdata);
  		  						//跟新总金额
  		  						var totalMoney = $("input[id='e.totalMoney']").val();
  		  						if(!totalMoney)
  		  							totalMoney = 0;
  		  						$("input[id='e.totalMoney']").val((totalMoney-oldv+v).toFixed(2));
        	            		}else{
        	            			actualPrice = oldActualPrice;
  		  						grid.updateCell('actualPrice',actualPrice, rowdata);
            	            	}
        	            	}
        	            	//重量发生变化
        	            	if (e.column.name == 'weight'){
        	            		if (rowdata.weight) {
        	            			var weight = rowdata.weight*1;
  		  						if(weight<0)
  		  							weight = oldWeight;
  		  						grid.updateCell('weight',weight, rowdata);
  		  						//更新总重量
  								var totalWeight = $("input[id='e.totalWeight']").val();
  		  						if(!totalWeight)
  		  							totalWeight = 0;
  		  						$("input[id='e.totalWeight']").val((totalWeight*1-oldWeight*1+weight*1).toFixed(2));
  		  						
  								var saleWeight = $("input[id='e.weight']").val();
  								if(!saleWeight)
  									saleWeight = 0;
  								$("input[id='e.weight']").val((saleWeight*1-oldWeight*1+weight*1).toFixed(2));
  		  						
  								//更新金额
  								//获取原始金额
  								var oldv = 0;
  								if(rowdata.amount)
  									oldv = rowdata.amount;
  								//获取单价
  								var actualPrice = 0;
  								if(rowdata.actualPrice)
  									actualPrice = rowdata.actualPrice;
  								//如果总金额不为空，计算单价，如果总金额为空或0，计算总金额
  								if(oldv != 0){
  									if(weight != 0){
  										var v = oldv/weight;
  										grid.updateCell('actualPrice',v.toFixed(2), rowdata);
  									}
  								}else{
  									//金额
  									var v = actualPrice*weight;
  									grid.updateCell('amount',v.toFixed(2), rowdata);
  								}
  		  						//跟新总金额
  		  						var totalMoney = $("input[id='e.totalMoney']").val();
  		  						if(!totalMoney)
  		  							totalMoney = 0;
  		  						$("input[id='e.totalMoney']").val((totalMoney*1-oldv*1+v*1).toFixed(2));
        	            		}else{
        	            			weight = oldWeight;
        	            			grid.updateCell('weight',weight, rowdata);
            	                }
        	            	}

        	            	//金额发生变化
        	            	if (e.column.name == 'amount'){
        	            		if (rowdata.amount) {
        	            			var amount = rowdata.amount*1;
  		  						if(amount<0)
  		  							amount = oldAmount;
  		  						grid.updateCell('amount',amount, rowdata);
  								//更新交易价
  								//获取重量
  								var weight = 0;
  								if(rowdata.weight){
  									weight = rowdata.weight*1;
  									//交易价
  									if(weight != 0){
  										var v = amount/weight;
  										grid.updateCell('actualPrice',v.toFixed(2), rowdata);
  									}
  								}
  		  						//跟新总金额
  		  						var totalMoney = $("input[id='e.totalMoney']").val();
  		  						if(!totalMoney)
  		  							totalMoney = 0;
  		  						$("input[id='e.totalMoney']").val((totalMoney*1-oldAmount*1+amount*1).toFixed(2));
        	            		}
        	            	}
        	            	
        	            	//头数发生变化
        	            	if (e.column.name == 'quantity'){
        	            		if (rowdata.quantity) {
        	            			var quantity = rowdata.quantity*1;
  		  						if(quantity<0)
  		  							quantity = oldQuantity;
  		  						grid.updateCell('quantity',quantity, rowdata);
  							
  		  						//更新总头数
  		  						var totalQuan = $("input[id='e.totalQuan']").val();
  		  						if(!totalQuan)
  		  							totalQuan = 0;
  		  						$("input[id='e.totalQuan']").val((totalQuan*1-oldQuantity*1+quantity*1).toFixed(0));

  		  					    //更新公司销售总头数
  		  						var allquantity = $("input[id='e.quantity']").val();
  		  						if(!allquantity)
  		  							allquantity = 0;
  		  						$("input[id='e.quantity']").val((allquantity*1-oldQuantity*1+quantity*1).toFixed(0));
        	            		}
        	            	}
             	        }
              }
  	  		  var template=$.pap.createEFormGridEdit({form:formOption,grid:gridOption});
  		      grid=template.getGrid();
  		      // 记录删除对象id集合
		  	  template.appendOtherData = function(){
		     	  var result= {'e.tempStack.deteleIds': delIds } ;
		          return result;
		      }
          });
        //生成明细
  	     var addClick = function(){
  	    	var batch = $("input[id='e.batch.id']").val();
  	    	if(!batch){
  	    		$.ligerDialog.warn('请选择养殖批次!');
  	    		return;
  	    	}
  	    	$.ligerDialog.open({ 
   			title:'销售级别',
   			url: 'farmer_sale_set.jsp',  
   			height: 320,
   			width: 450, 
   			onLoaded: function(param){
		        var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
		        $('input[id="e.contract.id"]',documentF).attr('value',$("input[id='e.batch.id']").val());
                $('input[id="e.contract.code"]',documentF).attr('value',$("input[id='batch']").val());
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
									if (data1['pigLevel.dcode'] == data.pigLevel.dcode){
										isRepeat = true;
									}
								});
								if (!isRepeat) {
	           						grid.addRow({
		           						'pigLevel.dcode':data.pigLevel.dcode,
		           					    'pigLevel.value':data.pigLevel.value,
		           					    'contPrice':data.price,
		           					    'actualPrice':data.price
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
              var delTotalMoney=0;
              var delTotalQuan=0;
              var delTotalWeight=0;
              $(selRows).each(function(index, data){
                  if (data['quantity']) {
                 	 delTotalQuan += data['quantity']*1;
                  }
                  if (data['weight']) {
                 	 delTotalWeight += data['weight']*1;
                  }
                  if (data['amount']) {
                 	 delTotalMoney += data['amount']*1;
                  }
                  //登记删除id
                  if(data['id'])
                	  delIds += data['id']+',';
                  
              });
              var totalQuan = $("input[id='e.totalQuan']").val();
              $('input[id="e.totalQuan"]').val((totalQuan*1 - delTotalQuan).toFixed(0));
              
              var totalWeight = $("input[id='e.totalWeight']").val();
              $('input[id="e.totalWeight"]').val((totalWeight*1 - delTotalWeight).toFixed(2));
              
              var totalMoney = $("input[id='e.totalMoney']").val();
              $('input[id="e.totalMoney"]').val((totalMoney*1 - delTotalMoney).toFixed(2));

    		  //删除选中的行
              grid.deleteSelectedRow(); 
        }
	  	
    </script>
</head>
<body></body>
</html>

