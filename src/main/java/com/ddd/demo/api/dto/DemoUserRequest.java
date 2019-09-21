package com.ddd.demo.api.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoUserRequest {

    @NotNull
    private String name;

    private int age;
}
