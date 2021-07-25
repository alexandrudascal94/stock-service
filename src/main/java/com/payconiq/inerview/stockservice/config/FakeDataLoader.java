package com.payconiq.inerview.stockservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.inerview.stockservice.domain.StockMapper;
import com.payconiq.inerview.stockservice.domain.StockVO;
import com.payconiq.inerview.stockservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
@Component
public class FakeDataLoader {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final StockRepository repository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void loadFakeData() throws IOException {
        InputStream stream = FakeDataLoader.class.getResourceAsStream("/static/stocks.json");
        List<StockVO> stocks = objectMapper.readValue(stream, new TypeReference<>() {
        });

        stocks.stream().map(StockMapper::toEntity).forEach(repository::save);

        log.info("Loaded " + stocks.size() + " stock items");
    }
}
