<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|用户管理-添加学生</title>
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
              <div class="card-header"><h4>添加学生</h4></div>
              <div class="card-body">
                <form action="add" id="user-add-form" method="post" class="row">
                  <div class="form-group col-md-12">
                    <label>头像上传</label>
                    <div class="form-controls">
                      <ul class="list-inline clearfix lyear-uploads-pic">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <figure>
                              <img src="/admin/images/default-head.jpg" id="show-picture-img" alt="默认头像">
                          </figure>
                        </li>
                        <input type="hidden" name="headPic" id="headPic">
                        <input type="file" id="select-file" style="display:none;" onchange="upload('show-picture-img','headPic')">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <a class="pic-add" id="add-pic-btn" href="javascript:void(0)" title="点击上传"></a>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">姓名</span>
                    <input type="text" class="form-control required" id="name" name="name" value=""
                           placeholder="请输入姓名" tips="请填写姓名" />
                  </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">年龄</span>
                        <input type="number"   class="form-control required"  id="age" name="age" value=""
                               placeholder="请输入年龄" tips="请填写年龄" oninput="if(value>200)value=200 ;if (value<0)value=0" min="0" max="200"/>
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">学号</span>
                        <input type="number"   class="form-control required"  id="studentNumber" name="studentNumber" value="" placeholder="请输入学号" tips="请填写学号" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">学院</span>
                        <select class="form-control" id="college" onchange="changeCollege()">
                            <option value="">--请选择</option>
                            <#list colleges as item>
                                 <option value="${item.id}">${item.name}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">专业</span>
                        <select class="form-control" id="professional" onchange="changeProfessional()">
                            <option value="">--请选择</option>
                        </select>
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">班级</span>
                        <select class="form-control" name="clazz.id" id="clazz">
                            <option value="">--请选择</option>
                        </select>
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">手机号</span>
                        <input type="text" class="form-control required"  id="phone" name="phone" value=""
                               placeholder="请输入手机号" tips="请填写手机号" />
                    </div>
                    <div class="input-group m-b-10">
                        <span class="input-group-addon">密码</span>
                        <input type="password" class="form-control required"  id="password" name="password" value=""
                               placeholder="请输入密码" tips="请填写密码" />
                    </div>
                    <div class="input-group" style="margin-top:15px;margin-bottom:15px;padding-left:25px;">
                        性别：
                        <label class="lyear-radio radio-inline radio-primary" style="margin-left:30px;">
                            <input type="radio" name="sex" value="1">
                            <span>男</span>
                        </label>
                        <label class="lyear-radio radio-inline radio-primary">
                            <input type="radio" name="sex" value="2">
                            <span>女</span>
                        </label>
                        <label class="lyear-radio radio-inline radio-primary">
                            <input type="radio" name="sex" value="0" checked>
                            <span>未知</span>
                        </label>
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
$(document).ready(function(){
	//提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("user-add-form")){
			return;
		}
		var data = $("#user-add-form").serialize();
		$.ajax({
			url:'add',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('添加成功!',function(){
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


function changeCollege()
{
    var collegeId = $("#college").val();
    if(collegeId.trim() == "")
    {
        $("#professional").html('');
        $("#clazz").html('');

        var option = '<option value="">--请选择</option>';
        $("#professional").append(option);

        $("#clazz").append(option);
        return ;
    }

    $.ajax({
        url:'find_professional',
        type:'POST',
        data:{id:collegeId},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                $("#professional").html('');

                var option = '<option value="">--请选择</option>';
                $("#professional").append(option);

                for (var row of data.data)
                {
                    option = '<option value="'+row.id+'">'+row.name+'</option>';
                    $("#professional").append(option);
                }
            }else{
                showErrorMsg(data.msg);
            }
        },
        error:function(data){
            alert('网络错误!');
        }
    });
}

function changeProfessional()
{
    var professional = $("#professional").val();

    if(professional.trim() == "")
    {
        $("#clazz").html('');
        var option = '<option value="">--请选择</option>';
        $("#clazz").append(option);
        return ;
    }

    $.ajax({
        url:'find_clazz',
        type:'POST',
        data:{id:professional},
        dataType:'json',
        success:function(data){
            if(data.code == 0){
                $("#clazz").html('');

                var option = '<option value="">--请选择</option>';
                $("#clazz").append(option);

                for (var row of data.data)
                {
                    option = '<option value="'+row.id+'">'+row.name+'</option>';
                    $("#clazz").append(option);
                }
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