<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|学生管理-${title!""}</title>
<#include "../common/header.ftl"/>
<style>
td{
	vertical-align:middle;
}

    .layui-upload-file
    {
        display: none;
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
                          姓名 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">姓名</a> </li>
                      </ul>
                    </div>
                    <input type="text" class="form-control" value="${name!""}" name="name" placeholder="请输入姓名">
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
                        <th>头像</th>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>班级</th>
                        <th>手机号</th>
                        <th>学院</th>
                        <th>专业</th>
                        <th>状态</th>
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
                          <td style="vertical-align:middle;">
                              <#if item.headPic??>
                                  <#if item.headPic?length gt 0>
                                    <img src="/photo/view?filename=${item.headPic}" width="60px" height="60px">
                                  <#else>
                                    <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                                  </#if>
                              <#else>
                                <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                              </#if>
                          </td>
                        <td style="vertical-align:middle;" align="center">${item.studentNumber}</td>
                        <td style="vertical-align:middle;" align="center">${item.name}</td>
                        <td style="vertical-align:middle;" align="center">
                            <#if item.sex == 0>
                                未知
                            <#elseif item.sex == 1>
                                男
                            <#else>
                                女
                            </#if>
                        </td>
                          <td style="vertical-align:middle;" align="center">${item.age}</td>
                          <td style="vertical-align:middle;" align="center">${item.clazz.name}</td>
                          <td style="vertical-align:middle;" align="center">${item.phone}</td>
                          <td style="vertical-align:middle;" align="center">${item.clazz.professional.college.name}</td>
                          <td style="vertical-align:middle;" align="center">${item.clazz.professional.name}</td>
                        <td style="vertical-align:middle;" style="vertical-align:middle;">
                            <#if item.status == 0>
                                <font color="red">不可用</font>
                            <#else>
                                <font color="green">可用</font>
                            </#if>
                        </td>
                      </tr>
                    </#list>
                    <#else>
                    <tr align="center"><td colspan="11">这里空空如也！</td></tr>
					</#if>
                    </tbody>
                  </table>
                </div>
                <#if pageBean.total gt 0>
                <ul class="pagination ">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                  <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=${showPage}">${showPage}</a></li>
                  </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?name=${name!""}&currentPage=${pageBean.totalPage}">»</a></li>
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
<script type="text/javascript" src="/common/layui/layui.all.js"></script>
<link rel="stylesheet" href="/common/layui/css/layui.css"/>
<script type="text/javascript">
$(document).ready(function(){
    $(".toolbar-btn-action").append("<a class='btn btn-primary m-r-5' name='file' id='file'><i class='mdi mdi-view-day'></i>导入</a>");

    layui.use(['upload'],function () {
        var upload = layui.upload;
        upload.render({
            elem:'#file',
            url: "/admin/student/lead",
            accept:'file',
            done:function (res) {
                if (res.code==0){
                    layer.msg("上传成功",{icon:1});
                    window.location.href = '/admin/student/list';
                } else {
                    layer.msg(res.msg,{icon:5});
                }
            }
        });
    });

});

function del(url){
	if($("input[type='checkbox']:checked").length != 1){
		showWarningMsg('请选择一条数据进行删除！');
		return;
	}
	var id = $("input[type='checkbox']:checked").val();
	$.confirm({
        title: '确定删除？',
        content: '删除后数据不可恢复，请慎重！',
        buttons: {
            confirm: {
                text: '确认',
                action: function(){
                    deleteReq(id,url);
                }
            },
            cancel: {
                text: '关闭',
                action: function(){
                    
                }
            }
        }
    });
}


//打开编辑页面
function edit(url){
	if($("input[type='checkbox']:checked").length != 1){
		showWarningMsg('请选择一条数据进行编辑！');
		return;
	}
	window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
}
//调用删除方法
function deleteReq(id,url){
	$.ajax({
		url:url,
		type:'POST',
		data:{id:id},
		dataType:'json',
		success:function(data){
			if(data.code == 0){
				showSuccessMsg('删除成功!',function(){
					$("input[type='checkbox']:checked").parents("tr").remove();
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

function loadFile(url)
{
    window.location.href=url;
}










</script>
</body>
</html>