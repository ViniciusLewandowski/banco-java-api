package com.vinilewa.banco.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaCorrente extends Conta {

    private BigDecimal limite = BigDecimal.ZERO;

    @Override
    public void sacar(BigDecimal valor) {

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque inválido.");
        }

        BigDecimal saldoDisponivel = getSaldo().add(limite);

        if (valor.compareTo(saldoDisponivel) > 0) {
            throw new IllegalArgumentException("Saldo + limite insuficiente.");
        }

        if (valor.compareTo(getSaldo()) <= 0) {
            super.sacar(valor);
        } else {
            BigDecimal restante = valor.subtract(getSaldo());
            super.sacar(getSaldo());
            limite = limite.subtract(restante);
        }
    }
}