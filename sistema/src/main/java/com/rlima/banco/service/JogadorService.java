package com.rlima.banco.service;

import com.rlima.banco.controller.dto.JogadorPortfoliosDTO;
import com.rlima.banco.controller.dto.JogarResponseDTO;
import com.rlima.banco.domain.model.Jogador;
import com.rlima.banco.domain.model.tabuleiro.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JogadorService {
    private final Jogador jogador1 = new Jogador("Jogador 1", 1000);
    private final Jogador jogador2 = new Jogador("Jogador 2", 1000);
    private final Tabuleiro tabuleiro = Tabuleiro.getInstance();
    private Jogador jogadorAtual = jogador1;

    public void trocarTurno() {
        jogadorAtual.limparEstadoJogada();
        jogadorAtual = (jogadorAtual == jogador1) ? jogador2 : jogador1;
    }

    public JogarResponseDTO jogar(int dado1, int dado2) {
        Jogador jogador = jogadorAtual;
        int posicaoAtual = jogador.getPosicao();
        int tamanhoTabuleiro = tabuleiro.getCasas().size();

        int posicaoAntesModulo = posicaoAtual + dado1 + dado2;
        int novaPosicao = posicaoAntesModulo % tamanhoTabuleiro;

        // Construir o caminho percorrido
        List<String> caminhoPercorrido = new ArrayList<>();
        for (int i = 1; i <= dado1 + dado2; i++) {
            int posicaoIntermediaria = (posicaoAtual + i) % tamanhoTabuleiro;
            caminhoPercorrido.add(tabuleiro.getCasas().get(posicaoIntermediaria).getNome());
        }

        jogador.setPosicao(novaPosicao);

        // Recupera o logradouro da nova posição
        Logradouro casa = tabuleiro.getCasas().get(novaPosicao);

        boolean passouPeloPonto = posicaoAntesModulo >= tamanhoTabuleiro;
        List<String> mensagensDeAlteracaoDeSaldo = new ArrayList<>();

        if (passouPeloPonto) {
            jogador.modificarSaldo(200);
            mensagensDeAlteracaoDeSaldo.add("Você passou pelo Ponto de Partida e ganhou 200 de saldo!");
        }

        // Verificar se o jogador tem saldo suficiente para pagar o aluguel
        if (casa.getProprietario() != null && casa.getProprietario() != jogador) {
            double taxa = casa.getTaxa();
            if (jogador.getSaldo() >= taxa) {
                jogador.modificarSaldo(-taxa);
                casa.getProprietario().modificarSaldo(taxa);
                mensagensDeAlteracaoDeSaldo.add("Você pagou " + taxa + " de aluguel para " + casa.getProprietario().getNome());
            } else {
                jogador.modificarSaldo(-taxa);
                casa.getProprietario().modificarSaldo(taxa);
                System.out.println(jogador.getNome() + " ficou com saldo menor ou igual a zero após pagar " + taxa + " e foi eliminado!");
                mensagensDeAlteracaoDeSaldo.add("Você pagou " + taxa + " de aluguel para " + casa.getProprietario().getNome());
                mensagensDeAlteracaoDeSaldo.add(jogador.getNome() + " ficou com saldo menor ou igual a zero após pagar " + taxa + " e foi eliminado!");
                return new JogarResponseDTO(casa.getNome(), novaPosicao, caminhoPercorrido, false, mensagensDeAlteracaoDeSaldo, true, jogador.getNome());
            }
        }

        // Se for um Lugar Especial com efeito negativo
        if (casa instanceof LugarEspecial) {
            LugarEspecial lugarEspecial = (LugarEspecial) casa;
            double efeito = lugarEspecial.getValorEfeito();

            if (efeito < 0 && jogador.getSaldo() < Math.abs(efeito)) {
                lugarEspecial.aplicarEfeito(jogador);
                System.out.println(jogador.getNome() + " ficou com saldo menor ou igual a zero após pagar " + efeito + " e foi eliminado!");
                mensagensDeAlteracaoDeSaldo.add("Você foi afetado por um Lugar Especial com efeito: " + efeito + " de saldo!");
                mensagensDeAlteracaoDeSaldo.add(jogador.getNome() + " ficou com saldo menor ou igual a zero após pagar " + efeito + " e foi eliminado!");
                return new JogarResponseDTO(casa.getNome(), novaPosicao, caminhoPercorrido, false, mensagensDeAlteracaoDeSaldo, true, jogador.getNome());
            }

            lugarEspecial.aplicarEfeito(jogador);
            mensagensDeAlteracaoDeSaldo.add("Você foi afetado por um Lugar Especial com efeito: " + efeito + " de saldo!");
        }

        return new JogarResponseDTO(casa.getNome(), novaPosicao, caminhoPercorrido, passouPeloPonto, mensagensDeAlteracaoDeSaldo, false, jogador.getNome());
    }


    public String comprarLogradouro(Logradouro logradouro) {
        Jogador jogador = jogadorAtual;

        // Verifica se o logradouro pode ser comprado (se não for um LugarEspecial)
        if (!(logradouro instanceof Imovel) && !(logradouro instanceof Empresa)) {
            return "Este local não pode ser comprado.";
        }

        if (jogador.getSaldo() >= logradouro.getPrecoCompra()) {
            jogador.setSaldo(jogador.getSaldo() - logradouro.getPrecoCompra());
            jogador.getPropriedades().add(logradouro);
            logradouro.setProprietario(jogador);
            return "Compra realizada com sucesso! " + logradouro.getNome() + " agora é seu.";
        } else {
            return "Saldo insuficiente para comprar " + logradouro.getNome() + ".";
        }
    }

    public JogadorPortfoliosDTO obterPortfolio(Jogador jogador) {
        double saldo = jogador.getSaldo();
        List<String> propriedades = jogador.getPropriedades().stream()
                .map(Logradouro::getNome)
                .collect(Collectors.toList());

        return new JogadorPortfoliosDTO(saldo, propriedades);
    }

    public JogadorPortfoliosDTO obterPortfolioJogadorAtual() {
        return obterPortfolio(jogadorAtual);
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public List<Jogador> getTodosJogadores() {
        return Arrays.asList(jogador1, jogador2);
    }
}
