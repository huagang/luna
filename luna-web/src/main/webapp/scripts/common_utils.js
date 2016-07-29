/**
 * common utils for simple extends
 * plugins should not appear here, better in separate independent file
 */

if (!String.prototype.capitalizeFirstLetter) {
    String.prototype.capitalizeFirstLetter = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
    }
}

if (!String.prototype.format) {
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined' ? args[number] : match;
        });
    };
}

/**
 * 设置Cookie
 * @param {[type]} c_name     [description]
 * @param {[type]} value      [description]
 * @param {[type]} expiredays [description]
 */
function setCookie(c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}
/**
 * 读取Cookie
 */
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=")
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1
            c_end = document.cookie.indexOf(";", c_start)
            if (c_end == -1) c_end = document.cookie.length
            return unescape(document.cookie.substring(c_start, c_end))
        }
    }
    return "";
}

/**
 * url格式化
 * @return {[type]} [description]
 */
function apiUrlFormat(s, arr) {
    if (!s || s.length == 0) {
        s = '';
    } else {
        for (var i = 0; i < arr.length; i++) {
            s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arr[i]);
        }
    }
    return s;
}

/**
 * 对象转成数组
 * @return {[type]} [description]
 */
function objToArr (obj){
	if(obj instanceof Object){
		var arr =[];
		for(key in obj ){
			
		}
	}else{
		return obj;
	}
}
