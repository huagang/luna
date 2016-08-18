/**
 * Created by wumengqiang on 16/8/17.
 */


$(function(){

    $('[data-toggle="tooltip"]').tooltip();
    window.cropper = new Cropper();
    function Cropper(){
        var that = this;

        // 初始化
        that._init = _init;

        // 事件绑定
        that._bindEvent = _bindEvent;

        // 设置文件, 将会自动弹出弹出框
        that.setFile = setFile;

        that._confirmCrop = _confirmCrop;

        // 取消上传
        that._handleCancel = _handleCancel;

        // 清除内容
        that.clear = clear;

        // 关闭
        that.close = close;

        // 图片操作, 移动图片
        that._moveImg = _moveImg;

        // 图片操作 缩小
        that._handleZoomIn = _handleZoomIn;

        // 图片操作 放大
        that._handleZoomOut = _handleZoomOut;

        // 图片操作 右旋转
        that._rotateRight = _rotateRight;

        // 图片操作 左旋转
        that._rotateLeft = _rotateLeft;

        // 图片操作 重置
        that._handleReset = _handleReset;

        that._init();
        // 初始化
        function _init(){
            that.container = $('.pic-cropper');
            that.dialog = $('.pic-cropper .pop-modal'); // 弹出框绑定
            that.mask = $('.pic-cropper .mask');  // 遮罩层
            that.image = $('.init-img');
            that.compressSelect = $('.compress-select');
            that.body = $(document.body);
            that._bindEvent();
        }

        // 事件绑定
        function _bindEvent(){
            that.mask.on('click', that._handleCancel);

            that.dialog.on('click', '.btn-close', that._handleCancel);
            that.dialog.on('click', '.cancel', that._handleCancel);
            that.dialog.on('click', '.confirm', that._confirmCrop);

            that.dialog.on('click', '.glyphicon-move', that._moveImg);
            that.dialog.on('click', '.glyphicon-zoom-in', that._handleZoomIn);
            that.dialog.on('click', '.glyphicon-zoom-out', that._handleZoomOut);
            that.dialog.on('click', '.rotate-right', that._rotateRight);
            that.dialog.on('click', '.rotate-left', that._rotateLeft);
            that.dialog.on('click', '.glyphicon-refresh', that._handleReset);
        }

        function setFile(file, successCallback, cancelCallback){

            if (file) {
                that.file = file;
                that.successCallback = successCallback;
                that.cancelCallback = cancelCallback;
                var reader = new FileReader();

                reader.onload = function (e) {
                    that.image.attr('src', e.target.result);
                    setTimeout(function(){
                        if(that.image.cropper){
                            that.image.cropper('destroy');
                        }
                        that.image.cropper({
                            viewMode: 3,
                            autoCropArea: 1,
                            preview: '.img-preview'
                        });

                    }, 100);
                };

                reader.readAsDataURL(file);

                that.clear();
                that.container.removeClass('hidden');
                that.body.addClass('modal-open');

            }
        }

        function _confirmCrop(){
            that.image.cropper('getCroppedCanvas').toBlob(function(blob){
                if(typeof that.successCallback === 'function'){
                    var file = new File([blob], that.file.name);
                    that.successCallback(file);
                }
            }, that.file.type, that.compressSelect.prop('checked') ? 0.8 : 1);

        }

        // 取消上传
        function _handleCancel(event){
            that.container.addClass('hidden');
            that.body.removeClass('modal-open');
            if(typeof that.cancelCallback === 'function'){
                that.cancelCallback();
            }
            event.preventDefault();
        }

        // 清除内容
        function clear(){
            that.compressSelect.prop('checked', true);
            that.image.attr('src', '');
            if(that.image.cropper){
                that.image.cropper('reset');
            }
        }

        function close(){
            that.container.addClass('hidden');
            that.body.removeClass('modal-open');
        }

        function _moveImg(){
            that.image.cropper('setDragMode','move');
        }

        function _handleZoomIn(){
            that.image.cropper('zoom', 0.1);
        }

        function _handleZoomOut(){
            that.image.cropper('zoom', -0.1);
        }

        function _rotateRight(){
            that.image.cropper('rotate', 90);
        }

        function _rotateLeft(){
            that.image.cropper('rotate', -90);
        }

        function _handleReset(){
            that.image.cropper('reset');
        }
    }
});