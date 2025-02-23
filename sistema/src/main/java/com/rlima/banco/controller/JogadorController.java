package com.rlima.banco.controller;

import com.rlima.banco.controller.dto.JogadorDTO;
import com.rlima.banco.controller.dto.JogadorPortfoliosDTO;
import com.rlima.banco.controller.dto.JogarResponseDTO;
import com.rlima.banco.domain.model.Jogador;
import com.rlima.banco.domain.model.tabuleiro.Logradouro;
import com.rlima.banco.domain.model.tabuleiro.Tabuleiro;
import com.rlima.banco.service.JogadorService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jogador")
@CrossOrigin(origins = "*")
public class JogadorController {
    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @GetMapping("/atual")
    public Jogador getJogadorAtual() {
        return jogadorService.getJogadorAtual();
    }

    @GetMapping("/tabuleiro")
    public Tabuleiro getTabuleiro() {
        return jogadorService.getTabuleiro();
    }

    @PostMapping("/jogar")
    public JogarResponseDTO jogar(@RequestParam int dado1, @RequestParam int dado2) {
        return jogadorService.jogar(dado1, dado2);
    }

    @GetMapping("/todos")
    public List<JogadorDTO> getTodosJogadores() {
        return jogadorService.getTodosJogadores().stream()
                .map(jogador -> new JogadorDTO(jogador.getNome(), jogador.getSaldo()))
                .collect(Collectors.toList());
    }

    @PostMapping("/trocarTurno")
    public Map<String, String> trocarTurno() {
        jogadorService.trocarTurno();
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Turno de " + jogadorService.getJogadorAtual().getNome());
        response.put("jogadorAtual", jogadorService.getJogadorAtual().getNome());
        return response;
    }

    @PostMapping("/comprarLogradouro")
    public String comprarLogradouro(@RequestParam int casaIndex) {
        // Recupera o logradouro da casa em que o jogador caiu
        Logradouro logradouro = jogadorService.getTabuleiro().getCasas().get(casaIndex);

        // Verifica se o logradouro está disponível para compra (sem proprietário)
        if (logradouro.getProprietario() == null) {
            return jogadorService.comprarLogradouro(logradouro);
        } else {
            return "Este logradouro já foi comprado.";
        }
    }

    @GetMapping("/posicao")
    public Map<String, Object> getPosicao() {
        Jogador jogador = jogadorService.getJogadorAtual();
        int posicaoAtual = jogador.getPosicao();

        // Recupera a casa correspondente à posição do jogador no tabuleiro
        Logradouro casaAtual = jogadorService.getTabuleiro().getCasas().get(posicaoAtual);

        // Retorna a casa e o índice
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("indiceCasa", posicaoAtual);
        resposta.put("nomeCasa", casaAtual.getNome());

        return resposta;
    }

    // Endpoint para consultar o portfólio do jogador atual
    @GetMapping("/portfolio")
    public JogadorPortfoliosDTO obterPortfolio() {
        return jogadorService.obterPortfolioJogadorAtual();
    }
}
