(function(a){a.fn.lightBox=function(K){var f=this;K=jQuery.extend({overlayBgColor:"#F9FBF9",overlayOpacity:0.9,bgPosition:"0",imageLoading:"/pics/lightbox/lightbox-ico-loading.gif",imageBtnPrev:a.browser.version=="6.0"?"/pics/online/left-hover.gif":"/pics/lightbox/btn-prev-hover.png",imageBtnNext:a.browser.version=="6.0"?"/pics/online/right-hover.gif":"/pics/lightbox/btn-next-hover.png",imageBtnClose:"/pics/online/close.gif",imageBlank:"/pics/lightbox/lightbox-blank.gif",containerResizeSpeed:400,noticeWord:"你的回应...",photosPerpage:90,imageArray:[],activeImage:0},K);function r(){o(this,f);return false}function o(M,L){q();K.imageArray.length=0;K.activeImage=0;x();if(L.length==1){K.imageArray.push([M.getAttribute("data-pid"),M.getAttribute("title")])}else{H(L)}while(K.imageArray[K.activeImage][0]!=M.getAttribute("data-pid")){K.activeImage++}B();c()}function x(){K.origLocation=window.location.href.split("#")[0];if(window.location.href.split("?start=").length>1){var L=window.location.href.split("?start=")[1]}K.currentPage=parseInt(parseInt(L)/K.photosPerpage)||0;K.isSortBy=window.location.href.split("sortby=").length>1?true:false}function H(M){for(var L=0;L<M.length;L++){if(K.imageArray[M[L].getAttribute("data-pid")]!=""){K.imageArray.push([M[L].getAttribute("data-pid"),M[L].getAttribute("title")])}}}function q(){var L=m();var M=p();var N=['<div id="jquery-overlay"></div>','<div id="jquery-lightbox"><div id="lightbox-wrapper">','<div id="lightbox-container-image-box">','<a id="lightbox-close-btn" href="" alt="关闭(ESC)" title="关闭(ESC)"></a>','<div id="lightbox-caption-nav"><a href="" target="_blank">查看详情</a></div>','<div id="lightbox-container-image">','<a href="#" id="lightbox-image-next"><img id="lightbox-image"></a>','<a href="#" id="lightbox-nav-btnPrev"></a><a href="#" id="lightbox-nav-btnNext"></a>','<div id="lightbox-loading">','<a href="#" id="lightbox-loading-link">','<img src="'+K.imageLoading+'"></a></div></div></div>','<div id="lightbox-container-image-data-box">','<div id="lightbox-container-image-data">','<div id="lightbox-image-details">','<span id="lightbox-image-details-caption"></span><span id="lightbox-image-uploader"></span>','<div id="lightbox-snsbar"></div>','<div id="lightbox-image-comments"></div>','<div id="lightbox-image-comment-box"></div>',"</div></div></div></div></div>"].join("");a("body").append(N).css("overflow","hidden").scroll(function(){return false});a(".top-nav,#wrapper").css({position:"relative","z-index":"-1"});a("#lightbox-close-btn").attr("hideFocus","hideFocus").css("outline","none");a("#jquery-lightbox").find("a").attr("hideFocus","hideFocus").css("outline","none");a("html").css({overflow:"visible"});a("#jquery-overlay").css({backgroundColor:K.overlayBgColor,opacity:K.overlayOpacity,height:L[1]*1.5}).fadeIn();a("#jquery-lightbox").css({zIndex:"none",top:M[1],left:M[0]}).show().scroll(function(){a("#db-div-sharing").hide()});a("#lightbox-loading-link,#lightbox-close-btn").click(function(){n();return false});a(window).resize(function(){g()})}function g(){var L=m();var M=p();a("#jquery-overlay").css({width:L[0],height:L[1]});a("#jquery-lightbox").css({top:M[1],left:M[0]})}function B(){var M=e(K.imageArray[K.activeImage][0]);var O=window.location.pathname;var N=u(O);var L=new Image();K.pid=M;K.detailHref="http://"+window.location.host+"/online/"+N+"/photo/"+M+"/";K.onlineHref=a("#photo_album .pl").find("a")[0]?a("#photo_album .pl").find("a")[0].href:K.origLocation;a("#lightbox-caption-nav a").attr("href",K.detailHref);History.replaceState(null,null,K.detailHref);a("#lightbox-loading").show();a("#lightbox-container-image-box").css({"border-bottom":"1px solid #DDD"});a("#lightbox-image,#lightbox-container-image-data-box").hide();L.onload=function(){a("#lightbox-image").attr("src",K.imageArray[K.activeImage][0]);l(L.width,L.height);L.onload=function(){}};L.src=K.imageArray[K.activeImage][0]}function l(P,N){var L=a("#lightbox-container-image-box").width(),O=a("#lightbox-container-image-box").height(),M=L-P,Q=O-N;a("#lightbox-container-image-box").css({width:P,height:N});a("#lightbox-container-image-data-box").css({width:P});F();if((M==0)&&(Q==0)){if(a.browser.msie){A(250)}else{A(100)}}}function F(){var L=600;a("#lightbox-loading").hide();a("#lightbox-container-image-box").css({"border-bottom":"none"});a("#db-div-sharing").hide();t("#lightbox-image",700);a("#lightbox-image").fadeIn(100);b();z()}function b(){var L=a("#jquery-lightbox");a("#lightbox-container-image-data-box").width(700).show();a("#lightbox-image-details-caption").html("").hide();if(K.imageArray[K.activeImage][1]){a("#lightbox-image-details-caption").html(K.imageArray[K.activeImage][1]).show()}G()}function G(){var L=a("#lightbox-image-uploader");var M=a("#lightbox-image-comments");L.html("");M.html("<p style='text-align:center;padding:20px;'>加载中..</p>");a.get(K.detailHref,function(N){$detailPage=a(N);$detailPage.find("script").remove();L.html($detailPage.find(" .photo-ft a:last"));if(a("#lightbox-image-details-caption").html()!==""){L.find("a").before(" (来自 ").after(")")}else{L.find("a").before("来自 ").after("")}a("#lightbox-snsbar").html($detailPage.find(".sns-bar"));M.html($detailPage.find("#comments"));M.find(".op-lnks").remove();var O=M.find(".comment-item").length;a("#lightbox-container-image-data-box").width(700);j(O);i();y()});M.ajaxError(function(){M.html("<p style='text-align:center;padding:20px;'>加载失败..</p>")})}function i(){if(a(".a_show_login").length>0){var L=a("#comments");var M='<h2>你的回应...</h2><div class="comment-form txd" id="add_comment"><div class="item" id="no-comments-textarea"><a class="j a_show_login fake"><textarea name="rv_comment" rows="4" cols="64" style="height: 60px; color: rgb(0, 0, 0); "></textarea></a><br></div><div class="item"><a class="bn-flat-hot bn-flat"><input type="submit" value="回应" class="j a_show_login"></a></div></div>';L.append(M);L.find("span").remove();load_event_monitor(document)}}function t(O,N){var L=a(O);var N=(L.width()<N)?N+"px":"";var M=a("#lightbox-container-image-box").width(N)}function j(M){var N=M!==0?true:false;var L=a(".bn-flat-hot").find("input").val("回应").end().addClass("bn-flat")}function c(){K.nextBtn=a.browser.version=="6.0"?"/pics/online/right.gif":"/pics/lightbox/btn-next.png";K.prevBtn=a.browser.version=="6.0"?"/pics/online/left.gif":"/pics/lightbox/btn-prev.png";a("#lightbox-nav").addClass("fixed-top").show();a("#lightbox-nav-btnPrev").css({background:"url("+K.prevBtn+") left "+K.bgPosition+" no-repeat"});a("#lightbox-nav-btnNext").css({background:"url("+K.nextBtn+") right "+K.bgPosition+" no-repeat"});k();C()}function k(){a("#lightbox-nav-btnPrev").unbind().hover(function(){a(this).css({background:"url("+K.imageBtnPrev+") left "+K.bgPosition+" no-repeat"})},function(){a(this).css({background:"url("+K.prevBtn+") left "+K.bgPosition+" no-repeat"})}).show().bind("click",function(L){if(K.activeImage==0){K.activeImage=K.imageArray.length-1}else{K.activeImage=K.activeImage-1}B();return false})}function C(){a("#lightbox-nav-btnNext").unbind().hover(function(){a(this).css({background:"url("+K.imageBtnNext+") right "+K.bgPosition+" no-repeat"})},function(){a(this).css({background:"url("+K.nextBtn+") right "+K.bgPosition+" no-repeat"})}).show().bind("click",function(){if(K.activeImage==K.imageArray.length-1){J()}else{K.activeImage=K.activeImage+1;B()}return false});a("#lightbox-image-next").unbind().bind("click",function(){if(K.activeImage==K.imageArray.length-1){J()}else{K.activeImage=K.activeImage+1;B()}return false})}function J(){var M=(parseInt(K.activeImage)+1)+K.currentPage*K.photosPerpage;var L=K.onlineHref.split("?")[0]+"?start="+M;if(K.isSortBy){L=L+"&sortby=popularity"}a("#lightbox-nav-btnNext,#lightbox-image-next").unbind().click(function(){return false});a.get(L,function(P){var N=a(P),O=N.find("#online-photos").find("a img").parent();K.hasList=N.find("#online-photos").length;H(O);if(O.length!==0){K.activeImage=K.activeImage+1}else{K.activeImage=0}B();c()})}function y(){a(".bn-flat-hot input[type=submit]").click(function(L){D();return false});I()}function D(){var N=a("form[name='comment_form']");var L=a("#comments textarea");var M=N.serialize();var O=L.val();if(O&&O!==K.noticeWord){a.post(K.detailHref+"add_comment",M,function(P){b()})}else{a("#post-notice").remove();L.after("<p id='post-notice'>说点儿啥再发</p>")}}function e(O){var N=O.split("/"),M,L;M=N[N.length-1];L=M.split(".")[0].slice(1);return L}function u(L){return L.split("/")[2]}function I(){a(document).unbind().keydown(function(L){h(L)})}function E(){a(document).unbind()}function h(L){if(L==null){keycode=event.keyCode;escapeKey=27}else{keycode=L.keyCode;escapeKey=L.DOM_VK_ESCAPE}if(keycode==27){v();n()}if(keycode==37){if(K.activeImage!=0){K.activeImage=K.activeImage-1}else{K.activeImage=K.imageArray.length-1}B()}if(keycode==39){if(K.activeImage!=(K.imageArray.length-1)){K.activeImage=K.activeImage+1;B()}else{J()}}}function z(){if((K.imageArray.length-1)>K.activeImage){objNext=new Image();objNext.src=K.imageArray[K.activeImage+1][0]}if(K.activeImage>0){objPrev=new Image();objPrev.src=K.imageArray[K.activeImage-1][0]}}function n(){var M=d();if(M){if(K.imageArray.length<=90){if(a.browser.msie&&parseInt(a.browser.version)<8){w()}v();E();if(a(".online_event_pic").length){if(K.hasList){w()}else{History.pushState(null,null,K.origLocation)}}else{var N=K.pid;var O="#"+N;var L=K.currentPage==0?"":"?start="+K.currentPage*K.photosPerpage;if(K.isSortBy){L==""?L="?sortby=popularity":L+="&sortby=popularity"}K.onlineHref=K.onlineHref.split("?")[0]+L;History.pushState(null,null,K.onlineHref);if(a(O).length>0){a(O).find("img").hide().fadeIn(700)}else{w()}}}else{w()}}}function w(){var Q=K.photosPerpage;var P=K.imageArray.length/Q;var O=parseInt(P);if(P==O){O-=1}var N=O+K.currentPage;var L=N==0?"":"?start="+N*Q;if(K.isSortBy){L==""?L="?sortby=popularity":L+="&sortby=popularity"}K.onlineHref=K.onlineHref.split("?")[0]+L;var M=K.onlineHref+"#"+K.pid;if(a.browser.msie){v();window.location.href=M;if(a.browser.version<8){location.reload()}}else{window.location.href=M}}function v(){a("body").css("overflow","auto");if(a.browser.version==7){a("body").height(0)}a("#jquery-lightbox").remove();a("#lightbox-close-btn").remove();a("#db-div-sharing").remove();a(".top-nav,#wrapper").css({position:"static","z-index":"auto"});a("#jquery-overlay").fadeOut(200,function(){a("#jquery-overlay").remove()})}function d(){var M=a("#comments textarea").val(),L=M!==""&&M!==K.noticeWord?true:false;if(a("#comments textarea").length>0){if(L){return confirm("确定要关闭吗?关闭页面后，之前的编辑将丢失")}else{return true}}else{return true}}function m(){var N,L;if(window.innerHeight&&window.scrollMaxY){N=window.innerWidth+window.scrollMaxX;L=window.innerHeight+window.scrollMaxY}else{if(document.body.scrollHeight>document.body.offsetHeight){N=document.body.scrollWidth;L=document.body.scrollHeight}else{N=document.body.offsetWidth;L=document.body.offsetHeight}}var M,O;if(self.innerHeight){if(document.documentElement.clientWidth){M=document.documentElement.clientWidth}else{M=self.innerWidth}O=self.innerHeight}else{if(document.documentElement&&document.documentElement.clientHeight){M=document.documentElement.clientWidth;O=document.documentElement.clientHeight}else{if(document.body){M=document.body.clientWidth;O=document.body.clientHeight}}}if(L<O){pageHeight=O}else{pageHeight=L}if(N<M){pageWidth=N}else{pageWidth=M}arrayPageSize=new Array(pageWidth,pageHeight,M,O);return arrayPageSize}function p(){var M,L;if(self.pageYOffset){L=self.pageYOffset;M=self.pageXOffset}else{if(document.documentElement&&document.documentElement.scrollTop){L=document.documentElement.scrollTop;M=document.documentElement.scrollLeft}else{if(document.body){L=document.body.scrollTop;M=document.body.scrollLeft}}}arrayPageScroll=new Array(M,L);return arrayPageScroll}function A(N){var M=new Date();L=null;do{var L=new Date()}while(L-M<N)}function s(){var L,M;L=a("#album_chunk_"+section_n);if(L.length&&a.trim(L.html())!==""){M=a(L.html());a(container).append(M);a("#online-photos").find("a").has("img").lightBox();section_n++;photos_layout(section_n)}if(section_n>section_total_num){a(".paginator").show();win.unbind("scroll")}}return this.unbind("click").click(r)}})(jQuery);