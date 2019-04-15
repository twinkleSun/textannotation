$(function () {

    var test="1";
    ajaxdoTaskInfo(test);
    console.log("haha");
});

/**
 * 做任务上传自己的标签
 * @param doTaskData
 */
function ajaxdoTaskInfo(doTaskData) {

    $.ajax({
        url: "/test/test1",
        type: "post",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:doTaskData,
        success: function (data) {
            console.log(data);

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });


};