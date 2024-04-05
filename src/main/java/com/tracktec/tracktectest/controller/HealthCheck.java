package com.tracktec.tracktectest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("healthCheck")
    @ResponseBody
    public String healthCheck() {
        return "TRACKTEC APP IS UP";
    }
}
