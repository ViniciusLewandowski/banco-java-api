package com.vinilewa.banco.repository;

import com.vinilewa.banco.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByClienteId(Long clienteId);
}