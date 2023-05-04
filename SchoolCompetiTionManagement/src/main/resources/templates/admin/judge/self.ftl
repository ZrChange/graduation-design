<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|评委管理-${title!""}</title>
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
                        <th>姓名</th>
                        <th>所属学院</th>
                        <th>手机</th>
                        <th>性别</th>
                        <th>状态</th>
                        <th>添加时间</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td style="vertical-align:middle;">${judge.name}</td>
                        <td style="vertical-align:middle;">${judge.college.name}</td>
                        <td style="vertical-align:middle;">${judge.mobile}</td>
                        <td style="vertical-align:middle;">
                          <#if judge.sex == 1>
                            <font class="text-success">男</font>
                          <#elseif judge.sex == 2>
                            <font class="text-info">女</font>
                          <#else>
                            <font class="text-warning">未知</font>
                          </#if>
                        </td>
                        <td style="vertical-align:middle;">
                          <#if judge.status == 1>
                            <font class="text-success">正常</font>
                          <#else>
                            <font class="text-warning">冻结</font>
                          </#if>
                        </td>
                        <td style="vertical-align:middle;" style="vertical-align:middle;"><font class="text-success">${judge.createTime}</font></td>
                      </tr>
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

//打开编辑页面
function edit(url){
  var id = ${judge.id}
	window.location.href = url+'?id='+id;
}
</script>
</body>
</html>