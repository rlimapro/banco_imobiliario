package com.rlima.banco.patterns.strategy;

import com.rlima.banco.domain.model.tabuleiro.Logradouro;

public class TaxaFixaStrategy implements TaxaStrategy {
    @Override
    public double calcularTaxa(int dados, Logradouro logradouro) {
        return logradouro.getTaxa();
    }
}
