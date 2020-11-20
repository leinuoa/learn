"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var jquery_3_3_1_min_1 = require("../js/lib/jquery-3.3.1.min");
// import {MyAjax} from "./ex/ajax/MyAjax";
var username = jquery_3_3_1_min_1.default("#username").val();
var password = jquery_3_3_1_min_1.default('#password').val();
// let myAjax = new MyAjax();
// let data = myAjax._post("/sys/login",null);
jquery_3_3_1_min_1.default.ajax({
    url: "/sys/login",
    data: {
        "username": username,
        "password": password
    }, success: function () {
        jquery_3_3_1_min_1.default(this).addClass("done");
    }
});
