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
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
    <script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript" ></script>       
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
    <script type="text/javascript">
    var form;
	var feed,pig;
	var toolbar = null;
	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
    $(function(){
          var currentPage = $.pap.getOpenPage(window);
  	  	  var data = currentPage.getParam("params");
  	  	  var ids=data.ids;	
  	  	  var id = data.id;	
  	  	  toolbar = $("#toolbar").ligerToolBar({ 
                items: [
                    { text: '关闭', icon: 'delete', click: function(item){
                        $.pap.removeTabItem();
                    }},
                    { line:true },
     	            { text: '上一单', icon: 'up', click: function(item){
     	                 var infoId="";
     	                 for(var i=0;i<ids.length;i++){
     	                    if(ids[i]==id){
     	                      if(i==0){
    	 	                     $.ligerDialog.warn('到顶啦！');
    	 	                      return;
     	                      }else{
     	                        infoId=ids[i-1];
     	                      }
     	                      continue;
     	                    }
     	                 }
    		  			    $.pap.addTabItem({ 
    		  			 		tabid:'viewContractOrder'+infoId,
      	   	    				text: '合同【'+infoId+'】',
          	   					params:{ 'ids':ids,'id':infoId},
      	   	    				url: 'contract!loadDetailById?id='+infoId
      	   	    		});
      	   	    		$.pap.removeTabItem();
     			     }},
                     { line:true },
     	             { text: '下一单', icon: 'down', click: function(item){
     	                 var infoId="";
     	                 for(var i=0;i<ids.length;i++){
     	                    if(ids[i]==id){
     	                      if(i==(ids.length-1)){
    	 	                     $.ligerDialog.warn('到底啦！');
    	 	                      return;
     	                      }else{
     	                        infoId=ids[i+1];
     	                      }
     	                      continue;
     	                    }
     	                 }
     	                 $.pap.addTabItem({ 
    	  			 		 	tabid:'viewContractOrder'+infoId,
    	   	    				text: '合同【'+infoId+'】',
      	   					params:{ 'ids':ids,'id':infoId},
    	   	    				url: 'contract!loadDetailById?id='+infoId
    	   	    		    });
      	   	    		$.pap.removeTabItem();
     			     }}
		        ]
 		     });
  	  	     form =$("#form0").ligerForm({
  	  	    	labelWidth: 70, 
	  			 inputWidth: 170,
          		 space:20,
	  			 fields: [
					{display: "合同流水",name:"e.id", type: "text",options: {value:'${e.id}',readonly: true},group: "养殖合同", groupicon: groupicon},	
					{display: "合同编码",name:"e.code", type: "text",options: {value:'${e.code}',readonly: true}}, 
					{display: "签订日期",name:"e.registDate", type: "date",options: {value: '${e.registDate}', readonly: true}},
					{display: "代养农户", name:"e.farmer.name", type:"text",group: "合同信息", groupicon: groupicon,options: {value:'${e.farmer.name}',readonly: true}},
					{display: "养殖公司", name:"e.company.name", type:"text",options: {value:'${e.company.name}',readonly: true}},
					{display: "饲料工厂", name:"e.feedFac.name",type:"text",options: {value:'${e.feedFac.name}',readonly: true}},
					{display: "开发人员", name: "e.developMan.name",newline: true,type: "text",options:{value:'${e.developMan.name}',readonly: true}},
					{display: "技术人员", name: "e.technician.name",type: "text",options:{value:'${e.technician.name}',readonly: true}},
					{display: "养殖品种", name:"e.variety.name",type:"text",options: {value:'${e.variety.name}',readonly: true}},
					{display: "养殖数量",name: "e.pigletQuan",newline: true, type: "text",options: {value:'${e.pigletQuan}',readonly: true}},
					{display: "猪苗单价", name:"e.pigletPrice", type: "text",options: {value:'${e.pigletPrice}',readonly: true}},
					{display: "允许料肉比偏差值",name: "e.allowDiff",labelWidth: 120, group: "料肉比参数", groupicon: groupicon, type: "text",validate: { required: true },options:{readonly: true,value:'${e.allowDiff}'}},
					{display: "超出允许偏差值扣款价(元/吨)",labelWidth: 180, name: "e.firstPrice", type: "number",validate: { required: true },options:{readonly: true,value:'${e.firstPrice}'}},
					{display: "料肉比一级偏差值",labelWidth: 120, name: "e.firstDiff", type: "number",validate: { required: true },newline: true,options:{readonly: true,value:'${e.firstDiff}'}},
					{display: "超出一级偏差值扣款价(元/吨)",labelWidth: 180,name: "e.overFirstPrice", type: "number",validate: { required: true },options:{readonly: true,value:'${e.overFirstPrice}'}},
					{display: "当前状态", name:"e.status.value",group: "合同状态", groupicon: groupicon, type: "text",options: {value:'${e.status.value}',readonly: true}},
				    {display: "审核状态",name: "e.checkStatus", type: "text", options:{readonly:true,value: '${e.checkStatus}',
					   render: function(v){
							var text = v;
							if(text=='1')
								return "已审核";
							else
								return "未审核";
					}}},
				    {display: "审核人",name: "e.checkUser", type: "text",options:{readonly:true,value: '${e.checkUser}'}},
				    {display: "审核时间",name: "e.checkDate",newline: true, type: "text",options:{readonly:true,value: '${e.checkDate}'}},
					{display: "创建人",name: "e.createUser", type: "text",options:{readonly:true,value: '${e.createUser}'}},
					{display: "创建时间",name: "e.createDate", type: "date",options:{readonly:true,value: '${e.createDate}'}},
					
               ]});

	  	  	   feed=$("#form1").ligerGrid({
			  	 	 url:"contract_feed_priced!loadByEntity",
			         parms:{"e.contract.id":"${e.id}"},
	                 columns:[
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
					checkbox:false,
					height:'99%',
		            rownumbers:true
	     	 });
			 pig=$("#form2").ligerGrid({
		  	     url:"contract_pig_priced!loadByEntity",
			     parms:{"e.contract.id":"${e.id}"},
		         columns:[
					{ display: "编码", name: 'id',hide:true},
					{ display: "销售级别编码", name: 'pigLevel.dcode',hide:true},
					{ display: "销售级别名称", name: 'pigLevel.value'},
					{ display: "销售级别定价(元)", name: 'price',editor: { type: 'text',options: { initSelect: true }}}
				  ],
				  usePager: false, 
				  checkbox:false,
				  height:'99%',
			      rownumbers:true
		    });
			$("#tab").ligerTab();
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

