package com.luizfernandotech.hrpayroll.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class Worker implements Serializable {

    private static final long serialVersionUID = 4438733389461471757L;

    private Long id;
    private String name;
    private Double dailyIncome;
}
