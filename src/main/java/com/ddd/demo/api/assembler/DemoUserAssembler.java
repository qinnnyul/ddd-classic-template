package com.ddd.demo.api.assembler;

import com.ddd.demo.api.dto.DemoUserRequest;
import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.domain.DemoUser;

public class DemoUserAssembler {
    public static DemoUser toDemoUser(DemoUserRequest demoUserRequest) {
        return DemoUser.builder()
                .name(demoUserRequest.getName())
                .age(demoUserRequest.getAge())
                .build();
    }

    public static DemoUserResponse toDemoUserResponse(DemoUser demoUser) {
        return DemoUserResponse.builder()
                .id(demoUser.getId())
                .name(demoUser.getName())
                .age(demoUser.getAge())
                .build();
    }

    public static DemoUser toDemoUser(String id, DemoUserRequest demoUserRequest) {
        return DemoUser.builder()
                .id(id)
                .name(demoUserRequest.getName())
                .age(demoUserRequest.getAge())
                .build();
    }
}
