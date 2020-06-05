package com.hxq.licps.jms.Listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.aliyuncs.exceptions.ClientException;
import com.hxq.utils.RandomCode;
import com.hxq.utils.SmsUtils;

@Component("phoneCodeConsume")
public class SendPhoneCode implements MessageListener {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void onMessage(Message arg0) {
		TextMessage message=(TextMessage)arg0;
		try {
			String phone = message.getText();
			String phoneCode = String.valueOf(RandomCode.genCode());
			//发送短信
			SmsUtils.sendSms(phone, phoneCode);
			//将验证码存入到redis中
			redisTemplate.opsForValue().set(phone, phoneCode);
			System.out.println("发送完成");
		} catch (JMSException | ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
