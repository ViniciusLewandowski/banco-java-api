package com.vinilewa.banco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroDaConta;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private Cliente cliente;

    public void depositar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de depósito inválido.");
        }

        saldo = saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque inválido.");
        }

        if (saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        saldo = saldo.subtract(valor);
    }

    public void transferir(Conta destino, BigDecimal valor) {
        if (destino == null) {
            throw new IllegalArgumentException("Conta destino inválida.");
        }

        if (this.equals(destino)) {
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta.");
        }

        this.sacar(valor);
        destino.depositar(valor);
    }

    public boolean autenticar(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }
}