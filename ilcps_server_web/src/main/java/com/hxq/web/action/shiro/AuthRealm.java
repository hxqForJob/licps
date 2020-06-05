package com.hxq.web.action.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.hxq.domain.Module;
import com.hxq.domain.Role;
import com.hxq.domain.User;
import com.hxq.service.UserService;

import oracle.net.aso.MD5;

/**
 * 自定义Realm域
 * @author 强仔
 *
 */
public class AuthRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		//System.out.println("调用了授权方法");
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		User user = (User) arg0.getPrimaryPrincipal();
		//获取用户的角色及角色的模块
		 Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			Set<Module> modules = role.getModules();
			for (Module module : modules) {
				if(module!=null)
				{
					info.addStringPermission(module.getCpermission());
				}
			}
		}
		
/*		Set<String> stringPermissions = info.getStringPermissions();
		System.out.println(stringPermissions);*/
		return info;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		//将接口token转换用户密码token
		UsernamePasswordToken token=(UsernamePasswordToken)arg0;
		final String userName=token.getUsername();
		List<User> users = userService.find(new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate = cb.
						and(cb.equal(root.get("state").as(Integer.class), 1),
								cb.equal(root.get("userName").as(String.class),userName));
				return query.where(predicate).getRestriction();
			}
		});
		if(users!=null&&users.size()>0)
		{
			User loginUser=users.get(0);
			//principal 用户, credentials 密码, realmName （任意字符串）类名
			AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(loginUser, 
					loginUser.getPassword(), getName());
			//返回给密码比较器去验证密码;
			return authenticationInfo;
		}
		return null;
	}

}
