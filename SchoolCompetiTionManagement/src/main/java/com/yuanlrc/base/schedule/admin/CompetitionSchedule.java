package com.yuanlrc.base.schedule.admin;

import com.yuanlrc.base.bean.CompetitionProcess;
import com.yuanlrc.base.bean.CompetitionStatus;
import com.yuanlrc.base.entity.admin.Competition;
import com.yuanlrc.base.service.admin.CompetitionService;
import com.yuanlrc.base.service.admin.DatabaseBakService;
import com.yuanlrc.base.util.DateUtil;
import com.yuanlrc.base.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 竞赛流程状态定时器
 * @author Administrator
 *
 */
@Configuration
@EnableScheduling
public class CompetitionSchedule {

	@Autowired
	private CompetitionService competitionService;
	
	private Logger log = LoggerFactory.getLogger(CompetitionSchedule.class);
	

	//@Scheduled(initialDelay=10000,fixedRate=5000)

	//@Scheduled(cron="0 0 1 * * ?")
	@Scheduled(cron="0 */1 * * * ?")//一分钟执行一次
	public void competitionStatus(){
		log.info("开始执行竞赛流程状态定时器！");
		List<Competition> competitionList = competitionService.findAll();
		String format = DateUtil.time_sdf.format(new Date());
		try {
			Date currentTime = DateUtil.time_sdf.parse(format);
			for(Competition competition:competitionList){
				Boolean isChange=false;
				if(competition.getPreliminariesStartTime().compareTo(currentTime)==0){
				//表示到了初赛的开始时间
					isChange=true;
					competition.setCompetitionStatus(CompetitionProcess.PRELIMINARIES.getCode());
				}
				if(competition.getSecondRoundStartTime().compareTo(currentTime)==0){
					//表示到了复赛的开始时间
					isChange=true;
					competition.setCompetitionStatus(CompetitionProcess.SECONDROUND.getCode());
				}
				if(competition.getFinalsStartTime().compareTo(currentTime)==0){
					//表示到了决赛的开始时间
					isChange=true;
					competition.setCompetitionStatus(CompetitionProcess.FINALS.getCode());
				}
				if(competition.getFinalsEndTime().compareTo(currentTime)==0){
					//表示到了决赛的结束时间  说明比赛已经结束 竞赛设置为过期
					isChange=true;
					competition.setCompetitionStatus(CompetitionProcess.END.getCode());
					competition.setStatus(CompetitionStatus.OVERDUE.getCode());
				}
				if(isChange){
					competitionService.save(competition);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
