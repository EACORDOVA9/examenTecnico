package com.example.demo.controller;

import com.example.demo.model.TipoCambio;
import com.example.demo.service.TipoCambioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-cambio")
public class TipoCambioController {

    private final TipoCambioService tipoCambioService;

    public TipoCambioController(TipoCambioService tipoCambioService) {
        this.tipoCambioService = tipoCambioService;
    }
    //Mapea las peticiones HTTP GET a los métodos.
    @GetMapping
    public ResponseEntity<List<TipoCambio>> obtenerTodos() {
        return ResponseEntity.ok(tipoCambioService.obtenerTodos());
    }

    @GetMapping("/{monedaBase}/{monedaDestino}")
    public ResponseEntity<TipoCambio> obtenerPorMonedas(@PathVariable String monedaBase, @PathVariable String monedaDestino) {
        Optional<TipoCambio> tipoCambio = tipoCambioService.obtenerPorMonedas(monedaBase, monedaDestino);
        return tipoCambio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //Mapea las peticiones HTTP POST a los métodos.
    @PostMapping
    public ResponseEntity<TipoCambio> crearTipoCambio(@RequestBody TipoCambio tipoCambio) {
        TipoCambio nuevoTipoCambio = tipoCambioService.crearTipoCambio(tipoCambio);
        return new ResponseEntity<>(nuevoTipoCambio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCambio> actualizarTipoCambio(@PathVariable Long id, @RequestBody TipoCambio tipoCambio) {
        Optional<TipoCambio> tipoCambioActualizado = tipoCambioService.actualizarTipoCambio(id, tipoCambio);
        return tipoCambioActualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //Mapea las peticiones HTTP DELETE a los métodos.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoCambio(@PathVariable Long id) {
        tipoCambioService.eliminarTipoCambio(id);
        return ResponseEntity.noContent().build();
    }
    //Mapea las peticiones HTTP POST a los métodos.
    @PostMapping("/convertir")
    public ResponseEntity<BigDecimal> convertir(@RequestParam String monedaOrigen,
                                                @RequestParam String monedaDestino,
                                                @RequestParam BigDecimal monto) {
        try {
            BigDecimal resultado = tipoCambioService.convertir(monedaOrigen, monedaDestino, monto);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}