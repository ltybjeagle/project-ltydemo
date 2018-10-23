package com.liuty.maven.dao.jpa;

import com.liuty.maven.dto.Fasp_T_Causer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户持久化对象
 */
@Repository
public interface DemoUserRepository extends JpaRepository<Fasp_T_Causer, String> {
}
