package com.leinuoa.learn.shardingjdbc.web.controller;

import com.leinuoa.learn.shardingjdbc.Service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @PostMapping
    public String insertOrder(Integer userId) {
        return demoService.insert(userId);
    }
}
