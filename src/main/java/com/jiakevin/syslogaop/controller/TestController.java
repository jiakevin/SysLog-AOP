package com.jiakevin.syslogaop.controller;

import com.jiakevin.syslogaop.annotation.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiakevin
 * @since 2021-10-21 23:52
 */
@RestController
public class TestController {

    @SysLog("test log")
    @GetMapping("/test")
    public String test(@RequestParam("name") String name){
        return name;
    }
}
