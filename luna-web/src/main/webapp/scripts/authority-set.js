/**
 * Created by wumengqiang on 16/8/9.
 */
$(function(){
    var apiUrls = Inter.getApiUrl();
    var id = location.href.match(/authority\/(\w+)/)[1];
    $('.save').on('click' , function(event){
        var checkboxList = $('input[type="checkbox"]'), checkedList = [];
        checkboxList.each(function(){
           if(this.checked === true){
               checkedList.push(parseInt(this.getAttribute('value')));
           }
        });
        console.log(checkedList);
        var data = new FormData();
        data.append('menuArray', JSON.stringify(checkedList));
        //data: JSON.stringify({menuArray: checkedList}),
        //contentType: 'application/json',
        $.ajax({
            url: apiUrls.updateAuthoritySet.url.format(id),
            type: apiUrls.updateAuthoritySet.type,
            contentType: 'application/x-www-form-urlencoded',
            data: data,
            dataType: 'json',
            success: function(data){
                console.log(data);
                if(data.code === '0'){

                } else{
                    alert(data.msg || '保存过程中出错了');
                }
            },
            error: function(data){
                alert(data.msg || '保存过程中出错了');
            }
        });

    });


});