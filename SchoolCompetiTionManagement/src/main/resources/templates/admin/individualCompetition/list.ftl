<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|个人参赛记录-${title!""}</title>
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
                <form class="pull-right search-bar" method="get" action="list" role="form">
                  <div class="input-group">
                    <div class="input-group-btn">
                      <button class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                      竞赛标题 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">竞赛标题</a> </li>
                      </ul>
                    </div>
                    <input type="text" class="form-control" value="${competitionTitle!""}" name="competitionTitle" placeholder="请输入竞赛标题">
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
                        <th>竞赛编号</th>
                        <th>竞赛标题</th>
                        <th>竞赛类型</th>
                        <th>报名状态</th>
                        <th>竞赛阶段</th>
                      </tr>
                    </thead>
                    <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as individualCompetition>
                      <tr>
                        <input type="hidden" name="competitionId" id="competitionId" value="${individualCompetition.competition.id}">
                        <td style="vertical-align:middle;">
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" name="ids[]" value="${individualCompetition.id}"><span></span>
                          </label>
                        </td>
                        <td style="vertical-align:middle;">${individualCompetition.competition.id}</td>
                        <td style="vertical-align:middle;">${individualCompetition.competition.title}</td>
                        <td style="vertical-align:middle;">
                          <#if individualCompetition.competition.raceType == 1>
                              <font class="text">开放型</font>
                          </#if>
                          <#if individualCompetition.competition.raceType == 2>
                            <font class="text">专业型</font>
                          </#if>
                        </td>
                        <td style="vertical-align:middle;">
                          <#if individualCompetition.applyStatus == 1>
                            <font class="text-success">报名成功</font>
                          <#elseif individualCompetition.applyStatus == 0>
                            <font class="text">审批中</font>
                          <#else>
                            <font class="text-error">报名未通过</font>
                          </#if>
                        </td>
                        <td>
                          <#if individualCompetition.applyStatus == 1>
                            <#if individualCompetition.preliminariesStatus == 1>
                              <#if individualCompetition.secondRoundStatus == 1>
                                <font class="text-error">决赛</font>
                              <#else>
                                <font class="text-error">复赛</font>
                              </#if>
                            <#else>
                              <font class="text-error">初赛</font>
                            </#if>
                          <#else>
                            <font class="text-error">未进入竞赛</font>
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
                  <li><a href="list?competitionTitle=${competitionTitle!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                  <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                  <#else>
                  <li><a href="list?competitionTitle=${competitionTitle!""}&currentPage=${showPage}">${showPage}</a></li>
                  </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?competitionTitle=${competitionTitle!""}&currentPage=${pageBean.totalPage}">»</a></li>
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
<div class="modal fade" id="score" tabindex="-1" role="dialog" aria-labelledby="score">
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
<input type="button" onclick="" id="fileButton"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript" src="/common/layui/layui.all.js"></script>
<link rel="stylesheet" href="/common/layui/css/layui.css"/>
<script type="text/javascript">
    var M = {};

$(document).ready(function(){
    $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='uploadAccessory()'>上传压缩附件</a>");
    $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='downloadFile()'>下载压缩附件</a>");

    layui.use(['upload'],function () {
        var upload = layui.upload;
        upload.render({
            elem:'#fileButton',
            url: "/upload/upload_zip",
            accept:'file',
            exts: 'zip|rar|7z',
            done:function (res) {
                if (res.code==0){
                    upload_accessory(res.data);
                } else {
                    layer.msg(res.msg,{icon:5});
                }
            }
        });
    });

});

//保存到数据库
function upload_accessory(data)
{
    $.ajax({
        url:'upload_accessory',
        type:'POST',
        data:{id:M.id,resocre:data},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("阶段附件上传成功", function()
                {
                    location.href = "list";
                });
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    })
}

function uploadAccessory()
{
    if ($("input[type='checkbox']:checked").length != 1) {
        showWarningMsg('请选择一条竞赛！');
        return;
    }
    M.id = $("input[type='checkbox']:checked").val();
    console.log(M.id);
    $.ajax({
        url:'is_upload_accessory',
        type:'POST',
        data:{id:M.id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                $("#fileButton").click();
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    })

}



function viewCompetition(url) {
  if ($("input[type='checkbox']:checked").length != 1) {
    showWarningMsg('请选择一条竞赛进行查看！');
    return;
  }
  var id = $('#competitionId').val()
  /*var id = $("input[type='checkbox']:checked").val();*/
  $.get(url, {id: id}, function (result) {
    $(".modal-body").empty();
    $(".modal-body").html(result);
    $('#viewCompetition').modal('show');
  });
}
/*//查看竞赛信息页面
function viewCompetition(url){
    var competitionId = $('#competitionId').val()
	if($("input[type='checkbox']:checked").length != 1){
		showWarningMsg('请选择要查看的竞赛！');
		return;
	}
	window.location.href = url + '?id='+competitionId;
}*/

//查看该竞赛成绩页面
function showScore(url){
  if($("input[type='checkbox']:checked").length != 1){
    showWarningMsg('请选择要查看的竞赛！');
    return;
  }
  var id = $("input[type='checkbox']:checked").val();
  $.ajax({
    url:'applyStatus',
    type:'POST',
    data:{id:id},
    dataType:'json',
    success:function(data){
      if(data.code == 0){
        window.location.href = url + '?id=' + id;
      }else{
        showErrorMsg(data.msg);
      }
    },
    error:function(data){
      alert('网络错误!');
    }
  })
}

function downloadFile()
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择竞赛！');
        return;
    }
    var id = $("input[type='checkbox']:checked").val();

    $.ajax({
        url:'is_download',
        type:'POST',
        data:{id:id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                location.href = "/download/downLoadZipFile?id=" + id;
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    })
}

</script>
</body>
</html>