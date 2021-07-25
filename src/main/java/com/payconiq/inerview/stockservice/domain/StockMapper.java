package com.payconiq.inerview.stockservice.domain;

public class StockMapper {

    public static StockVO toVO(StockEntity entity){
        return new StockVO(entity.getName(), entity.getPrice(), entity.getLastUpdated());
    }

    public static StockEntity toEntity(StockVO vo){
        return StockEntity.builder()
                .name(vo.getName())
                .price(vo.getPrice())
                .lastUpdated(vo.getLastUpdated())
                .build();
    }
}
