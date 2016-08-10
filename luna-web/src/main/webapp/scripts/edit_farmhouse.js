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

        that.getFields = getFields;

        // 请求 获取表单信息
        that.fetchFormData = fetchFormData;

        // 请求 获取农家乐信息
        that.fetchFarmhouseData = fetchFarmhouseData;

        // 请求 提交表单信息
        that.postFormData = postFormData;

        // 请求 测试函数
        that.getTestData = getTestData;


        that.init();

        // 初始化函数
        function init() {
            that.apiUrls = Inter.getApiUrl();
            that.fields = null; //表单信息
            that.farmhouseData = null; // 表单数据
            //that.appId = location.href.match('') ; //获取app id
            that._bindEvent();
            that.fetchFormData();

        }

        function _bindEvent(){
            $('.operation.save').on('click', that.handleSave);
            $('.operation.save').on('click', that.handlePreview);
            $('.operation.save').on('click', that.handlePublish);

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
                url: '/fdsafdsa/fdsafda',
                type: 'GET',
                data: {},
                success: function () {

                },
                error: function () {


                }
            });
            $.ajax({
                url: that.apiUrls.farmHouseFormInfo.url.format(285),
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

        }

        function handlePreview(){

        }

        function handlePublish(){
            
        }

        // 请求 获取农家乐信息
        function postFormData(){


        }

        // 请求 提交表单信息
        function fetchFarmhouseData(){

        }


    }





});