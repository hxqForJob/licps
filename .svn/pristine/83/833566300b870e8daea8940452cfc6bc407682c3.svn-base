package com.hxq.job;

import java.text.ParseException;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.hxq.utils.UtilFuns;

public class MailJob {

	@Autowired
	private MailSender sender;
	
	@Autowired
	private SimpleMailMessage message;
	public void send() throws ParseException
	{
		message.setTo("1023397421@qq.com");
		message.setSubject("test");
		message.setText("这是一个使用Quartz测试邮件");
		sender.send(message);
		System.out.println(UtilFuns.dateTimeFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
}
