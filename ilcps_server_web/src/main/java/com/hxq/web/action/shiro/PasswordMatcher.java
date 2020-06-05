package com.hxq.web.action.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * 密码比较器
 * @author 强仔
 *
 */
public class PasswordMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		// TODO Auto-generated method stub
		//用户输入的密码
		UsernamePasswordToken uToken=(UsernamePasswordToken)token;
		//md5加密
		String inputPwd=new Md5Hash(new String(uToken.getPassword()),uToken.getUsername(),2).toString();
		//数据库查询的密码
		String dbPwd = (String) info.getCredentials();
		return equals(dbPwd, inputPwd);
	}
}
