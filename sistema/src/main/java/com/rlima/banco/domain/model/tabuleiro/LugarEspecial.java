package com.rlima.banco.domain.model.tabuleiro;

import com.rlima.banco.domain.model.Jogador;

public class LugarEspecial implements Logradouro {
    private final String nome;
    private final String tipoEfeito;  // "mudarPosicao" ou "alterarSaldo"
    private final double valorEfeito;
    private Jogador proprietario;

    public LugarEspecial(String nome, String tipoEfeito, double valorEfeito) {
        this.nome = nome;
        this.tipoEfeito = tipoEfeito;
        this.valorEfeito = valorEfeito;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getTaxa() {
        return 0;
    }

    @Override
    public double calcularTaxa(int dados) {
        return 0;
    }

    @Override
    public Jogador getProprietario() {
        return proprietario;
    }

    @Override
    public void setProprietario(Jogador jogador) {
        this.proprietario = jogador;
    }

    @Override
    public double getPrecoCompra() {
        return 0;
    }

    public void aplicarEfeito(Jogador jogador) {
        System.out.println("Aplicando efeito do lugar especial: " + nome + " - Tipo: " + tipoEfeito + " - Valor: " + valorEfeito);

        if ("mudarPosicao".equals(tipoEfeito)) {
            jogador.setPosicao((int) valorEfeito);  // Muda a posição do jogador
        } else if ("alterarSaldo".equals(tipoEfeito)) {
            jogador.modificarSaldo(valorEfeito);  // Altera o saldo do jogador
            System.out.println("Novo saldo do jogador: " + jogador.getSaldo());
        }
    }

    public double getValorEfeito() {
        return valorEfeito;
    }
}
