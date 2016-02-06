package com.custom.core.util;

import java.math.BigDecimal;


import android.text.TextUtils;


/**
 * @Company SINA
 *
 * @Copyright 2012-2013
 *
 * @author LIU ZHONG LEI
 *
 * @date 2012-9-27 下午2:31:23
 *
 * @Version 1.0
 */
public class NumberFormatUtil {
	
	public static float format(float d,int digits){
		try{
			BigDecimal b = new BigDecimal(d);		
			b = b.setScale(digits, BigDecimal.ROUND_HALF_UP);
			return b.floatValue();
		}
		catch(Exception e){
			
		}
		return d;
	}
	
	private static String formatFloat(float d,int digits,boolean isAddPercentSign,boolean isAddPlus,String defaultRet,String unit,boolean isZeroReturnDefault) {
		
		if(isZeroReturnDefault && FloatUtils.isZero(d)) {
			return defaultRet;
		}
		
		try{
			BigDecimal b = new BigDecimal(Float.toString(d));		
			StringBuilder sb = new StringBuilder(b.setScale(digits, BigDecimal.ROUND_HALF_UP).toString());
			if(isAddPercentSign){
				sb.append('%');
			}
			else{
			    sb.append(unit);
			}
			
			if(d>0 && isAddPlus){
			    sb.insert(0, '+');
			}
			return sb.toString();
		}
		catch(NumberFormatException e){
			return defaultRet;
		}	
	}
	
	public static String formatFloat(float d,int digits,boolean isAddPercentSign,boolean isAddPlus,String defaultRet) {
		return formatFloat(d,digits,isAddPercentSign,isAddPlus,defaultRet,"",true);
	}
	
	public static String formatFloat(float d,int digits,boolean isAddPercentSign,boolean isAddPlus) {
		return formatFloat(d,digits,isAddPercentSign,isAddPlus,"","",false);
	}
	
	public static String formatFloat(float d,int digits,String defaultRet,String unit) {
		return formatFloat(d,digits,false,false,defaultRet,unit,true);
	}
	
	public static String formatFloat(float d,int digits,String defaultRet,boolean isZeroReturnDefault) {
		return formatFloat(d,digits,false,false,defaultRet,"",isZeroReturnDefault);
	}
	
	public static String formatFloat(float d,int digits,boolean isAddPercentSign) {
		return formatFloat(d,digits,isAddPercentSign,false,"","",false);
	}
	
	public static String formatFloat(float d,int digits,String defaultRet) {
		return formatFloat(d,digits,false,false,defaultRet,"",true);
	}
	
	public static String formatFloat(float d,int digits,boolean isAddPercentSign,String defaultRet,boolean isZeroReturnDefault) {
		return formatFloat(d,digits,isAddPercentSign,false,defaultRet,"",isZeroReturnDefault);
	}
	
	public static String formatFloat(float d,int digits) {
		return formatFloat(d,digits,false,false,"","",false);
	}
	
	public static String formatFloat(float d,String defaultRet){
		if(FloatUtils.isZero(d)){
			return defaultRet;
		}
		else{
			return d + "";
		}
	}
	
	/**
	 *	0：返回  defaultRet；非0：返回原字符
	 * 
	 * @param s
	 * @param defaultRet
	 * @return
	 */
	public static String formatString(String s,String defaultRet){
		if(!TextUtils.isEmpty(s)){
			float d = getFloatFromString(s);
			if(FloatUtils.isZero(d)){
				return defaultRet;
			}
			else{
				return s;
			}
		}		
		return defaultRet;
	}
	
	public static String formatString(String s,int digits,String defValue){
		return formatFloat(getFloatFromString(s),digits,defValue,"");
	}
	
	public static String formatPercent(String s,int digits,String defValue){
		return formatFloat(getFloatFromString(s),digits,true,true,defValue);
	}
	
	public static String formatPercent(float d, int digits, boolean isAddPlus) {
		return formatFloat(d,digits,true,isAddPlus,"","",false);
	}
	
	public static float getFloatFromString(String s) {
		if(s != null && !s.trim().equals("")){
			s = s.replaceAll(",", "");
			
			if(s.endsWith("%")){	
				try{
					return Float.valueOf(s.substring(0, s.length()-1));
				}
				catch(NumberFormatException e){
//					e.printStackTrace();
				}
			}
			else{
				try{
					return Float.valueOf(s);
				}
				catch(NumberFormatException e){
//					e.printStackTrace();
				}
			}
		}

		return 0;
	}
	
	public static int getInt(String s) {
		if(s != null && !s.trim().equals("")){
			s = s.replaceAll(",", "");
			
			try{
				return Integer.valueOf(s);
			}
			catch(NumberFormatException e){
//				e.printStackTrace();
			}
		}

		return 0;
	}
	
	public static String formatBigFloat(float d,int digits){
		if(d < FloatUtils.EPSILON){
			return "--";
		}
		else if(d < 1E4){
			return NumberFormatUtil.formatFloat(d, 0);
		}
		else if(d < 1E8){
			return NumberFormatUtil.formatFloat((float) (d/1E4), digits) + "万";
		}
		else{
			return NumberFormatUtil.formatFloat((float) (d/1E8), digits) + "亿";
		}
	}
	
	public static String formatBigString(String d,int digits){
		return formatBigFloat(getFloatFromString(d),digits);
	}
	
	/**
	 * 将传入的数字转换成以逗号分隔的形式，如传入123456789，转换成123,456,789
	 * @param number
	 * @return
	 */
	public static String formatCommaNumber(long number){
		//如果是负数，取正
		boolean bellowzero = false;
		if(number<0){
			bellowzero=true;
			number=-number;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(number);
		//根据数字的大小决定循环几次
		int count = (sb.length()-1)/3;
//		int mod = sb.length()%3==0?3:sb.length()%3;  fucking code, by junco!
		int mod = sb.length()%3;
		if(mod == 0) { mod = 3; }
		mod += 3*(count-1);
		for (int i = 1; i <= count; i++,mod-=3) {
//			sb.insert(mod+3*(count-i), ",");
			sb.insert(mod, ',');
		}
		if(bellowzero){
			sb.insert(0, '-');
		}
		return sb.toString();
	}
	
	/**
	 * 将传入的数字转换成以逗号分隔的形式，如传入123456789，转换成123,456,789
	 * @param number
	 * @return
	 */
	public static String formatCommaNumber(String s){
		StringBuffer sb = new StringBuffer("");
		if(s != null && !s.trim().equals("")){
			s = s.replaceAll(",", "");
			
			String s1 = s;
			int p = s.indexOf('.');
			if(p != -1){
				s1 = s.substring(0, p);
			}

			try{
				long l = Long.valueOf(s1);
				sb.append(formatCommaNumber(l));
				if(p != -1){
					sb.append(s.substring(p, s.length()));
				}
			}
			catch(NumberFormatException e){
//				e.printStackTrace();
			}
		}

		return sb.toString();
	
	}
	
	/**
	 * 逗号分隔数据
	 * @param d
	 * @param digits
	 * @param defaultRet
	 * @return
	 */
	public static String formatCommaNumber(float d,int digits,String defaultRet,String unit){
		if(FloatUtils.isZero(d)){
			return defaultRet;
		}
		else{
		    StringBuilder sb = new StringBuilder(formatCommaNumber(formatFloat(d, digits)));
		    sb.append(unit);
			return sb.toString();
		}
	}
	
	/**
	 * 逗号分隔数据
	 * @param d
	 * @param digits
	 * @return
	 */
	public static String formatCommaNumber(float d,String defaultRet,int digits){
		return formatCommaNumber(d,digits,defaultRet,"");
	}
	

	/**
	 * 逗号分隔数据
	 * @param unit
	 * @param d
	 * @param digits
	 * @return
	 */
	public static String formatCommaNumber(float d,int digits,String unit){
		return formatCommaNumber(d,digits,"",unit);
	}
	
	/**
	 * 逗号分隔数据
	 * @param d
	 * @param digits
	 * @return
	 */
	public static String formatCommaNumber(float d,int digits){
		return formatCommaNumber(d,digits,"","");
	}
	
}
