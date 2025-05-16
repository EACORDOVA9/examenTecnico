package com.example.demo.repository;

import com.example.demo.model.TipoCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TipoCambioRepository extends JpaRepository<TipoCambio, Long> {
    Optional<TipoCambio> findByMonedaBaseAndMonedaDestino(String monedaBase, String monedaDestino);
}