package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class TipoCambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "moneda_base")
    private String monedaBase;

    @Column(nullable = false, name = "moneda_destino")
    private String monedaDestino;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal valor;
}