package org.tecky.uaaservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.tecky.common.entities.UserEntity;
import sun.security.provider.PolicyParser;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecurityUserDetailService implements UserDetailsService {


    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;


    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = userService.getUserByUsername(username);
        if (user == null) {
            return null;
        }
        //获取权限
        List<PolicyParser.PermissionEntry> permissions = permissionService.getPermissionsByUserId(user.getId());
        List<String> codes = permissions.stream().map(PermissionEntry::getCode).collect(Collectors.toList());
        String[] authorities = null;
        if (CollectionUtils.isNotEmpty(codes)) {
            authorities = new String[codes.size()];
            codes.toArray(authorities);
        }
        //身份令牌
        String principal = JSON.toJSONString(user);
        return User.withUsername(principal).password(user.getPassword()).authorities(authorities).build();
    }
}