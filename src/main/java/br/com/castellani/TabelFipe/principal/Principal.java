package br.com.castellani.TabelFipe.principal;

import br.com.castellani.TabelFipe.visao.Interface;

public class Principal {

    public void exibeMenu() {
        // Instancia o objeto da tela
        Interface tela = new Interface();

        // Chama o método para exibir a janela
        tela.exibir();
    }
}