package com.payconiq.inerview.stockservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.payconiq.inerview.stockservice.validation.NameValidation;
import com.payconiq.inerview.stockservice.validation.PriceValidation;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class StockVO {

    @NotEmpty(groups = NameValidation.class)
    String name;

    @DecimalMin(value = "0.01", groups = PriceValidation.class)
    @NotNull(groups = PriceValidation.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigDecimal price;
    LocalDateTime lastUpdated;
}
