package com.alura.presentation;

public enum EPais {
    BOLIVIA("Bolivia", "BO");

    private final String nombre;
    private final String codigo;

    EPais(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return nombre + " + codigo + ";
    }

}
