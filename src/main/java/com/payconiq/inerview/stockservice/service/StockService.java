package com.payconiq.inerview.stockservice.service;

import com.payconiq.inerview.stockservice.domain.StockEntity;
import com.payconiq.inerview.stockservice.domain.StockMapper;
import com.payconiq.inerview.stockservice.domain.StockVO;
import com.payconiq.inerview.stockservice.exception.StockNotFoundException;
import com.payconiq.inerview.stockservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.payconiq.inerview.stockservice.domain.StockMapper.toEntity;
import static com.payconiq.inerview.stockservice.domain.StockMapper.toVO;

@Service
@AllArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Long add(StockVO newStock) {
        StockEntity stock = toEntity(newStock);
        stock.setLastUpdated(LocalDateTime.now());

        return stockRepository.save(stock).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public StockVO getById(Long stockId) {
        return toVO(getStockById(stockId));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public List<StockVO> findAll() {
        return StreamSupport.stream(stockRepository.findAll().spliterator(), false)
                .map(StockMapper::toVO)
                .collect(Collectors.toList());

    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public StockVO updatePrice(Long stockId, BigDecimal updatedPrice) {
        StockEntity currentStock = getStockById(stockId);
        currentStock.setPrice(updatedPrice);
        currentStock.setLastUpdated(LocalDateTime.now());

        return toVO(stockRepository.save(currentStock));
    }

    private StockEntity getStockById(Long stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException("Stock id = " + stockId + " does not found."));
    }
}
