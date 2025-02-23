package com.rlima.banco.patterns.factory;

import com.rlima.banco.domain.model.tabuleiro.Logradouro;
import com.rlima.banco.patterns.builder.LogradouroBuilder;

public class LogradouroFactory {
    public static Logradouro criarLogradouro(String tipo, String nome, double preco, double taxa, boolean taxaVariavel, double taxaUso) {
        LogradouroBuilder builder = new LogradouroBuilder().setNome(nome).setPreco(preco).setTaxa(taxa).setTaxaVariavel(taxaVariavel).setTaxaUso(taxaUso);

        if ("LugarEspecial".equals(tipo)) {
            String tipoEfeito;
            double valorEfeito;

            switch (nome) {
                case "Ponto de Partida":
                    tipoEfeito = "mudarPosicao";
                    valorEfeito = 0;
                    break;
                case "Imposto", "Lucro Surpresa", "Taxa de Manutenção":
                    tipoEfeito = "alterarSaldo";
                    valorEfeito = taxa;
                    break;
                case "Sorte ou Revés":
                    tipoEfeito = "alterarSaldo";
                    valorEfeito = (Math.random() > 0.75) ? 200 : -200;
                    break;
                default:
                    tipoEfeito = "alterarSaldo";
                    valorEfeito = 0;
                    break;
            }
            builder.setTipoEfeito(tipoEfeito).setValorEfeito(valorEfeito);
        }

        return builder.build(tipo);
    }
}

