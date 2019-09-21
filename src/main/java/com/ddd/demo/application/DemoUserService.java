package com.ddd.demo.application;

import com.ddd.demo.api.assembler.DemoUserAssembler;
import com.ddd.demo.api.dto.DemoUserRequest;
import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.common.exception.BussinessException;
import com.ddd.demo.domain.DemoUser;
import com.ddd.demo.domain.DemoUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DemoUserService {
    private DemoUserRepository demoUserRepository;

    DemoUserService(DemoUserRepository demoUserRepository) {
        this.demoUserRepository = demoUserRepository;
    }

    public DemoUserResponse getDemoUserById(String demoUserId) {
        DemoUser user = this.demoUserRepository.getById(demoUserId).orElseThrow(()
                -> new BussinessException("无法找到用户", HttpStatus.NOT_FOUND));

        return DemoUserAssembler.toDemoUserResponse(user);
    }

    public DemoUserResponse addDemoUser(DemoUserRequest demoUserRequest) {
        DemoUser user = DemoUserAssembler.toDemoUser(demoUserRequest);

        DemoUser savedUser = this.demoUserRepository.addDemoUser(user);
        return DemoUserAssembler.toDemoUserResponse(savedUser);
    }
}
