package com.merengones.wiki.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.merengones.wiki.models.Commit; 

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaginasController {

        private List<Commit> obtenerHistorialDeCommits() {
        RestTemplate restTemplate = new RestTemplate();
        String owner = "mgcruzp"; // Reemplaza con el nombre de usuario
        String repo = "Merengones"; // Reemplaza con el nombre del repositorio
        String apiUrl = String.format("https://api.github.com/repos/%s/%s/commits", owner, repo);
        
        List<Commit> commits = new ArrayList<>();
        try {
            ResponseEntity<JsonNode[]> response = restTemplate.getForEntity(apiUrl, JsonNode[].class);
            JsonNode[] commitsArray = response.getBody();

            for (JsonNode commitNode : commitsArray) {
                JsonNode commitData = commitNode.get("commit");
                String message = commitData.get("message").asText();
                String author = commitData.get("author").get("name").asText();
                String date = commitData.get("author").get("date").asText();
                
                // Limitar a los 5 últimos commits para no sobrecargar
                if (commits.size() < 5) {
                    commits.add(new Commit(message, author, date));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener commits de GitHub: " + e.getMessage());
            // Si hay un error, devolvemos una lista vacía para no romper la página
            return new ArrayList<>();
        }
        return commits;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("commits", obtenerHistorialDeCommits());
        return "index";
    }


    @GetMapping("/equipo")
    public String equipo() {
        return "equipo";
    }

    @GetMapping("/desarrollo")
    public String desarrollo() {
        return "desarrollo";
    }

    @GetMapping("/documentacion")
    public String documentacion() {
        return "documentacion";
    }

    @GetMapping("/pruebas")
    public String pruebas() {
        return "pruebas";
    }
}