package com.cetcxl.activity.repository;

import com.cetcxl.activity.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository  extends JpaRepository<Department,Department> {
}
