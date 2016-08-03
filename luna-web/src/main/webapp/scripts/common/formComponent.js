/*
    dependency files
        css
            styles/file_loading_tip.css

        js
            jquery
 */

(function() {
    if (!String.prototype.format) {
        String.prototype.format = function () {
            var args = arguments;
            return this.replace(/{(\d+)}/g, function (match, number) {
                return typeof args[number] != 'undefined'
                    ? args[number]
                    : match
                    ;
            });
        };
    }

    var apiUrls = Inter.getApiUrl();

    function BaseComponent(data) {
        this.data = data;
        if(data){
            this.init();
        }
    }

    BaseComponent.prototype.init = function () {
        this.data = this.data || {};
        this.data.value = this.data.value || {};


        this._type = 'baseComponent';   //组件类型

        this.value = this.data.value; // 表单内容数据 可以形成 data tree 结构

        this.defination = this.data.defination || {};

        this.handleChangeCallback = this.data.onChange || null;

        // 其他的回调函数扩展

        this._component = null; // 当前组件

        this._children = {}; // 子组件对象, 每个父组件都应知道有哪些子组件 组件命名方式应该与组件作用相一致

        // 初始化子组件
        this.initChildren();
    };

    BaseComponent.prototype.initChildren = function(){
        (this.defination || []).forEach(function(item, index){
            this._children[item.defination.type] = new this.nameMapping[item.defination.type](item);
        }.bind(this));

    };

    BaseComponent.prototype.render = function(selector) {
        // 1.获取子组件render结果
        // 2.渲染当前组件
        // 3.返回最终Dom节点结果
        if(! this._component ){
            this._component = document.createElement('div');
            this._component.className = 'base-component';
            var children = this.getOrderedChildren();
            children.forEach(function(item, index){
                if(item){
                    this._component.appendChild(item.render());
                }
            }.bind(this));
        }

        if(selector){
            $(selector).append(this._component);
        }

        return this._component;
    };

    BaseComponent.prototype.getOrderedChildren = function(){
        var _children = [];
        if(this._children){
            for(var name  in this._children){
                _children[this._children[name].defination.display_order - 1] = this._children[name];
            }
        }
        return _children;
    };


    BaseComponent.prototype.afterMount = function () { // 组件第一次渲染结束后调用

    };

    BaseComponent.prototype.updateComponent = function (data) { // 更新组件内容 只传递需要更改的内容

    };


    BaseComponent.prototype.willUnMount = function () { //组件即将销毁时调用, 可能需要解除事件绑定

    };

    BaseComponent.prototype.defaultLimit = {
        PIC: {
            ext: ['png', 'jpg'],
            max: 1   // default 1M
        },
        AUDIO: {
            ext: ['mp3', 'wav', 'wma'],
            max: 5    // default 5M
        },
        VIDEO: {
            ext: ['rm', 'rmvb', 'avi', 'mp4', '3gp'],
            max: 20  // default 20M
        }
    };

    BaseComponent.prototype.nameMapping = {
        'TEXT': '',
        'TEXTAREA': '',
        'PIC': FileUpload,
        'AUDIO': FileUpload,
        'VIDEO': FileUpload,
        'RADIOBUTTON': '',
        'CHECKBOX': '',
        'POINT': '',
        'SELECT': '',
        'TEXT-PIC':'',
        'COUNTYENJOYMENT': '',
        'RADIO-TEXT': ''
    };



    /************* label组件 start ***********************/
    function Label(data) {
        /* data format
         {
         value:{
         label:'文章头图'
         }

         }

         */
        var that = this;
        that.data = data;

        that.init = init;
        that.render = render;
        that.updateComponent = updateComponent;

        that.init();
        function init() {
            that._type = 'label';
            that.defination = that.data.defination;
            that.defination.display_order = that.defination.display_order || 1;
        }

        function render() {
            if (!that.component) {
                that._component = document.createElement('label');
                that._component.innerHTML = that.defination.show_name;
                return that._component;
            }

        }

        function updateComponent(data) {
            if (data.defination.show_name !== that.defination.show_name) {
                that._component.innerHTML = that.defination.show_name = data.defination.show_name;
            }
        }
    }

    Label.prototype = new BaseComponent();

    /************* label组件 end ***********************/


    /************* fileUpload(文件上传)组件start ****************/

    function FileUpload(data) {
        /*
         param data - 初始化参数
         format -{
         value: {
         url: '',
         label: ''
         },
         data: {
         fileType: '', // pic,video, audio
         fileTypeName: '',
         limit: {
         pic: {
         format: ['PNG', 'JPG'],
         maxSize: 1000000   // default 1M
         },
         audio: {
         format: ['MP3', 'WAV', 'WMA'],
         size: 5000000    // default 5M
         },
         video: {
         format:['RM','RMVB','AVI','MP4','3GP'],
         size: 20000000  // default 20M
         }
         }
         },

         }
         */
        var that = this;
        that.data = data;

        that.init = init;
        that.render = render;
        that.updateComponent = updateComponent;

        // 事件绑定
        that._bindEvent = _bindEvent;

        // 显示
        that.showLoadingTip = showLoadingTip;

        // 隐藏loading效果
        that.hideLoadingTip = hideLoadingTip;

        // 设置错误信息
        that.setWarn = setWarn;

        // 检查合法性
        that._checkValidation = _checkValidation;

        // 文件上传
        that.handleFileUp = handleFileUp;

        that.init();
        function init() {

            that.data.value = that.data.value || {};
            that.value = that.data.value; // 表单内容数据 可以形成 data tree 结构

            that.typeNameMapping = {
                'PIC': '图片',
                'AUDIO': '音频',
                'VIDEO': '视频'
            };

            that.defination = that.data.defination || {}; // 其他数据存储位置

            that.defination.limit = that.defination.limit || that.defaultLimit[that.defination.fileType];  // 如果没有传入限制参数, 则使用默认限制参数

            that._template =
                "<div class='fileup'> \
                    <input type='text' readonly='true' class='media' placeholder='请输入{0}介绍地址'> \
                    <span class='btn fileinput-button' title='文件大小不超过{1}M'> \
                        <input class='media-fileup' type='file' name='media_fileup'> \
                        <span>本地上传</span> \
                        <div class='load-container loading-tip load8 hidden'> \
                            <div class='loader'>Loading...</div> \
                        </div> \
                    </span> \
                    <div class='cleanInput hide'><a href='javascript:void(0)'>删除</a></div> \
                 </div>\
                 <p class='warn'></p>";

            that._children = {};
            if (that.defination.show_name) {
                that._children.label = new Label({defination: {show_name: that.defination.show_name}});
            }

        }

        function render() {
            if (!that._component) {

                that._component = document.createElement('div');
                that._component.className = 'fileInput';

                that._component.innerHTML = that._template.format(
                    that.typeNameMapping[that.defination.type], that.defination.limit.max);

                if (that._children.label) {
                    var label = that._children.label.render();
                    that._component.insertBefore(label, that._component.firstChild);
                }
                that.element = $(that._component);

                that._bindEvent();
            }

            return that._component;

        }

        function _checkValidation(file){
            var fileExt = file.name.substr(file.name.lastIndexOf('.')+1).toLowerCase();
            var limitFormat = that.defination.limit.ext;
            if(limitFormat.indexOf(fileExt) === -1){
                return {error: 'format', msg:'文件格式仅限于' + limitFormat.join(',')};
            }
            var limitSize = that.defination.limit.max ;
            if(file.size  / 1000000 > limitSize){
                return {error: 'size', msg:'文件大小不能超过' + limitSize  + 'M'};
            }
            return {error: null};
        }

        function _bindEvent(){
            that.element.find('.media-fileup').on('change', that.handleFileUp);
            that.element.find('.media').on('change', function(event){
                that.value.url = event.target.value;
                that.setWarn('');
            });
            that.element.find('.cleanInput').on('click', function(event){
                that.value.url = '';
                that.element.find('.media').val('');
                $(event.target).addClass('hidden');
            });

        }

        function handleFileUp(){
            var res = that._checkValidation(event.target.files[0]);
            if(res.error){
                that.setWarn(res.msg);
                return;
            }
            that.showLoadingTip();
            var data = new FormData();
            data.append('file', event.target.files[0]);
            data.append('type', that.defination.type.toLocaleLowerCase());
            data.append('resource_type', 'app');
            $.ajax({
                url: apiUrls.uploadPath,
                type: 'POST',
                data: data,
                contentType: false,
                dataType:'json',                       //服务器返回的格式,可以是json或xml等
                processData:false,
                success: function(data){
                    that.value.url = data.data.access_url;
                    that.element.find('.media').val(that.value.url);
                    that.element.find('.media-fileup').val('');
                    that.setWarn('');
                    that.hideLoadingTip();
                    that.element.find('.cleanInput').removeClass('hide');
                },
                error: function(){
                    that.setWarn(data.msg);
                    that.hideLoadingTip();
                }
            });
        }

        function setWarn(warn){
            that.element.find('.warn').html(warn);
        }

        function showLoadingTip() {
            that.element.find('.loading-tip').removeClass('hidden');
        }

        function hideLoadingTip() {
            that.element.find('.loading-tip').addClass('hidden');
        }

        function updateComponent(data) {
            if (data.value.url !== that.value.url) {
                that._component.url = that.value.url = data.value.url;
            }

            if (data.defination.show_name !== that.defination.show_name) {
                that.defination.show_name = data.defination.show_name;
                that._children.label.updateComponent({defination: {show_name: that.defination.show_name}});
            }
        }
    }
    FileUpload.prototype = new BaseComponent();

    /************* 文件上传组件end ******************/


    /*************** TEXTAREA组件 start*******************/
    function TextArea(){

    }

    TextArea.prototype = new BaseComponent();
    /*************** TEXTAREA组件 end*******************/

    window.FormComponent = {
        'BaseComponent': BaseComponent,
        'Label': Label,
        'FileInput': FileUpload
    };
}());

