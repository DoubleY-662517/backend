package com.homeworkai.repository;

import com.homeworkai.entity.SysUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserRepository extends MongoRepository<SysUser, String> {
    
    Optional<SysUser> findByUsername(String username);
    
    boolean existsByUsername(String username);
}
