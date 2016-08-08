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
                poiInit:{url:context + "/data/poi",type:"GET"},
                poiDataImport:{url: context + '/data/poi/batch',type:'POST'},// Poi 批量数据导入功能
                poiCheckDelete:{url: context + '/data/poi/checkPoiCanBeDeleteOrNot?_id={0}',type:'GET'},//检查是否能够删除
                poiDelete:{url: context +  '/data/poi/{0}',type:'DELETE'},//poi删除功能
                poiAddPage:{url: context +  '/data/poi/addPage',type:'GET'},//poi删除功能
                poiEdit:{url:  context + '/data/poi/initEditPage?poiId={0}',type:'GET'},//poi编辑功能
                poiAddSave:{url:  context + '/data/poi',type:'POST'},//poi 创建成功
                poiEditSave:{url: context +  '/data/poi/edit',type:'POST'},//poi编辑成功
                ayncSearchSubTag:{url:  context + '/data/poi/subTag/{0}',type:'GET'}, //查找二级栏目的数据
                poiReadPage:{url:  context + '/data/poi/batch/initReadPage',type:"POST"},//poi只读页面
                poiBatchEdit:{url: context + "/data/poi/batch/initEditPage",type:"POST"},//poi 批量导入 编辑

                poiCheckForEnglish:{url:  context + '/data/poi/checkPoi?poiId={0}&lang={1}',type:'GET'},//英文poi检查
                poiConfirmArea:'',//确认poi所在区域是否正确
                
                //CRM管理
                crmInit:{url: context + "/content/crm",type:"GET"},//CRM管理页面
                crmAddPage:{url:  context + '/content/crm/initAddPage',type:'GET'},//Crm添加页面
                crmEditPage:{url:  context + '/content/crm/initEditPage?merchantId={0}', type:'GET'},// crm 编辑页面
                crmUserInfo:{url:  context + '/content/crm/merchantId/{0}'}, // crm 获取商户信息
                crmAddSave:{url:  context + "/content/crm",type:"POST"},//crm 创建商户
                crmEditSave:{url:  context + "/content/crm/edit",type:"POST"},//crm 编辑商户
                crmDelete:{url:  context + "/content/crm",type:"DELETE"},//crm 删除商户
                crmEnableUser:{url:  context + '/content/crm/{0}/enable',type:'PUT'},//开启商户
                crmDisableUser:{url:  context + '/content/crm/merchantId/{0}/disable',type:'PUT'},//关闭商户
                crmCheckName:{url:  context + '/content/crm/checkName',type:'GET'},//检查姓名

                // 商户注册
                merchantInit:{url:  context + "/common/merchant/registPage",type:"GET"}, // 注册初始页面
                merchantRegist:{url: context + "/common/merchant",type:"POST"},// 注册
                merchantCheckName:{url: context + "/common/merchant/checkName",type:"GET"}, //检查用户名
                merchantSuccess:{url: context + "common/merchant/successPage",type:"GET"}, //注册成功页面

                // 业务数据关系管理
                bizRelationInit:{url: context + "/content/businessRelation",type:"GET"}, // 管理页面初始化
                bizRelationBizTreeSearch:{url: context + "/content/businessRelation/businessTree/search",type:"GET"}, //业务关系树搜索
                bizRelationBizSearch:{url: context + "/content/businessRelation/business/search",type:"GET"}, //业务搜索
                bizRelationDelete:{url: context + "/content/businessRelation/businessId/{0}",type:"DELETE"}, //删除业务关系树
                bizRelationCreate:{url: context + "/content/businessRelation/businessId/{0}",type:"POST"}, //创建业务关系树
                bizRelationEditPage:{url: context + "/content/businessRelation/businessTree/{0}", type:"GET"}, // 业务关系树编辑页面
                bizRelationBizTreeView:{url: context + "/content/businessRelation/businessId/{0}", type:"GET"}, // 业务关系树视图
                bizRelationPoiSearch:{url: context + "/content/businessRelation/searchPois", type:"GET"}, // 搜索POI
                bizRelationBizTreeEdit:{url: context + "/content/businessRelation/saveBusinessTree", type:"PUT"}, // 保存编辑的业务关系树




                // 商户
                searchMerchat:{url:  context + '/content/business/searchMerchant',type:''}, // 搜索商户接口 get 方法

                // 业务
                business: {url: context + '/content/business',type:''}, // 业务相关 POST PUT
                getBusinessList: {url: context + '/content/business/selectForEdit', type:'GET'},
                getBusinessListForEdit: {url: context + '/content/business/selectForEdit?unique_id={0}', type: 'GET'},
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

                // 用户注册
                registrationPage:{url:"/comomon/registration/token/{0}",type:"GET"},//注册页面
                registrationRegist:{url:"/comomon/registration",type:"POST"}, // 注册

                // 权限
                updateUserAuth: {url: '', type: 'PUT'}, // 更新用户权限
                inviteUsers: {url: context + '/platform/user', type: 'POST'}, // 邀请用户
                inviteAuth: {url: context + '/platform/user/invite?data', type: 'GET'}, // 获取邀请权限
                fetchUserAuthData: {url: context + '/platform/user/invite/{0}?edit', type: 'GET'},

                // 省市县\分类下拉列表
                pullDownProvinces:{url:"/common/pulldown/provinces",type:"GET"}, //省
                pullDownCitys:{url:"/common/pulldown/citys",type:"GET"}, //市
                pullDownCounties:{url:"/common/pulldown/counties",type:"GET"}, //县
                pullDownCategorys:{url:"/common/pulldown/categorys",type:"GET"}, //分类
                pullDownZoneIds:{url:"/common/pulldown/ZoneIds",type:"GET"} //分类
            };
        }
    };

}();
