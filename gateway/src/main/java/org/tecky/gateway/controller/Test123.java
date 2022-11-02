package org.tecky.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tecky.gateway.mapper.UserEntityRepository;

import java.time.Duration;

@Slf4j
@Component
public class Test123 {
    @Autowired
    UserEntityRepository userEntityRepository;

    public void tell(){

        userEntityRepository.findByUsername("ABg45364sddr345C").doOnNext(customer->log.info(customer.toString())).blockLast(Duration.ofSeconds(10));
    }

}
