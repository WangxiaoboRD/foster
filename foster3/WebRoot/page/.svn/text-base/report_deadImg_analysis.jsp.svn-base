<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<title>${initParam.title}</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		
		.box {
		    /* width: 850px; */
		    height: auto;
		    overflow: hidden;
		    /* background: #666; */
		    /* margin-top: 10px; */
		    margin-right: auto;
		    /* margin-bottom: 10px; */
		    margin-left: auto;
		    /* padding-top: 10px; */
		    padding-right: 0;
		    padding-bottom: 0;
		    padding-left: 7px;
		}
		.box ul {
			margin:0px;
			padding:0px;
			float:left;
			list-style-type:none;
		}
		.box li {
			width: 180px;
		    height: 120px;
		    float: left;
		    cursor: pointer;
		    display: inline;
		    margin: 0 10px 10px 0;
		    border: 1px solid rgba(51, 51, 51, 0.17);
		}
		#bg {
			width:100%;
			height:100%;
			position:absolute;
			left:0px;
			top:0px;
			background:#000;
		filter:alpha(opacity:50);
			opacity:0.5;
			display:none;
		}
		#bottom {
			width:215px;
			height:50px;
			position:absolute;
			left:50%;
			bottom:0px;
			margin:0 0 0 -107px;
			border:1px solid #232323;
			background:#444;
			padding:1px;
			z-index:1;
			display:none;
		}
		#bottom ul {
			width:100%;
			height:100%;
			margin:0px;
			padding:0px;
			list-style-type:none;
			background:#000;
		}
		#bottom li {
			background:url(../original/imgShow/images/ico.jpg) no-repeat;
			float:left;
			display:inline;
			margin:8px 0 0 18px;
			cursor:pointer;
		}
		#bottom li.prev {
			width:30px;
			height:33px;
			background-position:0 0;
		}
		#bottom li.next {
			width:30px;
			height:33px;
			background-position:-35px 0;
		}
		#bottom li.img {
			width:30px;
			height:33px;
			background-position:-106px 0;
		}
		#bottom li.close {
			width:31px;
			height:33px;
			background-position:-70px 0;
		}
		#frame {
			background:#fff;
			padding:5px;
			position:absolute;
			z-index:2;
			display:none;
			filter:alpha(opacity:0);
			opacity:0;
			text-align:center;
		}
	</style>      	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
    <script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
    <script src="../original/imgShow/js/lanrenzhijia.js" type="text/javascript"></script>
	
    <script type="text/javascript">
    	var toolbar;
    	var form;
        $(function () {
        	   var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
        	   toolbar =$("#toolbar").ligerToolBar({ 
   	                   items: [
                   			{ text: '查询条件', icon: 'search', click: function(){
                   				var display = $("form").css("display");
                   				if (display == 'none') {
	                   				$("form").css({ display: 'block' });
                   				}else {
                   					$("form").css({ display: 'none' });
                   				}
                            }}
                  		]
             	});
        	   form=$("form").ligerForm({
                    labelWidth: 80, 
                    inputWidth: 150,
	                fields:[
						{ display: "养殖公司",newline: true, name:"e.company.id",validate: { required: true },type:"select",options:{
						    url:'company!loadByEntity',
						    valueField: 'id', //关键项
							textField: 'name',
							keySupport:true,
							selectBoxHeight: 150,
							onSelected: function (nv){
								var _farmer = liger.get("farmer");
								if(_farmer)
									_farmer.setValue("");
								var _batch = liger.get("batch");
								if(_batch)
									_batch.setValue("");
                    		}
						}},
						{ display: "代养农户", name:"e.farmer.id",comboboxName: 'farmer', type:"select",options:{
							url: 'farmer!loadByPinYin',
			                valueField: 'id',
			                textField: 'name', 
			                selectBoxHeight: 180,
			                triggerToLoad: true,
			                keySupport:true,
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
						{ display: "批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次", name: "e.batch.id",comboboxName: 'batch',type: "select",options:{
						    url:'batch!loadByEntity',
						    valueField: 'id', 
					        textField: 'batchNumber',
					        keySupport:true,
							selectBoxHeight: 150,
							triggerToLoad: true,
							onBeforeOpen: function() {
			                	var g = this;
			                	g.setParm('e.farmer.id', $("input[id='e.farmer.id']").val());
			                }
						}},
				    ],
					buttons:[
	                    { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', width: 70, click:function(item){
	                    	var arra = $('form').formToArray();
	                		$.ligerui.ligerAjax({
								type: "POST",
								async:  false,
								url: "death_bill!loadByImg",
								data: arra,
								dataType: "json",
								success: function(data, status){
									$("#viwe").empty();
                					if(data != ''){
                						var rus = data.Rows;
                						//循环
                						var html="";
                						for ( var i in rus ){
                    						html += "<li><img src='${pageContext.request.contextPath}/deathBill/"+i+"' width='100%' height='100%' title='"+rus[i]+"' ></li>"
										}
                						$("#viwe").append(html);
                						imgView();
			                	   }else{
			                		   $.ligerDialog.error('未获取照片');
				                   }
								},
								error: function(XMLHttpRequest,status){
									$.ligerDialog.error('操作出现异常');
								},
								complete:function(){}
						   	})
	                    	$("form").css( { display: 'none' });
	                    }}
	                 ]
               	});
        });
    	
    </script>
 
</head>
<body>
<div id="toolbar" style='height:23px;' ></div>
<form></form>
<hr style="height:2px;border:none;border-top: solid 1px rgb(235, 235, 235);margin-left:7px; margin-right:42px;"/>
<div class="box" id="box">
	<ul id="viwe">
    	
    </ul>
</div>
<div id="bg"></div>
<div id="bottom">
	<ul>
    	<li class="prev"></li>
        <li class="img"></li>
        <li class="next"></li>
        <li class="close"></li>
    </ul>
</div>
<div id="frame"></div>
</body>
</html>
