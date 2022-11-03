package org.tecky.uaaservice.security.services;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tecky.common.entities.RoleEntity;
import org.tecky.common.entities.UserEntity;
import org.tecky.common.entities.UserRoleEntity;
import org.tecky.uaaservice.mapper.RoleEntityRespository;
import org.tecky.uaaservice.mapper.UserEntityRepository;
import org.tecky.uaaservice.mapper.UserRoleEntityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRoleEntityRepository userRoleEntityRepository;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    RoleEntityRespository roleEntityRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userEntityRepository.findByUsername(username);

        if(userEntity == null) {

            throw new UsernameNotFoundException(username);
        }

        List<UserRoleEntity> userRoleEntity = userRoleEntityRepository.findByUid(userEntity.getUid());

        List<GrantedAuthority> authorities = userRoleEntity.stream()
                .map(element -> roleEntityRespository
                .findByRoleid(element.getRoleid()))
                .map(element -> new SimpleGrantedAuthority(element.getName()))
                .collect(Collectors.toList());

        User user = new User(userEntity.getUsername(), userEntity.getShapassword(), authorities);

        return user;
    }
}
