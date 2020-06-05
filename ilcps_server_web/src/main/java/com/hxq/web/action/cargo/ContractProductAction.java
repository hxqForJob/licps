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
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.hxq.domain.ContractProduct;
import com.hxq.domain.Factory;
import com.hxq.service.ContractProductService;
import com.hxq.service.FactoryService;
import com.hxq.utils.Page;
import com.hxq.utils.file.StringUtil;
import com.hxq.web.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;



//购销合同下的产品控制器
@Namespace("/cargo")
@Result(name="toCProList",type="redirectAction",location="contractProductAction_tocreate?contract.id=${contract.id}")
public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct> {

	private ContractProduct model = new ContractProduct();

	@Override
	public ContractProduct getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	private Page<ContractProduct> page = new Page<>();

	public Page<ContractProduct> getPage() {
		return page;
	}

	public void setPage(Page<ContractProduct> page) {
		this.page = page;
	}

	@Autowired
	private ContractProductService contractProductService;
	
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
	 * 查询货物,创建货物视图
	 * @return
	 */
	@Action(value = "contractProductAction_tocreate", results = {
			@Result(name = "toContactProductCreate", location = "/WEB-INF/pages/cargo/contract/jContractProductCreate.jsp") })
	public String tocreate() {
		
		// 1.加载生产厂家列表 ctype="货物" and state=1
				Specification<Factory> spec = new Specification<Factory>() {
					public Predicate toPredicate(Root<Factory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate p1 = cb.equal(root.get("ctype").as(String.class), "货物");
						Predicate p2 = cb.equal(root.get("state").as(Integer.class), 1);
						return cb.and(p1, p2);//两个条件用and,or等进行连接
					}
				};
				List<Factory> factoryList = factoryService.find(spec);
				// 2.将factoryList放入值栈中
				super.put("factoryList", factoryList);
		
		//货物查询
		org.springframework.data.domain.Page<ContractProduct> findPage = contractProductService
				.findPage(new Specification<ContractProduct>() {

					@Override
					public Predicate toPredicate(Root<ContractProduct> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						// TODO Auto-generated method stub
						return cb.equal(root.get("contract").get("id"), model.getContract().getId());
					}
				}, new PageRequest(page.getPageNo() - 1, page.getPageSize()));
		page.setTotalRecord(findPage.getTotalElements());
		page.setResults(findPage.getContent());
		page.setTotalPage(findPage.getTotalPages());
		page.setUrl("contractProductAction_tocreate");
		push(page);
		return "toContactProductCreate";
	}
	
	/**
	 * 添加购销合同下的货物
	 * @return
	 * @throws IOException 
	 */
	@Action(value="contractProductAction_insert",interceptorRefs={@InterceptorRef("imgUploadInterceptors")})
	public String insertContractProduct() throws IOException
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
		contractProductService.saveOrUpdate(model);
		return "toCProList";
	}
	

	
	/**
	 * 更新货物视图
	 * @return
	 * 
	 */
	@Action(value="contractProductAction_toupdate",results={@Result(name="toUpdateView",location="/WEB-INF/pages/cargo/contract/jContractProductUpdate.jsp")})
	public String updateContractProductView() 
	{
		// 1.加载生产厂家列表 ctype="货物" and state=1
		Specification<Factory> spec = new Specification<Factory>() {
			public Predicate toPredicate(Root<Factory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("ctype").as(String.class), "货物");
				Predicate p2 = cb.equal(root.get("state").as(Integer.class), 1);
				return cb.and(p1, p2);//两个条件用and,or等进行连接
			}
		};
		List<Factory> factoryList = factoryService.find(spec);
		// 2.将factoryList放入值栈中
		super.put("factoryList", factoryList);
		//查询当前货物信息
		ContractProduct contractProduct = contractProductService.get(model.getId());
		push(contractProduct);
		return "toUpdateView";
	}
	
	/**
	 * 更新货物
	 * @return
	 * @throws IOException 
	 */
	@Action("contractProductAction_update")
	public String updateContractProduct() throws IOException
	{
		if(image!=null)
		{
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
		ContractProduct contractProduct = contractProductService.get(model.getId());
		contractProduct.setFactory(model.getFactory());
		contractProduct.setFactoryName(model.getFactoryName());
		contractProduct.setProductNo(model.getProductNo());
		if(!StringUtil.isEmpty(model.getProductImage()))
		{
			contractProduct.setProductImage(model.getProductImage());
		}
		contractProduct.setCnumber(model.getCnumber());
		contractProduct.setPackingUnit(model.getPackingUnit());
		contractProduct.setLoadingRate(model.getLoadingRate());
		contractProduct.setBoxNum(model.getBoxNum());
		contractProduct.setPrice(model.getPrice());
		contractProduct.setOrderNo(model.getOrderNo());
		contractProduct.setProductDesc(model.getProductDesc());
		contractProduct.setProductRequest(model.getProductRequest());
		contractProductService.saveOrUpdate(contractProduct);
		return "toCProList";
	}
	
	/**
	 * 删除货物
	 * @return
	 */
	@Action("contractProductAction_delete")
	public String deleteContractProduct()
	{
		contractProductService.deleteById(model.getId());
		return "toCProList";
	}
}
