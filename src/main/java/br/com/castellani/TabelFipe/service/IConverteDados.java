package br.com.castellani.TabelFipe.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
