package com.cetcxl.activity.repository;

import com.cetcxl.activity.entity.Department;
import com.cetcxl.activity.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository  extends JpaRepository<Institution, Department> {
}
