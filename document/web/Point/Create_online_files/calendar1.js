$.fn.extend({
    blur_hide:function(){
        var s=this,h=function(){return false};
        s.mousedown(h)
        $(document.body).mousedown(function(){
            s.hide().unbind('mousedown',h)
            $(document.body).unbind('mousedown',arguments.callee);
        })
        return this;
    }
});

var popUpCal = {
    selectedMonth: new Date().getMonth(),   // 0-11
    selectedYear: new Date().getFullYear(), // 4-digit year
    selectedDay: new Date().getDate(),
    today : new Date().getDate(),
    thisMonth : new Date().getMonth(),
    thisYear : new Date().getFullYear(),
    calendarId: 'calendarDiv',
    inputClass: 'calendarSelectDate',
        init: function () {
        var x = $('.'+popUpCal.inputClass);
        var y = $('#'+popUpCal.calendarId)[0];
        // set the calendar position based on the input position
        $('.'+popUpCal.inputClass).each(function(){
            if(/inline/.test(this.className)){
                setPos(this, y);
                popUpCal.linkTo = window.location.pathname + '?p=';
                $(y).show();
                popUpCal.drawCalendar(this); 
                popUpCal.setupLinks(this);
            }
            else if(/link/.test(this.className)){
                $(this).click(function(){
                    setPos(this, y,true);
                    popUpCal.linkTo = "/event/";
                    if(/events/.test(window.location.pathname)){
                        try{
                          loc = window.location.pathname.split("/location/")[1].split('/')[0];
                        } catch(e) {
                          loc = window.location.host.substring(0, window.location.host.indexOf('.'));
                        }
                        path = window.location.pathname;
                        if(path.indexOf('location') > 0){
                            popUpCal.linkTo = "/location/" + loc + "/events/";
                        } else {
                            popUpCal.linkTo = '/events/';
                        }
                    }
                    $(y).show().blur_hide();
                    popUpCal.drawCalendar(this); 
                    popUpCal.setupLinks(this);
                });
            }else{
                $(this).focus(function(){
                    setPos(this, y); 
                    $(y).show().blur_hide();
                    popUpCal.drawCalendar(this); 
                    popUpCal.setupLinks(this);
                })
            }
        })
    },
    
        drawCalendar: function (inputObj) {
        if($(inputObj).attr('rev')){
            var _ = $(inputObj).attr('rev').split('-');
            popUpCal.today = _[2];
            popUpCal.selectedMonth = popUpCal.thisMonth = _[1] - 1;
            popUpCal.selectedYear = popUpCal.thisYear = _[0];
            $(inputObj).attr('rev','');
        }
        var html = '';
        //html = '<a id="closeCalender">关闭</a>';
        html += '<table cellpadding="0" cellspacing="0" id="linksTable"><tr>';
        html += '   <td><a id="prevMonth"><< 上月</a></td>';
        html += '   <td><a id="nextMonth">下月 >></a></td>';
        html += '</tr></table>';
        html += '<table id="calendar" cellpadding="0" cellspacing="0"><tr>';
        html += '<th colspan="7" class="calendarHeader">'+getMonthName(parseInt(popUpCal.selectedMonth))+' '+popUpCal.selectedYear+'</th>';
        html += '</tr><tr class="weekDaysTitleRow">';
        var weekDays = new Array('日','一','二','三','四','五','六');
        for (var j=0; j<weekDays.length; j++) {
            html += '<td>'+weekDays[j]+'</td>';
        }
        var daysInMonth = getDaysInMonth(popUpCal.selectedYear, popUpCal.selectedMonth);
        var startDay = getFirstDayofMonth(popUpCal.selectedYear, popUpCal.selectedMonth);
        var numRows = 0;
        var printDate = 1;
        if (startDay != 7) {
            numRows = Math.ceil(((startDay+1)+(daysInMonth))/7); // calculate the number of rows to generate
        }
        
        // calculate number of days before calendar starts
        if (startDay != 7) {
            var noPrintDays = startDay + 1; 
        } else {
            var noPrintDays = 0; // if sunday print right away  
        }
        
        for (var e=0; e<numRows; e++) {
            html += '<tr class="weekDaysRow">';
            // create calendar days
            for (var f=0; f<7; f++) {
                if ( (printDate == popUpCal.today) 
                     && (popUpCal.selectedYear == popUpCal.thisYear) 
                     && (popUpCal.selectedMonth == popUpCal.thisMonth) 
                     && (noPrintDays == 0)) {
                    html += '<td id="today">';
                } else {
                    html += '<td class="weekDaysCell">';
                }
                if (noPrintDays == 0) {
                    if (printDate <= daysInMonth) {
                        html += '<a>'+printDate+'</a>';
                    }
                    printDate++;
                }
                html += '</td>';
                if(noPrintDays > 0) noPrintDays--;
            }
            html += '</tr>';
        }
        html += '</table>';
        
        // add calendar to element to calendar Div
        var calendarDiv = $('#'+popUpCal.calendarId)[0];
        calendarDiv.innerHTML = html;
       
        $('#prevMonth').click(function(){
            popUpCal.selectedMonth--;
            if(popUpCal.selectedMonth < 0) {
                popUpCal.selectedMonth = 11;
                popUpCal.selectedYear--;
            }
            popUpCal.drawCalendar(inputObj); 
            popUpCal.setupLinks(inputObj);
        });
        $('#nextMonth').click(function(){
            popUpCal.selectedMonth++;
            if (popUpCal.selectedMonth > 11) {
                popUpCal.selectedMonth = 0;
                popUpCal.selectedYear++;
            }
            popUpCal.drawCalendar(inputObj); 
            popUpCal.setupLinks(inputObj);
        });
        
    },
    
    setupLinks: function (inputObj) {
        var x = $('a','#calendar');
        for (var i=0; i<x.length; i++) {
            x[i].onclick = function () {
                popUpCal.selectedDay = this.innerHTML;
                if(popUpCal.linkTo == undefined){
                    document.getElementById(popUpCal.calendarId).style.display = 'none';
                    inputObj.value = formatDate(popUpCal.selectedDay, popUpCal.selectedMonth, popUpCal.selectedYear);
                }else{
                    if(/event|events|location/.test(popUpCal.linkTo)){
                        location.href = popUpCal.linkTo+(compactDate2(popUpCal.selectedDay, popUpCal.selectedMonth, popUpCal.selectedYear)+js_event_type);
                    }else{
                        location.href = popUpCal.linkTo+(compactDate(popUpCal.selectedDay, popUpCal.selectedMonth, popUpCal.selectedYear));
                    }
                }

            }
        }
    }
    
}
$(function(){popUpCal.init();});

function formatDate(Day, Month, Year) {
    Month++; 
    if (Month <10) Month = '0'+Month; 
    if (Day < 10) Day = '0'+Day; 
    var dateString = Year+'-'+Month+'-'+Day
    return dateString;
}

function compactDate(Day, Month, Year) {
    Month++;
    if (Month <10) Month = '0'+Month;
    if (Day < 10) Day = '0'+Day;
    
    return '' + Year + Month + Day
}



function compactDate2(Day, Month, Year) {
    Month++;
    if (Month <10) Month = '0'+Month;
    if (Day < 10) Day = '0'+Day;
    
    return '' + Year + '-' + Month + '-' + Day
}




function getMonthName(month) {
    var monthNames = new Array('一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月');
    return monthNames[month];
}

function getDayName(day) {
    var dayNames = new Array('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday')
    return dayNames[day];
}

function getDaysInMonth(year, month) {
    return 32 - new Date(year, month, 32).getDate();
}

function getFirstDayofMonth(year, month) {
    var day;
    day = new Date(year, month, 0).getDay();
    return day;
}

/* Position Functions */
function setPos(targetObj,moveObj,link) {
    var coors = link? $(targetObj).offset():$(targetObj).position();
    moveObj.style.position = 'absolute';
    moveObj.style.top = coors.top+(link?0:20) + 'px';
    moveObj.style.left = coors.left+(link?55:0) + 'px';
}


