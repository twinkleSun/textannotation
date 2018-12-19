/**
 * Created by lenovo on 2018/12/15.
 */

layui.use(['jquery', 'layer'], function(){
    var $ = layui.$ //重点处
        ,layer = layui.layer;

    //后面就跟你平时使用jQuery一样
    $(function(){
        console.log("jinrule");
        initData();
        $("#ttt").click(function() {

        });
    });

    function initData() {
        var taskId={
            taskid:31
        };
        $.ajax({
            url: "/task/trycookie",
            type: "get",
            traditional: true,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "text",
            success: function (data) {
                console.log(data);
                // var taskList=data.data.task;
                // var taskHtml="";
                // for(var i=0;i<taskList.length;i++){
                //     taskHtml += '<tr><td>' + taskList[i].id + '</td><td>'
                //         + taskList[i].id + '</td><td>'
                //         + taskList[i].id+ '</td><td>'
                //         + taskList[i].id + '</td><td>'
                //         + taskList[i].id + '</td><td>'
                //         + taskList[i].id + '</td><td>'
                //         + taskList[i].id + '</td><td>'
                //         + taskList[i].id + '</td></tr>>';
                // }
                // $("#test tbody").append(taskHtml);

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status);
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);

            },
        });
    };
});
