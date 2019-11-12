package com.ddd.demo.infrastructure.po;

import com.ddd.demo.domain.DemoUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Builder
@Entity
public class DemoUserPo {
    @Id
    private String id;

    private String name;

    private int age;

    public DemoUser toDemoUser() {
        return DemoUser.builder().id(this.getId()).age(this.getAge()).name(this.getName()).build();
    }
}
