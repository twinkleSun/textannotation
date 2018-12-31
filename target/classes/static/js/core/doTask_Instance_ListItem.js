/**
 * 文本关系配对处理函数
 * Created by lenovo on 2018/12/20.
 */

/**
 * 获取任务的详细信息
 */
var taskInfo;//任务相关信息
var documentList=new Array;//文件列表
var instanceItem;//文件内容
var instanceLength//instance长度
var listItem= new Array;//instance里面listitem的内容

/**
 * 当前的值
 */
var curInstanceIndex;//当前的instanceIndex
var x1;//左边
var x2;
var y1;//右边
var y2;
var curLeftId;
var curRightId;

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


/**
 * 存储的值
 */
var lineLR=new Array;
var tempNum=new Array;

$(function ($) {

    /**
     * 任务列表跳转时获得参数，形如http://localhost:8080/doTask3.html?taskid=7
     * @type {string}
     */
    var loc = location.href; //console.log("loc===="+loc);
    var taskidArr=loc.split("=");
    taskId = taskidArr[1];

    /**
     * 对连线的li进行初始化
     * @param options
     */
    $.fn.onLine = function (options) {
        var box = this; //console.log(this);

        /**
         * 对上下两个div进行初始化，设定绘画的位置
         */
        box.find(".showleft").children("li").each(function (index, element) {
            $(this).attr({
                left: $(this).position().left + $(this).outerWidth(),
                top: $(this).position().top + $(this).outerHeight() / 2
            });
        });
        box.find(".showright").children("li").each(function (index, element) {

            $(this).attr({
                left:box.find(".showright").position().left+$(this).position().left,
                top: $(this).position().top + $(this).outerHeight() / 2
            });

        });

        var canvas =box.find(".canvas")[0];  //获取canvas  实际连线标签
        canvas.width=box.find(".show").width();//canvas宽度等于div容器宽度
        canvas.height=box.find(".show").height();

        var canvas2 =box.find(".backcanvas")[0];  //获取canvas  实际连线标签
        canvas2.width=box.find(".show").width();//canvas宽度等于div容器宽度
        canvas2.height=box.find(".show").height();

        // $("#backOneStep").click(function(){
        //     var context = canvas.getContext('2d');  //canvas追加2d画图
        //     context.clearRect(0,0,box.find(".show").width(),box.find(".show").height());//整个画布清除
        //
        // });

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
        $("#div-doTask").show();

        $('#taskInfoPanel').collapse('hide');
    });


    /**
     * 后退一步，等于重新画页面
     * todo:看看有没有其他更好的方法
     */
    $("#backOneStep").click(function(){
        /**
         * 先清除整个画布
         * @type {Element}
         */
        var canvas = document.getElementById('canvas-front');
        var cWidth=$("#canvas-front").attr("width");
        var cHeight=$("#canvas-front").attr("height");
        var context = canvas.getContext('2d');
        context.clearRect(0,0,cWidth,cHeight);

        /**
         * 临时存储连线的点和几条线
         */
        var tempArr = jQuery.extend(true, {}, lineLR[curInstanceIndex]); //console.log(tet);
        var tempN=tempNum[curInstanceIndex]-1;

        /**
         * 清除原数据
         * @type {number}
         */
        tempNum[curInstanceIndex]=0;
        lineLR[curInstanceIndex]=new Array;

        /**
         * 重新画图
         */
        for(var i=0;i<tempN;i++){
            for (var property in tempArr[i]){
                drawLeft(property);// console.log(property);
                drawRight(tempArr[i][property]);
            }
        }

    });

    /**
     * 提交所有的线
     * todo:设置一个变量存储这个已经提交的线
     */
    $("#submit-instance").click(function(){

        /**
         * 先清除整个画布
         * @type {Element}
         */
        var canvas = document.getElementById('canvas-front');
        var cWidth=$("#canvas-front").attr("width");
        var cHeight=$("#canvas-front").attr("height");
        var context = canvas.getContext('2d');
        context.clearRect(0,0,cWidth,cHeight);

        /**
         * 临时存储连线的点和几条线
         */
        var tempArr = jQuery.extend(true, {}, lineLR[curInstanceIndex]); //console.log(tet);
        var tempN=tempNum[curInstanceIndex];

        /**
         * 清除原数据
         * @type {number}
         */
        tempNum[curInstanceIndex]=0;
        lineLR[curInstanceIndex]=new Array;

        /**
         * 重新画图
         */

        var fRes=0;
        for(var i=0;i<tempN;i++){
            for (var property in tempArr[i]){
                drawLeftBack(property); //console.log(property);
                drawRightBack(tempArr[i][property]);

                var doTaskData={
                    dtInstid:"",
                    taskId :taskId,
                    instanceId:instanceItem[curInstanceIndex].insid,
                    aListitemId:listItem[property.substring(5)].liid,
                    bListitemId:listItem[tempArr[i][property].substring(6)].liid
                };console.log(doTaskData);

                ajaxdoTaskInfo(doTaskData,fRes);
                if(fRes==-1){
                    alert("提交失败");
                }
            }
        }

        tempNum[curInstanceIndex]=0;
        lineLR[curInstanceIndex]=new Array;

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
            lineLR[0] = new Array;
            tempNum[0]=0;
            /**
             * 将第一部分内容写入dotask面板
             */
            paintDoTask(listItem);

            curInstanceIndex=0;
            /**
             * 左边ul导航点击定位
             */
            var ul_html="";
            for(var i=0;i<instanceLength;i++){
                var li_html="";
                if(i==0){
                    li_html=' <li class="active" id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
                        '第'+(i+1)+'部分'+'</a></li>';
                }else{
                    li_html=' <li  id="li-'+i+'"><a id="a-'+i+'" onclick="curInstanceId(this.id)">' +
                        '第'+(i+1)+'部分'+'</a></li>';
                }
                ul_html=ul_html+li_html;
                ul_li_instanceIndex[i]="li-"+i;
            }
            $("#ul-nav").append(ul_html);

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
    lineLR[curInstanceIndex] = new Array;

    tempNum[curInstanceIndex]=0;
    listItem=instanceItem[curInstanceIndex].listitems;
    paintDoTask(listItem);
}

/**
 * 根据curInstanceIndex输出右边连线的面板
 * @param listItem
 */
function paintDoTask(listItem) {
    var left_Html="";
    var right_Html="";
    for(var i=0;i<listItem.length;i++){
        /**
         * 设置一个flag用来标注这个词标注没，todo:不确定用不用得到
         * @type {number}
         */
        listItem[i].flag=0;

        /**
         * 分别写入左右两边
         */
        if(listItem[i].listindex=="1"){
            var lt= '<li class="showitem" id="left-'+i+'" onclick="drawLeft(this.id)">'
                +listItem[i].litemcontent+'</li>';
            left_Html=left_Html+lt;
        }else if(listItem[i].listindex=="2"){
            var rt='<li class="showitem" id="right-'+i+'" onclick="drawRight(this.id)">'
                +listItem[i].litemcontent+'</li>';
            right_Html=right_Html+rt;
        }
    }

    /**
     * 顺序执行，todo:会不会出现不是顺序执行的情况？
     * @type {number}
     */

    $("#div-left").html(left_Html);
    $("#div-right").html(right_Html);

    /**
     * todo:每次都重新加载canvas,等下看下如何保存已经连接的值
     * @type {string}
     */
    var canvasHtml=' <canvas class="canvas" id="canvas-front"></canvas>'
    +'<canvas class="backcanvas" id="canvas-back"></canvas>';
    $("#showCb").append(canvasHtml);

    /**
     *调用onLine进行元素初始化，设置元素的left和top方便连线
     * @type {*}
     */
    var obj = $(".dotaskDiv");  //console.log(obj);
    for(var i=0; i<obj.size(); i++ ){
        obj.eq(i).onLine({
            // regainCanvas: true
        });
    }
}

/**
 * 点击左边做任务面板的事件
 * @param obj
 */
function drawLeft(obj) {
    curLeftId=obj;
    x1=$("#"+obj).attr("left");
    x2=$("#"+obj).attr("top");
    $("#"+obj).css("background-color","#F96");



}

/**
 * 点击右边做任务面板的事件
 * @param obj
 */
function drawRight(obj) {
    curRightId=obj;

    var tempObj={};
    tempObj[curLeftId]=curRightId;

    if(JSON.stringify(lineLR[curInstanceIndex]).indexOf(JSON.stringify(tempObj))!=-1){
        console.log("已经划过了");
    }else {
        y1=$("#"+obj).attr("left");
        y2=$("#"+obj).attr("top");

        var linewidth = 2, linestyle = "#0C6";//连线绘制--线宽，线色
        var canvas = document.getElementById('canvas-front');
        var context = canvas.getContext('2d');
        context.lineWidth=linewidth;
        context.strokeStyle = linestyle;

        context.beginPath();
        context.moveTo(x1, x2);
        context.lineTo(y1, y2);
        context.stroke();
        context.restore();

        lineLR[curInstanceIndex][tempNum[curInstanceIndex]]={};
        lineLR[curInstanceIndex][tempNum[curInstanceIndex]][curLeftId]=curRightId;
        tempNum[curInstanceIndex]++;
        console.log(lineLR[curInstanceIndex]);

        /**
         * todo:取值调用接口
         * todo:查看正在进行中的任务
         * @type {Array}
         */


    }

    $("#"+curLeftId).css("background-color","#5bc0de");

};



/**
 * 点击左边做任务面板的事件
 * @param obj
 */
function drawLeftBack(obj) {
    curLeftId=obj;
    x1=$("#"+obj).attr("left");
    x2=$("#"+obj).attr("top");
}

/**
 * 点击右边做任务面板的事件
 * @param obj
 */
function drawRightBack(obj) {
    curRightId=obj;

    var tempObj={};
    tempObj[curLeftId]=curRightId;

    if(JSON.stringify(lineLR[curInstanceIndex]).indexOf(JSON.stringify(tempObj))!=-1){
        console.log("已经划过了");
    }else {
        y1=$("#"+obj).attr("left");
        y2=$("#"+obj).attr("top");

        var linewidth = 2, linestyle = "#5bc0de";//连线绘制--线宽，线色
        var canvas = document.getElementById('canvas-back');
        var context = canvas.getContext('2d');
        context.lineWidth=linewidth;
        context.strokeStyle = linestyle;

        context.beginPath();
        context.moveTo(x1, x2);
        context.lineTo(y1, y2);
        context.stroke();
        context.restore();

        lineLR[curInstanceIndex][tempNum[curInstanceIndex]]={};
        lineLR[curInstanceIndex][tempNum[curInstanceIndex]][curLeftId]=curRightId;
        tempNum[curInstanceIndex]++;
        console.log(lineLR[curInstanceIndex]);

        /**
         * todo:取值调用接口
         * todo:查看正在进行中的任务
         * @type {Array}
         */


    }

    $("#"+curLeftId).css("background-color","#5bc0de");

};


// var keys=[];
// for (var property in tempArr[i]) {
//     keys.push(property);
// }
// console.log(keys);

/**
 * 做任务上传自己的标注内容
 * @param doTaskData
 */
function ajaxdoTaskInfo(doTaskData,fRes) {
    $.ajax({
        url: "dotask/addListItem",
        type: "post",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:doTaskData,
        success: function (data) {
            if(data.status!=0){
                fRes=-1;
            }
            //console.log(data);
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {

        },
    });
};