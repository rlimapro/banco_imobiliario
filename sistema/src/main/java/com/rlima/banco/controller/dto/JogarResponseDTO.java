package com.rlima.banco.controller.dto;

import java.util.List;

public class JogarResponseDTO {
    private String nomeCasaFinal;
    private int indiceCasa;
    private List<String> caminhoPercorrido;
    private boolean passouPeloPonto;
    private List<String> mensagensDeAlteracaoDeSaldo;
    private boolean vencedor; // Novo campo
    private String nomeJogador;

    public JogarResponseDTO(String nomeCasaFinal, int indiceCasa, List<String> caminhoPercorrido, boolean passouPeloPonto, List<String> mensagensDeAlteracaoDeSaldo, boolean vencedor, String nomeJogador) {
        this.nomeCasaFinal = nomeCasaFinal;
        this.indiceCasa = indiceCasa;
        this.caminhoPercorrido = caminhoPercorrido;
        this.passouPeloPonto = passouPeloPonto;
        this.mensagensDeAlteracaoDeSaldo = mensagensDeAlteracaoDeSaldo;
        this.vencedor = vencedor;
        this.nomeJogador = nomeJogador;
    }

    public List<String> getMensagensDeAlteracaoDeSaldo() {
        return mensagensDeAlteracaoDeSaldo;
    }

    public String getNomeCasaFinal() {
        return nomeCasaFinal;
    }

    public List<String> getCaminhoPercorrido() {
        return caminhoPercorrido;
    }

    public boolean isPassouPeloPonto() {
        return passouPeloPonto;
    }

    public int getIndiceCasa() {
        return indiceCasa;
    }

    public boolean isVencedor() {
        return vencedor;
    }

    public void setVencedor(boolean vencedor) {
        this.vencedor = vencedor;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }
}
