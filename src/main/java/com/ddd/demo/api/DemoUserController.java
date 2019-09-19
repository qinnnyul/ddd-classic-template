package com.ddd.demo.api;

import com.ddd.demo.application.DemoUserAppService;

public class DemoUserController {
    private DemoUserAppService demoUserAppService;

    public DemoUserController(DemoUserAppService demoUserAppService) {

        this.demoUserAppService = demoUserAppService;
    }
}
