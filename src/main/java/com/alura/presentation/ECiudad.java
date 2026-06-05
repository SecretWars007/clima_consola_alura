package com.alura.presentation;

public enum ECiudad {
    LA_PAZ("La Paz", EPais.BOLIVIA.getCodigo()),
    SANTA_CRUZ("Santa Cruz", EPais.BOLIVIA.getCodigo()),
    COCHABAMBA("Cochabamba", EPais.BOLIVIA.getCodigo()),
    SUCRE("Sucre", EPais.BOLIVIA.getCodigo()),
    ORURO("Oruro", EPais.BOLIVIA.getCodigo()),
    PANDO("Pando", EPais.BOLIVIA.getCodigo()),
    BENI("Beni", EPais.BOLIVIA.getCodigo()),
    TARIJA("Tarija", EPais.BOLIVIA.getCodigo());

    private final String nombre;
    private final String pais;

    ECiudad(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }
    
    @Override
    public String toString() {
        return nombre;
    }

}
