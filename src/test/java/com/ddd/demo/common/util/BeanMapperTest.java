package com.ddd.demo.common.util;

import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.domain.DemoUser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class BeanMapperTest {

    @Test
    public void shouldMapObjectAtoObjectB() {
        //given
        String id = "test-id";
        int age = 18;
        String name = "yulin";
        DemoUser user = DemoUser.builder().id(id).age(age).name(name).build();

        //when
        DemoUserResponse response = BeanMapper.instance().map(user, DemoUserResponse.class);


        //then
        assertThat(response.getId(), is(id));
        assertThat(response.getAge(), is(age));
        assertThat(response.getName(), is(name));
    }
}