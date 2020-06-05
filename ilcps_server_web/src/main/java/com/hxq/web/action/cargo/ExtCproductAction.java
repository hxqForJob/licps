package com.hxq.web.action.cargo;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.hxq.domain.Contract;
import com.hxq.domain.ExtCproduct;
import com.hxq.domain.Factory;
import com.hxq.service.ExtCproductService;
import com.hxq.service.FactoryService;
import com.hxq.utils.Page;
import com.hxq.utils.file.StringUtil;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

//购销合同下的产品附件控制器
@Namespace("/cargo")
@Result(name="toCProList",type="redirectAction",location="extCproductAction_tocreate?contractProduct.contract.id=${contractProduct.contract.id}&contractProduct.id=${contractProduct.id}")
public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct> {

	private ExtCproduct model = new ExtCproduct();

	@Override
	public ExtCproduct getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	private Page<ExtCproduct> page = new Page<>();

	public Page<ExtCproduct> getPage() {
		return page;
	}

	public void setPage(Page<ExtCproduct> page) {
		this.page = page;
	}

	@Autowired
	private ExtCproductService extCproductService;
	
	@Autowired
	private FactoryService factoryService;

	/**
	 * 购销合同货物图片
	 */
	private File image;
	
	/**
	 * 文件名
	 * @return
	 */
	private String  imageFileName;
	
	/**
	 * 文件类型
	 * @return
	 */
	 private String imageContentType;
	 
	 
	 
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	/**
	 * 查询附件,创建附件视图
	 * @return
	 */
	@Action(value = "extCproductAction_tocreate", results = {
			@Result(name = "toExtProductCreate", location = "/WEB-INF/pages/cargo/contract/jExtCproductCreate.jsp") })
	public String tocreate() {
		
		// 1.加载生产厂家列表 ctype="附件" and state=1
				Specification<Factory> spec = new Specification<Factory>() {
					public Predicate toPredicate(Root<Factory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate p1 = cb.equal(root.get("ctype").as(String.class), "附件");
						Predicate p2 = cb.equal(root.get("state").as(Integer.class), 1);
						return cb.and(p1, p2);//两个条件用and,or等进行连接
					}
				};
				List<Factory> factoryList = factoryService.find(spec);
				// 2.将factoryList放入值栈中
				super.put("factoryList", factoryList);
		
		//附件查询
		org.springframework.data.domain.Page<ExtCproduct> findPage = extCproductService
				.findPage(new Specification<ExtCproduct>() {

					@Override
					public Predicate toPredicate(Root<ExtCproduct> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						return cb.equal(root.get("contractProduct").get("id"), model.getContractProduct().getId());
					}
				}, new PageRequest(page.getPageNo() - 1, page.getPageSize()));
		page.setTotalRecord(findPage.getTotalElements());
		page.setResults(findPage.getContent());
		page.setTotalPage(findPage.getTotalPages());
		page.setUrl("extCproductAction_tocreate");
		push(page);
		return "toExtProductCreate";
	}
	
	/**
	 * 添加购销合同下的附件
	 * @return
	 * @throws IOException 
	 */
	@Action("extCproductAction_insert")
	public String insertExtCproduct() throws IOException
	{
		if(image!=null){
			//上传路径
	        String path = ServletActionContext.getServletContext().getRealPath("/ufiles/jquery/");
	       
	        File file=new File(path);
	        //判断文件夹是否存在
	        if(!file.exists())
	        {
	        	file.mkdirs();
	        }
	        String newfileName="";
	        DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	        String date=dateFormat.format(new Date());
	        int lastIndex=imageFileName.lastIndexOf(".");
	        String suffix=imageFileName.substring(lastIndex);
	        newfileName=date+suffix;
	        file=new File(path,newfileName);
	        FileUtils.copyFile(image, file);
	        //上传到工程目录
	        file=new File("F:/新建文件夹/ilcps_parent/ilcps_server_web/src/main/webapp/ufiles/jquery/",newfileName);
	        FileUtils.copyFile(image, file);
	        model.setProductImage(newfileName);
		}
		extCproductService.saveOrUpdate(model);
		return "toCProList";
	}
	
	/**
	 * 更新附件视图
	 * @return
	 */
	@Action(value="extCproductAction_toupdate",results={@Result(name="toUpdateView",location="/WEB-INF/pages/cargo/contract/jExtCproductUpdate.jsp")})
	public String updateExtCproductView()
	{
		// 1.加载生产厂家列表 ctype="附件" and state=1
		Specification<Factory> spec = new Specification<Factory>() {
			public Predicate toPredicate(Root<Factory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("ctype").as(String.class), "附件");
				Predicate p2 = cb.equal(root.get("state").as(Integer.class), 1);
				return cb.and(p1, p2);//两个条件用and,or等进行连接
			}
		};
		List<Factory> factoryList = factoryService.find(spec);
		// 2.将factoryList放入值栈中
		super.put("factoryList", factoryList);
		//查询当前附件信息
		ExtCproduct extCproduct = extCproductService.get(model.getId());
		push(extCproduct);
		
		return "toUpdateView";
	}
	
	/**
	 * 更新附件
	 * @return
	 * @throws IOException 
	 */
	@Action("extCproductAction_update")
	public String updateExtCproduct() throws IOException
	{

		if(image!=null){
			//上传路径
	        String path = ServletActionContext.getServletContext().getRealPath("/ufiles/jquery/");
	       
	        File file=new File(path);
	        //判断文件夹是否存在
	        if(!file.exists())
	        {
	        	file.mkdirs();
	        }
	        String newfileName="";
	        DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	        String date=dateFormat.format(new Date());
	        int lastIndex=imageFileName.lastIndexOf(".");
	        String suffix=imageFileName.substring(lastIndex);
	        newfileName=date+suffix;
	        file=new File(path,newfileName);
	        FileUtils.copyFile(image, file);
	        //上传到工程目录
	        file=new File("F:/新建文件夹/ilcps_parent/ilcps_server_web/src/main/webapp/ufiles/jquery/",newfileName);
	        FileUtils.copyFile(image, file);
	        model.setProductImage(newfileName);
		}
		// 1.先查询原有的对象
		ExtCproduct obj = extCproductService.get(model.getId());
		// 2.针对页面上要修改的属性进行修改
		obj.setFactory(model.getFactory());
		obj.setFactoryName(model.getFactoryName());
		obj.setProductNo(model.getProductNo());
		if(!StringUtil.isEmpty(model.getProductImage()))
		{
			obj.setProductImage(model.getProductImage());
		}
		obj.setCnumber(model.getCnumber());
		obj.setPackingUnit(model.getPackingUnit());
		obj.setPrice(model.getPrice());
		obj.setOrderNo(model.getOrderNo());
		obj.setProductDesc(model.getProductDesc());
		obj.setProductRequest(model.getProductRequest());
		// 3.调用业务方法，实现更新
		extCproductService.saveOrUpdate(obj);
		return "toCProList";
	}
	
	/**
	 * 删除附件
	 * @return
	 */
	@Action("extCproductAction_delete")
	public String deleteExtCproduct()
	{
		extCproductService.deleteById(model.getId());
		return "toCProList";
	}
}
