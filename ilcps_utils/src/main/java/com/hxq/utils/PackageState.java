package com.hxq.utils;

/**
 * 装箱单状态
 * @author 强仔
 *
 */
public class PackageState {
	public static final int CHECK=5;//已结算
	public static final int FINANCE=4;//已上报财务
	public static final int INVOICE=3;//已开发票
	public static final int DELEGATE=2;//委托
	public static final int SUBMMIT=1;//提交
	public static final int CANCEL=0;//草稿
}
