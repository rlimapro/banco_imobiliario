package com.rlima.banco.controller.dto;

public class JogadorDTO {
    private String nome;
    private double saldo;

    public JogadorDTO(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }
}


