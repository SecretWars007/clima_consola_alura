package com.alura.infrastructure;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;

import com.alura.domain.Clima;
import com.alura.domain.IClimaRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.cdimascio.dotenv.Dotenv;

public class OpenWeatherRepository implements IClimaRepository {
        private final String apiKey;
        private final String apiUrl;
        private final HttpClient httpClient = HttpClient.newHttpClient();
        
        public OpenWeatherRepository() {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            this.apiKey = dotenv.get("OPENWEATHER_API_KEY");
            this.apiUrl = dotenv.get("OPENWEATHER_API_URL");

            if (apiKey == null || apiKey.isBlank()) 
                throw new IllegalStateException("La clave de API de OpenWeather no está configurada.");
            

            if (apiUrl == null || apiUrl.isBlank()) 
                throw new IllegalStateException("La URL de la API de OpenWeather no está configurada.");       
        }

        @Override
        public Clima obtenerClima(String ciudad, String pais) throws Exception {
            String query = URLEncoder.encode(ciudad + "," + pais, StandardCharsets.UTF_8);
            var url = String.format("%s?q=%s&appid=%s&units=metric&lang=es", apiUrl, query, apiKey);
            var request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .GET()
                    .build();

            var response = httpClient.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error al obtener el clima: " + response.body());
            }

            JsonObject jsonResponse = new JsonParser().parse(response.body()).getAsJsonObject();
            double temp = jsonResponse.getAsJsonObject("main").get("temp").getAsDouble();
            int humidity = jsonResponse.getAsJsonObject("main").get("humidity").getAsInt();
            String description = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
            double feelslike = jsonResponse.getAsJsonObject("main").get("feels_like").getAsDouble();
            double tempMin = jsonResponse.getAsJsonObject("main").get("temp_min").getAsDouble();
            double tempMax = jsonResponse.getAsJsonObject("main").get("temp_max").getAsDouble();
            int pressure = jsonResponse.getAsJsonObject("main").get("pressure").getAsInt();
            double windSpeed = jsonResponse.getAsJsonObject("wind").get("speed").getAsDouble();
            Double lluvia1h = null;
            if (jsonResponse.has("rain") && jsonResponse.getAsJsonObject("rain").has("1h")) {
                lluvia1h = jsonResponse.getAsJsonObject("rain").get("1h").getAsDouble();
            }

            return new Clima(
                    ciudad,
                    pais,
                    description,
                    temp,
                    feelslike,
                    tempMin,
                    tempMax,
                    humidity,
                    pressure,
                    windSpeed,
                    lluvia1h
            );
    }

}
