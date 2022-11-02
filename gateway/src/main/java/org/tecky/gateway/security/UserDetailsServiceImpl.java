package org.tecky.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.tecky.gateway.mapper.UserEntityRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

//    @Resource
//    private PasswordEncoder passwordEncoder;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        SecurityUserDetails securityUserDetails = new SecurityUserDetails(
                "AAAAAAAAA",
                "AAAAAAAAA",
                true, true, true, true, new ArrayList<>(),
                "AAAAAAAAA"
        );

        return Mono.just(securityUserDetails);
    }
}