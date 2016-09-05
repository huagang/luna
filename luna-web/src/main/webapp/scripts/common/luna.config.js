'use strict';
/**
 * @description: luna平台前端的配置文件
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-27
 */

var lunaConfig = function () {
    return {
        "host": '',
        "imghost": "http://cdn.visualbusiness.cn/public/vb",
        'microPanoType': {
            '0': '基础版',
            '1': '开发版',
            '2': '数据版',
        },
        'panoTypeObj': {
            '1': '单场景点',
            '2': '相册',
            '3': '自定义',
        },
        'poiLang': [{ id: 'zh', name: "中文" }, { id: 'en', name: "英文" }],
        'merchantAuditStatus': { 0: '待审批', 1: '已同意', 2: '已拒绝' },
        'accountType': { 0: '个人账户', 1: '对公账户' },
        'panoLang': [{ id: 'zh', name: "中文" }, { id: 'en', name: "英文" }],
        'menuTabIcon': [{
            name: '文字',
            code: 'text',
            type: 'text',
        }, {
                name: '概况',
                code: 'profile',
                type: 'default',
            }, {
                name: '交通',
                code: 'traffic',
                type: 'default',
            }, {
                name: '名人',
                code: 'celebrity',
                type: 'default',
            }, {
                name: '民族',
                code: 'nation',
                type: 'default',
            }, {
                name: '景点',
                code: 'attraction',
                type: 'default',
            }, {
                name: '食物',
                code: 'delicacy',
                type: 'default',
            }, {
                name: '酒店',
                code: 'lodge',
                type: 'default',
            }, {
                name: '活动',
                code: 'huodong',
                type: 'default',
            }, {
                name: '溯源',
                code: 'suyuan',
                type: 'default',
            }, {
                name: '特产',
                code: 'techan',
                type: 'default',
            }, {
                name: '文学',
                code: 'wenxue',
                type: 'default',
            }, {
                name: '沿革',
                code: 'yange',
                type: 'default',
            }, {
                name: '遗迹',
                code: 'yiji',
                type: 'default',
            }, {
                name: '自然环境',
                code: 'ziranhuanjing',
                type: 'default',
            }, {
                name: '地理特征',
                code: 'dilitezheng',
                type: 'default',
            }, {
                name: '风情',
                code: 'fengqing',
                type: 'default',
            }, {
                name: '长江三峡',
                code: 'changjiangsanxia',
                type: 'default',
            }, {
                name: '世界遗产',
                code: 'shijieyichan',
                type: 'default',
            }, {
                name: '渝西走廊',
                code: 'yuxizoulang',
                type: 'default',
            }, {
                name: '邮轮游艇',
                code: 'youlunyouting',
                type: 'default',
            }, {
                name: '温泉之都',
                code: 'wenquanzhidu',
                type: 'default',
            }, {
                name: '山水都市',
                code: 'shanshuidushi',
                type: 'default',
            }, {
                name: '美丽乡村',
                code: 'meilixiangcun',
                type: 'default',
            }, {
                name: '红岩联线',
                code: 'hongyanlianxian',
                type: 'default',
            }, {
                name: '乌江画廊',
                code: 'wujianghualang',
                type: 'default',
            }, {
                name: '客房',
                code: 'kefang',
                type: 'default',
            }, {
                name: '雅致',
                code: 'yazhi',
                type: 'default',
            }, {
                name: '尊享',
                code: 'zunxiang',
                type: 'default',
            }],
    };
} ();
