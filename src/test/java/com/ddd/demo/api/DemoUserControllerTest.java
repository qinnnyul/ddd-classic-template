package com.ddd.demo.api;

import com.ddd.demo.ControllerBaseTest;
import com.ddd.demo.application.DemoUserAppService;
import com.ddd.demo.domain.DemoUser;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class DemoUserControllerTest extends ControllerBaseTest {

    private DemoUserAppService demoUserAppService;


    @Before
    public void setUp() throws Exception {
        DemoUser user = DemoUser.builder().id("user").age(18).name("yulin").build();
        demoUserAppService = mock(DemoUserAppService.class);


    }

    @Test
    public void shouldName() {
        //given


        //when


        //then
    }
}