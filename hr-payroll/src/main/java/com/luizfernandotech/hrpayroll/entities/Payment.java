package com.luizfernandotech.hrpayroll.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;



@Data
@AllArgsConstructor
public class Payment implements Serializable {

    private static final long serialVersionUID = 8967717441023190799L;

    private String name;
    private Double dailyIncome;
    private Integer days;

    public Double getTotal() {

        return days * dailyIncome;
    }
}
