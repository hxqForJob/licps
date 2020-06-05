package com.hxq.utils;

public class RandomCode {
	public static int genCode(){
		int code = (int)((Math.random()*9+1)*100000);
		return code;
	}
	
	public static void main(String[] args) {
		System.out.println(RandomCode.genCode());
	}
}
