package com.homeworkai.repository;

import com.homeworkai.entity.SysRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysRoleRepository extends MongoRepository<SysRole, String> {
    
    Optional<SysRole> findByCode(String code);
}
