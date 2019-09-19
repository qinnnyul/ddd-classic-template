package com.ddd.demo.application;

import com.ddd.demo.api.dto.DemoUserRequest;
import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.common.exception.BussinessException;
import com.ddd.demo.common.util.BeanMapper;
import com.ddd.demo.domain.DemoUser;
import com.ddd.demo.domain.DemoUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DemoUserAppService {
    private DemoUserRepository demoUserRepository;

    DemoUserAppService(DemoUserRepository demoUserRepository) {
        this.demoUserRepository = demoUserRepository;
    }

    public DemoUserResponse getDemoUserById(String demoUserId) {
        DemoUser user = this.demoUserRepository.getById(demoUserId).orElseThrow(()
                -> new BussinessException("无法找到用户", HttpStatus.NOT_FOUND));

        return BeanMapper.instance().map(user, DemoUserResponse.class);
    }

    public DemoUserResponse addDemoUser(DemoUserRequest demoUserRequest) {
        DemoUser user = BeanMapper.instance().map(demoUserRequest, DemoUser.class);

        DemoUser savedUser = this.demoUserRepository.addDemoUser(user);
        return BeanMapper.instance().map(savedUser, DemoUserResponse.class);
    }
}
