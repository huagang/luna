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

        that.handlePublishReplace = handlePublishReplace;

        that.handleReplaceClick = handleReplaceClick;

        that.handleSelect = handleSelect;

        // 请求 获取表单信息
        that.fetchFormData = fetchFormData;

        that.handleForcePublish = handleForcePublish;

        that.showMessage = showMessage;

        that.init();

        // 初始化函数
        function init() {
            that.apiUrls = Inter.getApiUrl();
            that.fields = null; //表单信息
            that.farmhouseData = null; // 表单数据
            that.appId = location.href.match(/farm\/(\w+)/)[1]; //获取app id
            that._bindEvent();
            that.fetchFormData();
            new Clipboard('.publish-info .copy',{
                text: function(trigger) {
                    return $('.publish-link').html();
                }
            });

        }

        function _bindEvent(){
            $('.operation.save').on('click', that.handleSave);
            $('.operation.preview').on('click', that.handlePreview);
            $('.operation.publish').on('click', that.handlePublish);
            $('.qrcode-container .button').on('click', that.hideQRcode);

            $('.publish-pop-wrapper .btn-close').on('click' ,that.hidePublishDialog);
            $('.publish-pop-wrapper .button-close').on('click', that.hidePublishDialog);

            $('.publish-info .button.confirm').on('click' ,that.hidePublishDialog);
            $('.publish-pop-wrapper .mask').on('click' ,that.hidePublishDialog);

            $('.publish-confirm').on('click', '.button', that.handleForcePublish);

            $("#pop-message  #btn-mes").click(function () {
                $("#pop-message").css('display', "none");
            });
            $("#pop-message .btn-close").click(function () {
                $("#pop-message").css('display', "none");
            });
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
                var error = that._component.checkValidation();
                if(error){
                    //error
                    that.showMessage('信息未填写完毕,不能预览。详细错误信息如下\n' + error );
                    return;
                }
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

        function handleForcePublish(){
            that.forcePublish = true;
            that.handlePublish();
        }

        function handlePublish(){
            var error = that._component.checkValidation();
            if(error){
                //error
                that.showMessage('信息未填写完毕,不能发布。详细错误信息如下\n' + error );
                return;
            }
            var data = {force: (that.forcePublish ? 1 : -1)};
            if(that.oldId){
                data.old_app_id = that.oldId;
            }
            $.ajax({
                url: that.apiUrls.appPublish.url.format(that.appId),
                type: that.apiUrls.appPublish.type,
                data: data,
                success: function(res){
                    if(res.code === '0'){
                        that.publishUrl = res.data.link;
                        that.publishImg = res.data.QRImg;
                        $('.publish-info .publish-qrcode').attr('src', that.publishImg);
                        $('.publish-info .publish-link').attr('href', that.publishUrl).html(that.publishUrl);
                        $('.publish-pop-wrapper .mask').removeClass('hidden');
                        $('.publish-confirm.pop').removeClass('pop-show');
                        $('.publish-replace.pop').removeClass('pop-show');
                        $('.publish-info.pop').addClass('pop-show');
                        $(document.body).addClass('modal-open');
                        that.oldId = undefined;
                    } else if(res.code === '409'){
                        that.publishData =  res.data;
                        if(that.publishData.length > 0){
                            // 如果存在多个线上微景展,则只替换第一个
                            that.oldId = that.publishData[0].app_id;
                        }
                        $('.publish-confirm.pop').addClass('pop-show');
                        $('.publish-pop-wrapper .mask').removeClass('hidden');
                        $(document.body).addClass('modal-open');
                    } else{
                        alert(res.msg || '发布失败')
                    }
                    that.forcePublish = false;

                },
                error: function(res){
                    alert(res.msg || '发布失败')
                }
            });
        }

        function handleSelect(event){
            $('.publish-replace .replace-option').prop('checked', false);
            $(event.target).prop('checked', true);
            that.oldId = $(event.target).attr('data-value');
        }

        function handleReplaceClick(){
            if(that.publishData.length === 1){
                that.oldId = that.publishData[0].app_id;
                that.handleForcePublish();

            } else if(that.publishData.length === 2){
                $('.publish-confirm.pop').removeClass('pop-show');
                $('.publish-replace.pop').addClass('pop-show');
                var options = [$('#options-0'), $('#options-1')];
                that.publishData.forEach(function(item, index){
                    var option = options[index];
                    option.find('.replace-option').attr('data-value', item.app_id);
                    option.find('.app-name').text(item.app_name);
                    option.find('.qrcode').attr('src', item.QRImg);
                });
            }
        }

        function handlePublishReplace(){
            if(that.oldId){
                that.forcePublish = 1;
                that.handlePublish();
            }
        }


        function hidePublishDialog(){
            $('.publish-info.pop').removeClass('pop-show');
            $('.publish-confirm.pop').removeClass('pop-show');
            $(document.body).removeClass('modal-open');
            $('.publish-pop-wrapper .mask').addClass('hidden');
        }

        function showMessage(msg) {
            $("#pop-message .message").text(msg);
            $('#pop-message').css('display', "block");
        }

    }





});

function clcWindow(){

}