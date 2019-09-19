package com.ddd.demo.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoUserResponse {

    private String id;

    private String name;

    private int age;

}
