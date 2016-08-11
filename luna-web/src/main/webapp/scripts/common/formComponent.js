/*
    dependency files
        css
            styles/file_loading_tip.css
            plugins/selectizeJs/selectize.bootstrap3.css
            styles/formComponent.css
            styles/common.css
        js
            plugins/jquery.js
            plugins/deep-diff/deep-diff-0.3.3.min.js
            plugins/selectizeJs/selectize.min.js
            script/common/interface.js

*/
/*
       注意事项
            需要在设置window.context, 否则可能有些图片加载不了
            功能不太完善, 没有的功能要自己加 :)


       相关函数说明:
            - updateComponent 的参数只传需要更新的参数, 不需要更新的参数不用传, 判断参数中某一项是否需要更新应该用 data.attr !== undefined
            -
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


    // data包含组件信息和相关值
    function BaseComponent(data) {
        this.data = data;
        if (data) {
            this.init();
        }
    }

    // 信息初始化
    BaseComponent.prototype.init = function () {
        this.data = this.data || {};
        this.data.value = this.data.value || {};


        this._type = 'baseComponent';   //组件类型

        this.value = this.data.value; // 表单内容数据 可以形成 data tree 结构

        this.definition = this.data.definition || {};

        // 其他的回调函数扩展

        this._component = null; // 当前组件

        this._children = []; // 子组件对象, 每个父组件都应知道有哪些子组件 组件命名方式应该与组件作用相一致

        // 初始化子组件
        this.initChildren();
    };

    // 根据定义初始化子组件
    BaseComponent.prototype.initChildren = function () {
        (this.definition || []).forEach(function (item, index) {
            try{
                this._children[index] = new this.nameMapping[item.definition.type](item);
            } catch(e){

            }
        }.bind(this));
    };

    // 渲染组件 返回组件的DOM 如果selector已经设置,那么将组件渲染到该 $(selector)的尾部
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

    // 根据组件的display_order来将组件顺序排列
    BaseComponent.prototype.getOrderedChildren = function () {
        var _children = [];
        if (this._children) {
            for (var name  in this._children) {
                _children[this._children[name].definition.display_order - 1] = this._children[name];
            }
        }
        return _children;
    };

    // 更新组件内容
    BaseComponent.updateComponent = function (data) { // 更新组件内容 只传递需要更改的内容

    };

    // 用于提交表单是检查表单的值是否合乎要求
    BaseComponent.prototype.checkValidation = function(){
        this.validation = true;
        if(this._type === 'baseComponent'){
            this.validation = '';
        }
        var hasChildren = false, childrenLength = 0;
        for(var i in this._children){
            var name = this._children[i].definition.name;
            if(name || (this.formType === 'array' && parseInt(name) === 0)){
                childrenLength += 1;
                hasChildren  = true;
                if(! this._children[i].definition.empty){
                    if(this._type === 'baseComponent') {
                        if(! this._children[i].checkValidation()){
                            if(this._children[i].formType === 'array'){
                                this.validation += this._children[i].definition.show_name + '没有填写完毕或数量不符合要求   \n';
                            } else if(this._children[i].formType === 'object'){
                                this.validation += this._children[i].definition.show_name + '没有填写完毕   \n';
                            }
                        }
                    }
                    else if(this.validation && ! this._children[i].checkValidation()) {
                        this.validation = false;
                    }

                }
            }
        }

        // 对于有子节点且值是数组类型的组件,需要查看是否满足数量限制
        if(hasChildren === true && this.formType === 'array'){
            if((this.definition.limits || {}).num){
                if(this.definition.limits.num.max && childrenLength > this.definition.limits.num.max ){
                    this.validation = false;
                }
                if(this.definition.limits.num.min && childrenLength < this.definition.limits.num.min ){
                    this.validation = false;
                }
            }
        }

        // 没有子节点的组件
        if(!hasChildren){
            var isEmpty = false;
            if(! this.value){
                isEmpty = true;
            } else if(toString.call(this.value) === "[object Object]"){
                for(var i in this.value){
                    var notEmpty = true;
                }

                if(! notEmpty){
                    isEmpty =true;
                }

            } else if(toString.call(this.value) === "[object Array]"){
                if(this.value.length === 0 ){
                    isEmpty = true;
                }
            }
            if(isEmpty){
                this.validation = false;
            }
        }
        return this.validation;
    };

    BaseComponent.prototype.formType = 'object';  //object array 为了区分返回给后台值的类型,是对象还是数组

    // 获取组件的表单值
    BaseComponent.prototype.getFormValue = function(){
        var hasChildren = false;
        if(this.formType === 'object'){
            this.formValue = {}
        } else if(this.formType === 'array'){
            this.formValue = [];
        }
        for(var i in this._children){
            var name = this._children[i].definition.name;
            if(name || (this.formType === 'array' && parseInt(name) > -1)){
                hasChildren  = true;
                this.formValue[name] = this._children[i].getFormValue();
            }
        }
        if(! hasChildren){
            this.formValue = this.value;
        }

        return this.formValue;
    };

    // 更新label信息 由于几乎所有组件都可能有label, 因而将其抽出来单独列成一个函数
    BaseComponent.prototype.updateLabel = function(){
        if(this._children.label && data.definition &&
            data.definition.show_name !== undefined && this.definition.show_name !== data.definition.show_name){
            this.definition.show_name = data.definition.show_name;
            this._children.label.updateComponent(this.definition.show_name);
        }
    };

    // 组件的限制条件
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

    // 关联组件名称和构造函数
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
        'FileUploader': FileUploader,
        'RadioList': RadioList,
        'INPUT': Input,
        'DIVIDER': Divider,
        'POI': PoiSearch

    };


    /************* label组件 start ***********************/
    function Label(data) {

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
                that._component.className = 'component-label';
                that._component.innerHTML = that.definition.show_name;
                return that._component;
            }
        }

        function updateComponent(data) {
            if (data.definition.show_name !== undefined && data.definition.show_name !== that.definition.show_name) {
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

            that._type = 'fileUploader';
            that.data.value = that.data.value || '';
            that.value = that.data.value;

            that.typeNameMapping = {
                'PIC': '图片',
                'AUDIO': '音频',
                'VIDEO': '视频'
            };

            that.definition = that.data.definition || {};

            that.definition.limit = that.definition.limits[that.definition.type] || that.defaultLimits[that.definition.type];  // 如果没有传入限制参数, 则使用默认限制参数
            if(toString.call(that.definition.limit) === '[object Array]'){
                that.definition.limit = that.definition.limit[0];
            }

            that._template =
                "<div class='fileup'> \
                    <input type='text' readonly='true' class='media' placeholder='{0}介绍地址'> \
                    <span class='btn fileinput-button' title='文件大小不超过{1}M'> \
                        <input class='media-fileup' type='file' name='media_fileup'> \
                        <span>本地上传</span> \
                        <div class='load-container loading-tip load8 hidden'> \
                            <div class='loader'>Loading...</div> \
                        </div> \
                    </span> \
                    <div class='cleanInput hidden'><a href='javascript:void(0)'>{1}</a></div> \
                 </div>\
                 <p class='warn'></p>".format( that.typeNameMapping[that.definition.type],that.definition.hideDeleteButton ? '' : '删除');

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
                    that.element.find('.cleanInput').removeClass('hidden');
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
            that.element.find('.cleanInput').addClass('hidden');

            var data = new FormData();
            data.append('file', event.target.files[0]);
            data.append('type', that.definition.type.toLocaleLowerCase());
            data.append('resource_type', 'app');
            $.ajax({
                url: apiUrls.uploadPath.url,
                type: apiUrls.uploadPath.type,
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
                        that.element.find('.cleanInput').removeClass('hidden');
                    } else{
                        that.setWarn(data.msg);
                        if(that.value){
                            that.element.find('.cleanInput').removeClass('hidden');
                        }
                    }
                    that.hideLoadingTip();


                },
                error: function(data){
                    that.setWarn(data.msg);
                    that.hideLoadingTip();
                    if(that.value){
                        that.element.find('.cleanInput').removeClass('hidden');
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
            if (data.value !== undefined && data.value !== that.value) {
                that.value = data.value;
                that.element.find('.media').val(that.value);
                that.element.find('.cleanInput').removeClass('hidden');
            }

        }

        function clear(){
            that.value = '';
            that.element.find('.media').val(that.value);
            that.element.find('.media-fileup').val('');
            that.setWarn('');
            that.hideLoadingTip();
            that.element.find('.cleanInput').addClass('hidden');
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
            that._type = 'textarea';

            that.data.value = that.data.value || '';
            that.value = that.data.value;

            that.definition = that.data.definition || {};

            that.definition.limit = (that.definition.limits || that.defaultLimits)['TEXTAREA'];  // 如果没有传入限制参数, 则使用默认限制参数
            if(toString.call(that.definition.limit) === '[object Array]'){
                that.definition.limit = that.definition.limit[0];
            }


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

    Textarea.prototype = new BaseComponent();
    /*************** TEXTAREA组件 end*******************/


/*************** input[type='text'] 组件 start*******************/
    function Input(data){
        var that = this;
        that.data = data;

        that.init = init;
        that.render = render;
        that.updateComponent = updateComponent;

        // 事件绑定
        that._bindEvent = _bindEvent;

        // input onChange事件
        that.handleChange = handleChange;

        that.init();
        function init() {
            that._type = 'input';

            that.data.value = that.data.value || '';
            that.value = that.data.value;

            that.definition = that.data.definition || {};

            that.definition.limit = that.definition.limits['TEXT'] || that.defaultLimits['TEXT'];  // 如果没有传入限制参数, 则使用默认限制参数
            if(toString.call(that.definition.limit) === '[object Array]'){
                that.definition.limit = that.definition.limit[0];
            }

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

        // 更新组件内容
        function updateComponent(data){
            if(that.value !== data.value){
                that.value = data.value;
                that.element.find('input').val(that.value);
            }
            if(that._children.label && that.definition.show_name !== data.definition.show_name ){
                that.definition.show_name = data.definition.show_name
                that._children.label.updateComponent({definition: {show_name: that.definition.show_name }});
            }
        }
    }
    Input.prototype = new BaseComponent();

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
    Divider.prototype = new BaseComponent();



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
            that._type = 'radioList';

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
                    that.element.find('.radio-item[value="{0}"]'.format(that.value)).prop('checked', true);
                }
                that._bindEvent();
            }

            return this._component;
        }


        function getTemplate() {
            that._template = '';
            that.definition.options.forEach(function (item) {
                that._template += "<label class='radio-label'><input class='radio-item' type='radio'  value='{0}'/> {1}</label>".
                    format(item.value, item.label);
            });
            that._template = "<div class='radio-container'>{0}</div>".format(that._template);
        }

        function _bindEvent() {
            if(! that.binded) { //防止二次绑定
                that.binded = true;
                that.element.find('.radio-item').on('change', function (event) {
                    that.value = event.target.value;
                    if(typeof that.definition.options[0].value === 'number'){
                        that.value = parseInt(that.value);
                    }
                    that.element.find('.radio-item').prop('checked',false);
                    $(event.target).prop('checked', true);
                });
            }
        }

        function updateComponent(data) {
            if (data.definition && data.definition.options !== undefined && DeepDiff.diff(data.definition.options, that.definition.options)) {
                that.getTemplate();
                that.definition.options = data.definition.options;
                that.element.find('.radio-container').replaceWith(that._template);
            }

            if (data.value !== undefined && data.value !== that.value) {
                that.value = data.value;
                that.element.find('.radio-item').attr('checked',false);
                that._component.find('.radio-item[value="{0}"]'.format(that.value)).prop('checked', true);
            }
        }
    }
    RadioList.prototype = new BaseComponent();


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
            that._type = 'radioText';

            that.data.value = that.data.value || {};

            that.value = that.data.value;

            that.definition = that.data.definition || {};

            that.definition.limits = (that.definition.limits || that.defaultLimits);  // 如果没有传入限制参数, 则使用默认限制参数

            that._children = {};

            if(that.definition.options){
                that._children.radioList = new RadioList({
                    value: that.value.value || '',
                    definition: {
                        options: that.definition.options,
                        name: 'value'
                    }

                });
            }

            if (that.definition.show_name) {
                that._children.label = new Label({definition: {show_name: that.definition.show_name}});
            }

            that._children.input = new Input({
                    definition: {
                            limits: that.definition.limits, name: 'text'
                    },
                    placeholder: that.definition.placeholder || {'TEXT': ''},
                    value: that.value.text || ''
            });

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
    RadioText.prototype = new BaseComponent();




    /*************** radio text 组件 end*******************/


    /***************  textpic 组件 end*******************/

    function TextPic(data){
        var that = this;
        that.data = data;

        that.init = init;

        that.updateComponent = updateComponent;
        that.init();

        function init() {
            that._type = 'textPic';

            that.data.value = that.data.value || {};
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.definition.limits = that.definition.limits || that.defaultLimits;  // 如果没有传入限制参数, 则使用默认限制参数

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
                value: that.value.text || '',   // text
                definition: {
                    limits: that.definition.limits || that.defaultLimits,
                    placeholder: '',
                    display_order: curOrder,
                    name: 'text'
                }
            });
            that._children.picUploader = new FileUploader({
                value: that.value.pic || '', // url
                definition: {
                    limits: that.definition.limits || that.defaultLimits,
                    placeholder: '',
                    type: 'PIC',
                    name: 'pic',
                    display_order: curOrder + 1,
                    hideDeleteButton: that.definition.hideDeleteButton
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

            if(data.definition.name || data.definition.name === 0){
                that.definition.name = data.definition.name;
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
            that._type = 'textPicGroup';
            that.formType = 'array';  // 返回的表单数据类型是数组

            that.data.value = that.data.value || [];
            that.value = that.data.value;

            that.definition = that.data.definition || {};
            that.definition.limits = that.definition.limits || that.defaultLimits;  // 如果没有传入限制参数, 则使用默认限制参数
            that.itemNum = (that.definition.limits.num || {}).min || 1;

            if(that.value){
                that.itemNum = that.value.length;
            }

            // 初始化子组件
            that._children = [];
            var value = '';
            for(var i = 0 ; i< that.itemNum ; i++){
                if(that.value && that.value.length > 0){
                    value = that.value[i];
                } else{
                    value = '';
                }
                that._children.push(new TextPic({
                    value: value,
                    definition: {
                        display_order: i + 1,
                        name: i,
                        show_name: that.definition.show_name + (i + 1),
                        limits: that.definition.limits,
                        placeholder: that.definition.placeholder || '',
                        hideDeleteButton: true
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
            if((that.definition.limits.num || {}).max && that._children.length === that.definition.limits.num.max){
                return;
            }

            var child = new TextPic({
                value:'',
                definition: {
                    display_order: that._children.length + 1,
                    name: that._children.length,
                    show_name: that.definition.show_name + (that._children.length + 1),
                    limits: that.definition.limits,
                    placeholder: that.definition.placeholder || '',
                    hideDeleteButton: true,
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
                that._children[i].updateComponent({'definition':
                    {'show_name': that.definition.show_name + (i+1)},
                     name: i,
                     display_order: i + 1
                });
            }
        }

        function updateComponent(data){
            //  暂时没有这个需求 不写 :)
        }
    }
    TextPicGroup.prototype = new BaseComponent();


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
            that._type = 'countryEnjoyment';

            that.data.value = that.data.value || [];
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.definition.limits = that.definition.limits || that.defaultLimits;  // 如果没有传入限制参数, 则使用默认限制参数

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
                console.log(that.definition.options);
                if(that.definition.options && ! that.selectize){
                    that.selectize = that.element.find('.text-select').selectize({
                        options: that.definition.options,
                        labelField: 'name',
                        searchField: ['name'],
                        onChange: that.handleSelect,
                        highlight: false
                    });
                    that.selectize =  that.selectize[0].selectize;
                }

                that.element.find('.pic-upload').append(that._children.picUploader.render());
                that._bindEvent();
            }

            if(that.value.length > 0){
                var value = that.value;
                that.value = [];
                value.forEach(function(item){
                    that.addItem({
                        value: item.value,
                        name: item.name,
                        pic: item.pic || ''
                    });
                });
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
                if(parseInt(item.value) === parseInt(value)){
                    return false;
                }
                return true;
            });
            if(notFound){
                that.definition.options.some(function(item){
                    if(typeof that.definition.options[0].value === 'number'){
                        value = parseInt(value);
                    }
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
            if(typeof order === 'number'){
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
    CountryEnjoyment.prototype = new BaseComponent();


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
            that._type = 'checkboxList';

            that.data.value = that.data.value || [];
            that.value = that.data.value;
            that.definition = that.data.definition || {};
            that.definition.limits = that.definition.limits || that.defaultLimits;  // 如果没有传入限制参数, 则使用默认限制参数
            that._children = {};
            if(that.definition.show_name){
                that._children.label = new Label({
                    definition: { show_name: that.definition.show_name }
                });
            }
        }

        function render(){
            if (!that._component) {
                that._component = document.createElement('div');
                that.element = $(that._component);
                that._component.className = 'checkbox-list component';

                var list = '', now=Date.now(), id;
                (that.definition.options || []).forEach(function(item, index){
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

                if(that.value.length > 0){
                    // 如果有值 :)
                    that.value.forEach(function(item){
                       that.element.find('input[value="{0}"]'.format(item)).prop('checked',true);
                    });
                }
                that._bindEvent();
            }
            return that._component;
        }

        function _bindEvent(){
            that.element.find('.checkbox').on('change', function(event){
                var order = parseInt(event.target.dataset.order);
                var value = that.definition.options[order].value;
                var index = that.value.indexOf(value);
                if(index  === -1 ){
                    if(typeof that.definition.options[0].value === 'number'){
                        value = parseInt(value);
                    }
                    that.value.push(value);
                } else{
                    that.value.splice(index, 1);
                }
            });

        }

        function updateComponent(){
        }

    }
    CheckboxList.prototype = new BaseComponent();


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
            that._type = 'poiSearch';

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
                 <div class="form-group poi-thumbnail">\
                    <label class="form-label">缩略图</label>\
                    <img src="" class="form-value" height="200" alt="暂无图片"/> \
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
                var placeholder = (that.definition.placeholder || {'TEXT': ['请输入POI id或名称']})['TEXT'][0];
                if(placeholder){
                    that.element.find('.selectize-input input').attr('placeholder', placeholder);
                }
                that._bindEvent();
            }
            if(that.value){
                var value = that.value;
                that.value = '';
                that.handleSelect(value);
            }

            return that._component;

        }


        function _bindEvent(){

            that.element.find('.selectize-input input').on('input', that.handleInputChange);
            that.element.find('.selectize-input input').on('keydown', that.handleClearTimeout);
            that.element.find('.select-type').on('change', that.handleTypeChange);
        }

        function  fetchPoiList(){
            if(that.data.filter.text){
                $.ajax({
                    url: apiUrls.poiFilter.url.format(that.data.filter.type, that.data.filter.text, '', ''),
                    type: apiUrls.poiFilter.type,
                    success: function(res){
                        if(res.code === '0'){
                            that.options = res.data.pois;
                            that.selectize.clearOptions();
                            that.selectize.addOption(that.options);
                            that.selectize.open();
                        } else{
                            alert(res.msg || '获取poi筛选列表失败');
                        }
                    },
                    error: function(){
                        alert(res.msg || '获取poi筛选列表失败');
                    }
                });
            }
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

        function handleSelect(value){
            if(value &&  that.value !== value){
                that.value = value;
                $.ajax({
                    url: apiUrls.poiDetail.format(value),
                    type: 'GET',
                    success:function(res){
                        if(res.code === '0'){
                            var detail = that.data.poiDetail = res.data.zh;
                            that.element.addClass('show-poi-info');
                            that.element.find('.poi-name .form-value').html(detail.poi_name);
                            if(detail.lnglat.lat && detail.lnglat.lng){
                                var position = detail.lnglat.lat + ',' + detail.lnglat.lng;
                            } else{
                                position = '暂无';
                            }
                            that.element.find('.poi-position .form-value').html(position);
                            var address = [detail.address.province, detail.address.city, detail.address.county, detail.address.detail_address].join(',');
                            that.element.find('.poi-address .form-value').html(address);
                            var pano = "[{0}]{1}".format(detail.panorama.panorama_type_name || '暂无', detail.panorama.panorama_id||'暂无');
                            that.element.find('.poi-pano .form-value').html(pano);
                            that.element.find('.poi-phone .form-value').html(detail.contact_phone || '暂无');
                            that.element.find('.poi-thumbnail .form-value').attr('src', detail.thumbnail);

                        } else{
                            console.error(res.msg || '获取poi信息出错, 请重试');
                            that.element.removeClass('show-poi-info');
                        }
                    },
                    error:function(res){
                        console.error(res.msg || '获取poi信息出错, 请重试');
                        that.element.removeClass('show-poi-info');
                    }
                });
            }
        }

        function updateComponent(){


        }


    }
    PoiSearch.prototype = new BaseComponent();


    window.FormComponent = {
        'BaseComponent': BaseComponent,
        'CheckboxList': CheckboxList,
        'CountryEnjoyment': CountryEnjoyment,
        'Divider': Divider,
        'FileInput': FileUploader,
        'Input': Input,
        'Label': Label,
        'PoiSearch': PoiSearch,
        'RadioText':RadioText,
        'RadioList': RadioList,
        'Textarea':Textarea,
        'TextPic': TextPic,
        'TextPicGroup': TextPicGroup,
    };
}());

