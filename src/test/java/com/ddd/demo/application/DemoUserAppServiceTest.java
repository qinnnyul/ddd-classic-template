package com.ddd.demo.application;

import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.domain.DemoUser;
import com.ddd.demo.domain.DemoUserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DemoUserAppServiceTest {
    private DemoUserRepository demoUserRepository;
    private DemoUserAppService demoUserAppService;

    @Before
    public void setUp() throws Exception {
        demoUserRepository = mock(DemoUserRepository.class);
        demoUserAppService = new DemoUserAppService(demoUserRepository);
    }

    @Test
    public void shouldConvertDemoUserToDemoUserResponse() {
        //given
        String id = "user-id";
        Optional<DemoUser> demoUser = Optional.of(DemoUser.builder().id(id).age(18).name("yulin").build());
        when(demoUserRepository.getById(id)).thenReturn(demoUser);

        //when
        DemoUserResponse response = demoUserAppService.getDemoUserById(id);

        //then
        assertThat(response.getId(), is(id));
        assertThat(response.getName(), is("yulin"));
    }
}