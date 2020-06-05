package com.hxq.interceptor;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hxq.domain.Module;
import com.hxq.domain.OfenMoudle;
import com.hxq.domain.User;
import com.hxq.service.ModuleService;
import com.hxq.service.OfenMoudleService;
import com.hxq.utils.SysConstant;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;


/**
 * 用来记录用户操作的菜单
 * @author 强仔
 *
 */
public class RecordMenuInterceptor extends MethodFilterInterceptor {

	//模块业务
	@Autowired
	private ModuleService moduleService;
	
	//自选菜单业务
	@Autowired
	private OfenMoudleService ofenMoudleService;
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		//获取当前请求连接
		String requestURI = ServletActionContext.getRequest().getRequestURI();
		String contextPath = ServletActionContext.getRequest().getContextPath()+"/";
		final String realUrl = requestURI.replace(contextPath, "");
		
		//获取当前用户
		final User user=(User)ServletActionContext.getRequest().getSession().getAttribute(SysConstant.CURRENT_USER_INFO);
		//根据请求过来的Url获取对应的Moudle
		List<Module> modules = moduleService.find(new Specification<Module>() {
			
			@Override
			public Predicate toPredicate(Root<Module> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.and(cb.equal(root.get("curl").as(String.class),realUrl),cb.equal(root.get("state").as(int.class), 1));
			}
		});
		if(modules!=null&&modules.size()>0)
		{
			final Module module=modules.get(0);
			List<OfenMoudle> ofenMoudles = ofenMoudleService.find(new Specification<OfenMoudle>() {

				@Override
				public Predicate toPredicate(Root<OfenMoudle> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					// TODO Auto-generated method stub
					return cb.and(cb.equal(root.get("userId").as(String.class), user.getId())
							,cb.equal(root.get("moduleId").as(String.class), module.getId()));
				}
			});
			if(ofenMoudles!=null&&ofenMoudles.size()>0)
			{
				//添加访问次数
				OfenMoudle ofenMoudle = ofenMoudles.get(0);
				ofenMoudle.setTimes(ofenMoudle.getTimes()+1);
				ofenMoudleService.saveOrUpdate(ofenMoudle);
			}else {
				//新建浏览记录
				OfenMoudle ofenMoudle=new OfenMoudle();
				ofenMoudle.setModuleId(module.getId());
				ofenMoudle.setModuleName(module.getName());
				ofenMoudle.setModuleUrl(module.getCurl());
				ofenMoudle.setTimes(1);
				ofenMoudle.setType(0);
				ofenMoudle.setUserId(user.getId());
				ofenMoudleService.saveOrUpdate(ofenMoudle);
			}
			
		}
		String result = invocation.invoke();
		return result;
	}
}
