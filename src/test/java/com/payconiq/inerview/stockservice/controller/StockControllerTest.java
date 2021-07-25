package com.payconiq.inerview.stockservice.controller;

import com.payconiq.inerview.stockservice.StockServiceApplication;
import com.payconiq.inerview.stockservice.domain.StockEntity;
import com.payconiq.inerview.stockservice.repository.StockRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;


@SpringBootTest(classes = StockServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StockRepository repository;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        cleanStockRepository();
    }

    @Test
    void getAll_should_returnAllExistingStocks() {
        addTestStock(StockEntity.builder().name("test_name").price(BigDecimal.TEN).lastUpdated(LocalDateTime.now()).build());
        addTestStock(StockEntity.builder().name("test_name1").price(BigDecimal.ONE).lastUpdated(LocalDateTime.now()).build());

        when().get("/api/stocks/")
                .then()
                .statusCode(200)
                .body("$.size()", is(2));
    }

    @Test
    void get_when_exists_should_returnCorrespondingStock() {
        StockEntity testStock = addTestStock(StockEntity.builder()
                .name("test_name")
                .price(new BigDecimal("20.22"))
                .lastUpdated(LocalDateTime.now())
                .build());
        when().
                get("/api/stocks/" + testStock.getId()).
                then().
                statusCode(200)
                .body("price", equalTo(testStock.getPrice().toString()))
                .body("name", equalTo(testStock.getName()));
    }

    @Test
    void get_when_invalidStockId_should_returnBadRequest() {
        when().get("/api/stocks/" + "203")
                .then()
                .statusCode(400);
    }

    @Test
    void add_whenStockIsValid_should_returnOkAndLocationHeader() {
        given().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\" :\"sticker\",\n" +
                        "    \"price\":\"12.05\"\n" +
                        "  }")
                .when()
                .post("/api/stocks/")
                .then()
                .statusCode(201)
                .header("location", notNullValue());
    }

    @Test
    void add_when_stockHasNoName_should_returnBadRequest() {
        given().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"price\":\"12.05\"\n" +
                        "  }")
                .when()
                .post("/api/stocks/")
                .then()
                .statusCode(400);
    }

    @Test
    void add_when_stockNoPrice_should_returnBadRequest() {
        given().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\" :\"sticker\"\n" +
                        "  }")
                .when()
                .post("/api/stocks/")
                .then()
                .statusCode(400);
    }

    @Test
    void updatePrice_when_isValidPrice_should_returnStockWithUpdatedPrice() {
        StockEntity testStock = addTestStock(StockEntity.builder()
                .name("test_name")
                .price(new BigDecimal("20.22"))
                .lastUpdated(LocalDateTime.now())
                .build());

        given().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"price\":\"12.05\"\n" +
                        "  }")
                .when()
                .put("/api/stocks/" + testStock.getId())
                .then()
                .statusCode(200)
                .body("price", equalTo("12.05"));
    }

    @Test
    void updatePrice_when_isInvalidPrice_should_returnBadRequest() {
        StockEntity testStock = addTestStock(StockEntity.builder()
                .name("test_name")
                .price(new BigDecimal("20.22"))
                .lastUpdated(LocalDateTime.now())
                .build());

        given().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"price\":\"-12.05\"\n" +
                        "  }")
                .when()
                .put("/api/stocks/" + testStock.getId())
                .then()
                .statusCode(400);
    }


    private StockEntity addTestStock(StockEntity stock) {
        return repository.save(stock);
    }

    private void cleanStockRepository() {
        repository.deleteAll();
    }
}
