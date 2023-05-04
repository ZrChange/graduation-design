<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|团队成绩-${title!""}</title>
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
                        <th>
                            阶段
                        </th>
                          <th>
                              分数
                          </th>
                      </tr>
                    </thead>
                    <tbody>
                      <#if teamCompetition??>
                        <tr align="center">
                            <td>初赛</td>
                            <td>
                                <#if teamCompetition.preliminariesScoreStatus == 0>
                                    还未打分
                                <#else>
                                    ${teamCompetition.preliminariesScore}
                                </#if>
                            </td>
                        </tr>
                          <tr align="center">
                              <td>复赛</td>
                              <td>
                                  <#if teamCompetition.secondRoundScoreStatus == 0>
                                      还未打分
                                  <#else>
                                      ${teamCompetition.secondRoundScore}
                                  </#if>
                              </td>
                          </tr>
                          <tr align="center">
                              <td>决赛</td>
                              <td>
                                 <#if teamCompetition.finalsScoreStatus == 0>
                                     还未打分
                                 <#else>
                                     ${teamCompetition.finalsScore}
                                 </#if>
                              </td>
                          </tr>
                      <#else>
                        <tr align="center"><td colspan="9">还没有分数呢</td></tr>
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
$(document).ready(function(){


});

function show(url)
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择一条比赛记录！');
        return;
    }
    var id = $("input[type='checkbox']:checked").val();

    location.href = url + "?id=" + id;
}

function showScore(url)
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择一条比赛记录！');
        return;
    }
    var id = $("input[type='checkbox']:checked").val();

    location.href = url + "?id=" + id;
}
</script>
</body>
</html>