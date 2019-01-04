/**
 * 分类任务处理函数
 * Created by lenovo on 2018/12/20.
 */

/**
 * 获取任务的详细信息
 */
var taskInfo;//任务相关信息
var documentList=new Array;//文件列表
var labelList;//label的列表
var taskType;

var instanceItem;//文件内容
var instanceLength//instance长度
var curInstanceIndex;//当前的instanceIndex

/**
 * 做任务必传的值
 */
var taskId;//从页面跳转中获取
var docId;//从documentList中获取

var ul_li_instanceIndex=new Array;

$(function () {

    /**
     * 任务列表跳转时获得参数，形如http://localhost:8080/doTask3.html?taskid=7
     * @type {string}
     */
    var loc = location.href; //console.log("loc===="+loc);
    var taskidArr=loc.split("=");
    taskId = taskidArr[1];

    /**
     *ajax获取task详细信息
     */
    ajaxTaskInfo(taskId);

    /**
     * 点击我要做任务显示的面板，
     * 同时将任务详细信息折叠面板设为hide
     */
    $("#input-dotask").click(function(){
        $("#div-instance-item").show();

        $('#taskInfoPanel').collapse('hide');

    });


    $("#submit-item").click(function(){
        var item_label_0=$("#input-item-0").val();
        var item_label_1=$("#input-item-1").val();

        // var doTaskData={
        //     dtInstid:"",
        //     userId:"",
        //     taskId :taskId,
        //     instanceId:instanceItem[curInstanceIndex].insid,
        //     itemId1:instanceItem[curInstanceIndex].itemList[0].itid,
        //     item_label1:item_label_0,
        //     itemId2:instanceItem[curInstanceIndex].itemList[1].itid,
        //     item_label2:item_label_1
        // };  console.log(doTaskData);

        var item1Labels=[20,21,22];
        var item2Labels=[20,21,22];
        var instanceLabels=[28,21,22];

        var doTaskData={
            dtInstid:"",
            userId:"",
            taskId :taskId,
            instanceId:instanceItem[curInstanceIndex].insid,
            itemId1:instanceItem[curInstanceIndex].itemList[0].itid,
            item1Labels:item1Labels,
            itemId2:instanceItem[curInstanceIndex].itemList[1].itid,
            item2Labels:item2Labels,
            instanceLabels:instanceLabels

        };  console.log(doTaskData);

        ajaxdoTaskInfo(doTaskData);

    });




});


/**
 * 获取任务的详细信息
 * @param taskId
 */
function ajaxTaskInfo(taskId) {
    var taskid={
        tid:taskId
    };

    $.ajax({
        url: "task/getTaskInfo",
        type: "get",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:taskid,
        success: function (data) {

            console.log(data);
            taskInfo=data.data; //console.log(taskInfo);
            documentList =data.data.documentList;//console.log(documentList);
            docId=documentList[0].did;//console.log(docId);
            labelList=data.data.labelList;//console.log(labelList);
            taskType=data.data.type;

            /**
             * 页面上输入相关信息
             */
            $("#taskTitle").html(taskInfo.title);
            $("#taskDescription").html(taskInfo.description);
            $("#taskOtherInfo").html(taskInfo.otherinfo);
            $("#taskCreateTime").html(taskInfo.createtime);
            $("#taskDeadline").html(taskInfo.deadline);
            $("#pubUserName").html(data.pubUserName);
            /**
             * 处理文件列表
             */
            var taskFileListHtml="";
            for(var i=0;i<documentList.length;i++){
                var taskFileHtml="";
                if(documentList[i].filetype==".txt"){
                    taskFileHtml=  '<p><a id="taskfile-'+i+'" onclick="taskFileId(this.id)"><img src="images/TXT.png">'
                        +documentList[i].filename+'</a></p>';
                }else if(documentList[i].filetype==".doc"){
                    taskFileListHtml=  '<p><a id="taskfile-'+i+'" onclick="taskFileId(this.id)"><img src="images/DOC.png">'
                        +documentList[i].filename+'</a></p>';
                }else if(documentList[i].filetype==".docx"){
                    taskFileListHtml=  '<p><a id="taskfile-'+i+'" onclick="taskFileId(this.id)"><img src="images/DOCX.png">'
                        +documentList[i].filename+'</a></p>';
                }
                taskFileListHtml=taskFileListHtml+taskFileHtml;
            }
            $("#taskFiles").append(taskFileListHtml);


            /**
             * 处理标签
             */
            var labelListHtml="";
            for(var i=0;i<labelList.length;i++){
                var labelInfoHtml='<span class="text-info" style="font-size: 18px;">' +
                    labelList[i].labelname +'、'+
                    '</span>';
                labelListHtml=labelListHtml+labelInfoHtml;
            }
            $("#taskLabels").append(labelListHtml);


            /**
             * 获取文件内容，提前加载
             */
            ajaxDocInstanceItem(docId);

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });


}

/**
 * 获取文件的内容
 * @param docId
 */
function ajaxDocInstanceItem(docId) {
    var docid={
        docId: docId,
        userid:""
    };
    $.ajax({
        url: "instance/getInstanceItem",
        type: "get",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:docid,
        success: function (data) {
            console.log(data);

            instanceItem=data.instanceItem; console.log(instanceItem);
            instanceLength=instanceItem.length;

            /**
             * 左边ul导航点击定位
             */
            curInstanceIndex=0;
            var itemList= instanceItem[0].itemList;
            $("#p-item-0").html(itemList[0].itemcontent);
            $("#p-item-1").html(itemList[1].itemcontent);


            var ul_html="";
            for(var i=0;i<instanceLength;i++){
                var li_html="";
                if(i==0){
                    li_html=' <li class="active" id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
                        '第' + (i+1) + '部分' + '</a></li>';
                }else{
                    li_html=' <li  id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
                        '第' + (i+1) + '部分' + '</a></li>';
                }
                ul_html=ul_html+li_html;
                ul_li_instanceIndex[i]="li-"+i;
            }

            $("#ul-nav").append(ul_html);



        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });
}

function curInstanceId(obj) {
    $("#"+ul_li_instanceIndex[curInstanceIndex]).removeClass("active");
    var i_Str=obj.substring(2);  console.log(i_Str);
    curInstanceIndex=parseInt(i_Str);
    $("#"+ul_li_instanceIndex[curInstanceIndex]).addClass("active");

    var itemList= instanceItem[curInstanceIndex].itemList;
    $("#p-item-0").html(itemList[0].itemcontent);
    $("#p-item-1").html(itemList[1].itemcontent);

}

/**
 * 做任务上传自己的标签
 * @param doTaskData
 */
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

            alert("提交成功！");

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });


};

function testLabel(obj) {
    if($("#"+obj).attr("flag")==0){
        console.log(obj);
        $("#"+obj).attr("flag","1");
        $("#"+obj).addClass("label-warning").removeClass("label-info");
    }else if($("#"+obj).attr("flag")==1){
        console.log(obj);
        $("#"+obj).attr("flag","0");
        $("#"+obj).addClass("label-info").removeClass("label-warning");
    }


};