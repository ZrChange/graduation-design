<table class="table table-bordered">
    <thead>
    <tr class="competition-tr">
        <th>竞赛标题</th>
        <th>参赛类型</th>
        <th>参赛者姓名</th>
        <th>参赛者手机号</th>
        <th>报名时间</th>
        <th>报名状态</th>
        <#if type==2>
           <th>审批操作</th>
        </#if>
    </tr>
    </thead>
    <tbody>
          <#if pageBean.content?size gt 0>
        <#list pageBean.content as personalCompetition>
        <tr>
            <td>${personalCompetition.competition.title}</td>
            <td>
                <font style="color: green">个人</font>
            </td>
            <td>${personalCompetition.student.name}</td>
            <td>${personalCompetition.student.phone}</td>
            <td>${personalCompetition.createTime}</td>
            <td>
                <#if personalCompetition.applyStatus == 0>
                    审批中
                </#if>
                <#if personalCompetition.applyStatus == 1>
                    审批通过
                </#if>
            </td>
            <#if type==2>
                 <td>
                     <button type="button" class="btn btn-primary ajax-post" <#if personalCompetition.applyStatus==1>disabled</#if> onclick="approvePassed(this)"
                             data-name="${personalCompetition.competition.id}" id="${personalCompetition.id}">通过
                     </button>
                     <button type="button" class="btn btn-primary ajax-post" <#if personalCompetition.applyStatus==1>disabled</#if> onclick="approveNotPassed(this)"
                             data-name="${personalCompetition.competition.id}" id="${personalCompetition.id}">拒绝
                     </button>
                 </td>
            </#if>


        </tr>
        </#list>
          <#else>
             <tr align="center">
                 <td colspan="7">暂无信息！</td>
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
                                            <a href="javaScript:void(0)"
                                               onclick="currentPageT(${competition.id},1,${comType})">«</a>
                                        </li>
                                    </#if>
                                    <#list pageBean.currentShowPage as showPage>
                                        <#if pageBean.currentPage == showPage>
                                            <li class="active"><span>${showPage}</span></li>
                                        <#else>
                                            <li>
                                                <a href="javascript:void(0)"
                                                   onclick="currentPageT(${competition.id},${showPage},${comType})">${showPage}</a>
                                            </li>
                                        </#if>
                                    </#list>
                                    <#if pageBean.currentPage == pageBean.totalPage>
                                        <li class="disabled"><span>»</span></li>
                                    <#else>
                                        <li>
                                            <a href="javascript:void(0)"
                                               onclick="currentPageT(${competition.id},${pageBean.totalPage},${comType})">»</a>
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

<script type="text/javascript">

    function approvePassed(e) {
        var competitionId = e.getAttribute("data-name");
        var id = $(e).attr("id");
        var status = 1;
        $.ajax({
            url: '/admin/score/approve',
            type: 'post',
            data: {
                competitionId: competitionId,
                id: id,
                status: status,
            },
            dataType: 'json',
            success: function (data) {
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
                        $.get(url, {id: competitionId, type: 1}, function (result) {
                            $("#regis-body").empty();
                            $("#regis-body").html(result);
                            $("#viewRegistratition").modal("show");
                        });
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

    function approveNotPassed(e) {
        var competitionId = e.getAttribute("data-name");
        var id = $(e).attr("id");
        var status = 2;
        $.ajax({
            url: '/admin/score/approve',
            type: 'post',
            data: {
                competitionId: competitionId,
                id: id,
                status: status,
            },
            dataType: 'json',
            success: function (data) {
                /*$("#viewRegistratition").modal("hide");*/
                if (data.code == 0) {
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
                        $.get(url, {id: competitionId, type: 1}, function (result) {
                            $("#regis-body").empty();
                            $("#regis-body").html(result);
                            $("#viewRegistratition").modal("show");
                        });
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

</script>


<script>
    function currentPageT(id, currentPage, type) {
        var option = {};
        option.id = id;
        option.currentPage = currentPage;
        option.type = type;
        $.get("/admin/individualCompetition/viewPersionSignUp", option, function (result) {
            $("#regis-body").empty();
            $("#regis-body").html(result);
        });
    }
</script>