package com.vinilewa.banco.controller;

import com.vinilewa.banco.model.*;
import com.vinilewa.banco.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/corrente/{clienteId}")
    public ResponseEntity<?> criarCorrente(@PathVariable Long clienteId,
                                           @RequestParam BigDecimal limite) {
        return ResponseEntity.ok(
                contaService.criarContaCorrente(clienteId, limite)
        );
    }

    @PostMapping("/poupanca/{clienteId}")
    public ResponseEntity<?> criarPoupanca(@PathVariable Long clienteId) {
        return ResponseEntity.ok(
                contaService.criarContaPoupanca(clienteId)
        );
    }

    @PostMapping("/{contaId}/deposito")
    public ResponseEntity<?> depositar(@PathVariable Long contaId,
                                       @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(
                contaService.depositar(contaId, valor)
        );
    }

    @PostMapping("/{contaId}/saque")
    public ResponseEntity<?> sacar(@PathVariable Long contaId,
                                   @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(
                contaService.sacar(contaId, valor)
        );
    }

    @PostMapping("/transferencia")
    public ResponseEntity<?> transferir(@RequestParam Long origemId,
                                        @RequestParam Long destinoId,
                                        @RequestParam BigDecimal valor) {
        contaService.transferir(origemId, destinoId, valor);
        return ResponseEntity.ok("Transferência realizada com sucesso");
    }
}