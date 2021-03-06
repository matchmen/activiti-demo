package com.cetcxl.activiti;

import com.cetcxl.activity.ActivityDemoApplication;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityDemoApplication.class)
public class UserInfoTest {

    @Autowired
    private IdentityService identityService;

    /**
     * 初始化用户信息
     * 用户信息表:act_id_user
     */
    @Test
    public void initUser(){
/*        //增加普通员工张三 用户ID:zhangsan
        User zhangsan = identityService.newUser("zhangsan");
        zhangsan.setFirstName("张三");
        zhangsan.setPassword("111");
        identityService.saveUser(zhangsan);

        User zhaoer = identityService.newUser("zhaoer");
        zhaoer.setFirstName("赵二");
        zhaoer.setPassword("111");
        identityService.saveUser(zhaoer);

        //增加项目经理李四 用户ID:lisi
        User lisi = identityService.newUser("lisi");
        lisi.setFirstName("李四");
        lisi.setPassword("111");
        identityService.saveUser(lisi);

        User wanger = identityService.newUser("wanger");
        wanger.setFirstName("王二");
        wanger.setPassword("111");
        identityService.saveUser(wanger);

        User wangwu = identityService.newUser("wangwu");
        wangwu.setFirstName("王五");
        wangwu.setPassword("111");
        identityService.saveUser(wangwu);*/

        User liwu = identityService.newUser("liwu");
        liwu.setFirstName("李四");
        liwu.setPassword("111");
        identityService.saveUser(liwu);
    }

    /**
     * 初始化分组信息
     * 分组信息表(角色):act_id_group
     */
    @Test
    public void initGroup(){
        //增加普通员工张三 用户ID:zhangsan
        Group employee = identityService.newGroup("employee");
        employee.setName("普通员工");
        identityService.saveGroup(employee);

        Group programManager = identityService.newGroup("programManager");
        programManager.setName("项目经理");
        identityService.saveGroup(programManager);

        Group departmentManager = identityService.newGroup("departmentManager");
        departmentManager.setName("部门经理");
        identityService.saveGroup(departmentManager);
    }

    /**
     * 绑定用户角色关系
     * 涉及表:act_id_membership
     */
    @Test
    public void bindUserRole(){
        identityService.createMembership("zhangsan","employee");
        identityService.createMembership("lisi","programManager");
        identityService.createMembership("wangwu","departmentManager");
        identityService.createMembership("zhaoer","programManager");
        identityService.createMembership("wanger","employee");
    }

    @Test
    public void getUser() {
        List<String> lisi1 = identityService.getUserInfoKeys("lisi");

        System.out.println(lisi1.size());

    }


    @Test
    public void getGroup() {
        long potentialOwners = identityService.createUserQuery().memberOfGroup("management").count();
    }



}
