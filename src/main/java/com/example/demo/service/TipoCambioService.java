package com.example.demo.service;

import com.example.demo.model.TipoCambio;
import com.example.demo.repository.TipoCambioRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TipoCambioService {

    private final TipoCambioRepository tipoCambioRepository;

    public TipoCambioService(TipoCambioRepository tipoCambioRepository) {
        this.tipoCambioRepository = tipoCambioRepository;
    }

    public List<TipoCambio> obtenerTodos() {
        return tipoCambioRepository.findAll();
    }

    public Optional<TipoCambio> obtenerPorMonedas(String monedaBase, String monedaDestino) {
        return tipoCambioRepository.findByMonedaBaseAndMonedaDestino(monedaBase, monedaDestino);
    }

    public TipoCambio crearTipoCambio(TipoCambio tipoCambio) {
        return tipoCambioRepository.save(tipoCambio);
    }

    public Optional<TipoCambio> actualizarTipoCambio(Long id, TipoCambio nuevoTipoCambio) {
        return tipoCambioRepository.findById(id)
                .map(tipoCambio -> {
                    tipoCambio.setMonedaBase(nuevoTipoCambio.getMonedaBase());
                    tipoCambio.setMonedaDestino(nuevoTipoCambio.getMonedaDestino());
                    tipoCambio.setValor(nuevoTipoCambio.getValor());
                    return tipoCambioRepository.save(tipoCambio);
                });
    }

    public void eliminarTipoCambio(Long id) {
        tipoCambioRepository.deleteById(id);
    }

    public BigDecimal convertir(String monedaOrigen, String monedaDestino, BigDecimal monto) {
        Optional<TipoCambio> tipoCambioOptional = tipoCambioRepository.findByMonedaBaseAndMonedaDestino(monedaOrigen, monedaDestino);
        return tipoCambioOptional.map(tipoCambio -> monto.multiply(tipoCambio.getValor()))
                .orElseThrow(() -> new RuntimeException("Tipo de cambio no encontrado para " + monedaOrigen + " a " + monedaDestino));
    }
}