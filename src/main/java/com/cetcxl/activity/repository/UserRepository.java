package com.cetcxl.activity.repository;

import com.cetcxl.activity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,User> {

    User findById(String id);

}
