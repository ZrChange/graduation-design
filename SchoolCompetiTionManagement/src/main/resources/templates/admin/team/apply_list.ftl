<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|申请记录-${title!""}</title>
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
                <form class="pull-right search-bar" method="get" action="apply_list" role="form">
                  <div class="input-group">
                    <div class="input-group-btn">
                      <button class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                          申请记录 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">学生姓名</a> </li>
                      </ul>
                    </div>
                    <input type="text" class="form-control" value="${name!""}" name="name" placeholder="请输入姓名">
                    <input type="hidden" class="form-control" value="${id}" name="id" placeholder="请输入姓名">
                  	<span class="input-group-btn">
                      <button class="btn btn-primary" type="submit">搜索</button>
                    </span>
                  </div>
                </form>
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
                        <th>姓名</th>
                        <th>是否同意</th>
                      </tr>
                    </thead>
                    <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as item>
                      <tr>
                        <td style="vertical-align:middle;">
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" name="ids[]" value="${item.id}"><span></span>
                          </label>
                        </td>
                        <td style="vertical-align:middle;">${item.student.name}</td>
                        <td style="vertical-align:middle;">
                            <#if item.status == 0>
                                <font style="color:red;">未同意</font>
                            <#else>
                                <font style="color:green;">已同意</font>
                            </#if>
                        </td>

                      </tr>
                    </#list>
                    <#else>
                    <tr align="center"><td colspan="9">您还未参加过个人竞赛！</td></tr>
					</#if>
                    </tbody>
                  </table>
                </div>
                <#if pageBean.total gt 0>
                <ul class="pagination ">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="apply_list?id=${id}&name=${name!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                  <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                  <#else>
                  <li><a href="apply_list?id=${id}&name=${name!""}&currentPage=${showPage}">${showPage}</a></li>
                  </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="apply_list?id=${id}&name=${name!""}&currentPage=${pageBean.totalPage}">»</a></li>
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
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){

    $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='add()' id='file'>同意</a>");
    $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='del()' id='file'>删除</a>");

});

function del(url)
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择一条记录！');
        return;
    }
    var id = $("input[type='checkbox']:checked").val();

    $.ajax({
        url:'apply_delete',
        type:'POST',
        data:{id:id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("删除成功", function()
                {
                    location.href = "my_game_list";
                });
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    });
}

function add(url)
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择一条记录！');
        return;
    }
    var id = $("input[type='checkbox']:checked").val();

    $.ajax({
        url:'apply_add',
        type:'POST',
        data:{id:id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("同意成功", function()
                {
                    location.href = "my_game_list";
                });
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    });
}
</script>
</body>
</html>