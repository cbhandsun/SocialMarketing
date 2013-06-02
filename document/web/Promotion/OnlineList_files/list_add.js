
jQuery(function($){

		$('.goodList .item').live({
			mouseover:function(){				
				$(this).children('.trsp_bg').stop().animate( { height: '80px' }, 300 );
			},
			mouseout:function(){				
				$(this).children('.trsp_bg').stop().animate( { height: '15px' }, 300 );
			}
		});

//		$('.infinite_scroll .item_list').masonry({
//			//itemSelector 	: ".item_list .item",
//			isAnimated:true,
//			animationOptions: {
//				duration: 500,
//				easing: 'linear',
//				queue: false
//			  }
//		});
		$("img").lazyload({
			 effect       : "fadeIn",
			 load: function() {
				 loadErrorsrc(this)
			 }
		 });
//		var sp = 1
//		$(".infinite_scroll .item_list").infinitescroll({
//			navSelector  	: "#more",
//			nextSelector 	: "#more a",
//			itemSelector 	: ".item_list .item",
//			loading: {
//				img: TPL_PATH +"images/loading.gif",
//				msgText: "",
//				finishedMsg: '',
//				finished: function(){
//					sp++;
//					if(sp>=4){	//def.waterfall_sp
//						$("#more").remove();
//						//$("#infscr-loading").hide();
//						$(".page_box").show();
//						$(window).unbind('.infscr');
//					}
//					$("#infscr-loading").hide();
//				}	
//			},
//			errorCallback:function(){ 
//				$(".page_box").show();
//			}
//		}, function(newElements){
//			//var newElems = $(newElements);
//			//$('.infinite_scroll .item_list').masonry('appended', newElems, false);
//			//newElems.fadeIn();
//			
//			var newElems = $( newElements ).css({ opacity: 0 });
//			// ensure that images load before adding to masonry layout
//			newElems.imagesLoaded(function(){
//				// show elems now they're ready
//				newElems.animate({ opacity: 1 });
//				$('.infinite_scroll .item_list').masonry( 'appended', newElems, true ); 
//			});		   
//		});
	});

function check_jf(c,f)
{
	if(USER_JF == 0 || USER_ID == "vipshop")	
	{
		window.location.href = "list.php?action=me&c="+c+"&f="+f;
	}
	else
	{
		$.ajax({
			type:"GET",
			url: SITE_PATH+"services/service.php?m=exchange&a=myjf&c="+c+"&f="+f,
			cache:false,
			dataType:"json",
			success: function(result){
				switch(parseInt(result.status))
				{
					case 1:
					 $("#overlayer").show();
					 $("body").addClass('overflow:hidden;height:100%');
					 $("#loadbox").show();
					 break;
					 default:
						 window.location.href = "list.php?action=me&c="+c+"&f="+f;
					 
				}
			}
		})
	}
}
function layer_close() {
 $("#loadbox").hide();
 $("#overlayer").hide();
}
function loadErrorsrc(ojb){
	var $img = $(ojb);
	$img.each(function(i) {
		var image = new Image();
		image.src = $(this).attr('data-original');
		var thisH = image.height;
		var thisW = image.width;
		if (thisH == thisW && thisH != '') {
			var AttrerrorImgSrc = $(this).attr('data-onerror');
			$(this).attr('src', AttrerrorImgSrc)
		}
	})
}