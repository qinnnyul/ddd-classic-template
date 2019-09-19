package com.ddd.demo.api;

import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.application.DemoUserAppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo-users")
public class DemoUserController {
    private DemoUserAppService demoUserAppService;

    public DemoUserController(DemoUserAppService demoUserAppService) {
        this.demoUserAppService = demoUserAppService;
    }

    @GetMapping("/{id}")
    public DemoUserResponse getDemoUser(@PathVariable String id) {
        return this.demoUserAppService.getDemoUserById(id);
    }



}
