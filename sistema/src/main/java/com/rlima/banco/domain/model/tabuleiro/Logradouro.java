package com.rlima.banco.domain.model.tabuleiro;

import com.rlima.banco.domain.model.Jogador;

public interface Logradouro {
    String getNome();
    double getTaxa();
    double calcularTaxa(int dados);
    Jogador getProprietario();
    void setProprietario(Jogador jogador);
    double getPrecoCompra();
}
