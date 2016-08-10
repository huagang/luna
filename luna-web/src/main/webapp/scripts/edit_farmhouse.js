/**
 * Created by wumengqiang on 16/8/3.
 */
$(function(){

    var controller = new Controller();
    function Controller() {
        window.t = this;

        var that = this;

        // 初始化函数
        that.init = init;

        // 绑定事件
        that._bindEvent = _bindEvent;

        // 初始化表单内容
        that.initComponents = initComponents;

        // 隐藏二维码
        that.hideQRcode = hideQRcode;

        // 隐藏发布弹出框
        that.hidePublishDialog = hidePublishDialog;

        that.getFields = getFields;

        // 事件 保存表单信息
        that.handleSave = handleSave;

        // 事件 预览页面
        that.handlePreview = handlePreview;

        // 事件 发布页面
        that.handlePublish = handlePublish;

        // 请求 获取表单信息
        that.fetchFormData = fetchFormData;

        that.init();

        // 初始化函数
        function init() {
            that.apiUrls = Inter.getApiUrl();
            that.fields = null; //表单信息
            that.farmhouseData = null; // 表单数据
            that.appId = location.href.match(/farm\/(\w+)/)[1]; //获取app id
            that._bindEvent();
            that.fetchFormData();
            new Clipboard('.publish-info .copy');

        }

        function _bindEvent(){
            $('.operation.save').on('click', that.handleSave);
            $('.operation.preview').on('click', that.handlePreview);
            $('.operation.publish').on('click', that.handlePublish);
            $('.qrcode-container .button').on('click', that.hideQRcode);
            $('.publish-info .btn-close').on('click' ,that.hidePublishDialog);
            $('.publish-pop-wrapper .mask').on('click' ,that.hidePublishDialog);

        }

        function initComponents() {
            that._component = new window.FormComponent.BaseComponent({
                definition: that.fields,
                value: ''
            });
            that._component.render('.auto-form');
        }

        // 请求 获取表单信息
        function fetchFormData() {

            $.ajax({
                url: that.apiUrls.farmHouseFormInfo.url.format(that.appId),
                type: that.apiUrls.farmHouseFormInfo.type,
                success: function(data){
                    if(data.code === '0'){
                        that.fields = data.data.fields;
                        that.divider = data.data.divider;
                        that.getFields();
                        that.initComponents();
                    } else{
                        alert('获取微景展信息失败,请刷新重试');
                    }
                },
                error: function(data){
                    alert('获取微景展信息失败,请刷新重试');
                }
            });
        }

        function getFields() {
            for(var i = 0; i < that.divider.length ; i+=1){
                that.fields.splice(that.divider[i] + i, 0, {definition: {
                    type: 'DIVIDER'
                }});
            }
            that.fields.forEach(function(item, index){
                item.definition.display_order = index + 1;
            });


        }

        function handleSave(){
            var validation = that._component.checkValidation();
            if(validation){
                alert(validation);
                return;
            }
            var data = that._component.getFormValue();
            console.log(data);
            $.ajax({
                url: that.apiUrls.saveFarmHouseFormInfo.url.format(that.appId),
                type: that.apiUrls.saveFarmHouseFormInfo.type,
                data: {'data': JSON.stringify(data)},
                dataType: 'json',
                success: function(res){
                    if(res.code === '0'){
                        alert('保存成功');
                    } else{
                        alert(res.msg || '保存失败');
                    }
                },
                error: function(res){
                    alert(res.msg || '保存失败');
                }
            });

        }

        function handlePreview(){
            if(! that.previewImg){
                $.ajax({
                    url: that.apiUrls.farmHousePreview.url.format(that.appId),
                    type: that.apiUrls.farmHousePreview.type,
                    success: function(res){
                        if(res.code === '0'){
                            that.previewImg = res.data.QRImg;
                            var container = $('.qrcode-container');
                            container.removeClass('hidden');
                            container.find('.qrcode').attr('src', that.previewImg);
                            // 显示图片
                        } else{
                            alert(res.msg || '获取预览信息失败');
                        }
                    },
                    error: function(res){
                        alert(res.msg || '获取预览信息失败');
                    }
                });
            } else {
                $('.qrcode-container').removeClass('hidden');
            }

        }

        function hideQRcode(){
            $('.qrcode-container').addClass('hidden');
        }

        function handlePublish(){
            if(! that.publishImg){
                $.ajax({
                    url: that.apiUrls.farmHousePublish.url.format(that.appId),
                    type: that.apiUrls.farmHousePublish.type,
                    success: function(res){
                        if(res.code === '0'){
                            that.publishUrl = res.data.link;
                            that.publishImg = res.data.QRImg;
                            $('.publish-info .publish-qrcode').attr('src', that.publishImg);
                            $('.publish-info .publish-link').attr('href', that.publishUrl).html(that.publishUrl);
                            $('.publish-pop-wrapper .mask').removeClass('hidden');
                            $('.publish-info.pop').addClass('pop-show');
                            $(document.body).addClass('modal-open');
                        } else{
                            alert(res.msg || '发布失败')
                        }
                    },
                    error: function(res){
                        alert(res.msg || '发布失败')
                    }
                });
            }
            else{
                $('.publish-pop-wrapper .mask').removeClass('hidden');
                $('.publish-info.pop').addClass('pop-show');
                $(document.body).addClass('modal-open');
            }
        }

        function hidePublishDialog(){
            $('.publish-info.pop').removeClass('pop-show');
            $(document.body).removeClass('modal-open');
            $('.publish-pop-wrapper .mask').addClass('hidden');
        }

    }





});