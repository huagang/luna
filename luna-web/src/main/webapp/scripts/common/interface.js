'use strict';
/*global jQuery, $*/

/**
 * @description: 接口
 * @author: duyutao(452661976@qq.com)
 * @update:2016-6-22
 */
var Inter = function () {
    var host = "/luna-web";
    var context = window.context || "/luna-web",
        apiContext = '';

    var curHost = function () {
        var host = window.location.host;
        if (/localhost/.test(host)) {
            return 'local';
        } else if (/luna-test/.test(host)) {
            return 'test';
        } else {
            return 'current';
        }
    };

    var apiHost = {
        'local': 'http://localhost:8082/',
        'test': 'http://' + window.location.host + '/luna-api/',
        'current': 'http://' + window.location.host + '/luna-api/'
    };

    var lunaEditor = {
        'local': 'http://simon-test.visualbusiness.cn',
        'test': 'http://simon-test.visualbusiness.cn',
        'current': 'http://webapp.visualbusiness.cn '
    };

    var curApiHost = curHost();
    if (curApiHost === 'others') {
        apiContext = 'http://' + location.host + '/luna-api/';
    } else {
        apiContext = apiHost[curHost()];
    }
    apiContext = apiHost[curHost()];

    return {
        context: context,
        getPageUrl: function () {
            return {
                home: context + '/index',
                basicAppEdit: context + '/content/app/{0}?business_id={1}',
                devAppEdit: lunaEditor[curHost()] + '/app/{0}?appId={1}&token={2}',
                dataAppEdit: context + '/content/app/farm/{0}?business_id={1}',
                manageUser: context + '/platform/user', //
                routeConfig: context + '/content/route/configuration/{0}',
                addPoi: context + '/data/poi/addPage',
                editPoi: context + '/data/poi/initEditPage?poiId={0}',
                manageRouter: context + '/content/route',
                merchantApply: context + '/merchant/tradeApplication', //商户申请
                merchantDetail: context + '/platform/message/page/{0}', //商户申请审核
                messagePage: context + '/platform/message', //消息管理
            };
        },
        getApiUrl: function () {
            return {
                //选择业务的数据
                selectBusinessPage: context + '/common/business/select', //选择业务的页面 

                // token
                appToken: {
                    url: context + '/content/app/token',
                    type: 'GET'
                },

                //数据管理
                poiInit: {
                    url: context + "/data/poi",
                    type: "GET"
                },
                poiDataImport: {
                    url: context + '/data/poi/batch',
                    type: 'POST'
                }, // Poi 批量数据导入功能
                poiCheckDelete: {
                    url: context + '/data/poi/checkPoiCanBeDeleteOrNot?_id={0}',
                    type: 'GET'
                }, //检查是否能够删除
                poiDelete: {
                    url: context + '/data/poi/{0}?note={1}',
                    type: 'DELETE'
                }, //poi删除功能
                poiAddPage: {
                    url: context + '/data/poi/addPage',
                    type: 'GET'
                }, //poi删除功能
                poiEdit: {
                    url: context + '/data/poi/initEditPage?poiId={0}',
                    type: 'GET'
                }, //poi编辑功能
                poiAddSave: {
                    url: context + '/data/poi',
                    type: 'POST'
                }, //poi 创建成功
                poiEditSave: {
                    url: context + '/data/poi/edit',
                    type: 'POST'
                }, //poi编辑成功
                ayncSearchSubTag: {
                    url: context + '/data/poi/subTag/{0}',
                    type: 'GET'
                }, //查找二级栏目的数据
                poiReadPage: {
                    url: context + '/data/poi/batch/initReadPage',
                    type: "POST"
                }, //poi只读页面
                poiBatchEdit: {
                    url: context + "/data/poi/batch/initEditPage",
                    type: "POST"
                }, //poi 批量导入 编辑

                poiCheckForEnglish: {
                    url: context + '/data/poi/checkPoi',
                    type: 'POST'
                }, //英文poi检查
                poiConfirmArea: '', //确认poi所在区域是否正确

                poiThumbnailUpload: {
                    url: context + "/data/poi/thumbnail/upload",
                    type: "POST"
                }, //poi缩略图上传
                poiAudioUpload: {
                    url: context + "/data/poi/audio/upload",
                    type: "POST"
                }, //poi音频上传
                poiVideoUpload: {
                    url: context + "/data/poi/video/upload",
                    type: "POST"
                }, //poi视频上传
                //CRM管理
                crmInit: {
                    url: context + "/content/crm",
                    type: "GET"
                }, //CRM管理页面
                crmAddPage: {
                    url: context + '/content/crm/initAddPage',
                    type: 'GET'
                }, //Crm添加页面
                crmEditPage: {
                    url: context + '/content/crm/initEditPage?merchantId={0}',
                    type: 'GET'
                }, // crm 编辑页面
                crmUserInfo: {
                    url: context + '/content/crm/merchantId/{0}'
                }, // crm 获取商户信息
                crmAddSave: {
                    url: context + "/content/crm",
                    type: "POST"
                }, //crm 创建商户
                crmEditSave: {
                    url: context + "/content/crm/edit",
                    type: "POST"
                }, //crm 编辑商户
                crmDelete: {
                    url: context + "/content/crm",
                    type: "DELETE"
                }, //crm 删除商户
                crmEnableUser: {
                    url: context + '/content/crm/{0}/enable',
                    type: 'PUT'
                }, //开启商户
                crmDisableUser: {
                    url: context + '/content/crm/merchantId/{0}/disable',
                    type: 'PUT'
                }, //关闭商户
                crmCheckName: {
                    url: context + '/content/crm/checkName',
                    type: 'GET'
                }, //检查姓名

                // 商户注册
                merchantPicUpload: {
                    url: context + "/common/merchant/upload",
                    type: "POST"
                }, // 图片上传

                merchantInit: {
                    url: context + "/common/merchant/registPage",
                    type: "GET"
                }, // 注册初始页面
                merchantRegist: {
                    url: context + "/common/merchant",
                    type: "POST"
                }, // 注册
                merchantCheckName: {
                    url: context + "/common/merchant/checkName",
                    type: "GET"
                }, //检查用户名
                merchantSuccess: {
                    url: context + "/common/merchant/successPage",
                    type: "GET"
                }, //注册成功页面
                crmThumbnailUpload: {
                    url: context + "/content/crm/thumbnail/upload",
                    type: "POST"
                }, // 上传图片

                // 业务数据关系管理
                bizRelationExist: {
                    url: context + "/content/businessRelation/exist/{0}",
                    type: 'GET'
                },
                bizRelationInit: {
                    url: context + "/content/businessRelation",
                    type: "GET"
                }, // 管理页面初始化
                bizRelationBizTreeSearch: {
                    url: context + "/content/businessRelation/businessTree/search",
                    type: "GET"
                }, //业务关系树搜索
                bizRelationBizSearch: {
                    url: context + "/content/businessRelation/business/search",
                    type: "GET"
                }, //业务搜索
                bizRelationDelete: {
                    url: context + "/content/businessRelation/businessId/{0}",
                    type: "DELETE"
                }, //删除业务关系树
                bizRelationCreate: {
                    url: context + "/content/businessRelation/businessId/{0}",
                    type: "POST"
                }, //创建业务关系树
                bizRelationEditPage: {
                    url: context + "/content/businessRelation/businessTree/{0}",
                    type: "GET"
                }, // 业务关系树编辑页面
                bizRelationBizTreeView: {
                    url: context + "/content/businessRelation/businessId/{0}",
                    type: "GET"
                }, // 业务关系树视图
                bizRelationPoiSearch: {
                    url: context + "/content/businessRelation/searchPois",
                    type: "GET"
                }, // 搜索POI
                bizRelationBizTreeEdit: {
                    url: context + "/content/businessRelation/saveBusinessTree",
                    type: "PUT"
                }, // 保存编辑的业务关系树

                // 商户
                searchMerchat: {
                    url: context + '/content/business/searchMerchant',
                    type: 'GET'
                }, // 搜索商户接口 get 方法

                // 业务
                businessCreate: {
                    url: context + '/content/business',
                    type: 'POST'
                }, // 业务相关 POST PUT
                businessUpdate: {
                    url: context + '/content/business',
                    type: 'PUT'
                }, // 业务相关 POST PUT
                businessDelete: {
                    url: context + '/content/business',
                    type: 'DELETE'
                }, // 业务相关 POST PUT
                getBusinessList: {
                    url: context + '/common/business/selectForEdit',
                    type: 'GET'
                },
                getBusinessListForEdit: {
                    url: context + '/common/business/selectForEdit?unique_id={0}',
                    type: 'GET'
                },
                checkBusinessNameRepeat: {
                    url: context + '/content/business/businessName/check?business_name={0}&merchant_id={1}',
                    type: 'GET'
                },
                checkBusinessCodeRepeat: {
                    url: context + '/content/business/businessCode/check?business_code={0}&merchant_id={1}',
                    type: 'GET'
                },


                //栏目管理
                columnCreate: {
                    url: context + '/content/column',
                    type: 'POST'
                },
                columnUpdate: {
                    url: context + '/content/column/{0}',
                    type: 'PUT'
                },
                columnDelete: {
                    url: context + '/content/column/{0}',
                    type: 'DELETE'
                },

                //文章操作
                article: {
                    url: context + '/content/article',
                    type: ''
                },

                articleCreatePage: {
                    url: context + '/content/article?create&business_id={0}',
                    type: 'GET'
                },
                articleDelete: {
                    url: context + '/content/article/{0}',
                    type: 'DELETE'
                },
                articleSearch: {
                    url: context + '/content/article/{0}',
                    type: 'GET'
                },

                articleEditPage: {
                    url: context + '/content/article/{0}',
                    type: 'GET'
                }, //编辑文章界面
                articleEditData: {
                    url: context + '/content/article/{0}?data',
                    type: 'GET'
                }, //编辑文章界面的数据
                articleCreate: {
                    url: context + '/content/article',
                    type: 'POST'
                }, //保存文章
                articleUpdate: {
                    url: context + '/content/article/{0}',
                    type: 'POST'
                }, //更新文章
                articlePublish: {
                    url: context + '/content/article/publish/{0}',
                    type: 'PUT'
                }, //更新发布

                uploadImageInArtcle: {
                    url: context + "/add_poi.do?method=upload_thumbnail",
                    type: ''
                }, //上传图片
                uploadVideoInArtcle: {
                    url: context + "/add_poi.do?method=upload_video",
                    type: ''
                }, //上传视频

                //微景展管理页面操作
                appCreate: {
                    url: context + '/content/app/',
                    type: 'POST'
                }, //创建微景展
                appUpdate: {
                    url: context + '/content/app/{0}',
                    type: 'PUT'
                }, //更新微景展信息
                appCopy: {
                    url: context + '/content/app/copy',
                    type: 'POST'
                }, //复用app
                appDelete: {
                    url: context + '/content/app/{0}',
                    type: 'DELETE'
                }, //删除微景展
                appEditPage: {
                    url: context + '/content/app/{0}',
                    type: 'GET'
                }, //编辑微景展
                appPropInfo: {
                    url: context + '/content/app/setting/{0}',
                    type: 'GET'
                }, // 获取微景展属性信息
                appPropUpdate: {
                    url: context + '/content/app/setting/{0}',
                    type: 'POST'
                }, // 更新微景展属性信息

                //微景展编辑页面操作
                appSaveData: {
                    url: context + '/content/app/pages',
                    type: 'PUT'
                }, //保存页面数据
                getAppSummary: {
                    url: context + '/content/app/pages/summary?app_id={0}',
                    type: 'GET'
                }, //获取微景展概览数据
                appGetSetting: {
                    url: context + '/content/app/setting/{0}',
                    type: 'GET'
                }, //编辑微景展页面的操作
                appCreatePage: {
                    url: context + '/content/app/page',
                    type: 'POST'
                }, //复制微景展界面
                appCopyPage: {
                    url: context + '/content/app/page/copy',
                    type: 'POST'
                }, //微景展页面
                appModifyName: {
                    url: context + '/content/app/page/name/{0}',
                    type: 'PUT'
                }, //修改页面的名字
                appGetPageDetail: {
                    url: context + '/content/app/page/{0}',
                    type: 'GET'
                }, //获取单页面的详情
                appDeletePage: {
                    url: context + '/content/app/page/{0}',
                    type: 'DELETE'
                }, //删除单页面的数据
                appUpdatePageOrder: {
                    url: context + '/content/app/pages/order',
                    type: 'PUT'
                }, //更新页面的顺序
                appSaveSetting: {
                    url: context + '/content/app/setting/{0}',
                    type: 'POST'
                }, //保存页面设置
                appPreview: {
                    url: context + '/content/app/preview/{0}',
                    type: 'GET'
                }, //微景展预览界面
                appPublish: {
                    url: context + '/content/app/publish/{0}',
                    type: 'PUT'
                }, //微景展发布界面
                farmHouseFormInfo: {
                    url: context + '/content/app/farm/page/{0}',
                    type: 'GET'
                }, // 获取农家表单信息
                saveFarmHouseFormInfo: {
                    url: context + '/content/app/farm/{0}',
                    type: 'PUT'
                }, // 保存农家表单信息
                farmHousePreview: {
                    url: context + '/content/app/preview/{0}',
                    type: 'GET'
                }, // 获取预览二维码图片
                farmHousePublish: {
                    url: context + '/content/app/publish/{0}',
                    type: 'PUT'
                }, // 获取预览二维码图片

                // 业务搜索
                searchBusiness: {
                    url: context + '/manage/app.do?method=search_business',
                    type: ''
                }, //搜索业务请求              

                // 上传路径
                uploadPath: {
                    url: context + '/inner/upload',
                    type: 'POST'
                }, //统一上传接口
                uploadPic: {
                    url: context + '/inner/uploadPic',
                    type: 'POST'
                }, //图片上传接口
                // 上传路径
                uploadPicByUeditor: {
                    //url: context + '/inner/upload',
                    url: context + "/data/poi/thumbnail/upload",
                    type: 'POST'
                }, //统一上传接口
                uploadVideoByUeditor: {
                    //url: context + '/inner/uploadPic',
                    url: context + "/data/poi/video/upload",
                    type: 'POST'
                }, //图片上传接口


                login: {
                    url: context + '/common/login',
                    type: 'POST'
                }, //登录提交接口

                // 用户注册
                registrationPage: {
                    url: context + "/comomon/registration/token/{0}",
                    type: "GET"
                }, //注册页面
                registrationRegist: {
                    url: context + "/comomon/registration",
                    type: "POST"
                }, // 注册

                // 权限
                updateUserAuth: {
                    url: context + '/platform/user/{0}',
                    type: 'PUT'
                }, // 更新用户权限
                inviteUsers: {
                    url: context + '/platform/user',
                    type: 'POST'
                }, // 邀请用户
                inviteAuth: {
                    url: context + '/platform/user/invite?data',
                    type: 'GET'
                }, // 获取邀请权限
                fetchUserAuthData: {
                    url: context + '/platform/user/invite/{0}?edit',
                    type: 'GET'
                },
                delUser: {
                    url: context + '/platform/user/{0}',
                    type: 'DELETE'
                },

                // 省市县\分类下拉列表
                pullDownProvinces: {
                    url: context + "/common/pulldown/provinces",
                    type: "GET"
                }, //省
                pullDownCitys: {
                    url: context + "/common/pulldown/citys",
                    type: "GET"
                }, //市
                pullDownCounties: {
                    url: context + "/common/pulldown/counties",
                    type: "GET"
                }, //县
                pullDownCategorys: {
                    url: context + "/common/pulldown/categorys",
                    type: "GET"
                }, //分类
                pullDownZoneIds: {
                    url: context + "/common/pulldown/zoneIds",
                    type: "GET"
                }, //分类

                // 类别管理
                cateInit: {
                    url: context + "/platform/category",
                    type: "GET"
                }, //初始化
                cateCreate: {
                    url: context + "/platform/category",
                    type: "POST"
                }, // 创建
                cateUpdate: {
                    url: context + "/platform/category",
                    type: "PUT"
                }, // 更新
                cateDelete: {
                    url: context + "/platform/category/{0}",
                    type: "DELETE"
                }, //删除

                // 组权限管理
                updateAuthoritySet: {
                    url: context + '/platform/authority/{0}',
                    type: 'PUT'
                },

                //文章列表接口
                articleListApi: apiContext + 'article/businessId/{0}',

                //文章栏目列表
                articleColumn: {
                    url: context + '/content/column/listByBusiness/{0}',
                    type: 'PUT'
                },
                articleListByBid: apiContext + 'article/businessId/{0}', //通过业务ID获取
                articleListByBidAndCid: apiContext + 'article/businessId/{0}/columnIds/{1}', //通过业务ID和栏目Id获取

                // poi
                firstPoiByBid: apiContext + 'servicepoi.do?method=getPoisInFirstLevel&business_id={0}&lang=zh&fields=poi_name,category,boundary', //通过业务ID
                poiTypeListByBidAndFPoi: apiContext + 'servicepoi.do?method=getCtgrsByBizIdAndPoiId&business_id={0}&poi_id={1}', //通过业务id和poiId获取
                poiListByBidAndFPoi: apiContext + 'servicepoi.do?method=getPoisByBizIdAndPoiId&business_id={0}&poi_id={1}', //获取业务关系树 一层结构下所有POI数据接口
                poiListByBidAndFPoiAndPoiTyep: apiContext + 'servicepoi.do?method=getPoisByBizIdAndPoiIdAndCtgrId&business_id={0}&poi_id={1}&category_id={2}', //获取业务关系树 多个一级类别下的数据接口
                poiDetail: apiContext + 'servicepoi.do?method=getPoiById&poi_id={0}&lang=zh',
                poiFilter: {
                    url: apiContext + 'servicepoi.do?method=retrievePois&type={0}&filterName={1}&limit={2}&lang={3}',
                    type: 'GET'
                },

                //全景路径接口
                singlePano: 'http://pano.visualbusiness.cn/single/index.html?panoId={0}', //单点全景路径
                multiplyPano: 'http://pano.visualbusiness.cn/album/index.html?albumId={0}', //相册全景路径
                customerPano: 'http://data.pano.visualbusiness.cn/rest/album/view/{0}', //自定义全景

                searchPano: {
                    url: 'http://data.pano.visualbusiness.cn/rest/pano/search?q={0}&fromPage={1}&size={2}&from={3}&project={4}',
                    type: 'GET'
                }, // 通过关键字搜索全景
                searchAlbum: {
                    url: 'http://data.pano.visualbusiness.cn/rest/album/search?q={0}&fromPage={1}&size={2}&from={3}',
                    type: 'GET'
                },


                //zclip路径
                zclipSWFPath: context + "/plugins/jquery.zclip/ZeroClipboard.swf",

                // 线路管理
                createRoute: {
                    url: context + '/content/route',
                    type: 'POST'
                }, //创建路线
                editRoute: {
                    url: context + '/content/route/{0}',
                    type: 'PUT'
                }, //编辑路线
                getRouteList: {
                    url: context + '/content/route/search',
                    type: 'GET'
                }, //获取线路列表
                delRoute: {
                    url: context + '/content/route/{0}',
                    type: 'DELETE'
                }, // 删除线路
                checkRoute: {
                    url: context + '/content/route/checkName?name={0}&business_id={1}',
                    type: 'GET'
                }, // 检查线路名称是否合法
                fetchRouteConfig: {
                    url: context + '/content/route/configuration/{0}?data',
                    type: 'GET'
                },
                saveRouteConfig: {
                    url: context + '/content/route/configuration/{0}',
                    type: 'PUT'
                },

                // 商品类目
                fetchMerchantCat: { url: context + '/merchant/goodsCategory/get?offset={0}&limit={1}', type: 'GET' }, // 获取商品类目信息
                createMerchantCat: { url: context + '/merchant/goodsCategory', type: 'POST' },  // 新建商品类目信息
                saveMerchantCat: { url: context + '/merchant/goodsCategory/{0}', type: 'PUT' },  // 保存商品类目信息
                deleteMerchantCat: { url: context + '/merchant/goodsCategory/{0}', type: 'DELETE' },  // 删除商品类目
                searchMerchantCat: { url: context + '/merchant/goodsCategory/searchRoot?searchWord={0}&offset={1}&limit={2}', type: 'GET' },
                searchAllMerchatCat: { url: context + '/merchant/goodsCategory/search?searchWord={0}&limit={1}', type: 'GET' },

                //全景搜索
                searchSinglePano: {
                    url: "http://data.pano.visualbusiness.cn/rest/pano/search"
                },
                searchPanoList: {
                    url: "http://data.pano.visualbusiness.cn/rest/album/search"
                },

                //交易直通车
                saveMerchantInfo: { url: context + '/merchant/tradeApplication/create', type: 'POST' }, //保存商户数据
                getMerchantStatus: { url: context + '/merchant/tradeApplication/merchantStatus', type: 'GET' }, //获取商户状态
                getMerchatApplyList: { url: context + '/platform/message/getList', type: 'GET' },
                getMessageDetail: { url: context + '/platform/message/get/{0}', type: 'GET' },
                getMerchatDetail: { url: context + '/merchant/tradeApplication/detail', type: 'GET' },
                getMerchantInfo: { url: context + '/merchant/tradeApplication/getMerchantInfo', type: 'GET' },//获取crm信息
                getMerchatDetailData: { url: context + '/merchant/tradeApplication/get', type: 'GET' },
                merchatSign: { url: context + '/merchant/tradeApplication/sign', type: 'POST' },
                allowTradeTrain: { url: context + '/platform/message/check/{0}', type: 'POST' },

                //获取短信验证码
                getSMSCode: { url: context + '/common/sms/getCodeS', type: 'POST' },
                checkSMSCode: { url: context + '/common/sms/checkCode', type: 'GET' },


                //银行选择
                selectCity: { url: context + '/common/bnkAndCity/city/{0}', type: 'GET' }, //选择城市
                selectBranchBank: { url: context + '/common/bnkAndCity/branch/{0}', type: 'GET' }, //选择支行

            };
        }
    };

} ();