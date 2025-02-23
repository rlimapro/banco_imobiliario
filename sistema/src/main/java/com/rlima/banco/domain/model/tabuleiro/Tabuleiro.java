package com.rlima.banco.domain.model.tabuleiro;


import com.rlima.banco.patterns.factory.LogradouroFactory;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private static Tabuleiro instancia;
    private final List<Logradouro> casas = new ArrayList<>();

    private Tabuleiro() {
        inicializarTabuleiro();
    }

    public static Tabuleiro getInstance() {
        if (instancia == null) {
            instancia = new Tabuleiro();
        }
        return instancia;
    }

    private void inicializarTabuleiro() {
        casas.add(LogradouroFactory.criarLogradouro("LugarEspecial", "Ponto de Partida", 0, 200, false, 0));
        casas.add(LogradouroFactory.criarLogradouro("Imovel", "Casa Azul", 500, 400, false, 0));
        casas.add(LogradouroFactory.criarLogradouro("Empresa", "Loja Tech", 800, 0, true, 350));
        casas.add(LogradouroFactory.criarLogradouro("LugarEspecial", "Imposto", 0, -950, false, 0));
        casas.add(LogradouroFactory.criarLogradouro("Imovel", "Mansão Verde", 1200, 950, false, 0));
        casas.add(LogradouroFactory.criarLogradouro("Empresa", "Cafeteria Express", 700, 0, true, 395));
        casas.add(LogradouroFactory.criarLogradouro("LugarEspecial", "Sorte ou Revés", 0, 0, false, 0));
        casas.add(LogradouroFactory.criarLogradouro("Imovel", "Apartamento Luxo", 1500, 500, false, 0));
        casas.add(LogradouroFactory.criarLogradouro("Empresa", "Academia Fitness", 900, 0, true, 400));
        casas.add(LogradouroFactory.criarLogradouro("LugarEspecial", "Taxa de Manutenção", 0, -900, false, 0));
        casas.add(LogradouroFactory.criarLogradouro("Imovel", "Casa Amarela", 600, 400, false, 0));
        casas.add(LogradouroFactory.criarLogradouro("Empresa", "Supermercado Mega", 1000, 0, true, 820));
        casas.add(LogradouroFactory.criarLogradouro("LugarEspecial", "Lucro Surpresa", 0, 100, false, 0));
    }

    public List<Logradouro> getCasas() {
        return casas;
    }
}

