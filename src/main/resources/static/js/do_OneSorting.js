/**
 * Created by lenovo on 2019/1/11.
 */
/**
 * 获取任务的详细信息
 */
var taskType;
var taskInfo;//任务相关信息
var documentList=new Array;//文件列表
var instanceItem;//文件内容
var instanceLength//instance长度
var itemList=new Array;//instance里面listitem的内容
var alreadyDone=new Array;

/**
 * 当前的值
 */
var curInstanceIndex=0;//当前的instanceIndex

/**
 * 做任务必传的值
 */
var taskId;//从页面跳转中获取
var docId;//从documentList中获取

/**
 * 存放ID
 * @type {Array}
 */
var ul_li_instanceIndex=new Array;

$(function () {
    /**
     * 任务列表跳转时获得参数，形如http://localhost:8080/doTask3.html?taskid=7
     * @type {string}
     */
    var loc = location.href; //console.log("loc===="+loc);
    var taskidArr=loc.split("=");
    taskId = taskidArr[1]; //console.log(taskId);


    /**
     *ajax获取task详细信息
     */
    ajaxTaskInfo(taskId);

    /**
     * 点击我要做任务显示的面板，
     * 同时将任务详细信息折叠面板设为hide
     */
    $("#btn-dotask").click(function(){
       // $("#op-dotask").hide();
       // $("#op-button").show();
        $('#taskInfoPanel').collapse('hide');

    });

    // var itemId=[39,37];
    // var newIndex=[1,2];
     //console.log(doTaskData);


    var itemId=new Array;
    var newIndex=new Array;
    $("#submit-sorting").click(function(){
           var ulHtml=document.getElementById('right-sorting');
           var rightLiLength=ulHtml.children.length;
           if(rightLiLength!=itemList.length){
               alert("请全部排序完成后再提交");
           }else{
               for(var i=0;i<rightLiLength;i++){
                   newIndex[i]=i+1;
                   itemId[i]=itemList[parseInt(ulHtml.children[i].getAttribute('drag-id'))].itid;
               }

               var doTaskData={
                   dtInstid:"",
                   userId:"",
                   taskId :taskId,
                   dotime:"",
                   comptime:"",
                   dtstatus:"进行中",
                   instanceId:instanceItem[curInstanceIndex].insid,
                   itemIds:itemId,
                   newIndex:newIndex
               };
               addSortingTask(doTaskData);
               //console.log(doTaskData);
               // console.log(newIndex);
               // console.log(itemId);
               // console.log(itemList);

           }

           // console.log(rightLiLength);
           // console.log(ulHtml.children[0].getAttribute('drag-id'));
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

            /**
             * 获取文件内容，提前加载
             */
            taskInfo=data.data; //console.log(taskInfo);
            documentList =data.data.documentList;//console.log(documentList);
            docId=documentList[0].did;//console.log(docId);
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

            ajaxDocSortingInstanceItem(docId);

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });


}

/**
 * 获取文件的内容
 * @param docId
 */
function ajaxDocSortingInstanceItem(docId) {
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

            instanceItem=data.instanceItem; //console.log(instanceItem);
            instanceLength=instanceItem.length;
            for(var i=0;i<instanceLength;i++){
                instanceItem[parseInt(data.instanceItem[i].insindex)-1]=data.instanceItem[i];
            }
            itemList= instanceItem[curInstanceIndex].itemList;
            alreadyDone=instanceItem[curInstanceIndex].alreadyDone;

            $("#right-sorting").html("");

            /**
             * 写入内容
             */
            //console.log(curInstanceIndex);
            paintSortingContent(itemList,alreadyDone);

            /**
             * 左边ul导航点击定位
             */
            var ul_html="";
            for(var i=0;i<instanceLength;i++){
                var li_html="";
                if(i==curInstanceIndex){
                    li_html=' <li class="active" id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
                        '第' + (i+1) + '部分' + '</a></li>';
                }else{
                    li_html=' <li  id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
                        '第' + (i+1) + '部分' + '</a></li>';
                }
                ul_html=ul_html+li_html;
                ul_li_instanceIndex[i]="li-"+i;
            }
            $("#ul-nav").html(ul_html);



        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });
}
/**
 * 左边第几部分导航的点击事件
 * @param obj
 */
function curInstanceId(obj) {

    /**
     * 移除原控件的active属性
     */
    $("#"+ul_li_instanceIndex[curInstanceIndex]).removeClass("active");
    var i_Str=obj.substring(2);  //console.log(i_Str);

    /**
     * 设置当前控件的active属性
     */
    curInstanceIndex=parseInt(i_Str);
    $("#"+ul_li_instanceIndex[curInstanceIndex]).addClass("active");

    /**
     * 重新加载右边的做任务界面
     */

    $("#right-sorting").html("");
    itemList=instanceItem[curInstanceIndex].itemList;
    alreadyDone=instanceItem[curInstanceIndex].alreadyDone;

    paintSortingContent(itemList,alreadyDone);
}

/**
 * 做任务上传自己的标签
 * @param doTaskData
 */
function addSortingTask(doTaskData) {

    $.ajax({
        url: "dotask/addSortingTask",
        type: "post",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:doTaskData,
        success: function (data) {
            console.log(data);
            ajaxDocSortingInstanceItem(docId);

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });


};

/**
 * 绘制可排序的面板
 * @param itemList
 * @param alreadyDone
 */
function paintSortingContent(itemList,alreadyDone) {

    itemList=instanceItem[curInstanceIndex].itemList;
    alreadyDone=instanceItem[curInstanceIndex].alreadyDone;

    var leftItem=new Array;

    var alreadyItemId=new Array;
    var alreadyNewIndex=new Array;
    if(alreadyDone.length>0){

        for(var i=0;i<alreadyDone.length;i++){
            alreadyItemId[i]=alreadyDone[i].item_id;
            alreadyNewIndex[i]=alreadyDone[i].newindex;
        }

        for(var i=0;i<itemList.length;i++){
            console.log(alreadyItemId);
            if(alreadyItemId.indexOf(itemList[i].itid)!=-1){

                itemList[i].itemindex=alreadyNewIndex[alreadyItemId.indexOf(itemList[i].itid)];
                console.log(alreadyItemId.indexOf(itemList[i].itid));
            }

            leftItem[parseInt(itemList[i].itemindex)]='<li drag-id="'+i+'"><span class="drag-handle">&#9776;</span>' +
                itemList[i].itemcontent+'</li>';
        }
    }else{
        for(var i=0;i<itemList.length;i++){
            leftItem[parseInt(itemList[i].itemindex)]='<li drag-id="'+i+'"><span class="drag-handle">&#9776;</span>' +
                itemList[i].itemcontent+'</li>';
        }
    }

    // for(var i=0;i<itemList.length;i++){
    //
    //     rightItem[parseInt(itemList[i].itemindex)]='<li drag-id="'+i+'"><span class="drag-handle">&#9776;</span>' +
    //         itemList[i].itemcontent+'</li>';
    // }



    var leftHtml="";
    for(var i=1;i<leftItem.length;i++){
        leftHtml=leftHtml+leftItem[i];
    }

    $("#left-sorting").html(leftHtml);



}


