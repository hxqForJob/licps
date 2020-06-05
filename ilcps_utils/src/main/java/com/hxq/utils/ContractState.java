package com.hxq.utils;

/**
 * 购销合同状态
 * @author 强仔
 *
 */
public class ContractState {
	public static final int CHECK=7;//已结算
	public static final int FINANCE=6;//已上报财务
	public static final int INVOICE=5;//已开发票
	public static final int DELEGATE=4;//已委托
	public static final int PACKAGE=3;//已装箱
	public static final int EXPORT=2;//报运
	public static final int SUBMMIT=1;//已上报
	public static final int CANCEL=0;//草稿
}
