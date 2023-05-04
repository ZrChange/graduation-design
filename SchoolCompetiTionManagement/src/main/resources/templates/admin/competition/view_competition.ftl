
<table class="table table-bordered">
    <thead>
    <tr class="competition-tr">
        <th>标题</th>
        <td>${competition.title}</td>
    </tr>
    <tr class="competition-tr">
        <th>竞赛类型</th>
        <td>  <#if competition.raceType==1>
            <font style="color: #0c5460">开放型</font>
        <#else>
            <font style="color: #0FB25F;">专业型</font>
        </#if>
        </td>
    </tr>
    <tr class="competition-tr">
        <th>参赛类型</th>
        <td>
            <#if competition.competitionType==1>
                <font style="color: #0c5460">个人</font>
            <#else>
                 <font style="color: #0FB25F;">团队</font>
            </#if>
        </td>
    </tr>
    <#if competition.raceType==2>
         <tr class="competition-tr">
             <th>所属学院</th>
             <td>
                 ${competition.college.name}
             </td>
         </tr>
      <tr class="competition-tr">
          <th>所属专业</th>
          <td>
              ${competition.professional.name}
          </td>
      </tr>
    </#if>
    <tr class="competition-tr">
        <th>竞赛年级</th>
        <td>
                <#list competition.grades as grades>
                    <#if grades_index==0>
                        ${grades.name}
                    <#else>
                        ${grades.name+""}
                    </#if>
                </#list>
        </td>
    </tr>
    <tr class="competition-tr">
        <th>评分评委</th>
        <td>
            <#assign judeges="" />
                <#list competition.judges as judges>
                    <#if judges_index==0>
                        ${judeges+judges.name}
                    <#else>
                        ${judeges+""+judges.name}
                    </#if>
                </#list>
        ${judeges}
        </td>
    </tr>
    <tr class="competition-tr">
        <th>报名开始时间-报名结束时间</th>
        <td>
        ${competition.enrollStartTime}-${competition.enrollEndTime}
        </td>
    </tr>

    <tr class="competition-tr">
        <th>初赛开始时间-初赛结束时间</th>
        <td>
        ${competition.preliminariesStartTime}-${competition.preliminariesEndTime}
        </td>
    </tr>
    <tr class="competition-tr">
        <th>复赛开始时间-复赛结束时间</th>
        <td>
        ${competition.secondRoundStartTime}-${competition.secondRoundEndTime}
        </td>
    </tr>
    <tr class="competition-tr">
        <th>决赛开始时间-决赛结束时间</th>
        <td>
        ${competition.finalsStartTime}-${competition.finalsEndTime}
        </td>
    </tr>
    <tr class="competition-tr">
        <th>竞赛状态</th>
        <td>
            <#list competitionProcess as process>
                <#if process.code==competition.competitionStatus>
                    ${process.info}
                </#if>
            </#list>
        </td>
    </tr>
    <tr class="competition-tr">
        <th>可报名人数/团队</th>
        <td>
        ${competition.enrollmentNumber}
        </td>
    </tr>
    <#if competition.competitionType==2>
         <tr class="competition-tr">
             <th>团队人数</th>
             <td>
                 ${competition.teamSize}
             </td>
         </tr>
    </#if>
    <tr class="competition-tr">
        <th>已报名人数/团队</th>
        <td>
        ${competition.signedUp}
        </td>
    </tr>
    <tr class="competition-tr">
        <th>竞赛开放状态</th>
        <td>
        <#if competition.status==1>
            过期
        <#else>
            未过期
        </#if>
        </td>
    </tr>

    </thead>
</table>
<div>
    <span>竞赛详情</span>
    <div class="details">
        ${competition.detailedIntroduction}
    </div>
</div>
<#--
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script src="/admin/js/jquery-tags-input/jquery.tagsinput.min.js"></script>-->
