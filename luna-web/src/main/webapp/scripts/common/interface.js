'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */

var Inter = function() {
    var context = window.context || "";
    return {
        getApiUrl: function() {
            return {
                //数据管理
                poiInit:{url:"/data/poi",type:"GET"},
                poiDataImport:{url: '/data/poi/batch',type:'POST'},// Poi 批量数据导入功能
                poiCheckDelete:{url: '/data/poi/checkPoiCanBeDeleteOrNot?_id={0}',type:'GET'},//检查是否能够删除
                poiDelete:{url: '/data/poi/{0}',type:'DELETE'},//poi删除功能
                poiEdit:{url: '/data/poi/initEditPage?poiId={0}',type:'GET'},//poi编辑功能
                poiAddSave:{url: '/data/poi',type:'POST'},//poi 创建成功
                poiEditSave:{url: '/data/poi/edit',type:'POST'},//poi编辑成功
                ayncSearchSubTag:{url: '/data/poi/subTag/{0}',type:'GET'}, //查找二级栏目的数据
                poiReadPage:{url: '/data/poi/batch/initReadPage',type:"POST"},//poi只读页面
                poiBatchEdit:{url:"/data/poi/batch/initEditPage",type:"POST"},//poi 批量导入 编辑

                poiCheckForEnglish:{url: '/data/poi/checkPoi?poiId={0}&lang={1}',type:'GET'},//英文poi检查
                poiConfirmArea:'',//确认poi所在区域是否正确
                
                //CRM管理
                crmAddPage:{url: '/content/crm/initAddPage',type:'GET'},//Crm添加页面
                initEditPage:{url: '/content/crm/initEditPage?merchant_id={0}',type:'GET'}, //请求CRM编辑界面
                crmEnableUser:{url: '/content/crm/open',type:'PUT'},//开启商户
                crmDisableUser:{url: '/content/crm/close',type:'PUT'},//关闭商户
                crmCheckName:{url: '/content/crm/checkName',type:'GET'},//检查姓名



                // 商户
                searchMerchat:{url:  context + '/content/business/searchMerchant',type:''}, // 搜索商户接口 get 方法

                // 业务
                business: {url: context + '/content/business',type:''}, // 业务相关 POST PUT
                getBusinessList:{url:  context + '/common/business',type:''}, // 获取该用户能够选择的业务列表

            	//文章操作
                article: {url: context + '/content/article',type:''},

                createArticle: {url: context+'/manage/article.do?method=create_article',type:''}, //保存文章
                updateArticle: {url: context+'/manage/article.do?method=update_article',type:''},
                publishArticle: {url: context+'/manage/article.do?method=publish_article',type:''}, //发布文章
                readArticle: {url: context + "/manage/article.do?method=read_article",type:''}, //读取文章数据
                deleteArticle: {url: context + "/manage/article.do?method=delete_article",type:''},//删除文章

                //编辑器上传图片
                uploadImageInArtcle:{url:  context + "/add_poi.do?method=upload_thumbnail",type:''},//上传图片
                uploadVideoInArtcle: {url: context + "/add_poi.do?method=upload_video",type:''},//上传视频
                
                //微景展操作
                createApp: {url: context + '/manage/app.do?method=create_app',type:''},  //创建微景展
                updateApp: {url: context + '/manage/app.do?method=update_app',type:''},  //更新微景展信息
                copyApp:{url:  context + '/manage/app.do?method=copy_app',type:''}, //复用app
                
                // 业务搜索
                searchBusiness: {url: context+'/manage/app.do?method=search_business',type:''}, //搜索业务请求              

                // 上传路径
                uploadPath: {url: context+'/uploadCtrl.do?method=uploadFile2Cloud',type:''}, //统一上传接口

                login: {url: context + '/common/login', type:'POST'},  //登录提交接口
            };
        }
    };

}();
