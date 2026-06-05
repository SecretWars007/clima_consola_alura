package com.alura.domain;

public record Clima(String ciudad, String Pais, String descripcion, double temperatura, int humedad) {

    @Override
    public String toString() {
        return "Clima{" +
                "ciudad='" + ciudad + '\'' +
                ", Pais='" + Pais + '\'' +
                ", temperatura=" + temperatura +
                ", humedad=" + humedad +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
