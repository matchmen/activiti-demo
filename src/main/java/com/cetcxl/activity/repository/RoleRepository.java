package com.cetcxl.activity.repository;

import com.cetcxl.activity.entity.Department;
import com.cetcxl.activity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Role> {

    Role findById(String id);

}
