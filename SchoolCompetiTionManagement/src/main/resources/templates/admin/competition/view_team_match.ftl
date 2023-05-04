<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|竞赛管理-${headTitle!""}</title>
    <#include "../common/header.ftl"/>
    <style>
        td {
            vertical-align: middle;
        }
    </style>
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}"
                                             alt="${siteName!""}"/></a>
            </div>
            <div class="lyear-layout-sidebar-scroll">
                <#include "../common/left-menu.ftl"/>
            </div>

        </aside>
        <!--End 左侧导航-->
        <#include "../common/header-menu.ftl"/>
        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-toolbar clearfix">
                                <#include "../common/third-menu.ftl"/>
                            </div>
                            <div class="card-body">

                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>
                                                <label class="lyear-checkbox checkbox-primary">
                                                    <input type="checkbox" id="check-all"><span></span>
                                                </label>
                                            </th>
                                            <th>团队名称</th>
                                            <th>团队队长</th>
                                            <th>竞赛名称</th>
                                             <#if compeType!=4>
                                            <th>是否晋级</th>
                                             <#else>
                                                 <th>团队得分</th>
                                            <th>是否评分</th>
                                             </#if>
                                            <th>报名状态</th>
                                            <th>报名时间</th>
                                            <#if type==2>
                                                 <th>操作</th>
                                            </#if>
                                        </tr>
                                        </thead>
                                        <tbody>
                                              <#if pageBean.content?size gt 0>
           <#list pageBean.content as teamCompetition>
        <tr>
            <td style="vertical-align:middle;">
                <label class="lyear-checkbox checkbox-primary">
                    <input type="checkbox" name="ids[]"
                           value="${teamCompetition.id}"><span></span>
                </label>
            </td>
            <td>${teamCompetition.name}</td>
            <td>
                ${teamCompetition.student.name}
            </td>
            <td>${teamCompetition.competition.title}</td>
             <#if compeType!=4>
                       <td>
                           <#if compeType==2>
                            <#if teamCompetition.preliminariesStatus==1>
                                    <font style="color: green">已晋级</font>
                            <#else>
                                 <font style="color: red"></font>
                            </#if>
                           <#elseif compeType==3>
                               <#if teamCompetition.secondRoundStatus==0>
                                                           <font style="color: red"></font>
                               <#else >
                                                           <font style="color: green">已晋级</font>
                               </#if>
                           </#if>
                       </td>
             <#else>
                   <td>${teamCompetition.finalsScore}</td>
            <td>
                                                        <#if teamCompetition.finalsScoreStatus==0>
                                                            <font style="color: red">未评分</font>
                                                        <#else>
                                                             <font style="color: green">已评分</font>
                                                        </#if>
            </td>
             </#if>
            <td>
                <#if teamCompetition.status == 2>
                    审批中
                </#if>
                <#if teamCompetition.status == 1>
                    审批通过
                </#if>
            </td>
            <td>${teamCompetition.createTime}</td>
            <#if type==2>
                 <td>
                     <button class="btn btn-success btn-w-md" type="button"
                             <#if timeType==0>
                                 <#if compeType == 2>
                                     <#if teamCompetition.preliminariesScoreStatus == 1>
                                         disabled
                                     </#if>
                                 <#elseif compeType == 3>
                                     <#if teamCompetition.secondRoundScoreStatus == 1>
                                         disabled
                                     </#if>
                                 <#else>
                                     <#if teamCompetition.finalsScoreStatus == 1>
                                         disabled
                                     </#if>
                                 </#if>
                             <#else>
                                 disabled
                             </#if>
                             onclick="score(${teamCompetition.competition.id},${teamCompetition.id})">评分
                     </button>
                     <button class="btn btn-success btn btn-warning" type="button"
                             onclick="promotion(this)"
                             <#if compeType == 2>
                                 <#if timeType==0>
                                     <#if teamCompetition.preliminariesStatus == 1>
                                         disabled
                                     </#if>
                                 <#else>
                                     disabled
                                 </#if>
                             <#elseif compeType == 3>
                                 <#if timeType==0>
                                     <#if teamCompetition.secondRoundStatus == 1>
                                         disabled
                                     </#if>
                                 <#else>
                                     disabled
                                 </#if>
                             <#else>
                                 style="display: none"
                             </#if>
                             data-name="${teamCompetition.competition.id}"
                             id="${teamCompetition.id}">
                         晋级
                     </button>
                     <button class="btn btn-success btn btn-info" type="button"
                             onclick="download(this)"
                             data-name="${teamCompetition.competition.id}"
                             id="${teamCompetition.id}">
                         下载附件
                     </button>
                 </td>
            </#if>

        </tr>
           </#list>
                                              <#else>
             <tr align="center">
                 <td colspan="10">暂无信息！</td>
             </tr>
                                              </#if>
                                        </tbody>
                                    </table>
                                </div>
                                           <#if pageBean.total gt 0>
                                <ul class="pagination ">
                                    <#if pageBean.currentPage == 1>
                                        <li class="disabled"><span>«</span></li>
                                    <#else>
                                        <li>
                                            <a href="viewTeamMatch?id=${competition.id!""}&currentPage=1&compeType=${compeType}">«</a>
                                        </li>
                                    </#if>
                                    <#list pageBean.currentShowPage as showPage>
                                        <#if pageBean.currentPage == showPage>
                                            <li class="active"><span>${showPage}</span></li>
                                        <#else>
                                            <li>
                                                <a href="viewTeamMatch?id=${competition.id!""}&currentPage=${showPage}&compeType=${compeType}">${showPage}</a>
                                            </li>
                                        </#if>
                                    </#list>
                                    <#if pageBean.currentPage == pageBean.totalPage>
                                        <li class="disabled"><span>»</span></li>
                                    <#else>
                                        <li>
                                            <a href="viewTeamMatch?id=${competition.id!""}&currentPage=${pageBean.totalPage}&compeType=${compeType}">»</a>
                                        </li>
                                    </#if>
                                    <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                                </ul>
                                           </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </main>
        <!--End 页面主要内容-->
    </div>
</div>

<div class="modal fade" id="viewCompetition" tabindex="-1" role="dialog" aria-labelledby="viewCompetition">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="viewCompetition">评分</h4>
            </div>
            <div class="modal-body">
                <div class="input-group m-b-10" id="edit-personal">
                    <span class="input-group-addon">填写分数</span>
                    <input type="number" class="form-control required input" id="score"
                           name="score"
                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           placeholder="请输入分数"
                           tips="请输入分数"/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" id="score" onclick="scoreCompetition()">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>


 <#include "../common/footer.ftl"/>
<!--对话框-->
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script>

    $(document).ready(function(){
        var type = ${type};
        var compesType = ${compeType};
        if(type == 2 && compesType == 4){
            $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='showTop()' id='file'>查看前10名</a>");
            $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='exportTop()' id='file'>导出前10名</a>");
        }
    });

    var M = {};

    function score(e, a) {
        M.competitionId = e;
        M.participateId = a;
        $('#viewCompetition').modal('show');
    }

    function showTop() {
        var competitionId = ${competition.id};
        console.log(competitionId)
        window.location.href = '/admin/competition/showTeamTop?competitionId='+competitionId;
    }

    function exportTop() {
        var competitionId = ${competition.id};
        window.location.href = '/admin/competition/export_team?competitionId='+competitionId;
    }

    function scoreCompetition() {
        var competitionId = M.competitionId;
        var participateId = M.participateId;
        var compeType = ${compeType};
        var score = $('#score').val();
        if (score.length < 1) {
            showErrorMsg("请输入分数")
            return;
        }
        $.ajax({
            url: '/admin/score/score',
            type: 'post',
            data: {
                competitionId: competitionId,
                participateId: participateId,
                compeType: compeType,
                scores: score,
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    showSuccessMsg('评分成功!', function () {
                        window.location.href = '/admin/competition/viewTeamMatch?id=' + competitionId + '&compeType=' + compeType;
                    })
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error: function (data) {
                alert("网络错误!");
            }
        })
    }

    function promotion(e) {
        var competitionId = e.getAttribute("data-name");
        var id = $(e).attr("id");
        var compeType = ${compeType};
        $.ajax({
            url: '/admin/score/promotion',
            type: 'post',
            data: {
                competitionId: competitionId,
                id: id,
                compeType: compeType,
            },
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    showSuccessMsg('操作成功!', function () {
                        window.location.href = '/admin/competition/viewTeamMatch?id=' + competitionId + '&compeType=' + compeType;
                    })
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error: function (data) {
                alert("网络错误!");
            }
        })
    }

    function download(e) {
        var competitionId = e.getAttribute("data-name");
        var id = $(e).attr("id");
        var compeType = ${compeType};
        $.ajax({
            url: '/admin/score/download',
            type: 'post',
            data: {
                competitionId: competitionId,
                id: id,
                compeType: compeType,
            },
            success: function (data) {
                if(data == null || data == ""){
                    alert("当前无附件")
                }else{
                    window.location.href = '/download/downloadFile?fileSrc='+data;
                }
            },
            error: function (data) {
                console.log(data);
                alert("网络错误!");
            }
        })
    }

</script>
</body>
</html>