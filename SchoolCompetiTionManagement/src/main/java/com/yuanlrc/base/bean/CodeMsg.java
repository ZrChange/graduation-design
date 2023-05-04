package com.yuanlrc.base.bean;

import java.security.CodeSource;

/**
 * 错误码统一处理类，所有的错误码统一定义在这里
 * @author Administrator
 *
 */
public class CodeMsg {


	private int code;//错误码
	
	private String msg;//错误信息
	
	/**
	 * 构造函数私有化即单例模式
	 * @param code
	 * @param msg
	 */
	private CodeMsg(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//通用错误码定义
	//处理成功消息码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	//非法数据错误码
	public static CodeMsg DATA_ERROR = new CodeMsg(-1, "非法数据！");
	public static CodeMsg CPACHA_EMPTY = new CodeMsg(-2, "验证码不能为空！");
	public static CodeMsg VALIDATE_ENTITY_ERROR = new CodeMsg(-3, "");
	public static CodeMsg SESSION_EXPIRED = new CodeMsg(-4, "会话已失效，请刷新页面重试！");
	public static CodeMsg CPACHA_ERROR = new CodeMsg(-5, "验证码错误！");
	public static CodeMsg USER_SESSION_EXPIRED = new CodeMsg(-6, "还未登录或会话失效，请重新登录！");
	public static CodeMsg UPLOAD_PHOTO_SUFFIX_ERROR = new CodeMsg(-7, "图片格式不正确！");
	public static CodeMsg UPLOAD_PHOTO_ERROR = new CodeMsg(-8, "图片上传错误！");
	public static CodeMsg SAVE_ERROR = new CodeMsg(-11, "保存失败，请联系管理员！");
	public static CodeMsg ORDER_SN_ERROR = new CodeMsg(-12, "订单编号错误！");
	public static CodeMsg PHONE_ERROR = new CodeMsg(-13, "手机号错误！");
	public static CodeMsg ORDER_AUTH_ERROR = new CodeMsg(-14, "\u8ba2\u5355\u9a8c\u8bc1\u5931\u8d25\uff0c\u8ba2\u5355\u7f16\u53f7\u6216\u624b\u673a\u53f7\u8f93\u5165\u6709\u8bef\u6216\u8005\u53ef\u80fd\u4f60\u8d2d\u4e70\u7684\u662f\u76d7\u7248\uff0c\u8bf7\u8054\u7cfb\u3010\u733f\u6765\u5165\u6b64\u3011\u5ba2\u670d\uff01");
	
	//后台管理类错误码
	//用户管理类错误
	public static CodeMsg ADMIN_USERNAME_EMPTY = new CodeMsg(-2000, "账号不能为空！");
	public static CodeMsg ADMIN_PASSWORD_EMPTY = new CodeMsg(-2001, "密码不能为空！");
	public static CodeMsg ADMIN_NO_RIGHT = new CodeMsg(-2002, "您所属的角色没有该权限！");
	
	//登录类错误码
	public static CodeMsg ADMIN_USERNAME_NO_EXIST = new CodeMsg(-3000, "该账号不存在！");
	public static CodeMsg ADMIN_PASSWORD_ERROR = new CodeMsg(-3001, "密码错误！");
	public static CodeMsg ADMIN_USER_UNABLE = new CodeMsg(-3002, "该用户已被冻结，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_UNABLE = new CodeMsg(-3003, "该用户所属角色状态不可用，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_AUTHORITES_EMPTY = new CodeMsg(-3004, "该用户所属角色无可用权限，请联系管理员！");
	
	//后台菜单管理类错误码
	public static CodeMsg ADMIN_MENU_ADD_ERROR = new CodeMsg(-4000, "菜单添加失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_EDIT_ERROR = new CodeMsg(-4001, "菜单编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_ID_EMPTY = new CodeMsg(-4002, "菜单ID不能为空！");
	public static CodeMsg ADMIN_MENU_ID_ERROR = new CodeMsg(-4003, "菜单ID错误！");
	public static CodeMsg ADMIN_MENU_DELETE_ERROR = new CodeMsg(-4004, "改菜单下有子菜单，不允许删除！");
	//后台角色管理类错误码
	public static CodeMsg ADMIN_ROLE_ADD_ERROR = new CodeMsg(-5000, "角色添加失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_NO_EXIST = new CodeMsg(-5001, "该角色不存在！");
	public static CodeMsg ADMIN_ROLE_EDIT_ERROR = new CodeMsg(-5002, "角色编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_DELETE_ERROR = new CodeMsg(-5003, "该角色下存在用户信息，不可删除！");
	//后台用户管理类错误码
	public static CodeMsg ADMIN_USER_ROLE_EMPTY = new CodeMsg(-6000, "请选择用户所属角色！");
	public static CodeMsg ADMIN_USERNAME_EXIST = new CodeMsg(-6001, "该用户名已存在，请换一个试试！");
	public static CodeMsg ADMIN_USE_ADD_ERROR = new CodeMsg(-6002, "用户添加失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_NO_EXIST = new CodeMsg(-6003, "用户不存在！");
	public static CodeMsg ADMIN_USE_EDIT_ERROR = new CodeMsg(-6004, "用户编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_DELETE_ERROR = new CodeMsg(-6005, "该用户存在关联数据，不允许删除！");
	public static CodeMsg ADMIN_USE_MOBILE_NO_EXIST_ERROR = new CodeMsg(-6006, "该手机号已经存在，请换个手机号重新添加！");
	public static CodeMsg ADMIN_USE_MOBILE_ERROR = new CodeMsg(-6007, "请填写正确的手机号！");

	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_USER_UPDATE_PWD_ERROR = new CodeMsg(-7000, "旧密码错误！");
	public static CodeMsg ADMIN_USER_UPDATE_PWD_EMPTY = new CodeMsg(-7001, "新密码不能为空！");
	
	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_DATABASE_BACKUP_NO_EXIST = new CodeMsg(-8000, "备份记录不存在！");

	//学院管理类错误码
	public static CodeMsg COLLEGE_ADD_ERROR = new CodeMsg(-9000,"学院添加失败！");
	public static CodeMsg COLLEGE_NAME_EXIST = new CodeMsg(-9001,"该学院名称已存在，请换一个试试！");
	public static CodeMsg COLLEGE_DELETE_ERROR = new CodeMsg(-9002,"该学院存在关联数据，不允许删除！");
	public static CodeMsg COLLEGE_NO_EXIST = new CodeMsg(-9003,"学院不存在！");

	//评委管理类错误码
	public static CodeMsg JUDGE_COLLEGE_EMPTY = new CodeMsg(-10000,"请选择所属学院！");
	public static CodeMsg JUDGE_MOBILE_EXIST = new CodeMsg(-10001,"该手机号已存在，请换一个试试！");
	public static CodeMsg MOBILE_ERROR = new CodeMsg(-10002,"手机号格式错误，请重新输入！");
	public static CodeMsg JUDGE_ADD_ERROR = new CodeMsg(-10003,"评委添加失败！");
	public static CodeMsg JUDGE_DELETE_ERROR = new CodeMsg(-10004,"该评委存在关联数据，不允许删除！");
	public static CodeMsg JUDGE_NO_EXIST = new CodeMsg(-10005,"评委不存在！");

	//班级管理类错误码
	public static CodeMsg CLAZZ_GRADE_EMPTY = new CodeMsg(-11000,"请选择所属年级！");
	public static CodeMsg CLAZZ_PROFESSIONAL_EMPTY = new CodeMsg(-11001,"请选择所属专业！");
	public static CodeMsg CLAZZ_NO_EXIST = new CodeMsg(-11002,"班级不存在！");
	public static CodeMsg CLAZZ_NAME_EXIST = new CodeMsg(-11003,"该专业该年级已有该班级名称，请换一个试试！");
	public static CodeMsg CLAZZ_ADD_ERROR = new CodeMsg(-11004,"班级添加失败！");
	public static CodeMsg CLAZZ_DELETE_ERROR = new CodeMsg(-11005,"该班级存在关联数据，不允许删除！");

	//个人报名竞赛类错误码
	public static CodeMsg COMPETITION_IS_EMPTY = new CodeMsg(-12000,"该竞赛不存在！");
	public static CodeMsg APPLY_ERROR = new CodeMsg(-12001,"报名失败，请联系管理员！");
	public static CodeMsg COMPETITION_NOT_PERSONAL = new CodeMsg(-12002,"个人不能报名团队类竞赛！");
	public static CodeMsg IS_APPLY_ERROR = new CodeMsg(-12003,"您已经报名过或报名已通过！");
	public static CodeMsg ENROLL_TIME_ERROR = new CodeMsg(-12004,"当前不在报名时间段内");
	public static CodeMsg ENROLLMENT_NUBER_IS_FULL = new CodeMsg(-12005,"报名人数已满，无法报名！");
	public static CodeMsg STUDENT_PROFESSIONAL_ERROR = new CodeMsg(-12006,"专业不符合要求，无法报名！");


	/**
	 * @author zhong
	 */

	public static CodeMsg ADMIN_GRADE_DELETE_ERROR = new CodeMsg(-40000, "年级删除失败");
	public static CodeMsg ADMIN_GRADE_ADD_ERROR = new CodeMsg(-40001, "年级添加失败");
	public static CodeMsg ADMIN_GRADE_NAME_ERROR = new CodeMsg(-40002, "年级名称重复了");
	public static CodeMsg ADMIN_GRADE_EDIT_ERROR = new CodeMsg(-40003, "年级编辑失败");

	///专业
	public static CodeMsg ADMIN_PROFESSIONAL_DELETE_ERROR = new CodeMsg(-41000, "专业删除失败");
	public static CodeMsg ADMIN_PROFESSIONAL_NAME_ERROR = new CodeMsg(-41001, "专业名称重复");
	public static CodeMsg ADMIN_PROFESSIONAL_COLLEGE_NULL_ERROR = new CodeMsg(-41002, "请选择学院");
	public static CodeMsg ADMIN_PROFESSIONAL_ADD_ERROR = new CodeMsg(-41003,"专业添加失败");
	public static CodeMsg ADMIN_PROFESSIONAL_EDIT_ERROR = new CodeMsg(-41004, "专业编辑失败");

	//竞赛错误码
	public static CodeMsg ADMIN_COMPETITION_ENROLLTIME_ERROR = new CodeMsg(-60000, "报名开始时间不能大于报名结束时间");
	public static CodeMsg ADMIN_COMPETITION_PRELIMINARIESTIME_ERROR = new CodeMsg(-60001, "初赛开始时间不能大于初赛结束时间");
	public static CodeMsg ADMIN_COMPETITION_SECONDROUND_ERROR = new CodeMsg(-60002, "复赛开始时间不能大于复赛结束时间");
	public static CodeMsg ADMIN_COMPETITION_FINAL_ERROR = new CodeMsg(-60003, "决赛开始时间不能大于决赛结束时间");
	public static CodeMsg ADMIN_COMPETITION_SAVE_ERROR = new CodeMsg(-60004, "竞赛保存失败，请联系管理员");
	public static CodeMsg ADMIN_COMPETITION_NOTEMPTY_ERROR = new CodeMsg(-60005, "该竞赛不存在");
	public static CodeMsg ADMIN_COMPETITION_TAKE_PART_ERROR = new CodeMsg(-60006, "该竞赛已经有人报名不可修改");
	public static CodeMsg ADMIN_COMPETITION_DELETE_ERROR = new CodeMsg(-60007,"该竞赛已有人参加，不允许删除！");




	//登录类错误码
	public static CodeMsg PASSWORD_MIN_LENGTH_ERROR = new CodeMsg(-50000, "密码最少为4位");
	public static CodeMsg PASSWORD_MAX_LENGTH_ERROR = new CodeMsg(-50001, "密码最多为18位");
	public static CodeMsg USERNAME_MIN_LENGTH_ERROR = new CodeMsg(-50002, "账号最少为4位");
	public static CodeMsg USERNAME_MAX_LENGTH_ERROR = new CodeMsg(-50003, "账号最多为18位");

	//学生
	public static CodeMsg ADMIN_STUDENT_DELETE_ERROR = new CodeMsg(-42000,"删除学生失败");
	public static CodeMsg ADMIN_STUDENT_CLAZZ_NULL_ERROR = new CodeMsg(-42001,"请选择班级");
	public static CodeMsg ADMIN_STUDENT_PHONE_NULL_ERROR = new CodeMsg(-42002, "请输入手机号");
	public static CodeMsg ADMIN_STUDENT_PHONE_FORMAT_ERROR = new CodeMsg(-42003, "手机号格式错误");
	public static CodeMsg ADMIN_STUDENT_PHONE_REPETITION_ERROR = new CodeMsg(-42004,"手机号重复，已被注册过了");
	public static CodeMsg ADMIN_STUDENT_ADD_ERROR = new CodeMsg(-42005, "学生添加失败");
	public static CodeMsg ADMIN_STUDENT_AGE_VALUE_ERROR = new CodeMsg(-42006, "年龄必须0~200之间");
	public static CodeMsg ADMIN_STUDENT_EDIT_ERROR = new CodeMsg(-42007, "学生编辑失败");
	public static CodeMsg STUDENT_IMPORT_ADD_ERROR = new CodeMsg(-42008, "请选择Excel文件");
	public static CodeMsg STUDENT_IMPORT_EXCEL_ERROR = new CodeMsg(-42009, "Excel单元格不是文本类型");
	public static CodeMsg STUDENT_IMPORT_EXCEL_NULL_ERROR = new CodeMsg(-42010, "Excel单元格有空值");
	public static CodeMsg ADMIN_IMPORT_CLAZZ_NOT_FOUND_ERROR = new CodeMsg(-42011,"班级不存在");
	public static CodeMsg ADMIN_STUDENT_AGE_VALUE_ERROR_2 = new CodeMsg(-42012, "年龄转换错误");
	public static CodeMsg ADMIN_STUDENT_NUMBER_EXSITER_ERROR = new CodeMsg(-42013,"学号已存在");
	public static CodeMsg STUDENT_EXCEL_IMPORT_ERROR = new CodeMsg(-42014, "请检查Excel学号或手机号是否重复");

	//评委审批 评分 晋级
	public static CodeMsg SCORE_IS_NOT_EMPTY = new CodeMsg(-51000, "该阶段已评分，不能再进行评分！");
	public static CodeMsg APPROVE_ERROR = new CodeMsg(-51001, "审批失败，请联系管理员！");
	public static CodeMsg APPLY_IS_APPROVE = new CodeMsg(-51002, "已经审批过，不能再进行审批！");
	public static CodeMsg NUMBER_IS_ERROR = new CodeMsg(-51003, "人数不满足，不能进行审批！");
	public static CodeMsg SCORE_ERROR = new CodeMsg(-51004, "评分失败，请联系管理员！");
	public static CodeMsg COMPETITION_STATUS_ERROR = new CodeMsg(-51005, "竞赛还未开始，无法进行评分操作！");
	public static CodeMsg APPLY_TIME_ERROR = new CodeMsg(-51006, "当前已超过审批报名时间，无法再审批报名！");
	public static CodeMsg COMPETITION_STATUS_FINLS_ERROR = new CodeMsg(-51007, "当前已是决赛，无法进行晋级操作！");
	public static CodeMsg COMPETITION_TIME_ERROR = new CodeMsg(-51008, "竞赛还未开始，无法进行晋级操作！");
	public static CodeMsg PROMOTION_SCORE_ERROR = new CodeMsg(-51009, "还未评分，无法进行晋级操作！");
	public static CodeMsg PROMOTION_ERROR = new CodeMsg(-51010, "晋级操作失败，请联系管理员！");
	public static CodeMsg TIME_ERROR = new CodeMsg(-51011, "时间错误，无法进行该操作！");
	public static CodeMsg HAS_PROMOTION = new CodeMsg(-51012, "已晋级无法再次进行晋级！");
	public static CodeMsg SCORE_VALUE_ERROR = new CodeMsg(-51013, "评分为0-100，请合理评分！");




	//报名
	public static CodeMsg ADMIN_STUDENT_SHOW_APPLY_PASS_ERROR = new CodeMsg(-42015,"报名未通过");
	public static CodeMsg ADMIN_STUDENT_MAKE_CAPTAIN_ERROR = new CodeMsg(-42016, "队长转让失败");
	public static CodeMsg ADMIN_STUDENT_MAKE_CAPTAIN_ERROR_2 = new CodeMsg(-42017, "已经报名，无法转让给队长");
	public static CodeMsg ADMIN_STUDENT_MAKE_CAPTAIN_ERROR_3 = new CodeMsg(-42018, "你已经是队长了");
	public static CodeMsg ADMIN_STUDENT_QUIT_ERROR = new CodeMsg(-42019, "你是队长无法退出");
	public static CodeMsg ADMIN_STUDENT_QUIT_ERROR_2 = new CodeMsg(-42020, "退出失败");
	public static CodeMsg ADMIN_STUDENT_QUIT_ERROR_3 = new CodeMsg(-42021, "比赛报名已经通过了，退出失败");


	//学生查看竞赛成绩
	public static CodeMsg APPROVE_NOT_PASSED = new CodeMsg(-53000,"报名未通过，不能查看成绩");

	//解散
	public static CodeMsg ADMIN_STUDENT_DISSOLVE_ERROR = new CodeMsg(-42030, "解散团队失败");
	public static CodeMsg ADMIN_STUDENT_DISSOLVE_NOT_CAPTAIN_ERROR = new CodeMsg(-42031, "你不是队长解散失败");
	public static CodeMsg ADMIN_STUDENT_DISSOLVE_ERROR_2 = new CodeMsg(-42032, "已报名无法解散");
	public static CodeMsg ADMIN_STUDENT_JOIN_TEAM_ERROR = new CodeMsg(-42033, "你已经有团队了");
	public static CodeMsg ADMIN_STUDENT_JOIN_TEAM_ADD_ERROR = new CodeMsg(-42034, "加入团队失败");
	public static CodeMsg ADMIN_COMPETITION_NOT_FOUNT_ERROR = new CodeMsg(-42035, "竞赛不存在");
	public static CodeMsg ADMIN_COMPETITION_NOT_TEAM_ERROR = new CodeMsg(-42036, "不是团队赛");
	public static CodeMsg ADMIN_CREATE_TEAM_ERROR = new CodeMsg(-42037, "团队创建失败");
	public static CodeMsg ADMIN_COMPETITION_FULL_ERROR = new CodeMsg(-42038, "团队满员了");


	public static CodeMsg ADMIN_IS_CAPTAIN_ERROR = new CodeMsg(-43000, "你不是队长");
	public static CodeMsg ADMIN_CHECK_CENTER_ERROR = new CodeMsg(-43001, "团队已经报名了，无法加入新成员");
	public static CodeMsg ADMIN_APPLY_DELETE_ERROR = new CodeMsg(-43002, "申请记录删除失败");
	public static CodeMsg ADMIN_APPLY_ADD_ERROR = new CodeMsg(-43003, "已经报名了，无法在同意了");
	public static CodeMsg ADMIN_TEAM_FULL_ERROR = new CodeMsg(-43004,"团队已经满员了");
	public static CodeMsg ADMIN_NOT_TIME_RANGE_ERROR = new CodeMsg(-43005,"请在报名时间内同意");
	public static CodeMsg ADMIN_STUDENT_JOIN_TEAM_ERROR_2 = new CodeMsg(-43006,"他已经加入过团队了");
	public static CodeMsg ADMIN_APPLY_ADD_ERROR_2 = new CodeMsg(-43007, "同意失败");
	public static CodeMsg ADMIN_APPLY_ADD_PASS_ERROR = new CodeMsg(-43008, "已经同意过了");

	//上传附件
	public static CodeMsg ADMIN_UPLOAD_ACCESSORY_ERROR = new CodeMsg(-44000, "请选择上传附件");
	public static CodeMsg ADMIN_ACCESSORY_ERROR = new CodeMsg(-44001, "附件上传失败");
	public static CodeMsg ADMIN_APPLY_STATUS_ERROR = new CodeMsg(-44002,"你的报名还未通过哦");
	public static CodeMsg ADMIN_ACCESSORY_START_TIME_ERROR = new CodeMsg(-44003,"比赛还未开始哦");
	public static CodeMsg ADMIN_SECOND_ROUND_NOT_PASS_ERROR = new CodeMsg(-44004, "复赛未通过，不能参加决赛");
	public static CodeMsg ADMIN_PRELIMINARIES_NOT_PASS_ERROR = new CodeMsg(-44005, "初赛未通过，不能参加复赛");
	public static CodeMsg ADMIN_UPLOAD_ACCESSORY_NOT_FOUND_ERROR = new CodeMsg(-44006, "附件上传失败,请在规定时间段内上传");
	public static CodeMsg ADMIN_PRELIMINARIES_TIME_RANGE_ERROR = new CodeMsg(-44006, "请在初赛时间段内上传附件");
	public static CodeMsg ADMIN_SECOND_ROUND_TIME_RANGE_ERROR = new CodeMsg(-44007, "请在复赛时间段内上传附件");
	public static CodeMsg ADMIN_FINAL_TIME_RANGE_ERROR = new CodeMsg(-44008, "请在决赛时间段内上传附件");
	public static CodeMsg ADMIN_FINAL_ACCESSORY_ERROR = new CodeMsg(-44009, "决赛已经上传过了哦");

	public static CodeMsg ADMIN_CHECK_APPLY_ERROR = new CodeMsg(-45000, "你不是队长无法报名哦");
	public static CodeMsg ADMIN_CHECK_IS_PASS_AND_CHECK_ERROR = new CodeMsg(-45001, "竞赛已经在审批阶段了或通过阶段了");
	public static CodeMsg ADMIN_TEAM_MEMBERS_SIZE_ERROR = new CodeMsg(-45002, "团队人数还未达标哦，快去看看赛项介绍吧");
	public static CodeMsg ADMIN_CHECK_APPLY_ERROR_2 = new CodeMsg(-45003, "报名失败");
	public static CodeMsg ADMIN_CHECK_APPLY_ERROR_3 = new CodeMsg(-45004, "你不是队长或者这个队长不是你");
	public static CodeMsg ADMIN_KICK_OUT_ERROR = new CodeMsg(-45005, "不能踢出自己哦，请对队员负责");
	public static CodeMsg ADMIN_KICK_OUT_ERROR_2 = new CodeMsg(-45006, "已经报名了，不能踢人了");
	public static CodeMsg ADMIN_KICK_OUT_NOT_FOUNT_ERROR = new CodeMsg(-45007, "踢人失败");


	public static CodeMsg ADMIN_STUDENT_IMPORT_NUMBER_ERROR = new CodeMsg(-45008, "学号转换错误只能是数字类型");
	public static CodeMsg ADMIN_DOWNLOAD_ACCESSORY_ERROR = new CodeMsg(-45009, "你比赛还未上传过附件");
	public static CodeMsg ADMIN_ACCESSORY_UPLOAD_ERROR = new CodeMsg(-45010, "你已经在这个阶段上传过了");
}
