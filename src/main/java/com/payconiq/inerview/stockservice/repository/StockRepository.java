package com.payconiq.inerview.stockservice.repository;

import com.payconiq.inerview.stockservice.domain.StockEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<StockEntity, Long> {

}
