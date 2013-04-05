/*
--------------------------------
jquery 扩展开始
*/

$.extend({

    isIE: $.browser.msie != undefined,
    isIE6: $.isIE && $.browser.version == '6.0',

    copyText: function (obj) { var str = $.isElement(obj) ? obj.value : ($(obj).size() > 0 ? $(obj).val() : obj); if (window.clipboardData && clipboardData.setData && window.clipboardData.setData("Text", str)) { return true; } else { if ($.isElement(obj)) o.select(); return false; } },
    addBookMark: function (url, title) { try { if (window.sidebar) { window.sidebar.addPanel(title, url, ''); } else if ($.isIE) { window.external.AddFavorite(url, title); } else if (window.opera && window.print) { return true; } } catch (e) { alert("Your browser does not support it."); } },
    setHomePage: function (url) { try { document.body.style.behavior = 'url(#default#homepage)'; document.body.setHomePage(url); } catch (e) { if (window.netscape) { try { netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); } catch (e) { alert("Your browser does not support it."); } var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch); prefs.setCharPref('browser.startup.homepage', url); } } },

    getCookie: function (name) { var r = new RegExp('(^|;|\\s+)' + name + '=([^;]*)(;|$)'); var m = document.cookie.match(r); return (!m ? '' : decodeURIComponent(m[2])); },
    setCookie: function (name, value, expire, domain, path) { var s = name + '=' + encodeURIComponent(value); if (!$.isUndefined(path)) s = s + '; path=' + path; if (expire > 0) { var d = new Date(); d.setTime(d.getTime() + expire * 1000); if (!$.isUndefined(domain)) s = s + '; domain=' + domain; s = s + '; expires=' + d.toGMTString(); } document.cookie = s; },
    removeCookie: function (name, domain, path) { var s = name + '='; if (!$.isUndefined(domain)) s = s + '; domain=' + domain; if (!$.isUndefined(path)) s = s + '; path=' + path; s = s + '; expires=Fri, 02-Jan-1970 00:00:00 GMT'; document.cookie = s; },

    isUndefined: function (obj) { return typeof obj == 'undefined'; },
    isObject: function (obj) { return typeof obj == 'object'; },
    isNumber: function (obj) { return /^[0-9]*[1-9][0-9]*$/.test(obj); },
    isString: function (obj) { return typeof obj == 'string'; },
    isElement: function (obj) { return obj && obj.nodeType == 1; },
    isFunction: function (obj) { return typeof obj == 'function'; },
    isArray: function (obj) { return Object.prototype.toString.call(obj) === '[object Array]'; },

    isInt: function (str) { return /^-?\\d+$/.test(str); },
    isFloat: function (str) { return /^(-?\\d+)(\\.\\d+)?$/.test(str); },
    isIntPlus: function (str) { return /^[0-9]*[1-9][0-9]*$/.test(str); },
    isFloatPlus: function (str) { return /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test(str); },
    isEnglish: function (str) { return /^[A-Za-z]+$/.test(str); },
    isChinese: function (str) { return /^[\u0391-\uFFE5]+$/.test(str); },
    isZipCode: function (str) { return /^[1-9]\d{5}$/.test(str); },
    isEmail: function (str) { return /^[A-Z_a-z0-9-\.]+@([A-Z_a-z0-9-]+\.)+[a-z0-9A-Z]{2,4}$/.test(str); },
    isMobile: function (str) { return /^((\(\d{2,3}\))|(\d{3}\-))?((1[35]\d{9})|(18[89]\d{8}))$/.test(str); },
    isUrl: function (str) { return /^(http:|ftp:)\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"])*$/.test(str); },
    isIP: function (str) { return /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(str) },
    isIpAddress: function (str) { return /^(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5])$/.test(str); },
    isLegalStr: function (str) { return /^[A-Za-z0-9_.]+$/.test(str) },

    encode: function (str) { return encodeURIComponent(str); },
    decode: function (str) { return decodeURIComponent(str); },
    format: function () { if (arguments.length == 0) return ''; if (arguments.length == 1) return arguments[0]; var args = $.cloneArray(arguments); args.splice(0, 1); return arguments[0].replace(/{(\d+)?}/g, function ($0, $1) { return args[parseInt($1)]; }); },

    escapeHtml: function (str) { return str.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;"); },
    unescapeHtml: function (str) { return str.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nbsp;/g, " ").replace(/&quot;/g, "\"").replace(/&amp;/g, "&"); },
    filterHtml: function (str) { str = str.replace(/\<(.*?)\>/g, '', str); str = str.replace(/\<\/(.*?)\>/g, '', str); return str; },

    cloneArray: function (arr) { var cloned = []; for (var i = 0, j = arr.length; i < j; i++) { cloned[i] = arr[i]; } return cloned; },

    getKeyCode: function (e) { var evt = window.event || e; return evt.keyCode ? evt.keyCode : evt.which ? evt.which : evt.charCode; },
    enterSubmit: function (e, v) { if ($.getKeyCode(e) == 13) { if ($.isFunction(v)) { v(); } else if ($.isString(v)) { $(v)[0].click(); } } },
    ctrlEnterSubmit: function (e, v) { var evt = window.event || e; if (evt.ctrlKey && $.getKeyCode(evt) == 13) { if ($.isFunction(v)) { v(); } else if ($.isString(v)) { $(v)[0].click(); } } },

    getUrlQuery: function (key, decode, url) { url = url || window.location.href; if (url.indexOf("#") !== -1) url = url.substring(0, url.indexOf("#")); var rts = [], rt; queryReg = new RegExp("(^|\\?|&)" + key + "=([^&]*)(?=&|#|$)", "g"); while ((rt = queryReg.exec(url)) != null) { if (decode && decode == true) rts.push(decodeURIComponent(rt[2])); else rts.push(rt[2]); } return rts.length == 0 ? '' : (rts.length == 1 ? rts[0] : rts); },

    doGet: function (url, data, type, callback, option) { var op = { type: 'GET', url: url, data: data, dataType: type, success: callback, cache: false, error: function () { } }; $.extend(op, option); $.ajax(op); },
    doPost: function (url, data, type, callback, option) { var op = { type: 'POST', url: url, data: data, dataType: type, success: callback, cache: false, error: function () { } }; $.extend(op, option); $.ajax(op); },
    doJson: function (url, data, callback) { $.getJSON(url, data, function (data) { return callback(data) }); },
    doPostJson: function (url, data, callback) { $.doPost(url, data, 'json', function (data) { return callback(data) }); },
    subStr: function (start, end, str) { if (str.length > end) return str.substring(start, end) + "……"; else return str }
});



$.fn.extend({
    trim: function () { return $.trim(this.val()); },
    lTrim: function () { return this.val().replace(/^\s+/, ''); },
    rTrim: function () { return this.val().replace(/\s+$/, ''); },
    subStr: function (start, end, str) { return str.substring(start, end) },
    setDisabled: function (disabled) {
        return this.each(function () { $(this).attr('disabled', disabled).css('opacity', disabled ? 0.5 : 1.0); });
    },
    setReadOnly: function (readonly) {
        return this.each(function () { $(this).attr('readonly', readonly).css('opacity', readonly ? 0.5 : 1.0); });
    },
    setChecked: function (checked, value) {
        return this.each(function () { if (value == undefined) { $(this).attr('checked', checked); } else if ($(this).val() == value.toString()) { $(this).attr('checked', checked); } });
    }
});

/*
--------------------------------
jquery 扩展结束
*/

//Json时间转换公用函数
function formatJsonDate(obj) {
    var date = new Date(parseInt(obj.replace("/Date(", "").replace(")/", ""), 10));
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
}

//Json时间转行函数，返回Date对象
function getFormatJsonDate(obj) {
    var date = new Date(parseInt(obj.replace("/Date(", "").replace(")/", ""), 10));
    return date;
}

//Json时间转换格式为 2010-10-02 23:59:59
function formatJsonDateTime(obj, format) {
    obj = obj + "";
    if (obj.indexOf("/Date(", 0) == -1) {
        return obj;
    }
    obj = obj.replace("/Date(", "").replace(")/", "");
    var s = obj.indexOf('+');
    var timeResult;
    if (s > -1) {
        var t1 = obj.substring(0, s);
        var t2 = obj.substring(s + 1, obj.length);
        t2 = parseInt(t2, 10) / 100 * 60 * 60 * 1000;
        var myDate = new Date();
        timeResult = parseInt(t1, 10) + myDate.getTimezoneOffset() * 60 * 1000 + t2
    }
    else {
        timeResult = parseInt(obj, 10);
    }
    var date = new Date(timeResult);
    if (format) {
        return date.ChinaFormat(format);
    }
    else {
        return date.ChinaFormat("yyyy-MM-dd hh:mm:ss");
    }
}
//---------------------------------------------------  
// 日期格式化  
// 格式 YYYY/yyyy/YY/yy 表示年份  
// MM/M 月份  月份加一
// W/w 星期  
// dd/DD/d/D 日期  
// hh/HH/h/H 时间  
// mm/m 分钟  
// ss/SS/s/S 秒  
//---------------------------------------------------  
Date.prototype.ChinaFormat = function (formatStr) {
    var str = formatStr;
    var Week = ['日', '一', '二', '三', '四', '五', '六'];

    str = str.replace(/yyyy|YYYY/, this.getFullYear());
    str = str.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : '0' + (this.getYear() % 100));

    str = str.replace(/MM/, this.getMonth() > 8 ? parseInt(this.getMonth() + 1) : '0' + parseInt(this.getMonth() + 1));
    str = str.replace(/M/g, parseInt(this.getMonth() + 1));

    str = str.replace(/w|W/g, Week[this.getDay()]);

    str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
    str = str.replace(/d|D/g, this.getDate());

    str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : '0' + this.getHours());
    str = str.replace(/h|H/g, this.getHours());
    str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : '0' + this.getMinutes());
    str = str.replace(/m/g, this.getMinutes());

    str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : '0' + this.getSeconds());
    str = str.replace(/s|S/g, this.getSeconds());

    return str;
}
//浮点类型格式化
Number.prototype.ToString = function (arg) {
    var array, result;
    try {
        array = this.toString().split(".");
        result = array[0] + "." + array[1].substring(0, arg);
    } catch (e) {
        result = this;
    }
    return result;
}

//替换字符串
function ReplaceStr(data, repStr, newStr) {
    var reg = new RegExp(repStr.toString(), "g"); //创建正则RegExp对象
    return data.replace(reg, newStr);
}

//获取URL地址栏上参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//回车键事件
function EnterEvent(e, callback) {
    var keyPressed;
    if (window.event)
        keyPressed = window.event.keyCode; // IE
    else
        keyPressed = e.which; // Firefox
    if (keyPressed == 13) {
        callback;
    }
}

//随机生成GUID
function NewGuid() {
    var guid = "";
    for (var i = 1; i <= 32; i++) {
        var n = Math.floor(Math.random() * 16.0).toString(16);
        guid += n;
        if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
            guid += "-";
    }
    return guid;
}

//javascript key-value
function HashMap() {
    /** Map 大小 **/
    var size = 0;
    /** 对象 **/
    var entry = new Object();

    /** 存 **/
    this.put = function (key, value) {
        if (!this.containsKey(key)) {
            size++;
        }
        entry[key] = value;
    }

    /** 取 **/
    this.get = function (key) {
        if (this.containsKey(key)) {
            return entry[key];
        }
        else {
            return null;
        }
    }

    /** 删除 **/
    this.remove = function (key) {
        if (delete entry[key]) {
            size--;
        }
    }

    /** 是否包含 Key **/
    this.containsKey = function (key) {
        return (key in entry);
    }

    /** 是否包含 Value **/
    this.containsValue = function (value) {
        for (var prop in entry) {
            if (entry[prop] == value) {
                return true;
            }
        }
        return false;
    }

    /** 所有 Value **/
    this.values = function () {
        var values = new Array(size);
        for (var prop in entry) {
            values.push(entry[prop]);
        }
        return values;
    }

    /** 所有 Key **/
    this.keys = function () {
        var keys = new Array(size);
        for (var prop in entry) {
            keys.push(prop);
        }
        return keys;
    }

    /** Map Size **/
    this.size = function () {
        return size;
    }
}

Array.prototype.remove = function (dx) {
    if (isNaN(dx) || dx > this.length) { return false; }
    for (var i = 0, n = 0; i < this.length; i++) {
        if (this[i] != this[dx]) {
            this[n++] = this[i]
        }
    }
    this.length -= 1
}