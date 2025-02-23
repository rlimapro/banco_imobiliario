package com.rlima.banco.controller.dto;

import java.util.List;

public class JogadorPortfoliosDTO {
    private double saldo;
    private List<String> propriedades;

    public JogadorPortfoliosDTO(double saldo, List<String> propriedades) {
        this.saldo = saldo;
        this.propriedades = propriedades;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<String> getPropriedades() {
        return propriedades;
    }
}
