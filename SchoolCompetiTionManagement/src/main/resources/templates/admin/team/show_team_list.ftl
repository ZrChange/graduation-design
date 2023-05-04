<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|团队成员-${title!""}</title>
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
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" id="check-all"><span></span>
                          </label>
                        </th>
                        <th>团队名称</th>
                        <th>成员</th>
                      </tr>
                    </thead>
                    <tbody>
                      <#if teamMembers?size gt 0>
                      <#list teamMembers as item>
                      <tr>
                        <td style="vertical-align:middle;">
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" name="ids[]" value="${item.id}"><span></span>
                          </label>
                        </td>
                        <td style="vertical-align:middle;">${item.teamCompetition.name}</td>
                          <td style="vertical-align:middle;">
                              ${item.student.name}
                              <#if item.isCaptain == 1>
                                  <font style="font-size: 6px; margin-left: 8px" color="red">队长</font>
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
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
    <!--End 页面主要内容-->
  </div>
</div>
<input type="button" id="fileButton" style="display: none;"/>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript" src="/common/layui/layui.all.js"></script>
<link rel="stylesheet" href="/common/layui/css/layui.css"/>
<script type="text/javascript">
$(document).ready(function(){
    var loginCaptain = "${loginCaptain}";

    if(loginCaptain == "1")
    {
        $(".toolbar-btn-action")
                .append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='makeCaptain()' id='file'>转让队长</a>");
        $(".toolbar-btn-action")
                .append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='dissolveTeam()' id='file'>解散团队</a>");
        $(".toolbar-btn-action")
                .append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='kickOut()' id='file'>踢人</a>");

        if(M.showButton == 1)
            $(".toolbar-btn-action")
                    .append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='uploadAccessory()' id='file'>上传压缩附件</a>");
    }
    else
    {
        $(".toolbar-btn-action")
                .append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='quit()' id='file'>退出</a>");
    }

    if(M.showButton == 1)
        $(".toolbar-btn-action")
                .append("<a class='btn btn-primary m-r-5' name='file' href='javascript:void(0);' onclick='downloadFile()' id='file'>下载压缩附件</a>");


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

var M = {};
M.id = ${teamcompetitionId};
M.showButton = ${showButton}

function makeCaptain()
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择一个队员！');
        return;
    }

    var id = $("input[type='checkbox']:checked").val();

    $.ajax({
        url:'make_captain',
        type:'POST',
        data:{id:id,teamId:M.id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("队长转让成功",function()
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

function quit()
{
    $.ajax({
        url:'quit_team',
        type:'POST',
        data:{teamId:M.id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("退出成功",function()
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

//解散团队
function dissolveTeam()
{
    $.ajax({
        url:'dissolve_team',
        type:'POST',
        data:{teamId:M.id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("解散成功",function()
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

//下载文件
function downloadFile()
{
    $.ajax({
        url:'is_download',
        type:'POST',
        data:{id:M.id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                location.href = "/download/downLoadZipFileTeam?id=" + M.id;
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    })
}

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
                    location.href = "my_game_list";
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

function kickOut()
{
    if($("input[type='checkbox']:checked").length != 1){
        showWarningMsg('请选择一个队员！');
        return;
    }

    var id = $("input[type='checkbox']:checked").val();

    $.ajax({
        url:'kick_out',
        type:'POST',
        data:{id:id,teamId:M.id},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                showSuccessMsg("踢出成功", function()
                {
                    location=location
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

</script>
</body>
</html>