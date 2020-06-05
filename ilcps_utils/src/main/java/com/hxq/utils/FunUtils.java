package com.hxq.utils;

import java.util.Date;

/**
 * 
 * @author 强仔
 *
 */
public class  FunUtils {

	/**
	 * 字符串为空返回""
	 * @param str
	 * @return
	 */
	public static String checkIsNull(String str)
	{
		if(str==null)
		{
			return "";
		}
		return str.trim();
	}
	
	public static Float checkIsNull(Float str)
	{
		if(str==null)
		{
			return 0f;
		}
		return str;
	}
	public static Double checkIsNull(Double str)
	{
		if(str==null)
		{
			return 0.0;
		}
		return str;
	}
	public static Integer checkIsNull(Integer str)
	{
		if(str==null)
		{
			return 0;
		}
		return str;
	}
	public static Date checkIsNull(Date str)
	{
		if(str==null)
		{
			return new Date();
		}
		return str;
	}
}
