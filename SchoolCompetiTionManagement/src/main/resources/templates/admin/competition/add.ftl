<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|竞赛管理-添加竞赛</title>
<#include "../common/header.ftl"/>
    <link href="/admin/css/font-awesome.min.css" rel="stylesheet">
    <link href="/admin/select2/select2.min.css" rel="stylesheet">
    <link href="/admin/datepicker/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="/admin/kindeditor/themes/default/default.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="/admin/bootstrap-select/dist/css/bootstrap-select.min.css">

</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}"
                                             alt="${siteName!""}"/></a>
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
                            <div class="card-header"><h4>添加竞赛</h4></div>
                            <div class="card-body">
                                <form id="competition-add-form" method="post" class="row">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">竞赛标题</span>
                                        <input type="text" class="form-control required input" id="title" name="title"
                                               value="" placeholder="请输入竞赛标题" tips="请输入竞赛标题" minlength="5"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">竞赛类型</span>
                                        <select class="form-control selectRequired selectpicker" data-style="bg-primary"
                                                id="add-raceType" name="raceType" tips="请选择竞赛类型"
                                                size="1">
                                            <option value="-1">--请选择竞赛类型--</option>
                                            <#list RaceType as type>
                                                <option value="${type.code}">${type.info}</option>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10" style="display: none;" id="add-college">
                                        <span class="input-group-addon">竞赛学院</span>
                                        <select class="form-control" data-live-search="true"
                                                data-style="bg-primary"
                                                id="college" name="college.id" onchange="findJudegeListByCollege(this)"
                                                tips="请选择竞赛学院">
                                            <option value="-1">--请选择学院--</option>
                                            <#list collegeList as college>
                                                <option value="${college.id}">${college.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10" style="display: none;" id="add-professional">
                                        <span class="input-group-addon">竞赛专业</span>
                                        <select class="form-control selectRequired selectpicker" data-live-search="true"
                                                data-style="bg-primary"
                                                id="professional" name="professional.id" tips="请选择竞赛专业">

                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">竞赛评委</span>
                                        <select class="form-control select2-multiple judge" multiple id="judges"
                                                name="judges" tips="请选择竞赛评委">

                                        </select>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">竞赛年级</span>
                                        <select class="form-control selectRequired selectpicker select2-multiple"
                                                data-none-selected-text="请选择年级" multiple id="grades" name="grades"
                                                tips="请选择年级">
                                            <#list gradeList as grade>
                                                <option value="${grade.id}">${grade.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">参赛类型</span>
                                        <select class="form-control selectRequired selectpicker"
                                                id="add-competitionType"
                                                name="competitionType" tips="请选择参赛类型">
                                            <option value="-1">--请选择参赛类型--</option>
                                            <#list competitionType as type>
                                           <option value="${type.code}">${type.info}</option>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10" id="add-personal" style="display: none">
                                        <span class="input-group-addon">可参加人数</span>
                                        <input type="number" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"class="form-control required input" id="add-enrollmentNumber"
                                               name="enrollmentNumber" disabled
                                               value="1" placeholder="请输入最多人员数" tips="请输入最多人员数" min="1" max="100"/>
                                    </div>
                                    <div class="input-group m-b-10 add-team" id="add-team" style="display: none">
                                        <span class="input-group-addon">可参加团队数</span>
                                        <input type="number" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" class="form-control required input" id="add-teams"
                                               name="enrollmentNumber" disabled
                                               value="1" placeholder="请输入最多团队数" tips="请输入最多团队数"/>
                                    </div>
                                    <div class="input-group m-b-10 add-team" id="add-teamSize" style="display: none">
                                        <span class="input-group-addon">每个团队人数</span>
                                        <input type="number" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" class="form-control required input" id="teamSize"
                                               name="teamSize"
                                               value="1" placeholder="请输入最多团队数" tips="请输入最多团队数"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control datepicker-time" id="enrollStartTime"
                                               name="enrollStartTime"
                                               placeholder="报名开始时间" tips="请选择报名开始时间" >
                                        <span class="input-group-addon"><i class="mdi mdi-chevron-right"></i></span>
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control datepicker-time" id="enrollEndTime"
                                               name="enrollEndTime"
                                               placeholder="报名结束时间" tips="请选择报名结束时间">
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control datepicker-time"
                                               id="preliminariesStartTime"
                                               name="preliminariesStartTime"
                                               placeholder="初赛开始时间" tips="请选择初赛开始时间">
                                        <span class="input-group-addon"><i class="mdi mdi-chevron-right"></i></span>
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control datepicker-time"
                                               id="preliminariesEndTime"
                                               name="preliminariesEndTime"
                                               placeholder="初赛结束时间" tips="请选择初赛结束时间">
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control datepicker-time"
                                               id="secondRoundStartTime"
                                               name="secondRoundStartTime"
                                               placeholder="复赛开始时间" tips="请选择复赛开始时间">
                                        <span class="input-group-addon"><i class="mdi mdi-chevron-right"></i></span>
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control datepicker-time" id="secondRoundEndTime"
                                               name="secondRoundEndTime"
                                               placeholder="复赛结束时间" tips="请选择复赛结束时间">
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control datepicker-time" id="finalsStartTime"
                                               name="finalsStartTime"
                                               placeholder="决赛开始时间" tips="请选择决赛开始时间">
                                        <span class="input-group-addon"><i class="mdi mdi-chevron-right"></i></span>
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control datepicker-time" id="finalsEndTime"
                                               name="finalsEndTime"
                                               placeholder="决赛结束时间" tips="请选择决赛结束时间">
                                    </div>
                                    <div class="input-group m-b-10" id="add-personal">
                                        <span class="input-group-addon">竞赛的详情介绍</span>
                                        <textarea style="width:auto;height:250px" id="content"
                                                  name="detailedIntroduction"></textarea>
                                    </div>

                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post"
                                                id="add-form-submit-btn">确 定
                                        </button>
                                        <button type="button" class="btn btn-default"
                                                onclick="javascript:history.back(-1);return false;">返 回
                                        </button>
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
<script type="text/javascript" src="/admin/select2/select2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/admin/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript" src="/admin/datepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/admin/js/datetimepicker.js"></script>
<script type="text/javascript" src="/admin/bootstrap-select/js/bootstrap-select.js"></script>
<script type="text/javascript" src="/admin/bootstrap-select/js/i18n/defaults-zh_CN.js"></script>

<script type="text/javascript">

    $(document).ready(function () {

        $("#professional").selectpicker(
                {
                    "width": 60
                }
        )
        $("#grades").select2(
                {placeholder: "--请选择年级--"}
        );

        KindEditor.ready(function (K) {
            K.create('#content', {
                uploadJson: '/upload/uploadFile',
                filePostName: 'imgFile',
                allowFileManager: true,
                allowImageUpload: true,
                width: '100%',  //编辑器的宽
                height: '460px',  //编辑器的高
                items: ['fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold',
                    'italic', 'underline', 'removeformat', 'justifyleft', 'justifycenter', 'justifyright',
                    'insertorderedlist', 'insertunorderedlist', 'emoticons', 'image', 'insertfile'
                ],
                afterBlur: function () {
                    this.sync();
                },
                allowImageRemote: false,
                afterUpload: function (url, data, name) { //上传文件后执行的回调函数，必须为3个参数
                    if (name == "image" || name == "multiimage") { //单个和批量上传图片时
                        var img = new Image();
                        img.src = url;
                        img.onload = function () {
                        }
                    }
                }
            });
        });

        //提交按钮监听事件
        $("#add-form-submit-btn").click(function () {
            if (!checkForm("competition-add-form")) {
                return;
            }
            if (!checkSelect("form-control")) {
                return;
            }
            var juedges = $("#judges").val();
            var grades = $("#grades").val();
            if (juedges == null) {
                showWarningMsg("请选择评委");
                return;
            }
            if (grades == null) {
                showWarningMsg("请选择年级");
                return;
            }
            if ($("#add-raceType").val() == race_type.PROFESSIONAL) {
                if ($("#professional").val() == null || $("#professional").val() == "") {
                    showWarningMsg("请选择专业");
                    return;
                }
            } else {
                $("#college").attr("disabled", true);
            }
            if (!checkDateTime(".form-control.datepicker-time")) {
                return;
            }
            var details = $("#content").val();
            if (details == null || details == "") {
                showWarningMsg("请填写竞赛介绍");
                return;
            }
            var data = $("#competition-add-form").serialize();
            $.ajax({
                url: 'add',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        showSuccessMsg('竞赛添加成功!', function () {
                            window.location.href = 'list';
                        })
                    } else {
                        showErrorMsg(data.msg);
                    }
                },
                error: function (data) {
                    alert('网络错误!');
                }
            });
        });
        //监听上传图片按钮
        $("#add-pic-btn").click(function () {
            $("#select-file").click();
        });
    });

    //根据学院查找所属评委
    function findJudegeListByCollege(t) {
        var collegeId = $(t).val();
        findJuegeList(collegeId, true);
    }

    //查询评委
    function findJuegeList(collegeId, isMajar) {
        $.get("/admin/judge/judgeList", {collegeId: collegeId}, function (result) {
                    var judgeList = result.data;
                    var arr = new Array();
                    for (var i = 0; i < judgeList.length; i++) {
                        var option = {id: judgeList[i].id, text: judgeList[i].name};
                        arr.push(option);
                    }
                    $(".form-control.select2-multiple.judge").empty();
                    $('.form-control.select2-multiple.judge').select2({
                        language: "zh-CN",
                        width: "100%",
                        data: arr,
                        placeholder: "--请选择评委--",
                        allowClear: true,
                        closeOnSelect: false
                    });
                    $(".select2-selection.select2-selection--multiple").css("border-color", "rgb(231 236 235)");
                    if (isMajar) {
                        $.get("/admin/college/majorList", {collegeId: collegeId}, function (result) {
                            var majorList = result.data;
                            var html = "<option value=-1>--请选择专业--</option>";
                            for (var i = 0; i < majorList.length; i++) {
                                html += ' <option value=' + majorList[i].id + '>' + majorList[i].name + '</option>'
                            }
                            var selectProfessional = $("#professional");
                            $("#professional").empty();
                            selectProfessional.append(html);
                            $('#professional').selectpicker('refresh');
                        });
                    }
                }
        );
    }

    //根据参赛类型显示信息
    $("#add-competitionType").change(function () {
        var type = $(this).val();
        if (type == competition_status.PERSONAL) {
            //表示个人
            $("#add-personal").show();
            $(".input-group.m-b-10.add-team").hide();
            $("#add-enrollmentNumber").removeAttr("disabled");
            $("#add-teams").attr("disabled","disabled");
        } else if (type == competition_status.TEAM) {
            //表示团队
            $(".input-group.m-b-10.add-team").show();
            $("#add-personal").hide();
            $("#add-teams").removeAttr("disabled");
            $("#add-enrollmentNumber").attr("disabled","disabled");
        } else {
            $("#add-personal").hide();
            $(".input-group.m-b-10.add-team").hide();
        }
    });

    //选择竞赛类型
    $("#add-raceType").change(function () {
        var raceType = $(this).val();
        if (raceType == race_type.OPENNESS) {
            //开放性
            findJuegeList("-1", false);
            $("#add-college").hide();
            $("#add-professional").hide();
        } else {
            //专业性
            $("#college").selectpicker(
                    {
                        "width": 60
                    }
            )
            $("#add-college").show();
            $("#add-professional").show();
        }
    });


</script>
</body>
</html>