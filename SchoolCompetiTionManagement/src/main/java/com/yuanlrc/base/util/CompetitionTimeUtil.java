package com.yuanlrc.base.util;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.CompetitionProcess;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Competition;

import java.util.Date;

/**
 * 判断进入页面
 */
public class CompetitionTimeUtil {

    public static boolean competitionStatusTime(Date date, Competition competition,int comeType){

        if(comeType == CompetitionProcess.PRELIMINARIES.getCode()){
            if(date.after(competition.getSecondRoundStartTime()) || date.before(competition.getPreliminariesStartTime())){
                return false;
            }
        }else if(comeType == CompetitionProcess.SECONDROUND.getCode()){
            if(date.after(competition.getFinalsStartTime()) || date.before(competition.getSecondRoundStartTime())){
                return false;
            }
        }else{
            if(date.before(competition.getFinalsStartTime())){
                return false;
            }
        }

        return true;
    }



}
