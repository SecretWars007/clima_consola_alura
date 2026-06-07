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
    Double lluvia1h,
    double indiceUV,
    String faseLunar
) {
    @Override
    public String toString() {
        String emojiClima = getEmoji(descripcion);
        String lluviaTxt = lluvia1h != null && lluvia1h > 0 
            ? String.format("🌧️ Lluvia 1h: %.1f mm", lluvia1h) 
            : "☀️ Sin lluvia";
        
        String uvTxt = getNivelUV(indiceUV); // nivel de riesgo
        
        return String.format("""
        %s 📍 %s, %s
        🌤️ Condición: %s
        🌡️ Actual: %.1f°C  Sensación: %.1f°C
        📉 Mín: %.1f°C  📈 Máx: %.1f°C
        💧 Humedad: %d%%  🔽 Presión: %d hPa
        💨 Viento: %.1f m/s
        ☀️ UV Index: %.1f %s  🌙 Fase Lunar: %s
        %s
        """, emojiClima, ciudad, pais, descripcion, temperatura, sensacionTermica,
           tempMin, tempMax, humedad, presion, viento, 
           indiceUV, uvTxt, faseLunar, lluviaTxt);
    }
    
    private String getEmoji(String desc) {
        if (desc.contains("cielo claro")) return "☀️";
        if (desc.contains("nubes")) return "☁️";
        if (desc.contains("lluvia")) return "🌧️";
        if (desc.contains("nieve")) return "❄️";
        if (desc.contains("tormenta")) return "⛈️";
        return "🌡️";
    }

    private String getNivelUV(double indiceUV) {
        if (indiceUV >= 11) return "Bajo";
        if (indiceUV >= 8) return "Alto";
        if (indiceUV >= 6) return "Moderado";
        if (indiceUV >= 3) return "Bajo";
        return "Ninguno";
    }
}