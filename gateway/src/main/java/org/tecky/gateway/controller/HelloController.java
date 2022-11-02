package org.tecky.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tecky.gateway.mapper.UserEntityRepository;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;

@Slf4j
@RestController
public class HelloController {

    @Autowired
    UserEntityRepository userEntityRepository;


    @GetMapping ("/hello")
    public Mono<String> hello() throws InterruptedException {

        CountDownLatch cdl = new CountDownLatch(1);

        userEntityRepository.findByUsername("ABg45364sddr345C")
                .doFinally(s -> cdl.countDown())
                .subscribe(c -> log.info("Find {}", c));

        cdl.await();

        return Mono.just("A");
    }
}
