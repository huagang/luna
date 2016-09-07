/**
 * 直通车申请列表
 * Author：victor
 * date：2016-09-01
 */
$(function () {
    var id = 0,
        getRows = function () {
            var rows = [];
            for (var i = 0; i < 10; i++) {
                rows.push({
                    id: id
                });
                id++;
            }
            return rows;
        },
        $table = $('#table_business').bootstrapTable({
            data: getRows()
        });

    $('#condition_search').click(function () {
        $table.bootstrapTable('refresh');
    });
});

//名称格式化
function nameFormatter(value, row, index) {
    var html = '';
    if (row.appStatus == 0) {
        html = '<a class="name" href="' + Util.strFormat(Inter.getPageUrl().merchantDetail, [row.application_id]) + '">' + value + '</a>';
    } else {
        html = value;
    }
    return html;
}

function statusFormatter(value, row, index) {
    console.log(value);

    var html = "";
    switch (value) {
        case 0:
            html = '<label style="color:green">' + lunaConfig.merchantAuditStatus[value] + '</label>';
            break;
        case 1:
            html = '<label >' + lunaConfig.merchantAuditStatus[value] + '</label>';
            break;
        case 2:
            html = '<label style="color:red">' + lunaConfig.merchantAuditStatus[value] + '</label>';
            break;
    }

    return html;
}

function dateFormatter(value, row, index) {
    return dateFormat(value, 'yyyy-MM-dd hh:mm:ss');
}

function queryParams(params) {
    //alert(JSON.stringify(params));
    return {
        limit: params.limit,
        offset: params.offset,
        sort: params.sort,
        order: params.order
    };
}
function dateFormat(date, format) {
    var dateTime = new Date(date);
    var o = {
        "M+": dateTime.getMonth() + 1, //month 
        "d+": dateTime.getDate(), //day 
        "h+": dateTime.getHours(), //hour 
        "m+": dateTime.getMinutes(), //minute 
        "s+": dateTime.getSeconds(), //second 
        "q+": Math.floor((dateTime.getMonth() + 3) / 3), //quarter 
        "S": dateTime.getMilliseconds() //millisecond 
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (dateTime.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
