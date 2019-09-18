package com.ddd.demo.domain;

import java.util.Optional;

public interface DemoUserRepository {

    DemoUser addDemoUser(DemoUser demoUser);

    Optional<DemoUser> getById(String id);

    Optional<DemoUser> update(DemoUser demoUser);

    void deleteById(String id);
}
