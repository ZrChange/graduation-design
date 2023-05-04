<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|竞赛管理-查看团队名单</title>
<#include "../common/header.ftl"/>
    <link href="/admin/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/admin/js/jquery-tags-input/jquery.tagsinput.min.css">
</head>
<body>
<table class="table table-bordered">
    <thead>
    <tr class="competition-tr">
        <th>团队名称</th>
        <th>团队队长</th>
        <th>竞赛名称</th>
        <th>报名时间</th>
        <th>报名状态</th>
        <#if type==2>
             <th>审批操作</th>
        </#if>
    </tr>
    </thead>
    <tbody>
       <#if pageBean.content?size gt 0>
           <#list pageBean.content as teamCompetition>
        <tr>
            <td>${teamCompetition.name}</td>
            <td>
                ${teamCompetition.student.name}
            </td>
            <td>${teamCompetition.competition.title}</td>
            <td>${teamCompetition.createTime}</td>
            <td>
                <#if teamCompetition.status == 2>
                    审批中
                </#if>
                <#if teamCompetition.status == 1>
                    审批通过
                </#if>
            </td>
            <#if type==2>
                  <td>
                      <button type="button" class="btn btn-primary ajax-post"<#if teamCompetition.status==1>disabled</#if> onclick="approvePassed(this)" data-name="${teamCompetition.competition.id}" id="${teamCompetition.id}">通过</button>
                      <button type="button" class="btn btn-primary ajax-post"<#if teamCompetition.status==1>disabled</#if> onclick="approveNotPassed(this)" data-name="${teamCompetition.competition.id}" id="${teamCompetition.id}">拒绝</button>
                  </td>
            </#if>
        </tr>
        </#list>
          <#else>
             <tr align="center">
                 <td colspan="6">暂无信息！</td>
             </tr>
          </#if>
    </tbody>
</table>
<div style="text-align: center;">
<#if pageBean.total gt 0>
    <ul class="pagination ">
                                    <#if pageBean.currentPage == 1>
                                        <li class="disabled"><span>«</span></li>
                                    <#else>
                                        <li>
                                            <a href="javaScript:void(0)" onclick="currentPageTeam(${competition.id},1,${comType})">«</a>
                                        </li>
                                    </#if>
                                    <#list pageBean.currentShowPage as showPage>
                                        <#if pageBean.currentPage == showPage>
                                            <li class="active"><span>${showPage}</span></li>
                                        <#else>
                                            <li>
                                                <a  href="javascript:void(0)" onclick="currentPageTeam(${competition.id},${showPage},${comType})">${showPage}</a>
                                            </li>
                                        </#if>
                                    </#list>
                                    <#if pageBean.currentPage == pageBean.totalPage>
                                        <li class="disabled"><span>»</span></li>
                                    <#else>
                                        <li>
                                            <a  href="javascript:void(0)" onclick="currentPageTeam(${competition.id},${pageBean.totalPage},${comType})">»</a>
                                        </li>
                                    </#if>
        <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
    </ul>
</#if>
</div>
<script type="text/javascript" src="/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="/admin/js/bootstrap.min.js"></script>
<!--对话框-->
<script src="/admin/js/jconfirm/jquery-confirm.min.js"></script>
<script src="/admin/js/common.js"></script>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>

<script type="text/javascript">

    function approvePassed(e) {
        var competitionId = e.getAttribute("data-name");
        var id = $(e).attr("id");
        var status = 1;
        $.ajax({
            url:'/admin/score/approve',
            type:'post',
            data:{
                competitionId:competitionId,
                id:id,
                status:status,
            },
            dataType:'json',
            success:function (data) {
                if (data.code == 0) {
                    /*$("#viewRegistratition").modal("hide");*/
                    showSuccessMsg('审批成功!', function () {
                        var url = "";
                        var checkInput = $("input[type='checkbox']:checked");
                        var id = checkInput.val();
                        var competitionType = checkInput.attr("data-competitionType");
                        if (competitionType == competition_status.PERSONAL) {
                            url = "/admin/individualCompetition/viewPersionSignUp";
                        } else {
                            url = "/admin/team/viewTeamSignUp";
                        }
                        $.get(url, {id: competitionId, type: 2}, function (result) {
                            $("#regis-body").empty();
                            $("#regis-body").html(result);
                            $("#viewRegistratition").modal("show");
                        });
                    })
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error:function (data) {
                alert("网络错误!");
            }
        })

    }

    function approveNotPassed(e) {
        var competitionId = e.getAttribute("data-name");
        var id = $(e).attr("id");
        var status = 2;
        $.ajax({
            url:'/admin/score/approve',
            type:'post',
            data:{
                competitionId:competitionId,
                id:id,
                status:status,
            },
            dataType:'json',
            success:function (data) {
                if (data.code == 0) {
                    /*$("#viewRegistratition").modal("hide");*/
                    showSuccessMsg('审批成功!', function () {
                        var url = "";
                        var checkInput = $("input[type='checkbox']:checked");
                        var id = checkInput.val();
                        var competitionType = checkInput.attr("data-competitionType");
                        if (competitionType == competition_status.PERSONAL) {
                            url = "/admin/individualCompetition/viewPersionSignUp";
                        } else {
                            url = "/admin/team/viewTeamSignUp";
                        }
                        $.get(url, {id: competitionId, type: 2}, function (result) {
                            $("#regis-body").empty();
                            $("#regis-body").html(result);
                            $("#viewRegistratition").modal("show");
                        });
                    })
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error:function (data) {
                alert("网络错误!");
            }
        })

    }

</script>

<script>
    function currentPageTeam(id,currentPage,type) {
        var option={};
        option.id=id;
        option.currentPage=currentPage;
        option.type=type;
        $.get("/admin/team/viewTeamSignUp",option,function(result){
            $("#regis-body").empty();
            $("#regis-body").html(result);
        });
    }
</script>

</body>
</html>