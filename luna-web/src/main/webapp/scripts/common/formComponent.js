/*
    dependency files
        css
            styles/file_loading_tip.css
            plugins/selectizeJs/selectize.bootstrap3.css
            styles/ formComponent.css
            styles/ common.css


        js
            jquery
            deep-diff
            plugins/selectizeJs/selectize.min.js

*/

/*
       注意事项
            需要在设置window.context, 否则可能有些图片加载不了
 */

(function() {


    // 设置context
    window.context = window.context || '';


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
        if (data) {
            this.init();
        }
    }

    BaseComponent.prototype.init = function () {
        this.data = this.data || {};
        this.data.value = this.data.value || {};


        this._type = 'baseComponent';   //组件类型

        this.value = this.data.value; // 表单内容数据 可以形成 data tree 结构

        this.definition = this.data.definition || {};

        this.handleChangeCallback = this.data.onChange || null;

        // 其他的回调函数扩展

        this._component = null; // 当前组件

        this._children = {}; // 子组件对象, 每个父组件都应知道有哪些子组件 组件命名方式应该与组件作用相一致

        // 初始化子组件
        this.initChildren();
    };

    BaseComponent.prototype.initChildren = function () {
        (this.definition || []).forEach(function (item, index) {
            this._children[item.definition.type] = new this.nameMapping[item.definition.type](item);
        }.bind(this));

    };

    BaseComponent.prototype.render = function (selector) {
        // 1.获取子组件render结果
        // 2.渲染当前组件
        // 3.返回最终Dom节点结果
        if (!this._component) {
            this._component = document.createElement('div');
            this._component.className = this.definition.type ?
            this.definition.type.toLowerCase() + ' component' : 'base-component';
            var children = this.getOrderedChildren();
            children.forEach(function (item, index) {
                if (item) {
                    this._component.appendChild(item.render());
                }
            }.bind(this));
        }

        if (selector) {
            $(selector).append(this._component);
        }

        return this._component;
    };

    BaseComponent.prototype.getOrderedChildren = function () {
        var _children = [];
        if (this._children) {
            for (var name  in this._children) {
                _children[this._children[name].definition.display_order - 1] = this._children[name];
            }
        }
        return _children;
    };


    BaseComponent.prototype.afterMount = function () { // 组件第一次渲染结束后调用

    };

    BaseComponent.updateComponent = function (data) { // 更新组件内容 只传递需要更改的内容

    };


    BaseComponent.prototype.willUnMount = function () { //组件即将销毁时调用, 可能需要解除事件绑定

    };

    BaseComponent.prototype.defaultLimits = {
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
        },
        TEXTAREA: {
            max: 1024
        },
        TEXT: {
            max: 255
        }
    };

    BaseComponent.prototype.nameMapping = {
        'TEXT': '',
        'TEXTAREA': Textarea,
        'PIC': FileUploader,
        'AUDIO': FileUploader,
        'VIDEO': FileUploader,
        'RADIOBUTTON': '',
        'CHECKBOX': CheckboxList,
        'POINT': '',
        'SELECT': '',
        'TEXT_PIC': TextPic,
        'TEXT_PIC_GROUP': TextPicGroup,
        'COUNTRY_ENJOYMENT': CountryEnjoyment,
        'RADIO_TEXT': RadioText,
        'FileUploader': Divider,
        'RadioList': RadioList,
        'INPUT': Input,
        'DIVIDER': Divider,
        'POI_SEARCH': PoiSearch,

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
            that.definition = that.data.definition;
            that.definition.display_order = that.definition.display_order || 1;
        }

        function render() {
            if (!that.component) {
                that._component = document.createElement('label');
                that._component.className = 'component-label'
                that._component.innerHTML = that.definition.show_name;
                return that._component;
            }

        }

        function updateComponent(data) {
            if (data.definition.show_name !== that.definition.show_name) {
                that._component.innerHTML = that.definition.show_name = data.definition.show_name;
            }
        }
    }

    Label.prototype = new BaseComponent();

    /************* label组件 end ***********************/


    /************* FileUploader(文件上传)组件start ****************/

    function FileUploader(data) {

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

        // 清空内容
        that.clear = clear;

        that.init();
        function init() {

            that.data.value = that.data.value || '';
            that.value = that.data.value;

            that.typeNameMapping = {
                'PIC': '图片',
                'AUDIO': '音频',
                'VIDEO': '视频'
            };

            that.definition = that.data.definition || {};

            that.definition.limit = (that.definition.limits || that.defaultLimits)[that.definition.type];  // 如果没有传入限制参数, 则使用默认限制参数

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
            if (that.definition.show_name) {
                that._children.label = new Label({definition: {show_name: that.definition.show_name}});
            }

        }

        function render() {
            if (!that._component) {

                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'fileInput component';

                that._component.innerHTML = that._template.format(
                    that.typeNameMapping[that.definition.type], that.definition.limit.max);


                if (that._children.label) {
                    var label = that._children.label.render();
                    that._component.insertBefore(label, that._component.firstChild);
                }
                if(that.value){
                    that.element.find('.media').val(that.value);
                    that.element.find('.cleanInput').removeClass('hide');
                }

                that._bindEvent();
            }

            return that._component;

        }

        function _checkValidation(file){
            var fileExt = file.name.substr(file.name.lastIndexOf('.')+1).toLowerCase();
            var limitFormat = that.definition.limit.ext;
            if(limitFormat.indexOf(fileExt) === -1){
                return {error: 'format', msg:'文件格式仅限于' + limitFormat.join(',')};
            }
            var limitSize = that.definition.limit.max ;
            if(file.size  / 1000000 > limitSize){
                return {error: 'size', msg:'文件大小不能超过' + limitSize  + 'M'};
            }
            return {error: null};
        }

        function _bindEvent(){
            if(! that.binded) { //防止二次绑定
                that.binded = true;
                that.element.find('.media-fileup').on('change', that.handleFileUp);
                that.element.find('.media').on('change', function (event) {
                    that.value = event.target.value;
                    that.setWarn('');
                });
                that.element.find('.cleanInput').on('click', function (event) {
                    that.value = '';
                    that.element.find('.media').val('');
                    $(event.target).addClass('hidden');
                });
            }

        }

        function handleFileUp(){
            var res = that._checkValidation(event.target.files[0]);
            if(res.error){
                that.setWarn(res.msg);
                return;
            }
            that.showLoadingTip();
            that.element.find('.cleanInput').addClass('hide');

            var data = new FormData();
            data.append('file', event.target.files[0]);
            data.append('type', that.definition.type.toLocaleLowerCase());
            data.append('resource_type', 'app');
            $.ajax({
                url: apiUrls.uploadPath,
                type: 'POST',
                data: data,
                contentType: false,
                dataType:'json',                       //服务器返回的格式,可以是json或xml等
                processData:false,
                success: function(data){
                    if(data.code === '0'){
                        that.value = data.data.access_url;
                        that.element.find('.media').val(that.value);
                        that.element.find('.media-fileup').val('');
                        that.setWarn('');
                        that.element.find('.cleanInput').removeClass('hide');
                    } else{
                        that.setWarn(data.msg);
                        if(that.value){
                            that.element.find('.cleanInput').removeClass('hide');
                        }
                    }
                    that.hideLoadingTip();


                },
                error: function(data){
                    that.setWarn(data.msg);
                    that.hideLoadingTip();
                    if(that.value){
                        that.element.find('.cleanInput').removeClass('hide');
                    }
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
            if (data.value && data.value !== that.value) {
                that.value = data.value;
                that.element.find('.media').val(that.value);
                that.element.find('.cleanInput').removeClass('hide');
            }
        }

        function clear(){
            that.value = '';
            that.element.find('.media').val(that.value);
            that.element.find('.media-fileup').val('');
            that.setWarn('');
            that.hideLoadingTip();
            that.element.find('.cleanInput').addClass('hide');
        }

    }
    FileUploader.prototype = new BaseComponent();

    /************* 文件上传组件end ******************/


    /*************** TEXTAREA组件 start*******************/
    function Textarea(data){
        var that = this;
        that.data = data || {};
        // 初始化函数
        that.init = init;

        that._bindEvent = _bindEvent;

        // 渲染内容
        that.render = render;

        // 事件 onInput
        that.handleChange  = handleChange;

        // 更新内容
        that.updateComponent = updateComponent;

        that.init();
        function init(){
            that.data.value = that.data.value || '';
            that.value = that.data.value;

            that.definition = that.data.definition || {};

            that.definition.limit = (that.definition.limits || that.defaultLimits)['TEXTAREA'];  // 如果没有传入限制参数, 则使用默认限制参数
            that._children = {};
            if (that.definition.show_name) {
                that._children.label = new Label({definition: {show_name: that.definition.show_name}});
            }
        }

        function render() {
            if (!that._component) {

                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'textarea component';
                that.element.html('<textarea placeholder="{0}"></textarea>'.format(that.definition.placeholder || ''));
                if (that._children.label) {
                    var label = that._children.label.render();
                    that._component.insertBefore(label, that._component.firstChild);
                }

                if(that.value){
                    that.element.find('textarea').val(that.value);
                }

                that._bindEvent();
            }

            return that._component;

        }

        function _bindEvent(){
            if(! that.binded) { //防止二次绑定
                that.binded = true;
                that.element.on('input', that.handleChange);
            }
        }

        function handleChange(event){
            if(event.target.value.length > 1024){
                that.value = event.target.value.substr(0,1024);
                that.element.find('textarea').val(that.value);
            } else{
                that.value = event.target.value;
            }
        }

        function updateComponent(data){
            if(that.value !== data.value){
                that.value = data.value;
                that.element.find('textarea').val(that.value);
            }
        }
    }

    // Textarea.prototype = new BaseComponent();
    /*************** TEXTAREA组件 end*******************/


/*************** input 组件 start*******************/
    function Input(data){
        var that = this;
        that.data = data;

        that.init = init;
        that.render = render;
        that.updateComponent = updateComponent;

        // 事件绑定
        that._bindEvent = _bindEvent;

        that.init();
        function init() {
            that.data.value = that.data.value || '';
            that.value = that.data.value;

            that.definition = that.data.definition || {};

            that.definition.limit = (that.definition.limits || that.defaultLimits)['TEXT'];  // 如果没有传入限制参数, 则使用默认限制参数
            that._children = {};
            if (that.definition.show_name) {
                that._children.label = new Label({definition: {show_name: that.definition.show_name}});
            }
        }

        function render() {
            if (!that._component) {

                that._component = document.createElement('div');
                that._component.className = 'input component';

                that.element = $(that._component);
                that.element.html('<input type="text" class="input-text" placeholder="{0}" maxlength="{1}"/>'.format(
                    that.definition.placeholder || '', that.definition.limit.max || 255));

                if (that._children.label) {
                    var label = that._children.label.render();
                    that._component.insertBefore(label, that._component.firstChild);
                }

                if(that.value){
                    that.element.find('input').val(that.value);
                }

                if (that._children.label) {
                    var label = that._children.label.render();
                    that._component.insertBefore(label, that._component.firstChild);
                }
                that._bindEvent();
            }

            return that._component;

        }

        function _bindEvent(){
            if(! that.binded) { //防止二次绑定
                that.binded = true;
                that.element.on('input', that.handleChange);
            }
        }

        function handleChange(event){
            if(event.target.value.length > 1024){
                that.value = event.target.value.substr(0,1024);
                that.element.find('input').val(that.value);
            } else{
                that.value = event.target.value;
            }
        }

        function updateComponent(data){
            if(that.value !== data.value){
                that.value = data.value;
                that.element.find('input').val(that.value);
            }
        }
    }

    /*************** 分割线组件 start*******************/
    function Divider(data){
        this.data = data || {};
        this.definition = this.data.definition;
        this.render = function(){
            if(!this._component){
                this._component = document.createElement('div');
                this._component.className = 'component component-divider';
                this.element = $(this._component);
                this._component.innerHTML = "<div class='divider'></div>";
            }

            return this._component;
        }

    }



    /*************** radiolist 组件 start*******************/

    function RadioList(data) {
        var that = this;

        that.data = data;
        that.init = init;
        that.render = render;
        that.updateComponent = updateComponent;
        // 事件绑定
        that._bindEvent = _bindEvent;

        // 获取模板
        that.getTemplate = getTemplate;

        that.init();
        function init() {

            that.data.value = that.data.value || '';
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.getTemplate();
            that._children = {};
            if (that.definition.show_name) {
                that._children.label = new Label({definition: {show_name: that.definition.show_name}});
            }

        }

        function render() {
            if (!that._component) {
                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'component radio-list';
                that._component.innerHTML = that._template;
                if (that._children.label) {
                    var label = that._children.label.render();
                    that._component.insertBefore(label, that._component.firstChild);
                }

                if (that.value) {
                    that._component.find('.radio-item[value="{0}"]'.format(that.value)).attr('checked', true);
                }

            }

            return this._component;
        }


        function getTemplate() {
            that._template = '';
            var now = Date.now();
            that.definition.options.forEach(function (item, index) {
                that._template += "<input id='{0}'  class='radio-item' type='radio'  value='{1}'/><label class='radio-label' for='{0}'>{2}</label>".
                    format(now + '-' + index,item.value, item.text);
            });
            that._template = "<div class='radio-container'>{0}</div>".format(that._template);
        }

        function _bindEvent() {
            if(! that.binded) { //防止二次绑定
                that.binded = true;
                this.element.find('.radio-item').on('change', function (event) {
                    that.value = event.target.value;
                });
            }
        }

        function updateComponent(data) {
            if (data.definition && DeepDiff.diff(data.definition.options, that.definition.options)) {
                that.getTemplate();
                that.element.find('.radio-container').replaceWith(that._template);
                if(that.value){
                    that._component.find('.radio-item[value="{0}"]'.format(that.value)).attr('checked', true);
                }
            }

            if (data.value && data.value !== that.value) {
                that.value = data.value;
                that._component.find('.radio-item[value="{0}"]'.format(that.value)).attr('checked', true);
            }
        }
    }


    /*************** radio text 组件 start*******************/

    function RadioText(data){
        var that = this;
        that.data = data;

        that.init = init;
        that.render = render;
        that.updateComponent = updateComponent;

        // 事件绑定
        that._bindEvent = _bindEvent;

        that.init();
        function init() {

            that.data.value = that.data.value || {};

            that.value = that.data.value;

            that.definition = that.data.definition || {};

            that.definition.limits = (that.definition.limits || that.defaultLimits);  // 如果没有传入限制参数, 则使用默认限制参数

            that._children = {};

            if(that.definition.extension_attrs){
                that._children.radioList = new RadioList({
                    value: '',
                    definition: {options: that.definition.extension_attrs}
                });
            }

            if (that.definition.show_name) {
                that._children.label = new Label({definition: {show_name: that.definition.show_name}});
            }

            that._children.input = new Input({definition:{limits: that.definition.limits}, value: ''});

        }

        function render() {
            if (!that._component) {

                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'radio-text component';


                if (that._children.label) {
                    var label = that._children.label.render();
                    that._component.appendChild(label);
                }

                if(that._children.radioList){
                    that._component.appendChild(that._children.radioList.render());
                }

                that._component.appendChild(that._children.input.render());
                that.element.find('.input-text').val(that.value.text || '');

                that._bindEvent();
            }

            return that._component;

        }


        function _bindEvent(){

        }


        function updateComponent(data) {
            if (data.value.url !== that.value.url) {
                that._component.url = that.value.url = data.value.url;
            }

            if (data.definition.show_name !== that.definition.show_name) {
                that.definition.show_name = data.definition.show_name;
                that._children.label.updateComponent({definition: {show_name: that.definition.show_name}});
            }
        }
    }



    /*************** radio text 组件 end*******************/


    /***************  textpic 组件 end*******************/

    function TextPic(data){
        var that = this;
        that.data = data;

        that.init = init;

        that.updateComponent = updateComponent;
        that.init();

        function init() {
            that.data.value = that.data.value || {};
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.definition.limits = (that.definition.limits || that.defaultLimits);  // 如果没有传入限制参数, 则使用默认限制参数
            that.definition.type = 'TEXT_PIC';
            that._children = {};

            var curOrder = 1;
            if(that.definition.show_name){
                that._children.label = new Label({
                    definition: {
                        show_name: that.definition.show_name,
                        display_order: 1
                    }
                });
                curOrder = 2;
            }
            that._children.input = new Input({
                value: '',   // text
                definition: {
                    limits: that.definition.limits || that.defaultLimits,
                    placeholder: '',
                    display_order: curOrder
                }
            });
            that._children.picUploader = new FileUploader({
                value: '', // url
                definition: {
                    limits: that.definition.limits || that.defaultLimits,
                    placeholder: '',
                    type: 'PIC',
                    display_order: curOrder + 1
                }
            });
        }

        function updateComponent(data){
            if(data.value){  // 多值 object 类型
                if(data.value.url){
                    that._children.picUploader.updateComponent({value: data.value.url});
                }
                if(data.value.text){
                    that._children.input.updateComponent({value: data.value.text});
                }
            }

            if(data.definition.show_name && that._children.label){
                that._children.label.updateComponent(data);
            }

        }
    }
    TextPic.prototype = new BaseComponent();


    /******************* text pic group **********************/
    function TextPicGroup(data) {
        var that = this;
        that.data = data;

        that.init = init;

        that.render = render;

        that._bindEvent = _bindEvent;

        that.updateComponent = updateComponent;

        // 增加一项
        that._handleAddItem = _handleAddItem;

        // 删除一项
        that._handleDeleteItem = _handleDeleteItem;

        that.init();
        function init() {
            that.defautNum = 3;

            that.data.value = that.data.value || {};
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.definition.limits = (that.definition.limits || that.defaultLimits);  // 如果没有传入限制参数, 则使用默认限制参数
            that.definition.limits.num = that.definition.limits.num || that.defautNum;

            // 初始化子组件
            that._children = [];
            for(var i = 0 ; i< that.definition.limits.num; i++){
                that._children.push(new TextPic({
                    value:'',
                    definition: {
                        display_order: i + 1,
                        show_name: that.definition.show_name + (i + 1),
                        limits: that.definition.limits,
                        placeholder: that.definition.placeholder || ''
                    }
                }));
            }
        }

        function render(){
            if (!that._component) {
                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'textpic-group component';

                that._component.innerHTML = '<div><button class="add-item button">新增一条</button></div>';

                that._children.forEach(function(item, index){
                    var row = document.createElement('div');

                    row.className = 'textpic-row';
                    row.innerHTML = "<a href='javascript:void(0)' data-order='{0}' class='delete-item'>删除</a>".format(index);
                    row.insertBefore(item.render(), row.firstChild);
                    that._component.insertBefore(row, that._component.lastChild);
                });

                that._bindEvent();
            }
            return that._component;
        }

        function _bindEvent(){
            if(! that.binded){ //防止二次绑定
                that.binded = true;
                that.element.on('click', '.add-item', that._handleAddItem);
                that.element.on('click', '.delete-item', that._handleDeleteItem);
            }
        }


        function _handleAddItem(){
            if(that._children.length === that.definition.limits.maxNum){
                return;
            }

            var child = new TextPic({
                value:'',
                definition: {
                    display_order: that._children.length + 1,
                    show_name: that.definition.show_name + (that._children.length + 1),
                    limits: that.definition.limits,
                    placeholder: that.definition.placeholder || ''
                }
            });
            that._children.push(child);

            var row = document.createElement('div');
            row.className = 'textpic-row';
            row.innerHTML = "<a href='javascript:void(0)' data-order='{0}' class='delete-item'>删除</a>".format(that._children.length);
            row.insertBefore(child.render(), row.firstChild);
            that._component.insertBefore(row, that._component.lastChild);
        }

        function _handleDeleteItem(event){
            if(that._children.length === 1) return;

            var order = parseInt(event.target.getAttribute('data-order'));
            that._children.splice(order, 1);
            $('.textpic-group')[0].children[order].remove();
            for(var i = order; i < that._children.length; i++){
                that._children[i].updateComponent({'definition':{'show_name': that.definition.show_name + (i+1)}});
            }
        }

        function updateComponent(data){
            //  暂时没有这个需求 不写 :)
        }
    }


    /******************* country enjoyment **********************/

    function CountryEnjoyment(data) {
        var that = this;
        that.data = data;

        that.init = init;

        that.render = render;

        that._bindEvent = _bindEvent;

        that.updateComponent = updateComponent;

        // 选择选项
        that.handleSelect = handleSelect;

        that.addItem = addItem;

        // 删除已经选择项
        that.handleDelete = handleDelete;

        // 编辑已经选择项
        that.handleEdit = handleEdit;

        // 关闭弹窗
        that.handleClose = handleClose;

        // 保存编辑结果
        that.confirmEdit = confirmEdit;

        that.init();

        function init() {

            that.data.value = that.data.value || [];
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.definition.limits = (that.definition.limits || that.defaultLimits);  // 如果没有传入限制参数, 则使用默认限制参数
            that.definition.limits.num = that.definition.limits.num || that.defautNum;
            that._template =
                    '<select class="text-select"></select> \
                    <div class="mask hidden"></div>\
                    <div class="pop-edit hidden">\
                        <div class="pop-title">\
                            <h4>编辑{0}</h4>\
                            <a href="javascript:void(0)" class="btn-close">\
                                <img src="{1}/img/close.png" />\
                            </a>\
                        </div>\
                        <div class="pop-cont">\
                            <div class="form-group">\
                                <label>活动名称</label>\
                                <span class="activity-name"></span>\
                            </div>\
                            <div class="form-group pic-upload"></div>\
                        </div>\
                        <div class="pop-fun">\
                            <div class="button-container">\
                                <button class="button button-confirm"> 确定</button>\
                                <button class="button button-close"> 取消</button>\
                            </div> \
                        </div>\
                    </div>\
                    <div class="item-container">\
                        <div class="item hidden" data-value="">\
                            <div class="pic-wrapper">\
                                <div class="pic" style="background-color: #999">\
                                </div>\
                                <p class="item-name"></p>\
                            </div>\
                            <div class="footer" >\
                                <a class="edit-item">编辑</a>\
                                <a class="delete-item">删除</a>\
                            </div>\
                        </div>\
                    \
                    </div>'.format(that.definition.show_name, window.context);
            that._children = {};
            if(that.definition.show_name){
                that._children.label = new Label({
                    definition: {
                        show_name: that.definition.show_name
                    }
                });
            }

            that._children.picUploader = new FileUploader({
                value: '', // url
                definition: {
                    limits: that.definition.limits,
                    placeholder: '',
                    type: 'PIC',
                    display_order: 1,
                    show_name: '图标'
                }
            });
        }

        function render(){
            if (!that._component) {
                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'country-enjoyment component';
                that._component.innerHTML = that._template;
                that.element.prepend(that._children.label.render());
                if(that.definition.options){
                    if(! that.selectize){
                        that.selectize = that.element.find('.text-select').selectize({
                            options: that.definition.options,
                            labelField: 'name',
                            searchField: ['name'],
                            onChange: that.handleSelect,
                            highlight: false
                        });
                        that.selectize =  that.selectize[0].selectize;
                    }
                }

                that.element.find('.pic-upload').append(that._children.picUploader.render());
                that._bindEvent();

            }
            return that._component;
        }

        function updateComponent(){

        }

        function _bindEvent(){
            that.element.on('click', '.edit-item', that.handleEdit);
            that.element.on('click', '.delete-item', that.handleDelete);
            that.element.on('click', '.pop-edit .btn-close', that.handleClose);
            that.element.on('click', '.pop-edit .button-close', that.handleClose);
            that.element.on('click', '.mask', that.handleClose);
            that.element.on('click', '.pop-edit .button-confirm', that.confirmEdit);
        }



        function handleSelect(value){
            var notFound = that.value.every(function(item){
                if(item.value === value){
                    return false;
                }
                return true;
            });
            if(notFound){
                that.definition.options.some(function(item){
                    if(item.value === value){
                        that.addItem({
                            value: value,
                            name: item.name,
                            pic: item.pic || ''
                        });
                        return true;
                    }
                    return false;
                });

            }
        }

        function handleEdit(event){
            var value = $(event.target).parentsUntil('.item-container', '.item').attr('data-value');
            that.value.some(function(item, index){
                if(item.value === value){
                    that.curObj = item;
                    return true;
                }
                return false;
            });

            that.element.find('.pop-edit').removeClass('hidden');
            that.element.find('.mask').removeClass('hidden');
            $(document.body).addClass('modal-open');

            that.element.find('.activity-name').html(that.curObj.name);
            that._children.picUploader.clear();
            if(that.curObj.pic){
                that._children.picUploader.updateComponent({
                    value: that.curObj.pic
                });
            }
        }

        function confirmEdit(){
            that.curObj.pic = that._children.picUploader.value;
            that.element.find('.item[data-value="{0}"] .pic'.format(that.curObj.value)).attr('style',
                'background: url({0}) center center no-repeat;background-size: cover'.format(that.curObj.pic));

            that.handleClose();
        }

        function handleClose(){
            that.element.find('.pop-edit').addClass('hidden');
            that.element.find('.mask').addClass('hidden');
            $(document.body).removeClass('modal-open');
            that.curObj = undefined;
        }

        function handleDelete(event){
            var parent = $(event.target).parentsUntil('.item-container', '.item'),
                value = parent.attr('data-value'), order;
            that.value.some(function(item, index){
                if(item.value === value){
                    order = index;
                    return true;
                }
                return false;
            });
            if(order){
                that.value.splice(order, 1);
            }

            parent.remove();

        }

        function addItem(item){
            var ele = that.element.find('.item-container .item.hidden').clone(false, true);
            ele.attr('data-value', item.value || '');
            var pic = ele.find('.pic');
            ele.find('.item-name').html(item.name);
            if(item.pic){
                pic.attr('style','background: url({0}) center center no-repeat;background-size: cover'.format(item.pic));
            }
            ele.removeClass('hidden');
            that.element.find('.item-container').append(ele);
            that.value.push(item);
            that.selectize.clear();
        }


    }

    /******************** checkbox  *********************/
    function CheckboxList(data){
        var that = this;
        that.data = data;

        that.init = init;

        that.render = render;

        that._bindEvent = _bindEvent;

        that.updateComponent = updateComponent;

        that.init();

        function init() {
            that.data.value = that.data.value || {};
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.definition.limits = (that.definition.limits || that.defaultLimits);  // 如果没有传入限制参数, 则使用默认限制参数
            that._children = {};
            if(that.definition.show_name){
                that._children.label = new Label({
                    definition: { show_name: that.definition.show_name }
                });
            }

            that.checkedList = [];
        }

        function render(){
            if (!that._component) {
                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'checkbox-list component';

                var list = '', now=Date.now(), id;
                (that.definition.extension_attrs || []).forEach(function(item, index){
                    id = now + '-' + index;
                    list += "<label for='{0}' class='checkbox-label'>\
                                <div class='fullname'>{3}</div>\
                                <input class='checkbox' type='checkbox' value='{1}' id='{0}' data-order='{2}'/> {3}</label>"
                        .format(id, item.value, index, item.label);
                });

                that._component.innerHTML = '<div class="checkbox-container">{0}</div>'.format(list);

                if(this._children.label){
                    that.element.prepend(this._children.label.render());
                }

                if(that.value){
                    // 如果有值 :)

                }
                that._bindEvent();
            }
            return that._component;
        }

        function _bindEvent(){
            that.element.find('.checkbox').on('change', function(event){
                var order = parseInt(event.target.dataset.order);
                that.checkedList[order] = ! that.checkedList[order];
            });

        }

        function updateComponent(){
        }

    }


    /****************** poi绑定 ****************/
    function PoiSearch(data){  // 大组件 由于时间原因 没有进行细分

        var that = this;
        that.data = data;

        that.init = init;

        that.render = render;

        that._bindEvent = _bindEvent;

        that.updateComponent = updateComponent;

        that.handleSelect = handleSelect;

        that.handleInputChange = handleInputChange;

        that.fetchPoiList = fetchPoiList;

        that.handleTypeChange = handleTypeChange;

        that.handleClearTimeout = handleClearTimeout;

        that.init();

        function init() {
            that.data.value = that.data.value || '';
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.children = {};
            that.data.filter = {type: 'name' };
            that.template =
                '<div class="form-group poi-filter"> \
                    <label class="form-label">绑定POI</label>\
                    <div class="form-right">\
                        <select class="select-type">\
                            <option value="name">按名称</option>\
                            <option value="id">按id</option>\
                        </select>\
                        <select class="poi-select"></select>\
                    </div>\
                 </div>\
                 <div class="form-group poi-name">\
                    <label class="form-label">POI名称</label>\
                    <span class="form-value"></span>\
                 </div>\
                 <div class="form-group poi-position">\
                    <label class="form-label">坐标</label>\
                    <span class="form-value"></span>\
                 </div>\
                 <div class="form-group poi-address">\
                    <label class="form-label">地址</label>\
                    <span class="form-value"></span>\
                 </div>\
                 <div class="form-group poi-pano">\
                    <label class="form-label">全景标识</label>\
                    <span class="form-value"></span>\
                 </div>\
                 <div class="form-group poi-phone">\
                    <label class="form-label">联系电话</label>\
                    <span class="form-value"></span>\
                 </div>\
                 <div class="form-group poi-phone">\
                    <label class="form-label">缩略图</label>\
                    <span class="form-value"></span>\
                 </div>';

        }

        function render(){
            if (!that._component) {
                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'component poi-search';
                that._component.innerHTML = that.template;
                if(! that.selectize){
                    that.selectize = that.element.find('.poi-select').selectize({
                        options: that.options || [],
                        labelField: 'poi_name',
                        valueField: 'poi_id',
                        searchField: ['poi_name'],
                        onChange: that.handleSelect,
                        highlight: false
                    });
                    that.selectize =  that.selectize[0].selectize;
                }
                var placeholder = (that.definition.placeholder || {})['TEXT'];
                if(placeholder){
                    that.element.find('.selectize-input input').attr('placeholder', placeholder);
                }
                that._bindEvent();
            }

            return that._component;

        }


        function _bindEvent(){

            that.element.find('.selectize-input input').on('input', that.handleInputChange);
            that.element.find('.selectize-input input').on('keydown', that.handleClearTimeout);
            that.element.find('.select-type').on('change', that.handleTypeChange);
        }

        function  fetchPoiList(){
            if(! that.data.filter.text){
                return;
            }
            setTimeout(function(){
                that.options = that.definition.options.reduce(function(mem, cur, index){
                    var type =that.data.filter.type, text = that.data.filter.text;
                    if(type === 'name' && cur.poi_name.indexOf(text) !== -1){
                        mem.push(cur);
                    }

                    else if(type === 'id' && cur.poi_id.indexOf(text) !== -1){
                        mem.push(cur);
                    }
                    return mem;
                }, []) || [];
                console.log(that.options);
                that.selectize.clearOptions();
                that.selectize.addOption(that.options);
                that.selectize.open();
            }, 500);

        }

        function handleInputChange(event){
            that.data.filter.text = event.target.value;
        }

        function handleClearTimeout(event){
            if(that.data.filter.timeoutId){
                clearTimeout(that.data.filter.timeoutId);
            }

            that.data.filter.timeoutId = setTimeout(that.fetchPoiList, 500);
        }

        function handleTypeChange(event){
            that.data.filter.type = event.target.value;
            if(that.data.filter.type === 'name'){
                that.selectize.settings.searchField = ['poi_name'];
            } else if(that.data.filter.type === 'id'){
                that.selectize.settings.searchField = ['poi_id'];
            }
            that.selectize.clear();
            that.selectize.clearOptions();

         }

        function handleSelect(){

        }

        function updateComponent(){


        }


    }


    window.FormComponent = {
        'BaseComponent': BaseComponent,
        'Label': Label,
        'FileInput': FileUploader,
        'Textarea':Textarea,
        'RadioText':RadioText,
        'RadioList': RadioList,
        'Input': Input,
        'TextPic': TextPic,
        'TextPicGroup': TextPicGroup,
        'CheckboxList': CheckboxList,
        'PoiSearch': PoiSearch
    };
}());

