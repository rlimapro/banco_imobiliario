package com.rlima.banco.domain.model.tabuleiro;

import com.rlima.banco.domain.model.Jogador;
import com.rlima.banco.patterns.strategy.TaxaFixaStrategy;
import com.rlima.banco.patterns.strategy.TaxaStrategy;
import com.rlima.banco.patterns.strategy.TaxaVariavelStrategy;

public class Empresa implements Logradouro {
    private final String nome;
    private final double precoCompra;
    private TaxaStrategy taxaStrategy;
    private Jogador proprietario;
    private final double taxaUso;

    public Empresa(String nome, double precoCompra, boolean taxaVariavel, double taxaUso) {
        this.nome = nome;
        this.precoCompra = precoCompra;
        this.taxaUso = taxaUso;

        if (taxaVariavel) {
            this.taxaStrategy = new TaxaVariavelStrategy();
        } else {
            this.taxaStrategy = new TaxaFixaStrategy();
        }
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getTaxa() {
        return taxaUso;
    }

    @Override
    public double calcularTaxa(int dados) {
        return taxaStrategy.calcularTaxa(dados, this);
    }

    @Override
    public double getPrecoCompra() {
        return precoCompra;
    }

    @Override
    public Jogador getProprietario() {
        return proprietario;
    }

    @Override
    public void setProprietario(Jogador jogador) {
        this.proprietario = jogador;
    }
}


