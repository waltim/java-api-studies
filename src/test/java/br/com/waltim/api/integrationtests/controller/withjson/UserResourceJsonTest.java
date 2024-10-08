package br.com.waltim.api.integrationtests.controller.withjson;

import br.com.waltim.api.config.TestConfigs;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.domain.vo.Address;
import br.com.waltim.api.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static UserDTO user;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        user = new UserDTO();
    }

    @Test
    @Order(1)
    public void testCreateUser() throws IOException {

        mockUser();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONTEND)
                .setBasePath("/v1/users")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        UserDTO createdUser = objectMapper.readValue(content, UserDTO.class);
        user = createdUser;

        assertNotNull(createdUser);
        assertNotNull(createdUser.getKey());
        assertNotNull(createdUser.getName());
        assertNotNull(createdUser.getEmail());
        assertNotNull(createdUser.getAddress());
//        assertNotNull(createdUser.getPassword());

        assertTrue(createdUser.getKey() > 0);

        assertTrue(createdUser.getName().equals("Waltim"));
        assertTrue(createdUser.getEmail().equals("<EMAIL>"));
//        assertTrue(createdUser.getPassword().equals("<PASSWORD>"));
        assertTrue(createdUser.getAddress().getStreet().equals("Main Street"));
        assertTrue(createdUser.getAddress().getNumber().equals("123"));
        assertTrue(createdUser.getAddress().getComplement().equals("Apt 4B"));
        assertTrue(createdUser.getAddress().getCity().equals("Springfield"));
        assertTrue(createdUser.getAddress().getState().equals("IL"));
        assertTrue(createdUser.getAddress().getCountry().equals("USA"));
    }


    @Test
    @Order(2)
    public void testCreateUserWithInvalidOrigin() {

        mockUser();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_INVALID)
                .setBasePath("/v1/users")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    public void testFindById() throws IOException {

        mockUser();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/v1/users")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", user.getKey())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        UserDTO persistedUser = objectMapper.readValue(content, UserDTO.class);
        user = persistedUser;

        assertNotNull(persistedUser);
        assertNotNull(persistedUser.getKey());
        assertNotNull(persistedUser.getName());
        assertNotNull(persistedUser.getEmail());
        assertNotNull(persistedUser.getAddress());
//        assertNotNull(createdUser.getPassword());

        assertTrue(persistedUser.getKey() > 0);

        assertTrue(persistedUser.getName().equals("Waltim"));
        assertTrue(persistedUser.getEmail().equals("<EMAIL>"));
//        assertTrue(createdUser.getPassword().equals("<PASSWORD>"));
        assertTrue(persistedUser.getAddress().getStreet().equals("Main Street"));
        assertTrue(persistedUser.getAddress().getNumber().equals("123"));
        assertTrue(persistedUser.getAddress().getComplement().equals("Apt 4B"));
        assertTrue(persistedUser.getAddress().getCity().equals("Springfield"));
        assertTrue(persistedUser.getAddress().getState().equals("IL"));
        assertTrue(persistedUser.getAddress().getCountry().equals("USA"));
    }

    @Test
    @Order(4)
    public void testFindByIdWithInvalidOrigin() {

        mockUser();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_INVALID)
                .setBasePath("/v1/users")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", user.getKey())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    private void mockUser() {

        user.setName("Waltim");
        user.setEmail("<EMAIL>");
        user.setPassword("<PASSWORD>");
        user.setAddress(new Address("Main Street", "123", "Apt 4B", "Springfield", "IL", "USA"));
    }
}
