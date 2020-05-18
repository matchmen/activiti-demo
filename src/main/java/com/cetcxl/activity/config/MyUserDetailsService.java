/*
package com.cetcxl.activity.config;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IdentityService identityService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserQuery user = identityService.createUserQuery().userId(userName);
        if (user == null) {
            throw new UsernameNotFoundException("user + " + userName + "not found.");
        }

        UserDetails userDetails = new User(userName, "111", new ArrayList<GrantedAuthority>());

        return userDetails;
    }
}
*/
