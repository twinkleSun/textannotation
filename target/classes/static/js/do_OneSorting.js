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
});
