import React, { useState, useEffect } from 'react';
import "./App.css"

const App = () => {
    const [portfolio, setPortfolio] = useState(null);
    const [dados, setDados] = useState({ dado1: 0, dado2: 0 });
    const [posicao, setPosicao] = useState({ nomeCasa: "", indiceCasa: 0 });
    const [jogadorAtual, setJogadorAtual] = useState("");
    const [jogadores, setJogadores] = useState([]);
    const [caminhoPercorrido, setCaminhoPercorrido] = useState([]);
    const [passouPeloPonto, setPassouPeloPonto] = useState(false);
    const [mensagensDeAlteracaoDeSaldo, setMensagensDeAlteracaoDeSaldo] = useState([]);
    const [jogoAtivo, setJogoAtivo] = useState(() => {
        const jogoAtivoSalvo = localStorage.getItem("jogoAtivo");
        return jogoAtivoSalvo ? JSON.parse(jogoAtivoSalvo) : true;
    });
    const atualizarJogoAtivo = (estado) => {
        setJogoAtivo(estado);
        localStorage.setItem("jogoAtivo", JSON.stringify(estado));
    };

    const getJogadores = async () => {
        try {
            const response = await fetch('http://localhost:8080/jogador/todos');
            const data = await response.json();
            setJogadores(data);
        } catch (error) {
            console.error("Erro ao buscar os jogadores:", error);
        }
    };

    const getJogadorAtual = async () => {
        try {
            const response = await fetch('http://localhost:8080/jogador/atual');
            const data = await response.json();
            setJogadorAtual(data.nome);
        } catch (error) {
            console.error("Erro ao buscar jogador atual:", error);
        }
    };

    const trocarTurno = async () => {
        try {
            const response = await fetch('http://localhost:8080/jogador/trocarTurno', {
                method: 'POST',
            });
            const data = await response.json();
            alert(data.mensagem);
            setJogadorAtual(data.jogadorAtual);

            setPosicao({ nomeCasa: "", indiceCasa: 0 });
            setCaminhoPercorrido([]);
            setPassouPeloPonto(false);
            setMensagensDeAlteracaoDeSaldo([]);

            getJogadores();
            getPortfolio();
            getPosicao();

        } catch (error) {
            console.error("Erro ao trocar turno:", error);
        }
    };

    const getPortfolio = async () => {
        try {
            const response = await fetch('http://localhost:8080/jogador/portfolio');
            const data = await response.json();
            setPortfolio(data);
        } catch (error) {
            console.error("Erro ao buscar o portfólio:", error);
        }
    };

    const jogar = async () => {
        try {
            const dado1 = Math.floor(Math.random() * 6) + 1;
            const dado2 = Math.floor(Math.random() * 6) + 1;
            setDados({ dado1, dado2 });

            const response = await fetch(`http://localhost:8080/jogador/jogar?dado1=${dado1}&dado2=${dado2}`, {
                method: 'POST',
            });

            const data = await response.json();

            setPosicao({ nomeCasa: data.nomeCasaFinal, indiceCasa: data.indiceCasa });
            setCaminhoPercorrido(data.caminhoPercorrido);
            setPassouPeloPonto(data.passouPeloPonto);
            setMensagensDeAlteracaoDeSaldo(data.mensagensDeAlteracaoDeSaldo);

            getJogadores();
            getPortfolio();

            // Verifica se algum jogador foi eliminado ou se há um vencedor
            if (data.mensagensDeAlteracaoDeSaldo.some(mensagem =>
                mensagem.toLowerCase().includes('eliminado'))) {
                alert(data.nomeJogador + ' foi eliminado. O jogo terminou!');
                atualizarJogoAtivo(false);

                return;
            }

            if (data.vencedor) {
                alert('Jogador ' + (data.vencedor === 1 ? '1' : '2') + ' venceu o jogo!');
                atualizarJogoAtivo(false);
            }

        } catch (error) {
            console.error("Erro ao jogar:", error);
        }
    };

    const comprarLogradouro = async () => {
        if (posicao.indiceCasa === undefined || posicao.indiceCasa === null) {
            console.error("Índice de casa inválido. Tente novamente.");
            return;
        }

        console.log("Tentando comprar logradouro na posição:", posicao.indiceCasa);
        try {
            const response = await fetch(`http://localhost:8080/jogador/comprarLogradouro?casaIndex=${posicao.indiceCasa}`, {
                method: 'POST',
            });

            const message = await response.text();
            alert(message);

            getPortfolio();
            getPosicao();
            getJogadores();

        } catch (error) {
            console.error("Erro ao comprar logradouro:", error);
        }
    };

    const getPosicao = async () => {
        try {
            const response = await fetch('http://localhost:8080/jogador/posicao');
            const data = await response.json();
            setPosicao(data);
        } catch (error) {
            console.error("Erro ao buscar posição:", error);
        }
    };

    useEffect(() => {
        getPortfolio();
        getPosicao();
        getJogadores();
        getJogadorAtual();
    }, []);

    if (!portfolio) {
        return <div>Carregando...</div>;
    }

    return (
        <div className="App">
            <div className="header">
                <h1>Banco Imobiliário</h1>
                <div className="current-player">
                    Jogador Atual: {jogadorAtual}
                </div>
            </div>

            <div className="grid-container">
                {/* Lista de Jogadores */}
                <div className="card">
                    <h2 className="card-title">Jogadores</h2>
                    <ul className="player-list">
                        {jogadores.map((jogador, index) => (
                            <li key={index} className="player-item">
                                <span>{jogador.nome}</span>
                                <span className="balance">R${jogador.saldo}</span>
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Portfólio */}
                <div className="card">
                    <h2 className="card-title">Seu Portfólio</h2>
                    <div className="balance">Saldo: R${portfolio.saldo}</div>
                    <h3>Propriedades:</h3>
                    <ul className="properties-list">
                        {portfolio.propriedades && portfolio.propriedades.length === 0 ? (
                            <li className="empty-message">Você não tem propriedades no momento.</li>
                        ) : (
                            portfolio.propriedades.map((propriedade, index) => (
                                <li key={index} className="property-item">{propriedade}</li>
                            ))
                        )}
                    </ul>
                </div>

                {/* Posição Atual e Dados */}
                <div className="card">
                    <h2 className="card-title">Posição Atual</h2>
                    <div className="property-item">
                        <p>Casa atual: {posicao.nomeCasa}</p>
                        {posicao.nomeCasa && posicao.nomeCasa !== "" && (
                            <button onClick={comprarLogradouro} className="button button-buy" disabled={!jogoAtivo}>
                                Comprar Casa
                            </button>
                        )}
                    </div>

                    <h3 className="card-title">Dados</h3>
                    <div className="dice-container">
                        <div className="dice-box">
                            <p className="dice-label">Dado 1</p>
                            <p className="dice-value">{dados.dado1}</p>
                        </div>
                        <div className="dice-box">
                            <p className="dice-label">Dado 2</p>
                            <p className="dice-value">{dados.dado2}</p>
                        </div>
                    </div>
                    <div className="button-container">
                        <button
                            onClick={jogar}
                            className="button button-primary"
                            disabled={!jogoAtivo}
                        >
                            Lançar Dados
                        </button>
                        <button
                            onClick={trocarTurno}
                            className="button button-secondary"
                            disabled={!jogoAtivo}
                        >
                            Passar Turno
                        </button>
                    </div>
                </div>

                {/* Histórico */}
                <div className="card history-card">
                    <h2 className="card-title">Histórico do Jogo</h2>
                    <div className="history-grid">
                        <div className="history-section">
                            <h3>Trajeto Percorrido:</h3>
                            <ul className="history-list">
                                {caminhoPercorrido.length > 0 ? (
                                    caminhoPercorrido.map((casa, index) => (
                                        <li key={index} className="history-item">{casa}</li>
                                    ))
                                ) : (
                                    <li className="empty-message">Nenhuma casa percorrida ainda.</li>
                                )}
                            </ul>
                        </div>
                        <div className="history-section">
                            <h3>Alterações no Saldo:</h3>
                            <ul className="history-list">
                                {mensagensDeAlteracaoDeSaldo.length > 0 ? (
                                    mensagensDeAlteracaoDeSaldo.map((mensagem, index) => (
                                        <li key={index} className="history-item">{mensagem}</li>
                                    ))
                                ) : (
                                    <li className="empty-message">Nenhuma alteração no saldo.</li>
                                )}
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default App;
