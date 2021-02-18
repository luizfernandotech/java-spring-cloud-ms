package com.luizfernandotech.hroauth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = -8994794021008906758L;

    private Long id;
    private String roleName;
}
