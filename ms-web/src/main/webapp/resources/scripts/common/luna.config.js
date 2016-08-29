'use strict';
/**
 * @description: luna平台前端的配置文件
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-27
 */

var lunaConfig = function () {
    return {
        "host": '/luna-web',
        "imghost": "http://cdn.visualbusiness.cn/public/vb",
        "poiAction": {
            "phone": {
                'zh': "联系电话",
                'en': 'Telephone'
            }, 'navigation': {
                'zh': '导航',
                'en': 'Navigation'
            }, 'nopano': {
                'zh': '全景<br>拍摄中',
                'en': 'Panorama<br/>coming soon',
            }, 'pano': {
                'zh': '看全景',
                'en': 'Panorama',
            }, 'detail': {
                'zh': '查看详情',
                'en': 'Detail',
            }, 'more': {
                'zh': '更多内容，敬请期待…',
                'en': 'More',
            }
        }
    };
} ();