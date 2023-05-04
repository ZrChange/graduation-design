package com.yuanlrc.base.util;

import com.alibaba.fastjson.JSONObject;
import com.yuanlrc.base.bean.CodeMsg;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目通用工具类
 * @author Administrator
 *
 */
public class StringUtil {
	
	
	/**
	 * 返回指定格式的日期字符串
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String getFormatterDate(Date date,String formatter){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}
	
	/**
	 * 判断请求是否是ajax
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(header))return true;
		return false;
	}
	
	/**
	 * 从流读取字符串
	 * @param inputStream
	 * @return
	 */
	public static String getStringFromInputStream(InputStream inputStream){
		String string = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
			String buf = null;
			try {
				while((buf = bufferedReader.readLine()) != null){
					string += buf;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return string;
	}

	/**
	 * 验证是否是手机号
	 * @param
	 * @return
	 */
	public static boolean  isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String s2="^1[0-9]{10}$";// 验证手机号
		if(StringUtils.isNotBlank(str)){
			p = Pattern.compile(s2);
			m = p.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{
		if(str == null || str.trim().length() == 0)
			return true;

		return false;
	}

	/**
	 * 判断是否是手机号
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone)
	{
		String chkphone = "^1[3|4|5|7|8]\\d{9}$";
		Pattern r = Pattern.compile(chkphone);
		Matcher m = r.matcher(phone);
		if (m.matches()){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
	{
		//邮箱验证
		String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		Pattern emailReg = Pattern.compile(pattern);
		Matcher m = emailReg.matcher(email);
		if (m.matches()) {
			return true;
		}

		return false;
	}

	/**
	 * 身份证验证
	 * @param card
	 * @return
	 */
	public static boolean isCard(String card)
	{
		String checkIdCard = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
		Pattern c = Pattern.compile(checkIdCard);
		Matcher m2 = c.matcher(card);
		if (m2.matches()){
			return true;
		}
		return false;
	}
	/**
	 * 获取指定格式时间再指定分钟后的时间字符串
	 * @param date
	 * @param formatter
	 * @param minites
	 * @return
	 */
	public static String getFormatterDate(String date,String formatter,int minites){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		String ret = null;
		try {
			Date parse = sdf.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			calendar.add(Calendar.MINUTE, minites);
			ret = sdf.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 获取指定分钟前的时间
	 * @param date
	 * @param
	 * @return
	 */
	public static Date getBeforeDate(Date date,int beforeMinites){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -beforeMinites);
		return calendar.getTime();
	}

	/**
	 * 获取制定天数前的日期
	 * @param date
	 * @param beforeDays
	 * @return
	 */
	public static Date getBeforeDaysDate(Date date,int beforeDays){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -beforeDays);
		return calendar.getTime();
	}

	/**
	 * 生成唯一字符串
	 * @return
	 */
	public static String generateSn(){
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}

	public static String getMac(){
		String mac = "";
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			byte[] hardwareAddress = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<hardwareAddress.length; i++) {
				//字节转换为整数
				int temp = hardwareAddress[i]&0xff;
				String str = Integer.toHexString(temp);
				//System.out.println("每8位:"+str);
				if(str.length()==1) {
					sb.append("0"+str);
				}else {
					sb.append(str);
				}
			}
			mac = sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mac.toUpperCase();
	}

	public static boolean authOrder(String orderSn,String phone){
		Map<String, String> headerParaMap = new HashMap<String, String>();
		String mac = getMac();
		String paramToken = DESUtil.encrypt(orderSn, mac+"#"+orderSn+"#"+phone);
		headerParaMap.put("paramToken", paramToken);
		String timeToken = DESUtil.encrypt("muyi_ylrc", System.currentTimeMillis()+"");
		headerParaMap.put("timeToken", timeToken);
		String sendPost = HttpUtil.sendPost("http://120.25.120.129:8081/order_auth/verify",headerParaMap,"orderSn="+orderSn+"&phone="+phone+"&mac="+mac);
		JSONObject parseObject = JSONObject.parseObject(sendPost);
		if(parseObject.getIntValue("code") != CodeMsg.SUCCESS.getCode()){
			return false;
		}
		return true;
	}

	public static String readFileToString(File file){
		String string = "";
		if(file != null){
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = br.readLine()) != null) {
					string += line;
				}
				br.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return string;
	}

	public static void handleFile(File file){
		if(file.isDirectory()){
			File[] listFiles = file.listFiles();
			for(File f : listFiles){
				handleFile(f);
			}
		}else{
			System.out.println(file.getName());
			file.delete();
		}
	}
}
