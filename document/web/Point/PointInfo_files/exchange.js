var goodsID = 0;
jQuery(function($){

	$("#exchangeBtn").click(function(){
		var userjf = parseInt(USER_JF);
		if(isNaN(userjf)){
			alert('网络繁忙！您的积分获取有误，尝试刷新或重新登录！');
			window.location =window.location.href;
			return false;
			}

		goodsID = this.getAttribute("goodsID");if(goodsID==0)return false;
		goodsType = this.getAttribute("goodsType");
		goodsJF = parseInt(this.getAttribute("goodsJF"));
		goodsNum = parseInt($("#ex_num").html());
		if(goodsNum<0)return false;

oit_jf_cart(goodsID,goodsNum,goodsJF*goodsNum);

		overlayer_start();
		if(userjf < goodsJF*goodsNum)
		{
			hide_layer();			
			$("#layer2").show();
			$("#_lay_close").show();
			return false;
		}
		else
		{
			if(USER_ID == 0 || USER_ID == "vipshop")
			{
				hide_layer();
				$("#layer1").show();
				$("#_lay_close").show();
				return false;
			}
			if(USER_ID.length==32)
			{
				//唯品礼卷直接发货
				if(goodsType==2)
				{
				   $.ajax({
					type:"POST",
					url: SITE_PATH+"services/service.php?m=exchange&a=good",
					data:"id="+goodsID+"&ex_num="+goodsNum,
					cache:false,
					dataType:"json",
					success: function(result){
						switch(parseInt(result.status))
						{
							case 1:
								hide_layer();
								USER_JF = parseInt(USER_JF) - goodsJF*goodsNum;
								if(USER_JF>0)$("#u_jf").html(USER_JF);
								$("#layer6").show();
								break;
							case 2:
								hide_layer();
								layer_msg2(result.msg);
								break;
							default:
								hide_layer();
								$("#layer5").show();
						}
					}
				  });
				}
				else
				{
					hide_layer();
					$("#layer3").show();
					$("#_lay_close").hide();

				}
			}
			else
			{
				alert('网络出错！请重新登录尝试！');
				website_logout();
				return false;
			}
		}
		return false;
	});
	//随单发货
	$("#btn_order1").click(function(){
		$("#layer3").hide();
		$("#_lay_close").show();
		$("#_loading").show();
		//s.linkTrackVars='eVar24,';
		//s.eVar24="随新订单发货";
		//s.tl(this,'o','with the new order');
	  $.ajax({
		type:"POST",
		url: SITE_PATH+"services/service.php?m=exchange&a=good",
		data:"id="+goodsID+"&ex_num="+goodsNum,
		cache:false,
		dataType:"json",
		success: function(result){
			switch(parseInt(result.status))
			{
				case 1:
					hide_layer();
					USER_JF = parseInt(USER_JF) - goodsJF*goodsNum;
					if(USER_JF>0)$("#u_jf").html(USER_JF);
					$("#layer4").show();					
					break;
				case 2:
					hide_layer();
					layer_msg2(result.msg);
					break;
				default:
					hide_layer();
				    layer_msg2(result.msg);
					//$("#layer5").show();
			}

		}
	  });
	  return false;
	});
	//单独发货
	$("#btn_order2").click(function(){
		$("#layer3").hide();
		$("#_lay_close").show();
		$("#_loading").show();
		//s.linkTrackVars='eVar24,';
		//s.eVar24="单独发货";
		//s.tl(this,'o','with the new order');
	  $.ajax({
		url: "http://www.vipshop.com/integral/exchange.php?id="+goodsID+"&ex_num="+goodsNum,
		jsonp:"callback",
		dataType:"jsonp",
		success: function(json){
			//1为成功，0为失败，-1为未登陆
			switch(parseInt(json.result))
			{
				case 1:
					window.location.href = "http://cart.vipshop.com/te/";
					break;
				case -1:
					hide_layer();
					layer_msg2("登录错误，请重新登录进行兑换");
					break;
				default:
					hide_layer();
					layer_msg2(json.msg);
			}
		}
	  });
	  return false;
	});
	//输入密码后回车确定
	$("#pswd").keypress(function(){
		var event = arguments[0]||window.event;
		if(event.keyCode == 13 && $("#pswd").val()!="")
		{
			 checklogin();
		}
	});
	//兑换数量的下拉框
	$('.jslct dd').bind({
	  click: function() {
		$('#ex_num').html($(this).html());
		$('#sel_dl').hide();
	  }
	});
	$('.jslct dl').hover(
	  function () {
		$('#sel_dl').show();
	  },
	  function () {
		$('#sel_dl').hide();
	  }
	);
	//推荐产品图鼠标移上样式
	$('.goodList .item').live({
		mouseover:function(){
			$(this).children('.trsp_bg').stop().animate( { height: '80px' }, 300 );
		},
		mouseout:function(){
			$(this).children('.trsp_bg').stop().animate( { height: '15px' }, 300 );
		}
	});
});

document.onclick=function(e){
var e=e?e:window.event; 
var tar = e.srcElement||e.target; 
if(tar.id!="sel_kz"){ 
$('#sel_dl').hide();
}}

function checklogin() {
  var pswd = $("#pswd").val();
  $.ajax({
	type:"POST",
	url: SITE_PATH+"services/service.php?m=exchange&a=checklogin",
	data:"pswd="+pswd,
	cache:false,
	dataType:"text",
	success: function(result){
		if(result==1) {
				//唯品礼卷直接发货
				if(goodsType==2)
				{
				   $.ajax({
					type:"POST",
					url: SITE_PATH+"services/service.php?m=exchange&a=good",
					data:"id="+goodsID+"&ex_num="+goodsNum,
					cache:false,
					dataType:"json",
					success: function(result){
						switch(parseInt(result.status))
						{
							case 1:
								hide_layer();
								USER_JF = parseInt(USER_JF) - goodsJF*goodsNum;
								if(USER_JF>0)$("#u_jf").html(USER_JF);
								$("#layer6").show();
								break;
							case 2:
								hide_layer();
								layer_msg2(result.msg);
								break;
							default:
								hide_layer();
								$("#layer5").show();
						}
					}
				  });
				}
				else
				{
					hide_layer();
					$("#layer3").show();
					$("#_lay_close").hide();
				}	

		}
		else
		{
			hide_layer();
			layer_msg('您的密码输入错误！');
		}
	}
  });
  return false;
}

function overlayer_start() { 
 $("body").addClass('overflow:hidden;height:100%');
 $("#overlayer").show();
 $("#loadbox").show();
 $("#_loading").show();
}
function overlayer_close() {
 $("#overlayer").hide();
 $("body").removeClass('overflow:hidden;height:100%');
}
function hide_layer() {
 $("#_loading").hide();
 $("#layer1").hide();
 $("#layer2").hide();
 $("#layer3").hide();
 $("#layer4").hide();
 $("#layer5").hide();
 $("#layer6").hide();
 $("#msgshow").hide();
 $("#msgshow2").hide(); 
}
function layer_close() {
 $("#_loading").hide();
 $("#layer1").hide();
 $("#layer2").hide();
 $("#layer3").hide();
 $("#layer4").hide();
 $("#layer5").hide();
 $("#layer6").hide();
 $("#msgshow").hide();
 $("#msgshow2").hide();
 $("#loadbox").hide();
 $("#overlayer").hide();
 $("body").removeClass('overflow:hidden;height:100%');
}
function layer_msg(msg) {
 $("#layer_msg").html(msg);
 $("#msgshow").show();
}
function layer_msg2(msg) {
 $("#layer_msg2").html(msg);
 $("#msgshow2").show();
}
function layer_return(id) {
 $("#msgshow").hide();
 $("#"+id).show();
}