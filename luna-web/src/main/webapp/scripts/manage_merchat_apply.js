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
    return '<a class="name" href="' + Util.strFormat(Inter.getApiUrl().merchantDetail, [row.application_id]) + '">' + value + '</a>';
}

function statusFormatter(value, row, index) {
    var html = "";
    switch (value) {
        case '1':
            html = '<label style="color:green">' + lunaConfig.merchantAuditStatus[value] + '</label>';
            break;
        case '2':
            html = '<label >' + lunaConfig.merchantAuditStatus[value] + '</label>';
            break;
        case '3':
            html = '<label style="color:red">' + lunaConfig.merchantAuditStatus[value] + '</label>';
            break;
    }

    return html;
}
window.operationEvents = {
    'click .search': function (e, value, row, index) {
        permissionSetting(row.wjnm, row.province_id, row.city_id, row.category_id, row.region_id);
    }
}

function queryParams(params) {
    //alert(JSON.stringify(params));
    return {
        limit: params.limit,
        offset: params.offset,
        sort: params.sort,
        order: params.order
    }
};