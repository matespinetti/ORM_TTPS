package com.example.orm_ttps.repository;

import com.example.orm_ttps.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long>{
}
