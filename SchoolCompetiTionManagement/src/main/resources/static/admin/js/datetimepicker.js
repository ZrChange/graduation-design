/**
 * 竞赛日期js
 */
$("#enrollStartTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true,
    startDate: new Date()
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#enrollEndTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#enrollEndTime").datetimepicker('setStartDate', null);
    }
});
$("#enrollEndTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#preliminariesStartTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#preliminariesStartTime").datetimepicker('setStartDate', null);
    }
});

$("#preliminariesStartTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#preliminariesEndTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#preliminariesEndTime").datetimepicker('setStartDate', null);
    }
});
$("#preliminariesEndTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#secondRoundStartTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#secondRoundStartTime").datetimepicker('setStartDate', null);
    }
});

$("#secondRoundStartTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#secondRoundEndTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#secondRoundEndTime").datetimepicker('setStartDate', null);
    }
});
$("#secondRoundEndTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#finalsStartTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#finalsStartTime").datetimepicker('setStartDate', null);
    }
});

$("#finalsStartTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
}).on('changeDate', function (ev) {
    if (ev.date) {
        $("#finalsEndTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()))
    } else {
        $("#finalsEndTime").datetimepicker('setStartDate', null);
    }
});
$("#finalsEndTime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true
});