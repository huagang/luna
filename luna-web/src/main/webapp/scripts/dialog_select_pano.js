/**
 * 全景选择弹出框
 * 基于jQuery， 基于format属性扩充, interface文件
 */

$(document).ready(function() {

    $('#panoResultWrapper').on('click', '.pano-result-pic', function(e) {
        $('.pano-result-pic').each(function(i, el) {
            $(this).closest('.pano-item-wrapper').removeClass('selected');
        });
        $(this).closest('.pano-item-wrapper').addClass('selected');
    });

    $('#panoSelectConfirmBtn').on('click', function(e) {
        var selectedPano = document.querySelector('.pano-item-wrapper.selected');
        if (selectedPano) {
            var confirmCallback = eval(e.target.dataset.confirmcallback);
            confirmCallback(selectedPano.dataset.panoid);
            clcWindow(this);
        } else {
            alert('请选择全景');
        }
    });

    $('#panoBtnSearch').on('click', function(e) {
        var q = $('#panoSelectIdOrName').val();
        if (!q) return;
        var panoType = $('#panoSelectType').data('panotype'),
            url, param = {
                'q':q,
            };
        var panoPreUrl = {
            '1': Inter.getApiUrl().singlePano,
            '2': Inter.getApiUrl().multiplyPano,
            '3': Inter.getApiUrl().customerPano
        };
        if (panoType == '1') {
            url = Inter.getApiUrl().searchSinglePano.url;
            getPanoAjax(url, param, function(res) {
                if (res.result == 0) {
                    var panoResultHtml = [];
                    var data = res.data.searchResult;
                    data.forEach(function(element, index, array) {
                        if (index >= 12) {
                            return;
                        }
                        panoResultHtml.push('<li class="pano-item-wrapper" data-panoid= ' + element.panoId + '>\
                            <div class="pano-result-pic">\
                                <img class="" src="' + element.thumbnailUrl + '" alt="' + element.panoName + '">\
                            </div>\
                            <div class="pano-result-detail">\
                                <div class="pano-result-title-wrapper">' + element.panoName + '<a  href="' + panoPreUrl[panoType].format([element.panoId]) + '" class="pano-result-link" target="_blank"> <i class=""></i></a>\
                                </div>\
                            </div>\
                        </li>');
                    }, this);
                    $('#panoResultWrapper').empty().append(panoResultHtml.join(''));
                } else {
                    alert(res.msg);
                }
            });
        } else {
            url = Inter.getApiUrl().searchPanoList.url;
            getPanoAjax(url, param, function(res) {
                if (res.result == 0) {
                    var panoResultHtml = [];
                    var data = res.data.searchResult;
                    data.forEach(function(element) {
                        panoResultHtml.push('<li class="pano-item-wrapper"  data-panoid= ' + element.albumId + '>\
                            <div class="pano-result-pic">\
                                <img class="" src="http://cdn.visualbusiness.cn/public/vb/img/sample.png" alt="' + element.albumName + '">\
                            </div>\
                            <div class="pano-result-detail">\
                                <div class="pano-result-title-wrapper">' + element.albumName + '<a href="' + panoPreUrl[panoType].format([element.albumId]) + '" class="pano-result-link" target="_blank"> <i class=""></i></a>\
                                </div>\
                            </div>\
                        </li>');
                    }, this);
                    $('#panoResultWrapper').empty().append(panoResultHtml.join(''));
                } else {
                    alert(res.msg);
                }
            });
        }
    });

    // document.querySelector('.pano-result-pic').addEventListener('click',function (e) {  
    //     document.querySelectorAll('.pano-result-pic').forEach(function(el){
    //         el.classList.remove('selected');
    //     }); 
    //     e.classList.add('selected');
    // });



    function getPanoAjax(url, param, successCallback) {
        $.get(url, param, function(res) {
            console.log(res);
            if (successCallback) {
                successCallback(res);
            }
        }, "json");
    }

});