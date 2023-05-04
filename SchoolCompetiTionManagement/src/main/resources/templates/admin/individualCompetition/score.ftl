<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|个人参赛成绩</title>
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
                      <tr class="score-tr">
                        <th>标题</th>
                        <td>${individualCompetition.competition.title}</td>
                      </tr>
                      <tr class="score-tr">
                        <th>初赛分数</th>
                        <td>
                          <#if individualCompetition.preliminariesScoreStatus == 1>
                            ${individualCompetition.preliminariesScore!"0"}
                          <#else>
                            该阶段无成绩
                          </#if>
                        </td>
                      </tr>
                      <tr class="score-tr">
                        <th>复赛分数</th>
                        <td>
                          <#if individualCompetition.secondRoundScoreStatus == 1>
                            ${individualCompetition.secondRoundScore!"0"}
                          <#else>
                            该阶段无成绩
                          </#if>
                        </td>
                      </tr>
                      <tr class="score-tr">
                        <th>决赛分数</th>
                        <td>
                          <#if individualCompetition.finalsScoreStatus == 1>
                            ${individualCompetition.finalsScore!"0"}
                          <#else>
                            该阶段无成绩
                          </#if>
                        </td>
                      </tr>
                    </thead>
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
$(document).ready(function(){

});

//查看竞赛信息页面
function showCompetition(url){
    var competitionId = $('#competitionId').val()
	if($("input[type='checkbox']:checked").length != 1){
		showWarningMsg('请选择要查看的竞赛！');
		return;
	}
	window.location.href = url + '?id='+competitionId;
}

//查看该竞赛成绩页面
function showScore(url){
  if($("input[type='checkbox']:checked").length != 1){
    showWarningMsg('请选择要查看的竞赛！');
    return;
  }
  window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
}


</script>
</body>
</html>