function form_submit() {
    let name = $("#username").val();
    let pwd = $('#password').val();
    $.ajax({
        url: "/sys/login",
        type: "POST",

        data: JSON.stringify({
            "username":name,
            "password":pwd
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            $(this).addClass("done");
            console.log(data);
        }
    });
}
