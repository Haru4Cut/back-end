package com.haru4cut.purchase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class Goods {
    @Id
    @Column(name = "goodsId")
    private Long id;

    @Column(name = "pencils")
    private int pencil;

    @Column(name = "price")
    private BigDecimal price;


}
