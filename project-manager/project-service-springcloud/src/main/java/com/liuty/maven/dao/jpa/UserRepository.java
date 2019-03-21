package com.liuty.maven.dao.jpa;

import com.liuty.maven.dto.CauserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 类注解说明：
 *      1、注解@Repository：标注实例由SPRING容器加载管理，用于持久层
 *
 * @Description: 用户持久化对象组件
 */
@Repository
public interface UserRepository extends JpaRepository<CauserDTO, Integer> {
}
