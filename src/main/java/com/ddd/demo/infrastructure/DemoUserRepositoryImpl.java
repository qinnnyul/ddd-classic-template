package com.ddd.demo.infrastructure;

import com.ddd.demo.common.util.BeanMapper;
import com.ddd.demo.domain.DemoUser;
import com.ddd.demo.domain.DemoUserRepository;
import com.ddd.demo.infrastructure.dao.DemoUserDao;
import com.ddd.demo.infrastructure.po.DemoUserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class DemoUserRepositoryImpl implements DemoUserRepository {

    private DemoUserDao demoUserDao;

    @Autowired
    public DemoUserRepositoryImpl(DemoUserDao demoUserDao) {
        this.demoUserDao = demoUserDao;
    }

    @Override
    public DemoUser addDemoUser(DemoUser demoUser) {
        demoUser.setId(UUID.randomUUID().toString());
        DemoUserPo demoUserPo = BeanMapper.instance().map(demoUser, DemoUserPo.class);
        this.demoUserDao.save(demoUserPo);

        return demoUser;
    }

    @Override
    public Optional<DemoUser> getById(String id) {
        DemoUserPo demoUserPo = this.demoUserDao.getOne(id);
        if (demoUserPo == null) {
            return Optional.empty();
        }

        return Optional.of(demoUserPo.toDemoUser());
    }

    @Override
    public Optional<DemoUser> update(DemoUser demoUser) {
        DemoUserPo demoUserPo = BeanMapper.instance().map(demoUser, DemoUserPo.class);
        this.demoUserDao.save(demoUserPo);

        return getById(demoUser.getId());
    }

    @Override
    public void deleteById(String id) {
        this.demoUserDao.deleteById(id);
    }
}
