package com.luizfernandotech.hruser.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "tb_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -8994794021008906758L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
