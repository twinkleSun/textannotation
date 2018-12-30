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
var listItem;
/**
 * 做任务必传的值
 */
var taskId;//从页面跳转中获取
var docId;//从documentList中获取

var ul_li_instanceIndex=new Array;


// (function($) {
//
// });

$(function ($) {

    /**
     * 任务列表跳转时获得参数，形如http://localhost:8080/doTask3.html?taskid=7
     * @type {string}
     */
    var loc = location.href; //console.log("loc===="+loc);
    var taskidArr=loc.split("=");
    taskId = taskidArr[1];

    $.fn.onLine = function (options) {

        var box = this;
        var regainCanvas = options.regainCanvas;
        var linewidth = 2, linestyle = "#0C6";//连线绘制--线宽，线色
        var part1, part2;
        /**
         * 对上下两个div进行初始化，设定绘画的位置
         */

        console.log(this);

        part1 = box.find(".showleft");
        part2 = box.find(".showright");
        //初始化赋值 列表内容
        box.find(".showleft").children("li").each(function (index, element) {
            $(this).attr({
                group: "gpl",
                left: $(this).position().left + $(this).outerWidth(),
                top: $(this).position().top + $(this).outerHeight() / 2,
                sel: "0",
                check: "0"
            });
        });
        box.find(".showright").children("li").each(function (index, element) {

            $(this).attr({
                group: "gpr",
                left:box.find(".showright").position().left+$(this).position().left,
                top: $(this).position().top + $(this).outerHeight() / 2,
                sel: "0",
                check: "0"
            });
            //console.log(box.find(".showright").position().left);
        });


        part1.attr('first',0);//初始赋值 列表内容容器
        part2.attr('first',0);
        //canvas 赋值
        var canvas =box.find(".canvas")[0];  //获取canvas  实际连线标签
        canvas.width=box.find(".show").width();//canvas宽度等于div容器宽度
        canvas.height=box.find(".show").height();



        //canvas 追加2d画布
        var context = canvas.getContext('2d');  //canvas追加2d画图
        var lastX,lastY;//存放遍历坐标
        function strockline(){//绘制方法
            context.clearRect(0,0,box.find(".show").width(),box.find(".show").height());//整个画布清除
            context.save();
            context.beginPath();
            context.lineWidth = linewidth;

            for (var i=0;i<ms.length;i++) {  //遍历绘制
                lastX = mx[i];
                lastY = my[i];
                if (ms[i] == 0) {
                    context.moveTo(lastX,lastY);
                } else {
                    context.lineTo(lastX,lastY);
                }
            }
            context.strokeStyle = linestyle;
            context.stroke();
            context.restore();
        };
    };



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
            /**
             * 获取文件内容，提前加载
             */

            console.log(data);
            taskInfo=data.data; //console.log(taskInfo);
            documentList =data.data.documentList;//console.log(documentList);
            docId=documentList[0].did;//console.log(docId);
            ajaxDocInstanceItem(docId);

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
            // var labelListHtml="";
            // for(var i=0;i<labelList.length;i++){
            //     var labelInfoHtml='<span class="text-info" style="font-size: 18px;">' +
            //         labelList[i].labelname +'、'+
            //         '</span>';
            //     labelListHtml=labelListHtml+labelInfoHtml;
            // }
            // $("#taskLabels").append(labelListHtml);




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
        docId: docId
    };
    $.ajax({
        url: "instance/getInstanceListitem",
        type: "get",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:docid,
        success: function (data) {
            console.log(data);

            instanceItem=data.instanceItem; //console.log(instanceItem);
            instanceLength=instanceItem.length;
            listItem=instanceItem[0].listitems;


            var left_Html="";
            var right_Html="";
            for(var i=0;i<listItem.length;i++){

                var lHtml="";
                var rHtml="";
                if(listItem[i].listindex=="1"){
                    lHtml= '<li class="showitem" id="left-'+i+'" onclick="test2(this.id)">' +listItem[i].litemcontent
                        '</li>';
                    left_Html=left_Html+lHtml;
                }else if(listItem[i].listindex=="2"){
                    rHtml='<li class="showitem" id="right-'+i+'" onclick="test3(this.id)">' +listItem[i].litemcontent
                        ' </li>';
                    right_Html=right_Html+rHtml;
                }
            }

            var f1=0;
            var f2=0;
            if(f1==0){
                $("#left1").html(left_Html);
                $("#right2").html(right_Html);
                f2=1;
            }
            if(f2==1){
                var obj = $(".demo");
                console.log(obj);
                var size = obj.size();console.log(size);
                for(var i=0; i<size; i++ ){
                    obj.eq(i).onLine({
                        regainCanvas: true
                    });
                }


            }




            /**
             * 左边ul导航点击定位
             */
            curInstanceIndex=0;
            var itemList= instanceItem[0].itemList;
            // $("#p-item-0").html(itemList[0].itemcontent);
            // $("#p-item-1").html(itemList[1].itemcontent);


            var ul_html="";
            for(var i=0;i<instanceLength;i++){
                var li_html="";
                if(i==0){
                    li_html=' <li class="active" id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
                        '第' +
                        (i+1) +
                        '部分' +
                        '</a></li>';
                }else{
                    li_html=' <li  id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
                        '第' +
                        (i+1) +
                        '部分' +
                        '</a></li>';
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