/**
 * 分类任务处理函数
 * Created by lenovo on 2018/12/20.
 */

/**
 * 获取任务的详细信息
 */
var taskInfo;//任务相关信息
var labelList;//label的列表
var documentList=new Array;//文件列表

/**
 * 做任务必传的值
 */
var taskId;//从页面跳转中获取
var docId;//从documentList中获取

/**
 * 页面处理要用的数据
 */
var paraIndex=0;//文章的段落数，document->content
var paraContent=new Array;//每段的内容
var paraId=new Array;//每段的数据库ID，做任务传值需要-->contentid
var curParaIndex=0;//当前正在做的页面的索引
var para_label=new Array;//二维数组，存储content-label的对应关系
var labelLength;//label的数量
var curLabelIndex=0;//当前被选中的label
var ajaxTag=0;

/**
 * 页面的控件ID
 * @type {Array}
 */
var panel_footer_index=new Array;//段落处页脚数字的跳转ID，用来控制显示第几段
var label_list_img=new Array;//标签前面图片的ID

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
        $("#row-div-dotask").show();
        $('#taskInfoPanel').collapse('hide');

    });


    /**
     * ajaxdoTask提交事件
     */
    $("#submit-paraLabel").click(function(){
        ajaxTag=0;//用来判定是否有失败的标签
        console.log(para_label);
        for(var i=0;i<labelLength;i++){
            console.log(curParaIndex);
            if(para_label[curParaIndex][i]>-1){
                var doTaskData={
                    dtid:"",
                    userid:"",
                    taskid :taskId,
                    contentid:paraId[curParaIndex],
                    labelId:labelList[i].lid,
                    conbegin:-1,
                    conend:-1
                };
                //console.log(doTaskData);
                /**
                 * 调用ajax上传标签
                 */
                ajaxdoTaskInfo(doTaskData);
            }
            if(ajaxTag!=0){
                alert("有标签添加失败");
            }
        }
    });

});

/**
 * 选中label的事件
 * @param obj
 */
function imgClick(obj) {
    var i=obj.substring(15,obj.length);
    curLabelIndex=parseInt(i);
    console.log(curLabelIndex);
    if($("#"+label_list_img[i]).hasClass("isAns")){
        $("#"+label_list_img[i]).attr("src","./images/notAns.png");
        $("#"+label_list_img[i]).addClass("notAns").removeClass("isAns");

        para_label[curParaIndex][curLabelIndex]=-1;
        curLabelIndex=-1;
        //console.log(para_label);
    }else{
        $("#"+label_list_img[i]).attr("src","./images/isAns.png");
        $("#"+label_list_img[i]).removeClass("notAns").addClass("isAns");
        para_label[curParaIndex][curLabelIndex]=curLabelIndex;
        //console.log(para_label);
    }
};


/**
 * 页脚1，2，3对应的点击事件
 * @param obj
 */
function footerIndex(obj) {
    var aIndex=obj.substring(19,obj.length);//console.log("当前的段落索引为："+aIndex);
    curParaIndex=aIndex;//当前段落的索引

    var curParaIndexNum =parseInt(curParaIndex);
    $("#span-index").html("第"+(curParaIndexNum+1)+"段");//设置内容面板的标题
    $("#p-para").html(paraContent[curParaIndex]);//设置内容

    for(var i=0;i<labelLength;i++){
        if(para_label[curParaIndexNum][i]>-1){
            $("#"+label_list_img[i]).attr("src","./images/isAns.png");
            $("#"+label_list_img[i]).removeClass("notAns").addClass("isAns");
        }else{
            $("#"+label_list_img[i]).attr("src","./images/notAns.png");
            $("#"+label_list_img[i]).addClass("notAns").removeClass("isAns");
        }
    }
};

/**
 * 获取任务的详细信息，taskInfo,labelList,documentList
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
            labelList=data.data.labelList;//console.log(labelList);
            documentList =data.data.documentList;//console.log(documentList);
            docId=documentList[0].did;//console.log(docId);


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
            ajaxDocContent(docId);

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });

    
}

/**
 * 获取文件的内容
 * @param docId
 */
function ajaxDocContent(docId) {
    var docid={
        docId: docId
    };
    $.ajax({
        url: "content/getContent",
        type: "get",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:docid,
        success: function (data) {

            /**
             * 左边文件内容的显示处理
             */
            paraIndex =data.data.length;//段落数
            var div_footer='<div class="text-center">';
            for(var i=0;i<paraIndex;i++){
                //todo:可以合并存
                para_label[i]=new Array;
                paraContent[i]=data.data[i].paracontent;//每段内容
                paraId[i]=data.data[i].cid;//console.log(paraId[i]);//每段内容的ID

                var panel_footer= '\xa0\xa0\xa0\xa0<a id="panel-footer-index-'+i+'" style="color: #0d96f2" onclick="footerIndex(this.id)">'
                    + (i+1)
                    +'</a>\xa0\xa0\xa0';//页脚 1，2，3数字

                panel_footer_index[i]="panel-footer-index-"+i;//页脚a标签对应的ID

                div_footer=div_footer+panel_footer;
            }
            div_footer=div_footer+'</div>';
            curParaIndex=0;
            $("#p-para").html(paraContent[0]);//显示第1段内容
            $("#div-para-footer").append(div_footer);//显示页脚

            /**
             * 调用label处理函数
             */
            labelHtml(labelList);


        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });
}

/**
 * label部分的初始化
 * @param labelList
 */
function labelHtml(labelList) {

    labelLength =labelList.length;

    var label_html="";
    for(var i=0;i<labelLength;i++){
        var list_html ='<li class="list-group-item">'
            +'<img class="notAns" src="./images/notAns.png" id="label-list-img-'+i+'" onclick="imgClick(this.id)">'
            +labelList[i].labelname
            +'</li>';
        label_html =label_html+list_html;
        label_list_img[i]="label-list-img-"+i;
    }

    $("#ul-label-list").append(label_html);
    /**
     * 将值都初始化为-1，选中label则替换-1
     */
    for(var i=0;i<paraIndex;i++){
        for(var j=0;j<labelLength;j++){
            para_label[i][j]=-1;
        }
    }

};

/**
 * 做任务上传自己的标签
 * @param doTaskData
 */
function ajaxdoTaskInfo(doTaskData) {

    $.ajax({
        url: "dotask/addDoTask",
        type: "post",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:doTaskData,
        success: function (data) {
            console.log(data);
            if(data.status=="0"){
                ajaxTag=ajaxTag+0;
            }else{
                ajaxTag=-1;
            }

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });


};