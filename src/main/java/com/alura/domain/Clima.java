package com.alura.domain;

public record Clima(
    String ciudad,
    String pais,
    String descripcion,
    double temperatura,
    double sensacionTermica,
    double tempMin,
    double tempMax,
    int humedad,
    int presion,
    double viento,
    Double lluvia1h
) {
    @Override
    public String toString() {
        String emojiClima = getEmoji(descripcion);
        String lluviaTxt = lluvia1h != null && lluvia1h > 0 
            ? String.format("🌧️ Lluvia 1h: %.1f mm", lluvia1h) 
            : "☀️ Sin lluvia";
        
        return String.format("""
        %s 📍 %s, %s
        🌤️ Condición: %s
        🌡️ Actual: %.1f°C  Sensación: %.1f°C
        📉 Mín: %.1f°C  📈 Máx: %.1f°C
        💧 Humedad: %d%%  🔽 Presión: %d hPa
        💨 Viento: %.1f m/s
        %s
        """, emojiClima, ciudad, pais, descripcion, temperatura, sensacionTermica,
           tempMin, tempMax, humedad, presion, viento, lluviaTxt);
    }
    
    private String getEmoji(String desc) {
        if (desc.contains("cielo claro")) return "☀️";
        if (desc.contains("nubes")) return "☁️";
        if (desc.contains("lluvia")) return "🌧️";
        if (desc.contains("nieve")) return "❄️";
        if (desc.contains("tormenta")) return "⛈️";
        return "🌡️";
    }
}