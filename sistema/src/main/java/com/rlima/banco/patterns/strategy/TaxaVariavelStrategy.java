package com.rlima.banco.patterns.strategy;

import com.rlima.banco.domain.model.tabuleiro.Logradouro;

public class TaxaVariavelStrategy implements TaxaStrategy {
    private static final double MULTIPLICADOR_PADRAO = 10.0;

    @Override
    public double calcularTaxa(int dados, Logradouro logradouro) {
        double taxaBase = logradouro.getTaxa();
        return dados * (taxaBase > 0 ? taxaBase : MULTIPLICADOR_PADRAO);
    }
}
