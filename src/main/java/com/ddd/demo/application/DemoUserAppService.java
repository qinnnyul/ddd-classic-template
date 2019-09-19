package com.ddd.demo.application;

import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.domain.DemoUser;
import com.ddd.demo.domain.DemoUserRepository;
import com.ddd.demo.infrastructure.exception.BussinessException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DemoUserAppService {
    private DemoUserRepository demoUserRepository;

    public DemoUserAppService(DemoUserRepository demoUserRepository) {
        this.demoUserRepository = demoUserRepository;
    }

    public DemoUserResponse getDemoUserById(String demoUserId) {
        DemoUser user = this.demoUserRepository.getById(demoUserId).orElseThrow(()
                -> new BussinessException("无法找到用户", HttpStatus.NOT_FOUND));

        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, DemoUserResponse.class);
    }
}
