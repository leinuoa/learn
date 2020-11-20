"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
exports.MyAjax = void 0;
var TsAjax_1 = require("./TsAjax");
// 封装原生的ajax
var MyAjax = /** @class */ (function (_super) {
    __extends(MyAjax, _super);
    function MyAjax() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    MyAjax.prototype.doAjax = function (config) {
        var result = "";
        var xhr = new XMLHttpRequest();
        if (null == xhr) {
            xhr = new XMLHttpRequest("Microsoft.XMLHTTP");
        }
        if (null != xhr) {
            xhr.open(config.type, config.url, true);
            xhr.send(config.data);
            xhr.onreadystatechange = function () {
                console.log(xhr.statusText);
                var data = xhr.responseText;
                if (xhr.readyState === 4 && xhr.status === 200) {
                    if (config.dataType === 'json') {
                        result = JSON.parse(data);
                    }
                    else {
                        result = data;
                    }
                }
                else {
                    result = data; // error
                }
            };
        }
        else {
            result = "Your browser does not support XMLHTTP.";
        }
        console.log(result);
        return result;
    };
    MyAjax.prototype._delete = function (url, data) {
        return this.doAjax({
            type: 'delete',
            data: data,
            url: url,
            dataType: 'json'
        });
    };
    MyAjax.prototype._get = function (url, data) {
        return this.doAjax({
            type: 'get',
            data: data,
            url: url,
            dataType: 'json'
        });
    };
    MyAjax.prototype._post = function (url, data) {
        return this.doAjax({
            type: 'post',
            data: data,
            url: url,
            dataType: 'json'
        });
    };
    MyAjax.prototype._put = function (url, data) {
        return this.doAjax({
            type: 'put',
            data: data,
            url: url,
            dataType: 'json'
        });
    };
    return MyAjax;
}(TsAjax_1.TsAjax));
exports.MyAjax = MyAjax;
