package com.cetcxl.activity.config.activiti;

import com.cetcxl.activity.controller.DeploymentController;
import com.cetcxl.activity.entity.Role;
import com.cetcxl.activity.repository.RoleRepository;
import com.cetcxl.activity.repository.UserRepository;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.activiti.engine.identity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
public class CustomUserEntityManager extends UserEntityManager{
    private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findUserById(String userId){
        com.cetcxl.activity.entity.User user = userRepository.findById(userId);
        if(Objects.isNull(user)){
            return null;
        }

        User userEntity = new UserEntity();
        logger.info("客户信息查询,UserId:{}", userId);
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getName());
        return userEntity;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {

        if(Objects.isNull(userId)){
            return null;
        }
        com.cetcxl.activity.entity.User user = userRepository.findById(userId);
        if(Objects.isNull(user)){
            return null;
        }
        Role role = roleRepository.findById(user.getRoleId());
        List<Group> groups = new ArrayList<>();
        //实体转换
        Group group = new GroupEntity();
        group.setId(role.getId());
        group.setName(role.getName());
        groups.add(group);
        return groups;
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {

        throw new RuntimeException("not implement method findUserByQueryCriteria.");
    }

    private User getUser(String userId) {
        throw new RuntimeException("not implement method getUser.");
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new RuntimeException("not implement method findUserCountByQueryCriteria.");
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        throw new RuntimeException("not implement method findUserInfoByUserIdAndKey.");
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        throw new RuntimeException("not implement method findUserInfoKeysByUserIdAndType.");
    }

    @Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
        throw new RuntimeException("not implement method findPotentialStarterUsers.");
    }

    @Override
    public List<org.activiti.engine.identity.User> findUsersByNativeQuery(Map<String, Object> parameterMap,
                                                                          int firstResult, int maxResults) {
        throw new RuntimeException("not implement method findUsersByNativeQuery.");
    }

    @Override
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new RuntimeException("not implement method findUserCountByNativeQuery.");
    }


}