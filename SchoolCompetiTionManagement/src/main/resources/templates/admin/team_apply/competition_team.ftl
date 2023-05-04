<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|团队-${title!""}</title>
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
                  <form class="pull-right search-bar" method="get" action="competition_team" role="form">
                      <div class="input-group">
                          <div class="input-group-btn">
                              <button class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                                  团队名称 <span class="caret"></span>
                              </button>
                              <ul class="dropdown-menu">
                                  <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">团队名称</a> </li>
                              </ul>
                          </div>
                          <input type="hidden" name="competitionId" value="${competitionId}"/>
                          <input type="text" class="form-control" value="${name!""}" name="name" placeholder="请输入团队名称">
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
                        <th>团队名称</th>
                        <th>队长</th>
                          <th>团队成员</th>
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
                        <td style="vertical-align:middle;">${item.name}</td>
                        <td style="vertical-align:middle;">${item.student.name}</td>
                          <td style="vertical-align:middle;">
                              <#list item.teamMembers as teamItem>
                                  <span style="margin-left: 5px">${teamItem.student.name}</span>
                              </#list>
                          </td>
                      </tr>
                    </#list>
                    <#else>
                        <tr align="center"><td colspan="9">暂无数据！</td></tr>
					</#if>
                    </tbody>
                  </table>
                </div>
              <#if pageBean.total gt 0>
                <ul class="pagination ">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="competition_team?name=${name!""}&currentPage=1&competitionId=${competitionId}">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                      <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                      <#else>
                  <li><a href="competition_team?name=${name!""}&currentPage=${showPage}&competitionId=${competitionId}">${showPage}</a></li>
                      </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="competition_team?name=${name!""}&currentPage=${pageBean.totalPage}&competitionId=${competitionId}">»</a></li>
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
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="viewCompetition">创建团队</h4>
            </div>
            <div class="modal-body">
                <div class="input-group m-b-10" id="edit-personal">
                    <span class="input-group-addon">团队名称</span>
                    <input type="text" class="form-control required input" id="name"
                           name="name"
                           placeholder="请输入团队名称"
                           tips="请输入团队名称"/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" id="create-team">创建</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="score" tabindex="-1" role="dialog" aria-labelledby="score">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
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

<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='createTeam()' id='file'>创建团队</a>");
    $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='addTeam()' id='file'>申请加入</a>");
    if(M.button == 1)
        $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='checkApply()' id='file'>报名</a>");

});
var M = {};
M.id = ${competitionId};
M.button = ${applyButton};

//创建团队
function createTeam()
{
    $('#viewCompetition').modal('show');
}

function addTeam()
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择一个团队！');
        return;
    }

    var id = $("input[type='checkbox']:checked").val();
    $.ajax({
        url:'join_team',
        type:'POST',
        data:{id:id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg('申请成功!',function(){
                    window.location.href = '/admin/competition/list';
                })
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    });
}

$(function()
{
   $("#create-team").click(function()
   {
       var name = $("#name").val();
       if(name == null || name.trim().length == 0)
       {
           showErrorMsg("请输入团队名称");
           return ;
       }

       $.ajax({
           url:'create_team',
           type:'POST',
           data:{id:M.id,name:name},
           dataType:'json',
           success:function(data){
               if(data.code == 0){
                   showSuccessMsg('创建成功!',function(){
                       window.location.href = '/admin/competition/list';
                   })
               }else{
                   showErrorMsg(data.msg);
               }
           },
           error:function(data){
               alert('网络错误!');
           }
       });
   });
});

function checkApply()
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择一个团队！');
        return;
    }
    var id = $("input[type='checkbox']:checked").val();

    $.ajax({
        url:'/admin/team/check_apply',
        type:'POST',
        data:{id:id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg('报名成功!',function(){
                    window.location.href = '/admin/competition/list';
                })
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