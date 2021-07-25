package com.payconiq.inerview.stockservice.controller;

import com.payconiq.inerview.stockservice.domain.StockVO;
import com.payconiq.inerview.stockservice.service.StockService;
import com.payconiq.inerview.stockservice.validation.NameValidation;
import com.payconiq.inerview.stockservice.validation.PriceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockController {

    public static final String STOCKS_URL = "/api/stocks/";

    private final StockService stockService;

    @GetMapping("/stocks/{id}")
    public ResponseEntity<StockVO> getById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockService.getById(id));
    }

    @GetMapping("/stocks/")
    public ResponseEntity<List<StockVO>> findAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockService.findAll());
    }

    @PostMapping("/stocks/")
    public ResponseEntity add(@RequestBody @Validated({NameValidation.class, PriceValidation.class}) StockVO stock) {
        return ResponseEntity.created(URI.create(STOCKS_URL + stockService.add(stock)))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<StockVO> updatePrice(@PathVariable Long id,
                                               @RequestBody @Validated({PriceValidation.class}) StockVO stock) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockService.updatePrice(id, stock.getPrice()));
    }
}
