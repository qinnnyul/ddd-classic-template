package com.ddd.demo.api;

import com.ddd.demo.api.dto.DemoUserRequest;
import com.ddd.demo.api.dto.DemoUserResponse;
import com.ddd.demo.application.DemoUserAppService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DemoUserControllerTest extends ControllerBaseTest {

    private DemoUserAppService demoUserAppService;


    @Before
    public void setUp() throws Exception {
        demoUserAppService = mock(DemoUserAppService.class);
        RestAssuredMockMvc.standaloneSetup(new DemoUserController(demoUserAppService));
    }

    @Test
    public void shouldGetDemoUserResponse() {
        //given

        DemoUserResponse response = DemoUserResponse.builder().age(19).name("yulin").build();

        String demoUserId = "test-id";

        when(demoUserAppService.getDemoUserById(demoUserId)).thenReturn(response);
        //when

        given()
                .when().get("/demo-users/" + demoUserId)
                .then().statusCode(200).body("age", equalTo(19));

        //then
    }

    @Test
    public void shouldReturn201WhenAddDemoUser() {
        //given
        DemoUserRequest request = DemoUserRequest.builder().age(19).name("yulin").build();

        //then
        given()
                .contentType(ContentType.JSON).body(request)
                .when().post("/demo-users")
                .then().statusCode(201);
    }

}