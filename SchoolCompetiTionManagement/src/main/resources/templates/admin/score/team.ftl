<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|竞赛管理-${title!""}</title>
    <#include "../common/header.ftl"/>
    <style>
        td{
            vertical-align:middle;
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
                <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}" alt="${siteName!""}" /></a>
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
                                            <th>竞赛编号</th>
                                            <th>竞赛标题</th>
                                            <th>团队名称</th>
                                            <th>队长学号</th>
                                            <th>队长姓名</th>
                                            <th>分数</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#if topTenList??>
                                            <#list topTenList as topTen>
                                                <tr>
                                                    <td style="vertical-align:middle;">${topTen.competition.id}</td>
                                                    <td style="vertical-align:middle;">${topTen.competition.title}</td>
                                                    <td style="vertical-align:middle;">${topTen.name}</td>
                                                    <td style="vertical-align:middle;">${topTen.student.studentNumber}</td>
                                                    <td style="vertical-align:middle;">${topTen.student.name}</td>
                                                    <td style="vertical-align:middle;">
                                                       ${topTen.finalsScore}
                                                    </td>
                                                </tr>
                                            </#list>
                                        <#else>
                                            <tr align="center"><td colspan="9">这里空空如也！</td></tr>
                                        </#if>
                                        </tbody>
                                    </table>
                                </div>
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
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>