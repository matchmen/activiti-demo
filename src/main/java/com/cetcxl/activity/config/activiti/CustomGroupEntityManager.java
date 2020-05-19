package com.cetcxl.activity.config.activiti;

import java.util.*;

import com.cetcxl.activity.controller.DeploymentController;
import com.cetcxl.activity.entity.Role;
import com.cetcxl.activity.entity.User;
import com.cetcxl.activity.repository.RoleRepository;
import com.cetcxl.activity.repository.UserRepository;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomGroupEntityManager extends GroupEntityManager{

    private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Group> findGroupsByUser(String userId) {
        if(Objects.isNull(userId)){
            return null;
        }
        User user = userRepository.findById(userId);
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
    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new RuntimeException("not implement method findGroupsByNativeQuery.");
    }

    @Override
    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        logger.debug("findGroupByQueryCriteria called, query: " + query + " page: " + page);
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new RuntimeException("not implement method findGroupCountByQueryCriteria.");
    }

    @Override
    public Group createNewGroup(String groupId) {
        throw new RuntimeException("not implement method createNewGroup.");
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new RuntimeException("not implement method deleteGroup.");
    }

}