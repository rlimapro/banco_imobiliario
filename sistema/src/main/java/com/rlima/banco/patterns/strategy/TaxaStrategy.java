package com.rlima.banco.patterns.strategy;

import com.rlima.banco.domain.model.tabuleiro.Logradouro;

public interface TaxaStrategy {
    double calcularTaxa(int dados, Logradouro logradouro);
}
