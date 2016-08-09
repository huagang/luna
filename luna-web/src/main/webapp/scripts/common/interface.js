'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */

var Inter = function () {
    var context = window.context || "",
        apiContext = '';
        
    var curHost = function() {
        var host = window.location.host;
        if(/localhost/.test(host)){
            return 'local';
        }else if(/luna-test/.test(host)){
            return 'test';
        }else{
            return 'online';
        }
    };

    var apiHost = {
        'local':'http://localhost:8082/',
        'test':'http://luna-test.visualbusiness.cn/luna-api/',
        'online':'http://luna.visualbusiness.cn/luna-api/'
    };
    return {
        getApiUrl: function () {
            return {
                //选择业务的数据
                selectBusinessPage: '/content/business/select',//选择业务的页面 

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

                poiThumbnailUpload:{url:"/data/poi/thumbnail/upload",type:"POST"}, //poi缩略图上传
                poiAudioUpload:{url:"/data/poi/audio/upload",type:"POST"}, //poi音频上传
                poiVideoUpload:{url:"/data/poi/video/upload",type:"POST"}, //poi视频上传

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
                crmThumbnailUpload:{url:"/content/crm/thumbnail/upload",type:"POST"}, // 上传图片

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
                searchMerchat: { url: context + '/content/business/searchMerchant', type: 'GET' }, // 搜索商户接口 get 方法

                // 业务
                businessCreate: {url: context + '/content/business',type:'POST'}, // 业务相关 POST PUT
                businessUpdate: {url: context + '/content/business',type:'PUT'}, // 业务相关 POST PUT
                businessDelete: {url: context + '/content/business',type:'DELETE'}, // 业务相关 POST PUT
                getBusinessList: {url: context + '/content/business/selectForEdit', type:'GET'},
                getBusinessListForEdit: {url: context + '/content/business/selectForEdit?unique_id={0}', type: 'GET'},
            	

                //栏目管理
                columnCreate: { url: '/content/column', type: 'POST' },
                columnUpdate: { url: '/content/column/{0}', type: 'PUT' },
                columnDelete: { url: '/content/column/{0}', type: 'DELETE' },

                //文章操作
                article: { url: context + '/content/article', type: '' },

                articleCreatePage: { url: '/content/article?create&business_id={0}', type: 'GET' },
                articleDelete: { url: '/content/article/{0}', type: 'DELETE' },
                articleSearch: { url: '/content/article/{0}', type: 'GET' },

                articleEditPage: { url: '/content/article/{0}', type: 'GET' }, //编辑文章界面
                articleEditData: { url: '/content/article/{0}?data', type: 'GET' }, //编辑文章界面的数据
                articleCreate: { url: '/content/article', type: 'POST' }, //保存文章
                articleUpdate: { url: '/content/article/{0}', type: 'POST' }, //更新文章
                articlePublish:{ url: '/content/article/publish/{0}', type: 'PUT' }, //更新发布


                uploadImageInArtcle: { url: context + "/add_poi.do?method=upload_thumbnail", type: '' },//上传图片
                uploadVideoInArtcle: { url: context + "/add_poi.do?method=upload_video", type: '' },//上传视频

                //微景展管理页面操作
                appCreate: { url: context + '/content/app/', type: 'POST' },  //创建微景展
                appUpdate: { url: context + '/content/app/{0}', type: 'PUT' },  //更新微景展信息
                appCopy: { url: context + '/content/app/copy', type: 'POST' }, //复用app
                appDelete:{ url: '/content/app/{0}', type: 'DELETE' }, //删除微景展
                appEditPage:{ url: '/content/app/{0}', type: 'GET' }, //编辑微景展
                appPropInfo: {url: context + '/content/app/setting/{0}', type:'GET'},  // 获取微景展属性信息
                appPropUpdate: {url: context + '/content/app/setting/{0}', type:'POST'}, // 更新微景展属性信息

                //微景展编辑页面操作
                getAppSummary :{url: '/content/app/pages/summary?app_id={0}', type: 'GET'}, //获取微景展概览数据
                appGetSetting :{url: '/content/app/setting/{0}', type: 'GET'}, //编辑微景展页面的操作
                appCreatePage :{url: '/content/app/page', type: 'POST'}, //微景展页面
                appModifyName :{url: '/content/app/page/name/{0}', type: 'POST'}, //修改页面的名字
                appGetPageDetail :{url: '/content/app/page/{0}', type: 'GET'}, //获取单页面的详情
                appDeletePage :{url: '/content/app/page/{0}', type: 'DELETE'}, //删除单页面的数据
                appUpdatePageOrder :{url: '/content/app/page/order', type: 'PUT'}, //更新页面的顺序
                appSaveSetting :{url: '/content/app/setting/{0}', type: 'POST'}, //保存页面设置

                // 业务搜索
                searchBusiness: { url: context + '/manage/app.do?method=search_business', type: '' }, //搜索业务请求              

                // 上传路径
                uploadPath: { url: context + '/common/upload', type: 'POST' }, //统一上传接口
                uploadPic: { url: context + '/common/uploadPic', type: 'POST' }, //图片上传接口

                login: { url: context + '/common/login', type: 'POST' },  //登录提交接口

                // 用户注册
                registrationPage: { url: "/comomon/registration/token/{0}", type: "GET" },//注册页面
                registrationRegist: { url: "/comomon/registration", type: "POST" }, // 注册

                // 权限
                updateUserAuth: {url: context + '/platform/user/{0}', type: 'PUT'}, // 更新用户权限
                inviteUsers: {url: context + '/platform/user', type: 'POST'}, // 邀请用户
                inviteAuth: {url: context + '/platform/user/invite?data', type: 'GET'}, // 获取邀请权限
                fetchUserAuthData: {url: context + '/platform/user/invite/{0}?edit', type: 'GET'},
                delUser: {url: context + '/platform/user/{0}', type: 'DELETE'},

                // 省市县\分类下拉列表
                pullDownProvinces:{url:"/common/pulldown/provinces",type:"GET"}, //省
                pullDownCitys:{url:"/common/pulldown/citys",type:"GET"}, //市
                pullDownCounties:{url:"/common/pulldown/counties",type:"GET"}, //县
                pullDownCategorys:{url:"/common/pulldown/categorys",type:"GET"}, //分类
                pullDownZoneIds:{url:"/common/pulldown/zoneIds",type:"GET"}, //分类

                // 类别管理
                cateInit:{url:"/platform/category",type:"GET"}, //初始化
                cateCreate:{url:"/platform/category",type:"POST"}, // 创建
                cateUpdate:{url:"/platform/category",type:"PUT"}, // 更新
                cateDelete:{url:"/platform/category/{0}",type:"DELETE"},//删除


                // 组权限管理
                updateAuthoritySet: {url: context + '/platform/authority/{0}', type: 'PUT'},

                //文章列表接口
                articleListApi: apiHost[curHost()]+'article/businessId/{0}',

                //文章栏目列表
                articleColunmu: context + '/manage/article.do?method=read_column&business_id={0}',
                articleListByBid: apiHost[curHost()]+'article/businessId/{0}', //通过业务ID获取
                articleListByBidAndCid: apiHost[curHost()]+'article/businessId/{0}/columnIds/{1}', //通过业务ID和栏目Id获取
                firstPoiByBid: apiHost[curHost()]+'servicepoi.do?method=getPoisInFirstLevel&business_id={0}&lang=zh&fields=poi_name,category,boundary', //通过业务ID
                poiTypeListByBidAndFPoi: apiHost[curHost()]+'servicepoi.do?method=getCtgrsByBizIdAndPoiId&business_id={0}&poi_id={1}', //通过业务id和poiId获取
                poiListByBidAndFPoi: apiHost[curHost()]+'servicepoi.do?method=getPoisByBizIdAndPoiId&business_id={0}&poi_id={1}&lang=zh&fields=poi_name,other_name', //获取业务关系树 一层结构下所有POI数据接口
                poiListByBidAndFPoiAndPoiTyep: apiHost[curHost()]+'servicepoi.do?method=getPoisByBizIdAndPoiIdAndCtgrId&business_id={0}&poi_id={1}&category_id={2}&fields=poi_name&lang=zh', //获取业务关系树 多个一级类别下的数据接口
                
                //全景路径接口
                singlePano:'http://pano.visualbusiness.cn/single/index.html?panoId={0}',    //单点全景路径
                multiplyPano:'http://pano.visualbusiness.cn/album/index.html?albumId={0}',  //相册全景路径
                customerPano:'http://data.pano.visualbusiness.cn/rest/album/view/{0}',  //自定义全景

            };
        }
    };

} ();
