
Do(function(){
  
  var noti_ids = {"0": "444882108", "num": 1};
  var noti_api = 'http://www.douban.com/j/notification/read_notis';

  // 不再提醒
  Douban.init_delete_reply_notify=function(b){var a=function(g){g.preventDefault();var c=$(g.target);var h=c[0].href.split("#")[1];$.get("/j/accounts/remove_notify?id="+h);var d=c.closest(".item-req");if($.contains($(".top-nav-reminder")[0],d[0])){d=d.parent();var f=d.siblings().length;d.fadeOut(function(){d.remove()});if(f===0){d.closest(".bd").find(".no-new-notis").show()}}else{d.fadeOut()}};if(b.type==="click"){a(b)}else{$(b).click(a)}};
  Douban.init_discard_notify=function(b){var a=function(i){i.preventDefault();var c="/j/notification/discard";var f=$(i.target);var d=f[0].name;$.post_withck(c,{id:d},function(e){},"json");var g=f.closest(".item-req");if($.contains($(".top-nav-reminder")[0],g[0])){g=g.parent();var h=g.siblings().length;g.fadeOut(function(){g.remove()});if(h===0){g.closest(".bd").find(".no-new-notis").show()}}else{g.fadeOut()}};if(b.type==="click"){a(b)}else{$(b).click(a)}};
  if (window.load_event_monitor) {
    load_event_monitor($('#db-global-nav'));
  }
  (function(b){var e=document;var a=[];var d=function(h,f,j,p,g){var q=g.createElement("form");var l,r,m,o;var n=function(i,s){var k=g.createElement("input");k.type="hidden";k.name=i;k.value=s;return k};q.target=p;q.action=h;q.method=f;for(l in j){if(j.hasOwnProperty(l)){r=j[l];if(typeof r==="object"&&Object.prototype.toString.call(r).search(/array/i)){m=r.length;while(m--){o=n(l,r[m]);q.appendChild(o)}}o=n(l,r);q.appendChild(o)}}return q};var c=function(){a.push(0);return"_xpost_iframe_"+a.length};b.xPost=function(h,j){var k=c();var i;var g;var f;if("ActiveXObject" in window){f=new ActiveXObject("htmlfile");f.open();f.write('<html><body><iframe id="xpost-proxy-iframe'+k+'"></iframe></body></html>');f.close();g=f.getElementById("xpost-proxy-iframe"+k);g.contentWindow.name=k;i=d(h,"post",j,k,f);f.body.appendChild(i);f=f.body}else{g=e.createElement("iframe");g.width=0;g.name=k;g.height=0;g.style.position="absolute";g.style.visibility="hidden";g.src="javascript:false;";f=e.body;f.appendChild(g);i=d(h,"post",j,k,e);f.appendChild(i)}g.onload=function(){};i.submit()}})(jQuery);var popup;var nav=$("#db-global-nav");var more=nav.find(".bn-more");nav.delegate(".bn-more, .top-nav-reminder .lnk-remind","click",function(f){f.preventDefault();var c=$(this);var d=c.parent();if(popup){popup.parent().removeClass("more-active");if($.contains(d[0],popup[0])){popup=null;return false}}d.addClass("more-active");popup=d.find(".more-items");if(c.hasClass("lnk-remind")&&noti_ids.num){var a=nav.find(".top-nav-reminder .num");var b=(a.find("span").text()|0)-noti_ids.num;b=b<=0?0:b;if(b===0){a.hide()}else{a.find("span").text(b)}noti_ids.num=0;$.xPost(noti_api,$.extend({ck:get_cookie("ck")},noti_ids))}return false});$(document).click(function(a){if($(a.target).closest(".more-items").length){return}if(popup){popup.parent().removeClass("more-active");popup=null}});
});

Do(function(){
  var nav = $('#db-nav-location');
  var inp=$("#inp-query"),label=inp.closest(".nav-search").find("label");if(inp.val()!==""){label.hide()}inp.parent().click(function(){inp.focus();label.hide()}).end().focusin(function(){label.hide()}).focusout(function(){if($.trim(this.value)===""){label.show()}else{label.hide()}}).keydown(function(){label.hide()});nav.find(".lnk-more, .lnk-account").click(function(b){b.preventDefault();var d,a=$(this),c=a.hasClass("lnk-more")?$("#db-productions"):$("#db-usr-setting");if(!c.data("init")){d=a.offset();c.css({"margin-left":(d.left-$(window).width()/2-c.width()+a.width()+parseInt(a.css("padding-right"),10))+"px",left:"50%",top:d.top+a.height()+"px"});c.data("init",1);c.hide();$("body").click(function(g){var f=$(g.target);if(f.hasClass("lnk-more")||f.hasClass("lnk-account")||f.closest("#db-usr-setting").length||f.closest("#db-productions").length){return}c.hide()})}if(c.css("display")==="none"){$(".dropdown").hide();c.show()}else{$(".dropdown").hide()}});
  var b=$(document.body);var list=$("#db-nav-location-list");var lnk=$("#db-nav-location .label");lnk.click(function(a){b.toggleClass("locs-shown");if(b.hasClass("locs-shown")){var c=lnk.offset();list.css({display:"block","margin-left":-1*($(window).width()/2-c.left),top:c.top+32})}else{list.hide()}return false});b.click(function(){b.removeClass("locs-shown");list.hide()});
});

  Do(function(){var a={step:2,duration:0.4,autoplay:false,frametime:5,prevBtn:".btn-prev",nextBtn:".btn-next",prevBtnCls:"btn-prev",nextBtnCls:"btn-next"};function b(d){var c=this;c.hooks={run:{},ran:{}};c.config=$.extend({},a,d);return c}b.prototype={init:function(){var p=this;var k=p.config;var g=p.step=k.step;var d=p.container=$(k.container);var q=p.screen=d.hasClass("ui-slide-screen")?d:d.find(".ui-slide-screen");var o=p.film=q.children(".ui-slide-contents");var f=p.kids=o.children();var n=p.stepMax=p.kids.length;var h=p.stepNow=0;if(n<=g){return p}if(!p.stepWidth){p.stepWidth=q.width()/g}var j='<a href="javascript:;" tabindex="-1" class="{cls}">{txt}</a>';var e="-disabled";$.each(["prev","next"],function(s,t){var r=k[t+"Btn"];if(!r){return}if(typeof r=="string"){r=d.find(r)}if(r.length){p[t+"Btn"]=r;k[t+"BtnCls"]=r[0].className}});var i=p.prevBtn;var c=p.nextBtn;var l=k.prevBtnCls;var m=k.nextBtnCls;if(!i||!c){i=p.prevBtn=$(j.replace("{cls}",l).replace("{txt}","&lt;"));c=p.nextBtn=$(j.replace("{cls}",m).replace("{txt}","&gt;"));Do.ready(function(){d.append(i).append(c)})}i.click(function(r){r.preventDefault();p.prev()}).addClass(k.prevBtnCls+"-disabled");c.click(function(r){r.preventDefault();p.next()});if(k.autoplay){p.play();d.mouseover(function(){p.pause()}).mouseout(function(){p.play()})}return p},play:function(d){var c=this;c._t=setTimeout(function(){c.next();c.play()},c.config.frametime*1000)},pause:function(){try{clearTimeout(this._t)}catch(c){}},_run:function(h,f){var o=this;var j=o.config;var f=o.step=f||j.step;var g=o.stepNow;var n=o.stepMax;var e="-disabled";var i=o.prevBtn;var c=o.nextBtn;var l=j.prevBtnCls;var m=j.nextBtnCls;var k="+";var d=end=g;if(h=="next"){if(g<=0){i.removeClass(l+e)}if(g+2*f>=n){c.addClass(m+e)}if(g+f>=n){return o}k="-";d=o.stepNow+=f;end=d+f}else{if(g+f>=n){c.removeClass(m+e)}if(g<=f){i.addClass(l+e)}if(g<=0){return o}end--;d=o.stepNow-=f}o.punc=k;o.currentFrame=o.kids.slice(d,end);o._runHook("run");o.film.stop(true,true).animate({left:k+"="+(o.stepWidth*f)+"px"},j.duration*1000,function(){o._runHook("ran")});return o},_runHook:function(d){var c=this;var e,f;for(e in c.hooks[d]){f=c.hooks.run[e];if(typeof f=="function"){f.call(c,d)}else{e=b.plugins[e];e&&e.call(c,d,f)}}},next:function(d){var c=this;var e=c.stepNow;d=d||c.config.step;if(e+d>=c.stepMax){return c.prev(e)}return this._run("next",d)},prev:function(c){return this._run("prev",c)}};b.plugins={};Do.SimpleSlider=b});
  Do.SimpleSlider.plugins.lazyload=function(b){var a=this;var c=a.currentFrame;c.find("script.tmpl").each(function(d,e){e.parentNode.innerHTML=e.innerHTML})};
  Do.SimpleSlider.plugins.circular=function(c,b){var a=this;var d="-disabled";if(a.punc=="+"){if(a._stepMax){a.stepMax=a._stepMax;a.cloned_kids&&a.cloned_kids.remove();a._stepMax=0}return}if(c=="ran"){if(a._stepMax){a.kids.remove();a.kids=a.film.css({width:a.stepWidth*a.stepMax,left:0}).children();a.stepMax=a._stepMax;a.stepNow=0;a._stepMax=0}else{if(a.stepNow+a.config.step>=a.stepMax){a._stepMax=a.stepMax;a.cloned_kids=a.kids.clone();a.film.width(a.stepWidth*a.stepMax*2).append(a.cloned_kids);a.stepMax+=a.config.step}}}else{if(c=="run"){if(a._stepMax){a.prevBtn.addClass(a.config.prevBtnCls+"-disabled")}}}};
  Do(function() {
    var slider = new Do.SimpleSlider({
      step: 1,
      autoplay: true,
      container: $('#db-drama-slides')
    });
    slider.init();
    var counter = slider.container.find('span.ui-slide-counter');
    function updateCounter() {
      var step = slider.step;
      var now = slider.stepNow + 1;
      var max = slider._stepMax || slider.stepMax;
      if (now > max) now = 1;
      counter.html(Math.ceil(now/step) + '/' + Math.ceil(max/step));
    };
    updateCounter();
    slider.hooks.run.counter = updateCounter;
    // 启用lazyload 插件
    slider.hooks.run.lazyload = 1;
    // 启用循环播放插件
    slider.hooks.run.circular = 1;
    slider.hooks.ran.circular = 1;
  });
  