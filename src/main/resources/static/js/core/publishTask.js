/**
 * Created by lenovo on 2018/12/6.
 */
/**
 * Created by lenovo on 2018/12/4.
 */

var docId = "";//添加文件后取得文件的ID
var tagNum = 0;//添加的标签数量
var tagContent = new Array;//标签内容，#分隔，比如"a#b#c"
var taskType = "";//文件的类型


$(function(){

    //tag初始化
    var tag = new Tag("tagValue");
    tag.initView();

    /**
     * 监听select里任务类型的变化
     * 根据任务类型选择单文件还是多文件上传
     */
    $("#type").change(function(){

        taskType=$('#type option:selected').text();//获取标签名
        var taskValue=$('#type option:selected').val();//获取标签value
        if(taskValue=="1" || taskValue=="2"){
            $("#singleFileDiv").show();
            $("#multiFileDiv").hide();
        }else if(taskValue=="3" || taskValue=="4"){
            $("#singleFileDiv").hide();
            $("#multiFileDiv").show();
        }else{
            $("#singleFileDiv").hide();
            $("#multiFileDiv").hide();
        }
    });

    /**
     * 单文件上传的监听事件
     */
    $('input[id=sf]').change(function() {
        var sfile = document.getElementById("sf").files;

        //console.log(sfile[0].name);
        $('#sfileCover').val(sfile[0].name);
    });

    /**
     * 多文件上传的监听事件
     */
    $('input[id=mf]').change(function() {
        var mfile = document.getElementById("mf").files;
        var mfilename="";
        for(var i=0;i<mfile.length;i++){
            mfilename=mfilename+mfile[i].name+";;";
        }

        $('#mfileCover').val(mfilename);
    });

    /**
     * 上传文件，这里是单文件上传
     */
    $("#sf-upload").click(function() {
        var sformData = new FormData();
        sformData.append("files", $("#sf")[0].files[0]);
        sformData.append("userid","");

        $.ajax({
            type: 'POST',
            url: "/file/addsinglefile",
            data: sformData,
            contentType: false,
            processData: false,//这个很有必要，不然不行
            dataType: "json",
            mimeType: "multipart/form-data",
            success: function (data) {
                docId=data.data.docId;//这是上传成功的文件ID
                //console.log(docId);

                var layer=layui.layer;
                layer.open({
                    type:0,
                    content:"文件上传成功！",
                    icon:1
                })

            },error: function (XMLHttpRequest, textStatus, errorThrown) {
                  // console.log(XMLHttpRequest.status);
                  // console.log(XMLHttpRequest.readyState);
                  // console.log(textStatus);
                alert("上传失败!");
            },

        });

    });

    /**
     * 多文件上传
     */
    $("#mf-upload").click(function() {
        var mformData = new FormData();
        var mfileup = document.getElementById("mf").files;
        for(var i=0;i<mfileup.length;i++){
            mformData.append("files", $("#mf")[0].files[i]);
        }

        mformData.append("title","test");
        mformData.append("description","test");
        mformData.append("type","test");
        mformData.append("userid","");
        mformData.append("taskcompstatus","test");
        mformData.append("otherinfo","test");
        mformData.append("label","test");
        $.ajax({
            type: 'POST',
            url: "/task/addTask",
            data: mformData,
            contentType: false,
            processData: false,//这个很有必要，不然不行
            dataType: "text",
            mimeType: "multipart/form-data",
            success: function (data) {
                //todo:后台还没写好，先这样
                console.log(data);

            },error: function (XMLHttpRequest, textStatus, errorThrown) {
                // console.log(XMLHttpRequest.status);
                // console.log(XMLHttpRequest.readyState);
                // console.log(textStatus);
                alert("上传失败!");
            },

        });

    });

    /**
     * 提交任务
     */
    $("#sub-info").click(function() {

        //标签封装成"a#b#c"格式
        var tagStr ="";
        for(var i=0;i<tagNum-1;i++){
            tagStr=tagStr+tagContent[i]+"#";
        }
        tagStr=tagStr+tagContent[tagNum-1];
        //console.log(tagStr);

        var deadtime =$("#date").val();
        var currenttime =getNowFormatDate();

        var taskstatusStr="未完成";
        var taskfile = {
            id: "",
            title: $("#title").val(),
            description: $("#description").val(),
            createtime: currenttime,
            type: taskType,
            deadline: deadtime,
            taskcomptime: "",
            taskcompstatus: taskstatusStr,
            otherinfo: $("#otherinfo").val(),
            userid: "",
            docid: docId,
            label: tagStr
        };
        console.log(taskfile);

        $.ajax({
            url: "/task/addTask",
            type: "post",
            traditional: true,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",
            data: taskfile,
            success: function (data) {
                //TODO:提交成功后转向其他页面

                // layui.use('layer',function(){
                //     var layer=layui.layer;
                //     layer.open({
                //         type:2,
                //         content:'myTask.html'
                //     })
                // });

                layui.use('layer',function(){
                    var layer=layui.layer;
                    layui.use('layer',function(){
                        var layer=layui.layer;
                        layer.open({
                            type:1,
                            content:'<div style="padding:30px;line-height:40px;font-size: 20px;"><i class="layui-icon" style="color:#5FB878 ;font-size: 26px;padding:1px;">&#x1005;</i>任务发布成功!' +
                            '<br>你可以选择<a href="myTaskIndex.html" target="_top" style="color: cornflowerblue;">查看任务列表</a>' +
                            '或前去<a href="doTaskIndex.html" target="_top" style="color: cornflowerblue;">做任务</a>，' +
                            '也可以选择继续<a href="publishTaskIndex.html" target="_top" style="color: cornflowerblue;">发布任务</a></div>',
                        })
                    });
                    // layer.open({
                    //     type: 1,
                    //     title: "发布成功",
                    //     closeBtn: false,
                    //     area: ['500px', '300px'],
                    //     shade: 0.8,
                    //     id: 'LAY_layuipro',
                    //     moveType: 1 ,//拖拽模式，0或者1,
                    //     content: '<div style="padding:50px;line-height:40px; background-color: #4476A7;font-size: 20px;">任务发布成功!<br>你可以选择进入<a href="index_myTask.html" target="_top" style="color: cornflowerblue;">任务列表</a>或者前去做任务，也可以继续发布任务</div>',
                    //     success: function (layero) {
                    //
                    //     }
                    // })

                });
                // alert("提交成功,任务ID为"+data.data.taskid);
                console.log(data.data.taskid);

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.status);
                console.log(XMLHttpRequest.readyState);
                console.log(textStatus);

            },
        });

    });

    /**
     * Tag标签相关参数
     * @param inputId
     * @returns {Object}
     * @constructor
     */
    function Tag(inputId){
        var obj = new Object();
        if(inputId==null||inputId==""){
            alert("初始化失败，请检查参数！");
            return;
        }
        obj.inputId = inputId;
        //初始化
        obj = (function(obj){
            obj.tagValue="";
            obj.isDisable = false;
            return obj;
        })(obj);

        //初始化界面
        obj.initView=function(){
            var inputObj = $("#"+this.inputId);
            var inputId = this.inputId;
            inputObj.css("display","none");
            var appendStr='';
            appendStr+='<div class="tagsContaine" id="'+inputId+'_tagcontaine">';
            appendStr+='<div class="tagList"></div><input type="text" class="tagInput"/>';
            appendStr+='</div>';
            inputObj.after(appendStr);
            var tagInput = $("#"+inputId+"_tagcontaine .tagInput");
            if(!this.isDisable){
                $("#"+inputId+"_tagcontaine").attr("ds","1");
                tagInput.keydown(function(event){
                    if(event.keyCode==13){
                        var inputValue = $(this).val();
                        tagTake.setInputValue(inputId,inputValue);
                        $(this).val("");
                    }
                });
            }else{
                $("#"+inputId+"_tagcontaine").attr("ds","0");
                tagInput.remove();
            }
            if(this.tagValue!=null&&this.tagValue!=""){
                tagTake.setInputValue(inputId,this.tagValue);
                if(this.isDisable){
                    $("#"+inputId+"_tagcontaine .tagList .tagItem .delete").remove();
                }
            }
        }
        obj.disableFun=function(){
            if(this.isDisable){
                return;
            }
            var inputId = this.inputId;
            var tagInput = $("#"+inputId+"_tagcontaine .tagInput");
            tagInput.remove();
            this.isDisable = true;
            $("#"+inputId+"_tagcontaine").attr("ds","0");
            $("#"+inputId+"_tagcontaine .tagList .tagItem .delete").remove();
            tagTake.initTagEvent(inputId);

        }
        obj.unDisableFun = function(){
            if(!this.isDisable){
                return;
            }
            var inputId = this.inputId;
            var tagContaine = $("#"+inputId+"_tagcontaine");
            tagContaine.append('<input type="text" class="tagInput"/>');
            this.isDisable = false;
            $("#"+inputId+"_tagcontaine").attr("ds","1");
            var tagInput = $("#"+inputId+"_tagcontaine .tagInput");
            tagInput.keydown(function(event){
                if(event.keyCode==13){
                    var inputValue = $(this).val();
                    tagTake.setInputValue(inputId,inputValue);
                    $(this).val("");
                }
            });
            $("#"+inputId+"_tagcontaine .tagList .tagItem").append('<div class="delete"></div>');
            tagTake.initTagEvent(inputId);

        }

        return obj;
    };

    /**
     *Tag标签相关参数
     * @type{{
     *   setInputValue: setInputValue,
     *   initTagEvent: initTagEvent,
     *   resetTagValue: resetTagValue,
     *   getTagItemModel: getTagItemModel
     * }}
     */
    var tagTake ={
        "setInputValue":function(inputId,inputValue){
            if(inputValue==null||inputValue==""){
                return;
            }
            var tagListContaine = $("#"+inputId+"_tagcontaine .tagList");
            inputValue = inputValue.replace(/，/g,",");
            var inputValueArray = inputValue.split(",");
            for(var i=0;i<inputValueArray.length;i++){
                var valueItem = $.trim(inputValueArray[i]);
                if(valueItem!=""){
                    var appendListItem = tagTake.getTagItemModel(valueItem);
                    tagListContaine.append(appendListItem);
                }
            }
            tagTake.resetTagValue(inputId);
            tagTake.initTagEvent(inputId);
        },
        "initTagEvent":function(inputId){
            $("#"+inputId+"_tagcontaine .tagList .tagItem .delete").off();
            $("#"+inputId+"_tagcontaine .tagList .tagItem").off();
            var ds =  $("#"+inputId+"_tagcontaine").attr("ds");
            if(ds=="0"){
                return;
            }
            $("#"+inputId+"_tagcontaine .tagList .tagItem .delete").mousedown(function(){
                tagNum--;
                $(this).parent().remove();
                tagTake.resetTagValue(inputId);
            });

            $("#"+inputId+"_tagcontaine .tagList .tagItem").dblclick(function(){
                var tagItemObj = $(this);
                $(this).css("display","none");
                var updateInputObj = $("<input type='text' class='updateInput' value='"+tagItemObj.find("span").html()+"'>");
                updateInputObj.insertAfter(this);
                updateInputObj.focus();
                updateInputObj.blur(function(){
                    var inputValue = $(this).val();
                    if(inputValue!=null&&inputValue!=""){
                        tagItemObj.find("span").html(inputValue);
                        tagItemObj.css("display","block");
                    }else{
                        tagItemObj.remove();
                    }
                    updateInputObj.remove();
                    tagTake.resetTagValue(inputId);
                });
                updateInputObj.keydown(function(event){
                    if(event.keyCode==13){
                        var inputValue = $(this).val();
                        if(inputValue!=null&&inputValue!=""){
                            tagItemObj.find("span").html(inputValue);
                            tagItemObj.css("display","block");
                        }else{
                            tagItemObj.remove();
                        }
                        updateInputObj.remove();
                        tagTake.resetTagValue(inputId);
                    }
                });
            });
        },
        "resetTagValue":function(inputId){
            var tags = $("#"+inputId+"_tagcontaine .tagList .tagItem");
            var tagsStr="";
            for(var i=0;i<tags.length;i++){
                tagsStr+=tags.eq(i).find("span").html()+",";
            }
            tagsStr = tagsStr.substr(0,tagsStr.length-1);
            $("#"+inputId).val(tagsStr);
        },
        "getTagItemModel":function(valueStr){
            //添加tag标签
            tagContent[tagNum]=valueStr;
            tagNum++;
            return '<div class="tagItem"><span>'+valueStr+'</span><div class="delete"></div></div>';
        }
    };

    /**
     * 获取当前日期，datetime格式
     * @returns {string}
     */
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    }

});





