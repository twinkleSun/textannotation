/**
 * Created by lenovo on 2018/12/20.
 */

var labelList;//label的列表
var labelLength;//label的数量
var curLabelIndex=0;//当前被选中的label
var label_list_img=new Array;//标签前面图片的ID
var label_ans_ul=new Array;//ul的ID
var li_ans_div=new Array;//二维数组
var label_ans_li=new Array;//二维数组
var label_ans_div=new Array;//用户做任务的div面板
var li_img_ok=new Array;
var li_img_del=new Array;
var li_img_num=new Array;


var paraIndex=0;
var curParaIndex=0;
var paraContent =new Array;
var panel_footer_index=new Array;

$(function () {


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
            //console.log(resdata.data[0]);
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
                var list_html = '<div class="panel panel-success">'
                    +'<div class="panel-heading">'
                        +'<h4 class="panel-title">'
                            +'<a data-toggle="collapse" data-parent="#accordion" href="#'+label_ans_div[i]+'">'
                                +'<img class="notAns" src="./images/notAns.png" id="label-list-img-'+i+'" onclick="imgClick(this.id)">'
                            +'</a>'+labelList[i].labelname
                        +'</h4>'
                    +'</div>'
                    +'<div id="label-ans-div-'+i+'" class="panel-collapse collapse">'
                        +'<div class="panel-body">'
                            +'<ul class="list-group" id="label-ans-ul-'+i+'">'
                                +'<li class="list-group-item" id="label-ans-li-'+i+'-0">'
                                    +'<div class="row">'
                                        +'<div class="col-lg-10" id="li-ans-div-'+i+'-0">'
                                        +'</div>'
                                        +'<div class="col-lg-1">'
                                            +'<img class="okAns" src="./images/ok.png" id="li-img-ok-'+i+'-0" onclick="imgOkClick(this.id)">'
                                        +'</div>'
                                        +'<div class="col-lg-1">'
                                            +'<img class="delAns" src="./images/delete.png" id="li-img-del-'+i+'-0" onclick="imgDeleteClick(this.id)">'
                                        +'</div>'
                                    +'</div>'
                                +'</li>'
                         +'</ul>'
                        +'</div>'
                    +'</div>'
                  +'</div>';

                label_html =label_html+list_html;
                label_list_img[i]="label-list-img-"+i;
                label_ans_ul[i]="label-ans-ul-"+i;

                label_ans_li[i]=new Array;
                li_img_ok[i]=new Array;
                li_img_del[i]=new Array;
                li_ans_div[i]=new Array;
                label_ans_li[i][0]= "label-ans-li-"+i+"-0";
                li_ans_div[i][0]= "li-ans-div-"+i+"-0";
                li_img_ok[i][0] ="li-img-ok-"+i+"-0";
                li_img_del[i][0] ="li-img-del-"+i+"-0";
                li_img_num[i]=0;
                // console.log(label_list_img[i]);
            }

            $("#labellist-div-panel").append(label_html);


        }, error: function (XMLHttpRequest, textStatus, errorThrown) {

        },
    });

    $('#p-para').mouseup(function(){
        var txt = window.getSelection?window.getSelection():document.selection.createRange().text;
        //$("#label-p-ans-test").text(txt);
        //console.log(curLabelIndex+"ha");
        //console.log(label_ans_li[curLabelIndex][0]);

        var curLiDiv=li_img_num[curLabelIndex];
        $("#"+li_ans_div[curLabelIndex][curLiDiv]).text(txt);


        console.log(window.getSelection().anchorOffset);
    });

    // $("#label-list-img-test").click(function () {
    //
    //     console.log(labelList);
    //     if($("#label-list-img-test").hasClass("isAns")){
    //         $("#label-list-img-test").attr("src","./images/notAns.png")
    //         $("#label-list-img-test").addClass("notAns").removeClass("isAns");
    //
    //     }else{
    //         $("#label-list-img-test").attr("src","./images/isAns.png");
    //         $("#label-list-img-test").removeClass("notAns").addClass("isAns");
    //     }
    // });



});
function imgClick(obj) {
    //console.log(labelLength);
    var i=obj.substring(15,obj.length);

    curLabelIndex=i;
    //console.log(curLabelIndex);
    //var curlabelName=labelList[curLabelIndex].labelname;
    //$("#label-selected").html(curlabelName);
    if($("#"+label_list_img[i]).hasClass("isAns")){
        $("#"+label_list_img[i]).attr("src","./images/notAns.png");
        $("#"+label_list_img[i]).addClass("notAns").removeClass("isAns");

        var curLiDiv=li_img_num[curLabelIndex];


        // $("#"+li_ans_div[curLabelIndex][curLiDiv]).html("");

        curLabelIndex=0;
        //$("#"+label_ans_li[curLabelIndex][0]).html("");
       // $("#"+label_p_ans[i]).text("");

       // $("#label-selected").html("");
    }else{
        for(var j=0;j<labelLength;j++){
            $("#"+label_list_img[j]).attr("src","./images/notAns.png");
            $("#"+label_list_img[j]).removeClass("isAns").addClass("notAns");
            $("#"+label_ans_div[j]).collapse('hide');
            // console.log(j+"kkk");
        }
        //console.log(label_list_img[i]+"aa");
        $("#"+label_list_img[i]).attr("src","./images/isAns.png");
        $("#"+label_list_img[i]).removeClass("notAns").addClass("isAns");
        $("#"+label_ans_div[i]).collapse('show');
    }

};

function imgOkClick(obj) {
    // var okLiLength=curLabelIndex.length+11;
    // var okLiNum2 =obj.substring(okLiLength,obj.length);
    //
    // var okLiNum =parseInt(okLiNum2);
   var addLiNum= li_img_num[curLabelIndex];
    li_img_num[curLabelIndex]++;
    console.log(addLiNum);
    var addLi= '<li class="list-group-item" id="label-ans-li-'+curLabelIndex+'-'+(addLiNum+1)+'">'
    +'<div class="row">'
    +'<div class="col-lg-10" id="li-ans-div-'+curLabelIndex+'-'+(addLiNum+1)+'">'
    +'</div>'
    +'<div class="col-lg-1">'
    +'<img class="okAns" src="./images/ok.png" id="li-img-ok-'+curLabelIndex+'-'+(addLiNum+1)+'" onclick="imgOkClick(this.id)">'
    +'</div>'
    +'<div class="col-lg-1">'
    +'<img class="delAns" src="./images/delete.png" id="li-img-del-'+curLabelIndex+'-'+(addLiNum+1)+'" onclick="imgDelClick(this.id)">'
    +'</div>'
    +'</div>'
    +'</li>';
    label_ans_li[curLabelIndex][addLiNum+1]= "label-ans-li-"+curLabelIndex+"-"+(addLiNum+1);
    li_ans_div[curLabelIndex][addLiNum+1]= "li-ans-div-"+curLabelIndex+"-"+(addLiNum+1);
    console.log(label_ans_li[curLabelIndex][addLiNum+1]);
    li_img_ok[curLabelIndex][addLiNum+1] ="li-img-ok-"+curLabelIndex+"-"+(addLiNum+1);
    li_img_del[curLabelIndex][addLiNum+1] ="li-img-del-"+curLabelIndex+"-"+(addLiNum+1);

    $("#"+label_ans_ul[curLabelIndex]).append(addLi);

};

function imgDelClick(obj) {

    var delLiLength=curLabelIndex.length+12;
    var delLiStr =obj.substring(delLiLength,obj.length);
    var delLiNum =parseInt(delLiStr);
    console.log(delLiLength);
    console.log(delLiStr);
    console.log(delLiNum);
    console.log(label_ans_li[curLabelIndex][delLiNum]);
    // var delLiNum= li_img_num[curLabelIndex];
    li_img_num[curLabelIndex]--;
    $("#"+label_ans_li[curLabelIndex][delLiNum]).remove();


};

function imgDeleteClick(obj) {
    $("#"+li_ans_div[curLabelIndex][0]).html("");
};

// $("#submit-nextPara").click(function(){
//     curParaIndex++;
//
//     $("#span-index").html("第"+(curParaIndex+1)+"段");
//     $("#p-para").html(paraContent[curParaIndex]);
// });

function footerIndex(obj) {

    var i=obj.substring(19,obj.length);
    console.log(i);
    curParaIndex=i;
    var curParaIndexNum =parseInt(curParaIndex);
    $("#span-index").html("第"+(curParaIndexNum+1)+"段");
    $("#p-para").html(paraContent[curParaIndex]);
};

// $("#link1").click(function(){
//     console.log("hha");
// });