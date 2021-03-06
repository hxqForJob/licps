package com.hxq.dao;

import java.util.List;import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hxq.domain.Contract;
import com.hxq.domain.ContractProduct;

public interface ContractProductDao extends JpaRepository<ContractProduct,String>,JpaSpecificationExecutor<ContractProduct> {
	/**
	 * 根据船期获取出货信息
	 * @param shipTime
	 * @return
	 */
	@Query("from ContractProduct where to_char(contract.shipTime,'yyyy-MM')=?1")
	public List<ContractProduct> findByShipTime(String shipTime);
	
	/**
	 * 根据购销合同ID查询合同下的货物
	 * @param contractIds
	 * @return
	 */
	@Query("from ContractProduct where contract.id in (?1)")
	public List<ContractProduct> findCpByContractIds(String [] contractIds);
	
	
	/**
	 * 查询生产厂家销售情况
	 */
	@Query(value="select t.factory_name,sum(t.amount) from CONTRACT_PRODUCT_C t group by t.factory_name",nativeQuery=true)
	public List<Object[]> findFactrySale();
	
	
	/**
	 * 查询产品销售情况
	 * @return
	 */
	@Query(value="select * from (select product_no ,sum(amount) from CONTRACT_PRODUCT_C group by product_no order by sum(cnumber) desc) where rownum<15",nativeQuery=true)
	public List<Object[]> finProductSale();
	
	/**
	 * 查询访问量
	 */
	@Query(value="select o.a1, nvl(l.num,0) from online_info_t o left join(select to_char(login_time,'HH24') time , count(*) num from LOGIN_LOG_P t where to_char(login_time,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')  group by to_char(login_time,'HH24'))  l on o.a1=l.time order by o.a1",nativeQuery=true)
	public List<Object[]> findOnlineInfo();
}
