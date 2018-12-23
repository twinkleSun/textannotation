/**
 * Created by lenovo on 2018/12/20.
 */

var labelList;//label的列表
var labelLength;//label的数量
var curLabelIndex=0;//当前被选中的label
var label_list_img=new Array;//标签前面图片的ID
var label_ans_ul=new Array;//ul的ID
var label_ans_li=new Array;//二维数组
var label_ans_div=new Array;//用户做任务的div面板

var li_img_num=new Array;


var paraIndex=0;
var curParaIndex=0;
var paraContent =new Array;
var panel_footer_index=new Array;

$(function () {


    var testettt={
        dtid:"",
        userid:"",
        taskid :8,
        contentid:79,
        labelId:24,
        conbegin:1,
        conend:3
    };

    $.ajax({
        url: "dotask/addDoTask",
        type: "post",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:testettt,
        success: function (data) {
            console.log(data);

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });



    var dataDocId={
        docId: 11
    };
    $.ajax({
        url: "content/getContent",
        type: "get",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:dataDocId,
        success: function (data) {
            paraIndex =data.data.length;
            var div_footer='<div class="text-center">';
            for(var i=0;i<paraIndex;i++){
                paraContent[i]=data.data[i].paracontent;
                var panel_footer= '\xa0\xa0\xa0\xa0<a id="panel-footer-index-'+i+'" style="color: #0d96f2" onclick="footerIndex(this.id)">'
                    + (i+1)
                    +'</a>\xa0\xa0\xa0';
                panel_footer_index[i]="panel-footer-index-"+i;
                div_footer=div_footer+panel_footer;
            }
            div_footer=div_footer+'</div>';
            console.log(paraContent[0]);
            $("#p-para").html(paraContent[0]);
            $("#div-para-footer").append(div_footer);

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {


        },
    });


    var taskId={
        taskid:8
    };


    $.ajax({
        url: "/label/getLabelByTask",
        type: "get",
        traditional: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
        data:taskId,
        success: function (resdata) {
            labelList=resdata.label;
            labelLength =labelList.length;

            var label_html="";
            for(var i=0;i<labelLength;i++){
                label_ans_div[i]="label-ans-div-"+i;
                var list_html ='<li class="list-group-item">'
                    +'<img class="notAns" src="./images/notAns.png" id="label-list-img-'+i+'" onclick="imgClick(this.id)">'
                    +labelList[i].labelname
                    +'</li>';

                label_html =label_html+list_html;
                label_list_img[i]="label-list-img-"+i;
                label_ans_ul[i]="label-ans-ul-"+i;

                label_ans_li[i]=new Array;

                label_ans_li[i][0]= "label-ans-li-"+i+"-0";

                li_img_num[i]=0;
            }

            $("#ul-label-list").append(label_html);

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {

        },
    });

});
function imgClick(obj) {
    //console.log(labelLength);
    var i=obj.substring(15,obj.length);
    curLabelIndex=i;
    if($("#"+label_list_img[i]).hasClass("isAns")){
        $("#"+label_list_img[i]).attr("src","./images/notAns.png");
        $("#"+label_list_img[i]).addClass("notAns").removeClass("isAns");

        var curLiDiv=li_img_num[curLabelIndex];
        curLabelIndex=0;
    }else{
        $("#"+label_list_img[i]).attr("src","./images/isAns.png");
        $("#"+label_list_img[i]).removeClass("notAns").addClass("isAns");

    }
};

function footerIndex(obj) {
    var i=obj.substring(19,obj.length);
    console.log(i);
    curParaIndex=i;
    var curParaIndexNum =parseInt(curParaIndex);
    $("#span-index").html("第"+(curParaIndexNum+1)+"段");
    $("#p-para").html(paraContent[curParaIndex]);
};

