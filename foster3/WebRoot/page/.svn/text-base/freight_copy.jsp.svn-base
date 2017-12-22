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
	<script src="../ligerUI/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/ligerui.all.js"></script>
	<script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript" ></script> 
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
    <script type="text/javascript">

    	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
    	var toolbar = null;
    	var gridarea;
    	 // 获得当前页面对象
		 //var currentPage = $.pap.getOpenPage(window);
		 //var currentParam = currentPage.getParam("param");
        $(function () {
            toolbar = $("#toolbar").ligerToolBar({
            	items: [
                	 { text: '保存', icon: 'save', click: function(){
						 var formValue = $('#form0').formToArray();
						 var areavalue = gridarea.getEditGridDetails();
						 
						 var callBackDetails = {};
						 $.each(formValue,function(i,item){
						     if(item.name){
						        callBackDetails[item.name ] = item.value ||"";
						     }
						 });
						 var result =  $.extend(callBackDetails,areavalue);
                         //执行ajax保存
						 $.ligerui.ligerAjax({
								url:'freight!save',
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
            $("#form0").ligerForm({
                fields: [
					{ display: "养殖公司编码", name:"e.company.id", type: "hidden", options:{value: '${e.company.id}'}},
					{ display: "养殖公司", name:"e.company.name",  type: "text", options:{value: '${e.company.name}',readonly: true},group: "运费定价单", groupicon: groupicon},
					
					{ display: "执行时间", name: "e.registDate", validate:{ required: true }, type: "date",options:{format : "yyyy-MM-dd",showTime: true,value:"${e.registDate}"}},
					{ display: "备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注",newline: true, name: "e.remark", type: "text",width:490,options:{value:"${e.remark}"}}
					
                 ]
            });
           
            gridarea = $("#form1").ligerGrid({
  				 detailsIsNotNull:true,
    			 url:"freight_dtl!loadForCopy",
    			 parms:{"e.freight.id":"${e.id}"},
                 submitDetailsPrefix: 'e.details',
                 columns:[
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
	                 		{ display: "原始袋装料运费", name: 'oldPackagePrice'},
	                 		{ display: '袋装料运费单价(元/吨)', name: 'packagePrice', editor: { type: 'text', options: { number: true,initSelect: true  }}},
	                 		{ display: "原始散装料运费", name: 'oldBulkPrice'},
	                 		{ display: '散装料运费单价(元/吨)', name: 'bulkPrice', editor: { type: 'text', options: { number: true,initSelect: true  }}}
		    				
         			 ],
         			 usePager: false, 
         			 checkbox:true,
         			 height:'99%',
                     rownumbers:true,
	         		 enabledEdit: true,
                     toolbar: {
 	                 items: [
 	    		        { text: '添加', icon:'add', click: function(){
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
 	    										$(gridarea.rows).each(function(index, data1){
 	    											
 	    											});
 	    											if (!isRepeat) {
 	    												gridarea.addRow({
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
 	 	    		        
 	 	    		    }},
 	    		        { line: true },
 	    		        { text: '删除', icon:'delete', click: function(){
 	    		        	  var selRows = gridarea.getSelecteds();
	 	 	    	          if (selRows.length == 0) {
	 	 	    	          		$.ligerDialog.error('请选择要删除的记录！');
	 	 	    				 	return;
	 	 	    	          }
	 	 	    	          //$(selRows).each(function(index, data){
	 	 	    	          //});
	 	 	    			  //删除选中的行
	 	 	    	          gridarea.deleteSelectedRow(); 	
 	 	    		     }}
 	                 ]}
            });
           
        	$("#tab").ligerTab();
        	$.each(toolbar.toolButtons, function(i, item){
            	if (i == 1) {
	        		this.set('visible', false);
               	}
			});
			
			//分组 收缩/展开  
		    $(".togglebtn").live('click', function (){
		    	gridarea.setHeight(gridarea.options.height);
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
		<div id="tab1" title="运费定价单">
			<form id="form1"></form>
		</div>
		
	</div>
	<div id="loading" class="l-sumbit-loading" style="display: none;"></div>
</body>
</html>
