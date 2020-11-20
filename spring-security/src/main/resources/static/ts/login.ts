import $ from "../js/lib/jquery-3.3.1.min";
// import {MyAjax} from "./ex/ajax/MyAjax";

let username = $("#username").val();
let password = $('#password').val();

// let myAjax = new MyAjax();
// let data = myAjax._post("/sys/login",null);



$.ajax({
    url: "/sys/login",
    data: {
        "username":username,
        "password":password
    }, success: function () {
        $(this).addClass("done");
    }
});