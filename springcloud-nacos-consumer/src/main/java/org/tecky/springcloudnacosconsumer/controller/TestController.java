package org.tecky.springcloudnacosconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hi")
    public String hi(String name) {
        // 调用生产者 sayhi 方法，并返回结果
        return restTemplate.getForObject("http://service-nacos/sayhi/" + name,
                String.class);
    }
    @GetMapping("/hello")
    public String hello(String name) {

        return "Hello ";
    }

}