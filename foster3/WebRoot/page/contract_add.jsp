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
	    var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
		var toolbar = null;
		var form;
		var feed,pig;
        $(function(){
        	 toolbar = $("#toolbar").ligerToolBar({
             	items: [
               	   { text: '保存', icon: 'save', click: function(){
					 var formValue = $('#form0').formToArray();
					 var feedValue = feed.getEditGridDetails();
					 var pigValue = pig.getEditGridDetails();
					
					 var callBackDetails = {};
					 $.each(formValue,function(i,item){
					     if(item.name){
					        callBackDetails[item.name ] = item.value ||"";
					     }
					 });
					 var result =  $.extend(callBackDetails,feedValue,pigValue);
                        //执行ajax保存
					 $.ligerui.ligerAjax({
							url:'contract!save',
							dataType: "text",
							data: result,
							beforeSend: function(){
							 	if ($("#loading")) {
									$("#loading").show();
								}
								if ($.ligerDialog) {
									$.ligerDialog.waitting('系统正在处理，请稍候......'); 
								}
							},
							success:function(result,textStatus){
								if(result != null && result !=""){
									tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '保存成功！' });
									window.setTimeout(function(){ 
										tips.close();
										$.pap.removeTabItem();
									} ,2000);
								}
							},
							complete: function() {
								if( $.ligerDialog ){
									$.ligerDialog.closeWaitting();
								}
								if ($("#loading")) {
									$("#loading").hide();
								}
							}
						});	
               	    }}
                ]
            });
        	form =$("#form0").ligerForm({
	  			 labelWidth: 70, 
	  			 inputWidth: 170,
           		 space:20,
	  			 fields: [
					{display: "代养农户", name:"e.farmer.id",comboboxName: 'farmer',group: "养殖合同", groupicon: groupicon,validate: { required: true },type:"select",options:{
					    url:'farmer!loadByPinYin',
					    valueField: 'id', 
				        textField: 'name',
				        autocomplete: true,
		                keySupport:true,
						selectBoxHeight: 200,
						triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.stage.dcode', 'COOPERATION');
		                },
						onSelected: function (nv){
							var _feedFac = liger.get("feedFac");
							if(_feedFac)
								_feedFac.setValue("");
							
							var _developMan = liger.get("developMan");
							if(_developMan)
								_developMan.setValue("");
							
							var _technician = liger.get("technician");
							if(_technician)
								_technician.setValue("");
	            		}
					}},
					{display: "合同编码",name: "e.code", type: "text",validate: { required: true }},
					{display: "签订日期",name: "e.registDate", type: "date", validate: { required: true },options: {value: new Date(), format : "yyyy-MM-dd"}},
					{display: "饲料工厂", name:"e.feedFac.id",comboboxName:'feedFac',type:"select",group: "合同信息", groupicon: groupicon,validate: { required: true }, options:{
						url: 'feed_fac!loadByFeedFac',
		                valueField: 'id',
		                textField: 'name', 
		                selectBoxHeight: 150,
		                keySupport:true,
		                triggerToLoad: true,
						onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.farmer.id']").val());
		                }
					}},
					{ display: "开发人员", name: "e.developMan.id",comboboxName: 'developMan',validate: { required: true }, type: "select",options:{
					     url:'develop_man!loadByDevelopMan',
					     valueField: 'id', //关键项
					   	 textField: 'name',
					   	 selectBoxHeight: 150,
						 keySupport:true,
						 triggerToLoad: true,
					     onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.farmer.id']").val());
				         }
					}},
					{ display: "技术员", name: "e.technician.id",comboboxName: 'technician',validate: { required: true }, type: "select",options:{
					     url:'technician!loadByTech',
					     valueField: 'id', //关键项
					   	 textField: 'name',
						 keySupport:true,
						 selectBoxHeight: 150,
						 triggerToLoad: true,
					     onBeforeOpen: function() {
		                	var g = this;
		                	g.setParm('e.company.id', $("input[id='e.farmer.id']").val());
				         }
					}},
					{ display: "养殖品种", name:"e.variety.id",newline: true,comboboxName:'variety',type:"select",validate: { required: true }, options:{
						url: 'variety!loadByEntity',
		                valueField: 'id',
		                textField: 'name', 
		                selectBoxHeight: 180,
		                autocomplete: true,
		                keySupport:true
					}},
					{ display: "养殖数量",name: "e.pigletQuan", type: "digits",validate: { required: true },options:{initSelect: true,digits:true,value:"0"}},
					{ display: "猪苗单价", name: "e.pigletPrice", type: "number",validate: { required: true },options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "猪苗标重", name: "e.standPigletWeight", type: "number",newline: true,validate: { required: true },options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "出栏标重",name: "e.standSaleWeight", type: "number",validate: { required: true },options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "销售市价", name: "e.marketPrice", type: "number",validate: { required: true },options:{initSelect: true,number:true,value:"0.00"}},

					{ display: "允许料肉比偏差值",name: "e.allowDiff",labelWidth: 120, group: "料肉比参数", groupicon: groupicon, type: "number",validate: { required: true },options:{initSelect: true,number:true,value:'0.00'}},
					{ display: "超出允许偏差值扣款价(元/吨)",labelWidth: 180, name: "e.firstPrice", type: "number",validate: { required: true },options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "料肉比一级偏差值",labelWidth: 120, name: "e.firstDiff", type: "number",validate: { required: true },newline: true,options:{initSelect: true,number:true,value:"0.00"}},
					{ display: "超出一级偏差值扣款价(元/吨)",labelWidth: 180,name: "e.overFirstPrice", type: "number",validate: { required: true },options:{initSelect: true,number:true,value:"0.00"}}
               ]});
        	   feed=$("#form1").ligerGrid({
	  		  	 	 submitDetailsPrefix: 'e.feedPriced',
	  		  	     detailsIsNotNull:true,
	  		  	     enabledEdit: true,
                     columns:[
						{ display: "饲料编码", name: 'feed.id',hide:true},
						{ display: "饲料名称", name: 'feed.name'},
						{ display: "饲料规格", name: 'feed.spec'},
						{ display: "饲料包装", name: 'feed.packForm',render: function(data){
		    				var text = data['feed.packForm'];
	    					switch(text){
	    						case 1: text = "散装"; break;
	    						case 2: text = "袋装"; break;
	    					}
	    					return text;
	    				}},
						{ display: "饲料单位", name: 'feed.unit.value'},
						{ display: "饲料类型", name: 'feed.feedType.name'},
						{ display: "单价(元/kg)", name: 'price',editor: { type: 'text',options: { number: true,initSelect: true  }}}
				    ],
				    usePager: false, 
        			checkbox:true,
        			height:'99%',
                    rownumbers:true,
	         		enabledEdit: true,
                    toolbar: {
                       items: [
      		                { text: '加载饲料', icon: 'add', click:function(){
      		                	var farm = $("input[id='e.farmer.id']").val();
	      		      			if(!farm){
	      		      				$.ligerDialog.warn('请选择代养农户');
	      		      				return ;
	      		      			}
      		            		$.ligerDialog.open({ 
	      		              		title:'加载饲料',
	      		              		url: 'feed_set.jsp',
	      		              		height: 500,
	      		              		width: 700, 
	      		              		onLoaded: function(param){
	      		           		    	var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
	      		           		    	$('input[id="e.company.id"]',documentF).attr('value', $('input[id="e.farmer.id"]').val()); 
	      		           		    },
	      		              		buttons: [ 
	      		              			{ text: '选择', onclick:  function (item, dialog) { 
	      		              				var selRows = dialog.frame.f_select();
	      		              				if (selRows.length < 1) {
	      		              					$.ligerDialog.warn('请选择记录进行操作');
	      		           	           		}else {
	      		           						$(selRows).each(function(index, data){
	      		           							var isRepeat = false;
	      		      								$(feed.rows).each(function(index, data1){
	      		      									if (data['id'] == data1['feed.id']) {
	      		      										isRepeat = true;
	      		      										return false;
	      		      									}
	      		      								});
	      		      								if (!isRepeat) {
	      		      									feed.addRow({
	      		      	           			                'feed.id': data.id,
	      		      	           			             	'feed.name': data.name,
	      		      	           			             	'feed.spec': data.spec,
	      		      			           				    'feed.packForm':data.packForm,
	      		      			           				    'feed.unit.value':data['unit.value'],
		      		      			           				'feed.feedType.name':data['feedType.name'],
	      		      	           							'price':'0'
	      		      	           			            });
	      		      								}
	      		           	           			});
	      		                 				dialog.close();
	      		           	           		}
	      		             				}}, 
	      		         					{ text: '取消', onclick:  function (item, dialog) { dialog.close(); }}
	      		         		     ] 
      		         			});

          		            }},
      		                { line: true },	                
      		                { text: '删除定价', icon:'delete', click: function(){
      		           	 		var selRows = feed.getSelecteds();
	      		               	if (selRows.length == 0) {
	      		            		 $.ligerDialog.warn('请选择要删除的记录！');
	      		     			     return;
	      		               	}
	      		     		    //删除选中的行
	      		               	feed.deleteSelectedRow();
	      		        	}}
      	                 ]
      	            }
             });
       		 pig=$("#form2").ligerGrid({
       			 url:'contract_pig_priced!loadDetails',
		  	 	 submitDetailsPrefix: 'e.pigPriced',
		  	     enabledEdit: true,
		  	     detailsIsNotNull:true,
	             columns:[
					{ display: "销售级别编码", name: 'pigLevel.dcode',hide:true},
					{ display: "销售级别名称", name: 'pigLevel.value'},
					{ display: "销售级别定价(元)", name: 'price',editor: { type: 'text',options: { initSelect: true }}}
			   ],
			   usePager: false, 
	   		   checkbox:true,
	   		   height:'99%',
	           rownumbers:true,
	       	   enabledEdit: true
	    	});
       		$("#tab").ligerTab();
        	$.each(toolbar.toolButtons, function(i, item){
            	if (i == 1) {
            		this.set('visible', false);
               	}
    		});
    		
    		//分组 收缩/展开  
    	    $(".togglebtn").live('click', function (){
    	    	feed.setHeight(feed.options.height);
    	    	pig.setHeight(pig.options.height);
    	    });
        });
    </script>
</head>
<body>
	<div id="toolbar"></div>
	<div id="form">
		<form id="form0"></form>
	</div>
	<div id="tab">
		<div id="tab1" title="饲料定价">
			<form id="form1"></form>
		</div>
		<div id="tab2" title="销售定价" >
			<form id="form2"></form>
		</div>
	</div>
	<div id="loading" class="l-sumbit-loading" style="display: none;"></div>
</body>
</html>

