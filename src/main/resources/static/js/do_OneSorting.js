/**
 * Created by lenovo on 2019/1/11.
 */

$(function () {
    /**
     * 任务列表跳转时获得参数，形如http://localhost:8080/doTask3.html?taskid=7
     * @type {string}
     */
    var loc = location.href; //console.log("loc===="+loc);
    var taskidArr=loc.split("=");
    taskId = taskidArr[1];
    console.log(taskId);

    /**
     *ajax获取task详细信息
     */

    ajaxTaskInfo(taskId);
});

/**
 * 获取任务的详细信息
 * @param taskId
 */
function ajaxTaskInfo(taskId) {
    var taskid={
        tid:taskId
    };
    // var taskid={
    //     tid:"12"
    // };
    $.ajax({
        url: "task/getTaskInfo",
        type: "get",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:taskid,
        success: function (data) {

            console.log(data);
            // taskInfo=data.data; //console.log(taskInfo);
            var documentList =data.data.documentList;//console.log(documentList);
            var docId=documentList[0].did;//console.log(docId);
            // //labelList=data.data.labelList;//console.log(labelList);
            // taskType=data.data.type;
            //
            // /**
            //  * 页面上输入相关信息
            //  */
            // $("#taskTitle").html(taskInfo.title);
            // $("#taskDescription").html(taskInfo.description);
            // $("#taskOtherInfo").html(taskInfo.otherinfo);
            // $("#taskCreateTime").html(taskInfo.createtime);
            // $("#taskDeadline").html(taskInfo.deadline);
            // $("#pubUserName").html(data.pubUserName);
            // /**
            //  * 处理文件列表
            //  */
            // var taskFileListHtml="";
            // for(var i=0;i<documentList.length;i++){
            //     var taskFileHtml="";
            //     if(documentList[i].filetype==".txt"){
            //         taskFileHtml=  '<p><a id="taskfile-'+i+'" onclick="taskFileId(this.id)"><img src="images/TXT.png">'
            //             +documentList[i].filename+'</a></p>';
            //     }else if(documentList[i].filetype==".doc"){
            //         taskFileListHtml=  '<p><a id="taskfile-'+i+'" onclick="taskFileId(this.id)"><img src="images/DOC.png">'
            //             +documentList[i].filename+'</a></p>';
            //     }else if(documentList[i].filetype==".docx"){
            //         taskFileListHtml=  '<p><a id="taskfile-'+i+'" onclick="taskFileId(this.id)"><img src="images/DOCX.png">'
            //             +documentList[i].filename+'</a></p>';
            //     }
            //     taskFileListHtml=taskFileListHtml+taskFileHtml;
            // }
            // $("#taskFiles").append(taskFileListHtml);
            //
            //
            // /**
            //  * 处理标签
            //  */
            // // var labelListHtml="";
            // // for(var i=0;i<labelList.length;i++){
            // //     var labelInfoHtml='<span class="text-info" style="font-size: 18px;">' +
            // //         labelList[i].labelname +'、'+
            // //         '</span>';
            // //     labelListHtml=labelListHtml+labelInfoHtml;
            // // }
            // // $("#taskLabels").append(labelListHtml);
            //
            //
            // /**
            //  * 获取文件内容，提前加载
            //  */
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
        url: "instance/getSortingInstanceItem",
        type: "get",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:docid,
        success: function (data) {
            console.log(data);

            // instanceItem=data.instanceItem; //console.log(instanceItem);
            // instanceLength=instanceItem.length;
            // instanceLabel=data.instanceLabel;
            // item1Label=data.item1Label;
            // item2Label=data.item2Label;
            //
            // limitInstanceLabelNum=instanceItem[0].labelnum;
            //
            // // curInstanceIndex=0;
            // console.log(curInstanceIndex);
            // var itemList= instanceItem[0].itemList;
            //
            // limitItem1LabelNum=itemList[0].labelnum;
            // limitItem2LabelNum=itemList[1].labelnum;
            //
            // /**
            //  * 写入内容
            //  */
            //
            // paintContent(curInstanceIndex);
            // // $("#p-item-0").html(itemList[0].itemcontent);
            // // $("#p-item-1").html(itemList[1].itemcontent);
            //
            // paintLabelHtml(instanceLabel,item1Label,item2Label);
            //
            // /**
            //  * 左边ul导航点击定位
            //  */
            // var ul_html="";
            // for(var i=0;i<instanceLength;i++){
            //     var li_html="";
            //     if(i==curInstanceIndex){
            //         li_html=' <li class="active" id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
            //             '第' + (i+1) + '部分' + '</a></li>';
            //     }else{
            //         li_html=' <li  id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
            //             '第' + (i+1) + '部分' + '</a></li>';
            //     }
            //     ul_html=ul_html+li_html;
            //     ul_li_instanceIndex[i]="li-"+i;
            // }
            //
            // $("#ul-nav").html(ul_html);



        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });
}
