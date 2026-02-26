package com.vinilewa.banco.service;

import com.vinilewa.banco.model.*;
import com.vinilewa.banco.repository.ClienteRepository;
import com.vinilewa.banco.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository,
                        ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    public ContaCorrente criarContaCorrente(Long clienteId, BigDecimal limite) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        ContaCorrente conta = new ContaCorrente();
        conta.setCliente(cliente);
        conta.setNumeroDaConta("CC-" + System.currentTimeMillis());
        conta.setLimite(limite);
        conta.setSaldo(BigDecimal.ZERO);
        conta.setSenha("1234");

        return contaRepository.save(conta);
    }

    public ContaPoupanca criarContaPoupanca(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        ContaPoupanca conta = new ContaPoupanca();
        conta.setCliente(cliente);
        conta.setNumeroDaConta("CP-" + System.currentTimeMillis());
        conta.setSaldo(BigDecimal.ZERO);
        conta.setSenha("1234");

        return contaRepository.save(conta);
    }

    public Conta depositar(Long contaId, BigDecimal valor) {

        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.depositar(valor);
        return contaRepository.save(conta);
    }

    public Conta sacar(Long contaId, BigDecimal valor) {

        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.sacar(valor);
        return contaRepository.save(conta);
    }

    public void transferir(Long origemId, Long destinoId, BigDecimal valor) {

        Conta origem = contaRepository.findById(origemId)
                .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

        Conta destino = contaRepository.findById(destinoId)
                .orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));

        origem.transferir(destino, valor);

        contaRepository.save(origem);
        contaRepository.save(destino);
    }
}