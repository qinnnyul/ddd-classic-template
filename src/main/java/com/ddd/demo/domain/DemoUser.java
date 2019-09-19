package com.ddd.demo.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoUser {
    private String id;

    private String name;

    private int age;

}
