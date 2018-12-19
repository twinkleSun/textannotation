/**
 * Created by lenovo on 2018/12/4.
 */

$(function(){
    $("#login-submit").click(function() {

        var username = $("#username").val();
        var password = $("#password").val();

        var user = {
            username: username,
            password: password
        };
        console.log(user);
        $.ajax({
            url: "/user/login",
            type: "post",
            traditional: true,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",
            data: user,
            success: function (data) {
                console.log(data);
                if(data.status=="0"){
                    location.href = "/homepage.html";
                }else{
                    alert(data.msg);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // console.log(XMLHttpRequest.status);
                // console.log(XMLHttpRequest.readyState);
                // console.log(textStatus);
            },
        });

    });

});
