package com.api.presentation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Transactional
    @Test
    void registerProduct() {
        String date = "2024-01-01T00:00:00.000+09:00";

        Map<String, Object> request = new HashMap<>();
        request.put("name","nameTest");
        request.put("minimumAmount",111);
        request.put("auctionPeriod",date);
        request.put("sellerId", 1);

        ExtractableResponse<Response> responseBody =
                given().
                        accept(ContentType.JSON).
                        contentType(ContentType.JSON).
                        body(request).
                        when().
                        post("/products")
                        .then().log().all()
                .extract();

        assertThat(responseBody.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
