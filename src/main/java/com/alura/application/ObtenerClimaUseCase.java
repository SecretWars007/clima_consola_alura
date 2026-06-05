package com.alura.application;

import com.alura.domain.Clima;
import com.alura.domain.IClimaRepository;

public class ObtenerClimaUseCase {
    private final IClimaRepository climaRepository;

    public ObtenerClimaUseCase(IClimaRepository climaRepository) {
        this.climaRepository = climaRepository;
    }

    public Clima ejecutar(String ciudad, String pais) {
        try {
            if (ciudad == null || ciudad.isBlank() || pais == null || pais.isBlank()) {
                throw new IllegalArgumentException("La ciudad y el país no pueden estar vacíos.");
            }
            if (pais.length() != 2) {
                throw new IllegalArgumentException("El código del país debe tener exactamente 2 caracteres.");
            }
            var clima = climaRepository.obtenerClima(ciudad, pais);
            return clima;
        } catch (Exception e) {
            System.err.println("Error al obtener el clima: " + e.getMessage());
            throw new RuntimeException("Error al obtener el clima", e); 
        }
    }
}
