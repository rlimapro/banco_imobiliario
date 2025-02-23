package com.rlima.banco.patterns.builder;

import com.rlima.banco.domain.model.tabuleiro.Empresa;
import com.rlima.banco.domain.model.tabuleiro.Imovel;
import com.rlima.banco.domain.model.tabuleiro.Logradouro;
import com.rlima.banco.domain.model.tabuleiro.LugarEspecial;

public class LogradouroBuilder {
    private String nome;
    private double preco;
    private double taxa;
    private boolean taxaVariavel;
    private double taxaUso;
    private String tipoEfeito;
    private double valorEfeito;

    public LogradouroBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public LogradouroBuilder setPreco(double preco) {
        this.preco = preco;
        return this;
    }

    public LogradouroBuilder setTaxa(double taxa) {
        this.taxa = taxa;
        return this;
    }

    public LogradouroBuilder setTaxaVariavel(boolean taxaVariavel) {
        this.taxaVariavel = taxaVariavel;
        return this;
    }

    public LogradouroBuilder setTaxaUso(double taxaUso) {
        this.taxaUso = taxaUso;
        return this;
    }

    public LogradouroBuilder setTipoEfeito(String tipoEfeito) {
        this.tipoEfeito = tipoEfeito;
        return this;
    }

    public LogradouroBuilder setValorEfeito(double valorEfeito) {
        this.valorEfeito = valorEfeito;
        return this;
    }

    public Logradouro build(String tipo) {
        return switch (tipo) {
            case "Imovel" -> new Imovel(nome, preco, taxa);
            case "Empresa" -> new Empresa(nome, preco, taxaVariavel, taxaUso);
            case "LugarEspecial" -> new LugarEspecial(nome, tipoEfeito, valorEfeito);
            default -> throw new IllegalArgumentException("Tipo inválido");
        };
    }
}
