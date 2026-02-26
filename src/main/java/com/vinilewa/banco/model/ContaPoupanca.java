package com.vinilewa.banco.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public class ContaPoupanca extends Conta {

    @Override
    public void sacar(BigDecimal valor) {
        super.sacar(valor);
    }

    @Override
    public void transferir(Conta contaDestino, BigDecimal valor) {
        throw new UnsupportedOperationException(
                "Conta Poupança não permite transferências.");
    }
}