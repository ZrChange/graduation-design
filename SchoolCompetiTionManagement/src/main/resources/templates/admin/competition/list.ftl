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
                                <#if type!=3>
                                     <form class="pull-right search-bar" method="get" action="list" role="form">
                                         <div class="input-group">
                                             <div class="input-group-btn">
                                                 <button class="btn btn-default dropdown-toggle" id="search-btn"
                                                         data-toggle="dropdown" type="button" aria-haspopup="true"
                                                         aria-expanded="false">
                                                     竞赛标题 <span class="caret"></span>
                                                 </button>
                                                 <ul class="dropdown-menu">
                                                     <li><a tabindex="-1" href="javascript:void(0)"
                                                            data-field="title">竞赛标题</a></li>
                                                 </ul>
                                             </div>
                                             <input type="text" class="form-control" value="${title!""}" name="title"
                                                    placeholder="请输入竞赛标题">
                                             <span class="input-group-btn">
                      <button class="btn btn-primary" type="submit">搜索</button>
                    </span>
                                         </div>
                                     </form>
                                </#if>

                                <#if type==3>
                                     <form class="pull-right search-bar" method="get" action="list" role="form"
                                           style="max-width: 500px;">
                                         <div class="input-group" style="width: 500px;">
                                             <select class="form-control" id="select-competitionType" name="raceType"
                                                     style="width: 145px;height: 38px;"
                                                     size="1">
                                                 <option value="1" <#if raceType==1>selected</#if>>开放型</option>
                                                 <option value="2" <#if raceType==2>selected</#if>>专业型</option>
                                             </select>
                                             <div class="input-group-btn">
                                                 <button class="btn btn-default dropdown-toggle" id="search-btn"
                                                         data-toggle="dropdown" type="button" aria-haspopup="true"
                                                         aria-expanded="false">
                                                     竞赛标题 <span class="caret"></span>
                                                 </button>
                                                 <ul class="dropdown-menu">
                                                     <li><a tabindex="-1" href="javascript:void(0)"
                                                            data-field="title">竞赛标题</a></li>
                                                 </ul>
                                             </div>

                                             <input type="text" class="form-control" value="${title!""}" name="title"
                                                    placeholder="请输入竞赛标题" style="width:200px">
                                             <span class="input-group-btn">
                      <button class="btn btn-primary" type="submit">搜索</button>
                    </span>
                                         </div>
                                     </form>
                                </#if>
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
                                            <th>标题</th>
                                            <th>竞赛类型</th>
                                            <th>参赛类型</th>
                                            <th>报名开始时间</th>
                                            <th>报名结束时间</th>
                                            <th>报名人数/团队</th>
                                            <th>已报名人数/团队</th>
                                            <th>竞赛状态</th>
                                            <th>是否过期</th>
                                            <th>报名人数是否满</th>
                                            <th>添加时间</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                       <#if pageBean.content?size gt 0>
                                            <#list pageBean.content as competition>
                                                <tr row-id="${competition.id}"
                                                    row-type="${competition.competitionType}"
                                                    row-number="${competition.enrollmentNumber - competition.signedUp}"
                                                    row-status="${competition.status}">
                                                    <td style="vertical-align:middle;">
                                                        <label class="lyear-checkbox checkbox-primary">
                                                            <input type="checkbox" name="ids[]"
                                                                   value="${competition.id}"
                                                                   data-competitionType="${competition.competitionType}"><span></span>
                                                        </label>
                                                    </td>
                                                    <td style="vertical-align:middle;">${competition.title}</td>
                                                    <td style="vertical-align:middle;">
                                                        <#if competition.raceType==1>
                                                            <font style="color: #0c5460">开放型</font>
                                                        <#else>
                                                             <font style="color: #0FB25F;">专业型</font>
                                                        </#if>
                                                    </td>
                                                    <td style="vertical-align:middle;">
                                                        <#if competition.competitionType==1>
                                                            <font style="color: #0c5460">个人</font>
                                                        <#else>
                                                             <font style="color: #0FB25F;">团队</font>
                                                        </#if>
                                                    </td>
                                                    <td style="vertical-align:middle;">${competition.enrollStartTime}</td>
                                                    <td style="vertical-align:middle;">${competition.enrollEndTime}</td>
                                                    <td style="vertical-align:middle;">${competition.enrollmentNumber}</td>
                                                    <td style="vertical-align:middle;">${competition.signedUp}</td>
                                                    <td style="vertical-align:middle;">
                                                        <#switch competition.competitionStatus>
                                                          <#case 1>
                                                             <font style="color: #0c5460">报名</font>
                                                              <#break>
                                                            <#case 2>
                                                            <font style="color: #0FB25F">初赛</font>
                                                                <#break>
                                                             <#case 3>
                                                         <font style="color: #2ba3f6">复赛</font>
                                                                 <#break>
                                                           <#case 4>
                                                           <font style="color: blue">决赛</font>
                                                               <#break>
                                                               <#case 5>
                                                               <font style="color: red">结束</font>
                                                                   <#break>
                                                        </#switch>
                                                    </td>
                                                    <td style="vertical-align:middle;">
                                                        <#if competition.status==1>
                                                            <font style="color: red">过期</font>
                                                        <#else>
                                                              <font style="color: green">未过期</font>
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if competition.enrollmentNumber - competition.signedUp == 0>
                                                            <font style="color: red">已满</font>
                                                        <#else>
                                                             <font style="color: green">未满</font>
                                                        </#if>
                                                    </td>

                                                    <td style="vertical-align:middle;" style="vertical-align:middle;">
                                                        <font class="text-success">${competition.createTime}</font></td>
                                                </tr>
                                            </#list>
                                       <#else>
                                            <tr align="center">
                                                <td colspan="12">这里空空如也！</td>
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
                                        <li><a href="list?name=${title!""}&currentPage=1&raceType=${raceType}">«</a>
                                        </li>
                                    </#if>
                                    <#list pageBean.currentShowPage as showPage>
                                        <#if pageBean.currentPage == showPage>
                                            <li class="active"><span>${showPage}</span></li>
                                        <#else>
                                            <li>
                                                <a href="list?title=${title!""}&currentPage=${showPage}&raceType=${raceType}">${showPage}</a>
                                            </li>
                                        </#if>
                                    </#list>
                                    <#if pageBean.currentPage == pageBean.totalPage>
                                        <li class="disabled"><span>»</span></li>
                                    <#else>
                                        <li>
                                            <a href="list?title=${title!""}&currentPage=${pageBean.totalPage}&raceType=${raceType}">»</a>
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
<#include "../common/footer.ftl"/>

<div class="modal fade" id="viewCompetition" tabindex="-1" role="dialog" aria-labelledby="viewCompetition">
    <div class="modal-dialog" role="document">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="viewCompetition">竞赛信息</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<div class="modal" id="viewRegistratition" tabindex="-1" role="dialog" aria-labelledby="viewRegistratition">
    <div class="modal-dialog" role="document">

        <div class="modal-content" style="width:800px;border: 1px solid #b1acac;">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">报名信息</h4>
            </div>
            <div class="regis-body" id="regis-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
    //打开编辑页面
    function edit(url) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条数据进行编辑！');
            return;
        }
        window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
    }

    function apply(url) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择要报名的竞赛！');
            return;
        }
        var id = $("input[type='checkbox']:checked").val();

        //找到tr
        var tr = $("tr[row-id='" + id + "']");
        var status = Number(tr.attr("row-status").trim());  //是否过期 1为过期
        var number = Number(tr.attr("row-number").trim()); //可用报名人数
        var type = Number(tr.attr("row-type").trim());  //类型 1为个人

        if (number <= 0) {
            showErrorMsg("报名人数已满了");
            return;
        }

        if (status == 1) {
            showErrorMsg("改比赛已经过期了哦");
            return;
        }

        //个人报名
        if (type == 1) {
            $.ajax({
                url: '/admin/individualCompetition/apply',
                type: 'POST',
                data: {id: id},
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        showSuccessMsg("报名成功", function () {
                            window.location.href = 'list';
                        });
                    } else {
                        showErrorMsg(data.msg)
                    }
                },
                error: function (data) {
                    alert('网络错误!');
                }
            })

            return;
        }
        //团队报名
        location.href = "/admin/competition/team_apply/competition_team?competitionId=" + id;
    }

    function del(url) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条数据进行删除！');
            return;
        }
        var checkInput = $("input[type='checkbox']:checked");
        var id = checkInput.val();
        $.confirm({
            title: '确定删除？',
            content: '删除后数据不可恢复，请慎重！',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        deleteReq(id, url);
                    }
                },
                cancel: {
                    text: '关闭',
                    action: function () {

                    }
                }
            }
        });
    }

    //调用删除方法
    function deleteReq(id, url) {
        $.ajax({
            url: url,
            type: 'POST',
            data: {id: id},
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    showSuccessMsg('删除成功!', function () {
                        $("input[type='checkbox']:checked").parents("tr").remove();
                    })
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        });
    }

    function viewCompetition(url) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条竞赛进行查看！');
            return;
        }
        var id = $("input[type='checkbox']:checked").val();
        $.get(url, {id: id}, function (result) {
            $(".modal-body").empty();
            $('#viewCompetition').modal('show');
           $(".modal-body").html(result);

        });
    }

    //查看报名名单
    function viewRegistration(type) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条竞赛查看报名名单！');
            return;
        }
        var url = "";
        var checkInput = $("input[type='checkbox']:checked");
        var id = checkInput.val();
        var competitionType = checkInput.attr("data-competitionType");
        if (competitionType == competition_status.PERSONAL) {
            url = "/admin/individualCompetition/viewPersionSignUp";
        } else {
            url = "/admin/team/viewTeamSignUp";
        }
        $.get(url, {id: id, type: type}, function (result) {
            $("#regis-body").empty();
            $("#regis-body").html(result);
            $("#viewRegistratition").modal("show");
        });
    }

    //查看初赛、复赛、决赛名单
    function viewMatch(type) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条竞赛查看名单！');
            return;
        }
        var url = "";
        var checkInput = $("input[type='checkbox']:checked");
        var id = checkInput.val();
        var competitionType = checkInput.attr("data-competitionType");
        if (competitionType == competition_status.PERSONAL) {
            url = "/admin/competition/viewPersionMatch";
        } else {
            url = "/admin/competition/viewTeamMatch";
        }
        location.href = url + "?id=" + id + "&compeType=" + type;
    }

    /*  //学生选择竞赛类型
      $("#select-competitionType").change(function(){
          var competitionType=$(this).val();
          location.href="list?raceType="+competitionType;
      });*/
</script>
</body>
</html>