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
                definition: that.formData,
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
                        "definition": {
                            "show_name": "启动页背景图",
                            "display_order": 1,
                            "extension_attrs": [],
                            "limits": {
                                'PIC':{
                                    "ext": [
                                        "png",
                                        "jpg"
                                    ],
                                    "max": 1
                                }
                            },
                            "type": "PIC",
                            "name": "start_page_background_pic"
                        }
                    },
                    {
                        "definition": {
                            "show_name": "经营者自述",
                            "display_order": 2,
                            "name": "manager_self_introduction",
                            "extension_attrs": [],
                            "type": "TEXTAREA",
                            playceholder: '哈哈哈',
                            "limits": {
                                'TEXTAREA':{
                                    "max": 1024
                                }
                            }
                        },
                        "value": ""
                    },
                    {
                        "definition":{
                            type: 'DIVIDER',
                            "display_order": 3

                        }
                    },
                    {
                        "definition": {
                            "show_name": "精选房间全景标识",
                            "display_order": 5,
                            "name": "well_chosen_room_panorama_type",
                            "extension_attrs": [
                                {
                                    value: "1",
                                    text:"单场景点"
                                },
                                {
                                    value: "2",
                                    text:"相册"
                                },
                                {
                                    value: "3",
                                    text: "自定义接口"
                                }
                            ],
                            "type": "RADIO_TEXT",
                            "limits": {
                                "TEXT": {
                                    "max": 255
                                }
                            }
                        },
                        "value": {}
                    },

                    {
                        "definition": {
                            "show_name": "美食",
                            "display_order": 7,
                            "name": "delicacy",
                            "extension_attrs": [],
                            "type": "TEXT_PIC_GROUP",
                            "limits": {
                                "TEXT": {
                                    "max": 255
                                },
                                "PIC": {
                                    "ext": [
                                        "png",
                                        "jpg"
                                    ],
                                    "max": 20
                                },
                                'num': 2,
                                'maxNum': 5
                            }
                        },
                        "value": []
                    },
                    {
                        "definition": {
                            "show_name": "场地设施",
                            "display_order": 9,
                            "name": "facility",
                            "extension_attrs": [
                                {
                                    value:'1',
                                    label: "facility1facility1"
                                },{
                                    value:'2',
                                    label: "facility1facility1"
                                },{
                                    value:'3',
                                    label: "facility1facility1"
                                },{
                                    value:'4',
                                    label: "facility1facility1"
                                },{
                                    value:'5',
                                    label: "facility1facility1"
                                },{
                                    value:'6',
                                    label: "facility1facility1"
                                },{
                                    value:'7',
                                    label: "facility1facility1"
                                },{
                                    value:'8',
                                    label: "facility1facility1"
                                },{
                                    value:'9',
                                    label: "facility1facility1"
                                },{
                                    value:'10',
                                    label: "facility1facility1"
                                },{
                                    value:'11',
                                    label: "facility1facility1"
                                },{
                                    value:'12',
                                    label: "facility1facility1"
                                },

                            ],
                            "type": "CHECKBOX"
                        },
                        "value": []
                    }

                ]
            };
        }

    }





});