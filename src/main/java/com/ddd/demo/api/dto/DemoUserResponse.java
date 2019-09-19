package com.ddd.demo.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DemoUserResponse {

    private String id;

    private String name;

    private int age;

}
