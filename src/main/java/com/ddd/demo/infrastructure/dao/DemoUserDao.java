package com.ddd.demo.infrastructure.dao;

import com.ddd.demo.infrastructure.po.DemoUserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoUserDao extends JpaRepository<DemoUserPo, String> {

}
