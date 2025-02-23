package com.rlima.banco.domain.model;

import com.rlima.banco.domain.model.tabuleiro.Logradouro;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private double saldo;
    private int posicao;
    private List<Logradouro> propriedades = new ArrayList<>();
    private List<String> caminhoPercorrido = new ArrayList<>();
    private boolean primeiroTurno = true;
    private boolean vencedor = false;

    public Jogador(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
        this.posicao = 0;
    }

    public void modificarSaldo(double valor) {
        this.saldo += valor;

        // Se o saldo for negativo, o jogador perde o jogo
        if (this.saldo < 0) {
            this.saldo = 0; // Evita saldo negativo na exibição
            System.out.println(nome + " ficou sem saldo e foi eliminado do jogo!");
        }
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public List<Logradouro> getPropriedades() {
        return propriedades;
    }

    public boolean isPrimeiroTurno() {
        return primeiroTurno;
    }

    public void setPrimeiroTurno(boolean primeiroTurno) {
        this.primeiroTurno = primeiroTurno;
    }

    public void limparEstadoJogada() {
        // Limpa o caminho percorrido ao trocar de turno
        caminhoPercorrido.clear();
    }

    public void setVencedor(boolean vencedor) {
        this.vencedor = true;
    }

    public boolean isVencedor() {
        return vencedor;
    }

    public List<String> getCaminhoPercorrido() {
        return caminhoPercorrido;
    }
}
