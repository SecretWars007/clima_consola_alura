package com.alura.domain;

public interface IClimaRepository {
    Clima obtenerClima(String ciudad, String pais) throws Exception;
}
