package com.hxq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.hxq.domain.Package;

public interface PackageDao extends JpaRepository<Package,String>,JpaSpecificationExecutor<Package> {

}
