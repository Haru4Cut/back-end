package com.haru4cut.purchase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name="goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @Column(name = "pencils")
    private int pencil;

    @Column(name = "price")
    private BigDecimal price;


}
