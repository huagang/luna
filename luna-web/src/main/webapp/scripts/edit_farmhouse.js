/**
 * Created by wumengqiang on 16/8/3.
 */
$(function(){

    var controller = new Controller();
    function Controller(){
        var that = this;

        // 初始化函数
        that.init = init;

        // 初始化表单内容
        that.initComponents = initComponents;

        /* 应该将其放到子组件中,而不是放到这儿
        // 请求 搜索poi
        that.searchPois = searchPois;

        // 请求 获取单个poi信息
        that.fetchPoiData = fetchPoiData;
        */

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
        function init(){
            that.testData = that.getTestData();

            that.formData = null; //表单信息
            that.farmhouseData = null; // 表单数据

            that.fetchFormData();
        }

        function initComponents(){
            that._component = new window.FormComponent.BaseComponent({
                defination: that.formData,
                value: ''
            });
            that._component.render('.auto-form');
        }

        function fetchPoiData(){

        }

        function searchPois(){

        }

        // 请求 获取表单信息
        function fetchFormData(){
            $.ajax({
                url: '/fdsafdsa/fdsafda',
                type: 'GET',
                data: {},
                success: function(){

                },
                error: function(){
                    that.formData = that.testData.formData;
                    that.initComponents();
                }
            });
        }

        // 请求 获取农家乐信息
        function postFormData(){


        }

        // 请求 提交表单信息
        function fetchFarmhouseData(){

        }

        function getTestData(){
            return {
                formData: [
                    {
                        "value": "",
                        "defination": {
                            "show_name": "启动页背景图",
                            "display_order": 1,
                            "extension_attrs": [],
                            "limit": {
                                "ext": [
                                    "png",
                                    "jpg"
                                ],
                                "max": 1
                            },
                            "type": "PIC",
                            "name": "start_page_background_pic"
                        }
                    }

                ]
            };
        }

    }





});