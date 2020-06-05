package com.hxq.client.action.user;



import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.network.jms.JmsQueueConnector;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.hxq.client.action.BaseAction;
import com.hxq.client.domain.UserClient;
import com.hxq.client.service.UserClientService;
import com.hxq.utils.ImageUtil;
import com.hxq.utils.RandomCode;



@Namespace("/user")
public class UserAction extends BaseAction {
	
	private String telephone;//电话号码
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsQueueTemplate ;//AcvtiveMQ实例
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private UserClientService userClientService;
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取验证码
	 * @return
	 * @throws Exception
	 */
	@Action(value="userAction_genActiveCode")
	public String genActiveCode() throws Exception {
		//获取随机验证码
		String rundomStr = ImageUtil.getRundomStr();
		//将验证码保存到session 中
		session.put("ImageCode", rundomStr);
		HttpServletResponse response = ServletActionContext.getResponse();
		ImageUtil.getImage(rundomStr, response.getOutputStream());
		return NONE;
	}
	
	/**
	 * 发送验证码
	 * @return
	 * @throws Exception
	 */
	@Action(value="userAction_sendVerCode")
	public String sendVerCode() throws Exception {
		// TODO Auto-generated method stub
		String phoneCode = String.valueOf(RandomCode.genCode());
		//将发送验证码交给ActiveMQ处理
		jmsQueueTemplate.send("phoneQueue", new MessageCreator() {
			
			@Override
			public Message createMessage(Session arg0) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage textMessage = arg0.createTextMessage(telephone);
				return textMessage;
			}
		});
		
		
		return NONE;
	}
	
	/**
	 * 注册
	 * @return
	 * @throws Exception
	 */
	@Action(value="userAction_register")
	public String register() throws Exception {
		String returnStr="2";
		String imgCode=session.get("ImageCode").toString();
		String phoneCode=redisTemplate.opsForValue().get(telephone);
		//检验图片验证码
		if(!imgCode.equalsIgnoreCase(vercode))
		{
			returnStr="0";
		} 
		//检验手机验证码
		else if(!phoneVercode.equals(phoneCode))
		{
			returnStr="1";
		}
		else {
			UserClient userC=new UserClient();
			userC.setTelephone(telephone);
			userC.setEmail(email);
			userClientService.saveOrUpdate(userC);
			
			//删除缓存
			redisTemplate.delete(telephone);
			session.remove("ImageCode");
		}
		
		ServletActionContext.getResponse().getWriter().write(returnStr);
		return NONE;
	}
	
 private String	vercode;//验证码
 private String phoneVercode;//手机验证码
 private String email;//邮箱


public String getVercode() {
	return vercode;
}

public void setVercode(String vercode) {
	this.vercode = vercode;
}

public String getPhoneVercode() {
	return phoneVercode;
}

public void setPhoneVercode(String phoneVercode) {
	this.phoneVercode = phoneVercode;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}
 
 
}
