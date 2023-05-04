<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|班级管理-添加班级</title>
<#include "../common/header.ftl"/>

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
              <div class="card-header"><h4>添加班级</h4></div>
              <div class="card-body">

                <form id="judge-add-form" class="row">

                  <div class="input-group m-b-10">
                    <span class="input-group-addon">班级名称</span>
                    <input type="text" class="form-control required" id="name" name="name" value="" placeholder="请输入班级名称" tips="请填写班级名称" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">所属年级</span>
                    <select name="grade" class="form-control" id="grade">
                      <option value="">--请选择年级--</option>
                      <#list grades as grade>
                        <option value="${grade.id}">${grade.name}</option>
                      </#list>
                    </select>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">所属学院</span>
                    <select name="college" class="form-control" id="college" onchange="changeCate()">
                      <option value="">--请选择学院--</option>
                      <#list colleges as college>
                        <option value="${college.id}">${college.name}</option>
                      </#list>
                    </select>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">所属专业</span>
                    <select name="professional" class="form-control" id="professional">
                    </select>
                  </div>
                  <div class="form-group col-md-12">
                    <button type="button" class="btn btn-primary ajax-post" id="add-form-submit-btn">确 定</button>
                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                  </div>
                </form>
       
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

  function changeCate(){
    $("#professional").empty();
    var  collegeId = $("#college").find("option:selected").val();
    $.ajax({
      url:'professional',
      type:'post',
      dataType:'json',
      data:{
        id:collegeId
      },
      success:function (data) {
        var html='<option value=\'\'>---请选择专业---</option>';
        for(var i = 0, len = data.length; i < len; i++)
        {
          html+="<option value='" + data[i].id + "'>" + data[i].name + "</option>";
        }
        $("#professional").append(html);
      },
      error:function(data){
        alert('网络错误!');
      }
    })

  }


$(document).ready(function(){
	//提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("judge-add-form")){
			return;
		}
		var data = $("#judge-add-form").serialize();
		$.ajax({
			url:'add',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('班级添加成功!',function(){
						window.location.href = 'list';
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
	//监听上传图片按钮
	$("#add-pic-btn").click(function(){
		$("#select-file").click();
	});
});

</script>
</body>
</html>