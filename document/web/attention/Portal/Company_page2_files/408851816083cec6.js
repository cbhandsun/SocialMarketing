
    /* for room nav */
    Do(function () {
        $('.nav-items .opt > a').click(function (e) {
            // 不显示管理菜单
            if (e.target.id === 'admin-icon') {
              return;
            }
            var self = this;
            $(this).next().show().blur_hide();
            if (!$('ul', this).is(':hidden')) {
                if (roomHoverColor) {
                    $(this).css('background-color', roomHoverColor);
                }
                $(this).addClass('admin-icon-active');
            }
            $(document.body).click(function (e) {
                if (e.target.id !== 'admin-icon') {
                    $(self).removeAttr('class');
                }
                if (e.target.id !== 'more-icon' && roomHoverColor) {
                    $(self).removeAttr('style');
                }
            });
            e.preventDefault();
        });
    });

    Do.add('dshare', {path: 'http://img3.douban.com/js/packed_dshare3165349252.js', type: 'js', requires:['dialog']});
    Do('dshare', function () {
        $('#share-site').delegate(".shuo", "click", function(){
            $("#share-site").find(".lnk-sharing").click();
            return false;
        })
    });
    Do('dialog', function () {
        var srcNoPic = 'http://img3.douban.com/view/site/large/public/4d7ca2ccf223f8d.jpg',
            srcAddPic = 'http://img3.douban.com/pics/site/icon_default_large_hover.png';

        $('#no-pic').hover(
            function () { $('img', this).attr('src', srcAddPic); },
            function () { $('img', this).attr('src', srcNoPic); }
        ).click(function (e) {
            e.preventDefault();
            location.href = '/' + $('body').attr('id') + '/admin/icon';
        });

        /* like site */
        var site_is_commercial = false;
        $('#like').click(function (e) {
            e.preventDefault();
            var site_id = $("body").attr('id');
            var followed = $('#followed').val();
            $.post_withck(
                '/j/site/'+site_id+'/like',
                function (o) {
                    if (!site_is_commercial && followed == 0){
                        var dlg = dui.Dialog({
                            width: 300,
                            url: '/j/site/'+site_id+'/pop_like_form',
                            callback: function(e, o) {
                                o.setTitle('我喜欢这个小站');
                                $('#follow_confirm').click(function (e) {
                                    e.preventDefault();
                                    location.reload(1);
                                    dlg.close();
                                });
                            }
                        }).open();
                        dlg.node.find('.dui-dialog-close').click(function(){
                            location.reload(1);
                        });
                    } else {
                        location.reload(1);
                    }

                }
            );
        });

        /* cancel like*/
        $('#unlike').click(function (e) {
            e.preventDefault();
            var site_id = $("body").attr('id');
            var followed = $('#followed').val();
            $.post_withck(
                '/j/site/'+site_id+'/unlike',
                function (o) {
                    if (site_is_commercial){
                        $.post_withck(
                            '/j/site/'+site_id+'/unfollow',
                            function (o) {
                                location.reload();
                        });

                    } else {
                    if (followed == 1){
                        var dlg = dui.Dialog({
                            width: 300,
                            url: '/j/site/'+site_id+'/pop_unlike_form',
                            callback: function(e, o) {
                                o.setTitle('取消喜欢这个小站');
                                $('#unfollow_submit').click(function (e) {
                                    e.preventDefault();
                                    var url='/j/site/'+site_id;
                                    if($('#un_follow').attr('checked') == true){
                                        url += '/unfollow';
                                    }
                                    else {
                                        location.reload(1);
                                    }
                                    $.post_withck(
                                      url,
                                      function (o) {
                                          location.reload(1);
                                    });
                                    dlg.close();
                                });
                            }
                        }).open();
                       dlg.node.find('.dui-dialog-close').click(function(){
                           location.reload(1);
                       });
                     }
                    else {
                        location.reload();
                     }
                   }
           });
        });
    });
