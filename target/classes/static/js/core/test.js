/**
 * Created by lenovo on 2018/12/27.
 */

$(function () {

    var doTaskData={
        dtInstid:"",
        userId:"",
        taskId :1,
        instanceId:1,
        itemId:2,
        labelId:1,
        item_label:"ceshiyong"
    };


    var doTaskData2={
        dtInstid:"",
        taskId :1,
        instanceId:1,
        aListitemId:2,
        bListitemId:1
    };
    //ajaxdoTaskInfo2(doTaskData2);
    //ajaxdoTaskInfo(doTaskData);
});

function ajaxdoTaskInfo(doTaskData) {

    $.ajax({
        url: "dotask/addInstanceItem",
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


function ajaxdoTaskInfo2(doTaskData2) {

    $.ajax({
        url: "dotask/addListItem",
        type: "post",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:doTaskData2,
        success: function (data) {
            console.log(data);


        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });


};