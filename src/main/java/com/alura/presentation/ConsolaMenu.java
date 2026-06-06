package com.alura.presentation;

import java.util.Arrays;
import java.util.Scanner;

import com.alura.application.ObtenerClimaUseCase;
import com.alura.domain.Clima;

public class ConsolaMenu {
    private final ObtenerClimaUseCase obtenerClimaUseCase;
    private final Scanner scanner = new Scanner(System.in);
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";

    public ConsolaMenu(ObtenerClimaUseCase obtenerClimaUseCase) {
        this.obtenerClimaUseCase = obtenerClimaUseCase;
    }

    public void iniciar() {
        mostrarBanner();
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            switch (opcion) {
                case 1 -> seleccionarPais();
                case 2 -> System.out.println("Gracias por usar el sistema de clima. ¡Hasta luego!");
                default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 2);
        scanner.close();
        System.out.println("Gracias por usar el sistema de clima. ¡Hasta luego!");
    }

    private void mostrarMenu() {
        System.out.println(YELLOW + """
                ┌─────────────────────────┐
                │ 1. 🔍 Consultar clima  │
                │ 2. 👋 Salir            │
                └─────────────────────────┘""" + RESET);
        System.out.print(GREEN + "Elige tu destino: " + RESET);
    }

    private void seleccionarPais() {
        System.out.println("Seleccione un país:");
        for (EPais pais : EPais.values()) {
            System.out.println(pais.ordinal() + 1 + ". " + pais.getNombre());
        }
        System.out.print("Seleccione ciudad: ");
        int opcion = leerOpcion();
        if (opcion < 1 || opcion > EPais.values().length) {
            System.out.println("Opción no válida. Por favor, intente de nuevo.");
            return;
        }
        var paisSeleccionado = EPais.values()[opcion - 1];
        seleccionarCiudad(paisSeleccionado.getCodigo());
    }

    private void seleccionarCiudad(String pais) {
        System.out.println("Seleccione una ciudad:");
        ECiudad[] ciudades = Arrays.stream(ECiudad.values())
                .filter(c -> c.getPais().equals(pais))
                .toArray(ECiudad[]::new);
        for (int i = 0; i < ciudades.length; i++) {
            System.out.println((i + 1) + ". " + ciudades[i].getNombre());
        }
        System.out.print("9. Volver al menú principal");
        System.out.print("Seleccione ciudad: ");
        int opcion = leerOpcion();
        if (opcion == 9) {
            return;
        }
        if (opcion < 1 || opcion > ciudades.length) {
            System.out.println("Opción no válida. Por favor, intente de nuevo.");
            return;
        }
        var ciudadSeleccionada = ciudades[opcion - 1];
        consultarClima(ciudadSeleccionada);
    }

    private void consultarClima(ECiudad ciudad) {
        try {
            String nombreCiudad = ciudad.getNombre();
            String codigoPais = ciudad.getPais();
            System.out.println("Obteniendo clima para " + nombreCiudad + ", " + codigoPais + "...");
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.print("🌎 ");
            }
            System.out.println();
            var clima = obtenerClimaUseCase.ejecutar(nombreCiudad, codigoPais);
            mostrarReporteClima(clima);
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine(); // Espera a que el usuario presione Enter
        } catch (Exception e) {
            System.err.println("Error al obtener el clima: " + e.getMessage());
        }
    }

    private int leerOpcion() {
        try {
            System.out.print("Seleccione una opción: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, ingrese un número válido: ");
                scanner.nextLine();
            }
            return scanner.nextInt();
        } catch (NumberFormatException e) {
            return -1; // Opción no válida
        }

    }

    private void mostrarBanner() {
        System.out.println(CYAN + """

                 ☁️  ☀️  🌤️  🌧️  ❄️
                CIELOSCOPIO 3000
                 El clima, pero cool
                 ☁️  ☀️  🌤️  🌧️  ❄️
                """ + RESET);
    }

    private String mensajeDivertido(Clima clima) {
        double temp = clima.temperatura();
        String desc = clima.descripcion();

        if (desc.contains("cielo claro") && temp > 25) {
            return "🔥 Ponte bloqueador, está que arde!";
        }
        if (desc.contains("lluvia")) {
            return "☔ Sacá la sombrilla o te vas a mojar como sopa";
        }
        if (temp < 10) {
            return "🥶 Abrígate! Hace más frío que el corazón de tu ex";
        }
        return "😎 Clima perfecto para salir a pasear";
    }

    private void mostrarReporteClima(Clima c) {

        System.out.println(CYAN + "┌─────────────────────────────────────┐");
        System.out.printf("│ %-35s │%n", "🌤️ REPORTE CLIMÁTICO");
        System.out.println("├─────────────────────────────────────┤" + RESET);

        System.out.printf(YELLOW + "│ 📍 Ciudad: %-24s │%n" + RESET, c.ciudad() + ", " + c.pais());
        System.out.printf(YELLOW + "│ 🌤️ Condición: %-22s │%n" + RESET, c.descripcion());
        System.out.println(CYAN + "├─────────────────────────────────────┤" + RESET);

        System.out.printf(YELLOW + "│ 🌡️ Actual: %-5.1f°C  Sensación: %-5.1f°C │%n" + RESET, c.temperatura(),
                c.sensacionTermica());
        System.out.printf(YELLOW + "│ 📉 Mín: %-6.1f°C   📈 Máx: %-6.1f°C  │%n" + RESET, c.tempMin(), c.tempMax());
        System.out.printf(YELLOW + "│ 💧 Humedad: %-3d%%   🔽 Presión: %-4d hPa │%n" + RESET, c.humedad(), c.presion());
        System.out.printf(YELLOW + "│ 💨 Viento: %-5.1f m/s              │%n" + RESET, c.viento());

        if (c.lluvia1h() != null && c.lluvia1h() > 0) {
            System.out.printf(YELLOW + "│ 🌧️ Lluvia 1h: %-6.1f mm           │%n" + RESET, c.lluvia1h());
        } else {
            System.out.printf(YELLOW + "│ ☀️ Sin lluvia                      │%n" + RESET);
        }

        System.out.println(CYAN + "└─────────────────────────────────────┘" + RESET);
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
