package com.ddd.demo.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoUserRequest {

    private String name;

    private int age;
}
