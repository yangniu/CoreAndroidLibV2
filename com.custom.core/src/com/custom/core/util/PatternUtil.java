/**
 * PatternUtil.java
 * 功能：正则表达式处理 
 * author      date          time      
 * ─────────────────────────────────────────────
 * niufei     2012-8-10       上午10:42:15
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.custom.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class PatternUtil {

	private static String ChineseMobilePattern = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";

	/**
	 * 判断邮箱
	 * @param str
	 * @return
	 */
	public  static boolean checkEmail(String email) {
		boolean flag=false;
		String regex =null;
		if(email!=null){
			regex ="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*.\\w+([-.]\\w+)*";
			flag= checkStr(regex,email);
		}
		return flag;
	}
	/** 
	 * 功能：身份证的有效验证 
	 *  
	 * @param IDStr 
	 *            身份证号 
	 * @return 有效：返回"" 无效：返回String信息 
	 * @throws ParseException 
	 */  
	public static boolean IDCardValidate(String IDStr) throws ParseException {  
		String errorInfo = "";// 记录错误信息  
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
				"3", "2" };  
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
				"9", "10", "5", "8", "4", "2" };  
		String Ai = "";  
		// ================ 号码的长度 15位或18位 ================  
		if (IDStr.length() != 15 && IDStr.length() != 18) {  
			errorInfo = "身份证号码长度应该为15位或18位。";  
			return false;  
		}  
		// =======================(end)========================  

		// ================ 数字 除最后以为都为数字 ================  
		if (IDStr.length() == 18) {  
			Ai = IDStr.substring(0, 17);  
		} else if (IDStr.length() == 15) {  
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);  
		}  
		if (isNumeric(Ai) == false) {  
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";  
			return false;  
		}  
		// =======================(end)========================  

		// ================ 出生年月是否有效 ================  
		String strYear = Ai.substring(6, 10);// 年份  
		String strMonth = Ai.substring(10, 12);// 月份  
		String strDay = Ai.substring(12, 14);// 月份  
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {  
			errorInfo = "身份证生日无效。";  
			return false;  
		}  
		GregorianCalendar gc = new GregorianCalendar();  
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
		try {  
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150  
					|| (gc.getTime().getTime() - s.parse(  
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {  
				errorInfo = "身份证生日不在有效范围。";  
				return false;  
			}  
		} catch (NumberFormatException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		} catch (java.text.ParseException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}  
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {  
			errorInfo = "身份证月份无效";  
			return false;  
		}  
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {  
			errorInfo = "身份证日期无效";  
			return false;  
		}  
		// =====================(end)=====================  

		// ================ 地区码时候有效 ================  
		Hashtable h = GetAreaCode();  
		if (h.get(Ai.substring(0, 2)) == null) {  
			errorInfo = "身份证地区编码错误。";  
			return false;  
		}  
		// ==============================================  

		// ================ 判断最后一位的值 ================  
		int TotalmulAiWi = 0;  
		for (int i = 0; i < 17; i++) {  
			TotalmulAiWi = TotalmulAiWi  
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))  
					* Integer.parseInt(Wi[i]);  
		}  
		int modValue = TotalmulAiWi % 11;  
		String strVerifyCode = ValCodeArr[modValue];  
		Ai = Ai + strVerifyCode;  

		if (IDStr.length() == 18) {  
			if (Ai.equals(IDStr) == false) {  
				errorInfo = "身份证无效，不是合法的身份证号码";  
				return false;  
			}  
		} else {  
			return true;  
		}  
		// =====================(end)=====================  
		return true;  
	}
	/** 
     * 功能：判断字符串是否为数字 
     *  
     * @param str 
     * @return 
     */  
    private static boolean isNumeric(String str) {  
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(str);  
        if (isNum.matches()) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
  
	/** 
	 * 功能：设置地区编码 
	 *  
	 * @return Hashtable 对象 
	 */  
	@SuppressWarnings({ "rawtypes", "unchecked" })  
	private static Hashtable GetAreaCode() {  
		Hashtable hashtable = new Hashtable();  
		hashtable.put("11", "北京");  
		hashtable.put("12", "天津");  
		hashtable.put("13", "河北");  
		hashtable.put("14", "山西");  
		hashtable.put("15", "内蒙古");  
		hashtable.put("21", "辽宁");  
		hashtable.put("22", "吉林");  
		hashtable.put("23", "黑龙江");  
		hashtable.put("31", "上海");  
		hashtable.put("32", "江苏");  
		hashtable.put("33", "浙江");  
		hashtable.put("34", "安徽");  
		hashtable.put("35", "福建");  
		hashtable.put("36", "江西");  
		hashtable.put("37", "山东");  
		hashtable.put("41", "河南");  
		hashtable.put("42", "湖北");  
		hashtable.put("43", "湖南");  
		hashtable.put("44", "广东");  
		hashtable.put("45", "广西");  
		hashtable.put("46", "海南");  
		hashtable.put("50", "重庆");  
		hashtable.put("51", "四川");  
		hashtable.put("52", "贵州");  
		hashtable.put("53", "云南");  
		hashtable.put("54", "西藏");  
		hashtable.put("61", "陕西");  
		hashtable.put("62", "甘肃");  
		hashtable.put("63", "青海");  
		hashtable.put("64", "宁夏");  
		hashtable.put("65", "新疆");  
		hashtable.put("71", "台湾");  
		hashtable.put("81", "香港");  
		hashtable.put("82", "澳门");  
		hashtable.put("91", "国外");  
		return hashtable;  
	}  
	/** 
	 * 验证日期字符串是否是YYYY-MM-DD格式 
	 *  
	 * @param str 
	 * @return 
	 */  
	private static boolean isDataFormat(String str) {  
		boolean flag = false;  
		// String  
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";  
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";  
		Pattern pattern1 = Pattern.compile(regxStr);  
		Matcher isNo = pattern1.matcher(str);  
		if (isNo.matches()) {  
			flag = true;  
		}  
		return flag;  
	}  

	/**
	 * 判断用户名
	 * @param str
	 * @return
	 */
	//	 public   boolean checkUserName(String username) {
	//		 boolean flag=false;
	//		 String regex =null;
	//		 int len=username.length();
	//		 if(len<6||len>18){
	//			 flag=false;
	//		 }else{
	//		  regex = "([a-z]|[A-Z]|[0-9])+";//只判定数字或者字母
	//		  flag= checkStr(regex,username);
	//		 }
	//		 return flag;
	//	}
	/**
	 * 判断手机号码 可为null 并且满足号码格式
	 * @param str
	 * @return
	 */
	public static  boolean checkTelPhone(String mobile) {
		boolean flag=false;
		if(mobile!=null&&!"".equals(mobile)){
			flag= checkStr(ChineseMobilePattern,mobile);//判定电话号码
		}else{
			flag=true;
		}
		return flag;
	}
	/** 
	 * 电话号码验证 
	 *  
	 * @param  str 
	 * @return 验证通过返回true 
	 */  
	public static boolean isPhone(String str) {   
		boolean b = false;
		if(!TextUtils.isEmpty(str)) {
			Pattern p1 = null,p2 = null;  
			Matcher m = null;     
			p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
			p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
			if(str.length() >9)  
			{   m = p1.matcher(str);  
			b = m.matches();    
			}else{  
				m = p2.matcher(str);  
				b = m.matches();   
			}	
		}
		return b;  
	}  
	/**
	 *判断手机号码非空并且限制为11位
	 * @param str
	 * @return
	 */
	public  static boolean checkTelPhone2(String mobile) {
		boolean flag=false;
		if(mobile!=null&&!"".equals(mobile)){
			flag= checkStr(ChineseMobilePattern,mobile);//判定电话号码
		}
		return flag;
	}
	/**
	 * 判断密码    字符串 字母或者数字
	 * @param str
	 * @return
	 */
	public static boolean checkPassword(String password) {
		boolean flag=false;
		String regex =null;
		int len=password.length();
		if(len<6||len>18){
			flag=false;
		}else{
			regex = "([a-z]|[A-Z]|[0-9]|[_*])+";//判定数字或者字母或者下划线
			flag= checkStr(regex,password);
		}
		return flag;
	}
	/**
	 * 判断执业医师证书编码 18位数字 可为null
	 * @param str
	 * @return
	 */
	public static  boolean checkDoctorLicenceStr(String doctorLicence) {
		boolean flag=false;
		String regex =null;
		if(doctorLicence!=null&&!"".equals(doctorLicence)){
			regex = "^\\d{15}$";//判定执业医师证书编码
			flag= checkStr(regex,doctorLicence);
		}else{
			flag=true;
		}
		return flag;
	}
	/**
	 * 正则检查字符串
	 * @param regex
	 * @param input
	 * @return
	 */
	private static boolean checkStr(String regex,String input){

		boolean flag=false;
		if(regex!=null){
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(input);
			flag=m.matches();
		}
		return flag;
	}
	/**
	 * 判定数字或者字母或者中文
	 * @param str
	 * @return
	 */
	public static  boolean checkAll(String str) {
		boolean flag=false;
		String regex =null;
		regex = "([a-z]|[A-Z]|[0-9]|[\u4e00-\u9fa5])+";//判定数字或者字母或者中文
		flag= checkStr(regex,str);
		return flag;
	}
	/**
	 * 判断用户名
	 * @param str
	 * @return
	 */
	public static  boolean checkContainChinese(String str) {
		boolean flag=false;
		String regex =null;
		regex = "([\u4e00-\u9fa5])+";//判定中文
		flag= checkStr(regex,str);
		return flag;
	}
	/**
	 * 判断真实姓名 姓名为汉字，并且至少两位
	 * @param str
	 * @return
	 */
	public static  boolean checkTrueName(String str) {
		boolean flag=false;
		String regex =null;
		regex = "([\u4e00-\u9fa5])+";//判定中文
		flag= checkStr(regex,str);
		if(flag){
			return  str.length()>1?true:false;
		}else
			return flag;
	}

	/**
	 * 过滤标点符号
	 * @param str
	 * @return
	 */
	public static String  filterPunctuation(String str,String replaceStr){
		if(str!=null)
			str=str.replaceAll("\\pP|\\pS|\\s",replaceStr);
		return str;
	}
	/**
	 * 过滤单个重复字符
	 * @param str
	 * @return
	 */
	public static String filterRepitChar(String str){
		if(str!=null){
			str=str.replaceAll("(?s)(.)(?=.*\\1)", "");
		}
		return str;

	}

	//	//查找以Java开头,任意结尾的字符串 
	//	  Pattern pattern = Pattern.compile("^Java.*"); 
	//	  Matcher matcher = pattern.matcher("Java不是人"); 
	//	  boolean b= matcher.matches(); 
	//	  //当条件满足时，将返回true，否则返回false 
	//	  System.out.println(b); 
	//
	//
	//	  ◆以多条件分割字符串时 
	//	  Pattern pattern = Pattern.compile("[, |]+"); 
	//	  String[] strs = pattern.split("Java Hello World Java,Hello,,World|Sun"); 
	//	  for (int i=0;i<strs.length;i++) { 
	//	      System.out.println(strs[i]); 
	//	  } 
	//
	//	  ◆文字替换（首次出现字符） 
	//	  Pattern pattern = Pattern.compile("正则表达式"); 
	//	  Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World"); 
	//	  //替换第一个符合正则的数据 
	//	  System.out.println(matcher.replaceFirst("Java")); 
	//
	//	  ◆文字替换（全部） 
	//	  Pattern pattern = Pattern.compile("正则表达式"); 
	//	  Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World"); 
	//	  //替换第一个符合正则的数据 
	//	  System.out.println(matcher.replaceAll("Java")); 
	//
	//
	//	  ◆文字替换（置换字符） 
	//	  Pattern pattern = Pattern.compile("正则表达式"); 
	//	  Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World "); 
	//	  StringBuffer sbr = new StringBuffer(); 
	//	  while (matcher.find()) { 
	//	      matcher.appendReplacement(sbr, "Java"); 
	//	  } 
	//	  matcher.appendTail(sbr); 
	//	  System.out.println(sbr.toString()); 
	//
	//	  ◆验证是否为邮箱地址 
	//
	//	  String str="ceponline@yahoo.com.cn"; 
	//	  Pattern pattern = Pattern.compile("[//w//.//-]+@([//w//-]+//.)+[//w//-]+",Pattern.CASE_INSENSITIVE); 
	//	  Matcher matcher = pattern.matcher(str); 
	//	  System.out.println(matcher.matches()); 
	//
	//	  ◆去除html标记 
	//	  Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL); 
	//	  Matcher matcher = pattern.matcher("<a href=/"index.html/">主页</a>"); 
	//	  String string = matcher.replaceAll(""); 
	//	  System.out.println(string); 
	//
	//	  ◆查找html中对应条件字符串 
	//	  Pattern pattern = Pattern.compile("href=/"(.+?)/""); 
	//	  Matcher matcher = pattern.matcher("<a href=/"index.html/">主页</a>"); 
	//	  if(matcher.find()) 
	//	  System.out.println(matcher.group(1)); 
	//	  } 
	//
	//	  /**
	//	   * 截取http://地址 
	//	   * 
	//	   */
	//	  //截取url 
	//	  Pattern pattern = Pattern.compile("(http://|https://){1}[//w//.//-/:]+"); 
	//	  Matcher matcher = pattern.matcher("dsdsds<http://dsds//gfgffdfd>fdf"); 
	//	  StringBuffer buffer = new StringBuffer(); 
	//	  while(matcher.find()){              
	//	      buffer.append(matcher.group());        
	//	      buffer.append("/r/n");              
	//	  System.out.println(buffer.toString()); 
	//	  } 
	//	          
	//	/**
	//	 * 替换指定{}中文字 
	//	 */
	//	  String str = "Java目前的发展史是由{0}年-{1}年"; 
	//	  String[][] object={new String[]{"//{0//}","1995"},new String[]{"//{1//}","2007"}}; 
	//	  System.out.println(replace(str,object)); 
	//
	//	  public static String replace(final String sourceString,Object[] object) { 
	//	              String temp=sourceString;    
	//	              for(int i=0;i<object.length;i++){ 
	//	                        String[] result=(String[])object[i]; 
	//	                 Pattern    pattern = Pattern.compile(result[0]); 
	//	                 Matcher matcher = pattern.matcher(temp); 
	//	                 temp=matcher.replaceAll(result[1]); 
	//	              } 
	//	              return temp; 
	//	  } 

}
