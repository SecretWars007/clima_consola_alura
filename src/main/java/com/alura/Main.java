package com.alura;

import com.alura.application.ObtenerClimaUseCase;
import com.alura.infrastructure.OpenWeatherRepository;
import com.alura.presentation.ConsolaMenu;

public class Main {
    public static void main(String[] args) {
        var RepositorioClima = new OpenWeatherRepository();
        var ObtenerClimaUseCase = new ObtenerClimaUseCase(RepositorioClima);
        var ConsolaMenu = new ConsolaMenu(ObtenerClimaUseCase);
        ConsolaMenu.iniciar();
    }
}