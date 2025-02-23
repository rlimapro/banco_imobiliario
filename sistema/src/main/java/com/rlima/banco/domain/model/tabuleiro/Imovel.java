package com.rlima.banco.domain.model.tabuleiro;

import com.rlima.banco.domain.model.Jogador;

public class Imovel implements Logradouro {
    private final String nome;
    private final double precoCompra;
    private final double taxaAluguel;
    private Jogador proprietario;

    public Imovel(String nome, double precoCompra, double taxaAluguel) {
        this.nome = nome;
        this.precoCompra = precoCompra;
        this.taxaAluguel = taxaAluguel;
    }

    @Override
    public double calcularTaxa(int dados) {
        return proprietario == null ? 0 : taxaAluguel;
    }

    @Override
    public void setProprietario(Jogador jogador) {
        this.proprietario = jogador;
    }

    @Override
    public double getTaxa() {
        return taxaAluguel;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getPrecoCompra() {
        return precoCompra;
    }

    public double getTaxaAluguel() {
        return taxaAluguel;
    }

    @Override
    public Jogador getProprietario() {
        return proprietario;
    }
}
