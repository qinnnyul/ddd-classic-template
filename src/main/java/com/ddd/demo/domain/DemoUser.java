package com.ddd.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DemoUser {
    private String id;

    private String name;

    private int age;

}
