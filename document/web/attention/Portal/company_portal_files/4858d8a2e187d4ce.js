
	Do = (typeof Do === 'undefined')? $ : Do;
	Do(function(){
      var reportDiv = "#link-report".concat("1321618");
      $("body").delegate(reportDiv, 'mouseenter mouseleave', function(e){

        switch (e.type) {
          case "mouseenter":
            $(this).find(".report").css('visibility', 'visible');
            break;
          case "mouseleave":
            $(this).find(".report").css('visibility', 'hidden');
            break;
        }
      });
      $(reportDiv).delegate(".report a", 'click', function(e){
          e.preventDefault();
          var auditUrl = "http://www.douban.com/misc/audit_report?url=",
              opt = "bulletin";
          var obj = $(e.target).closest(reportDiv);
          var id = obj.length != 0 ? obj.data("id") : undefined;
          var params = (opt&&id) ? '?'.concat(opt, '=', id) : '';
          var url = "http://site.douban.com/site/idrama/".concat(params);
          generate_report_dialog({report_url: url});
      });

      $(reportDiv).append('<div class="report"><a rel="nofollow" href="javascript:void(0)">举报</a></div>');
  });

	Do = (typeof Do === 'undefined')? $ : Do;
	Do(function(){
      var reportDiv = "#link-report".concat("1321595");
      $("body").delegate(reportDiv, 'mouseenter mouseleave', function(e){

        switch (e.type) {
          case "mouseenter":
            $(this).find(".report").css('visibility', 'visible');
            break;
          case "mouseleave":
            $(this).find(".report").css('visibility', 'hidden');
            break;
        }
      });
      $(reportDiv).delegate(".report a", 'click', function(e){
          e.preventDefault();
          var auditUrl = "http://www.douban.com/misc/audit_report?url=",
              opt = "bulletin";
          var obj = $(e.target).closest(reportDiv);
          var id = obj.length != 0 ? obj.data("id") : undefined;
          var params = (opt&&id) ? '?'.concat(opt, '=', id) : '';
          var url = "http://site.douban.com/site/idrama/".concat(params);
          generate_report_dialog({report_url: url});
      });

      $(reportDiv).append('<div class="report"><a rel="nofollow" href="javascript:void(0)">举报</a></div>');
  });
