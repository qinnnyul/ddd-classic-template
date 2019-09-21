package com.ddd.demo.api;

import com.ddd.demo.api.dto.DemoUserRequest;
import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.application.DemoUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo-users")
public class DemoUserController {
    private DemoUserService demoUserService;

    DemoUserController(DemoUserService demoUserService) {
        this.demoUserService = demoUserService;
    }

    @GetMapping("/{id}")
    @ApiOperation("获取用户信息")
    public DemoUserResponse getDemoUser(@PathVariable String id) {
        return this.demoUserService.getDemoUserById(id);
    }

    @PostMapping
    @ApiOperation("创建用户")
    @ResponseStatus(HttpStatus.CREATED)
    public DemoUserResponse addDemoUser(@RequestBody DemoUserRequest request) {
        return this.demoUserService.addDemoUser(request);
    }

}
