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




    $("#register-submit").click(function() {

        var usernamer = $("#usernamer").val();
        var passwordr = $("#passwordr").val();
        var emailr = $("#emailr").val();

        var user = {
            username: usernamer,
            password: passwordr,
            email:emailr
        };
        console.log(user);
        $.ajax({
            url: "/user/register",
            type: "post",
            traditional: true,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",
            data: user,
            success: function (data) {
                console.log(data);
                if(data.status=="0"){
                    alert("请重新登陆");
                    $("#login-form").show();
                    $("#register-form").hide();
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
function userRegister() {
    $("#login-form").hide();
    $("#register-form").show();
};

function userLogin() {
    $("#login-form").show();
    $("#register-form").hide();
};